package com.dreamfirestudios.bluefusioncustommessages.StaticEnumPulseConfig;

import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.dreamconfig.Abstract.StaticEnumPulseConfig;
import com.dreamfirestudios.dreamconfig.Interface.ConfigVersion;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Static config binding {@link com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_InventoryItems} to {@link ItemStack}.
 *
 * <p>Provides default values via {@link com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_InventoryItems#ReturnItemStack}.
 * This allows menu items and inventory slots to be consistently defined
 * and reused across menus.</p>
 */
@PulseAutoRegister
@ConfigVersion(1)
public class InventoryItemsConfig extends StaticEnumPulseConfig<InventoryItemsConfig, com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_InventoryItems, ItemStack> {

    /**
     * Asynchronously loads the InventoryItemsConfig.
     *
     * @param onSuccess callback that receives the loaded config (never null)
     */
    public static void GetInventoryItemsConfig(Consumer<InventoryItemsConfig> onSuccess){
        Objects.requireNonNull(onSuccess, "onSuccess");
        InventoryItemsConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetInstance(), InventoryItemsConfig.class, onSuccess);
    }

    /** @return the plugin main class */
    @Override public JavaPlugin mainClass() { return BlueFusionCustomMessages.GetInstance(); }

    /** @return the enum key type ({@link com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_InventoryItems}) */
    @Override protected Class<com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_InventoryItems> getKeyClass() { return com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_InventoryItems.class; }

    /** @return the value type ({@link ItemStack}) */
    @Override protected Class<ItemStack> getValueClass() { return ItemStack.class; }

    /**
     * Provides the default value for a given inventory item key.
     *
     * @param key the enum key (never null)
     * @return a fresh {@link ItemStack} clone for the key
     */
    @Override
    protected ItemStack getDefaultValueFor(final com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_InventoryItems key) {
        Objects.requireNonNull(key, "key");
        return key.ReturnItemStack(DreamMessageSettings.all(), null);
    }

    /** @return false, config does not use subfolders */
    @Override public boolean useSubFolder() { return false; }
}