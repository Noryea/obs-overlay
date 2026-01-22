package me.zziger.obsoverlay;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;

public final class OverlayPipelines {
    public static final RenderPipeline OVERLAY_COMPOSITE = register(
            RenderPipeline.builder()
                    .withLocation(ResourceLocation.fromNamespaceAndPath("obs_overlay", "pipeline/overlay_composite"))
                    .withVertexShader("core/blit_screen")
                    .withFragmentShader(ResourceLocation.fromNamespaceAndPath("obs_overlay", "core/overlay"))
                    .withSampler("InSampler")
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .withDepthWrite(false)
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                    .withColorWrite(true, false)
                    .withCull(false)
                    .withVertexFormat(DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS)
                    .build()
    );

    private static RenderPipeline register(RenderPipeline pipeline) {
        RenderPipelines.PIPELINES_BY_LOCATION.put(pipeline.getLocation(), pipeline);
        return pipeline;
    }
}