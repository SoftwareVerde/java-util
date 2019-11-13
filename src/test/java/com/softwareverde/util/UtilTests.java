package com.softwareverde.util;

import com.softwareverde.constable.bytearray.MutableByteArray;
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
    public void parseBool_should_return_true_when_string_true() {
        // Setup
        final String stringValue = "true";

        // Action
        final Boolean booleanValue = Util.parseBool(stringValue);

        // Assert
        Assert.assertTrue(booleanValue);
    }

    @Test
    public void parseBool_should_return_false_when_false() {
        // Setup
        final String stringValue = "false";

        // Action
        final Boolean booleanValue = Util.parseBool(stringValue);

        // Assert
        Assert.assertFalse(booleanValue);
    }

    @Test
    public void parseBool_should_return_false_when_random_string() {
        // Setup
        final String stringValue = "abcd";

        // Action
        final Boolean booleanValue = Util.parseBool(stringValue);

        // Assert
        Assert.assertFalse(booleanValue);
    }

    @Test
    public void parseBool_should_return_false_when_zero() {
        // Setup
        final String stringValue = "0";

        // Action
        final Boolean booleanValue = Util.parseBool(stringValue);

        // Assert
        Assert.assertFalse(booleanValue);
    }

    @Test
    public void parseBool_should_return_true_when_positive_integer() {
        // Setup
        final String stringValue = "4";

        // Action
        final Boolean booleanValue = Util.parseBool(stringValue);

        // Assert
        Assert.assertTrue(booleanValue);
    }

    @Test
    public void parseBool_should_return_false_when_negative_integer() {
        // Setup
        final String stringValue = "-4";

        // Action
        final Boolean booleanValue = Util.parseBool(stringValue);

        // Assert
        Assert.assertFalse(booleanValue);
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

    @Test
    public void areEqual_should_be_equal_for_floats() {
        Assert.assertTrue(Util.areEqual(0F, 0F, 0.000001F));
        Assert.assertTrue(Util.areEqual(0.205F, 0.200F, 0.01F));
        Assert.assertTrue(Util.areEqual(0.200F, 0.205F, 0.01F));

        Assert.assertFalse(Util.areEqual(0F, 0.00001F, 0.000001F));
        Assert.assertFalse(Util.areEqual(0.205F, 0.200F, 0.001F));
        Assert.assertFalse(Util.areEqual(0.200F, 0.205F, 0.001F));

        Assert.assertTrue(Util.areEqual(0D, 0D, 0.000001D));
        Assert.assertTrue(Util.areEqual(0.205D, 0.200D, 0.01D));
        Assert.assertTrue(Util.areEqual(0.200D, 0.205D, 0.01D));

        Assert.assertFalse(Util.areEqual(0D, 0.00001D, 0.000001D));
        Assert.assertFalse(Util.areEqual(0.205D, 0.200D, 0.001D));
        Assert.assertFalse(Util.areEqual(0.200D, 0.205D, 0.001D));
    }

    @Test
    public void areEqual_should_be_equal_for_jbyteArrays() {
        Assert.assertTrue(Util.areEqual(new byte[]{ 0x00, 0x01 }, new byte[]{ 0x00, 0x01 }));
        Assert.assertFalse(Util.areEqual(new byte[]{ 0x00, 0x00 }, new byte[]{ 0x00, 0x01 }));

        Assert.assertTrue(Util.areEqual(new byte[]{ 0x00, 0x01 }, MutableByteArray.wrap(new byte[]{ 0x00, 0x01 })));
        Assert.assertFalse(Util.areEqual(new byte[]{ 0x00, 0x02 }, MutableByteArray.wrap(new byte[]{ 0x00, 0x01 })));

        Assert.assertTrue(Util.areEqual(MutableByteArray.wrap(new byte[]{ 0x00, 0x01 }), new byte[]{ 0x00, 0x01 }));
        Assert.assertFalse(Util.areEqual(MutableByteArray.wrap(new byte[]{ 0x00, 0x02 }), new byte[]{ 0x00, 0x01 }));

        Assert.assertTrue(Util.areEqual(new MutableByteArray(0), new byte[]{ }));
        Assert.assertFalse(Util.areEqual(new MutableByteArray(1), new byte[]{ }));

        Assert.assertTrue(Util.areEqual(new byte[]{ }, new MutableByteArray(0)));
        Assert.assertFalse(Util.areEqual(new byte[]{ }, new MutableByteArray(1)));
    }
}
