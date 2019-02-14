package com.softwareverde.util;

import com.softwareverde.logging.Log;

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
     * Reads the stream until EOF and returns the raw bytes from the stream.
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
     *  The inputStream is closed at the end of the call.
     *  On error, null is returned.
     */
    public static byte[] readStream(final InputStream paramInputStream) {
        try (final InputStream inputStream = paramInputStream) {
            return _readStreamOrThrow(inputStream);
        }
        catch (final Exception exception) {
            Log.error("Unable to read stream", exception);
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
            Log.error("Unable to read stream", exception);
            return null;
        }
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
            return _readStreamOrThrow(inputStream);
        }
        catch (final Exception exception) {
            Log.error("Unable to read file contents", exception);
            return null;
        }
    }

    public static Boolean putFileContents(final String filename, final byte[] bytes) {
        try (final OutputStream outputStream = new FileOutputStream(filename)) {
            outputStream.write(bytes);
            outputStream.flush();
            return true;
        }
        catch (final Exception exception) {
            Log.error("Unable to read file contents", exception);
            return false;
        }
    }

    public static byte[] getFileContents(final String filename) {
        final File file = new File(filename);
        return IoUtil.getFileContents(file);
    }
}
