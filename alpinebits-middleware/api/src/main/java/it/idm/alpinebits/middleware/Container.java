package it.idm.alpinebits.middleware;

import java.util.List;

/**
 * A container is an object that handles the execution of a {@link Middleware}.
 */
public interface Container {

    /**
     * Add the given {@link Middleware} to the container.
     * @param middleware the middleware to add
     */
    void addMiddleware(Middleware middleware);

    /**
     * Add the given {@link Middleware} list to the container.
     * @param middlewares a list of middlewares to add
     */
    void addMiddlewares(List<Middleware> middlewares);

    /**
     * Return the context used by the container.
     * @return Context the context used by the container
     */
    Context getContext();

    /**
     * Set the {@link ExceptionHandler} for this container. This handler is
     * called for all uncaught exceptions.
     *
     * @param handler exception handler used to catch all uncaught errors
     */
    void setExceptionHandler(ExceptionHandler handler);
}
