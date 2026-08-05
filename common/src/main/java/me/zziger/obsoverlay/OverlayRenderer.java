package me.zziger.obsoverlay;

import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.GpuSurface;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.SurfaceException;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import me.zziger.obsoverlay.component.IOverlayComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;

import java.io.Closeable;
import java.util.Optional;
import java.util.OptionalDouble;

public class OverlayRenderer implements Closeable {
    private boolean framebufferOverridden = false;
    private OverlayFramebuffer overlayFramebuffer;
    private GpuSurface overlaySurface;

    private final GuiRenderState overlayGuiState = new GuiRenderState();
    private GuiGraphicsExtractor overlayGuiGraphicsExtractor;

    OverlayRenderer() {
        OverlayHook.init();
        OverlayHook.subscribe(this::renderFrame);
        initializeFramebuffers();
        initializeSurface();
    }

    public void close() {
        OverlayHook.unsubscribe(this::renderFrame);
    }

    private void initializeFramebuffers() {
        Minecraft client = Minecraft.getInstance();
        RenderTarget simpleFramebuffer = new TextureTarget("Overlay Target", client.getWindow().getWidth(), client.getWindow().getHeight(), true, GpuFormat.RGBA8_UNORM);
        clearFramebuffer(simpleFramebuffer);
        this.overlayFramebuffer = new OverlayFramebuffer(simpleFramebuffer);
    }

    /**
     * Creates the overlay's own {@link GpuSurface}
     */
    private void initializeSurface() {
        Minecraft client = Minecraft.getInstance();
        this.overlaySurface = new GpuSurface(new OverlaySurfaceBackend());
        try {
            this.overlaySurface.configure(new GpuSurface.Configuration(
                    client.getWindow().getWidth(),
                    client.getWindow().getHeight(),
                    GpuSurface.PresentMode.IMMEDIATE
            ));
        } catch (SurfaceException e) {
            OBSOverlay.LOGGER.error("Failed to configure overlay surface", e);
        }
    }

    private void markOverlayDirty() {
        if (overlayFramebuffer == null) return;
        overlayFramebuffer.dirty = true;
    }

    private static void clearFramebuffer(RenderTarget target) {
        GpuTexture colorTexture = target.getColorTexture();
        if (colorTexture == null) return;

        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        if (target.useDepth && target.getDepthTexture() != null) {
            encoder.clearColorAndDepthTextures(colorTexture, new Vector4f(0, 0, 0, 0), target.getDepthTexture(), 1.0);
        } else {
            encoder.clearColorTexture(colorTexture, new Vector4f(0, 0, 0, 0));
        }
    }

    public GuiRenderState getOverlayGuiState() {
        return this.overlayGuiState;
    }

    public RenderTarget getGuiRenderTarget() {
        if (this.framebufferOverridden && overlayFramebuffer != null) return overlayFramebuffer.object;
        else return Minecraft.getInstance().gameRenderer.mainRenderTarget();
    }

    public GuiGraphicsExtractor getGuiGraphicsExtractor() {
        return this.overlayGuiGraphicsExtractor;
    }

    public @NotNull GuiGraphicsExtractor getGuiGraphicsExtractor(IOverlayComponent component, GuiGraphicsExtractor original) {
        if (!component.isOverlayEnabled()) return original;
        if (component.isHidden()) return DummyGuiGraphicsExtractor.INSTANCE;
        GuiGraphicsExtractor GuiGraphicsExtractor = getGuiGraphicsExtractor();
        return GuiGraphicsExtractor != null ? GuiGraphicsExtractor : original;
    }

    public void beginDraw() {
        if (overlayFramebuffer == null) return;
        framebufferOverridden = true;
        markOverlayDirty();
    }

    public void endDraw() {
        if (overlayFramebuffer == null) return;
        framebufferOverridden = false;
    }

    public void onResolutionChanged(Minecraft client) {
        if (overlayFramebuffer == null) return;
        overlayFramebuffer.object.resize(
                client.getWindow().getWidth(),
                client.getWindow().getHeight()
        );
        if (overlaySurface == null) return;
        try {
            overlaySurface.configure(new GpuSurface.Configuration(
                    client.getWindow().getWidth(),
                    client.getWindow().getHeight(),
                    GpuSurface.PresentMode.IMMEDIATE
            ));
        } catch (SurfaceException e) {
            OBSOverlay.LOGGER.warn("Failed to reconfigure overlay surface", e);
        }
    }

    private void renderFrame() {
        if (overlayFramebuffer == null || overlaySurface == null || !overlayFramebuffer.dirty) return;
        overlayFramebuffer.dirty = false;

        Minecraft minecraft = Minecraft.getInstance();
        RenderTarget mainTarget = minecraft.gameRenderer.mainRenderTarget();
        if (mainTarget.getColorTextureView() == null) return;

        renderQuad(overlayFramebuffer.object, mainTarget);

        try {
            overlaySurface.acquireNextTexture();
            overlaySurface.blitFromTexture(RenderSystem.getDevice().createCommandEncoder(), mainTarget.getColorTextureView());
            overlaySurface.present();
        } catch (SurfaceException e) {
            OBSOverlay.LOGGER.error("Failed to present overlay surface", e);
        }
    }

    private static void renderQuad(RenderTarget overlayTarget, RenderTarget mainTarget) {
        GpuTextureView overlayView = overlayTarget.getColorTextureView();
        GpuTextureView mainColorView = mainTarget.getColorTextureView();
        if (overlayView == null || mainColorView == null) return;

        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                () -> "Overlay Composite",
                mainColorView,
                Optional.empty(),
                null,
                OptionalDouble.empty()
        )) {
//            renderPass.setPipeline(RenderPipelines.ENTITY_OUTLINE_BLIT);
            renderPass.setPipeline(OverlayPipelines.OVERLAY_COMPOSITE);
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.bindTexture(
                    "InSampler",
                    overlayView,
                    RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST)
            );
            renderPass.draw(3, 1, 0, 0);
        }
    }

    public void beginFrame() {
        if (overlayFramebuffer == null) return;
        clearFramebuffer(overlayFramebuffer.object);
        resetGuiExtraction();
    }

    private void resetGuiExtraction() {
        Minecraft minecraft = Minecraft.getInstance();
        int mouseX = (int) minecraft.mouseHandler.getScaledXPos(minecraft.getWindow());
        int mouseY = (int) minecraft.mouseHandler.getScaledYPos(minecraft.getWindow());

        this.overlayGuiState.reset();
        this.overlayGuiGraphicsExtractor = new GuiGraphicsExtractor(minecraft, overlayGuiState, mouseX, mouseY);
    }
}
