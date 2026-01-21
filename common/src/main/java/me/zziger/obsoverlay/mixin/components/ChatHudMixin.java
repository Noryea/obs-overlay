package me.zziger.obsoverlay.mixin.components;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class ChatHudMixin {
    @Inject(method = "render(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IIZ)V", at = @At("HEAD"))
    private void drawStart(ChatComponent.ChatGraphicsAccess chatGraphicsAccess, int height, int tickCount, boolean focused, CallbackInfo ci) {
        OBSOverlay.getAPI().beginDraw(AllDefaultOverlayComponents.chat);
    }

    @Inject(method = "render(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IIZ)V", at = @At("RETURN"))
    private void drawEnd(ChatComponent.ChatGraphicsAccess chatGraphicsAccess, int height, int tickCount, boolean focused, CallbackInfo ci) {
        // guiGraphics.bufferSource.endLastBatch();
        OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.chat);
    }
}
