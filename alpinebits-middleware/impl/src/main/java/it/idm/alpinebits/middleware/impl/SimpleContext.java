package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple {@link Context} implementation, that uses a {@link ConcurrentHashMap}
 * to handle the context state.
 */
public class SimpleContext implements Context {

    private final Map<String, Object> state = new ConcurrentHashMap<>();

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return (T) this.state.get(key);
    }

    @Override
    public Context set(String key, Object value) {
        this.state.put(key, value);
        return this;
    }

    @Override
    public Object remove(String key) {
        return this.state.remove(key);
    }

    @Override
    public boolean contains(String key) {
        return this.state.containsKey(key);
    }

    @Override
    public void handleException(Exception e) {
        throw new RuntimeException(e);
    }
}
