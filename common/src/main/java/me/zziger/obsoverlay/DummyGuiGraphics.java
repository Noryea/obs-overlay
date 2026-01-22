package me.zziger.obsoverlay;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import me.zziger.obsoverlay.mixin.accessor.GuiGraphicsAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.object.banner.BannerFlagModel;
import net.minecraft.client.model.object.book.BookModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.profiling.ResultField;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

@SuppressWarnings("NullableProblems")
public class DummyGuiGraphics extends GuiGraphics {

    public static final DummyGuiGraphics INSTANCE = new DummyGuiGraphics();

    private DummyGuiGraphics() {
        super(Minecraft.getInstance(), new GuiRenderState(), 0, 0);
    }

    @Override
    public void nextStratum() {
        var accessor = (GuiGraphicsAccessor) this;
        accessor.getGuiRenderState().reset();
    }

    @Override
    public void blurBeforeThisStratum() {
    }

    @Override
    public void fill(RenderPipeline pipeline, int minX, int minY, int maxX, int maxY, int color) {
    }

    @Override
    public void fillGradient(int minX, int minY, int maxX, int maxY, int colorFrom, int colorTo) {
    }

    @Override
    public void fill(RenderPipeline pipeline, TextureSetup textureSetup, int minX, int minY, int maxX, int maxY) {
    }

    @Override
    public void blitSprite(RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, int color) {
    }

    @Override
    public void blitSprite(RenderPipeline pipeline, Identifier sprite, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height, int color) {
    }

    @Override
    public void blitSprite(RenderPipeline pipeline, TextureAtlasSprite sprite, int x, int y, int width, int height, int color) {
    }

    @Override
    public void blit(RenderPipeline pipeline, Identifier atlas, int x, int y, float u, float v, int width, int height, int uWidth, int vHeight, int textureWidth, int textureHeight, int color) {
    }

    @Override
    public void drawString(Font font, FormattedCharSequence text, int x, int y, int color, boolean drawShadow) {
    }

    @Override
    public void submitMapRenderState(MapRenderState renderState) {
    }

    @Override
    public void submitEntityRenderState(EntityRenderState renderState, float scale, Vector3f translation, Quaternionf rotation, Quaternionf overrideCameraAngle, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void submitSkinRenderState(PlayerModel playerModel, Identifier texture, float rotationX, float rotationY, float pivotY, float x0, int y0, int x1, int y1, int scale) {
    }

    @Override
    public void submitBookModelRenderState(BookModel bookModel, Identifier texture, float open, float flip, float x0, int y0, int x1, int y1, int scale) {
    }

    @Override
    public void submitBannerPatternRenderState(BannerFlagModel flag, DyeColor baseColor, BannerPatternLayers resultBannerPatterns, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void submitSignRenderState(Model.Simple signModel, float scale, WoodType woodType, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void submitProfilerChartRenderState(List<ResultField> chartData, int x0, int y0, int x1, int y1) {
    }
}
