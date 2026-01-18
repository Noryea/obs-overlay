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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTabOverlay.class)
public class PlayerListHudMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void drawStart(GuiGraphics context, int scaledWindowWidth, Scoreboard scoreboard, Objective objective, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.playerList);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void drawEnd(GuiGraphics context, int scaledWindowWidth, Scoreboard scoreboard, Objective objective, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.playerList);
    }
}
