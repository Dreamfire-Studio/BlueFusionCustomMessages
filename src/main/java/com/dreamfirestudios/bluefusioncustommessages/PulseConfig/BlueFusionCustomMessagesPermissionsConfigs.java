/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */

package com.dreamfirestudios.bluefusioncustommessages.PulseConfig;

import com.dreamfirestudios.dreamconfig.Abstract.StaticEnumPulseConfig;
import com.dreamfirestudios.dreamconfig.Interface.ConfigVersion;
import com.dreamfirestudios.dreamcore.DreamChat.DreamChat;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import com.dreamfirestudios.dreamcore.DreamLuckPerms.DreamLuckPerms;
import com.dreamfirestudios.bluefusioncustommessages.Core.ExpiringCache;
import com.dreamfirestudios.bluefusioncustommessages.Core.RateLimiter;
import com.dreamfirestudios.bluefusioncustommessages.Core.Services;
import com.dreamfirestudios.bluefusioncustommessages.Enum.BlueFusionCustomMessagesPermissionLevel;
import com.dreamfirestudios.bluefusioncustommessages.Enum.BlueFusionCustomMessagesPermissions;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.Util.PermissionStrings;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.util.Objects;

/**
 * Permission string resolution and permission checks with simple caching and rate limiting.
 */
@PulseAutoRegister
@ConfigVersion(1)
public final class BlueFusionCustomMessagesPermissionsConfigs
        extends StaticEnumPulseConfig<BlueFusionCustomMessagesPermissionsConfigs, BlueFusionCustomMessagesPermissions, String> {

    // Cache permission checks for a short window to reduce LP traffic.
    private final ExpiringCache<String, Boolean> permCache =
            Services.expiringCache("__proj_perm_cache", Duration.ofSeconds(5));

    // Rate limit "no permission" error messages per player+perm key.
    private final RateLimiter<String> denyLimiter =
            Services.rateLimiter("__proj_perm_deny", 2, Duration.ofSeconds(1));

    @Override public JavaPlugin mainClass() { return BlueFusionCustomMessages.GetBlueFusionCustomMessages(); }
    @Override protected Class<BlueFusionCustomMessagesPermissions> getKeyClass() { return BlueFusionCustomMessagesPermissions.class; }
    @Override protected Class<String> getValueClass() { return String.class; }

    @Override
    protected String getDefaultValueFor(final BlueFusionCustomMessagesPermissions key) {
        Objects.requireNonNull(key, "key");
        return key.getPermissionFormat();
    }

    @Override public boolean useSubFolder() { return false; }

    /**
     * Resolve permission node and check if the player is authorized (Admin or Player level).
     * Optionally emits the enum's error message (rate-limited) when unauthorized.
     *
     * @return true if authorized; false otherwise
     */
    public boolean DoesPlayerHavePermission(final BlueFusionCustomMessagesPermissions perm,
                                            final Player player,
                                            final boolean sendError,
                                            final DreamMessageSettings settings) {
        Objects.requireNonNull(perm, "perm");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(settings, "settings");

        final String format = getDefaultValueFor(perm);
        final String pluginName = BlueFusionCustomMessages.class.getSimpleName();

        final String adminPerm  = PermissionStrings.resolve(format, pluginName, BlueFusionCustomMessagesPermissionLevel.Admin);
        final String playerPerm = PermissionStrings.resolve(format, pluginName, BlueFusionCustomMessagesPermissionLevel.Player);

        final String cacheKey = player.getUniqueId() + "|" + adminPerm + "|" + playerPerm;

        final boolean allowed = permCache.getOrCompute(cacheKey, () -> {
            final var user = DreamLuckPerms.getUser(player);
            return DreamLuckPerms.hasPermission(user, adminPerm) || DreamLuckPerms.hasPermission(user, playerPerm);
        });

        if (!allowed && sendError) {
            final String denyKey = player.getUniqueId() + "|" + perm.name();
            if (denyLimiter.tryAcquire(denyKey)) {
                DreamChat.SendMessageToPlayer(player, perm.GetError(), settings);
            }
        }

        return allowed;
    }
}