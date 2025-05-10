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
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessagesConfig.class, coreConfig -> {
            coreConfig.systemEnabled = state;
            coreConfig.SaveConfig(onSuccess, Throwable::printStackTrace);
        }, Throwable::printStackTrace);
    }

    public static void BlueFusionCustomMessagesEnableSystem(Consumer<BlueFusionCustomMessagesConfig> onSuccess){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessagesConfig.class, coreConfig -> {
            coreConfig.systemEnabled = !coreConfig.systemEnabled;
            coreConfig.SaveConfig(onSuccess, Throwable::printStackTrace);
        }, Throwable::printStackTrace);
    }

    public static void BlueFusionCustomMessagesSerializeItem(Consumer<BlueFusionCustomMessagesSerilizableItems> onSuccess, String id, ItemStack itemStack){
        BlueFusionCustomMessagesSerilizableItems.ReturnStaticAsync(BlueFusionCustomMessagesSerilizableItems.class, coreConfig -> {
            coreConfig.AddItemStack(id, itemStack);
            coreConfig.SaveConfig(onSuccess, Throwable::printStackTrace);
        }, Throwable::printStackTrace);
    }

    public static void BlueFusionCustomMessagesResetConfigs(){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync( BlueFusionCustomMessagesConfig.class, coreConfig -> {
            if(!coreConfig.systemEnabled) return;
            DreamConfig.GetDreamfireConfig().RegisterStatic(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), true);
            new BlueFusionCustomMessagesResetConfigEvent();
        }, Throwable::printStackTrace);
    }

    public static void BlueFusionCustomMessagesReloadConfigs(){
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessagesConfig.class, coreConfig -> {
            if(!coreConfig.systemEnabled) return;
            DreamConfig.GetDreamfireConfig().RegisterStatic(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), true);
            new BlueFusionCustomMessagesReloadConfigEvent();
        }, Throwable::printStackTrace);
    }
}
