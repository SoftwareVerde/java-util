package com.softwareverde.util;

import org.junit.Assert;
import org.junit.Test;

public class VersionTests {
    @Test
    public void parseWithoutPrefixWithoutExtra() {
        // Setup
        final String input = "14.0.1";
        final Version expectedOutput = new Version(14, 0, 1, "", false);

        // Action
        final Version actualOutput = Version.parse(input);
        System.out.println("Actual: " + actualOutput);
        System.out.println("Expected: " + expectedOutput);
        System.out.println();

        // Assert
        Assert.assertEquals(expectedOutput.equals(actualOutput), true);
    }

    @Test
    public void parseWithPrefixWithoutExtra() {
        // Setup
        final String input = "v14.0.1";
        final Version expectedOutput = new Version(14, 0, 1, "", true);

        // Action
        final Version actualOutput = Version.parse(input);
        System.out.println("Actual: " + actualOutput);
        System.out.println("Expected: " + expectedOutput);
        System.out.println();

        // Assert
        Assert.assertEquals(expectedOutput.equals(actualOutput), true);
    }

    @Test
    public void parseWithPrefixWithExtraPlus() {
        // Setup
        final String input = "v14.0.1+7";
        final Version expectedOutput = new Version(14, 0, 1, "+7", true);

        // Action
        final Version actualOutput = Version.parse(input);
        System.out.println("Actual: " + actualOutput);
        System.out.println("Expected: " + expectedOutput);
        System.out.println();

        // Assert
        Assert.assertEquals(expectedOutput.equals(actualOutput), true);
    }

    @Test
    public void parseWithPrefixWithExtraDash() {
        // Setup
        final String input = "v14.0.1-7";
        final Version expectedOutput = new Version(14, 0, 1, "-7", true);

        // Action
        final Version actualOutput = Version.parse(input);
        System.out.println("Actual: " + actualOutput);
        System.out.println("Expected: " + expectedOutput);
        System.out.println();

        // Assert
        Assert.assertEquals(expectedOutput.equals(actualOutput), true);
    }

    @Test
    public void parseWithoutPrefixWithExtraPlus() {
        // Setup
        final String input = "14.0.1+7";
        final Version expectedOutput = new Version(14, 0, 1, "+7", false);

        // Action
        final Version actualOutput = Version.parse(input);
        System.out.println("Actual: " + actualOutput);
        System.out.println("Expected: " + expectedOutput);
        System.out.println();

        // Assert
        Assert.assertEquals(expectedOutput.equals(actualOutput), true);
    }

    @Test
    public void parseWithoutPrefixWithExtraDash() {
        // Setup
        final String input = "14.0.1-7";
        final Version expectedOutput = new Version(14, 0, 1, "-7", false);

        // Action
        final Version actualOutput = Version.parse(input);
        System.out.println("Actual: " + actualOutput);
        System.out.println("Expected: " + expectedOutput);
        System.out.println();

        // Assert
        Assert.assertEquals(expectedOutput.equals(actualOutput), true);
    }
}
