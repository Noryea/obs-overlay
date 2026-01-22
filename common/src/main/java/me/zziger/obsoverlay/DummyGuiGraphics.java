package me.zziger.obsoverlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.profiling.ResultField;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public class DummyGuiGraphics extends GuiGraphics {

    public static final DummyGuiGraphics INSTANCE = new DummyGuiGraphics();

    private DummyGuiGraphics() {
        super(Minecraft.getInstance(), new GuiRenderState());
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

    @Override
    public void submitEntityRenderState(@NotNull EntityRenderState renderState, float scale, @NotNull Vector3f translation, @NotNull Quaternionf rotation, @Nullable Quaternionf overrideCameraAngle, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void submitSkinRenderState(PlayerModel playerModel, ResourceLocation texture, float scale, float rotationX, float rotationY, float pivotY, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void submitBookModelRenderState(BookModel bookModel, ResourceLocation texture, float scale, float open, float flip, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void submitBannerPatternRenderState(ModelPart flag, DyeColor baseColor, BannerPatternLayers resultBannerPatterns, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void submitSignRenderState(Model signModel, float scale, WoodType woodType, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void submitProfilerChartRenderState(@NotNull List<ResultField> chartData, int x0, int y0, int x1, int y1) {
    }
}
