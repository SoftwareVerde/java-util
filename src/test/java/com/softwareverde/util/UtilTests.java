package com.softwareverde.util;

import org.junit.Assert;
import org.junit.Test;

public class UtilTests {
    @Test
    public void isBool_should_return_true_when_string_true() {
        // Setup
        final String stringValue = "true";

        // Action
        final Boolean isBoolean = Util.isBool(stringValue);

        // Assert
        Assert.assertTrue(isBoolean);
    }

    @Test
    public void isBool_should_return_true_when_string_false() {
        // Setup
        final String stringValue = "false";

        // Action
        final Boolean isBoolean = Util.isBool(stringValue);

        // Assert
        Assert.assertTrue(isBoolean);
    }

    @Test
    public void isBool_should_return_true_when_string_positive_integer() {
        // Setup
        final String stringValue = "1000";

        // Action
        final Boolean isBoolean = Util.isBool(stringValue);

        // Assert
        Assert.assertTrue(isBoolean);
    }

    @Test
    public void isBool_should_return_true_when_string_negative_integer() {
        // Setup
        final String stringValue = "-1000";

        // Action
        final Boolean isBoolean = Util.isBool(stringValue);

        // Assert
        Assert.assertTrue(isBoolean);
    }

    @Test
    public void isBool_should_return_true_when_string_zero() {
        // Setup
        final String stringValue = "0";

        // Action
        final Boolean isBoolean = Util.isBool(stringValue);

        // Assert
        Assert.assertTrue(isBoolean);
    }

    @Test
    public void isBool_should_return_false_when_string_without_number() {
        // Setup
        final String stringValue = "An Angry Duck";

        // Action
        final Boolean isBoolean = Util.isBool(stringValue);

        // Assert
        Assert.assertFalse(isBoolean);
    }

    @Test
    public void isBool_should_return_false_when_string_contains_a_number() {
        // Setup
        final String stringValue = "Angry 1 Duck";

        // Action
        final Boolean isBoolean = Util.isBool(stringValue);

        // Assert
        Assert.assertFalse(isBoolean);
    }

    @Test
    public void isBool_should_return_false_when_string_starts_with_a_number() {
        // Setup
        final String stringValue = "1 Angry Duck";

        // Action
        final Boolean isBoolean = Util.isBool(stringValue);

        // Assert
        Assert.assertFalse(isBoolean);
    }

    @Test
    public void areEqual_returns_true_when_comparing_null_values() {
        // Setup
        final Object objectA = null;
        final Object objectB = null;

        // Action
        final Boolean areEqual = Util.areEqual(objectA, objectB);

        // Assert
        Assert.assertTrue(areEqual);
    }

    @Test
    public void areEqual_integer_and_long_of_equal_value_should_be_equal() {
        // Setup
        final Long oneLong = 1L;
        final Integer oneInteger = 1;

        // Action
        final Boolean areEqual = Util.areEqual(oneLong, oneInteger);

        // Assert
        Assert.assertTrue(areEqual);
    }

    @Test
    public void areEqual_integer_and_long_of_equal_value_should_be_equal_2() {
        // Setup
        final Long longValue = (long) Integer.MAX_VALUE;
        final Integer integerValue = Integer.MAX_VALUE;

        // Action
        final Boolean areEqual = Util.areEqual(longValue, integerValue);

        // Assert
        Assert.assertTrue(areEqual);
    }

    @Test
    public void areEqual_integer_and_long_of_equal_value_should_be_equal_3() {
        // Setup
        final Float floatValueA = Float.MAX_VALUE;
        final Float floatValueB = Float.MAX_VALUE;

        // Action
        final Boolean areEqual = Util.areEqual(floatValueA, floatValueB);

        // Assert
        Assert.assertTrue(areEqual);
    }

    @Test
    public void areEqual_integer_and_long_of_equal_value_should_be_equal_4() {
        // Setup
        final Integer integerValueA = 0;
        final Float floatValueB = 0F;

        // Action
        final Boolean areEqual = Util.areEqual(integerValueA, floatValueB);

        // Assert
        Assert.assertTrue(areEqual);
    }

    @Test
    public void areEqual_integer_and_long_of_unequal_value_should_not_be_equal() {
        // Setup
        final Long longValue = Long.MAX_VALUE;
        final Integer integerValue = (int) Long.MAX_VALUE;

        // Action
        final Boolean areEqual = Util.areEqual(longValue, integerValue);

        // Assert
        Assert.assertFalse(areEqual);
    }

    @Test
    public void areEqual_integer_and_long_of_unequal_value_should_not_be_equal_1() {
        // Setup
        final Long longValue = (Integer.MAX_VALUE + 1L);
        final Integer integerValue = (Integer.MAX_VALUE + 1);

        // Action
        final Boolean areEqual = Util.areEqual(longValue, integerValue);

        // Assert
        Assert.assertFalse(areEqual);
    }

    @Test
    public void areEqual_integer_and_float_of_unequal_value_should_not_be_equal_2() {
        // Setup
        final Integer integerValue = 0;
        final Float floatValue = Float.MIN_NORMAL;

        // Action
        final Boolean areEqual = Util.areEqual(integerValue, floatValue);

        // Assert
        Assert.assertFalse(areEqual);
    }

    @Test
    public void areEqual_integer_and_long_of_unequal_value_should_not_be_equal_2() {
        // Setup
        final Long longValue = 1L;
        final Integer integerValue = 2;

        // Action
        final Boolean areEqual = Util.areEqual(longValue, integerValue);

        // Assert
        Assert.assertFalse(areEqual);
    }
}
