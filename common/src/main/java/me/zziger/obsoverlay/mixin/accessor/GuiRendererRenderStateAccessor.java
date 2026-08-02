package me.zziger.obsoverlay.mixin.accessor;

import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiRenderer.class)
public interface GuiRendererRenderStateAccessor {
    @Accessor("renderState")
    GuiRenderState getRenderState();

    @Accessor("renderState")
    @Mutable
    void setRenderState(GuiRenderState state);
}
