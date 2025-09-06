/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */

package com.dreamfirestudios.bluefusioncustommessages;

import com.dreamfirestudios.dreamcommand.DreamCommand;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.dreamcore.DreamJava.DreamClassAPI;
import com.dreamfirestudios.dreamcore.DreamVariable.DreamEnumVariableTest;
import com.dreamfirestudios.bluefusioncustommessages.API.BlueFusionCustomMessagesAPI;
import com.dreamfirestudios.bluefusioncustommessages.Core.Services;
import com.dreamfirestudios.bluefusioncustommessages.Core.VersionChecks;
import com.dreamfirestudios.bluefusioncustommessages.Enum.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * Main plugin entry.
 */
public class BlueFusionCustomMessages extends JavaPlugin {
    private static BlueFusionCustomMessages BlueFusionCustomMessages;

    /**
     * Global accessor for the plugin instance.
     * @return active plugin
     */
    public static BlueFusionCustomMessages GetBlueFusionCustomMessages(){
        return Objects.requireNonNull(BlueFusionCustomMessages, "plugin not initialized yet");
    }

    /** Bootstrap on enable. */
    @Override
    public void onEnable() {
        BlueFusionCustomMessages = this;
        Services.bootstrap(this);
        VersionChecks.logPlatformInfo(getLogger());
        DreamClassAPI.RegisterPulseVariableTest(this, new DreamEnumVariableTest<>(BlueFusionCustomMessagesFeatureFlagKey.class));
        DreamClassAPI.RegisterPulseVariableTest(this, new DreamEnumVariableTest<>(BlueFusionCustomMessagesInventoryItems.class));
        DreamClassAPI.RegisterPulseVariableTest(this, new DreamEnumVariableTest<>(BlueFusionCustomMessagesMessages.class));
        DreamClassAPI.RegisterPulseVariableTest(this, new DreamEnumVariableTest<>(BlueFusionCustomMessagesPermissionLevel.class));
        DreamClassAPI.RegisterPulseVariableTest(this, new DreamEnumVariableTest<>(BlueFusionCustomMessagesPermissions.class));
        BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesReloadConfigs(DreamMessageSettings.all());
        DreamClassAPI.RegisterClasses(this);
        DreamCommand.RegisterRaw(this);
    }
}
