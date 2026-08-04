package me.zziger.obsoverlay.mixin.components;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.component.AllDefaultOverlayComponents;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Gui.class)
public class GuiMixin {
    @ModifyVariable(method = "extractScoreboardSidebar", at = @At("HEAD"), argsOnly = true, index = 1)
    private GuiGraphicsExtractor drawStartScoreboard(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.scoreboards, graphics);
    }

    @ModifyVariable(method = "extractOverlayMessage", at = @At("HEAD"), argsOnly = true, index = 1)
    private GuiGraphicsExtractor drawStartActionbar(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.actionbar, graphics);
    }

    @ModifyVariable(method = "extractTitle", at = @At("HEAD"), argsOnly = true, index = 1)
    private GuiGraphicsExtractor drawStartTitleSubtitle(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.titleSubtitle, graphics);
    }

    @ModifyVariable(method = "extractEffects", at = @At("HEAD"), argsOnly = true, index = 1)
    private GuiGraphicsExtractor drawStartEffects(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.effects, graphics);
    }

    @ModifyVariable(method = "extractChat", at = @At("HEAD"), argsOnly = true, index = 1)
    private GuiGraphicsExtractor drawStartChat(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.chat, graphics);
    }

    @ModifyVariable(method = "extractTabList", at = @At("HEAD"), argsOnly = true, index = 1)
    private GuiGraphicsExtractor drawStartPlayerList(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.playerList, graphics);
    }

    @ModifyVariable(method = "extractSubtitleOverlay", at = @At("HEAD"), argsOnly = true, index = 1)
    private GuiGraphicsExtractor drawStartSubtitles(GuiGraphicsExtractor graphics) {
        return OBSOverlay.getAPI().getGuiGraphicsExtractor(AllDefaultOverlayComponents.subtitles, graphics);
    }
}
