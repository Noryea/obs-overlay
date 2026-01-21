package me.zziger.obsoverlay;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import me.zziger.obsoverlay.component.IOverlayComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CompiledShaderProgram;
import net.minecraft.client.renderer.ShaderDefines;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.client.renderer.ShaderProgram;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.io.Closeable;

import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;

public class OverlayRenderer implements Closeable {
    static ShaderProgram SHADER = new ShaderProgram(ResourceLocation.fromNamespaceAndPath("obs_overlay", "core/overlay"), DefaultVertexFormat.POSITION_TEX, ShaderDefines.EMPTY);

    private int lastFramebuffer = 0;
    private boolean framebufferOverridden = false;
    private OverlayFramebuffer overlayFramebuffer;

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
        RenderTarget simpleFramebuffer = new TextureTarget(client.getWindow().getWidth(), client.getWindow().getHeight(), true);
        simpleFramebuffer.setClearColor(0, 0, 0, 0);
        simpleFramebuffer.clear();
        this.overlayFramebuffer = new OverlayFramebuffer(simpleFramebuffer);
    }

    public boolean isFramebufferOverridden() {
        return framebufferOverridden;
    }

    private void markOverlayDirty() {
        if (overlayFramebuffer == null) return;
        overlayFramebuffer.dirty = true;
    }

    private void backupFramebuffer() {
        int boundFramebuffer = GlStateManager.getBoundFramebuffer();
        if (boundFramebuffer != overlayFramebuffer.object.frameBufferId && boundFramebuffer != 0) {
            lastFramebuffer = boundFramebuffer;
        }
    }

    private void restoreFramebuffer() {
        if (lastFramebuffer != 0)
            GlStateManager._glBindFramebuffer(GL_FRAMEBUFFER, lastFramebuffer);
    }

    public void beginDraw() {
        if (overlayFramebuffer == null) return;
        backupFramebuffer();
        GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, overlayFramebuffer.object.frameBufferId);
        markOverlayDirty();
        framebufferOverridden = true;
    }

    public void beginEmptyDraw() {
        if (overlayFramebuffer == null) return;
        backupFramebuffer();

        GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        framebufferOverridden = true;
    }

    public void beginDraw(IOverlayComponent component) {
        if (!component.isOverlayEnabled()) return;
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ONE
        );
        if (component.isHidden()) beginEmptyDraw();
        else beginDraw();
    }

    public void endDraw() {
        if (overlayFramebuffer == null) return;
        restoreFramebuffer();
        framebufferOverridden = false;
    }

    public void endDraw(IOverlayComponent component) {
        if (!component.isOverlayEnabled()) return;
        RenderSystem.defaultBlendFunc();
        endDraw();
    }

    public void onResolutionChanged(Minecraft client) {
        if (overlayFramebuffer == null) return;
        overlayFramebuffer.object.resize(
                client.getWindow().getWidth(),
                client.getWindow().getHeight()
        );
    }

    private static void renderQuad(RenderTarget framebuffer) {
        Minecraft client = Minecraft.getInstance();
        CompiledShaderProgram shaderProgram;

        try {
            shaderProgram = client.getShaderManager().getProgramForLoading(SHADER);
        } catch (ShaderManager.CompilationException e) {
            return;
        }

        GlStateManager._disableDepthTest();
        GlStateManager._enableBlend();
        GlStateManager._disableCull();
        GlStateManager._blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager._viewport(0, 0, client.getWindow().getWidth(), client.getWindow().getHeight());

        shaderProgram.setDefaultUniforms(VertexFormat.Mode.QUADS, new Matrix4f().identity(), new Matrix4f().identity(), client.getWindow());
        shaderProgram.bindSampler("Sampler0", framebuffer.getColorTextureId());
        shaderProgram.apply();

        BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.addVertex(-1.0f, -1.0f, 0.0F).setUv(0, 0);
        bufferBuilder.addVertex(1.0f, -1.0f, 0.0F).setUv(1, 0);
        bufferBuilder.addVertex(1.0f, 1.0f, 0.0F).setUv(1, 1);
        bufferBuilder.addVertex(-1.0f, 1.0f, 0.0F).setUv(0, 1);
        BufferUploader.draw(bufferBuilder.buildOrThrow());

        shaderProgram.clear();
    }

    public void beginFrame() {
        if (overlayFramebuffer == null) return;
        overlayFramebuffer.object.setClearColor(0, 0, 0, 0);
        overlayFramebuffer.object.clear();
    }

    public void renderFrame() {
        if (overlayFramebuffer == null || !overlayFramebuffer.dirty) return;
        overlayFramebuffer.dirty = false;
        GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        renderQuad(overlayFramebuffer.object);
    }
}
