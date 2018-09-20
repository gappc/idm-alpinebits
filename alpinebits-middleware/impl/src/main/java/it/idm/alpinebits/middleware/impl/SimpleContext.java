package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Container;
import it.idm.alpinebits.middleware.Context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple {@link Context} implementation that covers the base cases.
 */
public class SimpleContext implements Context {

    private final Container container;
    private final Map<String, Object> state = new ConcurrentHashMap<>();


    public SimpleContext(Container container) {
        if (container == null) {
            throw new IllegalArgumentException("The container must not be null");
        }
        this.container = container;
    }

    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    public <T> T getState(String key, Class<T> clazz) {
        return (T)this.state.get(key);
    }

    @Override
    public Context setState(String key, Object value) {
        this.state.put(key, value);
        return this;
    }

    @Override
    public Object removeState(String key) {
        return this.state.remove(key);
    }

    @Override
    public boolean stateContainsKey(String key) {
        return this.state.containsKey(key);
    }
}
