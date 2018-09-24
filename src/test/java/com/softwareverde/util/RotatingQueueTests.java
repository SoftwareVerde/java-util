package com.softwareverde.util;

import org.junit.Assert;
import org.junit.Test;

public class RotatingQueueTests {
    @Test
    public void should_drop_old_items_past_max_capacity() {
        // Setup
        final RotatingQueue<String> rotatingQueue = new RotatingQueue<String>(5);

        // Action
        rotatingQueue.add("0");
        rotatingQueue.add("1");
        rotatingQueue.add("2");
        rotatingQueue.add("3");
        rotatingQueue.add("4");
        rotatingQueue.add("5");

        // Assert
        Assert.assertEquals(5, rotatingQueue.size());
        Assert.assertEquals("1", rotatingQueue.remove());
        Assert.assertEquals("2", rotatingQueue.remove());
        Assert.assertEquals("3", rotatingQueue.remove());
        Assert.assertEquals("4", rotatingQueue.remove());
        Assert.assertEquals("5", rotatingQueue.remove());
        Assert.assertTrue(rotatingQueue.isEmpty());
    }
}
