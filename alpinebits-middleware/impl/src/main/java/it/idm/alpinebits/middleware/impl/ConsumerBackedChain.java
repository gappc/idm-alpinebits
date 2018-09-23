package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Context;
import it.idm.alpinebits.middleware.MiddlewareChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * {@link MiddlewareChain} implementation that implements its chaining
 * mechanism with {@link Consumer}.
 * <p>
 * This chain is most often used in combination with the {@link ComposingMiddlewareBuilder}.
 */
public class ConsumerBackedChain implements MiddlewareChain {

    private final Logger LOG = LoggerFactory.getLogger(ConsumerBackedChain.class);

    private final Context ctx;
    private final Consumer<Context> consumer;

    public ConsumerBackedChain(Context ctx, Consumer<Context> consumer) {
        this.ctx = ctx;
        this.consumer = consumer;
    }

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    public void next() {
        LOG.debug("{} invokes next consumer {}", this, consumer);
        try {
            this.consumer.accept(this.ctx);
        } catch (Exception e) {
            this.ctx.handleException(e);
        }
    }
}
