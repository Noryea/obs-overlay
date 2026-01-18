package me.zziger.obsoverlay.mixin;

import me.zziger.obsoverlay.OBSOverlay;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Inject(method = "renderLevel(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V", at = @At(value = "RETURN"))
    private void renderWorld(CallbackInfo ci) {
        OBSOverlay.getAPI().backupDepth(false);
    }

}
