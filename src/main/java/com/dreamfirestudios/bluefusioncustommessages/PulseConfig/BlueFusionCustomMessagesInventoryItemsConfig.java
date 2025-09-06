/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */

package com.dreamfirestudios.bluefusioncustommessages.PulseConfig;

import com.dreamfirestudios.dreamconfig.Abstract.StaticEnumPulseConfig;
import com.dreamfirestudios.dreamconfig.Interface.ConfigVersion;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageFormatter;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import com.dreamfirestudios.dreamcore.DreamPersistentData.DreamPersistentItemStack;
import com.dreamfirestudios.bluefusioncustommessages.Enum.BlueFusionCustomMessagesInventoryItems;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Static enum-based configuration for predefined {@link ItemStack} templates.
 */
@PulseAutoRegister
@ConfigVersion(1)
public final class BlueFusionCustomMessagesInventoryItemsConfig
        extends StaticEnumPulseConfig<BlueFusionCustomMessagesInventoryItemsConfig, BlueFusionCustomMessagesInventoryItems, ItemStack> {

    @Override public JavaPlugin mainClass() { return BlueFusionCustomMessages.GetBlueFusionCustomMessages(); }
    @Override protected Class<BlueFusionCustomMessagesInventoryItems> getKeyClass() { return BlueFusionCustomMessagesInventoryItems.class; }
    @Override protected Class<ItemStack> getValueClass() { return ItemStack.class; }
    @Override public boolean useSubFolder() { return false; }

    @Override
    protected ItemStack getDefaultValueFor(final BlueFusionCustomMessagesInventoryItems key) {
        Objects.requireNonNull(key, "key");
        final var settings = DreamMessageSettings.all();
        final var is = new ItemStack(key.itemMaterial);
        final var meta = is.getItemMeta();

        meta.displayName(DreamMessageFormatter.format(Objects.requireNonNull(key.displayName, "displayName"), settings));

        final var lore = new ArrayList<Component>();
        for (final var l : key.itemLore) {
            lore.add(DreamMessageFormatter.format(Objects.requireNonNull(l, "itemLore line"), settings));
        }
        meta.lore(lore);
        meta.setCustomModelData(key.modelData);
        is.setItemMeta(meta);

        for (final var k : key.keys) {
            DreamPersistentItemStack.Add(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), is, PersistentDataType.STRING,
                    Objects.requireNonNull(k, "persistent key"), k);
        }
        return is;
    }
}