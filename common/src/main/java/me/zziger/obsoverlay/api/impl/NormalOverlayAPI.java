package me.zziger.obsoverlay.api.impl;

import me.zziger.obsoverlay.OverlayRenderer;
import me.zziger.obsoverlay.api.IOverlayAPI;
import me.zziger.obsoverlay.component.IOverlayComponent;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class NormalOverlayAPI implements IOverlayAPI {
    private final OverlayRenderer renderer;

    public NormalOverlayAPI(OverlayRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public GuiGraphicsExtractor getGuiGraphicsExtractor(IOverlayComponent component, GuiGraphicsExtractor original) {
        return this.renderer.getGuiGraphicsExtractor(component, original);
    }

    @Override
    public GuiGraphicsExtractor getOverlayGuiGraphicsExtractor() {
        return this.renderer.getGuiGraphicsExtractor();
    }
}
