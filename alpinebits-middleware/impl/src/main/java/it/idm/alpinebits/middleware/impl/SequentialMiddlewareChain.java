package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Context;
import it.idm.alpinebits.middleware.Middleware;
import it.idm.alpinebits.middleware.MiddlewareChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Implementation of {@link MiddlewareChain} that calls the configured middlewares in the providewd order.
 */
public class SequentialMiddlewareChain implements MiddlewareChain {

    private static final Logger LOG = LoggerFactory.getLogger(SequentialMiddlewareChain.class);

    private final Context ctx;
    private final List<Middleware> middlewares;
    private int index = -1;

    public SequentialMiddlewareChain(Context ctx, List<Middleware> middlewares) {
        if (ctx == null) {
            throw new IllegalArgumentException("The context must not be null");
        }
        if (middlewares == null) {
            throw new IllegalArgumentException("The middlewares must not be null");
        }
        this.ctx = ctx;
        this.middlewares = middlewares;
    }

    @Override
    public void next() {
        LOG.trace("Invocation of next() method");

        int chainSize = this.middlewares.size();

        if (this.index < chainSize - 1) {
            this.index++;
            Middleware currentMiddleware = this.middlewares.get(this.index);
            LOG.debug("Calling next middleware {} ({} of {} in this chain)", currentMiddleware, index + 1, chainSize);
            this.middlewares.get(this.index).handleRequest(this.ctx, this);
        } else {
            LOG.debug("No more middelwares to call (total: {} middlewares)", middlewares.size());
        }
    }

}
