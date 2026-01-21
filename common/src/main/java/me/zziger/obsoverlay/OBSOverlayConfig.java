package me.zziger.obsoverlay;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import dev.architectury.platform.Platform;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.Requirement;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import me.zziger.obsoverlay.component.OverlayComponentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Config(name = OBSOverlay.MOD_ID)
public class OBSOverlayConfig implements ConfigData {
    private static OBSOverlayConfig INSTANCE;

    public HashMap<String, Boolean> overlayComponents = new HashMap<>();
    public HashMap<String, Boolean> autoHideComponents = new HashMap<>();
    public HashMap<String, Boolean> overlayScreensList = new HashMap<>();

    public boolean hideAllScreens = false;
    public boolean overlayHandledScreensEnabled = false;
    public HashSet<String> overlayHandledScreensList = new HashSet<>();

    public transient HashSet<Class<?>> overlayScreensClasses = new HashSet<>();

    public boolean showTestIcon;

    public static void init() {
        Path configPath = getPath();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (Files.exists(configPath)) {
            try {
                BufferedReader reader = Files.newBufferedReader(configPath);
                OBSOverlayConfig ret = gson.fromJson(reader, OBSOverlayConfig.class);
                reader.close();
                INSTANCE = ret;
            } catch (JsonParseException | IOException var4) {
                OBSOverlay.LOGGER.error("Failed to load config", var4);
                INSTANCE = new OBSOverlayConfig();
            }
        } else {
            INSTANCE = new OBSOverlayConfig();
        }

        INSTANCE.updateCache();
    }

    private void updateCache() {
        this.overlayScreensClasses.clear();
        this.overlayScreensList.forEach((screenId, state) -> {
            if (!state) return;
            if (!OverlayComponentRegistry.hideableScreens.containsKey(screenId)) return;
            this.overlayScreensClasses.add(OverlayComponentRegistry.hideableScreens.get(screenId));
        });
    }

    public static OBSOverlayConfig get() {
        return INSTANCE;
    }

    public static Path getPath() {
        return Platform.getConfigFolder().resolve(OBSOverlay.MOD_ID + ".json");
    }

    public static Supplier<Screen> getScreenSupplier(Screen parent) {
        OBSOverlayConfig config = get();

        return () -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Component.translatable("obs_overlay.config.title"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            ConfigCategory general = builder.getOrCreateCategory(Component.empty());


            general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("obs_overlay.config.show_test_icon"), config.showTestIcon)
                    .setTooltip(Component.translatable("obs_overlay.config.show_test_icon.tooltip"))
                    .setDefaultValue(false)
                    .setSaveConsumer((value) -> config.showTestIcon = value)
                    .build());

            HashMap<String, BooleanListEntry> overlayEntries = new HashMap<>();
            SubCategoryBuilder componentsToOverlay = entryBuilder.startSubCategory(Component.translatable("obs_overlay.config.components_to_overlay"))
                    .setExpanded(true)
                    .setTooltip(Component.translatable("obs_overlay.config.components_to_overlay.tooltip"));

            OverlayComponentRegistry.components.forEach(component -> {
                BooleanListEntry entry = entryBuilder.startBooleanToggle(Component.translatable("obs_overlay.component." + component.getId()), component.isOverlayEnabled())
                        .setTooltip(Component.translatable("obs_overlay.component." + component.getId() + ".tooltip"))
                        .setDefaultValue(component.isOverlayEnabledDefault())
                        .setSaveConsumer(component::setOverlayEnabled)
                        .build();
                overlayEntries.put(component.getId(), entry);
                componentsToOverlay.add(entry);
            });
            general.addEntry(componentsToOverlay.build());

            SubCategoryBuilder screensToOverlay = entryBuilder.startSubCategory(Component.translatable("obs_overlay.config.screens_to_overlay"))
                    .setTooltip(Component.translatable("obs_overlay.config.screens_to_overlay.tooltip"));

            BooleanListEntry hideAllScreens = entryBuilder.startBooleanToggle(Component.translatable("obs_overlay.config.hide_all_screens"), config.hideAllScreens)
                            .setDefaultValue(false)
                            .setSaveConsumer(state -> config.hideAllScreens = state)
                            .build();
            screensToOverlay.add(hideAllScreens);

            OverlayComponentRegistry.hideableScreens.forEach((screenId, clazz) -> {
                BooleanListEntry entry = entryBuilder.startBooleanToggle(Component.translatable("obs_overlay.screen." + screenId), config.overlayScreensList.getOrDefault(screenId, false))
                        .setDefaultValue(false)
                        .setDisplayRequirement(Requirement.isFalse(hideAllScreens))
                        .setSaveConsumer(state -> config.overlayScreensList.put(screenId, state))
                        .build();
                screensToOverlay.add(entry);
            });

            BooleanListEntry customHandledScreens = entryBuilder.startBooleanToggle(Component.translatable("obs_overlay.config.enable_custom_handled_screens"), config.overlayHandledScreensEnabled)
                    .setTooltip(Component.translatable("obs_overlay.config.enable_custom_handled_screens.tooltip"))
                    .setDefaultValue(false)
                    .setDisplayRequirement(Requirement.isFalse(hideAllScreens))
                    .setSaveConsumer(state -> config.overlayHandledScreensEnabled = state)
                    .build();

            screensToOverlay.add(customHandledScreens);

            screensToOverlay.add(entryBuilder.startStrList(Component.translatable("obs_overlay.config.custom_handled_screens"), config.overlayHandledScreensList.stream().toList())
                    .setTooltip(Component.translatable("obs_overlay.config.custom_handled_screens.tooltip"))
                    .setSaveConsumer(state -> config.overlayHandledScreensList = new HashSet<>(state))
                    .setDisplayRequirement(Requirement.all(Requirement.isFalse(hideAllScreens), Requirement.isTrue(customHandledScreens)))
                    .setErrorSupplier(list -> {
                        for (String id : list) {
                            try {
                                if (!BuiltInRegistries.MENU.containsKey(Identifier.parse(id)))
                                    return Optional.of(Component.translatable("obs_overlay.config.screen_doesnt_exist", id));
                            } catch(Exception e) {
                                return Optional.of(Component.translatable("obs_overlay.config.screen_doesnt_exist", id));
                            }
                        }

                        return Optional.empty();
                    })
                    .build()
            );


            screensToOverlay.add(entryBuilder.startStrList(Component.translatable("obs_overlay.config.existing_handled_screens"), BuiltInRegistries.MENU.keySet().stream().filter(Objects::nonNull).map(e -> e.toString()).toList())
                    .setTooltip(Component.translatable("obs_overlay.config.existing_handled_screens.tooltip"))
                    .setDisplayRequirement(Requirement.all(Requirement.isFalse(hideAllScreens), Requirement.isTrue(customHandledScreens)))
                    .setInsertButtonEnabled(false)
                    .setDeleteButtonEnabled(false)
                    .build()
            );


            general.addEntry(screensToOverlay.build());

            SubCategoryBuilder autoHideComponents = entryBuilder.startSubCategory(Component.translatable("obs_overlay.config.auto_hide_components"))
                    .setExpanded(false)
                    .setTooltip(Component.translatable("obs_overlay.config.auto_hide_components.tooltip"));

            OverlayComponentRegistry.components.forEach(component -> {
                if (!component.canAutoHide()) return;
                if (!overlayEntries.containsKey(component.getId())) return;

                autoHideComponents.add(entryBuilder.startBooleanToggle(Component.translatable("obs_overlay.component." + component.getId()), component.isAutoHideEnabled())
                        .setDefaultValue(true)
                        .setRequirement(Requirement.isTrue(overlayEntries.get(component.getId())))
                        .setTooltipSupplier(() -> {
                            Component mainTooltip = Component.translatable("obs_overlay.component." + component.getId() + ".tooltip");
                            if (overlayEntries.get(component.getId()).getValue()) {
                                return Optional.of(new Component[]{mainTooltip});
                            } else {
                                Component optionName = Component.translatable("obs_overlay.component." + component.getId());
                                return Optional.of(new Component[] {mainTooltip, Component.translatable("obs_overlay.config.requires_overlay_enabled", optionName).withStyle(ChatFormatting.RED)});
                            }
                        })
                        .setSaveConsumer(component::setAutoHideEnabled)
                        .build());
            });
            general.addEntry(autoHideComponents.build());

            builder.setDoesConfirmSave(false);
            builder.setShouldListSmoothScroll(false);
            builder.setShouldTabsSmoothScroll(false);

            builder.setSavingRunnable(() -> {
                Path configPath = getPath();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                config.updateCache();

                try {
                    Files.createDirectories(configPath.getParent());
                    BufferedWriter writer = Files.newBufferedWriter(configPath);
                    gson.toJson(config, writer);
                    writer.close();
                } catch (IOException e) {
                    OBSOverlay.LOGGER.error("Failed to save config", e);
                }
            });

            return builder.build();
        };
    }

    public static boolean isScreenOverlayed(Screen screen) {
        OBSOverlayConfig config = OBSOverlayConfig.get();

        if (config.hideAllScreens && Minecraft.getInstance().level != null) return true;
        if (config.overlayScreensClasses.contains(screen.getClass())) return true;
        if (config.overlayHandledScreensEnabled) {
            if (screen instanceof AbstractContainerScreen<?> handledScreen) {
                try {
                    Identifier id = BuiltInRegistries.MENU.getKey(handledScreen.getMenu().getType());
                    if (id != null && (config.overlayHandledScreensList.contains(id.toString()) || config.overlayHandledScreensList.contains(id.getPath())))
                        return true;
                } catch (Exception ignored) {
                }
            }
        }
        return false;
    }
}
