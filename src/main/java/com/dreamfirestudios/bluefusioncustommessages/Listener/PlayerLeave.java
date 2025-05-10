package com.dreamfirestudios.bluefusioncustommessages.Listener;

import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesConfig;
import com.dreamfirestudios.dreamCore.DreamfireChat.DreamfireMessage;
import com.dreamfirestudios.dreamCore.DreamfireJava.PulseAutoRegister;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@PulseAutoRegister
public class PlayerLeave implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessagesConfig.class, blueFusionCustomMessagesConfig -> {
            if(!blueFusionCustomMessagesConfig.systemEnabled) return;
            if(blueFusionCustomMessagesConfig.leaveMessage.isEmpty()) return;
            var player = event.getPlayer();
            var joinMessage = blueFusionCustomMessagesConfig.leaveMessage;
            var joinMessagePlaceholder = PlaceholderAPI.setPlaceholders(player, joinMessage);
            Bukkit.broadcast(DreamfireMessage.formatMessage(joinMessagePlaceholder, player));
        }, Throwable::printStackTrace);
    }
}