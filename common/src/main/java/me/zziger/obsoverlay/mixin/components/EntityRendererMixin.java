package me.zziger.obsoverlay.mixin.components;

import com.mojang.blaze3d.vertex.PoseStack;
import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OverlayUtils;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @Inject(method = "renderNameTag(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"))
    protected void draw(EntityRenderState state, Component text, PoseStack matrices, MultiBufferSource vertexConsumers, int light, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().beginDraw(state.isDiscrete ? AllDefaultOverlayComponents.nameTagSneaking : AllDefaultOverlayComponents.nameTag);
    }

    @Inject(method = "renderNameTag(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("RETURN"))
    protected void drawEnd(EntityRenderState state, Component text, PoseStack matrices, MultiBufferSource vertexConsumers, int light, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().endDraw(state.isDiscrete ? AllDefaultOverlayComponents.nameTagSneaking : AllDefaultOverlayComponents.nameTag);
    }
}
