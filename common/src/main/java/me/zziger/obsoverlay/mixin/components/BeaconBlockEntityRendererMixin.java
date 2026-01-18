package me.zziger.obsoverlay.mixin.components;

import com.mojang.blaze3d.vertex.PoseStack;
import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OverlayUtils;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeaconRenderer.class)
public class BeaconBlockEntityRendererMixin {
    @Inject(method = "renderBeaconBeam(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/resources/ResourceLocation;FFJIIIFF)V", at = @At("HEAD"))
    private static void renderPattern(PoseStack matrices, MultiBufferSource vertexConsumers, ResourceLocation textureId, float tickDelta, float heightScale, long worldTime, int yOffset, int maxY, int color, float innerRadius, float outerRadius, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.beaconBeam);
    }

    @Inject(method = "renderBeaconBeam(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/resources/ResourceLocation;FFJIIIFF)V", at = @At("RETURN"))
    private static void renderPatternEnd(PoseStack matrices, MultiBufferSource vertexConsumers, ResourceLocation textureId, float tickDelta, float heightScale, long worldTime, int yOffset, int maxY, int color, float innerRadius, float outerRadius, CallbackInfo ci) {
        OverlayUtils.forceDraw(vertexConsumers);
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.beaconBeam);
    }
}
