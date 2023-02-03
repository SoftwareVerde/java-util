package com.softwareverde.util;

import com.softwareverde.constable.bytearray.ByteArray;
import com.softwareverde.logging.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class IoUtil {

    protected IoUtil() { }

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
            Logger.trace("Unable to read stream.", exception);
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
            Logger.trace("Unable to read stream.", exception);
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
            Logger.trace("Unable to read file contents.", exception);
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
            Logger.trace("Unable to write file contents.", exception);
            return false;
        }
    }

    public static byte[] getFileContents(final String filename) {
        final File file = new File(filename);
        return IoUtil.getFileContents(file);
    }

    /**
     * Skips byteCount bytes within inputStream.
     *  The number of bytes skipped is returned.  If no bytes were skipped, then 0 is returned.
     *  If byteCount is less than 1, 0 is returned.
     *  This method is similar to InputStream::skip except that this function will not return until EOF is reached or byteCount bytes has been skipped.
     */
    public static Long skipBytes(final Long byteCount, final InputStream inputStream) {
        if (byteCount < 1) { return 0L; }

        int numberOfTimesSkipReturnedZero = 0;
        long skippedByteCount = 0L;
        while (skippedByteCount < byteCount) {
            final long skipReturnValue;
            try {
                skipReturnValue = inputStream.skip(byteCount - skippedByteCount);
            }
            catch (final IOException exception) { break; }

            skippedByteCount += skipReturnValue;

            if (skipReturnValue == 0) {
                numberOfTimesSkipReturnedZero += 1;
            }
            else {
                numberOfTimesSkipReturnedZero = 0;
            }

            // InputStream::skip can sometimes return zero for "valid" reasons, but does not report EOF...
            //  If skip returns zero 32 times (arbitrarily chosen), EOF is assumed...
            if (numberOfTimesSkipReturnedZero > 32) { break; }
        }
        return skippedByteCount;
    }

    public static Boolean fileExists(final String path) {
        final File file = new File(path);
        return file.exists();
    }

    public static Boolean isEmpty(final String path) {
        final File file = new File(path);
        if (! file.exists()) { return true; }
        if (! file.isFile()) { return true; }

        return (file.length() < 1);
    }

    public static Boolean putFileContents(final String filename, final ByteArray bytes) {
        final File file = new File(filename);
        return IoUtil.putFileContents(file, bytes);
    }

    public static Boolean putFileContents(final File file, final ByteArray bytes) {
        final int pageSize = (16 * 1024);

        int bytesWritten = 0;
        int bytesRemaining = bytes.getByteCount();
        try (final OutputStream outputStream = new FileOutputStream(file)) {
            while (bytesRemaining > 0) {
                final byte[] buffer = bytes.getBytes(bytesWritten, Math.min(pageSize, bytesRemaining));
                outputStream.write(buffer);
                bytesWritten += buffer.length;
                bytesRemaining -= buffer.length;
            }
            outputStream.flush();
            return true;
        }
        catch (final Exception exception) {
            Logger.trace("Unable to write file contents.", exception);
            return false;
        }
    }

    public static ByteArray readCompressed(final InputStream inputStream) {
        final int pageSize = (16 * 1024);

        try (final GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream, pageSize)) {
            final ByteBuffer byteBuffer = new ByteBuffer();

            while (true) {
                final byte[] buffer = new byte[pageSize];
                final int byteCount = gzipInputStream.read(buffer, 0, pageSize);
                if (byteCount < 1) { break; }
                byteBuffer.appendBytes(buffer, byteCount);
            }

            return byteBuffer;
        }
        catch (final Exception exception) {
            Logger.trace("Unable to read file contents.", exception);
            return null;
        }
    }

    public static ByteArray getCompressedFileContents(final File file) {
        try (final InputStream inputStream = new FileInputStream(file)) {
            return IoUtil.readCompressed(inputStream);
        }
        catch (final Exception exception) {
            Logger.trace("Unable to read file contents.", exception);
            return null;
        }
    }

    public static Boolean writeCompressed(final ByteArray bytes, final OutputStream outputStream) {
        final int pageSize = (16 * 1024);

        int bytesWritten = 0;
        int bytesRemaining = bytes.getByteCount();
        try (final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream, pageSize)) {
            while (bytesRemaining > 0) {
                final byte[] buffer = bytes.getBytes(bytesWritten, Math.min(pageSize, bytesRemaining));
                gzipOutputStream.write(buffer);
                bytesWritten += buffer.length;
                bytesRemaining -= buffer.length;
            }
            outputStream.flush();
            return true;
        }
        catch (final Exception exception) {
            Logger.trace("Unable to write file contents.", exception);
            return false;
        }
    }

    public static Boolean putCompressedFileContents(final ByteArray bytes, final File file) {
        try (final OutputStream outputStream = new FileOutputStream(file)) {
            return IoUtil.writeCompressed(bytes, outputStream);
        }
        catch (final Exception exception) {
            Logger.trace("Unable to write file contents.", exception);
            return false;
        }
    }
}
