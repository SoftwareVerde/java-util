package com.softwareverde.constable.bytearray;

import com.softwareverde.util.bytearray.ByteArrayBuilder;
import org.junit.Assert;
import org.junit.Test;

public class ByteArrayBuilderTests {
    @Test
    public void should_get_sub_bytes() {
        // Setup
        final ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendBytes(new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09 });
        byteArrayBuilder.appendBytes(new byte[] { 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F });
        byteArrayBuilder.appendBytes(new byte[] { 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19 });
        byteArrayBuilder.appendBytes(new byte[] { 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F });

        final byte[] concatenatedBytes = new byte[] {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F
        };

        // Assert
        Assert.assertEquals(32, byteArrayBuilder.getByteCount());
        Assert.assertEquals(32L, byteArrayBuilder.getLongByteCount());

        Assert.assertEquals(0x00, byteArrayBuilder.getByte(0));
        Assert.assertEquals(0x09, byteArrayBuilder.getByte(9));
        Assert.assertEquals(0x0A, byteArrayBuilder.getByte(10));
        Assert.assertEquals(0x0F, byteArrayBuilder.getByte(15));
        Assert.assertEquals(0x10, byteArrayBuilder.getByte(16));
        Assert.assertEquals(0x19, byteArrayBuilder.getByte(25));
        Assert.assertEquals(0x1A, byteArrayBuilder.getByte(26));
        Assert.assertEquals(0x1F, byteArrayBuilder.getByte(31));

        Assert.assertArrayEquals(new byte[] { 0x00, 0x01, 0x02 }, byteArrayBuilder.getBytes(0, 3));
        Assert.assertArrayEquals(new byte[] { 0x09, 0x0A }, byteArrayBuilder.getBytes(9, 2));
        Assert.assertArrayEquals(new byte[] { 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10, 0x11 }, byteArrayBuilder.getBytes(8, 10));
        Assert.assertArrayEquals(concatenatedBytes, byteArrayBuilder.getBytes());
        Assert.assertArrayEquals(concatenatedBytes, byteArrayBuilder.getBytes(0, concatenatedBytes.length));

        Assert.assertFalse(byteArrayBuilder.getBit(0L));
        Assert.assertTrue(byteArrayBuilder.getBit(15L));
        Assert.assertTrue(byteArrayBuilder.getBit(255L));
    }
}
