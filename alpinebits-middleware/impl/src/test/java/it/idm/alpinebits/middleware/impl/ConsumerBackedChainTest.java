package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Context;
import it.idm.alpinebits.middleware.Middleware;
import it.idm.alpinebits.middleware.MiddlewareChain;
import org.testng.annotations.Test;

/**
 * Test cases for {@link ConsumerBackedChain} class.
 */
public class ConsumerBackedChainTest {

    @Test
    public void testNext() throws Exception {

    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = MiddlewareBuilder.FULL_EXCEPTION_MESSAGE)
    public void testThrowingMiddleware() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware throwingMiddleware = MiddlewareBuilder.buildThrowingMiddleware();
        MiddlewareChain chain = new ConsumerBackedChain(ctx, context -> throwingMiddleware.handle(ctx, null));
        chain.next();
    }
}