package me.zziger.obsoverlay.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OBSOverlayConfig;
import me.zziger.obsoverlay.OverlayRenderer;
import me.zziger.obsoverlay.mixin.accessor.GuiRendererAccessor;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    /*@Inject(method = "renderItemInHand(Lnet/minecraft/client/Camera;FLorg/joml/Matrix4f;)V", at = @At(value = "HEAD"))
    private void renderHand(Camera camera, float tickDelta, Matrix4f matrix4f, CallbackInfo ci) {
        OverlayRenderer renderer = OBSOverlay.getRenderer();
        if (renderer != null) renderer.renderingHands = true;
        OBSOverlay.getAPI().backupDepth(true);
    }

    @Inject(method = "renderItemInHand(Lnet/minecraft/client/Camera;FLorg/joml/Matrix4f;)V", at = @At(value = "RETURN"))
    private void renderHandEnd(Camera camera, float tickDelta, Matrix4f matrix4f, CallbackInfo ci) {
        OverlayRenderer renderer = OBSOverlay.getRenderer();
        if (renderer != null) renderer.renderingHands = false;
        OBSOverlay.getAPI().backupDepth(true);
    }*/

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;render(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V", shift = At.Shift.BEFORE))
    private void renderTestIcon(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        if (OBSOverlayConfig.get().showTestIcon && OBSOverlay.getIsInitialized()) {
            GuiGraphics overlayGraphics = Objects.requireNonNull(OBSOverlay.getRenderer()).getGuiGraphics();
            if (overlayGraphics != null) {
                try {
                    overlayGraphics.blitSprite(
                            RenderPipelines.GUI_TEXTURED,
                            Identifier.withDefaultNamespace("icon/checkmark"),
                            0, 0, 16, 16
                    );
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Inject(method = "render", at = @At(value = "NEW", target = "(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/render/state/GuiRenderState;II)Lnet/minecraft/client/gui/GuiGraphics;"))
    private void beginGuiExtraction(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        if (OBSOverlay.getIsInitialized()) {
            OBSOverlay.getAPI().resetGuiExtraction();
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;render(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V"))
    private void redirectGuiRendering(GuiRenderer instance, GpuBufferSlice fogBuffer) {
        if (OBSOverlay.getIsInitialized()) {
            OverlayRenderer overlayRenderer = OBSOverlay.getRenderer();
            assert overlayRenderer != null;

            overlayRenderer.beginDraw();

            var accessor = (GuiRendererAccessor) instance;
            var customRenderer = new GuiRenderer(
                    overlayRenderer.overlayGuiRenderState,
                    accessor.getBufferSource(),
                    accessor.getSubmitNodeCollector(),
                    accessor.getFeatureRenderDispatcher(),
                    accessor.getPictureInPictureRenderers().values().stream().toList()
            );
            customRenderer.render(fogBuffer);

            overlayRenderer.endDraw();
        }

        instance.render(fogBuffer);
    }
}
