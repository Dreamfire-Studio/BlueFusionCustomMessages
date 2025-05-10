package com.dreamfirestudios.bluefusioncustommessages.Enum;

import com.dreamfirestudios.dreamCore.DreamfireChat.DreamfireMessage;
import com.dreamfirestudios.dreamCore.DreamfirePersistentData.DreamfirePersistentItemStack;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

public enum InventoryItems {
    BlankTile(" ",Material.BLACK_STAINED_GLASS_PANE, List.of(), List.of(), 0),
    SystemEnabled("#38b227System Enabled!", Material.GREEN_CONCRETE, List.of(), List.of(), 0),
    ResetConfigs("#38b227Reset Configs!", Material.GREEN_CONCRETE, List.of(), List.of(), 0),
    ReloadConfigs("#38b227Reload Configs!", Material.GREEN_CONCRETE, List.of(), List.of(), 0);

    public final String displayName;
    public final Material itemMaterial;
    public final List<String> itemLore;
    public final List<String> keys;
    public final int modelData;

    InventoryItems(String displayName, Material itemMaterial, List<String> itemLore, List<String> keys, int modelData){
        this.displayName = displayName;
        this.itemMaterial = itemMaterial;
        this.itemLore = itemLore;
        this.keys = keys;
        this.modelData = modelData;
    }

    public ItemStack ReturnItemStack(){
        var itemStack = new ItemStack(itemMaterial);
        var itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(DreamfireMessage.formatMessage(displayName));
        var lore = new ArrayList<Component>();
        for(var x : itemLore) lore.add(DreamfireMessage.formatMessage(x));
        itemMeta.lore(lore);
        itemMeta.setCustomModelData(modelData);
        itemStack.setItemMeta(itemMeta);
        for(var key : keys) DreamfirePersistentItemStack.Add(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), itemStack, PersistentDataType.STRING, key, key);
        return itemStack;
    }
}
