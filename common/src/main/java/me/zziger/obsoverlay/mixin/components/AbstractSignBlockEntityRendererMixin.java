package me.zziger.obsoverlay.mixin.components;

import com.mojang.blaze3d.vertex.PoseStack;
import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OverlayUtils;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.AbstractSignRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSignRenderer.class)
public class AbstractSignBlockEntityRendererMixin {
    @Inject(method = "renderSignText(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/SignText;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IIIZ)V", at = @At("HEAD"))
    void renderText(BlockPos pos, SignText text, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int textLineHeight, int maxTextWidth, boolean front, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.signText);
    }

    @Inject(method = "renderSignText(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/SignText;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IIIZ)V", at = @At("RETURN"))
    void renderTextEnd(BlockPos pos, SignText text, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int textLineHeight, int maxTextWidth, boolean front, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.signText);
    }
}
