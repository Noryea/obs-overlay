package me.zziger.obsoverlay.fabric.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.zziger.obsoverlay.ScreenOverlayRenderer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    public abstract Minecraft getMinecraft();

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderWithTooltipAndSubtitles(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", shift = At.Shift.BEFORE))
    private void beforeScreenRender(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        Screen screen = this.getMinecraft().screen;
        if (screen != null) ScreenOverlayRenderer.beforeScreenRender(screen);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderWithTooltipAndSubtitles(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", shift = At.Shift.AFTER))
    private void afterScreenRender(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci, @Local GuiGraphics guiGraphics) {
        Screen screen = this.getMinecraft().screen;
        if (screen != null) ScreenOverlayRenderer.afterScreenRender(screen, guiGraphics);
    }
}
