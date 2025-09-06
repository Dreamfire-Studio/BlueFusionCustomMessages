/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */
package com.dreamfirestudios.bluefusioncustommessages.Core;


import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;


/** Non-blocking token bucket limiter for spam protection. */
public final class RateLimiter {
    private final double ratePerSec;
    private final int burst;
    private final AtomicLong tokensNanos = new AtomicLong(0L);
    private volatile long last = System.nanoTime();


    private RateLimiter(final double permitsPerSecond, final int burst) {
        if (permitsPerSecond <= 0) throw new IllegalArgumentException("rate");
        if (burst <= 0) throw new IllegalArgumentException("burst");
        this.ratePerSec = permitsPerSecond;
        this.burst = burst;
        this.tokensNanos.set(toNanos(burst));
    }


    public static RateLimiter of(final double permitsPerSecond, final int burst) {
        return new RateLimiter(permitsPerSecond, burst);
    }


    public boolean tryAcquire() {
        final long now = System.nanoTime();
        refill(now);
        while (true) {
            final long cur = tokensNanos.get();
            if (cur <= 0) return false;
            if (tokensNanos.compareAndSet(cur, cur - toNanos(1))) return true;
        }
    }


    private void refill(final long now) {
        final long elapsed = now - last;
        if (elapsed <= 0) return;
        last = now;
        final double tokensAdd = (elapsed / 1_000_000_000.0) * ratePerSec;
        final long addNanos = toNanos((int)Math.floor(tokensAdd));
        if (addNanos <= 0) return;
        long cur, upd;
        do {
            cur = tokensNanos.get();
            upd = Math.min(cur + addNanos, toNanos(burst));
        } while (!tokensNanos.compareAndSet(cur, upd));
    }


    private static long toNanos(final int tokens) { return tokens * 1_000_000L; }
}