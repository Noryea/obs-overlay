package me.zziger.obsoverlay.neoforge.mixin;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(method = "renderHotbar", at = @At(value = "HEAD"))
    private void drawStartHotbar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderHotbar", at = @At(value = "RETURN"))
    private void drawEndHotbar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        // arg.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderContextualInfoBarBackground", at = @At(value = "HEAD"))
    private void drawStartContextualInfoBarBarBackground(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderContextualInfoBarBackground", at = @At(value = "RETURN"))
    private void drawEndContextualInfoBarBarBackground(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        // arg.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderContextualInfoBar", at = @At(value = "HEAD"))
    private void drawStartContextualInfoBar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderContextualInfoBar", at = @At(value = "RETURN"))
    private void drawEndContextualInfoBar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        // arg.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderExperienceLevel", at = @At(value = "HEAD"))
    private void drawStartExperienceBar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderExperienceLevel", at = @At(value = "RETURN"))
    private void drawEndExperienceBar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        // arg.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderHealthLevel", at = @At(value = "HEAD"))
    private void drawStartPlayerHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderHealthLevel", at = @At(value = "RETURN"))
    private void drawEndPlayerHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        // arg.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderArmorLevel", at = @At(value = "HEAD"))
    private void drawStartPlayerArmor(GuiGraphics guiGraphics, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderArmorLevel", at = @At(value = "RETURN"))
    private void drawEndPlayerArmor(GuiGraphics guiGraphics, CallbackInfo ci) {
        // arg.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderFoodLevel", at = @At(value = "HEAD"))
    private void drawStartPlayerFood(GuiGraphics guiGraphics, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderFoodLevel", at = @At(value = "RETURN"))
    private void drawEndPlayerFood(GuiGraphics guiGraphics, CallbackInfo ci) {
        // arg.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderVehicleHealth", at = @At(value = "HEAD"))
    private void drawStartVehicleHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderVehicleHealth", at = @At(value = "RETURN"))
    private void drawEndVehicleHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        // arg.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderAirLevel", at = @At(value = "HEAD"))
    private void drawStartAirLevel(GuiGraphics guiGraphics, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderAirLevel", at = @At(value = "RETURN"))
    private void drawEndAirLevel(GuiGraphics guiGraphics, CallbackInfo ci) {
        // arg.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderSelectedItemName", at = @At(value = "HEAD"))
    private void drawStartSelectedItemName(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderSelectedItemName", at = @At(value = "RETURN"))
    private void drawEndSelectedItemName(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        // arg.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderSpectatorTooltip", at = @At(value = "HEAD"))
    private void drawStartSpectatorTooltip(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderSpectatorTooltip", at = @At(value = "RETURN"))
    private void drawEndSpectatorTooltip(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        // arg.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

}
