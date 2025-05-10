package com.dreamfirestudios.bluefusioncustommessages.Listener;

import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesConfig;
import com.dreamfirestudios.dreamCore.DreamfireChat.DreamfireMessage;
import com.dreamfirestudios.dreamCore.DreamfireJava.PulseAutoRegister;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@PulseAutoRegister
public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.joinMessage(null);
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessagesConfig.class, blueFusionCustomMessagesConfig -> {
            if(!blueFusionCustomMessagesConfig.systemEnabled) return;
            if(blueFusionCustomMessagesConfig.joinMessage.isEmpty()) return;
            var player = event.getPlayer();
            var joinMessage = player.hasPlayedBefore() ? blueFusionCustomMessagesConfig.joinMessage : blueFusionCustomMessagesConfig.firstJoinMessage;
            var joinMessagePlaceholder = PlaceholderAPI.setPlaceholders(player, joinMessage);
            Bukkit.broadcast(DreamfireMessage.formatMessage(joinMessagePlaceholder, player));
        }, Throwable::printStackTrace);
    }
}
