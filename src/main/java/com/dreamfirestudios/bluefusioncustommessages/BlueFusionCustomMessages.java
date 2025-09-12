/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */

package com.dreamfirestudios.bluefusioncustommessages;

import com.dreamfirestudios.bluefusioncustommessages.API.BlueFusionCustomMessagesAPI;
import com.dreamfirestudios.bluefusioncustommessages.DynamicPulseConfig.PlayerDynamicPulseConfig;
import com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_Permissions;
import com.dreamfirestudios.bluefusioncustommessages.Enum.CustomMessageNames;
import com.dreamfirestudios.dreamcommand.DreamCommand;
import com.dreamfirestudios.dreamcore.DreamChat.DreamMessageSettings;
import com.dreamfirestudios.dreamcore.DreamJava.DreamClassAPI;
import com.dreamfirestudios.dreamcore.DreamVariable.DreamEnumVariableTest;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Main entry point for the BlueFusionCustomMessages plugin.
 *
 * <p>Handles registration of variable tests, command routing, and
 * initial config loading.</p>
 */
public class BlueFusionCustomMessages extends JavaPlugin {
    private static BlueFusionCustomMessages instance;

    /**
     * @return the singleton plugin instance
     */
    public static BlueFusionCustomMessages GetInstance(){
        return instance;
    }

    /**
     * @return the plugin's class name, used in permissions/messages
     */
    public static String GetClassName(){
        return instance.getClass().getSimpleName();
    }

    public static HashMap<UUID, PlayerDynamicPulseConfig> PlayerDynamicPulseConfigs = new HashMap<>();
    public static final Map<UUID, String> ReasonContextMap   = new ConcurrentHashMap<>();
    public static final Map<UUID, String> DurationContextMap = new ConcurrentHashMap<>();
    public static final Map<UUID, String> KillerContextMap   = new ConcurrentHashMap<>();
    public static final Map<UUID, String> EntityContextMap   = new ConcurrentHashMap<>();
    public static final Map<UUID, String> CauseContextMap    = new ConcurrentHashMap<>();

    /**
     * Called when the plugin is enabled.
     *
     * <p>Registers variable tests, command handlers, and loads configs.</p>
     */
    @Override
    public void onEnable() {
        instance = this;

        // Register enums as variable tests for configs/commands
        DreamClassAPI.RegisterPulseVariableTest(this, new DreamEnumVariableTest<>(BFCM_Permissions.class));
        DreamClassAPI.RegisterPulseVariableTest(this, new DreamEnumVariableTest<>(CustomMessageNames.class));
        DreamClassAPI.RegisterPulseVariableTest(this, new DreamEnumVariableTest<>(com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_InventoryItems.class));
        DreamClassAPI.RegisterPulseVariableTest(this, new DreamEnumVariableTest<>(com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_Messages.class));
        DreamClassAPI.RegisterPulseVariableTest(this, new DreamEnumVariableTest<>(com.dreamfirestudios.bluefusioncustommessages.Enum.BFCM_SmartInventories.class));
        DreamClassAPI.RegisterPulseVariableTest(this, new DreamEnumVariableTest<>(InventoryType.class));

        // Register annotated classes & commands
        DreamClassAPI.RegisterClasses(this);
        DreamCommand.RegisterRaw(this);

        // Load configs
        BlueFusionCustomMessagesAPI.ReloadConfigs(DreamMessageSettings.all(), false);
    }

    /**
     * Called when the plugin is disabled.
     *
     * <p>Ensures all inventories are closed to prevent ghost menus.</p>
     */
    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);
    }
}