package com.dreamfirestudios.bluefusioncustommessages.Enum;

public enum CustomMessageNames {
    JOIN_MESSAGE      ("&a%player_name% &7has joined the game."),
    FIRST_JOIN_MESSAGE("&bWelcome &a%player_name% &7to the server for the first time!"),
    LEAVE_MESSAGE     ("&c%player_name% &7has left the game."),
    KICK_MESSAGE      ("&e%player_name% &7was kicked: &f%dreamcore_reason%"),
    BAN_MESSAGE       ("&4%player_name% &7was permanently banned: &f%dreamcore_reason%"),
    TEMPBAN_MESSAGE   ("&6%player_name% &7was temporarily banned for &f%duration%&7: &f%dreamcore_reason%"),
    DEATH_GENERIC     ("&c%player_name% &7died."),
    DEATH_PVP         ("&c%player_name% &7was slain by &c%killer%"),
    DEATH_ENTITY      ("&c%player_name% &7was slain by &c%entity%"),
    DEATH_CAUSE       ("&c%player_name% &7died from &c%cause%"),
    SUICIDE           ("&c%player_name% &7took their own life."),
    FALL_DEATH        ("&c%player_name% &7fell from a high place."),
    LAVA_DEATH        ("&c%player_name% &7tried to swim in lava."),
    DROWNING          ("&c%player_name% &7drowned."),
    FIRE_DEATH        ("&c%player_name% &7burned to death."),
    EXPLOSION_DEATH   ("&c%player_name% &7blew up."),
    VOID_DEATH        ("&c%player_name% &7fell into the void.");

    private final String defaultMessage;

    CustomMessageNames(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}