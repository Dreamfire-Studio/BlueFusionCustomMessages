package com.dreamfirestudios.bluefusioncustommessages.Listener;

import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesConfig;
import com.dreamfirestudios.dreamCore.DreamfireChat.DreamfireMessage;
import com.dreamfirestudios.dreamCore.DreamfireJava.PulseAutoRegister;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

@PulseAutoRegister
public class PlayerLogin implements Listener {

    private BlueFusionCustomMessagesConfig blueFusionCustomMessagesConfig;

    public PlayerLogin(){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, blueFusionCustomMessagesConfig -> {
            this.blueFusionCustomMessagesConfig = blueFusionCustomMessagesConfig;
        });
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event){
        if(!blueFusionCustomMessagesConfig.systemEnabled) return;
        if (event.getResult() != PlayerLoginEvent.Result.KICK_BANNED) return;
        if(blueFusionCustomMessagesConfig.banMessage.isEmpty()) return;
        var player = event.getPlayer();
        var joinMessage = blueFusionCustomMessagesConfig.leaveMessage;
        var joinMessagePlaceholder = PlaceholderAPI.setPlaceholders(player, joinMessage);
        var joinMessageString = DreamfireMessage.formatMessage(joinMessagePlaceholder, player);
        event.kickMessage(joinMessageString);
    }
}
