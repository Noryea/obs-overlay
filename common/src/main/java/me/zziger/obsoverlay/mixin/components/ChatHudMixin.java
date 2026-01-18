package me.zziger.obsoverlay.mixin.components;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class ChatHudMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void drawStart(GuiGraphics context, int currentTick, int mouseX, int mouseY, boolean focused, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.chat);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void drawEnd(GuiGraphics context, int currentTick, int mouseX, int mouseY, boolean focused, CallbackInfo ci) {
        context.flush();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.chat);
    }
}
