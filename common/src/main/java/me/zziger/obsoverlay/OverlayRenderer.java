package me.zziger.obsoverlay;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.opengl.GlDevice;
import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.vertex.*;
import me.zziger.obsoverlay.component.IOverlayComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiRenderState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;

public class OverlayRenderer implements Closeable {
    private GpuBuffer overlayBuffer;
    private boolean framebufferOverridden = false;
    private OverlayFramebuffer overlayFramebuffer;

    public final GuiRenderState overlayGuiRenderState = new GuiRenderState();
    private GuiGraphics overlayGuiGraphics;
    // private boolean overlayExtractionActive = false;

    OverlayRenderer() {
        OverlayHook.init();
        OverlayHook.subscribe(this::renderFrame);
        initializeFramebuffers();
        initializeQuadOverlayBuffer();
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

    private void initializeQuadOverlayBuffer() {
        BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.addVertex(-1.0f, -1.0f, 0.0F).setUv(0, 0);
        bufferBuilder.addVertex(1.0f, -1.0f, 0.0F).setUv(1, 0);
        bufferBuilder.addVertex(1.0f, 1.0f, 0.0F).setUv(1, 1);
        bufferBuilder.addVertex(-1.0f, 1.0f, 0.0F).setUv(0, 1);
        try (MeshData meshData = bufferBuilder.buildOrThrow()) {
            this.overlayBuffer = RenderSystem.getDevice().createBuffer(
                    () -> "OBS Overlay Composite Vertices",
                    GpuBuffer.USAGE_VERTEX,
                    meshData.vertexBuffer()
            );
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
            encoder.clearColorAndDepthTextures(colorTexture, 0, target.getDepthTexture(), 1.0);
        } else {
            encoder.clearColorTexture(colorTexture,0);
        }
    }

    private static Optional<Integer> getFboId(RenderTarget renderTarget) {
        if (renderTarget == null) return Optional.empty();
        GpuTexture colorTexture = renderTarget.getColorTexture();
        GpuTexture depthTexture = renderTarget.getDepthTexture();
        if (RenderSystem.getDevice() instanceof GlDevice glDevice && colorTexture instanceof GlTexture t) {
            int fbo = t.getFbo(glDevice.directStateAccess(), depthTexture);
            return Optional.of(fbo);
        }
        return Optional.empty();
    }

    public RenderTarget getGuiRenderTarget() {
        if (this.framebufferOverridden && overlayFramebuffer != null) return overlayFramebuffer.object;
        else return Minecraft.getInstance().getMainRenderTarget();
    }

    public @Nullable GuiGraphics getGuiGraphics() {
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

    // 使用 1.21.11 的 RenderPipeline API 替代旧的 ShaderProgram
    private void renderQuad(RenderTarget framebuffer) {
        Minecraft client = Minecraft.getInstance();

        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        RenderTarget mainTarget = client.getMainRenderTarget();
        try (RenderPass pass = encoder.createRenderPass(
            () -> "OBS Overlay Composite",
            mainTarget.getColorTextureView(),
            OptionalInt.empty(),
            mainTarget.getDepthTextureView(),
            OptionalDouble.empty()
        )) {
            pass.setPipeline(OverlayPipelines.OVERLAY_COMPOSITE);
            RenderSystem.bindDefaultUniforms(pass);

            pass.setVertexBuffer(0, this.overlayBuffer);
            GpuSampler sampler = RenderSystem.getSamplerCache().getSampler(
                    AddressMode.CLAMP_TO_EDGE,
                    AddressMode.CLAMP_TO_EDGE,
                    FilterMode.LINEAR,
                    FilterMode.NEAREST,
                    false
            );
            pass.bindTexture("Sampler0", framebuffer.getColorTextureView(), sampler);
            pass.draw(0, 4);
        }
    }

    public void beginFrame() {
        if (overlayFramebuffer == null) return;
        clearFramebuffer(overlayFramebuffer.object);
    }

    public void resetGuiExtraction() {
        if (overlayFramebuffer == null) return;

        Minecraft minecraft = Minecraft.getInstance();
        int mouseX = (int) minecraft.mouseHandler.getScaledXPos(minecraft.getWindow());
        int mouseY = (int) minecraft.mouseHandler.getScaledYPos(minecraft.getWindow());

        // 创建新的 overlay 渲染状态
        this.overlayGuiRenderState.reset();
        this.overlayGuiGraphics = new GuiGraphics(minecraft, overlayGuiRenderState, mouseX, mouseY);
    }

    public void renderFrame() {
        if (overlayFramebuffer == null || !overlayFramebuffer.dirty) return;
        overlayFramebuffer.dirty = false;
        GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        renderQuad(overlayFramebuffer.object);
    }
}
