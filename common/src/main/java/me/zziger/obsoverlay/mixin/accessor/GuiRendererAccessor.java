package me.zziger.obsoverlay.mixin.accessor;

import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(GuiRenderer.class)
public interface GuiRendererAccessor {
    @Accessor("bufferSource")
    MultiBufferSource.BufferSource getBufferSource();

    @Accessor("submitNodeCollector")
    SubmitNodeCollector getSubmitNodeCollector();

    @Accessor("featureRenderDispatcher")
    FeatureRenderDispatcher getFeatureRenderDispatcher();

    @Accessor("pictureInPictureRenderers")
    Map<Class<? extends PictureInPictureRenderState>, PictureInPictureRenderer<?>> getPictureInPictureRenderers();
}
