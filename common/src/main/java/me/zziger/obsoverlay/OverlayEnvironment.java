package me.zziger.obsoverlay;

import java.nio.file.Path;

/**
 * Minimal loader bridge that replaces {@code dev.architectury.platform.Platform}.
 * <p>
 * Each platform entry point must call {@link #setGameFolder(Path)} with its native
 * loader API (FabricLoader#getGameDir / FMLLoader#getGameDir) before
 * {@link OBSOverlay#init()} runs.
 */
public final class OverlayEnvironment {
    private static Path gameFolder;

    private OverlayEnvironment() {
    }

    public static void setGameFolder(Path path) {
        gameFolder = path;
    }

    public static Path getGameFolder() {
        if (gameFolder == null) {
            gameFolder = Path.of(System.getProperty("user.dir", "."));
        }
        return gameFolder;
    }

    public static Path getConfigFolder() {
        return getGameFolder().resolve("config");
    }
}
