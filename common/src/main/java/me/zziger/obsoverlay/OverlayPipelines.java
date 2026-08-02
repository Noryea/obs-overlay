package me.zziger.obsoverlay;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public final class OverlayPipelines {
    public static final RenderPipeline OVERLAY_COMPOSITE = RenderPipelines.register(
            RenderPipeline.builder()
                    .withLocation(Identifier.fromNamespaceAndPath("obs_overlay", "pipeline/overlay_composite"))
                    .withVertexShader("core/screenquad")
                    .withFragmentShader(Identifier.fromNamespaceAndPath("obs_overlay", "core/overlay"))
                    .withSampler("InSampler")
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .withDepthWrite(false)
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                    .withColorWrite(true, false)
                    .withCull(false)
                    .withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES)
                    .build()
    );
}