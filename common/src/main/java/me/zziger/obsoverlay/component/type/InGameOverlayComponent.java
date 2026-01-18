package me.zziger.obsoverlay.component.type;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.zziger.obsoverlay.OverlayFramebufferType;
import net.minecraft.resources.ResourceLocation;

public class InGameOverlayComponent extends DefaultOverlayComponent {
    public InGameOverlayComponent(ResourceLocation id, boolean defaultOverlay, boolean canAutoHide) {
        super(id, defaultOverlay, canAutoHide);
    }

    @Override
    public void beforeBeginDraw() {
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
    }

    @Override
    public void beforeEndDraw() {
        RenderSystem.defaultBlendFunc();
    }

    @Override
    public OverlayFramebufferType getFramebufferType() {
        return OverlayFramebufferType.DEPTH;
    }
}
