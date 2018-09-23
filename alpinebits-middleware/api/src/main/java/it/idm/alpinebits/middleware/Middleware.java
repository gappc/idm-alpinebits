package it.idm.alpinebits.middleware;

/**
 * The middleware implements a certain aspect or functionality. It is invoked
 * by a {@link MiddlewareChain}.
 */
public interface Middleware {

    /**
     * Handle the current request. Call {@link MiddlewareChain#next()} to invoke
     * the next middleware in the chain.
     *
     * @param ctx   context provided by the {@link Container}. This context is shared
     *              between all middlewares of the container instance.
     * @param chain the chain is used to call the next middleware when appropriate
     */
    void handle(Context ctx, MiddlewareChain chain);

}
