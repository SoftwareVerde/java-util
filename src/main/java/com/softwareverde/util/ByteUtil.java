package com.softwareverde.util;

import com.softwareverde.constable.bytearray.ByteArray;
import com.softwareverde.constable.bytearray.MutableByteArray;

public class ByteUtil {
    public static class Unit {
        @Deprecated public static final Long BYTES      = Binary.BYTES;
        @Deprecated public static final Long KILOBYTES  = Binary.KIBIBYTES;
        @Deprecated public static final Long MEGABYTES  = Binary.MEBIBYTES;
        @Deprecated public static final Long GIGABYTES  = Binary.GIBIBYTES;
        @Deprecated public static final Long TERABYTES  = Binary.TEBIBYTES;

        public static class Si {
            public static final Long BYTES      =             1L;
            public static final Long KILOBYTES  =          1000L;
            public static final Long MEGABYTES  =       1000000L;
            public static final Long GIGABYTES  =    1000000000L;
            public static final Long TERABYTES  = 1000000000000L;
        }

        public static class Binary {
            public static final Long BYTES      =          0x01L;
            public static final Long KIBIBYTES  =         0x400L;
            public static final Long MEBIBYTES  =      0x100000L;
            public static final Long GIBIBYTES  =    0x40000000L;
            public static final Long TEBIBYTES  = 0x10000000000L;
        }
    }

    protected static long _bytesToLong(final int nativeByteCount, final ByteArray bytes) {
        final int byteCount = bytes.getByteCount();
        long value = 0;
        for (int i = 0; i < nativeByteCount; ++i) {
            final byte b = (byteCount + i < nativeByteCount ? 0x00 : bytes.getByte(byteCount - nativeByteCount + i));
            value |= (b & 0xFF);
            if (i < (nativeByteCount - 1)) {
                value <<= 8;
            }
        }
        return value;
    }

    protected static byte[] _reverseEndian(final ByteArray bytes) {
        final int byteCount = bytes.getByteCount();
        final byte[] reversedBytes = new byte[byteCount];
        for (int i = 0; i < byteCount; ++i) {
            reversedBytes[i] = bytes.getByte((byteCount - 1) - i);
        }
        return reversedBytes;
    }

    protected static byte[] _copyBytes(final ByteArray bytes) {
        final int byteCount = bytes.getByteCount();
        final byte[] copiedBytes = new byte[byteCount];
        for (int i = 0; i < byteCount; ++i) {
            copiedBytes[i] = bytes.getByte(i);
        }
        return copiedBytes;
    }

    protected static byte[] _copyBytes(final ByteArray bytes, final int startIndex, final int byteCount) {
        final int bytesByteCount = bytes.getByteCount();
        final byte[] copiedBytes = new byte[byteCount];
        for (int i = 0; i < copiedBytes.length; ++i) {
            copiedBytes[i] = ((startIndex + i) < bytesByteCount ? bytes.getByte(startIndex + i) : 0x00);
        }
        return copiedBytes;
    }

    protected static Integer _setBytes(final MutableByteArray destination, final ByteArray value, final int offset) {
        final int destinationByteCount = destination.getByteCount();
        final int valueByteCount = value.getByteCount();
        for (int i = 0; (i + offset) < destinationByteCount; ++i) {
            if (i >= valueByteCount) { break; }

            final int index = (i + offset);
            final byte byteValue = value.getByte(i); // (i < valueByteCount ? value.getByte(i) : 0x00);
            destination.setByte(index, byteValue);
        }

        return Math.min((destinationByteCount - offset), valueByteCount);
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
        return ByteUtil.integerToBytes((int) value);
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
        return _bytesToLong(8, MutableByteArray.wrap(bytes));
    }

    public static long bytesToLong(final ByteArray bytes) {
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
        return (int) _bytesToLong(4, MutableByteArray.wrap(bytes));
    }

    public static int bytesToInteger(final ByteArray bytes) {
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
        return _reverseEndian(MutableByteArray.wrap(bytes));
    }

    public static byte[] reverseEndian(final ByteArray bytes) {
        return _reverseEndian(bytes);
    }

    public static byte[] copyBytes(final byte[] bytes) {
        return _copyBytes(MutableByteArray.wrap(bytes));
    }

    public static byte[] copyBytes(final ByteArray bytes) {
        return _copyBytes(bytes);
    }

    public static byte[] copyBytes(final byte[] bytes, final int startIndex, final int byteCount) {
        return _copyBytes(MutableByteArray.wrap(bytes), startIndex, byteCount);
    }

    public static byte[] copyBytes(final ByteArray bytes, final int startIndex, final int byteCount) {
        return _copyBytes(bytes, startIndex, byteCount);
    }

    /**
     * Writes `value` to at the `destinationOffset` within `destination`, or until `destination` is full.
     *  Returns the number of bytes actually written.
     */
    public static Integer setBytes(final byte[] destination, final byte[] value, final int destinationOffset) {
        return _setBytes(MutableByteArray.wrap(destination), MutableByteArray.wrap(value), destinationOffset);
    }

    public static Integer setBytes(final byte[] destination, final byte[] value) {
        return _setBytes(MutableByteArray.wrap(destination), MutableByteArray.wrap(value), 0);
    }

    public static Integer setBytes(final MutableByteArray destination, final ByteArray value, final int offset) {
        return _setBytes(destination, value, offset);
    }

    public static Integer setBytes(final MutableByteArray destination, final ByteArray value) {
        return _setBytes(destination, value, 0);
    }

    public static void setBytes(final MutableByteArray destination, final ByteArray source, final Integer destinationOffset) {
        for (int i = 0; i < source.getByteCount(); ++i) {
            final int writeIndex = (i + destinationOffset);
            if (writeIndex >= destination.getByteCount()) { break; }
            destination.setByte(writeIndex, source.getByte(i));
        }
    }

    public static Boolean areEqual(final byte[] bytes0, final byte[] bytes1) {
        if (bytes0.length != bytes1.length) { return false; }
        for (int i = 0; i < bytes0.length; ++i) {
            final byte b0 = bytes0[i];
            final byte b1 = bytes1[i];
            if (b0 != b1) { return false; }
        }
        return true;
    }

    public static void cleanByteArray(final byte[] bytes) {
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = 0x00;
        }
    }

    public static void cleanByteArray(final MutableByteArray bytes) {
        final int byteCount = bytes.getByteCount();
        final byte zero = 0x00;

        for (int i = 0; i < byteCount; ++i) {
            bytes.setByte(i, zero);
        }
    }

    public static Boolean areEqual(final ByteArray bytes0, final ByteArray bytes1) {
        final int byteCount0 = bytes0.getByteCount();
        if (byteCount0 != bytes1.getByteCount()) { return false; }

        for (int i = 0; i < byteCount0; ++i) {
            final byte b0 = bytes0.getByte(i);
            final byte b1 = bytes1.getByte(i);
            if (b0 != b1) { return false; }
        }
        return true;
    }

    public static void clearByteArray(final MutableByteArray bytes) {
        for (int i = 0; i < bytes.getByteCount(); i += 1) {
            bytes.setByte(i, (byte) 0x00);
        }
    }

    public static byte reverseBits(final byte b) {
        return (byte) (Integer.reverse(b) >>> 24);
    }

    public static byte[] getTailBytes(final byte[] bytes, final Integer byteCount) {
        final MutableByteArray byteArray = ByteUtil.getTailBytes(MutableByteArray.wrap(bytes), byteCount);
        return byteArray.unwrap();
    }

    public static MutableByteArray getTailBytes(final ByteArray byteArray, final Integer byteCount) {
        final int byteArrayByteCount = byteArray.getByteCount();
        final MutableByteArray bytes = new MutableByteArray(byteCount);
        for (int i = 0; i < byteCount; ++i) {
            if (i >= byteArrayByteCount) { break; }
            bytes.setByte((byteCount - i - 1), byteArray.getByte(byteArrayByteCount - i - 1));
        }
        return bytes;
    }

    public static void setTailBytes(final byte[] destination, final byte[] value) {
        final int copyCount = Math.min(destination.length, value.length);
        for (int i = 0; i < copyCount; ++i) {
            destination[(destination.length - i) - 1] = value[(value.length - i) - 1];
        }
    }

    public static void setTailBytes(final MutableByteArray destination, final ByteArray value) {
        final int destinationByteCount = destination.getByteCount();
        final int valueByteCount = value.getByteCount();

        final int copyCount = Math.min(destinationByteCount, valueByteCount);
        for (int i = 0; i < copyCount; ++i) {
            destination.setByte(((destinationByteCount - i) - 1),  value.getByte((valueByteCount - i) - 1));
        }
    }

    public static int compare(final byte b0, final byte b1) {
        return (Byte.toUnsignedInt(b0) - Byte.toUnsignedInt(b1));
    }
}
