package com.dreamfirestudios.bluefusioncustommessages.PulseConfig;

import com.dreamfirestudios.dreamConfig.Abstract.StaticPulseConfig;
import com.dreamfirestudios.dreamConfig.Interface.StorageComment;
import com.dreamfirestudios.dreamCore.DreamfireJava.PulseAutoRegister;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.Event.BlueFusionCustomMessagesEnableSystemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

@PulseAutoRegister
public class BlueFusionCustomMessagesConfig extends StaticPulseConfig<BlueFusionCustomMessagesConfig> {
    @Override
    public JavaPlugin mainClass() {return BlueFusionCustomMessages.GetBlueFusionCustomMessages();}
    @StorageComment("WARNING: SYSTEM WONT RUN IF FALSE!")
    public boolean systemEnabled = true;
    @StorageComment("Display debugs in the console logs for changes in this config!")
    public boolean debugConfig = false;

    public String firstJoinMessage = "Welcome #ffffff%player_name% to %server_name% for the first time!";
    public String joinMessage = "Welcome #ffffff%player_name% to %server_name%!";
    public String leaveMessage = "Goodbye #ffffff%player_name%!";
    public String kickMessage = "You have been kicked from the server %server_name%!";
    public String banMessage = "You have been kicked from the server %server_name%!";

    public void ToggleSystemEnabled(Consumer<BlueFusionCustomMessagesConfig> onSuccess, boolean newState){
        new BlueFusionCustomMessagesEnableSystemEvent(systemEnabled, newState);
        systemEnabled = newState;
        SaveDreamConfig(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), onSuccess);
    }
}
