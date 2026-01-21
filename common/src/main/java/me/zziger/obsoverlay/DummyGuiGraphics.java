package me.zziger.obsoverlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.object.banner.BannerFlagModel;
import net.minecraft.client.model.object.book.BookModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.profiling.ResultField;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class DummyGuiGraphics extends GuiGraphics {

    public static final DummyGuiGraphics INSTANCE = new DummyGuiGraphics();

    private DummyGuiGraphics() {
        super(Minecraft.getInstance(), new GuiRenderState(), 0, 0);
    }

    @Override
    public void nextStratum() {
    }


    @Override
    public void blurBeforeThisStratum() {
    }

    @Override
    public void drawString(@NotNull Font font, @NotNull FormattedCharSequence text, int x, int y, int color, boolean drawShadow) {
    }

    @Override
    public void submitMapRenderState(@NotNull MapRenderState renderState) {
    }

    public void submitEntityRenderState(@NotNull EntityRenderState renderState, float scale, @NotNull Vector3f translation, @NotNull Quaternionf rotation, @Nullable Quaternionf overrideCameraAngle, int x0, int y0, int x1, int y1) {
    }

    public void submitSkinRenderState(@NotNull PlayerModel playerModel, @NotNull Identifier texture, float rotationX, float rotationY, float pivotY, float x0, int y0, int x1, int y1, int scale) {
    }

    public void submitBookModelRenderState(@NotNull BookModel bookModel, @NotNull Identifier texture, float open, float flip, float x0, int y0, int x1, int y1, int scale) {
    }

    public void submitBannerPatternRenderState(@NotNull BannerFlagModel flag, @NotNull DyeColor baseColor, @NotNull BannerPatternLayers resultBannerPatterns, int x0, int y0, int x1, int y1) {
    }

    public void submitSignRenderState(Model.@NotNull Simple signModel, float scale, @NotNull WoodType woodType, int x0, int y0, int x1, int y1) {
    }

    public void submitProfilerChartRenderState(@NotNull List<ResultField> chartData, int x0, int y0, int x1, int y1) {
    }
}
