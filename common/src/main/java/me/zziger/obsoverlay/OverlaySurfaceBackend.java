package me.zziger.obsoverlay;

import com.mojang.blaze3d.opengl.GlCommandEncoder;
import com.mojang.blaze3d.systems.CommandEncoderBackend;
import com.mojang.blaze3d.systems.GpuSurface;
import com.mojang.blaze3d.systems.GpuSurfaceBackend;
import com.mojang.blaze3d.systems.SurfaceException;
import com.mojang.blaze3d.textures.GpuTextureView;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Set;

/**
 * Backend for the overlay's own {@link GpuSurface}.
 *
 * <p>The main game presents via {@code Minecraft.windowSurface} (a {@link GpuSurface} wrapping
 * {@link GlSurface}): after rendering the frame it blits the main render target onto the window's
 * back buffer, then {@code present()} calls {@code glfwSwapBuffers}. This backend mirrors that
 * surface contract for the overlay framebuffer: {@code blitFromTexture} uses the exact same
 * {@link GlCommandEncoder#presentTexture} path as {@link GlSurface}, but it is driven from the
 * {@code wglSwapBuffers} hook — i.e. after OBS captured the frame — so the overlay is composited
 * only for the player, not for the capture.
 *
 * <p>Only the OpenGL backend is supported today.
 */
public class OverlaySurfaceBackend implements GpuSurfaceBackend {
    private int width;
    private int height;

    @Override
    public void configure(GpuSurface.Configuration config) throws SurfaceException {
        this.width = config.width();
        this.height = config.height();
    }

    @Override
    public boolean isSuboptimal() {
        return false;
    }

    @Override
    public void acquireNextTexture() throws SurfaceException {
        // Not a real swapchain — there is nothing to acquire.
    }

    @Override
    public void blitFromTexture(CommandEncoderBackend commandEncoder, GpuTextureView textureView) {
        if (commandEncoder instanceof GlCommandEncoder glCommandEncoder) {
            // Blit the overlay framebuffer onto the window back buffer, exactly like
            // GlSurface.blitFromTexture does for the main render target.
            glCommandEncoder.presentTexture(textureView, this.width, this.height);
        } else {
            throw new UnsupportedOperationException(
                    "Overlay surface only supports the OpenGL backend, got " + commandEncoder.getClass().getName());
        }
    }

    @Override
    public void present() {
        // The main windowSurface performs the actual buffer swap.
    }

    @Override
    public void close() {
        // Nothing to release.
    }

    @Override
    @NonNull
    public Collection<GpuSurface.PresentMode> supportedPresentModes() {
        return Set.of(GpuSurface.PresentMode.IMMEDIATE);
    }
}
