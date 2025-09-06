/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */

package com.dreamfirestudios.bluefusioncustommessages.PulseConfig;

import com.dreamfirestudios.dreamconfig.Abstract.StaticEnumPulseConfig;
import com.dreamfirestudios.dreamconfig.Interface.ConfigVersion;
import com.dreamfirestudios.dreamcore.DreamChat.DreamChat;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageFormatter;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import com.dreamfirestudios.bluefusioncustommessages.Core.Services;
import com.dreamfirestudios.bluefusioncustommessages.Enum.BlueFusionCustomMessagesMessages;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * Localized/templated message configuration with main-thread-safe delivery.
 */
@PulseAutoRegister
@ConfigVersion(1)
public final class BlueFusionCustomMessagesMessagesConfig
        extends StaticEnumPulseConfig<BlueFusionCustomMessagesMessagesConfig, BlueFusionCustomMessagesMessages, String> {

    @Override public JavaPlugin mainClass() { return BlueFusionCustomMessages.GetBlueFusionCustomMessages(); }
    @Override protected Class<BlueFusionCustomMessagesMessages> getKeyClass() { return BlueFusionCustomMessagesMessages.class; }
    @Override protected Class<String> getValueClass() { return String.class; }
    @Override protected String getDefaultValueFor(final BlueFusionCustomMessagesMessages key) { return key.GetTemplate(); }
    @Override public boolean useSubFolder() { return false; }

    private static Object[] withPlugin(final Object... args) {
        final String pluginName = BlueFusionCustomMessages.class.getSimpleName();
        final int baseLen = (args == null ? 0 : args.length);
        final Object[] full = new Object[baseLen + 1];
        full[0] = pluginName;
        if (baseLen > 0) System.arraycopy(args, 0, full, 1, baseLen);
        return full;
    }

    private static String formatPlain(final String template, final DreamMessageSettings settings, final Object... fmtArgs) {
        Objects.requireNonNull(template, "template");
        Objects.requireNonNull(settings, "settings");
        final var comp = DreamMessageFormatter.format(String.format(template, fmtArgs), settings);
        return PlainTextComponentSerializer.plainText().serialize(comp);
    }

    private static String formatPlain(final String template, final Player p, final DreamMessageSettings settings, final Object... fmtArgs) {
        Objects.requireNonNull(template, "template");
        Objects.requireNonNull(settings, "settings");
        final var comp = DreamMessageFormatter.format(String.format(template, fmtArgs), p, settings);
        return PlainTextComponentSerializer.plainText().serialize(comp);
    }

    public void SendMessageToBroadcast(final BlueFusionCustomMessagesMessages msg, final DreamMessageSettings settings, final Object... args){
        Objects.requireNonNull(msg, "msg");
        Objects.requireNonNull(settings, "settings");
        final var template = getDefaultValueFor(msg);
        if (template == null || template.isEmpty()) return;

        Services.scheduler().main(() ->
                DreamChat.BroadcastMessage(formatPlain(template, settings, withPlugin(args)), settings));
    }

    public void SendMessageToPlayerPermission(final BlueFusionCustomMessagesMessages msg,
                                              final com.dreamfirestudios.bluefusioncustommessages.Enum.BlueFusionCustomMessagesPermissions perm,
                                              final DreamMessageSettings settings, final Object... args) {
        Objects.requireNonNull(msg, "msg");
        Objects.requireNonNull(perm, "perm");
        Objects.requireNonNull(settings, "settings");

        Services.scheduler().main(() -> {
            for (final var player : Bukkit.getOnlinePlayers()) {
                BlueFusionCustomMessagesPermissionsConfigs.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(),
                        BlueFusionCustomMessagesPermissionsConfigs.class, cfg -> {
                            if (cfg.DoesPlayerHavePermission(perm, player, false, settings)) {
                                SendMessageToPlayer(msg, player, settings, args);
                            }
                        });
            }
        });
    }

    public void SendMessageToPlayer(final BlueFusionCustomMessagesMessages msg, final Player player,
                                    final DreamMessageSettings settings, final Object... args){
        Objects.requireNonNull(msg, "msg");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(settings, "settings");
        final var template = getDefaultValueFor(msg);
        if (template == null || template.isEmpty()) return;

        Services.scheduler().main(() ->
                DreamChat.SendMessageToPlayer(player,
                        formatPlain(template, player, settings, withPlugin(args)), settings));
    }

    public void SendMessageToContext(final BlueFusionCustomMessagesMessages msg, final Player player,
                                     final ConversationContext ctx, final DreamMessageSettings settings, final Object... args) {
        Objects.requireNonNull(msg, "msg");
        Objects.requireNonNull(ctx, "ctx");
        Objects.requireNonNull(settings, "settings");
        final var template = getDefaultValueFor(msg);
        if (template == null || template.isEmpty()) return;

        Services.scheduler().main(() ->
                ctx.getForWhom().sendRawMessage(
                        formatPlain(template, player, settings, withPlugin(args))));
    }

    public void SendMessageToConsole(final BlueFusionCustomMessagesMessages msg, final DreamMessageSettings settings, final Object... args){
        Objects.requireNonNull(msg, "msg");
        Objects.requireNonNull(settings, "settings");
        final var template = getDefaultValueFor(msg);
        if (template == null || template.isEmpty()) return;

        Services.scheduler().main(() ->
                DreamChat.SendMessageToConsole(
                        formatPlain(template, settings, withPlugin(args)), settings));
    }
}