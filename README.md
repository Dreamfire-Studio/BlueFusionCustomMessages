# BlueFusionCustomMessages

**Version:** `1.21.8-r0.1`
**Author:** Dreamfire Studio, Joshua Filer
**License:** MIT License

---

## Overview

The **BlueFusionCustomMessages** plugin lets you fully customize **join, leave, kick, ban, unban, and death messages** on Bukkit/Spigot/Paper servers.
It supports **placeholders**, **MiniMessage formatting**, and **dynamic configs** powered by DreamCore’s `DreamConfig` and `DreamPlaceholder` frameworks.

With this plugin, you can define messages that include player names, killers, reasons, ban durations, and more — all editable without server restarts.

---

## Key Features

* **Customizable Messages:** Join, leave, kick, ban, tempban, unban, and detailed death messages.
* **Placeholders:** `%player_name%`, `%dreamcore_reason%`, `%dreamcore_duration%`, `%dreamcore_killer%`, `%dreamcore_entity%`, `%dreamcore_cause%`.
* **MiniMessage & Colors:** Supports `&` legacy codes and full MiniMessage tags.
* **Dynamic Configs:** All messages stored in `CustomMessageNamesConfig` with live reload support.
* **Event Coverage:** Hooks into player connection, moderation, and death events.
* **Integration:** Built on DreamCore (`DreamMessageFormatter`, `DreamPlaceholder`, `DreamConfig`).

---

## Installation

### Download

Download the latest release here:
👉 [GitHub Releases](https://github.com/Dreamfire-Studio/BlueFusionCustomMessages/releases)

Place the `BlueFusionCustomMessages.jar` into your server’s `plugins/` folder, then restart the server.

### Maven

```xml
<repository>
  <id>github</id>
  <url>https://maven.pkg.github.com/Dreamfire-Studio/BlueFusionCustomMessages</url>
</repository>

<dependency>
  <groupId>com.dreamfirestudios</groupId>
  <artifactId>bluefusioncustommessages-1.21.8-r0.1</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

---

## Permissions

| Permission Node                         | Description                 |
| --------------------------------------- | --------------------------- |
| `BlueFusionCustomMessages.ReloadConfig` | Reload configuration files  |
| `BlueFusionCustomMessages.EditConfig`   | Edit messages dynamically   |
| `BlueFusionCustomMessages.Bypass`       | Bypass message restrictions |

---

## Custom Messages

All messages are configurable inside `CustomMessageNamesConfig.yml`.
Each message supports color codes (`&a`, `&c`) and MiniMessage (`<red>`, gradients, hover/click).

### Supported Events & Placeholders

| Message              | Default Example                                                                               | Placeholders Supported                                        |
| -------------------- | --------------------------------------------------------------------------------------------- | ------------------------------------------------------------- |
| `JOIN_MESSAGE`       | `&a%player_name% &7has joined the game.`                                                      | `%player_name%`                                               |
| `FIRST_JOIN_MESSAGE` | `&bWelcome &a%player_name% &7to the server!`                                                  | `%player_name%`                                               |
| `LEAVE_MESSAGE`      | `&c%player_name% &7has left the game.`                                                        | `%player_name%`                                               |
| `KICK_MESSAGE`       | `&e%player_name% &7was kicked: &f%dreamcore_reason%`                                          | `%player_name%`, `%dreamcore_reason%`                         |
| `BAN_MESSAGE`        | `&4%player_name% &7was permanently banned: &f%dreamcore_reason%`                              | `%player_name%`, `%dreamcore_reason%`                         |
| `TEMPBAN_MESSAGE`    | `&6%player_name% &7was temporarily banned for &f%dreamcore_duration%&7: &f%dreamcore_reason%` | `%player_name%`, `%dreamcore_reason%`, `%dreamcore_duration%` |
| `UNBAN_MESSAGE`      | `&a%player_name% &7was unbanned.`                                                             | `%player_name%`                                               |
| `DEATH_GENERIC`      | `&c%player_name% &7died.`                                                                     | `%player_name%`                                               |
| `DEATH_PVP`          | `&c%player_name% &7was slain by &c%dreamcore_killer%`                                         | `%player_name%`, `%dreamcore_killer%`                         |
| `DEATH_ENTITY`       | `&c%player_name% &7was slain by &c%dreamcore_entity%`                                         | `%player_name%`, `%dreamcore_entity%`                         |
| `DEATH_CAUSE`        | `&c%player_name% &7died from &c%dreamcore_cause%`                                             | `%player_name%`, `%dreamcore_cause%`                          |
| `SUICIDE`            | `&c%player_name% &7took their own life.`                                                      | `%player_name%`                                               |
| `FALL_DEATH`         | `&c%player_name% &7fell from a high place.`                                                   | `%player_name%`                                               |
| `LAVA_DEATH`         | `&c%player_name% &7tried to swim in lava.`                                                    | `%player_name%`                                               |
| `DROWNING`           | `&c%player_name% &7drowned.`                                                                  | `%player_name%`                                               |
| `FIRE_DEATH`         | `&c%player_name% &7burned to death.`                                                          | `%player_name%`                                               |
| `EXPLOSION_DEATH`    | `&c%player_name% &7blew up.`                                                                  | `%player_name%`                                               |
| `VOID_DEATH`         | `&c%player_name% &7fell into the void.`                                                       | `%player_name%`                                               |

---

## Commands

* `/bcm reload` → Reloads all configs
* `/bcm edit <message> <text>` → Updates a message live (if enabled)

---

## Links

* 🌐 [Dreamfire Studio Website](https://www.dreamfirestudio.net)
* 🎥 [YouTube Channel](https://www.youtube.com/@Dreamfire_Studio)
* 📖 [Wiki Documentation](https://www.dreamfirestudio.net/wiki/Dreamfire-Studio/BlueFusionCustomMessages)
* 📦 [GitHub Releases](https://github.com/Dreamfire-Studio/BlueFusionCustomMessages/releases)

---

## Contributing

Contributions are welcome! Fork the repo, open issues, or submit pull requests.

---

## License

This project is licensed under the \[MIT Lice
