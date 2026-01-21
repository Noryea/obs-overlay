package me.zziger.obsoverlay.mixin.disabled;

import net.minecraft.client.renderer.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    /*@Inject(method = "renderNameTag(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"))
    protected void draw(EntityRenderState state, Component text, PoseStack matrices, MultiBufferSource vertexConsumers, int light, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().beginDraw(state.isDiscrete ? AllDefaultOverlayComponents.nameTagSneaking : AllDefaultOverlayComponents.nameTag);
    }

    @Inject(method = "renderNameTag(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("RETURN"))
    protected void drawEnd(EntityRenderState state, Component text, PoseStack matrices, MultiBufferSource vertexConsumers, int light, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().endDraw(state.isDiscrete ? AllDefaultOverlayComponents.nameTagSneaking : AllDefaultOverlayComponents.nameTag);
    }*/
}
