package me.zziger.obsoverlay.fabric.client;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OverlayEnvironment;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public final class OBSOverlayFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OverlayEnvironment.setGameFolder(FabricLoader.getInstance().getGameDir());
        OBSOverlay.init();
    }
}
