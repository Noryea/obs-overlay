package me.zziger.obsoverlay.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.zziger.obsoverlay.*;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "renderItemInHand(Lnet/minecraft/client/Camera;FLorg/joml/Matrix4f;)V", at = @At(value = "HEAD"))
    private void renderHand(Camera camera, float tickDelta, Matrix4f matrix4f, CallbackInfo ci) {
        OverlayRenderer renderer = OBSOverlay.getRenderer();
        if (renderer != null) renderer.renderingHands = true;
        OBSOverlay.getAPI().backupDepth(true);
    }

    @Inject(method = "renderItemInHand(Lnet/minecraft/client/Camera;FLorg/joml/Matrix4f;)V", at = @At(value = "RETURN"))
    private void renderHandEnd(Camera camera, float tickDelta, Matrix4f matrix4f, CallbackInfo ci) {
        OverlayRenderer renderer = OBSOverlay.getRenderer();
        if (renderer != null) renderer.renderingHands = false;
        OBSOverlay.getAPI().backupDepth(true);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;flush()V", shift = At.Shift.AFTER))
    private void renderTestIcon(DeltaTracker tickCounter, boolean tick, CallbackInfo ci, @Local() GuiGraphics instance) {
        if (OBSOverlayConfig.get().showTestIcon && OBSOverlay.getIsInitialized()) {
            instance.flush();
            OBSOverlay.getAPI().beginDraw(OverlayFramebufferType.NORMAL);
            try {
                instance.blitSprite(RenderType::guiTextured, ResourceLocation.withDefaultNamespace("icon/checkmark"), 0, 0, 16, 16);
                instance.flush();
            } catch (Exception ignored) {
            }
            OBSOverlay.getAPI().endDraw(OverlayFramebufferType.NORMAL);
        }
    }
}
