package com.dreamfirestudios.bluefusioncustommessages.Listeners;

import com.dreamfirestudios.bluefusioncustommessages.DynamicPulseConfig.PlayerDynamicPulseConfig;
import com.dreamfirestudios.bluefusioncustommessages.Enum.CustomMessageNames;
import com.dreamfirestudios.bluefusioncustommessages.StaticEnumPulseConfig.CustomMessageNamesConfig;
import com.dreamfirestudios.dreamcore.DreamChat.DreamChat;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

@PulseAutoRegister
public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.joinMessage(null);
        var player = Objects.requireNonNull(event.getPlayer());
        var customMessageName = player.hasPlayedBefore() ? CustomMessageNames.JOIN_MESSAGE : CustomMessageNames.FIRST_JOIN_MESSAGE;
        PlayerDynamicPulseConfig.GetCustomMessageNames(player, customMessageName, message ->{
            String plainMessage = PlainTextComponentSerializer.plainText().serialize(message);
            DreamChat.BroadcastMessage(plainMessage, DreamMessageSettings.all());
        });
    }
}
