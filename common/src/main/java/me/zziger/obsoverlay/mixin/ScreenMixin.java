package me.zziger.obsoverlay.mixin;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OBSOverlayConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(method = "extractBlurredBackground", at = @At("HEAD"), cancellable = true)
    private void extractBlurredBackground(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        Screen screen = (Screen) (Object) this;
        if (OBSOverlayConfig.isScreenOverlayed(screen)) {
            ci.cancel();
        }
    }

    @ModifyVariable(method = "extractRenderState", at = @At("HEAD"), argsOnly = true, index = 1)
    private GuiGraphicsExtractor onExtractRenderState(GuiGraphicsExtractor graphics) {
        Screen screen = (Screen) (Object) this;
        if (OBSOverlayConfig.isScreenOverlayed(screen)) {
            return OBSOverlay.getAPI().getOverlayGuiGraphicsExtractor();
        }
        return graphics;
    }

    @ModifyVariable(method = "extractBackground", at = @At("HEAD"), argsOnly = true, index = 1)
    private GuiGraphicsExtractor onExtractBackground(GuiGraphicsExtractor graphics) {
        Screen screen = (Screen) (Object) this;
        if (OBSOverlayConfig.isScreenOverlayed(screen)) {
            return OBSOverlay.getAPI().getOverlayGuiGraphicsExtractor();
        }
        return graphics;
    }

    @Inject(method = "extractRenderStateWithTooltipAndSubtitles", at = @At("RETURN"))
    private void forceFlushDeferred(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
        GuiGraphicsExtractor overlayGuiGraphics = OBSOverlay.getAPI().getOverlayGuiGraphicsExtractor();
        if (overlayGuiGraphics != null) overlayGuiGraphics.extractDeferredElements(mouseX, mouseY, a);
    }
}
