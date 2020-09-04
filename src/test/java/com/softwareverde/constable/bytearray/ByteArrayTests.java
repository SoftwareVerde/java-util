package com.softwareverde.constable.bytearray;

import com.softwareverde.util.ByteUtil;
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
        byteArray.setByte(0, (byte) 0xFF);

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
        byteArray.setByte(0, (byte) 0xFF);

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
        byteArray.setByte(0, (byte) 0xFF);

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
        byteArray.setByte(0, (byte) 0xFF);
        byteArray.setByte(1, (byte) 0xFF);

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
        byteArray.setByte(0, (byte) 0xFF);
        byteArray.setByte(1, (byte) 0xFF);

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

    @Test
    public void should_get_last_bit_of_max_size_byte_array() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(ByteArray.MAX_BYTE_COUNT);
        final int byteIndex = (ByteArray.MAX_BYTE_COUNT - 1);
        byteArray.setByte(byteIndex, (byte) 0xFF);

        // Action
        byteArray.setBit((byteIndex * 8L) + 6L, false);
        byteArray.setBit((byteIndex * 8L) + 7L, false);

        final byte b0 = byteArray.getByte(byteIndex);
        final boolean bit7 = byteArray.getBit((byteIndex * 8L) + 6L);
        final boolean bit8 = byteArray.getBit((byteIndex * 8L) + 7L);

        // Assert
        Assert.assertEquals((byte) 0b11111100, b0);
        Assert.assertFalse(bit7);
        Assert.assertFalse(bit8);
    }

    @Test
    public void getBytes_should_get_last_byte_of_byte_array() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(1);
        byteArray.setByte(0, (byte) 0xFF);

        // Action
        final byte[] bytes = byteArray.getBytes(0, 1);

        // Assert
        Assert.assertTrue(ByteUtil.areEqual(new byte[] { (byte) 0xFF }, bytes));
    }

    @Test
    public void getBytes_should_throw_when_getting_bytes_past_end_of_byte_array() {
        // Setup
        final MutableByteArray byteArray = new MutableByteArray(1);
        byteArray.setByte(0, (byte) 0xFF);

        // Action
        byte[] bytes = null;
        Exception exception = null;
        try {
            bytes = byteArray.getBytes(0, 2);
        }
        catch (final Exception thrownException) {
            exception = thrownException;
        }

        // Assert
        Assert.assertTrue(exception instanceof IndexOutOfBoundsException);
        Assert.assertNull(bytes);
    }

    @Test
    public void immutable_getBytes_should_get_last_byte_of_byte_array() {
        // Setup
        final ImmutableByteArray byteArray = new ImmutableByteArray(new byte[]{ (byte) 0xFF });

        // Action
        final byte[] bytes = byteArray.getBytes(0, 1);

        // Assert
        Assert.assertTrue(ByteUtil.areEqual(new byte[] { (byte) 0xFF }, bytes));
    }

    @Test
    public void immutable_getBytes_should_throw_when_getting_bytes_past_end_of_byte_array() {
        // Setup
        final ImmutableByteArray byteArray = new ImmutableByteArray(new byte[]{ (byte) 0xFF });

        // Action
        byte[] bytes = null;
        Exception exception = null;
        try {
            bytes = byteArray.getBytes(0, 2);
        }
        catch (final Exception thrownException) {
            exception = thrownException;
        }

        // Assert
        Assert.assertTrue(exception instanceof IndexOutOfBoundsException);
        Assert.assertNull(bytes);
    }

    @Test
    public void identical_arrays_should_have_same_hash_code() {
        { // Length = 8
            final ByteArray byteArray0 = new MutableByteArray(new byte[]{ 0, 1, 2, 3, 4, 5, 6, 7 });
            final ByteArray byteArray1 = new MutableByteArray(new byte[]{ 0, 1, 2, 3, 4, 5, 6, 7 });
            final int hashCode0 = byteArray0.hashCode();
            final int hashCode1 = byteArray1.hashCode();
            Assert.assertEquals(hashCode0, hashCode1);
            Assert.assertEquals(8, byteArray0.getByteCount());
        }

        { // Length = 9
            final ByteArray byteArray0 = new MutableByteArray(new byte[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8 });
            final ByteArray byteArray1 = new MutableByteArray(new byte[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8 });
            final int hashCode0 = byteArray0.hashCode();
            final int hashCode1 = byteArray1.hashCode();
            Assert.assertEquals(hashCode0, hashCode1);
            Assert.assertEquals(9, byteArray0.getByteCount());
        }

        { // Length = 17
            final ByteArray byteArray0 = new MutableByteArray(new byte[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 });
            final ByteArray byteArray1 = new MutableByteArray(new byte[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 });
            final int hashCode0 = byteArray0.hashCode();
            final int hashCode1 = byteArray1.hashCode();
            Assert.assertEquals(hashCode0, hashCode1);
            Assert.assertEquals(17, byteArray0.getByteCount());
        }
    }
}
