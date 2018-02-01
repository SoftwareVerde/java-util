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
}
