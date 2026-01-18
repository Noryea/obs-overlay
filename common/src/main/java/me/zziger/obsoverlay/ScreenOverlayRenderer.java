package me.zziger.obsoverlay;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

public class ScreenOverlayRenderer {

    public static void beforeScreenRender(Screen instance) {
        boolean overlay = OBSOverlayConfig.isScreenOverlayed(instance);
        if (overlay) {
            OBSOverlay.getAPI().beginDraw(OverlayFramebufferType.NORMAL);
        }
    }

    public static void afterScreenRender(Screen instance, GuiGraphics guiGraphics) {
        guiGraphics.flush();
        boolean overlay = OBSOverlayConfig.isScreenOverlayed(instance);
        if (overlay) {
            OBSOverlay.getAPI().endDraw(OverlayFramebufferType.NORMAL);
        }
    }
}
