package me.zziger.obsoverlay.mixin.components;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @ModifyVariable(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Font;IIIZZ)V", shift = At.Shift.AFTER), argsOnly = true, index = 1)
    private GuiGraphics drawStart(GuiGraphics guiGraphics) {
        return OBSOverlay.getAPI().getGuiGraphics(AllDefaultOverlayComponents.chatBar, guiGraphics);
    }
}
