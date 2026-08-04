package me.zziger.obsoverlay;

import java.nio.file.Path;

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
