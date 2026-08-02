package me.zziger.obsoverlay;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import me.zziger.obsoverlay.mixin.accessor.GuiGraphicsExtractorAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

@SuppressWarnings("NullableProblems")
public class DummyGuiGraphicsExtractor extends GuiGraphicsExtractor {

    public static final DummyGuiGraphicsExtractor INSTANCE = new DummyGuiGraphicsExtractor();

    private DummyGuiGraphicsExtractor() {
        super(Minecraft.getInstance(), new GuiRenderState(), 0, 0);
    }

    @Override
    public void nextStratum() {
        var accessor = (GuiGraphicsExtractorAccessor) this;
        accessor.getGuiRenderState().reset();
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
    public void blitSprite(RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height) {
    }

    @Override
    public void blitSprite(RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, int color) {
    }

    @Override
    public void blitSprite(RenderPipeline pipeline, Identifier sprite, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height) {
    }

    @Override
    public void blitSprite(RenderPipeline pipeline, Identifier sprite, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height, int color) {
    }

    @Override
    public void blitSprite(RenderPipeline pipeline, TextureAtlasSprite sprite, int x, int y, int width, int height) {
    }

    @Override
    public void blitSprite(RenderPipeline pipeline, TextureAtlasSprite sprite, int x, int y, int width, int height, int color) {
    }

    @Override
    public void blit(RenderPipeline pipeline, Identifier atlas, int x, int y, float u, float v, int width, int height, int uWidth, int vHeight) {
    }

    @Override
    public void blit(RenderPipeline pipeline, Identifier atlas, int x, int y, float u, float v, int width, int height, int uWidth, int vHeight, int textureWidth, int textureHeight) {
    }

    @Override
    public void text(Font font, FormattedCharSequence text, int x, int y, int color, boolean drawShadow) {
    }

    @Override
    public void map(MapRenderState renderState) {
    }

    @Override
    public void entity(EntityRenderState renderState, float scale, Vector3f translation, Quaternionf rotation, Quaternionf overrideCameraAngle, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void skin(PlayerModel playerModel, Identifier texture, float rotationX, float rotationY, float pivotY, float scale, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void book(BookModel bookModel, Identifier texture, float open, float flip, float scale, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void bannerPattern(BannerFlagModel flag, DyeColor baseColor, BannerPatternLayers resultBannerPatterns, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void sign(Model.Simple signModel, float scale, WoodType woodType, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void profilerChart(List<ResultField> chartData, int x0, int y0, int x1, int y1) {
    }

    @Override
    public void item(ItemStack stack, int x, int y) {
    }

    @Override
    public void item(ItemStack stack, int x, int y, int seed) {
    }
}