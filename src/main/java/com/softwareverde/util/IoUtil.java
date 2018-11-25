package com.softwareverde.util;

import com.softwareverde.logging.Log;

import java.io.*;

public class IoUtil {

    private static byte[] _readStream(final InputStream inputStream) throws IOException {
        final byte[] bytes;

        try (final ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            byte[] readBuffer = new byte[1024];
            while ((nRead = inputStream.read(readBuffer, 0, readBuffer.length)) != -1) {
                buffer.write(readBuffer, 0, nRead);
            }

            buffer.flush();
            bytes = buffer.toByteArray();
        }

        return bytes;
    }

    public static byte[] readStream(final InputStream inputStream) {
        try {
            return _readStream(inputStream);
        }
        catch (final Exception exception) {
            Log.error("Unable to read stream", exception);
            return null;
        }
        finally {
            try {
                inputStream.close();
            }
            catch (final IOException ioException) { }
        }
    }

    public static String streamToString(final InputStream inputStream) {
        try {
            return new String(IoUtil._readStream(inputStream), "UTF-8");
        }
        catch (final Exception exception) {
            Log.error("Unable to read stream", exception);
            return null;
        }
        finally {
            try {
                inputStream.close();
            }
            catch (final IOException exception) { }
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
            return IoUtil._readStream(inputStream);
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
