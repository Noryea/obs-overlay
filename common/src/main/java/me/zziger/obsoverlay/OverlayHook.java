package me.zziger.obsoverlay;

import com.sun.jna.Function;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import me.zziger.obsoverlay.error.OverlayHookException;
import me.zziger.obsoverlay.modules.Kernel32;
import me.zziger.obsoverlay.modules.MinHook;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class OverlayHook {
    private static final long REINSTALL_DEBOUNCE_MILLIS = 100L;
    private static boolean libraryInitialized = false;
    private static PointerByReference reference;
    private static final ArrayList<Handler> handlerList = new ArrayList<>();
    private static MinHook minHook;
    private static int lastFramebufferWidth = -1;
    private static int lastFramebufferHeight = -1;
    private static volatile long lastResizeTimeMillis = 0;
    private static volatile boolean pendingReinstall = false;

    private OverlayHook() {
    }

    public interface Handler {
        void run();
    }

    public static void subscribe(Handler handler) {
        handlerList.add(handler);
    }

    public static void unsubscribe(Handler handler) {
        handlerList.remove(handler);
    }

    public static MinHook getMinHook() {
        if (minHook == null) return minHook = Native.load("MinHook", MinHook.class);
        return minHook;
    }

    public static void init() {
        if (libraryInitialized) return;
        initLibrary();
        initHook();
        Minecraft client = Minecraft.getInstance();
        lastFramebufferWidth = client.getWindow().getWidth();
        lastFramebufferHeight = client.getWindow().getHeight();
        libraryInitialized = true;
    }

    private static void initLibrary() {
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            throw new OverlayHookException("OBS Overlay is only supported on Windows");
        }

        String arch = System.getProperty("os.arch").toLowerCase();

        if (arch.contains("aarch")) {
            throw new OverlayHookException("OBS Overlay is only supported on x64 and x86 systems");
        }

        boolean is64 = arch.equals("x86_64") || arch.equals("amd64") || arch.equals("x64") || arch.equals("ia64");
        InputStream libFile = OBSOverlay.class.getResourceAsStream(is64 ? "/lib/MinHook.x64.dll" : "/lib/MinHook.x86.dll");
        if (libFile == null) {
            throw new OverlayHookException("Failed to get MinHook dll");
        }

        File nativeDir = OverlayEnvironment.getGameFolder().resolve("native").toFile();
        File copyLibFile = new File(nativeDir, "MinHook.dll");
        nativeDir.mkdir();

        try {
            FileOutputStream fos = new FileOutputStream(copyLibFile);
            copyLibFile.createNewFile();
            IOUtils.copy(libFile, fos);
            fos.close();
        } catch (IOException e) {
            throw new OverlayHookException("Failed to copy dependency dll");
        }

        System.setProperty("jna.library.path", nativeDir.getAbsolutePath());
        OBSOverlay.LOGGER.info("Copied dependency DLL successfully");
    }

    private static MinHook.wglSwapBuffers buildHookCallback() {
        return (hDc) -> {
            // debounced so a continuous window drag don't break swapping update.
            if (System.currentTimeMillis() - lastResizeTimeMillis < REINSTALL_DEBOUNCE_MILLIS) return true;
            for (Handler handler : handlerList) {
                try {
                    handler.run();
                } catch (Throwable t) {
                    OBSOverlay.LOGGER.error("Overlay hook handler failed", t);
                }
            }
            Function origFunction = Function.getFunction(reference.getValue(), Function.ALT_CONVENTION);
            boolean result = (boolean) origFunction.invoke(Boolean.class, new Object[]{hDc});
            if (pendingReinstall) rebuildHookAtomic();
            return result;
        };
    }

    private static Pointer getHookMethod() {
        Pointer module = Kernel32.INSTANCE.GetModuleHandleA("opengl32.dll");
        Pointer proc = Kernel32.INSTANCE.GetProcAddress(module, "wglSwapBuffers");
        if (proc == null) {
            throw new OverlayHookException("Failed to locate wglSwapBuffers, which is needed to call");
        }
        return proc;
    }

    private static void initHook() {
        Pointer proc = getHookMethod();

        try {
            MinHook minhook = getMinHook();
            int r = minhook.MH_Initialize();
            if (r != MinHook.MH_OK) throw new OverlayHookException("MH_Initialize failed: " + r);

            reference = new PointerByReference();
            r = minhook.MH_CreateHook(proc, buildHookCallback(), reference);
            if (r != MinHook.MH_OK) throw new OverlayHookException("MH_CreateHook failed: " + r);

            r = minhook.MH_EnableHook(proc);
            if (r != MinHook.MH_OK) throw new OverlayHookException("MH_EnableHook failed: " + r);
        } catch (Exception e) {
            OBSOverlay.LOGGER.error("Failed to initialize MinHook");
            throw e;
        }
    }

    public static void reinstallHook() {
        if (!libraryInitialized) return;

        Minecraft client = Minecraft.getInstance();
        int width = client.getWindow().getWidth();
        int height = client.getWindow().getHeight();
        if (width == lastFramebufferWidth && height == lastFramebufferHeight) return;

        OBSOverlay.LOGGER.debug("Resize to {}x{} detected; hook will be reinstalled once the resize settles", width, height);
        lastFramebufferWidth = width;
        lastFramebufferHeight = height;
        pendingReinstall = true;
        lastResizeTimeMillis = System.currentTimeMillis();
    }

    private static void rebuildHookAtomic() {
        if (!pendingReinstall) return;
        Pointer proc = getHookMethod();
        MinHook mh = getMinHook();
        PointerByReference newRef = new PointerByReference();

        OBSOverlay.LOGGER.debug("Reinstalling wglSwapBuffers hook after resize");
        int r = mh.MH_DisableHook(proc);
        if (r != MinHook.MH_OK && r != MinHook.MH_ERROR_DISABLED) {
            OBSOverlay.LOGGER.warn("MH_DisableHook failed ({}), aborting reinstall", r);
            pendingReinstall = false;
            return;
        }
        r = mh.MH_RemoveHook(proc);
        if (r != MinHook.MH_OK) {
            OBSOverlay.LOGGER.warn("MH_RemoveHook failed ({}), aborting reinstall", r);
            pendingReinstall = false;
            return;
        }
        // The entry is now restored to the bytes MinHook captured at the first `MH_CreateHook`.
        // If the rebuild fails we are simply detached (overlay stops compositing) rather
        // than frozen, because this frame's swap already completed.
        r = mh.MH_CreateHook(proc, buildHookCallback(), newRef);
        if (r != MinHook.MH_OK) {
            OBSOverlay.LOGGER.error("MH_CreateHook failed ({}); overlay hook is detached", r);
            pendingReinstall = false;
            return;
        }
        r = mh.MH_EnableHook(proc);
        if (r != MinHook.MH_OK) {
            OBSOverlay.LOGGER.error("MH_EnableHook failed ({}); hook created but not enabled", r);
            pendingReinstall = false;
            return;
        }
        reference = newRef;
        pendingReinstall = false;
    }
}
