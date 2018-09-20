package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Middleware;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;

/**
 * Tests cases for {@link SimpleContainer} class.
 */
public class SimpleContainerTest {

    @Test
    public void testAddMiddleware() throws Exception {
        SimpleContainer container = ContainerBuilder.buildSimpleContainer();

        String key = "key";
        String value = "value";

        assertNull(container.getContext().getState(key, Object.class));

        // When the container chain is invoked, the handleRequest method of all
        // registered middlewares is called. In this case, the registered middleware
        // writes the provided value to the container context state. This way it is
        // possible to check if the addMiddleware method call was successful.
        Middleware middleware = MiddlewareBuilder.buildMiddleware(key, value);
        container.addMiddleware(middleware);
        container.invokeChain();

        assertEquals(value, container.getContext().getState(key, String.class));
    }

    @Test
    public void testAddMiddlewares() throws Exception {
        SimpleContainer container = ContainerBuilder.buildSimpleContainer();

        String key1 = "key1";
        String value1 = "value1";

        assertNull(container.getContext().getState(key1, Object.class));

        String key2 = "key2";
        String value2 = "value2";

        assertNull(container.getContext().getState(key2, Object.class));

        // When the container chain is invoked, the handleRequest method of all
        // registered middlewares is called. In this case, the registered middleware
        // writes the provided value to the container context state. This way it is
        // possible to check if the addMiddleware method call was successful.
        Middleware middleware1 = MiddlewareBuilder.buildMiddleware(key1, value1);
        Middleware middleware2 = MiddlewareBuilder.buildMiddleware(key2, value2);
        container.addMiddlewares(Arrays.asList(middleware1, middleware2));
        container.invokeChain();

        assertEquals(value1, container.getContext().getState(key1, String.class));
        assertEquals(value2, container.getContext().getState(key2, String.class));
    }

    @Test
    public void testGetContext() throws Exception {
        SimpleContainer container = ContainerBuilder.buildSimpleContainer();

        assertNotNull(container.getContext());
    }

    @Test
    public void testOnError() throws Exception {
        SimpleContainer container = ContainerBuilder.buildSimpleContainer();

        String key = "key";
        String value = "value";

        container.setExceptionHandler(e -> container.getContext().setState(key, value));

        Middleware middleware = MiddlewareBuilder.buildThrowingMiddleware();
        container.addMiddleware(middleware);
        container.invokeChain();

        assertEquals(value, container.getContext().getState(key, String.class));
    }

    @Test
    public void testInvokeChain() throws Exception {
        this.testAddMiddleware();
    }
}