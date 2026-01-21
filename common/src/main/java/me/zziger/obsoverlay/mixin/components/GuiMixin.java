package me.zziger.obsoverlay.mixin.components;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.scores.Objective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @ModifyVariable(method = "displayScoreboardSidebar(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/scores/Objective;)V", at = @At("HEAD"), argsOnly = true)
    private GuiGraphics drawStartScoreboard(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.scoreboards, value);
    }

    @Inject(method = "displayScoreboardSidebar(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/scores/Objective;)V", at = @At("RETURN"))
    private void drawEndScoreboard(GuiGraphics guiGraphics, Objective objective, CallbackInfo ci) {
        // guiGraphics.bufferSource.endLastBatch();
        // OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.scoreboards);
    }

    @ModifyVariable(method = "renderOverlayMessage", at = @At("HEAD"), argsOnly = true)
    private GuiGraphics drawStartActionbar(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.actionbar, value);
    }

    @Inject(method = "renderOverlayMessage", at = @At("RETURN"))
    private void drawEndActionbar(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        // guiGraphics.bufferSource.endLastBatch();
        // OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.actionbar);
    }

    @ModifyVariable(method = "renderTitle", at = @At("HEAD"), argsOnly = true)
    private GuiGraphics drawStartTitleSubtitle(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.titleSubtitle, value);
    }

    @Inject(method = "renderTitle", at = @At("RETURN"))
    private void drawEndTitleSubtitle(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        // guiGraphics.bufferSource.endLastBatch();
        // OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.titleSubtitle);
    }

    @ModifyVariable(method = "renderEffects", at = @At("HEAD"), argsOnly = true)
    private GuiGraphics drawStartEffects(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.effects, value);
    }

    @Inject(method = "renderEffects", at = @At("RETURN"))
    private void drawEndEffects(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        // guiGraphics.bufferSource.endLastBatch();
        // OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.effects);
    }

    @ModifyVariable(method = "renderHotbarAndDecorations", at = @At("HEAD"), argsOnly = true)
    private GuiGraphics drawStartMainHud(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, value);
    }

    @Inject(method = "renderHotbarAndDecorations", at = @At("RETURN"))
    private void drawEndMainHud(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        // guiGraphics.bufferSource.endLastBatch();
        // // OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }
}
