package com.dreamfirestudios.bluefusioncustommessages.Listeners;

import com.dreamfirestudios.bluefusioncustommessages.Enum.CustomMessageNames;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageFormatter;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.dreamcore.DreamJava.PulseAutoRegister;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.kyori.adventure.text.Component;

@PulseAutoRegister
public class PlayerDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        var lastDamage = event.getEntity().getLastDamageCause();
        CustomMessageNames msgType = CustomMessageNames.DEATH_GENERIC;
        if (player.equals(player.getKiller())) {
            msgType = CustomMessageNames.SUICIDE;
        } else if (player.getKiller() != null) {
            msgType = CustomMessageNames.DEATH_PVP;
        } else if (lastDamage != null) {
            switch (lastDamage.getCause()) {
                case LAVA -> msgType = CustomMessageNames.LAVA_DEATH;
                case FIRE, FIRE_TICK -> msgType = CustomMessageNames.FIRE_DEATH;
                case VOID -> msgType = CustomMessageNames.VOID_DEATH;
                case FALL -> msgType = CustomMessageNames.FALL_DEATH;
                case DROWNING -> msgType = CustomMessageNames.DROWNING;
                case BLOCK_EXPLOSION, ENTITY_EXPLOSION -> msgType = CustomMessageNames.EXPLOSION_DEATH;
                default -> {
                    lastDamage.getEntity();
                    msgType = CustomMessageNames.DEATH_ENTITY;
                }
            }
        }

        String rawMessage = msgType.getDefaultMessage().replace("%player_name%", player.getName());
        Component formatted = DreamMessageFormatter.format(rawMessage, player, DreamMessageSettings.all());
        event.deathMessage(formatted);
    }
}