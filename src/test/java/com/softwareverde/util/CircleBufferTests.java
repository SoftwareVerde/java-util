package com.softwareverde.util;

import org.junit.Assert;
import org.junit.Test;

public class CircleBufferTests {
    @Test
    public void should_return_correct_items_after_push_and_pop_via_get_and_iteration() {
        final CircleBuffer<Integer> circleBuffer = new CircleBuffer<Integer>(4);
        Assert.assertNull(circleBuffer.get(0));

        circleBuffer.push(0);

        Assert.assertEquals(Integer.valueOf(0), circleBuffer.get(0));

        circleBuffer.push(1);

        Assert.assertEquals(Integer.valueOf(0), circleBuffer.get(0));
        Assert.assertEquals(Integer.valueOf(1), circleBuffer.get(1));

        circleBuffer.push(2);

        Assert.assertEquals(Integer.valueOf(0), circleBuffer.get(0));
        Assert.assertEquals(Integer.valueOf(1), circleBuffer.get(1));
        Assert.assertEquals(Integer.valueOf(2), circleBuffer.get(2));

        circleBuffer.push(3);

        Assert.assertEquals(Integer.valueOf(0), circleBuffer.get(0));
        Assert.assertEquals(Integer.valueOf(1), circleBuffer.get(1));
        Assert.assertEquals(Integer.valueOf(2), circleBuffer.get(2));
        Assert.assertEquals(Integer.valueOf(3), circleBuffer.get(3));

        circleBuffer.push(4);

        Assert.assertEquals(Integer.valueOf(1), circleBuffer.get(0));
        Assert.assertEquals(Integer.valueOf(2), circleBuffer.get(1));
        Assert.assertEquals(Integer.valueOf(3), circleBuffer.get(2));
        Assert.assertEquals(Integer.valueOf(4), circleBuffer.get(3));

        final Integer poppedItem = circleBuffer.pop();

        Assert.assertEquals(Integer.valueOf(1), poppedItem);
        Assert.assertEquals(Integer.valueOf(2), circleBuffer.get(0));
        Assert.assertEquals(Integer.valueOf(3), circleBuffer.get(1));
        Assert.assertEquals(Integer.valueOf(4), circleBuffer.get(2));
        Assert.assertNull(circleBuffer.get(3));

        int i = 2;
        for (final Integer item : circleBuffer) {
            Assert.assertEquals(Integer.valueOf(i++), item);
        }
    }
}
