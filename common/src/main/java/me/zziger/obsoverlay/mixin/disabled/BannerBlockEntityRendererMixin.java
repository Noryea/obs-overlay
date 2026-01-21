package me.zziger.obsoverlay.mixin.disabled;

import net.minecraft.client.renderer.blockentity.BannerRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BannerRenderer.class)
public class BannerBlockEntityRendererMixin {
    /*@Inject(method = "render(Lnet/minecraft/world/level/block/entity/BannerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("HEAD"))
    private static void renderPattern(BannerBlockEntity bannerBlockEntity, float f, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumerProvider);
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.bannerCanvas);
    }

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/BannerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("RETURN"))
    private static void renderPatternEnd(BannerBlockEntity bannerBlockEntity, float f, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumerProvider);
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.bannerCanvas);
    }*/
}
