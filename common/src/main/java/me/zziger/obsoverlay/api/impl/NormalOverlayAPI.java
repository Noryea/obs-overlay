package me.zziger.obsoverlay.api.impl;

import me.zziger.obsoverlay.OverlayRenderer;
import me.zziger.obsoverlay.api.IOverlayAPI;
import me.zziger.obsoverlay.component.IOverlayComponent;
import net.minecraft.client.gui.GuiGraphics;

public class NormalOverlayAPI implements IOverlayAPI {
    private final OverlayRenderer renderer;

    public NormalOverlayAPI(OverlayRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public GuiGraphics getGuiGraphics(IOverlayComponent component, GuiGraphics original) {
        return this.renderer.getGuiGraphics(component, original);
    }

    @Override
    public GuiGraphics getOverlayGuiGraphics() {
        return this.renderer.getGuiGraphics();
    }
}
