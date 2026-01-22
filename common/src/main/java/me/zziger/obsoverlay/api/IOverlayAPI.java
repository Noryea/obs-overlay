package me.zziger.obsoverlay.api;

import me.zziger.obsoverlay.DummyGuiGraphics;
import me.zziger.obsoverlay.component.IOverlayComponent;
import net.minecraft.client.gui.GuiGraphics;

public interface IOverlayAPI {
    /**
     * Should be called before submitting a specific component to a `GuiGraphics`
     * This will hide component only if it is enabled in settings
     * @param component Component that is being drawn
     */
    default GuiGraphics getGuiGraphics(IOverlayComponent component, GuiGraphics original) {
        return original;
    }

    /**
     * Should be called before rendering elements you want hidden from stream to a `GuiGraphics`
     * This method does not check settings
     */
    default GuiGraphics getOverlayGuiGraphics() {
        return DummyGuiGraphics.INSTANCE;
    }
}
