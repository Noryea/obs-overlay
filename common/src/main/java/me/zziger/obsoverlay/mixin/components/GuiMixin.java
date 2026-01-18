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
    @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V", at = @At("RETURN"))
    private void drawStartInGameHud(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().backupDepth(false);
    }

    @Inject(method = "displayScoreboardSidebar(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/scores/Objective;)V", at = @At("HEAD"))
    private void drawStartScoreboard(GuiGraphics drawContext, Objective objective, CallbackInfo ci) {
        drawContext.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.scoreboards);
    }

    @Inject(method = "displayScoreboardSidebar(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/scores/Objective;)V", at = @At("RETURN"))
    private void drawEndScoreboard(GuiGraphics drawContext, Objective objective, CallbackInfo ci) {
        drawContext.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.scoreboards);
    }

    @Inject(method = "renderOverlayMessage", at = @At("HEAD"))
    private void drawStartActionbar(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.actionbar);
    }

    @Inject(method = "renderOverlayMessage", at = @At("RETURN"))
    private void drawEndActionbar(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.actionbar);
    }

    @Inject(method = "renderTitle", at = @At("HEAD"))
    private void drawStartTitleSubtitle(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.titleSubtitle);
    }

    @Inject(method = "renderTitle", at = @At("RETURN"))
    private void drawEndTitleSubtitle(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.titleSubtitle);
    }

    @Inject(method = "renderExperienceLevel", at = @At("HEAD"))
    private void drawStartExperienceLevel(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderExperienceLevel", at = @At("RETURN"))
    private void drawEndExperienceLevel(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderEffects", at = @At("HEAD"))
    private void drawStartEffects(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.effects);
    }

    @Inject(method = "renderEffects", at = @At("RETURN"))
    private void drawEndEffects(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.effects);
    }

    @Inject(method = "renderHotbarAndDecorations", at = @At("HEAD"))
    private void drawStartMainHud(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderHotbarAndDecorations", at = @At("RETURN"))
    private void drawEndMainHud(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }
}
