package me.zziger.obsoverlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;

public class OverlayUtils {
    public static void showToast(Component title, Component description) {
        Minecraft.getInstance().submit(() ->
                Minecraft.getInstance().gui
                        .toastManager()
                        .addToast(new SystemToast(SystemToast.SystemToastId.LOW_DISK_SPACE, title, description))
        );
    }
}
