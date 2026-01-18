package me.zziger.obsoverlay;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import me.zziger.obsoverlay.component.IOverlayComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CompiledShaderProgram;
import net.minecraft.client.renderer.ShaderDefines;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.client.renderer.ShaderProgram;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class OverlayRenderer implements Closeable {
    static ShaderProgram SHADER = new ShaderProgram(ResourceLocation.fromNamespaceAndPath("obs_overlay", "core/overlay"), DefaultVertexFormat.POSITION_TEX, ShaderDefines.EMPTY);

    public boolean renderingHands = false;
    private int lastFramebuffer = 0;
    private RenderTarget depthBackupFramebuffer = null;
    private boolean framebufferOverridden = false;
    private final HashMap<OverlayFramebufferType, OverlayFramebuffer> framebuffers = new HashMap<>();

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

        depthBackupFramebuffer = new TextureTarget(client.getWindow().getWidth(), client.getWindow().getHeight(), true);
        depthBackupFramebuffer.setClearColor(0, 0, 0, 0);
        depthBackupFramebuffer.clear();

        RenderTarget depthFramebuffer = new TextureTarget(client.getWindow().getWidth(), client.getWindow().getHeight(), true);
        depthFramebuffer.setClearColor(0, 0, 0, 0);
        depthFramebuffer.clear();

        RenderTarget normalFramebuffer = new TextureTarget(client.getWindow().getWidth(), client.getWindow().getHeight(), true);
        normalFramebuffer.setClearColor(0, 0, 0, 0);
        normalFramebuffer.clear();

        framebuffers.put(OverlayFramebufferType.DEPTH, new OverlayFramebuffer(depthFramebuffer));
        framebuffers.put(OverlayFramebufferType.NORMAL, new OverlayFramebuffer(normalFramebuffer));
    }

    public boolean isFramebufferOverridden() {
        return framebufferOverridden;
    }

    private void markOverlayDirty(OverlayFramebufferType type) {
        OverlayFramebuffer framebuffer = framebuffers.getOrDefault(type, null);
        if (framebuffer == null) return;
        framebuffer.dirty = true;
    }

    public void backupDepth(boolean fullDepth) {
        Minecraft client = Minecraft.getInstance();

        int fb = GlStateManager._getInteger(GL_DRAW_FRAMEBUFFER_BINDING);
        int prevDepthTest = GlStateManager._getInteger(GL_DEPTH_TEST);
        int prevDepthMask = GlStateManager._getInteger(GL_DEPTH_WRITEMASK);
        int prevBlend = GlStateManager._getInteger(GL_BLEND);
        int prevCull = GlStateManager._getInteger(GL_CULL_FACE);

        GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, depthBackupFramebuffer.frameBufferId);

        renderQuad(false, true, fullDepth, client.getMainRenderTarget());

        GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fb);

        if (prevDepthTest == GL_TRUE) GlStateManager._enableDepthTest();
        else GlStateManager._disableDepthTest();
        GlStateManager._depthMask(prevDepthMask == GL_TRUE);
        if (prevBlend == GL_TRUE) GlStateManager._enableBlend();
        else GlStateManager._disableBlend();
        if (prevCull == GL_TRUE) GlStateManager._enableCull();
        else GlStateManager._disableCull();
    }

    private void backupFramebuffer() {
        if (framebuffers.isEmpty()) return;
        int boundFramebuffer = GlStateManager.getBoundFramebuffer();

        if (boundFramebuffer != framebuffers.get(OverlayFramebufferType.NORMAL).object.frameBufferId
                && boundFramebuffer != framebuffers.get(OverlayFramebufferType.DEPTH).object.frameBufferId
                && boundFramebuffer != 0) {
            lastFramebuffer = boundFramebuffer;
        }
    }

    private void restoreFramebuffer() {
        if (lastFramebuffer != 0)
            GlStateManager._glBindFramebuffer(GL_FRAMEBUFFER, lastFramebuffer);
    }

    public void beginDraw(OverlayFramebufferType type) {
        if (framebuffers.isEmpty()) return;
        backupFramebuffer();

        OverlayFramebuffer framebuffer = framebuffers.getOrDefault(type, null);
        if (framebuffer == null) return;

        GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebuffer.object.frameBufferId);
        GlStateManager._enableDepthTest();

        markOverlayDirty(type);
        framebufferOverridden = true;
    }

    public void beginEmptyDraw() {
        if (framebuffers.isEmpty()) return;
        backupFramebuffer();

        GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        framebufferOverridden = true;
    }

    public void beginDraw(IOverlayComponent component) {
        if (!component.isOverlayEnabled()) return;
        component.beforeBeginDraw();
        if (component.isHidden()) beginEmptyDraw();
        else beginDraw(component.getFramebufferType());
    }

    public void endDraw() {
        if (framebuffers.isEmpty()) return;
        restoreFramebuffer();
        framebufferOverridden = false;
    }

    public void endDraw(IOverlayComponent component) {
        if (!component.isOverlayEnabled()) return;
        component.beforeEndDraw();
        endDraw();
    }

    public void onResolutionChanged(Minecraft client) {
        int width = client.getWindow().getWidth();
        int height = client.getWindow().getHeight();

        framebuffers.forEach((type, framebuffer) -> {
            if (framebuffer.object != null) {
                framebuffer.object.resize(width, height);
            }
        });
        if (depthBackupFramebuffer != null) {
            depthBackupFramebuffer.resize(width, height);
        }
    }

    private void renderQuad(boolean writeDepth, boolean depthTest, boolean overrideDepth, RenderTarget framebuffer) {
        Minecraft client = Minecraft.getInstance();
        CompiledShaderProgram shaderProgram;

        try {
            shaderProgram = client.getShaderManager().getProgramForLoading(SHADER);
        } catch (ShaderManager.CompilationException e) {
            return;
        }

        if (depthTest) GlStateManager._enableDepthTest();
        else GlStateManager._disableDepthTest();
        GlStateManager._depthMask(true);
        GlStateManager._enableBlend();
        GlStateManager._disableCull();
        GlStateManager._blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager._viewport(0, 0, client.getWindow().getWidth(), client.getWindow().getHeight());

        if (writeDepth) {
            GlStateManager._glBindFramebuffer(GL_READ_FRAMEBUFFER, depthBackupFramebuffer.frameBufferId);
            GlStateManager._glBlitFrameBuffer(0, 0, client.getWindow().getWidth(), client.getWindow().getHeight(),
                    0, 0, client.getWindow().getWidth(), client.getWindow().getHeight(),
                    GL_DEPTH_BUFFER_BIT, GL_NEAREST);
            GlStateManager._glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);
        }

        shaderProgram.setDefaultUniforms(VertexFormat.Mode.QUADS, new Matrix4f().identity(), new Matrix4f().identity(), client.getWindow());
        shaderProgram.bindSampler("Sampler0", framebuffer.getColorTextureId());
        shaderProgram.bindSampler("Sampler1", framebuffer.getDepthTextureId());
        Objects.requireNonNull(shaderProgram.getUniform("OverrideDepth")).set(overrideDepth ? 1 : 0);
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
        framebuffers.forEach((type, framebuffer) -> {
            if (framebuffer.object != null) {
                framebuffer.object.setClearColor(0, 0, 0, 0);
                framebuffer.object.clear();
            }
        });

        if (depthBackupFramebuffer != null)
            depthBackupFramebuffer.clear();
    }

    private void renderFramebuffer(OverlayFramebufferType type) {
        OverlayFramebuffer framebuffer = framebuffers.getOrDefault(type, null);
        if (framebuffer == null || framebuffer.object == null || !framebuffer.dirty) return;


        framebuffer.dirty = false;
        GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        renderQuad(type == OverlayFramebufferType.DEPTH, type == OverlayFramebufferType.DEPTH, false, framebuffer.object);

    }

    public void renderFrame() {
        renderFramebuffer(OverlayFramebufferType.DEPTH);
        renderFramebuffer(OverlayFramebufferType.NORMAL);
    }
}
