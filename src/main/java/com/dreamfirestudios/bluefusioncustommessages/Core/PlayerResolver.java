/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */
package com.dreamfirestudios.bluefusioncustommessages.Core;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


import java.util.*;
import java.util.stream.Collectors;


/** Resolution helpers for online players. */
public final class PlayerResolver {
    private PlayerResolver() { }


    public static Optional<Player> byName(final String name) {
        if (name == null) return Optional.empty();
        final Player p = Bukkit.getPlayerExact(name);
        return Optional.ofNullable(p);
    }


    public static Optional<Player> byUuid(final UUID id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(Bukkit.getPlayer(id));
    }


    public static List<Player> onlineWithPermission(final String node) {
        if (node == null || node.isBlank()) return List.of();
        return Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission(node)).collect(Collectors.toList());
    }
}