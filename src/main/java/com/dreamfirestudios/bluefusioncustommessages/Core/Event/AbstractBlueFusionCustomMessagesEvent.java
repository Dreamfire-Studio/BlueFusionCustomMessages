/*
 * MIT License
 *
 * Copyright (c) 2025 Dreamfire Studio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.dreamfirestudios.bluefusioncustommessages.Core.Event;

import com.dreamfirestudios.bluefusioncustommessages.Core.EventBus;
import com.dreamfirestudios.bluefusioncustommessages.Core.Log;
import com.dreamfirestudios.bluefusioncustommessages.Core.Scheduler;
import com.dreamfirestudios.bluefusioncustommessages.BlueFusionCustomMessages;
import com.dreamfirestudios.bluefusioncustommessages.PulseConfig.BlueFusionCustomMessagesConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

/**
 * <summary>
 * Abstract base class for all {@code BlueFusionCustomMessages} events.
 * </summary>
 * <remarks>
 * <ul>
 *   <li>Provides standard Bukkit {@link HandlerList} boilerplate.</li>
 *   <li>Centralizes dispatch through {@link EventBus}, including null-checks.</li>
 *   <li>Guards against firing when the system is disabled.</li>
 *   <li>Enforces main-thread dispatch via {@link Scheduler}.</li>
 *   <li>Provides optional debug logging controlled by config.</li>
 * </ul>
 * </remarks>
 */
public abstract class AbstractBlueFusionCustomMessagesEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Log log = Log.of(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), false);
    private final EventBus bus = new EventBus(BlueFusionCustomMessages.GetBlueFusionCustomMessages());
    private final Scheduler scheduler = new Scheduler(BlueFusionCustomMessages.GetBlueFusionCustomMessages());

    /**
     * Constructs a plugin event, auto-detecting async state.
     */
    protected AbstractBlueFusionCustomMessagesEvent() {
        super(!Bukkit.isPrimaryThread());
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Fires this event if and only if the system is enabled.
     * Dispatch is always performed on the main thread.
     */
    public void callEvent() {
        BlueFusionCustomMessagesConfig.ReturnStaticAsync(BlueFusionCustomMessages.GetBlueFusionCustomMessages(), BlueFusionCustomMessagesConfig.class,
                cfg -> {
                    if (!cfg.systemEnabled) {
                        if (cfg.debugConfig) log.debug("Events", "Skipped event (systemEnabled=false): " + getClass().getSimpleName());
                        return;
                    }
                    if (cfg.debugConfig) log.debug("Events", "Firing: " + getClass().getSimpleName());
                    bus.fire(this);
                }
        );
    }
}