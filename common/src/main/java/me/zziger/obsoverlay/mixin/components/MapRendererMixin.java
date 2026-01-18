package me.zziger.obsoverlay.mixin.components;

import com.mojang.blaze3d.vertex.PoseStack;
import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OverlayRenderer;
import me.zziger.obsoverlay.OverlayUtils;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.renderer.MapRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.state.MapRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MapRenderer.class)
public class MapRendererMixin {
    @Inject(method = "render(Lnet/minecraft/client/renderer/state/MapRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ZI)V", at = @At("HEAD"))
    private static void draw(MapRenderState state, PoseStack matrices, MultiBufferSource vertexConsumers, boolean bl, int light, CallbackInfo ci) {
        OverlayRenderer renderer = OBSOverlay.getRenderer();

        if (renderer != null && !renderer.renderingHands) {
            OverlayUtils.forceDraw(vertexConsumers);
            OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.itemFrameMap);
        }
    }

    @Inject(method = "render(Lnet/minecraft/client/renderer/state/MapRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ZI)V", at = @At("RETURN"))
    private static void drawEnd(MapRenderState state, PoseStack matrices, MultiBufferSource vertexConsumers, boolean bl, int light, CallbackInfo ci) {
        OverlayRenderer renderer = OBSOverlay.getRenderer();

        if (renderer != null && !renderer.renderingHands) {
            OverlayUtils.forceDraw(vertexConsumers);
            OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.itemFrameMap);
        }
    }
}
