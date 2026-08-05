package me.zziger.obsoverlay.fabric.client.mixin;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Hud.class)
public abstract class HudMixin {
    @ModifyVariable(method = "extractHotbarAndDecorations", at = @At("HEAD"), argsOnly = true)
    private GuiGraphicsExtractor drawStartMainHud(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.mainHud, graphics);
    }
}
