package me.zziger.obsoverlay.mixin.components;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {
    @ModifyVariable(method = "extractRenderState", at = @At("HEAD"), argsOnly = true, index = 1)
    private GuiGraphicsExtractor drawStart(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.debugMenu, graphics);
    }
}
