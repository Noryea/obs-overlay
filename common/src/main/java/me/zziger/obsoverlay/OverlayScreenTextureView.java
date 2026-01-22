package me.zziger.obsoverlay;

import com.mojang.blaze3d.opengl.DirectStateAccess;
import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.opengl.GlTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import org.jetbrains.annotations.NotNull;

// A Fake Texture View, to insure FBO=0
public class OverlayScreenTextureView extends GlTextureView {
    private final int width;
    private final int height;

    public OverlayScreenTextureView(int width, int height) {
        super(new ScreenTexture(width, height), 0, 1);
        this.width = width;
        this.height = height;
    }

    @Override
    public int getFbo(@NotNull DirectStateAccess dsa, com.mojang.blaze3d.textures.GpuTexture depth) {
        return 0;
    }

    @Override
    public void close() {
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public int getWidth(int mipLevel) {
        return width;
    }

    @Override
    public int getHeight(int mipLevel) {
        return height;
    }

    // Fake Texture
    private static class ScreenTexture extends GlTexture {
        public ScreenTexture(int width, int height) {
            super(
                    USAGE_RENDER_ATTACHMENT,
                    "Screen",
                    TextureFormat.RGBA8,
                    width,
                    height,
                    1,
                    1,
                    0   // Screen
            );
        }

        @Override
        public void close() {
        }

        @Override
        public boolean isClosed() {
            return false;
        }

        @Override
        public void addViews() {
        }

        @Override
        public void removeViews() {
        }
    }
}