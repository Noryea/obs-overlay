package me.zziger.obsoverlay.api.impl;

import me.zziger.obsoverlay.OverlayRenderer;
import me.zziger.obsoverlay.api.IOverlayAPI;
import me.zziger.obsoverlay.component.IOverlayComponent;

public class NormalOverlayAPI implements IOverlayAPI {
    private final OverlayRenderer renderer;

    public NormalOverlayAPI(OverlayRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void beginDraw(IOverlayComponent component) {
        this.renderer.beginDraw(component);
    }

    @Override
    public void endDraw(IOverlayComponent component) {
        this.renderer.endDraw(component);
    }

    @Override
    public void beginDraw() {
        this.renderer.beginDraw();
    }

    @Override
    public void endDraw() {
        this.renderer.endDraw();
    }
}
