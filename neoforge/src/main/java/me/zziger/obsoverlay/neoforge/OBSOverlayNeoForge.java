package me.zziger.obsoverlay.neoforge;

import me.zziger.obsoverlay.OBSOverlay;
import me.zziger.obsoverlay.OBSOverlayConfig;
import me.zziger.obsoverlay.OverlayEnvironment;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(OBSOverlay.MOD_ID)
public final class OBSOverlayNeoForge {
    private static void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, parent) -> {
            return OBSOverlayConfig.getScreenSupplier(parent).get();
        });
    }

    public OBSOverlayNeoForge() {
        // Provide the game directory before running common setup.
        OverlayEnvironment.setGameFolder(FMLLoader.getCurrent().getGameDir());

        // Run our common setup.
        OBSOverlay.init();

        if (FMLEnvironment.getDist().isClient()) {
            registerModsPage();
        }
    }
}
