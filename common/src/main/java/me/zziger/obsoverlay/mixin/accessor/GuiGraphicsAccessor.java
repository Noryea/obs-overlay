package me.zziger.obsoverlay.mixin.accessor;

import com.mojang.blaze3d.platform.cursor.CursorType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiGraphics.class)
public interface GuiGraphicsAccessor {
    @Accessor("pendingCursor")
    CursorType getPendingCursor();

    @Accessor("guiRenderState")
    GuiRenderState getGuiRenderState();
}
