/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */

package com.dreamfirestudios.bluefusioncustommessages.PulseConfig;

import com.dreamfirestudios.dreamconfig.Abstract.StaticPulseConfig;
import com.dreamfirestudios.dreamconfig.Interface.ConfigVersion;
import com.dreamfirestudios.dreamconfig.Interface.StorageComment;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import com.dreamfirestudios.bluefusioncustommessages.Event.BlueFusionCustomMessagesSystemToggleEvent;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Primary configuration for {@code BlueFusionCustomMessages}.
 */
@PulseAutoRegister
@ConfigVersion(1)
public class BlueFusionCustomMessagesConfig extends StaticPulseConfig<BlueFusionCustomMessagesConfig> {

    @Override public JavaPlugin mainClass() { return BlueFusionCustomMessages.GetBlueFusionCustomMessages(); }

    @StorageComment("WARNING: SYSTEM WON'T RUN IF FALSE!")
    public boolean systemEnabled = true;

    @StorageComment("Display debugs in the console logs for changes in this config!")
    public boolean debugConfig = false;

    @Override public boolean useSubFolder() { return false; }

    /**
     * Update {@link #systemEnabled} and emit a {@link BlueFusionCustomMessagesSystemToggleEvent}.
     * Event is dispatched before persisting so listeners see the transition.
     *
     * @param onSuccess callback invoked post-save (non-null)
     * @param newState  new enabled state
     */
    public void ToggleSystemEnabled(final Consumer<BlueFusionCustomMessagesConfig> onSuccess, final boolean newState) {
        Objects.requireNonNull(onSuccess, "onSuccess");
        final var event = new BlueFusionCustomMessagesSystemToggleEvent(systemEnabled, newState);
        event.callEvent(); // dispatch on main thread if enabled (see Abstract event)
        systemEnabled = newState;
        SaveDreamConfig(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), onSuccess);
    }
}