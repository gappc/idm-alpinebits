package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Context;
import it.idm.alpinebits.middleware.Middleware;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link ComposingMiddlewareBuilder} class.
 */
public class ComposingMiddlewareBuilderTest {

    private static final String KEY_1 = "key1";
    private static final String KEY_2 = "key2";
    private static final String KEY_3 = "key3";
    private static final String KEY_4 = "key4";
    private static final String VALUE_1 = "value1";
    private static final String VALUE_2 = "value2";
    private static final String VALUE_3 = "value3";
    private static final String VALUE_4 = "value4";

    @DataProvider(name = "testComposeDataProvider")
    public static Object[][] testComposeDataProvider() {
        return new Object[][]{
                {false, false, null},
                {false, true, null},
                {true, false, VALUE_2},
                {true, true, VALUE_2},
        };
    }

    @DataProvider(name = "testNestedComposeDataProvider")
    public static Object[][] testNestedComposeDataProvider() {
        return new Object[][]{
                {false, false, false, null},
                {false, false, true, null},
                {false, true, false, null},
                {false, true, true, null},
                {true, false, false, null},
                {true, false, true, null},
                {true, true, false, VALUE_3},
                {true, true, true, VALUE_3},
        };
    }


    @DataProvider(name = "testNestedComposeDataProvider_WithTwoComposedMiddlewares")
    public static Object[][] testNestedComposeDataProvider_WithTwoComposedMiddlewares() {
        return new Object[][]{
                {false, false, false, false, null},
                {false, false, false, true, null},
                {false, false, true, false, null},
                {false, false, true, true, null},
                {false, true, false, false, null},
                {false, true, false, true, null},
                {false, true, true, false, null},
                {false, true, true, true, null},
                {true, false, false, false, null},
                {true, false, false, true, null},
                {true, false, true, false, null},
                {true, false, true, true, null},
                {true, true, false, false, null},
                {true, true, false, true, null},
                {true, true, true, false, VALUE_4},
                {true, true, true, true, VALUE_4},
        };
    }

    @Test(dataProvider = "testComposeDataProvider")
    public void testCompose(
            boolean middleware1CallsNext,
            boolean middleware2CallsNext,
            String expectedValue
    ) throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware middleware1 = MiddlewareBuilder.buildMiddleware(KEY_1, VALUE_1, middleware1CallsNext);
        Middleware middleware2 = MiddlewareBuilder.buildMiddleware(KEY_2, VALUE_2, middleware2CallsNext);

        Middleware composedMiddleware = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware1, middleware2));
        composedMiddleware.handle(ctx, () -> {
        });

        assertEquals(ctx.get(KEY_1, String.class), VALUE_1);
        assertEquals(ctx.get(KEY_2, String.class), expectedValue);

        // Assert the value of the toString() method for 100% test coverage
        assertEquals(ComposingMiddlewareBuilder.COMPOSING_MIDDLEWARE_NAME, composedMiddleware.toString());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testComposeMiddlewareListNull() throws Exception {
        Middleware composedMiddleware = ComposingMiddlewareBuilder.compose(null);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testComposeOneMiddlewareNull() throws Exception {
        List<Middleware> middlewares = new ArrayList<>();
        middlewares.add(null);
        Middleware composedMiddleware = ComposingMiddlewareBuilder.compose(middlewares);
    }

    @Test(dataProvider = "testNestedComposeDataProvider")
    public void testNestedCompose_WhereComposedMiddlewareIsFirst(
            boolean middleware1CallsNext,
            boolean middleware2CallsNext,
            boolean middleware3CallsNext,
            String expectedValue
    ) throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware middleware1 = MiddlewareBuilder.buildMiddleware(KEY_1, VALUE_1, middleware1CallsNext);
        Middleware middleware2 = MiddlewareBuilder.buildMiddleware(KEY_2, VALUE_2, middleware2CallsNext);
        Middleware middleware3 = MiddlewareBuilder.buildMiddleware(KEY_3, VALUE_3, middleware3CallsNext);

        // Compose middlewares 1 and 2
        Middleware tmpComposedMiddleware = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware1, middleware2));

        // Compose tmpComposedMiddleware and middleware 3
        Middleware finalComposedMiddlewar = ComposingMiddlewareBuilder.compose(Arrays.asList(tmpComposedMiddleware, middleware3));

        finalComposedMiddlewar.handle(ctx, () -> {
        });

        assertEquals(ctx.get(KEY_1, String.class), VALUE_1);
        assertEquals(ctx.get(KEY_3, String.class), expectedValue);
    }

    @Test(dataProvider = "testNestedComposeDataProvider")
    public void testNestedCompose_WhereComposedMiddlewareIsLast(
            boolean middleware1CallsNext,
            boolean middleware2CallsNext,
            boolean middleware3CallsNext,
            String expectedValue
    ) throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware middleware1 = MiddlewareBuilder.buildMiddleware(KEY_1, VALUE_1, middleware1CallsNext);
        Middleware middleware2 = MiddlewareBuilder.buildMiddleware(KEY_2, VALUE_2, middleware2CallsNext);
        Middleware middleware3 = MiddlewareBuilder.buildMiddleware(KEY_3, VALUE_3, middleware3CallsNext);

        // Compose middlewares 2 and 3
        Middleware tmpComposedMiddleware = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware2, middleware3));

        // Compose middleware1 and tmpComposedMiddleware and middleware 3
        Middleware finalComposedMiddlewar = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware1, tmpComposedMiddleware));

        finalComposedMiddlewar.handle(ctx, () -> {
        });

        assertEquals(ctx.get(KEY_1, String.class), VALUE_1);
        assertEquals(ctx.get(KEY_3, String.class), expectedValue);
    }

    @Test(dataProvider = "testNestedComposeDataProvider_WithTwoComposedMiddlewares")
    public void testNestedCompose_WithTwoComposedMiddlewares(
            boolean middleware1CallsNext,
            boolean middleware2CallsNext,
            boolean middleware3CallsNext,
            boolean middleware4CallsNext,
            String expectedValue
    ) throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware middleware1 = MiddlewareBuilder.buildMiddleware(KEY_1, VALUE_1, middleware1CallsNext);
        Middleware middleware2 = MiddlewareBuilder.buildMiddleware(KEY_2, VALUE_2, middleware2CallsNext);
        Middleware middleware3 = MiddlewareBuilder.buildMiddleware(KEY_3, VALUE_3, middleware3CallsNext);
        Middleware middleware4 = MiddlewareBuilder.buildMiddleware(KEY_4, VALUE_4, middleware4CallsNext);

        // Compose middlewares 1 and 2
        Middleware tmpComposedMiddleware1 = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware1, middleware2));

        // Compose middlewares 3 and 4
        Middleware tmpComposedMiddleware2 = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware3, middleware4));

        // Compose middleware1 and tmpComposedMiddleware and middleware 3
        Middleware finalComposedMiddlewar = ComposingMiddlewareBuilder.compose(Arrays.asList(tmpComposedMiddleware1, tmpComposedMiddleware2));

        finalComposedMiddlewar.handle(ctx, () -> {
        });

        assertEquals(ctx.get(KEY_1, String.class), VALUE_1);
        assertEquals(ctx.get(KEY_4, String.class), expectedValue);
    }
}