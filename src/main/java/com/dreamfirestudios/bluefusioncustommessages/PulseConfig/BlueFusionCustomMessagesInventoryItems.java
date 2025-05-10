package com.dreamfirestudios.bluefusioncustommessages.PulseConfig;

import com.dreamfirestudios.dreamConfig.Abstract.StaticEnumPulseConfig;
import com.dreamfirestudios.dreamCore.DreamfireJava.PulseAutoRegister;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.Enum.InventoryItems;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

@PulseAutoRegister
public class BlueFusionCustomMessagesInventoryItems extends StaticEnumPulseConfig<BlueFusionCustomMessagesInventoryItems, InventoryItems, ItemStack> {
    @Override
    public JavaPlugin mainClass() {return BlueFusionCustomMessages.GetBlueFusionCustomMessages();}
    @Override
    protected Class<InventoryItems> getKeyClass () {return InventoryItems.class;}
    @Override
    protected Class<ItemStack> getValueClass () {return ItemStack.class;}
    @Override
    protected ItemStack getDefaultValueFor (InventoryItems craftLegendsCoreInventoryItem) {return craftLegendsCoreInventoryItem.ReturnItemStack();}
}
