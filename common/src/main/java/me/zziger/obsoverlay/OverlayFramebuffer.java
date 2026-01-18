package me.zziger.obsoverlay;

import com.mojang.blaze3d.pipeline.RenderTarget;

public class OverlayFramebuffer {
    public RenderTarget object;
    public boolean dirty;

    OverlayFramebuffer(RenderTarget object) {
        this.object = object;
        this.dirty = false;
    }
}
