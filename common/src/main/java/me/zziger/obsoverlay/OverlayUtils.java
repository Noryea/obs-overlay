package me.zziger.obsoverlay;

import me.zziger.obsoverlay.compat.ImmediatelyFastCompat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.raphimc.immediatelyfast.feature.core.BatchableBufferSource;

public class OverlayUtils {
    public static void forceDraw(MultiBufferSource consumer) {
        if (ImmediatelyFastCompat.hasImmediatelyFast() && consumer instanceof BatchableBufferSource batchable) batchable.endBatch();
        if (consumer instanceof MultiBufferSource.BufferSource immediate) immediate.endBatch();
    }

    public static void showToast(Component title, Component description) {
        Minecraft.getInstance().submit(() ->
                Minecraft.getInstance()
                        .getToastManager()
                        .addToast(new SystemToast(SystemToast.SystemToastId.LOW_DISK_SPACE, title, description))
        );
    }
}
