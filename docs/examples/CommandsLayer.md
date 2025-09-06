# Commands Layer

> Turn-key command surface for console and players, built on DreamCommand and wired into the Core’s config, permissions, messaging, and threading utilities.

---

## What this layer provides

* **Annotation-first command binding** via DreamCommand (`@PCMethod`, `@PCTab`, `@PCOP`).
* **Two entrypoints**: player (`BlueFusionCustomMessagesPlayerCommand`) and server/console (`BlueFusionCustomMessagesServerCommand`).
* **Centralized gating** through `BlueFusionCustomMessagesPlayerCommandHelper`:

    * `checkSystemEnabled(...)`
    * `checkPermission(...)`
    * `checkSystemEnabledAndPermission(...)`
* **Thread safety** using `Scheduler` for all main-thread tasks (GUIs, chat, Bukkit APIs).
* **Consistent feedback** using `BlueFusionCustomMessagesMessagesConfig` with Adventure-backed formatting.
* **Permissions** resolved through `BlueFusionCustomMessagesPermissionsConfigs` + LuckPerms.
* **Feature flags** (optional) available through `BlueFusionCustomMessagesFeatureFlagsConfig`.
* **Rate limiting** and **anti-spam** hooks (optional) through `RateLimiter`.
* **Resiliency** with `Try` helpers where background work is involved.
* **Player UX**: SmartInvs-powered admin panel (`BlueFusionCustomMessagesCoreMenu`).

---

## File map

* `PlayerCommand/BlueFusionCustomMessagesPlayerCommand.java` — player-facing commands.
* `ServerCommand/BlueFusionCustomMessagesServerCommand.java` — console/automation commands.
* `Core/BlueFusionCustomMessagesPlayerCommandHelper.java` — reusable gates for enabled/permission checks.

> See also: `PulseConfig/*` for config + messages + permissions, and `SmartInvs/*` for GUI actions.

---

## Command registration lifecycle

1. **Plugin enable** (`BlueFusionCustomMessages.onEnable()`):

    * Registers variable tests & classes with DreamClassAPI.
    * Registers all annotated commands with `DreamCommand.RegisterRaw(this)`.
    * Reloads configs once to make message templates & permissions available.
2. **DreamCommand** scans classes annotated with `@PulseAutoRegister` and binds methods marked with `@PCMethod`.

---

## Anatomy of a command method

```java
@PCMethod({"configs", "reload"})
public void TimeStealCoreReloadMethod(final Player player) {
    // 1) Gate: enabled + permission
    BlueFusionCustomMessagesPlayerCommandHelper.checkSystemEnabledAndPermission(
        BlueFusionCustomMessagesPermissions.ReloadConfigs,
        player,
        /* sendError */ true,
        DreamMessageSettings.all(),
        // 2) Action
        () -> {
            BlueFusionCustomMessagesAPI.BlueFusionCustomMessagesReloadConfigs(DreamMessageSettings.all());
            // 3) Feedback on main thread
            BlueFusionCustomMessagesMessagesConfig.ReturnStaticAsync(
                BlueFusionCustomMessages.GetBlueFusionCustomMessages(),
                BlueFusionCustomMessagesMessagesConfig.class,
                messageConfig -> new Scheduler(BlueFusionCustomMessages.GetBlueFusionCustomMessages())
                    .main(() -> messageConfig.SendMessageToPlayer(
                        BlueFusionCustomMessagesMessages.PlayerReloadedConfig,
                        player,
                        DreamMessageSettings.all()
                    ))
            );
        }
    );
}
```

**Key points**

* **Never** touch Bukkit APIs off the main thread; route with `Scheduler.main(...)`.
* Return types are `void`; DreamCommand handles invocation and tab/arg parsing.
* Annotate tab completers with `@PCTab` as needed.

---

## Player vs Server commands

### Player (`BlueFusionCustomMessagesPlayerCommand`)

* Opens GUIs (SmartInvs), serializes held items, toggles features, reloads/resets configs.
* Uses `BlueFusionCustomMessagesPlayerCommandHelper` to reduce boilerplate.
* Example commands:

    * `/bluefusioncustommessages` → opens admin GUI (permission `AdminConsole`).
    * `/bluefusioncustommessages enable <true|false>` → toggle system (permission `EnableSystem`).
    * `/bluefusioncustommessages serialize <ID>` → persist main-hand item (permission `SerializeItem`).
    * `/bluefusioncustommessages configs reset|reload` → config ops (system enabled + respective perms).

### Server (`BlueFusionCustomMessagesServerCommand`)

* Console-only flows that don’t require a `Player` context.
* Status, enable/disable, and config ops.

---

## Permissions & errors

* Logical permission keys live in `Enum/BlueFusionCustomMessagesPermissions` and map to format strings like `"%s.%s.ReloadConfigs"`.
* Levels (`Admin`, `Player`) come from `Enum/BlueFusionCustomMessagesPermissionLevel`.
* `BlueFusionCustomMessagesPermissionsConfigs` resolves concrete nodes (e.g., `Plugin.Admin.ReloadConfigs`).
* On failure, the helper can **optionally** send the standardized error message.

```java
BlueFusionCustomMessagesPlayerCommandHelper.checkPermission(
  BlueFusionCustomMessagesPermissions.AdminConsole,
  player,
  /* sendError */ true,
  DreamMessageSettings.all(),
  () -> openGui()
);
```

---

## Messaging & feedback

* All feedback routes through `BlueFusionCustomMessagesMessagesConfig` using Adventure formatting.
* Templates live in `Enum/BlueFusionCustomMessagesMessages` and accept the plugin name as the first `%s` automatically.
* Helpers:

    * `SendMessageToPlayer(...)`
    * `SendMessageToBroadcast(...)`
    * `SendMessageToConsole(...)`
    * `SendMessageToPlayerPermission(...)`

**Tip:** Keep messages short and green for success, red for failure, matching your current templates.

---

## Threading rules

* Use `Scheduler.main(...)` for any operation that touches:

    * SmartInvs / inventory
    * Bukkit chat/players/world state
    * Opening/closing GUIs
* Background work is fine via `ReturnStaticAsync(...)`, but **always hop back** to main for the final action.

---

## Rate limiting (optional)

To prevent spammy commands, add a rate limit check at the start of a handler:

```java
if (!RateLimiter.global().tryAcquire("cmd:reload:" + player.getUniqueId(), Duration.ofSeconds(2))) {
    // Optionally send a throttle message
    return;
}
```

Use per-command keys and sensible windows (1–3 seconds for UX-critical commands).

---

## Try/Retry (optional)

Wrap non-critical background work when interacting with IO or external services:

```java
Try.run(() -> doExpensiveThing())
   .onFailure(ex -> log.warn("ExpensiveThing failed: " + ex.getMessage()));

Retry.withBackoff(3, Duration.ofMillis(150))
     .run(() -> maybeUnstableCall());
```

Avoid wrapping Bukkit calls; they should be predictable on main.

---

## Feature flags (optional)

Gate entire command trees with `BlueFusionCustomMessagesFeatureFlagsConfig` if needed. Example:

```java
BlueFusionCustomMessagesFeatureFlagsConfig.ReturnStaticAsync(plugin, BlueFusionCustomMessagesFeatureFlagsConfig.class, flags -> {
    if (flags.GetValue(BlueFusionCustomMessagesFeatureFlagKey.CONFIG_COMMANDS)) {
        // proceed to expose config commands
    }
});
```

---

## SmartInvs integration

The base command `/bluefusioncustommessages` opens `BlueFusionCustomMessagesCoreMenu`, a single-row admin bar with items loaded from `BlueFusionCustomMessagesInventoryItemsConfig`.

* Use `BlueFusionCustomMessagesSmartInvsItems` helpers to fetch configured items asynchronously and place `ClickableItem`s.
* After actions (toggle/reload/reset), **re-open** the menu to reflect new state.

---

## Tabs & aliases

* Add `@PCTab` for simple completions, e.g. `@PCTab(pos=1, type=TabType.PureData, data="ITEM ID")` for serialize.
* Provide aliases by setting the `COMMAND_ALIASES` array in the command class. Keep them short and predictable.

---

## Error handling

* Prefer permission error via enum-provided message.
* For invalid args, rely on DreamCommand’s parsing where possible; otherwise send a concise usage line.
* Avoid throwing exceptions out of handlers; log and send a friendly message instead.

---

## Testing checklist

* [ ] Command registers and is visible to the right audience (player/console).
* [ ] Permission gates deny unauthorised users with the standard message.
* [ ] System disabled gate blocks config commands (and GUI toggle reflects state).
* [ ] Messages render correctly and include plugin branding.
* [ ] GUI actions run on main thread and reflect current state after action.
* [ ] Optional: rate limiting prevents spam without harming UX.
* [ ] Optional: Try/Retry does not wrap Bukkit calls and only covers IO/async.

---

## Patterns & conventions

* Name handlers `ThingMethod` consistently to match current codebase.
* Keep command methods tiny: **gate → action → feedback**.
* Prefer helpers (`BlueFusionCustomMessagesPlayerCommandHelper`, `MessagesConfig`) to avoid duplication.
* All `Objects.requireNonNull(...)` at the boundary for parameters.

---

## Extending the layer

* Add new permissions in `Enum/BlueFusionCustomMessagesPermissions` and wire defaults in `BlueFusionCustomMessagesPermissionsConfigs`.
* Add new menu buttons by extending `BlueFusionCustomMessagesInventoryItems` and mapping defaults in its config.
* Introduce new command groups by adding new `@PulseAutoRegister` classes; DreamCommand will pick them up.
