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

    public void ToggleSystemEnabled(Consumer<BlueFusionCustomMessagesConfig> onSuccess, boolean newState){
        new BlueFusionCustomMessagesEnableSystemEvent(systemEnabled, newState);
        systemEnabled = newState;
        SaveConfig(onSuccess, Throwable::printStackTrace);
    }
}
