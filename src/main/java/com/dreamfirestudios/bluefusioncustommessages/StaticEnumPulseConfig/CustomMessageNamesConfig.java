package com.dreamfirestudios.bluefusioncustommessages.StaticEnumPulseConfig;

import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_InventoryItems;
import com.dreamfirestudios.bluefusioncustommessages.Enum.CustomMessageNames;
import com.dreamfirestudios.dreamconfig.Abstract.StaticEnumPulseConfig;
import com.dreamfirestudios.dreamconfig.Interface.ConfigVersion;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.function.Consumer;

@PulseAutoRegister
@ConfigVersion(1)
public class CustomMessageNamesConfig extends StaticEnumPulseConfig<CustomMessageNamesConfig, CustomMessageNames, String> {
    public static void GetCustomMessageNamesConfig(Consumer<CustomMessageNamesConfig> onSuccess){
        Objects.requireNonNull(onSuccess, "onSuccess");
        CustomMessageNamesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetInstance(), CustomMessageNamesConfig.class, onSuccess);
    }

    public static void GetCustomMessageNames(CustomMessageNames customMessageNames, Consumer<String> response){
        CustomMessageNamesConfig.GetCustomMessageNamesConfig(customMessageNamesConfig -> {
            response.accept(customMessageNamesConfig.saveableHashmap.getHashMap().getOrDefault(customMessageNames, customMessageNames.getDefaultMessage()));
        });
    }

    public static void SetCustomMessage(CustomMessageNames customMessageNames, String newValue, Consumer<CustomMessageNamesConfig> response){
        CustomMessageNamesConfig.GetCustomMessageNamesConfig(customMessageNamesConfig -> {
           customMessageNamesConfig.saveableHashmap.getHashMap().put(customMessageNames, newValue);
           customMessageNamesConfig.SaveDreamConfig(BlueFusionCustomMessages.GetInstance(), response);
        });
    }

    public static void ResetCustomMessage(CustomMessageNames customMessageNames, Consumer<CustomMessageNamesConfig> response){
        CustomMessageNamesConfig.GetCustomMessageNamesConfig(customMessageNamesConfig -> {
            customMessageNamesConfig.saveableHashmap.getHashMap().put(customMessageNames, customMessageNames.getDefaultMessage());
            customMessageNamesConfig.SaveDreamConfig(BlueFusionCustomMessages.GetInstance(), response);
        });
    }

    @Override public JavaPlugin mainClass() { return BlueFusionCustomMessages.GetInstance(); }
    @Override protected Class<CustomMessageNames> getKeyClass() { return CustomMessageNames.class; }
    @Override protected Class<String> getValueClass() { return String.class; }
    @Override protected String getDefaultValueFor(final CustomMessageNames key) {return Objects.requireNonNull(key, "key").getDefaultMessage();}
    @Override public boolean useSubFolder() { return false; }
}
