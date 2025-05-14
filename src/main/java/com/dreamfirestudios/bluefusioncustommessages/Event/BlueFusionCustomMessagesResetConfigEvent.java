package com.dreamfirestudios.bluefusioncustommessages.Event;

import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class BlueFusionCustomMessagesResetConfigEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public BlueFusionCustomMessagesResetConfigEvent(){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, coreConfig -> {
            if(coreConfig.systemEnabled) return;
            Bukkit.getScheduler().runTask(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), () -> {Bukkit.getPluginManager().callEvent(this);});
        });
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    public HandlerList getHandlers() {
        return handlers;
    }
}
