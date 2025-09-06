/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */
package com.dreamfirestudios.bluefusioncustommessages.Core;


import com.dreamfirestudios.bluefusioncustommessages.Core.Interface.Result;


import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;


/** Utility to wrap unsafe calls and retry where appropriate. */
public final class Try {
    private Try() { }


    public static <T> Result<T> of(final Callable<T> c) {
        Objects.requireNonNull(c, "callable");
        try { return new Result.Ok<>(c.call()); }
        catch (Throwable t) { return new Result.Err<>(t.getMessage()); }
    }


    public static <T> Result<T> retry(final Callable<T> c, final int times, final Duration backoff) {
        Objects.requireNonNull(c, "callable");
        if (times <= 1) return of(c);
        final int attempts = Math.max(1, times);
        for (int i = 1; i <= attempts; i++) {
            final Result<T> r = of(c);
            if (r instanceof Result.Ok<T>) return r;
            if (i < attempts && backoff != null && !backoff.isZero() && !backoff.isNegative()) {
                try { Thread.sleep(backoff.toMillis()); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
            }
        }
        return of(c); // last attempt result
    }
}