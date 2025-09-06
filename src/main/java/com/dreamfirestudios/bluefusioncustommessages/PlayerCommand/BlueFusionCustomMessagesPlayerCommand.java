/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */

package com.dreamfirestudios.bluefusioncustommessages.PlayerCommand;

import com.dreamfirestudios.dreamcommand.Annotations.PCMethod;
import com.dreamfirestudios.dreamcommand.Annotations.PCTab;
import com.dreamfirestudios.dreamcommand.Enums.TabType;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import com.dreamfirestudios.bluefusioncustommessages.API.BlueFusionCustomMessagesAPI;
import com.dreamfirestudios.bluefusioncustommessages.Core.Services;
import com.dreamfirestudios.bluefusioncustommessages.Core.BlueFusionCustomMessagesPlayerCommandHelper;
import com.dreamfirestudios.bluefusioncustommessages.Enum.BlueFusionCustomMessagesMessages;
import com.dreamfirestudios.bluefusioncustommessages.Enum.BlueFusionCustomMessagesPermissions;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesMessagesConfig;
import com.dreamfirestudios.bluefusioncustommessages.SmartInvs.BlueFusionCustomMessagesCoreMenu;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * Player command entrypoint for {@code BlueFusionCustomMessages}.
 *
 * Responsibilities:
 *  - Expose admin GUI and operational commands to players.
 *  - Use PlayerCommandHelper for consistent enable/permission checks.
 *  - Marshal UI/feedback to the main thread via Services.scheduler().
 */
@PulseAutoRegister
public final class BlueFusionCustomMessagesPlayerCommand {

    public static final String COMMAND_NAME = "bluefusioncustommessages";
    public static final String[] COMMAND_ALIASES = {};
    public static final boolean COMMAND_DEBUG = false;

    public BlueFusionCustomMessagesPlayerCommand() { }

    /** Open admin GUI (permission-only). */
    @PCMethod({})
    public void BlueFusionCustomMessagesMethod(final Player player) {
        Objects.requireNonNull(player, "player");
        BlueFusionCustomMessagesPlayerCommandHelper.checkPermission(
                BlueFusionCustomMessagesPermissions.AdminConsole, player, true, DreamMessageSettings.all(),
                () -> Services.scheduler().main(() -> new BlueFusionCustomMessagesCoreMenu(player))
        );
    }

    /** Enable/disable system (permission-only). */
    @PCMethod({"enable"})
    public void BlueFusionCustomMessagesEnableMethod(final Player player, final boolean state) {
        Objects.requireNonNull(player, "player");
        BlueFusionCustomMessagesPlayerCommandHelper.checkPermission(
                BlueFusionCustomMessagesPermissions.EnableSystem, player, true, DreamMessageSettings.all(),
                () -> BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesEnableSystem(cfg -> {
                    BlueFusionCustomMessagesMessagesConfig.ReturnStaticAsync(
                            BlueFusionCustomMessages.GetBlueFusionCustomMessages(),
                            BlueFusionCustomMessagesMessagesConfig.class,
                            messageConfig -> Services.scheduler().main(() ->
                                    messageConfig.SendMessageToPlayer(
                                            state ? BlueFusionCustomMessagesMessages.ConsoleEnabledSystem
                                                    : BlueFusionCustomMessagesMessages.ConsoleDisableSystem,
                                            player, DreamMessageSettings.all()))
                    );
                }, state)
        );
    }

    /** Serialize main-hand item (permission-only). */
    @PCMethod({"serialize"})
    @PCTab(pos = 1, type = TabType.PureData, data = "ITEM ID")
    public void BlueFusionCustomMessagesSerializeItemMethod(final Player player, final String itemName) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(itemName, "itemName");

        BlueFusionCustomMessagesPlayerCommandHelper.checkPermission(
                BlueFusionCustomMessagesPermissions.SerializeItem, player, true, DreamMessageSettings.all(),
                () -> BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesSerializeItem(x -> {
                    BlueFusionCustomMessagesMessagesConfig.ReturnStaticAsync(
                            BlueFusionCustomMessages.GetBlueFusionCustomMessages(),
                            BlueFusionCustomMessagesMessagesConfig.class,
                            messageConfig -> Services.scheduler().main(() ->
                                    messageConfig.SendMessageToPlayer(
                                            BlueFusionCustomMessagesMessages.PlayerSerializedItem,
                                            player, DreamMessageSettings.all(), itemName))
                    );
                }, itemName, player.getInventory().getItemInMainHand())
        );
    }

    /** Reset configs (requires system enabled + permission). */
    @PCMethod({"configs", "reset"})
    public void BlueFusionCustomMessagesConfigsResetMethod(final Player player) {
        Objects.requireNonNull(player, "player");
        BlueFusionCustomMessagesPlayerCommandHelper.checkSystemEnabledAndPermission(
                BlueFusionCustomMessagesPermissions.ResetConfigs, player, true, DreamMessageSettings.all(),
                () -> {
                    BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesResetConfigs(DreamMessageSettings.all());
                    BlueFusionCustomMessagesMessagesConfig.ReturnStaticAsync(
                            BlueFusionCustomMessages.GetBlueFusionCustomMessages(),
                            BlueFusionCustomMessagesMessagesConfig.class,
                            messageConfig -> Services.scheduler().main(() ->
                                    messageConfig.SendMessageToPlayer(
                                            BlueFusionCustomMessagesMessages.PlayerResetConfig,
                                            player, DreamMessageSettings.all()))
                    );
                });
    }

    /** Reload configs (requires system enabled + permission). */
    @PCMethod({"configs", "reload"})
    public void BlueFusionCustomMessagesReloadMethod(final Player player) {
        Objects.requireNonNull(player, "player");
        BlueFusionCustomMessagesPlayerCommandHelper.checkSystemEnabledAndPermission(
                BlueFusionCustomMessagesPermissions.ReloadConfigs, player, true, DreamMessageSettings.all(),
                () -> {
                    BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesReloadConfigs(DreamMessageSettings.all());
                    BlueFusionCustomMessagesMessagesConfig.ReturnStaticAsync(
                            BlueFusionCustomMessages.GetBlueFusionCustomMessages(),
                            BlueFusionCustomMessagesMessagesConfig.class,
                            messageConfig -> Services.scheduler().main(() ->
                                    messageConfig.SendMessageToPlayer(
                                            BlueFusionCustomMessagesMessages.PlayerReloadedConfig,
                                            player, DreamMessageSettings.all()))
                    );
                });
    }
}