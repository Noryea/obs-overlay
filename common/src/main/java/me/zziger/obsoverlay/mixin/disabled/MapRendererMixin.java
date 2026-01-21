package me.zziger.obsoverlay.mixin.disabled;

import net.minecraft.client.renderer.MapRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MapRenderer.class)
public class MapRendererMixin {
    /*@Inject(method = "render(Lnet/minecraft/client/renderer/state/MapRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ZI)V", at = @At("HEAD"))
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
    }*/
}
