package com.dreamfirestudios.bluefusioncustommessages.IDreamPlaceholder;

import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import com.dreamfirestudios.dreamcore.DreamPlaceholder.IDreamPlaceholder;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@PulseAutoRegister
public class ReasonPlaceholder implements IDreamPlaceholder {
    @Override
    public @NotNull String key() {
        return "reason";
    }

    @Override
    public @NotNull String resolve(@Nullable OfflinePlayer player, @NotNull String[] args) {
        if (player == null) return "";
        return BlueFusionCustomMessages.ReasonContextMap.getOrDefault(player.getUniqueId(), "No Reason Given!");
    }
}
