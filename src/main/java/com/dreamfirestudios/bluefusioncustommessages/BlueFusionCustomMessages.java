package com.dreamfirestudios.bluefusioncustommessages;

import com.dreamfirestudios.dreamCommand.DreamCommand;
import com.dreamfirestudios.dreamCore.DreamfireJava.DreamfireClassAPI;
import com.dreamfirestudios.bluefusioncustommessages.API.BlueFusionCustomMessagesAPI;
import com.dreamfirestudios.bluefusioncustommessages.DreamfireVariableTest.BlueFusionCustomMessagesInventoryItemsVariableTest;
import com.dreamfirestudios.bluefusioncustommessages.DreamfireVariableTest.BlueFusionCustomMessagesMessagesVariableTest;
import com.dreamfirestudios.bluefusioncustommessages.DreamfireVariableTest.BlueFusionCustomMessagesPermissionsVariableTest;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlueFusionCustomMessages extends JavaPlugin {
    private static BlueFusionCustomMessages craftLegendsCore;
    public static BlueFusionCustomMessages GetBlueFusionCustomMessages(){return craftLegendsCore;}

    @Override
    public void onEnable() {
        craftLegendsCore = this;
        DreamfireClassAPI.RegisterPulseVariableTest(craftLegendsCore, new BlueFusionCustomMessagesMessagesVariableTest());
        DreamfireClassAPI.RegisterPulseVariableTest(craftLegendsCore, new BlueFusionCustomMessagesInventoryItemsVariableTest());
        DreamfireClassAPI.RegisterPulseVariableTest(craftLegendsCore, new BlueFusionCustomMessagesPermissionsVariableTest());
        BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesReloadConfigs();
        DreamfireClassAPI.RegisterClasses(this);
        DreamCommand.RegisterRaw(this);
    }
}
