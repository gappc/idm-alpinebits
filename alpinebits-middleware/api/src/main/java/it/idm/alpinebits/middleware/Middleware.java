package it.idm.alpinebits.middleware;

/**
 * The middleware implements a certain aspect or functionality. It is invoked
 * by a {@link MiddlewareChain}.
 */
public interface Middleware {

    /**
     * Handle the current request. Call {@link MiddlewareChain#next()} to invoke
     * the next middleware in the chain.
     * @param ctx context provided by the {@link Container}. This context is shared
     *            between all middlewares of the container instance.
     * @param chain
     */
    void handleRequest(Context ctx, MiddlewareChain chain);
}
