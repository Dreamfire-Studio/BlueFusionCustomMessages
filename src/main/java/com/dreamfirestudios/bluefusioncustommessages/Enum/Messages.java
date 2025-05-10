package com.dreamfirestudios.bluefusioncustommessages.Enum;

import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;

public enum Messages {
    ConsoleEnabledSystem(String.format("#7fff36%s has been enabled!", BlueFusionCustomMessages.GetBlueFusionCustomMessages().getName())),
    ConsoleDisableSystem(String.format("#7fff36%s has been disabled!", BlueFusionCustomMessages.GetBlueFusionCustomMessages().getName())),
    PlayerReloadedConfig(String.format("#7fff36%s configs have been reloaded!", BlueFusionCustomMessages.GetBlueFusionCustomMessages().getName())),
    PlayerSerlizedItem("#7fff36You have added a item to the serialized items: #ffffff%s"),
    PlayerResetConfig(String.format("#7fff36%s configs have been reset!", BlueFusionCustomMessages.GetBlueFusionCustomMessages().getName())),
    SystemIsntEnabled(String.format("#7fff36%s Isn't Enabled!", BlueFusionCustomMessages.GetBlueFusionCustomMessages().getName()));

    public final String message;
    Messages(String message){
        this.message = message;
    }
}
