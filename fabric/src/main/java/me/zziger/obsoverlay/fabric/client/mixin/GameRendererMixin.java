package me.zziger.obsoverlay.fabric.client.mixin;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OBSOverlayConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    public abstract Minecraft getMinecraft();

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderWithTooltip(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"))
    private GuiGraphics onScreenRender(GuiGraphics original) {
        Screen screen = this.getMinecraft().screen;
        if (screen != null && OBSOverlayConfig.isScreenOverlayed(screen)) {
            return OBSOverlay.getAPI().getOverlayGuiGraphics();
        }
        return original;
    }
}
