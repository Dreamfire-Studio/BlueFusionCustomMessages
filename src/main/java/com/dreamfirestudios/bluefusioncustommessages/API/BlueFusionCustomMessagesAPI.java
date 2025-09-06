/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */

package com.dreamfirestudios.bluefusioncustommessages.API;

import com.dreamfirestudios.dreamconfig.DreamConfig;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.bluefusioncustommessages.Core.Services;
import com.dreamfirestudios.bluefusioncustommessages.Core.Try;
import com.dreamfirestudios.bluefusioncustommessages.Event.BlueFusionCustomMessagesConfigReloadEvent;
import com.dreamfirestudios.bluefusioncustommessages.Event.BlueFusionCustomMessagesConfigResetEvent;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesConfig;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesSerializableItems;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Public, static API surface for <em>BlueFusionCustomMessages</em> features.
 * <p>Enables/disables the system, serializes items into config, and resets/reloads
 * configs with proper main-thread dispatch and plugin events.</p>
 */
public final class BlueFusionCustomMessagesAPI {

    private BlueFusionCustomMessagesAPI() { }

    /**
     * Set the system enabled flag and persist the config; success callback runs on main thread.
     *
     * @param onSuccess callback invoked with the updated config after save (non-null)
     * @param state     desired enabled state
     */
    public static void BlueFusionCustomMessagesEnableSystem(final Consumer<BlueFusionCustomMessagesConfig> onSuccess, final boolean state) {
        Objects.requireNonNull(onSuccess, "onSuccess");
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, config -> {
            config.systemEnabled = state;
            config.SaveDreamConfig(BlueFusionCustomMessages.GetBlueFusionCustomMessages(),
                    _ -> Services.scheduler().main(() -> onSuccess.accept(config)));
        });
    }

    /**
     * Toggle the system enabled flag and persist the config; success callback runs on main thread.
     *
     * @param onSuccess callback invoked with the updated config after save (non-null)
     */
    public static void BlueFusionCustomMessagesEnableSystem(final Consumer<BlueFusionCustomMessagesConfig> onSuccess) {
        Objects.requireNonNull(onSuccess, "onSuccess");
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, config -> {
            config.systemEnabled = !config.systemEnabled;
            config.SaveDreamConfig(BlueFusionCustomMessages.GetBlueFusionCustomMessages(),
                    _ -> Services.scheduler().main(() -> onSuccess.accept(config)));
        });
    }

    /**
     * Serialize and store an {@link ItemStack} under an ID, then persist and callback on main thread.
     *
     * @param onSuccess callback invoked with the serializable-items config after save (non-null)
     * @param id        key to store the item under (non-null)
     * @param itemStack item to store (non-null)
     */
    public static void BlueFusionCustomMessagesSerializeItem(final Consumer<BlueFusionCustomMessagesSerializableItems> onSuccess,
                                                     final String id,
                                                     final ItemStack itemStack) {
        Objects.requireNonNull(onSuccess, "onSuccess");
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(itemStack, "itemStack");

        BlueFusionCustomMessagesSerializableItems.ReturnStaticAsync(
                BlueFusionCustomMessages.GetBlueFusionCustomMessages(),
                BlueFusionCustomMessagesSerializableItems.class,
                cfg -> {
                    cfg.AddItemStack(id, itemStack);
                    cfg.SaveDreamConfig(BlueFusionCustomMessages.GetBlueFusionCustomMessages(),
                            _ -> Services.scheduler().main(() -> onSuccess.accept(cfg)));
                }
        );
    }

    /**
     * Reset configs (fresh registration) and fire {@link BlueFusionCustomMessagesConfigResetEvent}, no-op if disabled.
     *
     * @param settings message formatting/settings to pass through registration
     */
    public static void BlueFusionCustomMessagesResetConfigs(final DreamMessageSettings settings) {
        Objects.requireNonNull(settings, "settings");
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, config -> {
            if (!config.systemEnabled) return;

            Services.scheduler().main(() -> {
                // Retry a few times in case of transient load-order issues on boot.
                Try.runWithRetry("RegisterStatic(reset)", 3, Duration.ofMillis(50), () ->
                        DreamConfig.GetDreamConfig().RegisterStatic(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), true, settings));

                new BlueFusionCustomMessagesConfigResetEvent().callEvent();
            });
        });
    }

    /**
     * Reload configs (non-destructive) and fire {@link BlueFusionCustomMessagesConfigReloadEvent}, no-op if disabled.
     *
     * @param settings message formatting/settings to pass through registration
     */
    public static void BlueFusionCustomMessagesReloadConfigs(final DreamMessageSettings settings) {
        Objects.requireNonNull(settings, "settings");
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, cfg -> {
            if (!cfg.systemEnabled) return;

            Services.scheduler().main(() -> {
                Try.runWithRetry("RegisterStatic(reload)", 3, Duration.ofMillis(50), () ->
                        DreamConfig.GetDreamConfig().RegisterStatic(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), false, settings));

                new BlueFusionCustomMessagesConfigReloadEvent().callEvent();
            });
        });
    }
}