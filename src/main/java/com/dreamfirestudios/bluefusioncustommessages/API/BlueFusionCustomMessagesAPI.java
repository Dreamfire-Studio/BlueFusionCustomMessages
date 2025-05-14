package com.dreamfirestudios.bluefusioncustommessages.API;

import com.dreamfirestudios.dreamConfig.DreamConfig;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.Event.BlueFusionCustomMessagesReloadConfigEvent;
import com.dreamfirestudios.bluefusioncustommessages.Event.BlueFusionCustomMessagesResetConfigEvent;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesConfig;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesSerilizableItems;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class BlueFusionCustomMessagesAPI {
    public static void BlueFusionCustomMessagesEnableSystem(Consumer<BlueFusionCustomMessagesConfig> onSuccess, boolean state){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, coreConfig -> {
            coreConfig.systemEnabled = state;
            coreConfig.SaveDreamConfig(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), onSuccess);
        });
    }

    public static void BlueFusionCustomMessagesEnableSystem(Consumer<BlueFusionCustomMessagesConfig> onSuccess){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, coreConfig -> {
            coreConfig.systemEnabled = !coreConfig.systemEnabled;
            coreConfig.SaveDreamConfig(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), onSuccess);
        });
    }

    public static void BlueFusionCustomMessagesSerializeItem(Consumer<BlueFusionCustomMessagesSerilizableItems> onSuccess, String id, ItemStack itemStack){
        BlueFusionCustomMessagesSerilizableItems.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesSerilizableItems.class, coreConfig -> {
            coreConfig.AddItemStack(id, itemStack);
            coreConfig.SaveDreamConfig(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), onSuccess);
        });
    }

    public static void BlueFusionCustomMessagesResetConfigs(){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, coreConfig -> {
            if(!coreConfig.systemEnabled) return;
            DreamConfig.GetDreamConfig().RegisterStatic(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), true);
            new BlueFusionCustomMessagesResetConfigEvent();
        });
    }

    public static void BlueFusionCustomMessagesReloadConfigs(){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, coreConfig -> {
            if(!coreConfig.systemEnabled) return;
            DreamConfig.GetDreamConfig().RegisterStatic(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), true);
            new BlueFusionCustomMessagesReloadConfigEvent();
        });
    }
}
