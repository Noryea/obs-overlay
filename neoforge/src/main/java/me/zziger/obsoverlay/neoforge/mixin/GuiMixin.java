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
    private void drawStartHotbar(GuiGraphics arg, DeltaTracker arg2, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderHotbar", at = @At(value = "RETURN"))
    private void drawEndHotbar(GuiGraphics arg, DeltaTracker arg2, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderJumpMeter", at = @At(value = "HEAD"))
    private void drawStartJumpMeter(GuiGraphics arg, DeltaTracker arg2, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderJumpMeter", at = @At(value = "RETURN"))
    private void drawEndJumpMeter(GuiGraphics arg, DeltaTracker arg2, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderExperienceBar", at = @At(value = "HEAD"))
    private void drawStartExperienceBar(GuiGraphics arg, DeltaTracker arg2, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderExperienceBar", at = @At(value = "RETURN"))
    private void drawEndExperienceBar(GuiGraphics arg, DeltaTracker arg2, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderHealthLevel", at = @At(value = "HEAD"))
    private void drawStartPlayerHealth(GuiGraphics arg, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderHealthLevel", at = @At(value = "RETURN"))
    private void drawEndPlayerHealth(GuiGraphics arg, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderArmorLevel", at = @At(value = "HEAD"))
    private void drawStartPlayerArmor(GuiGraphics arg, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderArmorLevel", at = @At(value = "RETURN"))
    private void drawEndPlayerArmor(GuiGraphics arg, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderFoodLevel", at = @At(value = "HEAD"))
    private void drawStartPlayerFood(GuiGraphics arg, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "renderFoodLevel", at = @At(value = "RETURN"))
    private void drawEndPlayerFood(GuiGraphics arg, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderVehicleHealth", at = @At(value = "HEAD"))
    private void drawStartVehicleHealth(GuiGraphics arg, DeltaTracker arg2, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderVehicleHealth", at = @At(value = "RETURN"))
    private void drawEndVehicleHealth(GuiGraphics arg, DeltaTracker arg2, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderSelectedItemName", at = @At(value = "HEAD"))
    private void drawStartSelectedItemName(GuiGraphics arg, DeltaTracker arg2, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderSelectedItemName", at = @At(value = "RETURN"))
    private void drawEndSelectedItemName(GuiGraphics arg, DeltaTracker arg2, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderSpectatorTooltip", at = @At(value = "HEAD"))
    private void drawStartSpectatorTooltip(GuiGraphics arg, DeltaTracker arg2, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.mainHud);
    }

    @Inject(method = "maybeRenderSpectatorTooltip", at = @At(value = "RETURN"))
    private void drawEndSpectatorTooltip(GuiGraphics arg, DeltaTracker arg2, CallbackInfo ci) {
        arg.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.mainHud);
    }

}
