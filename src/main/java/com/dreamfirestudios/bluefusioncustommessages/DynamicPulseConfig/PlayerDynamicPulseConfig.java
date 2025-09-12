package com.dreamfirestudios.bluefusioncustommessages.DynamicPulseConfig;

import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.Enum.CustomMessageNames;
import com.dreamfirestudios.bluefusioncustommessages.MenuConfig.AdminMenuConfig;
import com.dreamfirestudios.bluefusioncustommessages.StaticEnumPulseConfig.CustomMessageNamesConfig;
import com.dreamfirestudios.dreamconfig.Abstract.DynamicPulseConfig;
import com.dreamfirestudios.dreamconfig.Interface.IDreamConfig;
import com.dreamfirestudios.dreamconfig.SaveableObjects.SaveableHashmap;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageFormatter;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerDynamicPulseConfig extends DynamicPulseConfig<PlayerDynamicPulseConfig> {
    public static void GetPlayerDynamicPulseConfig(Player player, Consumer<PlayerDynamicPulseConfig> response){
        PlayerDynamicPulseConfig.GetConfig(BlueFusionCustomMessages.GetInstance(), PlayerDynamicPulseConfig.class, player.getUniqueId().toString(),
                false, playerDynamicPulseConfig -> {
                    BlueFusionCustomMessages.PlayerDynamicPulseConfigs.put(player.getUniqueId(), playerDynamicPulseConfig);
                    response.accept(playerDynamicPulseConfig);
                });
    }

    public static void GetCustomMessageNames(Player player, CustomMessageNames customMessageNames, Consumer<Component> response){
        GetPlayerDynamicPulseConfig(player, playerDynamicPulseConfig -> {
            CustomMessageNamesConfig.GetCustomMessageNames(customMessageNames, defaultCustomMessageNames ->{
                var customMessage = playerDynamicPulseConfig.customMessages.getHashMap().getOrDefault(customMessageNames, defaultCustomMessageNames);
                response.accept(DreamMessageFormatter.format(customMessage, player, DreamMessageSettings.all()));
            });
        });
    }

    public static void SetCustomMessageNames(Player player, CustomMessageNames customMessageNames, String value, Consumer<PlayerDynamicPulseConfig> response){
        GetPlayerDynamicPulseConfig(player, playerDynamicPulseConfig -> {
            playerDynamicPulseConfig.customMessages.getHashMap().put(customMessageNames, value);
            playerDynamicPulseConfig.SaveDreamConfig(BlueFusionCustomMessages.GetInstance(), response);
        });
    }

    public static void ResetCustomMessageNames(Player player, CustomMessageNames customMessageNames, Consumer<PlayerDynamicPulseConfig> response){
        GetPlayerDynamicPulseConfig(player, playerDynamicPulseConfig -> {
            CustomMessageNamesConfig.GetCustomMessageNames(customMessageNames, defaultCustomMessageNames ->{
                playerDynamicPulseConfig.customMessages.getHashMap().put(customMessageNames, defaultCustomMessageNames);
                playerDynamicPulseConfig.SaveDreamConfig(BlueFusionCustomMessages.GetInstance(), response);
            });
        });
    }

    public final SaveableHashmap<CustomMessageNames, String> customMessages = new SaveableHashmap<>(CustomMessageNames.class, String.class);

    public PlayerDynamicPulseConfig() {
        super();
    }

    public PlayerDynamicPulseConfig(String playerId) {
        super(playerId);
    }

    public PlayerDynamicPulseConfig(UUID uuid) {
        super(Objects.requireNonNull(uuid).toString());
    }

    public PlayerDynamicPulseConfig(Player player) {
        super(Objects.requireNonNull(player).getUniqueId().toString());
    }

    @Override
    public void FirstLoadConfig() {
        CreateDefaultValues();
    }

    @Override
    public void AfterLoadConfig() {
        CreateDefaultValues();
    }

    @Override
    public void BeforeSaveConfig() {
        CreateDefaultValues();
    }

    @Override
    public <T extends IDreamConfig> void SaveDreamConfig(JavaPlugin javaPlugin, Consumer<T> onSuccessSave) {
        BlueFusionCustomMessages.PlayerDynamicPulseConfigs.put(UUID.fromString(documentID()), this);
        super.SaveDreamConfig(javaPlugin, onSuccessSave);
    }

    private void CreateDefaultValues(){
        for(CustomMessageNames customMessageNames : CustomMessageNames.values()){
            if(!customMessages.getHashMap().containsKey(customMessageNames)) {
                CustomMessageNamesConfig.GetCustomMessageNames(customMessageNames, defaultMessageNames->{
                    customMessages.getHashMap().put(customMessageNames, defaultMessageNames);
                });
            }
        }
    }
}
