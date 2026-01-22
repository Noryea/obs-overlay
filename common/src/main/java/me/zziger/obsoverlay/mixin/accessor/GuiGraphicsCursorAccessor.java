package me.zziger.obsoverlay.mixin.accessor;

import com.mojang.blaze3d.platform.cursor.CursorType;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiGraphics.class)
public interface GuiGraphicsCursorAccessor {
    @Accessor("pendingCursor")
    CursorType getPendingCursor();
}
