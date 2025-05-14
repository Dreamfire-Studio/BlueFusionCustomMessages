package com.dreamfirestudios.bluefusioncustommessages.SmartInvs;

import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.dreamCore.DreamfireSmartInvs.SmartInventory;
import com.dreamfirestudios.dreamCore.DreamfireSmartInvs.content.InventoryContents;
import com.dreamfirestudios.dreamCore.DreamfireSmartInvs.content.InventoryProvider;
import com.dreamfirestudios.bluefusioncustommessages.API.BlueFusionCustomMessagesAPI;
import com.dreamfirestudios.bluefusioncustommessages.Enum.InventoryItems;
import com.dreamfirestudios.bluefusioncustommessages.Enum.Permissions;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesConfig;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesPermissions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlueFusionCustomMessagesCoreMenu implements InventoryProvider {
    private final SmartInventory smartInventory;

    public BlueFusionCustomMessagesCoreMenu(Player... players){
        smartInventory = SmartInventory.builder()
                .id("BlueFusionCustomMessagesCoreMenu")
                .provider(this)
                .size(1, 9)
                .title(NamedTextColor.RED + "Admin Menu")
                .build();
        for(var player : players) smartInventory.open(player);
    }

    public CompletableFuture<Void> init(Player player, InventoryContents inventoryContents) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class, craftLegendsCoreConfig -> {
            BlueFusionCustomMessagesSmartInvsItems.InventoryItem(player, InventoryItems.BlankTile, clickableItem -> {
                inventoryContents.fillRow(0, clickableItem);
            }, this::BlankTileClick);

            BlueFusionCustomMessagesSmartInvsItems.InventoryItemWithFeedback(player, InventoryItems.SystemEnabled,
                    itemStack -> {
                        var itemMeta = itemStack.getItemMeta();
                        var lore = craftLegendsCoreConfig.systemEnabled
                                ? List.of(Component.text(NamedTextColor.WHITE + "Currently: " + NamedTextColor.GREEN + "ENABLED"))
                                : List.of(Component.text(NamedTextColor.WHITE + "Currently: " + NamedTextColor.RED + "DISABLED"));
                        itemMeta.lore(lore);
                        itemStack.setItemMeta(itemMeta);
                        return itemStack;
                    },
                    clickableItem -> {
                        inventoryContents.set(0, 2, clickableItem);
                    }, this::SystemEnabledClick);

            BlueFusionCustomMessagesSmartInvsItems.InventoryItem(player, InventoryItems.ReloadConfigs, clickableItem -> {
                inventoryContents.set(0, 4, clickableItem);
            }, this::ReloadConfigsClick);

            BlueFusionCustomMessagesSmartInvsItems.InventoryItem(player, InventoryItems.ResetConfigs, clickableItem -> {
                inventoryContents.set(0, 6, clickableItem);
            }, this::ResetConfigsClick);

            future.complete(null);
        });
        return future;
    }

    private void BlankTileClick(Player player, InventoryClickEvent inventoryClickEvent){
        inventoryClickEvent.setCancelled(false);
    }

    private void SystemEnabledClick(Player player, InventoryClickEvent inventoryClickEvent){
        BlueFusionCustomMessagesPermissions.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesPermissions.class, craftLegendsCorePermissions -> {
            if(!craftLegendsCorePermissions.DoesPlayerHavePermission(Permissions.EnableSystem, player, true)) return;
            BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesEnableSystem(dreamCoreTestTemplateConfig -> {});
            smartInventory.open(player);
        });
    }

    private void ReloadConfigsClick(Player player, InventoryClickEvent inventoryClickEvent) {
        BlueFusionCustomMessagesPermissions.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesPermissions.class, craftLegendsCorePermissions -> {
            if (!craftLegendsCorePermissions.DoesPlayerHavePermission(Permissions.ReloadConfigs, player, true)) return;
            BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesReloadConfigs();
            smartInventory.open(player);
        });
    }

    private void ResetConfigsClick(Player player, InventoryClickEvent inventoryClickEvent){
        BlueFusionCustomMessagesPermissions.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesPermissions.class, craftLegendsCorePermissions -> {
            if(!craftLegendsCorePermissions.DoesPlayerHavePermission(Permissions.ResetConfigs, player, true)) return;
            BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesResetConfigs();
            smartInventory.open(player);
        });
    }
}
