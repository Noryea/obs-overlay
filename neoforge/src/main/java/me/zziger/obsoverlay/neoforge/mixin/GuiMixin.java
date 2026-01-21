package me.zziger.obsoverlay.neoforge.mixin;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Gui.class)
public class GuiMixin {
    @ModifyVariable(method = "renderHotbar", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphics drawStartHotbar(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, value);
    }

    @ModifyVariable(method = "renderContextualInfoBarBackground", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphics drawStartContextualInfoBarBarBackground(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, value);
    }

    @ModifyVariable(method = "renderContextualInfoBar", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphics drawStartContextualInfoBar(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, value);
    }

    @ModifyVariable(method = "renderExperienceLevel", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphics drawStartExperienceBar(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, value);
    }

    @ModifyVariable(method = "renderHealthLevel", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphics drawStartPlayerHealth(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, value);
    }

    @ModifyVariable(method = "renderArmorLevel", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphics drawStartPlayerArmor(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, value);
    }

    @ModifyVariable(method = "renderFoodLevel", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphics drawStartPlayerFood(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, value);
    }

    @ModifyVariable(method = "renderVehicleHealth", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphics drawStartVehicleHealth(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, value);
    }

    @ModifyVariable(method = "renderAirLevel", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphics drawStartAirLevel(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, value);
    }

    @ModifyVariable(method = "maybeRenderSelectedItemName", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphics drawStartSelectedItemName(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, value);
    }

    @ModifyVariable(method = "maybeRenderSpectatorTooltip", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphics drawStartSpectatorTooltip(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, value);
    }

}
