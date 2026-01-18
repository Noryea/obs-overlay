package me.zziger.obsoverlay.mixin;

import me.zziger.obsoverlay.OBSOverlayConfig;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VideoSettingsScreen.class)
public abstract class VideoSettingsScreenMixin extends OptionsSubScreen {

    public VideoSettingsScreenMixin(Screen parent, Options gameOptions, Component title) {
        super(parent, gameOptions, title);
    }

    @Inject(method = "addOptions", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        this.list.addSmall(Button.builder(Component.translatable("obs_overlay.config.title"), (button) -> {
            this.minecraft.setScreen(OBSOverlayConfig.getScreenSupplier((VideoSettingsScreen) (Object) this).get());
        }).build(), null);
    }
}
