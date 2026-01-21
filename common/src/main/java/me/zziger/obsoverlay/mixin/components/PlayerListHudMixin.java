package me.zziger.obsoverlay.mixin.components;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTabOverlay.class)
public class PlayerListHudMixin {
    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true)
    private GuiGraphics drawStart(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.playerList, value);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void drawEnd(GuiGraphics guiGraphics, int width, Scoreboard scoreboard, Objective objective, CallbackInfo ci) {
        // guiGraphics.bufferSource.endLastBatch();
        // OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.playerList);
    }
}
