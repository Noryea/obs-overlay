package me.zziger.obsoverlay.mixin.components;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SubtitleOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SubtitleOverlay.class)
public class SubtitlesHudMixin {
    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true)
    private GuiGraphics drawStart(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.subtitles, value);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void drawEnd(GuiGraphics guiGraphics, CallbackInfo ci) {
        // guiGraphics.bufferSource.endLastBatch();
        // OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.subtitles);
    }
}
