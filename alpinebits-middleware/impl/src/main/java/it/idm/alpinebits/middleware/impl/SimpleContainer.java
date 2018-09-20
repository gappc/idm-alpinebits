package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Container;
import it.idm.alpinebits.middleware.Context;
import it.idm.alpinebits.middleware.ExceptionHandler;
import it.idm.alpinebits.middleware.Middleware;
import it.idm.alpinebits.middleware.MiddlewareChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Simple {@link Container} implementation that covers the base usage.
 */
public class SimpleContainer implements Container {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleContainer.class);

    private final List<Middleware> middlewares = new CopyOnWriteArrayList<>();
    private final Context ctx;

    private ExceptionHandler exceptionHandler = new LoggingExceptionHandler();

    public SimpleContainer() {
        this.ctx = new SimpleContext(this);
    }

    public SimpleContainer(Context ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException("The context must not be null");
        }
        this.ctx = ctx;
    }

    @Override
    public void addMiddleware(Middleware middleware) {
        if (ctx == null) {
            throw new IllegalArgumentException("The middlware must not be null");
        }
        LOG.debug("Adding middleware: {}", middleware);
        this.middlewares.add(middleware);
    }

    @Override
    public void addMiddlewares(List<Middleware> middlewares) {
        for (Middleware middleware : middlewares) {
            this.addMiddleware(middleware);
        }
    }

    @Override
    public Context getContext() {
        return this.ctx;
    }

    @Override
    public void setExceptionHandler(ExceptionHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("The handler must not be null");
        }
        this.exceptionHandler = handler;
    }

    @SuppressWarnings("checkstyle:illegalcatch")
    public void invokeChain() {
        LOG.debug("Invoking chain with {} middlewares", this.middlewares.size());
        MiddlewareChain chain = new SequentialMiddlewareChain(this.ctx, this.middlewares);
        try {
            chain.next();
        } catch (Exception e) {
            this.exceptionHandler.handleException(e);
        }
    }

}
