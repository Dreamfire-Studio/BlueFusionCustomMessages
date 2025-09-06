/*
 * MIT License
 * Copyright (c) 2025 Dreamfire Studio
 */
package com.dreamfirestudios.bluefusioncustommessages.Core;


import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Minimal service locator / registry for cross-plugin singletons.
 * <p>Register once during plugin enable; fetch anywhere without new-ing internals.</p>
 */
public final class Services {
    private static final Map<Class<?>, Object> REGISTRY = new ConcurrentHashMap<>();


    private Services() { }


    public static <T> void register(final Class<T> type, final T impl) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(impl, "impl");
        REGISTRY.put(type, impl);
    }


    public static <T> T get(final Class<T> type) {
        Objects.requireNonNull(type, "type");
        final Object o = REGISTRY.get(type);
        if (o == null) throw new IllegalStateException("Service not registered: " + type.getName());
        return type.cast(o);
    }


    public static <T> Optional<T> maybe(final Class<T> type) {
        Objects.requireNonNull(type, "type");
        final Object o = REGISTRY.get(type);
        return Optional.ofNullable(type.cast(o));
    }


    public static boolean isRegistered(final Class<?> type) {
        Objects.requireNonNull(type, "type");
        return REGISTRY.containsKey(type);
    }
}