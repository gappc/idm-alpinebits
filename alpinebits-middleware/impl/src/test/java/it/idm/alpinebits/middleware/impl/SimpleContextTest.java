package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Container;
import it.idm.alpinebits.middleware.Context;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test cases for {@link SimpleContext} class.
 */
public class SimpleContextTest {

    @Test
    public void testGetContainer() throws Exception {
        Container container = new SimpleContainer();
        Context ctx = new SimpleContext(container);
        assertEquals(container, ctx.getContainer());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInitEmptyContainer() throws Exception {
        new SimpleContext(null);
    }

    @Test
    public void testGetState() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        int intValue = 1;
        ctx.setState(intKey, intValue);
        assertEquals(intValue, ctx.getState(intKey, Integer.class).intValue());

        String longKey = "longKey";
        Long longValue = 2L;
        ctx.setState(longKey, longValue);
        assertEquals(longValue, ctx.getState(longKey, Long.class));

        String stringKey = "stringKey";
        String stringValue = "one";
        ctx.setState(stringKey, stringValue);
        assertEquals(stringValue, ctx.getState(stringKey, String.class));

        String objectKey = "objectKey";
        Object objectValue = new Object();
        ctx.setState(objectKey, objectValue);
        assertEquals(objectValue, ctx.getState(objectKey, Object.class));
    }

    @Test
    public void testSetState() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        int intValue1 = 1;
        ctx.setState(intKey, intValue1);
        assertEquals(intValue1, ctx.getState(intKey, Integer.class).intValue());

        int intValue2 = 2;
        ctx.setState(intKey, intValue2);
        assertEquals(intValue2, ctx.getState(intKey, Integer.class).intValue());
    }

    @Test
    public void testRemoveState() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        int intValue = 1;
        ctx.setState(intKey, intValue);
        assertEquals(intValue, ctx.getState(intKey, Integer.class).intValue());

        int removedValue = (int)ctx.removeState(intKey);

        assertEquals(intValue, removedValue);
        assertNull(ctx.getState(intKey, Integer.class));
    }

    @Test
    public void testStateContainsKey() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        int intValue = 1;
        ctx.setState(intKey, intValue);

        assertTrue(ctx.stateContainsKey(intKey));
        assertFalse(ctx.stateContainsKey("not existing key"));
    }
}