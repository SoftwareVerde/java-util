package com.softwareverde.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class IoUtilTests {
    @Test
    public void file_reading_variants_should_create_same_data_as_written_to_file() throws IOException {
        // Setup
        final byte[] rawData = HexUtil.hexStringToByteArray("0102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F");
        final File tempFile = File.createTempFile("test", "dat");
        IoUtil.putFileContents(tempFile, rawData);

        // Action
        final byte[] data1 = IoUtil.getFileContents(tempFile);
        final byte[] data2 = IoUtil.getFileContents(tempFile.getAbsolutePath());
        final byte[] data3 = IoUtil.readStreamOrThrow(new FileInputStream(tempFile));
        final byte[] data4 = IoUtil.readStreamOrThrow(new FileInputStream(tempFile), tempFile.length());

        // Test
        Assert.assertTrue(Util.areEqual(rawData, data1));
        Assert.assertTrue(Util.areEqual(rawData, data2));
        Assert.assertTrue(Util.areEqual(rawData, data3));
        Assert.assertTrue(Util.areEqual(rawData, data4));
    }

    @Test
    public void file_reading_with_length_too_large_should_throw_an_exception() throws IOException {
        // Setup
        final byte[] rawData = HexUtil.hexStringToByteArray("101112131415161718191A1B1C1D1E1F0102030405060708090A0B0C0D0E0F");
        final File tempFile = File.createTempFile("test", "dat");
        IoUtil.putFileContents(tempFile, rawData);

        // Action
        try {
            final byte[] dataRead = IoUtil.readStreamOrThrow(new FileInputStream(tempFile), tempFile.length() + 1);
            Assert.fail("readStreamOrThrow should fail on stream with less data than expected.");
        }
        catch (IOException exception) {
            // expected
        }
    }

    @Test
    public void file_reading_with_length_too_small_should_throw_an_exception() throws IOException {
        // Setup
        final byte[] rawData = HexUtil.hexStringToByteArray("101112131415161718191A1B1C1D1E1F0102030405060708090A0B0C0D0E0F");
        final File tempFile = File.createTempFile("test", "dat");
        IoUtil.putFileContents(tempFile, rawData);

        // Action
        try {
            final byte[] dataRead = IoUtil.readStreamOrThrow(new FileInputStream(tempFile), tempFile.length() - 1);
            Assert.fail("readStreamOrThrow should fail on stream with more data than expected.");
        }
        catch (IOException exception) {
            // expected
        }
    }

    @Test
    public void reading_bytes_from_a_stream_should_return_the_correct_number_of_bytes_read() throws IOException {
        // Setup
        final byte[] rawData = HexUtil.hexStringToByteArray("0102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F");
        final File tempFile = File.createTempFile("test", "dat");
        IoUtil.putFileContents(tempFile, rawData);

        try (final FileInputStream fileInputStream = new FileInputStream(tempFile)) {
            final byte[] buffer = new byte[20];

            // Action
            final int bytesRead1 = IoUtil.readBytesFromStream(fileInputStream, buffer);
            final int bytesRead2 = IoUtil.readBytesFromStream(fileInputStream, buffer);
            final int bytesRead3 = IoUtil.readBytesFromStream(fileInputStream, buffer);

            // Test
            Assert.assertEquals(20, bytesRead1);
            Assert.assertEquals(11, bytesRead2);
            Assert.assertEquals(0, bytesRead3);
        }
    }
}
