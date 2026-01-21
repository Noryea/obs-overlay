package me.zziger.obsoverlay.mixin.components;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SubtitleOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SubtitleOverlay.class)
public class SubtitlesHudMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void drawStart(GuiGraphics guiGraphics, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.subtitles);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void drawEnd(GuiGraphics guiGraphics, CallbackInfo ci) {
        // guiGraphics.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.subtitles);
    }
}
