package com.softwareverde.util;

import org.junit.Assert;
import org.junit.Test;

public class CircleBufferTests {
    @Test
    public void should_append_items_until_full_then_get_items_then_return_null() {
        // Setup
        final CircleBuffer<Integer> circleBuffer = new CircleBuffer<Integer>(3);

        final Integer[] poppedItems = new Integer[4];
        final Integer size;

        // Action
        circleBuffer.push(0);
        circleBuffer.push(1);
        circleBuffer.push(2);
        size = circleBuffer.getCount();

        poppedItems[0] = circleBuffer.pop();
        poppedItems[1] = circleBuffer.pop();
        poppedItems[2] = circleBuffer.pop();
        poppedItems[3] = circleBuffer.pop(); // Should be null...

        // Assert
        Assert.assertEquals(0, poppedItems[0].intValue());
        Assert.assertEquals(1, poppedItems[1].intValue());
        Assert.assertEquals(2, poppedItems[2].intValue());
        Assert.assertNull(poppedItems[3]);
        Assert.assertEquals(3, size.intValue());
    }

    @Test
    public void should_overwrite_oldest_value_after_overflow() {
        // Setup
        final CircleBuffer<Integer> circleBuffer = new CircleBuffer<Integer>(3);

        final Integer[] poppedItems = new Integer[4];
        final Integer size;

        // Action
        circleBuffer.push(0);
        circleBuffer.push(1);
        circleBuffer.push(2);
        circleBuffer.push(3); // Overflows and overwrites item at index 0...
        size = circleBuffer.getCount();

        poppedItems[0] = circleBuffer.pop();
        poppedItems[1] = circleBuffer.pop();
        poppedItems[2] = circleBuffer.pop();
        poppedItems[3] = circleBuffer.pop(); // Should be null...

        // Assert
        Assert.assertEquals(1, poppedItems[0].intValue());
        Assert.assertEquals(2, poppedItems[1].intValue());
        Assert.assertEquals(3, poppedItems[2].intValue());
        Assert.assertNull(poppedItems[3]);
        Assert.assertEquals(3, size.intValue());
    }

    @Test
    public void should_overwrite_oldest_value_after_lapped_overflow() {
        // Setup
        final CircleBuffer<Integer> circleBuffer = new CircleBuffer<Integer>(3);

        final Integer[] poppedItems = new Integer[4];
        final Integer size;

        // Action
        circleBuffer.push(0);
        circleBuffer.push(1);
        circleBuffer.push(2);
        circleBuffer.push(3); // Overflows and overwrites item at index 0...
        circleBuffer.push(4); // Overwrites item at index 1...
        circleBuffer.push(5); // Overwrites item at index 2...
        circleBuffer.push(6); // Overwrites item at index 0...
        circleBuffer.push(7); // Overwrites item at index 1...
        circleBuffer.push(8); // Overwrites item at index 2...
        size = circleBuffer.getCount();

        poppedItems[0] = circleBuffer.pop();
        poppedItems[1] = circleBuffer.pop();
        poppedItems[2] = circleBuffer.pop();
        poppedItems[3] = circleBuffer.pop(); // Should be null...

        // Assert
        Assert.assertEquals(6, poppedItems[0].intValue());
        Assert.assertEquals(7, poppedItems[1].intValue());
        Assert.assertEquals(8, poppedItems[2].intValue());
        Assert.assertNull(poppedItems[3]);
        Assert.assertEquals(3, size.intValue());
    }

    @Test
    public void should_report_correct_size_when_less_than_full() {
        // Setup
        final CircleBuffer<Integer> circleBuffer = new CircleBuffer<Integer>(3);

        final Integer[] poppedItems = new Integer[4];
        final Integer size;

        // Action
        circleBuffer.push(0);
        circleBuffer.push(1);
        size = circleBuffer.getCount();

        // Assert
        Assert.assertEquals(2, size.intValue());
    }

    @Test
    public void should_return_correct_values_when_reading_before_overwriting() {
        // Setup
        final CircleBuffer<Integer> circleBuffer = new CircleBuffer<Integer>(3);

        final Integer[] poppedItems = new Integer[4];
        final Integer size;

        // Action
        circleBuffer.push(0);
        poppedItems[0] = circleBuffer.pop();
        circleBuffer.push(1);
        poppedItems[1] = circleBuffer.pop();
        circleBuffer.push(2);
        poppedItems[2] = circleBuffer.pop();
        circleBuffer.push(3);
        size = circleBuffer.getCount();
        poppedItems[3] = circleBuffer.pop();


        // Assert
        Assert.assertEquals(0, poppedItems[0].intValue());
        Assert.assertEquals(1, poppedItems[1].intValue());
        Assert.assertEquals(2, poppedItems[2].intValue());
        Assert.assertEquals(3, poppedItems[3].intValue());
        Assert.assertEquals(1, size.intValue());
    }

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
