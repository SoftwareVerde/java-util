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
            buffer.close();
        }

        return bytes;
    }

    public static byte[] readStream(final InputStream inputStream) {
        try {
            return _readStream(inputStream);
        }
        catch (Exception e) {
            Log.error("Unable to read stream", e);
            return null;
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {}
        }
    }

    public static String streamToString(final InputStream inputStream) {
        try {
            return new String(IoUtil._readStream(inputStream), "UTF-8");
        }
        catch (final Exception e) {
            Log.error("Unable to read stream", e);
            return null;
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {}
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
        catch (final Exception e) {
            Log.error("Unable to read file contents", e);
            return null;
        }
    }

    public static byte[] getFileContents(final String filename) {
        final File file = new File(filename);
        return IoUtil.getFileContents(file);
    }
}
