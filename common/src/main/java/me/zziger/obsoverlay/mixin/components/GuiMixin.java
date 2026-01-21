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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @Inject(method = "displayScoreboardSidebar(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/scores/Objective;)V", at = @At("HEAD"))
    private void drawStartScoreboard(GuiGraphics guiGraphics, Objective objective, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.scoreboards);
    }

    @Inject(method = "displayScoreboardSidebar(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/scores/Objective;)V", at = @At("RETURN"))
    private void drawEndScoreboard(GuiGraphics guiGraphics, Objective objective, CallbackInfo ci) {
        guiGraphics.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.scoreboards);
    }

    @Inject(method = "renderOverlayMessage", at = @At("HEAD"))
    private void drawStartActionbar(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.actionbar);
    }

    @Inject(method = "renderOverlayMessage", at = @At("RETURN"))
    private void drawEndActionbar(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        guiGraphics.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.actionbar);
    }

    @Inject(method = "renderTitle", at = @At("HEAD"))
    private void drawStartTitleSubtitle(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.titleSubtitle);
    }

    @Inject(method = "renderTitle", at = @At("RETURN"))
    private void drawEndTitleSubtitle(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        guiGraphics.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.titleSubtitle);
    }

    @Inject(method = "renderExperienceLevel", at = @At("HEAD"))
    private void drawStartExperienceLevel(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderExperienceLevel", at = @At("RETURN"))
    private void drawEndExperienceLevel(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        guiGraphics.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderEffects", at = @At("HEAD"))
    private void drawStartEffects(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.effects);
    }

    @Inject(method = "renderEffects", at = @At("RETURN"))
    private void drawEndEffects(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        guiGraphics.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.effects);
    }

    @Inject(method = "renderHotbarAndDecorations", at = @At("HEAD"))
    private void drawStartMainHud(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderHotbarAndDecorations", at = @At("RETURN"))
    private void drawEndMainHud(GuiGraphics guiGraphics, DeltaTracker tickCounter, CallbackInfo ci) {
        guiGraphics.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }
}
