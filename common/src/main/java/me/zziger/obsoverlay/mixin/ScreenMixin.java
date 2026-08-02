package me.zziger.obsoverlay.mixin;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OBSOverlayConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(method = "renderBlurredBackground", at = @At("HEAD"), cancellable = true)
    private void renderBlurredBackground(CallbackInfo ci) {
        if (OBSOverlayConfig.isScreenOverlayed((Screen) (Object) this))
            ci.cancel();
    }

    @ModifyVariable(method = "renderWithTooltipAndSubtitles", at = @At("HEAD"), argsOnly = true, index = 1)
    private GuiGraphics onExtractRenderState(GuiGraphics guiGraphics) {
        Screen screen = (Screen) (Object) this;
        if (OBSOverlayConfig.isScreenOverlayed(screen)) {
            return OBSOverlay.getAPI().getOverlayGuiGraphics();
        }
        return guiGraphics;
    }

    @Inject(method = "renderWithTooltipAndSubtitles", at = @At("RETURN"))
    private void forceFlushDeferred(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        GuiGraphics overlayGuiGraphics = OBSOverlay.getAPI().getOverlayGuiGraphics();
        if (overlayGuiGraphics != null) overlayGuiGraphics.renderDeferredElements();
    }
}
