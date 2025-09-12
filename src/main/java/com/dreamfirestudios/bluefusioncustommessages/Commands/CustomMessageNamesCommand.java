package com.dreamfirestudios.bluefusioncustommessages.Commands;

import com.dreamfirestudios.bluefusioncustommessages.DynamicPulseConfig.PlayerDynamicPulseConfig;
import com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_Messages;
import com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_Permissions;
import com.dreamfirestudios.bluefusioncustommessages.Enum.CustomMessageNames;
import com.dreamfirestudios.bluefusioncustommessages.StaticEnumPulseConfig.CustomMessageNamesConfig;
import com.dreamfirestudios.bluefusioncustommessages.StaticEnumPulseConfig.MessagesConfig;
import com.dreamfirestudios.bluefusioncustommessages.StaticEnumPulseConfig.PermissionsConfig;
import com.dreamfirestudios.dreamcommand.Annotations.PCMethod;
import com.dreamfirestudios.dreamcommand.Annotations.PCTab;
import com.dreamfirestudios.dreamcommand.Enums.TabType;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Objects;

@PulseAutoRegister
public class CustomMessageNamesCommand {

    public static final String COMMAND_NAME = "bluefusion";
    public static final String[] COMMAND_ALIASES = {};
    public static final boolean COMMAND_DEBUG = false;

    @PCMethod({"custommessages", "player", "set"})
    @PCTab(pos = 1, type = TabType.OnlinePlayerNames)
    @PCTab(pos = 2, type = TabType.Enum, data = "com.dreamfirestudios.bluefusioncustommessages.Enum.CustomMessageNames")
    public void SetPlayerMessage(final Player sender, final String targetName, final CustomMessageNames key, final String... newMessage) {
        Objects.requireNonNull(sender, "sender");
        Objects.requireNonNull(targetName, "targetName");
        Objects.requireNonNull(key, "key");

        PermissionsConfig.DoesPlayerHavePermission(sender, BFCM_Permissions.UpdatePlayerCustomMessages, true, cfg -> {
            Player target = Bukkit.getPlayerExact(targetName);
            if (target == null) {
                MessagesConfig.GetMessagesConfig(msgCfg -> msgCfg.SendToSinglePlayer(BFCM_Messages.TargetPlayerNotFound, sender, DreamMessageSettings.all()));
                return;
            }
            String value = String.join(" ", newMessage);
            PlayerDynamicPulseConfig.SetCustomMessageNames(target, key, value, updated -> {
                MessagesConfig.GetMessagesConfig(msgCfg -> {
                    msgCfg.SendToSinglePlayer(BFCM_Messages.PlayerUpdatedCustomMessage, sender, DreamMessageSettings.all());
                    sender.playSound(sender.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                });
            });
        });
    }

    @PCMethod({"custommessages", "player", "reset"})
    @PCTab(pos = 1, type = TabType.OnlinePlayerNames)
    @PCTab(pos = 2, type = TabType.Enum, data = "com.dreamfirestudios.bluefusioncustommessages.Enum.CustomMessageNames")
    public void ResetPlayerMessage(final Player sender, final String targetName, final CustomMessageNames key) {
        Objects.requireNonNull(sender, "sender");
        PermissionsConfig.DoesPlayerHavePermission(sender, BFCM_Permissions.UpdatePlayerCustomMessages, true, cfg -> {
            Player target = Bukkit.getPlayerExact(targetName);
            if (target == null) {
                MessagesConfig.GetMessagesConfig(msgCfg -> msgCfg.SendToSinglePlayer(BFCM_Messages.TargetPlayerNotFound, sender, DreamMessageSettings.all()));
                return;
            }
            PlayerDynamicPulseConfig.ResetCustomMessageNames(target, key, updated -> {
                MessagesConfig.GetMessagesConfig(msgCfg -> {
                    msgCfg.SendToSinglePlayer(BFCM_Messages.PlayerResetCustomMessage, sender, DreamMessageSettings.all());
                    sender.playSound(sender.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                });
            });
        });
    }

    @PCMethod({"custommessages", "global", "set"})
    @PCTab(pos = 1, type = TabType.Enum, data = "com.dreamfirestudios.bluefusioncustommessages.Enum.CustomMessageNames")
    public void SetGlobalMessage(final Player sender, final CustomMessageNames key, final String... newMessage) {
        Objects.requireNonNull(sender, "sender");
        PermissionsConfig.DoesPlayerHavePermission(sender, BFCM_Permissions.UpdateMasterCustomMessages, true, cfg -> {
            String value = String.join(" ", newMessage);
            CustomMessageNamesConfig.SetCustomMessage(key, value, updated -> {
                MessagesConfig.GetMessagesConfig(msgCfg -> {
                    msgCfg.SendToSinglePlayer(BFCM_Messages.GlobalUpdatedCustomMessage, sender, DreamMessageSettings.all());
                    sender.playSound(sender.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                });
            });
        });
    }

    @PCMethod({"custommessages", "global", "reset"})
    @PCTab(pos = 1, type = TabType.Enum, data = "com.dreamfirestudios.bluefusioncustommessages.Enum.CustomMessageNames")
    public void ResetGlobalMessage(final Player sender, final CustomMessageNames key) {
        Objects.requireNonNull(sender, "sender");
        PermissionsConfig.DoesPlayerHavePermission(sender, BFCM_Permissions.UpdateMasterCustomMessages, true, cfg -> {
            CustomMessageNamesConfig.ResetCustomMessage(key, updated -> {
                MessagesConfig.GetMessagesConfig(msgCfg -> {
                    msgCfg.SendToSinglePlayer(BFCM_Messages.GlobalResetCustomMessage, sender, DreamMessageSettings.all());
                    sender.playSound(sender.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                });
            });
        });
    }

    @PCMethod(value = {"custommessages", "global", "set"})
    public void SetGlobalMessageConsole(final org.bukkit.command.CommandSender sender, final CustomMessageNames key, final String... newMessage) {
        Objects.requireNonNull(sender, "sender");
        String value = String.join(" ", newMessage);
        CustomMessageNamesConfig.SetCustomMessage(key, value, updated -> {
            MessagesConfig.GetMessagesConfig(msgCfg -> {
                msgCfg.SendToConsole(BFCM_Messages.GlobalUpdatedCustomMessage, DreamMessageSettings.all());
            });
        });
    }

    @PCMethod(value = {"custommessages", "global", "reset"})
    public void ResetGlobalMessageConsole(final org.bukkit.command.CommandSender sender, final CustomMessageNames key) {
        Objects.requireNonNull(sender, "sender");
        CustomMessageNamesConfig.ResetCustomMessage(key, updated -> {
            MessagesConfig.GetMessagesConfig(msgCfg -> {
                msgCfg.SendToConsole(BFCM_Messages.GlobalResetCustomMessage, DreamMessageSettings.all());
            });
        });
    }
}
