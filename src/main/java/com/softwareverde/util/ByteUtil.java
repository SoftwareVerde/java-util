package com.softwareverde.util;

public class ByteUtil {
    public static class Unit {
        public static final Long BYTES      =          0x01L;
        public static final Long KILOBYTES  =         0x400L;
        public static final Long MEGABYTES  =      0x100000L;
        public static final Long GIGABYTES  =    0x40000000L;
        public static final Long TERABYTES  = 0x10000000000L;
    }

    public static byte[] integerToBytes(final int value) {
        return new byte[] {
            (byte) (value >>> 24),
            (byte) (value >>> 16),
            (byte) (value >>> 8),
            (byte) (value)
        };
    }

    public static byte[] integerToBytes(final long value) {
        return integerToBytes((int) value);
    }

    private static long _bytesToLong(final int nativeByteCount, final byte[] bytes) {
        long value = 0;
        for (int i=0; i<nativeByteCount; ++i) {
            final byte b = (bytes.length + i < nativeByteCount ? 0x00 : bytes[bytes.length - nativeByteCount + i]);
            value |= (b & 0xFF);
            if (i < (nativeByteCount - 1)) {
                value <<= 8;
            }
        }
        return value;
    }

    public static long byteToLong(final byte value) {
        return (value & 0xFFL);
    }

    public static int byteToInteger(final byte value) {
        return (value & 0xFF);
    }

    /**
     * Returns a Long as decoded by the provided bytes.
     *  Format is assumed to be Big Endian.
     *  If less than the required bytes are provided, the provided bytes are left-padded with zeroes.
     *  ex.:
     *      00 00 00 01 -&gt; 1
     *      00 00 01 00 -&gt; 255
     *      10 00 00 00 -&gt; 268435456
     */
    public static long bytesToLong(final byte[] bytes) {
        return _bytesToLong(8, bytes);
    }

    /**
     * Returns an Integer as decoded by the provided bytes.
     *  Format is assumed to be Big Endian.
     *  If less than the required bytes are provided, the provided bytes are left-padded with zeroes.
     *  ex.:
     *      00 00 00 01 -&gt; 1
     *      00 00 01 00 -&gt; 255
     *      10 00 00 00 -&gt; 268435456
     */
    public static int bytesToInteger(final byte[] bytes) {
        return (int) _bytesToLong(4, bytes);
    }

    public static byte[] longToBytes(final long value) {
        return new byte[] {
            (byte) (value >>> 56),
            (byte) (value >>> 48),
            (byte) (value >>> 40),
            (byte) (value >>> 32),
            (byte) (value >>> 24),
            (byte) (value >>> 16),
            (byte) (value >>> 8),
            (byte) (value)
        };
    }

    public static byte[] longToBytes(final int value) {
        return longToBytes(((long) value) & 0x00000000FFFFFFFFL);
    }

    public static byte[] reverseEndian(final byte[] bytes) {
        final byte[] reversedBytes = new byte[bytes.length];
        for (int i=0; i<bytes.length; ++i) {
            reversedBytes[i] = bytes[(bytes.length - 1) - i];
        }
        return reversedBytes;
    }

    public static byte[] copyBytes(final byte[] bytes) {
        final byte[] copiedBytes = new byte[bytes.length];
        for (int i=0; i<bytes.length; ++i) {
            copiedBytes[i] = bytes[i];
        }
        return copiedBytes;
    }

    public static byte[] copyBytes(final byte[] bytes, final int startIndex, final int byteCount) {
        final byte[] copiedBytes = new byte[byteCount];
        for (int i=0; i<copiedBytes.length; ++i) {
            copiedBytes[i] = ((startIndex + i) < bytes.length ? bytes[startIndex + i] : 0x00);
        }
        return copiedBytes;
    }

    public static void setBytes(final byte[] destination, final byte[] value, final int offset) {
        for (int i=0; (i+offset)<destination.length; ++i) {
            destination[i + offset] = (i < value.length ? value[i] : 0x00);
        }
    }

    public static void setBytes(final byte[] destination, final byte[] value) {
        ByteUtil.setBytes(destination, value, 0);
    }

    public static Boolean areEqual(final byte[] bytes0, final byte[] bytes1) {
        if (bytes0.length != bytes1.length) { return false; }
        for (int i=0; i<bytes0.length; ++i) {
            final byte b0 = bytes0[i];
            final byte b1 = bytes1[i];
            if (b0 != b1) { return false; }
        }
        return true;
    }
}
