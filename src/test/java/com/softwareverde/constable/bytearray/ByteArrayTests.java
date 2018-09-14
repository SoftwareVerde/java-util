package com.softwareverde.constable.bytearray;

import org.junit.Assert;
import org.junit.Test;

public class ByteArrayTests {
    @Test
    public void should_get_bits_0() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(1);

        // Action
        byteArray.setBit(0, true);

        final byte b = byteArray.getByte(0);
        final boolean bit = byteArray.getBit(0);

        // Assert
        Assert.assertEquals((byte) 0b10000000, b);
        Assert.assertTrue(bit);
    }

    @Test
    public void should_get_bits_1() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(1);

        // Action
        byteArray.setBit(1, true);

        final byte b = byteArray.getByte(0);
        final boolean bit = byteArray.getBit(1);

        // Assert
        Assert.assertEquals((byte) 0b01000000, b);
        Assert.assertTrue(bit);
    }

    @Test
    public void should_get_bits_7() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(1);

        // Action
        byteArray.setBit(7, true);

        final byte b = byteArray.getByte(0);
        final boolean bit = byteArray.getBit(7);

        // Assert
        Assert.assertEquals((byte) 0b00000001, b);
        Assert.assertTrue(bit);
    }

    @Test
    public void should_get_bits_8() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(2);

        // Action
        byteArray.setBit(8, true);

        final byte b0 = byteArray.getByte(0);
        final byte b1 = byteArray.getByte(1);
        final boolean bit = byteArray.getBit(8);

        // Assert
        Assert.assertEquals(0x00, b0);
        Assert.assertEquals((byte) 0b10000000, b1);
        Assert.assertTrue(bit);
    }

    @Test
    public void should_get_bits_7_8() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(2);

        // Action
        byteArray.setBit(7, true);
        byteArray.setBit(8, true);

        final byte b0 = byteArray.getByte(0);
        final byte b1 = byteArray.getByte(1);
        final boolean bit7 = byteArray.getBit(7);
        final boolean bit8 = byteArray.getBit(8);

        // Assert
        Assert.assertEquals((byte) 0b00000001, b0);
        Assert.assertEquals((byte) 0b10000000, b1);
        Assert.assertTrue(bit7);
        Assert.assertTrue(bit8);
    }

    @Test
    public void should_throw_on_out_of_bounds() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(2);

        // Action
        try {
            byteArray.setBit(16, true);
            Assert.fail();
        }
        catch (final IndexOutOfBoundsException exception) { }
        catch (final Exception exception) {
            Assert.fail();
        }

        try {
            final Boolean isSet = byteArray.getBit(16);
            Assert.fail();
        }
        catch (final IndexOutOfBoundsException exception) { }
        catch (final Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void should_set_bits_0() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(1);
        byteArray.set(0, (byte) 0xFF);

        // Action
        byteArray.setBit(0, false);

        final byte b = byteArray.getByte(0);
        final boolean bit = byteArray.getBit(0);

        // Assert
        Assert.assertEquals((byte) 0b01111111, b);
        Assert.assertFalse(bit);
    }

    @Test
    public void should_set_bits_1() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(1);
        byteArray.set(0, (byte) 0xFF);

        // Action
        byteArray.setBit(1, false);

        final byte b = byteArray.getByte(0);
        final boolean bit = byteArray.getBit(1);

        // Assert
        Assert.assertEquals((byte) 0b10111111, b);
        Assert.assertFalse(bit);
    }

    @Test
    public void should_set_bits_7() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(1);
        byteArray.set(0, (byte) 0xFF);

        // Action
        byteArray.setBit(7, false);

        final byte b = byteArray.getByte(0);
        final boolean bit = byteArray.getBit(7);

        // Assert
        Assert.assertEquals((byte) 0b11111110, b);
        Assert.assertFalse(bit);
    }

    @Test
    public void should_set_bits_8() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(2);
        byteArray.set(0, (byte) 0xFF);
        byteArray.set(1, (byte) 0xFF);

        // Action
        byteArray.setBit(8, false);

        final byte b0 = byteArray.getByte(0);
        final byte b1 = byteArray.getByte(1);
        final boolean bit = byteArray.getBit(8);

        // Assert
        Assert.assertEquals((byte) 0b11111111, b0);
        Assert.assertEquals((byte) 0b01111111, b1);
        Assert.assertFalse(bit);
    }

    @Test
    public void should_set_bits_7_8() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(2);
        byteArray.set(0, (byte) 0xFF);
        byteArray.set(1, (byte) 0xFF);

        // Action
        byteArray.setBit(7, false);
        byteArray.setBit(8, false);

        final byte b0 = byteArray.getByte(0);
        final byte b1 = byteArray.getByte(1);
        final boolean bit7 = byteArray.getBit(7);
        final boolean bit8 = byteArray.getBit(8);

        // Assert
        Assert.assertEquals((byte) 0b11111110, b0);
        Assert.assertEquals((byte) 0b01111111, b1);
        Assert.assertFalse(bit7);
        Assert.assertFalse(bit8);
    }
}
