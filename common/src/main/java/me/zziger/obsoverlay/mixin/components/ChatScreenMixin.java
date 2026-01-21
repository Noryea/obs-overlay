package me.zziger.obsoverlay.mixin.components;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @ModifyVariable(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Font;IIIZZ)V", shift = At.Shift.AFTER), argsOnly = true, index = 1)
    private GuiGraphics drawStart(GuiGraphics value) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.chatBar, value);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void drawEnd(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        // guiGraphics.bufferSource.endLastBatch();
        // OBSOverlay.getAPI().endDraw(AllDefaultOverlayComponents.chatBar);
    }
}
