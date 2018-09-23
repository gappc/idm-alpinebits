package it.idm.alpinebits.middleware;

/**
 * A MiddlewareChain is an object provided to the developer giving a view into the
 * {@link Middleware} invocation chain. Middlewares use the MiddlewareChain to invoke
 * the next middleware in the chain, or if the calling middleware is the last middleware
 * in the chain, to invoke the resource at the end of the chain.
 */
public interface MiddlewareChain {

    /**
     * Causes the next middleware in the chain to be invoked, or if the calling middleware
     * is the last middleware in the chain, causes the resource at the end of the chain to
     * be invoked.
     */
    void next();

}
