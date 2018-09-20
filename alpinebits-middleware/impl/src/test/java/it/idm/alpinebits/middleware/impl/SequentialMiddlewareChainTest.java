package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Context;
import it.idm.alpinebits.middleware.Middleware;
import it.idm.alpinebits.middleware.MiddlewareChain;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;

/**
 * Tests cases for {@link SequentialMiddlewareChain} class.
 */
public class SequentialMiddlewareChainTest {

    @Test
    public void testNext() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        String key = "key";
        String value = "value";

        Middleware middleware = MiddlewareBuilder.buildMiddleware(key, value);

        MiddlewareChain chain = new SequentialMiddlewareChain(ctx, Arrays.asList(middleware));
        chain.next();

        assertEquals(value, ctx.getState(key, String.class));
    }
}