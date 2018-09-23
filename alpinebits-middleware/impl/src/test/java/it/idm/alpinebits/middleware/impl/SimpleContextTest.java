package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Context;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test cases for {@link SimpleContext} class.
 */
public class SimpleContextTest {

    @Test
    public void testGetState() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        int intValue = 1;
        ctx.set(intKey, intValue);
        assertEquals(intValue, ctx.get(intKey, Integer.class).intValue());

        String longKey = "longKey";
        Long longValue = 2L;
        ctx.set(longKey, longValue);
        assertEquals(longValue, ctx.get(longKey, Long.class));

        String stringKey = "stringKey";
        String stringValue = "one";
        ctx.set(stringKey, stringValue);
        assertEquals(stringValue, ctx.get(stringKey, String.class));

        String objectKey = "objectKey";
        Object objectValue = new Object();
        ctx.set(objectKey, objectValue);
        assertEquals(objectValue, ctx.get(objectKey, Object.class));
    }

    @Test
    public void testSetState() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        int intValue1 = 1;
        ctx.set(intKey, intValue1);
        assertEquals(intValue1, ctx.get(intKey, Integer.class).intValue());

        int intValue2 = 2;
        ctx.set(intKey, intValue2);
        assertEquals(intValue2, ctx.get(intKey, Integer.class).intValue());
    }

    @Test
    public void testRemoveState() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        int intValue = 1;
        ctx.set(intKey, intValue);
        assertEquals(intValue, ctx.get(intKey, Integer.class).intValue());

        int removedValue = (int)ctx.remove(intKey);

        assertEquals(intValue, removedValue);
        assertNull(ctx.get(intKey, Integer.class));
    }

    @Test
    public void testStateContainsKey() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        int intValue = 1;
        ctx.set(intKey, intValue);

        assertTrue(ctx.contains(intKey));
        assertFalse(ctx.contains("not existing key"));
    }
}