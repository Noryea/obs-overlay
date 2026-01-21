package me.zziger.obsoverlay.mixin.disabled;

import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BeaconRenderer.class)
public class BeaconBlockEntityRendererMixin {
    /*@Inject(method = "renderBeaconBeam(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/resources/ResourceLocation;FFJIIIFF)V", at = @At("HEAD"))
    private static void renderPattern(PoseStack matrices, MultiBufferSource vertexConsumers, ResourceLocation textureId, float tickDelta, float heightScale, long worldTime, int yOffset, int maxY, int color, float innerRadius, float outerRadius, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.beaconBeam);
    }

    @Inject(method = "renderBeaconBeam(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/resources/ResourceLocation;FFJIIIFF)V", at = @At("RETURN"))
    private static void renderPatternEnd(PoseStack matrices, MultiBufferSource vertexConsumers, ResourceLocation textureId, float tickDelta, float heightScale, long worldTime, int yOffset, int maxY, int color, float innerRadius, float outerRadius, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.beaconBeam);
    }*/
}
