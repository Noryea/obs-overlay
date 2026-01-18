package me.zziger.obsoverlay.mixin.components;

import com.mojang.blaze3d.vertex.PoseStack;
import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OverlayUtils;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestRenderer.class)
public class ChestBlockEntityRendererMixin {
    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("HEAD"))
    void renderText(BlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.chest);
    }

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("RETURN"))
    void renderTextEnd(BlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.chest);
    }
}
