package me.zziger.obsoverlay.mixin.components;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugScreenOverlay.class)
public class DebugHudMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void drawStart(GuiGraphics context, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.debugMenu);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void drawEnd(GuiGraphics context, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.debugMenu);
    }
}
