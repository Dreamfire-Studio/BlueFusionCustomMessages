/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */
package com.dreamfirestudios.bluefusioncustommessages.Core;


import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;


/** Simple TTL cache for hot values. */
public final class ExpiringCache<K, V> {
    private static final class Entry<V> { final V v; final long exp; Entry(V v, long exp){this.v=v; this.exp=exp;} }


    private final long ttlNanos;
    private final ConcurrentMap<K, Entry<V>> map = new ConcurrentHashMap<>();


    public ExpiringCache(final Duration ttl) {
        Objects.requireNonNull(ttl, "ttl");
        this.ttlNanos = Math.max(1L, ttl.toNanos());
    }


    public Optional<V> get(final K key) {
        Objects.requireNonNull(key, "key");
        final Entry<V> e = map.get(key);
        if (e == null) return Optional.empty();
        if (System.nanoTime() > e.exp) { map.remove(key, e); return Optional.empty(); }
        return Optional.ofNullable(e.v);
    }


    public V get(final K key, final Function<K,V> loaderIfMiss) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(loaderIfMiss, "loaderIfMiss");
        final long now = System.nanoTime();
        final Entry<V> e = map.get(key);
        if (e != null && now <= e.exp) return e.v;
        final V v = loaderIfMiss.apply(key);
        map.put(key, new Entry<>(v, now + ttlNanos));
        return v;
    }


    public void put(final K key, final V value) {
        Objects.requireNonNull(key, "key");
        final long exp = System.nanoTime() + ttlNanos;
        map.put(key, new Entry<>(value, exp));
    }


    public void invalidate(final K key) { Objects.requireNonNull(key, "key"); map.remove(key); }
    public void invalidateAll() { map.clear(); }
}