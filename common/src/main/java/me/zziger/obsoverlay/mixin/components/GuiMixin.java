package me.zziger.obsoverlay.mixin.components;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Gui.class)
public class GuiMixin {

    @ModifyVariable(method = "displayScoreboardSidebar(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/scores/Objective;)V", at = @At("HEAD"), argsOnly = true)
    private GuiGraphics drawStartScoreboard(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.scoreboards, value);
    }

    @ModifyVariable(method = "renderOverlayMessage", at = @At("HEAD"), argsOnly = true)
    private GuiGraphics drawStartActionbar(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.actionbar, value);
    }

    @ModifyVariable(method = "renderTitle", at = @At("HEAD"), argsOnly = true)
    private GuiGraphics drawStartTitleSubtitle(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.titleSubtitle, value);
    }

    @ModifyVariable(method = "renderEffects", at = @At("HEAD"), argsOnly = true)
    private GuiGraphics drawStartEffects(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.effects, value);
    }

    @ModifyVariable(method = "renderHotbarAndDecorations", at = @At("HEAD"), argsOnly = true)
    private GuiGraphics drawStartMainHud(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, value);
    }
}
