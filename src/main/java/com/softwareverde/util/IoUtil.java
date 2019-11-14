package com.softwareverde.util;

import com.softwareverde.logging.Logger;

import java.io.*;

public class IoUtil {

    /**
     * Reads the stream until EOF and returns the raw bytes from the stream.
     *  Unlike other functions in this class, the inputStream is NOT closed at the end of the call.
     *  On error, the exception is thrown.
     */
    protected static byte[] _readStreamOrThrow(final InputStream inputStream) throws IOException {
        try (final ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int readByteCount;
            final byte[] readBuffer = new byte[1024];
            while ((readByteCount = inputStream.read(readBuffer, 0, readBuffer.length)) != -1) {
                buffer.write(readBuffer, 0, readByteCount);
            }
            buffer.flush();
            return buffer.toByteArray();
        }
    }

    /**
     * Reads the stream until EOF or the expected length is reached.  This method is more memory-efficient than its
     * sister method due to the fact that it only creates an array of the expected length (which the data is buffered into).
     *  Unlike other functions in this class, the inputStream is NOT closed at the end of the call.
     *  If EOF is not reached at the expected length, an IOException is thrown.
     *  On error, the exception is thrown.
     */
    protected static byte[] _readStreamOrThrow(final InputStream inputStream, final long longLength) throws IOException {
        if (longLength > Integer.MAX_VALUE) {
            throw new IOException("Unable to read file with " + longLength + " bytes into a byte array.");
        }

        final int length = (int) longLength;
        final byte[] data = new byte[length];

        int startIndex = 0;
        boolean shouldContinue = true;
        while (shouldContinue) {
            int bytesRead = inputStream.read(data, startIndex, length - startIndex);
            startIndex += bytesRead;

            if (bytesRead < 0) {
                // EOF was reached
                if (startIndex != length) {
                    throw new IOException("Unable to read expected byte length from stream (expected: " + length + ", read: " + startIndex + ")");
                }
                shouldContinue = false;
            }
            if (bytesRead == 0) {
                // reached expected length
                if (inputStream.available() > 0 || inputStream.read() > 0) {
                    throw new IOException("Additional data available beyond expected length of " + length);
                }
                shouldContinue = false;
            }
        }
        return data;
    }

    /**
     * Reads the stream until EOF or expected length and returns the raw bytes from the stream.
     *  The inputStream is closed at the end of the call.
     *  On error, the exception is thrown.
     */
    public static byte[] readStreamOrThrow(final InputStream paramInputStream) throws IOException {
        try (final InputStream inputStream = paramInputStream) {
            return _readStreamOrThrow(inputStream);
        }
    }

    /**
     * Reads the stream until EOF and returns the raw bytes from the stream.
     *  This method is more memory-efficient than its sister method due to the fact that it only creates an array of the
     *      expected length (which the data is buffered into).
     *  The inputStream is closed at the end of the call.
     *  If EOF is not reached at the expected length, an IOException is thrown.
     *  On error, the exception is thrown.
     */
    public static byte[] readStreamOrThrow(final InputStream paramInputStream, final long expectedLength) throws IOException {
        try (final InputStream inputStream = paramInputStream) {
            return _readStreamOrThrow(inputStream, expectedLength);
        }
    }

    /**
     * Attempts to fill the provided buffer with bytes from the specified stream.
     *   Returns the number of bytes read; if this is less than the buffer length, it indicates that the stream ended
     *      before the buffer could be filled.
     *   On error, the exception is thrown.
     * @param inputStream
     * @param buffer
     * @return
     */
    public static int readBytesFromStream(final InputStream inputStream, final byte[] buffer) throws IOException {
        int readByteCount = 0;
        int iterationByteCount;
        while ((iterationByteCount = inputStream.read(buffer, readByteCount, buffer.length - readByteCount)) > 0) {
            readByteCount += iterationByteCount;
        }
        return readByteCount;
    }

    /**
     * Reads the stream until EOF and returns the raw bytes from the stream.
     *  The inputStream is closed at the end of the call.
     *  On error, null is returned.
     */
    public static byte[] readStream(final InputStream paramInputStream) {
        try (final InputStream inputStream = paramInputStream) {
            return _readStreamOrThrow(inputStream);
        }
        catch (final Exception exception) {
            Logger.warn("Unable to read stream.", exception);
            return null;
        }
    }

    /**
     * Reads inputStream until EOF and converts its contents as a UTF8 String.
     *  The inputStream is closed at the end of the call.
     *  On error, null is returned.
     */
    public static String streamToString(final InputStream paramInputStream) {
        try (final InputStream inputStream = paramInputStream) {
            return new String(_readStreamOrThrow(inputStream), "UTF-8");
        }
        catch (final Exception exception) {
            Logger.warn("Unable to read stream.", exception);
            return null;
        }
    }

    /**
     * Returns a stream to a file contained within the project's resources directory.
     * @param filename  - The file name of the resource within the resources. It should be prefixed with a forward-slash.
     * @return          - An input stream to the contents of the resource or null if it was not found.
     */
    public static InputStream getResourceAsStream(final String filename) {
        final InputStream resourceStream = Util.class.getResourceAsStream(filename);
        return resourceStream;
    }

    /**
     * Returns the content of a file contained within the project's resources directory.
     * @param filename  - The file name of the resource within the resources. It should be prefixed with a forward-slash.
     * @return          - The contents of the resource if found, otherwise an empty string.
     */
    public static String getResource(final String filename) {
        final InputStream resourceStream = Util.class.getResourceAsStream(filename);
        if (resourceStream == null) { return ""; }
        return IoUtil.streamToString(resourceStream);
    }

    public static byte[] getFileContents(final File file) {
        try (final InputStream inputStream = new FileInputStream(file)) {
            return _readStreamOrThrow(inputStream, file.length());
        }
        catch (final Exception exception) {
            Logger.warn("Unable to read file contents.", exception);
            return null;
        }
    }

    public static Boolean putFileContents(final String filename, final byte[] bytes) {
        final File file = new File(filename);
        return IoUtil.putFileContents(file, bytes);
    }

    public static Boolean putFileContents(final File file, final byte[] bytes) {
        try (final OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(bytes);
            outputStream.flush();
            return true;
        }
        catch (final Exception exception) {
            Logger.warn("Unable to write file contents.", exception);
            return false;
        }
    }

    public static byte[] getFileContents(final String filename) {
        final File file = new File(filename);
        return IoUtil.getFileContents(file);
    }
}
