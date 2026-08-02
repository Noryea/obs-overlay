package me.zziger.obsoverlay.api;

import me.zziger.obsoverlay.DummyGuiGraphicsExtractor;
import me.zziger.obsoverlay.component.IOverlayComponent;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public interface IOverlayAPI {
    /**
     * Should be called before submitting a specific component to a `GuiGraphicsExtractor`
     * This will hide component only if it is enabled in settings
     * @param component Component that is being drawn
     */
    default GuiGraphicsExtractor getGuiGraphicsExtractor(IOverlayComponent component, GuiGraphicsExtractor original) {
        return original;
    }

    /**
     * Should be called before rendering elements you want hidden from stream to a `GuiGraphicsExtractor`
     * This method does not check settings
     */
    default GuiGraphicsExtractor getOverlayGuiGraphicsExtractor() {
        return DummyGuiGraphicsExtractor.INSTANCE;
    }
}
