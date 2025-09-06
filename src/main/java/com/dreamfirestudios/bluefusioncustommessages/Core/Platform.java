/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */
package com.dreamfirestudios.bluefusioncustommessages.Core;


import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;


import java.util.Objects;


/** Utilities for version gating and optional dependency checks. */
public final class Platform {
    private Platform() { }


    public static String bukkitVersion() { return Bukkit.getVersion(); }


    public static boolean hasPlugin(final String name) {
        return Bukkit.getPluginManager().getPlugin(Objects.requireNonNull(name, "name")) != null;
    }


    /** naive semver compare: "1.20.1" */
    public static boolean atLeast(final String required) {
        Objects.requireNonNull(required, "required");
        String ver = Bukkit.getBukkitVersion(); // e.g., 1.20.6-R0.1-SNAPSHOT
        int dash = ver.indexOf('-');
        if (dash != -1) ver = ver.substring(0, dash);
        return compare(ver, required) >= 0;
    }


    private static int compare(final String a, final String b) {
        final String[] as = a.split("\\.");
        final String[] bs = b.split("\\.");
        for (int i = 0; i < Math.max(as.length, bs.length); i++) {
            int ai = i < as.length ? parse(as[i]) : 0;
            int bi = i < bs.length ? parse(bs[i]) : 0;
            if (ai != bi) return Integer.compare(ai, bi);
        }
        return 0;
    }


    private static int parse(final String s) { try { return Integer.parseInt(s); } catch (Exception e) { return 0; } }
}