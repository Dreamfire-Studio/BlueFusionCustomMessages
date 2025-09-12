package com.dreamfirestudios.bluefusioncustommessages.Listeners;

import com.dreamfirestudios.bluefusioncustommessages.DynamicPulseConfig.PlayerDynamicPulseConfig;
import com.dreamfirestudios.bluefusioncustommessages.Enum.CustomMessageNames;
import com.dreamfirestudios.dreamcore.DreamChat.DreamChat;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

@PulseAutoRegister
public class PlayerQuit implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        event.quitMessage(null);
        var player = Objects.requireNonNull(event.getPlayer());
        PlayerDynamicPulseConfig.GetCustomMessageNames(player, CustomMessageNames.LEAVE_MESSAGE, message ->{
            String plainMessage = PlainTextComponentSerializer.plainText().serialize(message);
            DreamChat.BroadcastMessage(plainMessage, DreamMessageSettings.all());
        });
    }
}
