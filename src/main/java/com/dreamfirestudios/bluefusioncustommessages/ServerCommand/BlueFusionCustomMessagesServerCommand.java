/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */

package com.dreamfirestudios.bluefusioncustommessages.ServerCommand;

import com.dreamfirestudios.dreamcommand.Annotations.PCMethod;
import com.dreamfirestudios.dreamcommand.Annotations.PCOP;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import com.dreamfirestudios.bluefusioncustommessages.API.BlueFusionCustomMessagesAPI;
import com.dreamfirestudios.bluefusioncustommessages.Core.Services;
import com.dreamfirestudios.bluefusioncustommessages.Enum.BlueFusionCustomMessagesMessages;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesConfig;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesMessagesConfig;
import org.bukkit.command.CommandSender;

import java.util.Objects;

/**
 * Console/server command endpoints (no player context).
 */
@PulseAutoRegister
public final class BlueFusionCustomMessagesServerCommand {

    public static final String COMMAND_NAME = "bluefusioncustommessages_server";
    public static final String[] COMMAND_ALIASES = {};
    public static final boolean COMMAND_DEBUG = false;

    public BlueFusionCustomMessagesServerCommand() { }

    /** Report status to console. No-op when system disabled. */
    @PCMethod({"status"})
    @PCOP
    public void BlueFusionCustomMessagesMethod(final CommandSender sender) {
        Objects.requireNonNull(sender, "sender");
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, config -> {
            if (!config.systemEnabled) return;
            BlueFusionCustomMessagesMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesMessagesConfig.class, messageConfig ->
                    Services.scheduler().main(() ->
                            messageConfig.SendMessageToConsole(BlueFusionCustomMessagesMessages.SystemIsntEnabled, DreamMessageSettings.all()))
            );
        });
    }

    /** Enable/disable system via console. */
    @PCMethod({"enable"})
    @PCOP
    public void BlueFusionCustomMessagesEnableMethod(final CommandSender sender, final boolean state) {
        Objects.requireNonNull(sender, "sender");
        BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesEnableSystem(x -> {
            BlueFusionCustomMessagesMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesMessagesConfig.class, messagesConfig ->
                    Services.scheduler().main(() ->
                            messagesConfig.SendMessageToConsole(
                                    state ? BlueFusionCustomMessagesMessages.ConsoleEnabledSystem : BlueFusionCustomMessagesMessages.ConsoleDisableSystem,
                                    DreamMessageSettings.all()))
            );
        }, state);
    }

    /** Reset all plugin configs. No-op when system disabled. */
    @PCMethod({"configs", "reset"})
    @PCOP
    public void BlueFusionCustomMessagesConfigsResetMethod(final CommandSender sender) {
        Objects.requireNonNull(sender, "sender");
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, config -> {
            if (!config.systemEnabled) return;
            BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesResetConfigs(DreamMessageSettings.all());
            BlueFusionCustomMessagesMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesMessagesConfig.class, messageConfig ->
                    Services.scheduler().main(() ->
                            messageConfig.SendMessageToConsole(BlueFusionCustomMessagesMessages.PlayerResetConfig, DreamMessageSettings.all()))
            );
        });
    }

    /** Reload all plugin configs. No-op when system disabled. */
    @PCMethod({"configs", "reload"})
    @PCOP
    public void BlueFusionCustomMessagesConfigsReloadMethod(final CommandSender sender) {
        Objects.requireNonNull(sender, "sender");
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, config -> {
            if (!config.systemEnabled) return;
            BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesReloadConfigs(DreamMessageSettings.all());
            BlueFusionCustomMessagesMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesMessagesConfig.class, messageConfig ->
                    Services.scheduler().main(() ->
                            messageConfig.SendMessageToConsole(BlueFusionCustomMessagesMessages.PlayerReloadedConfig, DreamMessageSettings.all()))
            );
        });
    }
}