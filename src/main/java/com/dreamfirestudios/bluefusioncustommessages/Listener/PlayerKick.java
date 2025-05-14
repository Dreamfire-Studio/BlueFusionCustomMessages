package com.dreamfirestudios.bluefusioncustommessages.Listener;

import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesConfig;
import com.dreamfirestudios.dreamCore.DreamfireChat.DreamfireMessage;
import com.dreamfirestudios.dreamCore.DreamfireJava.PulseAutoRegister;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

@PulseAutoRegister
public class PlayerKick implements Listener {

    private BlueFusionCustomMessagesConfig blueFusionCustomMessagesConfig;

    public PlayerKick(){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, blueFusionCustomMessagesConfig -> {
            this.blueFusionCustomMessagesConfig = blueFusionCustomMessagesConfig;
        });
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
        if(!blueFusionCustomMessagesConfig.systemEnabled) return;
        if(blueFusionCustomMessagesConfig.kickMessage.isEmpty()) return;
        var player = event.getPlayer();
        var joinMessage = blueFusionCustomMessagesConfig.leaveMessage;
        var joinMessagePlaceholder = PlaceholderAPI.setPlaceholders(player, joinMessage);
        var joinMessageString = DreamfireMessage.formatMessage(joinMessagePlaceholder, player);
        event.reason(joinMessageString);
    }
}
