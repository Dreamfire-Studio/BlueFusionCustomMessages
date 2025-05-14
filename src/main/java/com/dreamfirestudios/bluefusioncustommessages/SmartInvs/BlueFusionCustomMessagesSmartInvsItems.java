package com.dreamfirestudios.bluefusioncustommessages.SmartInvs;

import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.dreamCore.DreamfireSmartInvs.ClickableItem;
import com.dreamfirestudios.bluefusioncustommessages.Enum.InventoryItems;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesInventoryItems;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesSerilizableItems;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class BlueFusionCustomMessagesSmartInvsItems {
    public static void SerializedItem(Player player, String itemID, Function<ItemStack, ItemStack> testFunction, Consumer<ClickableItem> clickableItemConsumer, BiConsumer<Player, InventoryClickEvent> inventoryClickEventConsumer) {
        BlueFusionCustomMessagesSerilizableItems.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesSerilizableItems.class, craftLegendsCoreSerilizableItems -> {
            var itemStack = testFunction.apply(craftLegendsCoreSerilizableItems.GetItemStack(itemID).clone());
            clickableItemConsumer.accept(ClickableItem.of(itemStack, event -> inventoryClickEventConsumer.accept(player, event)));
        });
    }

    public static void InventoryItemWithFeedback(Player player, InventoryItems inventoryItems, Function<ItemStack, ItemStack> testFunction, Consumer<ClickableItem> clickableItemConsumer, BiConsumer<Player, InventoryClickEvent> inventoryClickEventConsumer) {
        BlueFusionCustomMessagesInventoryItems.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesInventoryItems.class, craftLegendsCoreInventoryItems -> {
            var itemStack = testFunction.apply(craftLegendsCoreInventoryItems.GetValue(inventoryItems).clone());
            clickableItemConsumer.accept(ClickableItem.of(itemStack, event -> inventoryClickEventConsumer.accept(player, event)));
        });
    }

    public static void InventoryItem(Player player, InventoryItems inventoryItems, Consumer<ClickableItem> clickableItemConsumer, BiConsumer<Player, InventoryClickEvent> inventoryClickEventConsumer) {
        BlueFusionCustomMessagesInventoryItems.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesInventoryItems.class, craftLegendsCoreInventoryItems -> {
            var itemStack = craftLegendsCoreInventoryItems.GetValue(inventoryItems).clone();
            clickableItemConsumer.accept(ClickableItem.of(itemStack, event -> inventoryClickEventConsumer.accept(player, event)));
        });
    }

    public static void SystemEnabled(Player player, boolean isEnabled, Consumer<ClickableItem> clickableItemConsumer, BiConsumer<Player, InventoryClickEvent> inventoryClickEventConsumer){
        BlueFusionCustomMessagesInventoryItems.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesInventoryItems.class, craftLegendsCoreInventoryItems -> {
            var itemStack = craftLegendsCoreInventoryItems.GetValue(InventoryItems.SystemEnabled).clone();
            clickableItemConsumer.accept(ClickableItem.of(itemStack, event -> inventoryClickEventConsumer.accept(player, event)));
        });
    }
}
