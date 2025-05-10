package com.dreamfirestudios.bluefusioncustommessages.PulseConfig;

import com.dreamfirestudios.dreamConfig.Abstract.StaticPulseConfig;
import com.dreamfirestudios.dreamConfig.SaveableObjects.SaveableHashmap;
import com.dreamfirestudios.dreamCore.DreamfireJava.PulseAutoRegister;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

@PulseAutoRegister
public class BlueFusionCustomMessagesSerilizableItems extends StaticPulseConfig<BlueFusionCustomMessagesSerilizableItems> {
    @Override
    public JavaPlugin mainClass() {return BlueFusionCustomMessages.GetBlueFusionCustomMessages();}

    public SaveableHashmap<String, ItemStack> itemStackSaveableHashmap = new SaveableHashmap<>(String.class, ItemStack.class);

    public void AddItemStack(String id, ItemStack itemStack){
        itemStackSaveableHashmap.getHashMap().put(id, itemStack);
    }

    public ItemStack GetItemStack(String id){
        return itemStackSaveableHashmap.getHashMap().getOrDefault(id, null);
    }
}
