package me.zziger.obsoverlay.mixin;

import me.zziger.obsoverlay.OBSOverlayConfig;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(method = "renderBlurredBackground", at = @At("HEAD"), cancellable = true)
    private void renderBlur(CallbackInfo ci) {
        if (OBSOverlayConfig.isScreenOverlayed((Screen) (Object) this))
            ci.cancel();
    }
}
