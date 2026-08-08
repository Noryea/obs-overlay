package me.zziger.obsoverlay.mixin;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OverlayRenderer;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "onGameLoadFinished", at = @At("RETURN"))
    private void onGameLoadFinished(CallbackInfo ci) {
        OBSOverlay.initRender();
    }

    @Inject(method = "resizeDisplay()V", at = @At("RETURN"))
    private void onResolutionChanged(CallbackInfo ci) {
        OverlayRenderer renderer = OBSOverlay.getRenderer();
        if (renderer != null)
            renderer.onResolutionChanged((Minecraft)(Object)this);
    }

    @Inject(method = "runTick(Z)V", at = @At("HEAD"))
    private void onRender(boolean tick, CallbackInfo ci) {
        OverlayRenderer renderer = OBSOverlay.getRenderer();
        if (renderer != null)
            renderer.beginFrame();
    }
}
