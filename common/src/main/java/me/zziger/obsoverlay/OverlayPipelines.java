package me.zziger.obsoverlay;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public final class OverlayPipelines {
    public static final RenderPipeline OVERLAY_COMPOSITE = register(
            RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
                    .withVertexShader(Identifier.fromNamespaceAndPath("obs_overlay", "core/overlay"))
                    .withFragmentShader(Identifier.fromNamespaceAndPath("obs_overlay", "core/overlay"))
                    .withSampler("Sampler0")
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .withDepthWrite(false)
                    .withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS)
                    .withLocation(Identifier.fromNamespaceAndPath("obs_overlay", "pipeline/overlay_composite"))
                    .build()
    );

    private static RenderPipeline register(RenderPipeline pipeline) {
        RenderPipelines.PIPELINES_BY_LOCATION.put(pipeline.getLocation(), pipeline);
        return pipeline;
    }
}