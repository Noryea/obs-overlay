package me.zziger.obsoverlay;

import com.mojang.blaze3d.pipeline.RenderTarget;
import org.jetbrains.annotations.NotNull;

public class OverlayFramebuffer {
    @NotNull
    public final RenderTarget object;
    public boolean dirty;

    OverlayFramebuffer(@NotNull RenderTarget object) {
        this.object = object;
        this.dirty = false;
    }
}
