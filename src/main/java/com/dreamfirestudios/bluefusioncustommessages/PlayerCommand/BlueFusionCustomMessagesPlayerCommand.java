package com.dreamfirestudios.bluefusioncustommessages.PlayerCommand;

import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.dreamCommand.Enums.TabType;
import com.dreamfirestudios.dreamCommand.Interface.PCAutoTab;
import com.dreamfirestudios.dreamCommand.Interface.PCMethod;
import com.dreamfirestudios.dreamCommand.Interface.PCSignature;
import com.dreamfirestudios.dreamCommand.Interface.PCTab;
import com.dreamfirestudios.dreamCommand.PlayerCommand;
import com.dreamfirestudios.dreamCore.DreamfireJava.PulseAutoRegister;
import com.dreamfirestudios.bluefusioncustommessages.API.BlueFusionCustomMessagesAPI;
import com.dreamfirestudios.bluefusioncustommessages.Enum.Messages;
import com.dreamfirestudios.bluefusioncustommessages.Enum.Permissions;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesConfig;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesMessages;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesPermissions;
import com.dreamfirestudios.bluefusioncustommessages.SmartInvs.BlueFusionCustomMessagesCoreMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.UUID;

@PulseAutoRegister
public class BlueFusionCustomMessagesPlayerCommand extends PlayerCommand {
    public BlueFusionCustomMessagesPlayerCommand() {
        super("bluefusioncustommessages", false);
    }

    @Override
    public void NoMethodFound(Player player, String s, String[] strings) {}
    @Override
    public String helpMenuPrefix(Player player) {
        return "";
    }
    @Override
    public LinkedHashMap<String, String> helpMenuFormat(Player player, LinkedHashMap<String, String> linkedHashMap) {return new LinkedHashMap<>();}
    @Override
    public String helpMenuSuffix(Player player) {
        return "";
    }

    @PCMethod
    @PCSignature({})
    public void TimeStealCoreMethod(UUID playerID){
        var player = Bukkit.getPlayer(playerID);
        if(player == null) return;
        BlueFusionCustomMessagesPermissions.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesPermissions.class, craftLegendsCorePermissions -> {
            if(!craftLegendsCorePermissions.DoesPlayerHavePermission(Permissions.AdminConsole, player, true)) return;
            new BlueFusionCustomMessagesCoreMenu(player);
        });
    }

    @PCMethod
    @PCSignature({"enable"})
    @PCAutoTab(pos = 1)
    public void TimeStealCoreEnableMethod(UUID playerID, boolean state){
        var player = Bukkit.getPlayer(playerID);
        if(player == null) return;
        BlueFusionCustomMessagesPermissions.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesPermissions.class, craftLegendsCorePermissions -> {
            if(!craftLegendsCorePermissions.DoesPlayerHavePermission(Permissions.EnableSystem, player, true)) return;
            BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesEnableSystem(dreamCoreTestTemplateConfig -> {}, state);
            BlueFusionCustomMessagesMessages.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesMessages.class, craftLegendsCoreMessages -> {
                craftLegendsCoreMessages.SendMessageToPlayer(state ? Messages.ConsoleEnabledSystem : Messages.ConsoleDisableSystem, player);
            });
        });
    }

    @PCMethod
    @PCSignature({"serialize"})
    @PCTab(pos = 1, type = TabType.PureData, data = "ITEM ID")
    public void TimeStealCoreSerializeItemMethod(UUID playerUUID, String itemName){
        var player = Bukkit.getPlayer(playerUUID);
        if(player == null) return;
        BlueFusionCustomMessagesPermissions.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesPermissions.class, craftLegendsCorePermissions -> {
            if(!craftLegendsCorePermissions.DoesPlayerHavePermission(Permissions.SerializeItem, player, true)) return;
            BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesSerializeItem(dreamCoreTestTemplateSerilizableItems -> {}, itemName, player.getInventory().getItemInMainHand());
            BlueFusionCustomMessagesMessages.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesMessages.class, craftLegendsCoreMessages -> {
                craftLegendsCoreMessages.SendMessageToPlayer(Messages.PlayerSerlizedItem, player, itemName);
            });
        });
    }

    @PCMethod
    @PCSignature({"configs", "reset"})
    public void TimeStealCoreConfigsResetMethod(UUID playerID){
        var player = Bukkit.getPlayer(playerID);
        if(player == null) return;
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, craftLegendsCoreConfig -> {
            if(!craftLegendsCoreConfig.systemEnabled) return;
            BlueFusionCustomMessagesPermissions.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesPermissions.class, craftLegendsCorePermissions -> {
                if(!craftLegendsCorePermissions.DoesPlayerHavePermission(Permissions.ResetConfigs, player, true)) return;
                BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesResetConfigs();
                BlueFusionCustomMessagesMessages.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesMessages.class, craftLegendsCoreMessages -> {
                    craftLegendsCoreMessages.SendMessageToPlayer(Messages.PlayerResetConfig, player);
                });
            });
        });
    }

    @PCMethod
    @PCSignature({"configs", "reload"})
    public void TimeStealCoreReloadMethod(UUID playerID){
        var player = Bukkit.getPlayer(playerID);
        if(player == null) return;
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, craftLegendsCoreConfig -> {
            if(!craftLegendsCoreConfig.systemEnabled) return;
            BlueFusionCustomMessagesPermissions.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesPermissions.class, craftLegendsCorePermissions -> {
                if(!craftLegendsCorePermissions.DoesPlayerHavePermission(Permissions.ReloadConfigs, player, true)) return;
                BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesReloadConfigs();
                BlueFusionCustomMessagesMessages.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesMessages.class, craftLegendsCoreMessages -> {
                    craftLegendsCoreMessages.SendMessageToPlayer(Messages.PlayerReloadedConfig, player);
                });
            });
        });
    }
}
