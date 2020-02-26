package com.softwareverde.util;

import org.junit.Assert;
import org.junit.Test;

public class TupleTests {
    @Test
    public void should_be_equal() {
        Assert.assertEquals(new Tuple<Object, Object>(null, null), new Tuple<Object, Object>(null, null));
        Assert.assertEquals(new Tuple<Object, Object>(0, null), new Tuple<Object, Object>(0, null));
        Assert.assertEquals(new Tuple<Object, Object>(null, 0), new Tuple<Object, Object>(null, 0));

        Assert.assertEquals(new Tuple<Integer, Integer>(0, 0), new Tuple<Integer, Integer>(0, 0));
        Assert.assertEquals(new Tuple<Integer, Integer>(1, 0), new Tuple<Integer, Integer>(1, 0));
        Assert.assertEquals(new Tuple<Integer, Integer>(0, 1), new Tuple<Integer, Integer>(0, 1));
        Assert.assertEquals(new Tuple<Integer, Integer>(-10, 0), new Tuple<Integer, Integer>(-10, 0));
        Assert.assertEquals(new Tuple<Integer, Integer>(0, -10), new Tuple<Integer, Integer>(0, -10));

        Assert.assertEquals(new Tuple<String, String>("0", "0"), new Tuple<String, String>("0", "0"));
        Assert.assertEquals(new Tuple<String, String>("1", "0"), new Tuple<String, String>("1", "0"));
        Assert.assertEquals(new Tuple<String, String>("0", "1"), new Tuple<String, String>("0", "1"));
        Assert.assertEquals(new Tuple<String, String>("-10", "0"), new Tuple<String, String>("-10", "0"));
        Assert.assertEquals(new Tuple<String, String>("0", "-10"), new Tuple<String, String>("0", "-10"));
    }

    @Test
    public void should_not_be_equal() {
        Assert.assertNotEquals(new Tuple<Object, Object>(0, 0), new Tuple<Object, Object>(null, null));
        Assert.assertNotEquals(new Tuple<Object, Object>(0, 0), new Tuple<Object, Object>(0, null));
        Assert.assertNotEquals(new Tuple<Object, Object>(0, 0), new Tuple<Object, Object>(null, 0));
        Assert.assertNotEquals(new Tuple<Object, Object>(null, 0), new Tuple<Object, Object>(0, 0));
        Assert.assertNotEquals(new Tuple<Object, Object>(0, null), new Tuple<Object, Object>(0, 0));

        Assert.assertNotEquals(new Tuple<Integer, Integer>(0, 0), new Tuple<Integer, Integer>(1, 0));
        Assert.assertNotEquals(new Tuple<Integer, Integer>(0, 0), new Tuple<Integer, Integer>(0, 1));
        Assert.assertNotEquals(new Tuple<Integer, Integer>(1, 0), new Tuple<Integer, Integer>(0, 0));
        Assert.assertNotEquals(new Tuple<Integer, Integer>(0, 1), new Tuple<Integer, Integer>(0, 0));

        Assert.assertNotEquals(new Tuple<String, String>("0", "0"), new Tuple<String, String>("1", "0"));
        Assert.assertNotEquals(new Tuple<String, String>("0", "0"), new Tuple<String, String>("0", "1"));
        Assert.assertNotEquals(new Tuple<String, String>("1", "0"), new Tuple<String, String>("0", "0"));
        Assert.assertNotEquals(new Tuple<String, String>("0", "1"), new Tuple<String, String>("0", "0"));
    }

    @Test
    public void hashcode_should_be_equal_when_equal() {
        Assert.assertEquals((new Tuple<Object, Object>(null, null)).hashCode(), (new Tuple<Object, Object>(null, null)).hashCode());
        Assert.assertEquals((new Tuple<Object, Object>(0, null)).hashCode(), (new Tuple<Object, Object>(0, null)).hashCode());
        Assert.assertEquals((new Tuple<Object, Object>(null, 0)).hashCode(), (new Tuple<Object, Object>(null, 0)).hashCode());

        Assert.assertEquals((new Tuple<Integer, Integer>(0, 0)).hashCode(), (new Tuple<Integer, Integer>(0, 0)).hashCode());
        Assert.assertEquals((new Tuple<Integer, Integer>(1, 0)).hashCode(), (new Tuple<Integer, Integer>(1, 0)).hashCode());
        Assert.assertEquals((new Tuple<Integer, Integer>(0, 1)).hashCode(), (new Tuple<Integer, Integer>(0, 1)).hashCode());
        Assert.assertEquals((new Tuple<Integer, Integer>(-10, 0)).hashCode(), (new Tuple<Integer, Integer>(-10, 0)).hashCode());
        Assert.assertEquals((new Tuple<Integer, Integer>(0, -10)).hashCode(), (new Tuple<Integer, Integer>(0, -10)).hashCode());

        Assert.assertEquals((new Tuple<String, String>("0", "0")).hashCode(), (new Tuple<String, String>("0", "0")).hashCode());
        Assert.assertEquals((new Tuple<String, String>("1", "0")).hashCode(), (new Tuple<String, String>("1", "0")).hashCode());
        Assert.assertEquals((new Tuple<String, String>("0", "1")).hashCode(), (new Tuple<String, String>("0", "1")).hashCode());
        Assert.assertEquals((new Tuple<String, String>("-10", "0")).hashCode(), (new Tuple<String, String>("-10", "0")).hashCode());
        Assert.assertEquals((new Tuple<String, String>("0", "-10")).hashCode(), (new Tuple<String, String>("0", "-10")).hashCode());
    }
}
