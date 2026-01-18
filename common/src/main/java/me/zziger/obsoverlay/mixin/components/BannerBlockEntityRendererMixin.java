package me.zziger.obsoverlay.mixin.components;

import com.mojang.blaze3d.vertex.PoseStack;
import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OverlayUtils;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BannerRenderer.class)
public class BannerBlockEntityRendererMixin {
    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/BannerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("HEAD"))
    private static void renderPattern(BannerBlockEntity bannerBlockEntity, float f, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumerProvider);
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.bannerCanvas);
    }

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/BannerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("RETURN"))
    private static void renderPatternEnd(BannerBlockEntity bannerBlockEntity, float f, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumerProvider);
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.bannerCanvas);
    }
}
