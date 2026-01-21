package me.zziger.obsoverlay.mixin.components;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugScreenOverlay.class)
public class DebugHudMixin {
    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true, index = 1)
    private GuiGraphics drawStart(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.debugMenu, value);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void drawEnd(GuiGraphics guiGraphics, CallbackInfo ci) {
        // guiGraphics.bufferSource.endLastBatch();
        // OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.debugMenu);
    }
}
