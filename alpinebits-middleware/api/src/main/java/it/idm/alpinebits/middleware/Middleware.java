package it.idm.alpinebits.middleware;

/**
 * The middleware implements a certain aspect or functionality. Its
 * {@link Middleware#handle(Context, MiddlewareChain)} method is usually invoked
 * by a {@link MiddlewareChain}.
 */
public interface Middleware {

    /**
     * Handle the current request. Call {@link MiddlewareChain#next()} to invoke
     * the next middleware in the chain.
     *
     * @param ctx   The {@link Context} is shared between all middlewares of the chain
     * @param chain the chain is used to call the next middleware when appropriate
     */
    void handle(Context ctx, MiddlewareChain chain);

}
