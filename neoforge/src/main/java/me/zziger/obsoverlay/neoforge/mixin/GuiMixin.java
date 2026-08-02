package me.zziger.obsoverlay.neoforge.mixin;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Gui.class)
public class GuiMixin {
    @ModifyVariable(method = "extractHotbar", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphicsExtractor drawStartHotbar(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.mainHud, graphics);
    }

    @ModifyVariable(method = "extractContextualInfoBarBackground", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphicsExtractor drawStartContextualInfoBarBarBackground(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.mainHud, graphics);
    }

    @ModifyVariable(method = "extractContextualInfoBar", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphicsExtractor drawStartContextualInfoBar(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.mainHud, graphics);
    }

    @ModifyVariable(method = "extractExperienceLevel", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphicsExtractor drawStartExperienceBar(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.mainHud, graphics);
    }

    @ModifyVariable(method = "extractHealthLevel", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphicsExtractor drawStartPlayerHealth(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.mainHud, graphics);
    }

    @ModifyVariable(method = "extractArmorLevel", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphicsExtractor drawStartPlayerArmor(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.mainHud, graphics);
    }

    @ModifyVariable(method = "extractFoodLevel", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphicsExtractor drawStartPlayerFood(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.mainHud, graphics);
    }

    @ModifyVariable(method = "extractVehicleHealth", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphicsExtractor drawStartVehicleHealth(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.mainHud, graphics);
    }

    @ModifyVariable(method = "extractAirLevel", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphicsExtractor drawStartAirLevel(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.mainHud, graphics);
    }

    @ModifyVariable(method = "extractSelectedItemName(Lnet/minecraft/client/gui/GuiGraphicsExtractor;I)V", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphicsExtractor drawStartSelectedItemName(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.mainHud, graphics);
    }

    @ModifyVariable(method = "maybeExtractSpectatorTooltip", at = @At(value = "HEAD"), argsOnly = true)
    private GuiGraphicsExtractor drawStartSpectatorTooltip(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.mainHud, graphics);
    }

}
