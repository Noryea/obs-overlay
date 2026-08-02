package me.zziger.obsoverlay.fabric.client.mixin;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @ModifyVariable(method = "renderHotbarAndDecorations", at = @At("HEAD"), argsOnly = true)
    private GuiGraphics drawStartMainHud(GuiGraphics graphics) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.mainHud, graphics);
    }
}
