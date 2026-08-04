package me.zziger.obsoverlay;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import me.zziger.obsoverlay.component.IOverlayComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiRenderState;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.util.OptionalInt;

public class OverlayRenderer implements Closeable {
    private boolean framebufferOverridden = false;
    private OverlayFramebuffer overlayFramebuffer;

    private final GuiRenderState overlayGuiState = new GuiRenderState();
    private GuiGraphics overlayGuiGraphics;

    OverlayRenderer() {
        OverlayHook.init();
        OverlayHook.subscribe(this::renderFrame);
        initializeFramebuffers();
    }

    public void close() {
        OverlayHook.unsubscribe(this::renderFrame);
    }

    private void initializeFramebuffers() {
        Minecraft client = Minecraft.getInstance();
        RenderTarget simpleFramebuffer = new TextureTarget("Overlay Target", client.getWindow().getWidth(), client.getWindow().getHeight(), true);
        clearFramebuffer(simpleFramebuffer);
        this.overlayFramebuffer = new OverlayFramebuffer(simpleFramebuffer);
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
            encoder.clearColorAndDepthTextures(colorTexture, 0, target.getDepthTexture(), 1.0);
        } else {
            encoder.clearColorTexture(colorTexture,0);
        }
    }

    public GuiRenderState getOverlayGuiState() {
        return this.overlayGuiState;
    }

    public RenderTarget getGuiRenderTarget() {
        if (this.framebufferOverridden && overlayFramebuffer != null) return overlayFramebuffer.object;
        else return Minecraft.getInstance().getMainRenderTarget();
    }

    public GuiGraphics getGuiGraphics() {
        return this.overlayGuiGraphics;
    }

    public @NotNull GuiGraphics getGuiGraphics(IOverlayComponent component, GuiGraphics original) {
        if (!component.isOverlayEnabled()) return original;
        if (component.isHidden()) return DummyGuiGraphics.INSTANCE;
        GuiGraphics guiGraphics = getGuiGraphics();
        return guiGraphics != null ? guiGraphics : original;
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
    }

    private static void renderQuad(RenderTarget framebuffer) {
        if (framebuffer.getColorTexture() == null) return;

        Minecraft minecraft = Minecraft.getInstance();
        RenderTarget mainTarget = minecraft.getMainRenderTarget();
        if (mainTarget.getColorTextureView() == null) return;

        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                () -> "Overlay Composite",
                mainTarget.getColorTextureView(),
                OptionalInt.empty()
        )) {
//            renderPass.setPipeline(RenderPipelines.ENTITY_OUTLINE_BLIT);
            renderPass.setPipeline(OverlayPipelines.OVERLAY_COMPOSITE);
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.bindTexture(
                    "InSampler",
                    framebuffer.getColorTextureView(),
                    RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST)
            );
            renderPass.draw(0, 3);
        }
        // Update swap chain presentation to display the composited frame
        RenderSystem.getDevice().createCommandEncoder().presentTexture(mainTarget.getColorTextureView());
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
        this.overlayGuiGraphics = new GuiGraphics(minecraft, overlayGuiState, mouseX, mouseY);
    }

    public void renderFrame() {
        if (overlayFramebuffer == null || !overlayFramebuffer.dirty) return;
        overlayFramebuffer.dirty = false;
        renderQuad(overlayFramebuffer.object);
    }
}
