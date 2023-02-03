package com.softwareverde.util;

import com.softwareverde.constable.bytearray.ByteArray;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ByteBufferTests {
    @Before
    public void before() { }

    @After
    public void after() { }

    @Test
    public void should_get_all_bytes_when_all_bytes_are_valid_when_appended() throws Exception {
        // Setup
        final ByteBuffer buffer = new ByteBuffer();
        final int size = 1024;
        final int expectedIntCount = (size / 4);
        for (int i = 0; i < expectedIntCount; ++i) {
            byte[] bytes = java.nio.ByteBuffer.allocate(4).putInt(i).array();
            buffer.appendBytes(bytes, 4);
        }

        // Action
        final byte[] bytes = buffer.readBytes(size);

        // Assert
        final java.nio.IntBuffer intBuffer = java.nio.ByteBuffer.wrap(bytes).asIntBuffer();

        int bufferSize = 0;
        while (intBuffer.hasRemaining()) {
            final int bufferValue = intBuffer.get();
            Assert.assertEquals(bufferSize, bufferValue);
            bufferSize += 1;
        }
        Assert.assertEquals(expectedIntCount, bufferSize);
    }

    @Test
    public void should_get_all_bytes_when_all_bytes_are_not_valid_when_appended() throws Exception {
        // Setup
        final ByteBuffer buffer = new ByteBuffer();
        final int size = 1024;
        final int expectedIntCount = (size / 4);
        for (int i = 0; i < expectedIntCount; ++i) {
            byte[] bytes = java.nio.ByteBuffer.allocate(8).putInt(i).array();
            buffer.appendBytes(bytes, 4);
        }

        // Action
        final byte[] bytes = buffer.readBytes(size);

        // Assert
        final java.nio.IntBuffer intBuffer = java.nio.ByteBuffer.wrap(bytes).asIntBuffer();

        int bufferSize = 0;
        while (intBuffer.hasRemaining()) {
            final int bufferValue = intBuffer.get();

            Assert.assertEquals(bufferSize, bufferValue);
            bufferSize += 1;
        }
        Assert.assertEquals(expectedIntCount, bufferSize);
    }

    @Test
    public void should_get_bytes_from_mid_buffer() throws Exception {
        // Setup
        final ByteBuffer buffer = new ByteBuffer();
        final int size = 1024;
        final int intCount = (size / 4);
        for (int i = 0; i < intCount; ++i) {
            byte[] bytes = java.nio.ByteBuffer.allocate(4).putInt(i).array();
            buffer.appendBytes(bytes, 4);
        }

        for (int i = 0; i < intCount; ++i) {
            // Doesn't cross buffer boundary.
            final int expectedValue = i;
            final int readIndex = (i * 4);
            final int expectedIntCount = 1;

            // Action
            final byte[] bytes = buffer.getBytes(readIndex, 4);

            // Assert
            final java.nio.IntBuffer intBuffer = java.nio.ByteBuffer.wrap(bytes).asIntBuffer();

            int bufferSize = 0;
            while (intBuffer.hasRemaining()) {
                final int bufferValue = intBuffer.get();
                Assert.assertEquals(expectedValue, bufferValue);
                bufferSize += 1;
            }
            Assert.assertEquals(expectedIntCount, bufferSize);
        }
    }

    @Test
    public void should_get_bytes_from_mid_buffer_crossing_internal_buffer_boundary() throws Exception {
        // Setup
        final ByteBuffer buffer = new ByteBuffer();
        final int size = 1024;
        final int intCount = (size / 4);
        for (int i = 0; i < intCount; ++i) {
            byte[] bytes = java.nio.ByteBuffer.allocate(4).putInt(i).array();
            buffer.appendBytes(bytes, 4);
        }

        for (int i = 1; i < (intCount - 1); ++i) {
            // Doesn't cross buffer boundary.
            final int expectedValue = i;
            final int readIndex = (i * 4);

            // Action
            final byte[] bytes = buffer.getBytes(readIndex - 1, 6); // Read an extra byte before and after.

            // Assert
            final byte expectedPreviousValue = (byte) ((expectedValue - 1) & 0x000000FF);
            final byte expectedPostValue = 0; // (byte) ((expectedValue + 1) >> 24);

            final byte[] middleBytes = new byte[]{ bytes[1], bytes[2], bytes[3], bytes[4] };
            final int value = java.nio.ByteBuffer.wrap(middleBytes).asIntBuffer().get();

            Assert.assertEquals(expectedPreviousValue, bytes[0]);
            Assert.assertEquals(expectedValue, value);
            Assert.assertEquals(expectedPostValue, bytes[bytes.length - 1]);
        }
    }

    @Test
    public void should_get_bit() throws Exception {
        // Setup
        final ByteBuffer buffer = new ByteBuffer();
        final int size = 1024;
        final int intCount = (size / 4);
        for (int i = 0; i < intCount; ++i) {
            final int value = (i == 1 ? 1 : 0);
            byte[] bytes = java.nio.ByteBuffer.allocate(6).putInt(value).array();
            buffer.appendBytes(bytes, 4);
        }

        // Action
        Assert.assertFalse(buffer.getBit(0));
        Assert.assertFalse(buffer.getBit(1));
        Assert.assertFalse(buffer.getBit(32));
        Assert.assertFalse(buffer.getBit(62));
        Assert.assertTrue(buffer.getBit(63)); // 4 bytes of zero, 3 bytes of zero, 1 byte of 0x01
        Assert.assertFalse(buffer.getBit(64));
        Assert.assertFalse(buffer.getBit(1024));
    }

    @Test
    public void get_byte_should_properly_index_all_bytes() throws Exception {
        // Setup

        // This is a onvenient way to receive a ByteBuffer with multiple pages;
        //  if IoUtil.readCompressed changes it implementation then this test will need to be updated.
        final ByteArray buffer = IoUtil.readCompressed(IoUtil.getResourceAsStream("/00000000000000000C1A742EB218762F507FDF57DAFC02F8F23619AF92C5185E"));
        Assert.assertTrue(buffer instanceof ByteBuffer);

        // Action
        final ByteArray expectedBytes = ByteArray.wrap(buffer.getBytes());
        final Boolean areEqual = ByteUtil.areEqual(expectedBytes, buffer); // Iterates through the array one byte at a time via getByte.

        // Assert
        Assert.assertTrue(areEqual);
    }
}
