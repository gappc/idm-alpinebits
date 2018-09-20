package it.idm.alpinebits.middleware;

/**
 * Each middleware receives a context object that encapsulates an incoming message
 * and the corresponding response to that message.
 * <p>
 * A Context is created per request, and is referenced in middleware.
 */
public interface Context {

    /**
     * Get the {@link Container} in which this context is executed.
     *
     * @return container for this context
     */
    Container getContainer();

    /**
     * Get the value for the given key from the state.
     *
     * @param key identifier used to retrieve the value of the associated state
     * @param clazz type expected to be returned
     * @return value referenced by the given key
     */
    <T> T getState(String key, Class<T> clazz);

    /**
     * Set the state value for the given key in this context.
     *
     * @param key identifier used for the given value
     * @return the current context
     */
    Context setState(String key, Object value);

    /**
     * Remove the state value referenced by the given key.
     *
     * @param key identifier referencing the state value, that should be removed
     * @return the state referenced by the given key
     */
    Object removeState(String key);

    /**
     * Check if the given key is part of this context's state.
     *
     * @param key check if the state contains the given key
     * @return true if the key was found in the state, false otherwise
     */
    boolean stateContainsKey(String key);

}
