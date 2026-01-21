package me.zziger.obsoverlay.mixin.disabled;

import net.minecraft.client.renderer.blockentity.AbstractSignRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractSignRenderer.class)
public class AbstractSignBlockEntityRendererMixin {
    /*@Inject(method = "renderSignText(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/SignText;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IIIZ)V", at = @At("HEAD"))
    void renderText(BlockPos pos, SignText text, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int textLineHeight, int maxTextWidth, boolean front, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.signText);
    }

    @Inject(method = "renderSignText(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/SignText;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IIIZ)V", at = @At("RETURN"))
    void renderTextEnd(BlockPos pos, SignText text, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int textLineHeight, int maxTextWidth, boolean front, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.signText);
    }*/
}
