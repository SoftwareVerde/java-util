package com.softwareverde.util;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTests {
    @Test
    public void should_format_percent_with_two_decimals() {
        // Setup

        // Action
        final String percentString = StringUtil.formatPercent(100.00F);

        // Assert
        Assert.assertEquals("100.00%", percentString);
    }

    @Test
    public void should_format_percent_with_many_decimals() {
        // Setup

        // Action
        final String percentString = StringUtil.formatPercent(123.45678F);

        // Assert
        Assert.assertEquals("123.46%", percentString);
    }

    @Test
    public void should_format_percent_without_comma() {
        // Setup

        // Action
        final String percentString = StringUtil.formatPercent(1234.5678F);

        // Assert
        Assert.assertEquals("1234.57%", percentString);
    }

    @Test
    public void should_format_number_with_comma() {
        // Setup

        // Action
        final String numberString = StringUtil.formatNumberString(123456);

        // Assert
        Assert.assertEquals("123,456", numberString);
    }

    @Test
    public void should_format_number_when_null() {
        // Setup

        // Action
        final String numberString = StringUtil.formatNumberString(null);

        // Assert
        Assert.assertEquals(null, numberString);
    }
}
