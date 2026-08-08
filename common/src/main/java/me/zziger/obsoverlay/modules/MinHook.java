package me.zziger.obsoverlay.modules;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface MinHook extends StdCallLibrary {
    // MinHook MH_STATUS codes
    int MH_OK = 0;
    int MH_ERROR_ALREADY_INITIALIZED = 1;
    int MH_ERROR_NOT_INITIALIZED = 2;
    int MH_ERROR_ALREADY_CREATED = 3;
    int MH_ERROR_NOT_CREATED = 4;
    int MH_ERROR_ENABLED = 5;
    int MH_ERROR_DISABLED = 6;
    int MH_ERROR_NOT_EXECUTABLE = 7;
    int MH_ERROR_UNSUPPORTED_FUNCTION = 8;
    int MH_ERROR_MEMORY_ALLOC = 9;
    int MH_ERROR_MEMORY_PROTECT = 10;
    int MH_ERROR_MODULE_NOT_FOUND = 11;
    int MH_ERROR_FUNCTION_NOT_FOUND = 12;
    int MH_ERROR_INTERNAL = 13;

    interface wglSwapBuffers extends StdCallCallback {
        boolean callback(Pointer hDc);
    }

    int MH_Initialize();
    int MH_CreateHook(Pointer method, wglSwapBuffers hook, PointerByReference origMethod);
    int MH_EnableHook(Pointer method);
    int MH_DisableHook(Pointer method);
    int MH_RemoveHook(Pointer method);
}
