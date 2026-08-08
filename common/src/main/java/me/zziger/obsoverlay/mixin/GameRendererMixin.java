package me.zziger.obsoverlay.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.platform.cursor.CursorType;
import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OBSOverlayConfig;
import me.zziger.obsoverlay.OverlayRenderer;
import me.zziger.obsoverlay.mixin.accessor.GuiGraphicsAccessor;
import me.zziger.obsoverlay.mixin.accessor.GuiRendererRenderStateAccessor;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
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
            try {
                GuiGraphics overlayGraphics = Objects.requireNonNull(OBSOverlay.getRenderer()).getGuiGraphics();
                overlayGraphics.blitSprite(
                        RenderPipelines.GUI_TEXTURED,
                        Identifier.withDefaultNamespace("icon/checkmark"),
                        0, 0, 16, 16
                );
            } catch (Exception ignored) {
            }
        }
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;render(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V"))
    private void obs_overlay$wrapGuiRendering(GuiRenderer instance, GpuBufferSlice fogBuffer, Operation<Void> original) {
        original.call(instance, fogBuffer);

        if (!OBSOverlay.getIsInitialized()) return;
        OverlayRenderer overlayRenderer = OBSOverlay.getRenderer();
        if (overlayRenderer == null) return;

        GuiRenderState overlayState = overlayRenderer.getOverlayGuiState();
        if (overlayState == null) return;

        GuiRendererRenderStateAccessor accessor = (GuiRendererRenderStateAccessor) instance;
        GuiRenderState originalState = accessor.getRenderState(); // to be restored for the original pass

        accessor.setRenderState(overlayState);
        overlayRenderer.beginDraw();
        instance.render(fogBuffer);
        overlayRenderer.endDraw();
        accessor.setRenderState(originalState);
    }

    @Shadow
    public abstract Minecraft getMinecraft();

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;applyCursor(Lcom/mojang/blaze3d/platform/Window;)V", shift = At.Shift.AFTER))
    private void fixCursor(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        if (!OBSOverlay.getIsInitialized()) return;

        GuiGraphics guiGraphics = OBSOverlay.getAPI().getOverlayGuiGraphics();
        if (guiGraphics != null && ((GuiGraphicsAccessor) guiGraphics).getPendingCursor() != CursorType.DEFAULT) {
            guiGraphics.applyCursor(this.getMinecraft().getWindow());
        }
    }
}
