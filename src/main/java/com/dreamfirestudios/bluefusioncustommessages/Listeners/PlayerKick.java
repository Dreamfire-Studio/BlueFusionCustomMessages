package com.dreamfirestudios.bluefusioncustommessages.Listeners;

import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.DynamicPulseConfig.PlayerDynamicPulseConfig;
import com.dreamfirestudios.bluefusioncustommessages.Enum.CustomMessageNames;
import com.dreamfirestudios.dreamcore.DreamChat.DreamChat;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageFormatter;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import io.papermc.paper.ban.BanListType;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.awt.*;
import java.util.Objects;

@PulseAutoRegister
public class PlayerKick implements Listener {
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
        var player = Objects.requireNonNull(event.getPlayer());

        var customMessageNames = CustomMessageNames.KICK_MESSAGE;
        if(player.isBanned()){
            var entry = Bukkit.getBanList(BanListType.PROFILE).getBanEntry(player.getPlayerProfile());
            if (entry != null && entry.getExpiration() != null) {
                customMessageNames = CustomMessageNames.TEMPBAN_MESSAGE;
            } else {
                customMessageNames = CustomMessageNames.BAN_MESSAGE;
            }
        }

        var playerDynamicPulseConfig = BlueFusionCustomMessages.PlayerDynamicPulseConfigs.get(player.getUniqueId());
        var storedMessage = playerDynamicPulseConfig.customMessages.getHashMap().getOrDefault(customMessageNames, customMessageNames.getDefaultMessage());
        String reasonText = PlainTextComponentSerializer.plainText().serialize(event.reason());
        BlueFusionCustomMessages.ReasonContextMap.put(player.getUniqueId(), reasonText);
        event.reason(DreamMessageFormatter.format(storedMessage, player, DreamMessageSettings.all()));
    }
}
