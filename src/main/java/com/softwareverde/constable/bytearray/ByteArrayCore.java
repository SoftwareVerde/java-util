package com.softwareverde.constable.bytearray;

import com.softwareverde.util.ByteUtil;
import com.softwareverde.util.HexUtil;

import java.util.Iterator;

public abstract class ByteArrayCore implements ByteArray {

    public static boolean getBit(final ByteArray bytes, final long index) {
        final int byteIndex = (int) (index >>> 3);
        final byte b = bytes.getByte(byteIndex);

        final int bitMask = ( 0x01 << ( 7 - (0x07 & index) ) );
        return ( (b & bitMask) != 0x00 );
    }

    public static int hashCode(final ByteArray bytes) {
        final int byteCount = bytes.getByteCount();
        long value = bytes.getByteCount();
        if (byteCount <= 8) {
            for (final byte b : bytes) {
                value += ByteUtil.byteToLong(b);
            }
        }
        else {
            // Sample 4 evenly-distributed bytes from the head and tail ends of the array...
            for (int i = 0; i < 4; ++i) {
                final int offset = (i > 0 ? ((byteCount / 8) * i) : 0);
                final byte b0 = bytes.getByte(offset);
                final byte b1 = bytes.getByte(byteCount - offset - 1);

                value += ByteUtil.byteToLong(b0);
                value += ByteUtil.byteToLong(b1);
            }
        }
        return Long.valueOf(value).hashCode();
    }

    protected byte[] _bytes;

    @Override
    public boolean getBit(final long index) {
        return ByteArrayCore.getBit(this, index);
    }

    @Override
    public byte getByte(final int index) throws IndexOutOfBoundsException {
        if (index >= _bytes.length) { throw new IndexOutOfBoundsException(); }

        return _bytes[index];
    }

    @Override
    public byte[] getBytes(final int index, final int byteCount) throws IndexOutOfBoundsException {
        if (index + byteCount > _bytes.length) { throw new IndexOutOfBoundsException(); }

        final byte[] bytes = new byte[byteCount];
        for (int i=0; i<byteCount; ++i) {
            final int readIndex = (index + i);
            bytes[i] = _bytes[readIndex];
        }
        return bytes;
    }

    @Override
    public int getByteCount() {
        return _bytes.length;
    }

    @Override
    public boolean isEmpty() {
        return (_bytes.length == 0);
    }

    @Override
    public byte[] getBytes() {
        return ByteUtil.copyBytes(_bytes);
    }

    @Override
    public ByteArray toReverseEndian() {
        return MutableByteArray.wrap(ByteUtil.reverseEndian(_bytes));
    }

    @Override
    public ImmutableByteArray asConst() {
        return new ImmutableByteArray(this);
    }

    @Override
    public int hashCode() {
        return ByteArrayCore.hashCode(this);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) { return false; }
        if (this == obj) { return true; }

        final byte[] bytes;
        {
            if (obj instanceof ByteArray) {
                final ByteArray object = (ByteArray) obj;
                return ByteArray.areEqual(this, object);
            }
            else if (obj instanceof byte[]) {
                bytes = (byte[]) obj;
                return ByteUtil.areEqual(_bytes, bytes);
            }
            else { return false; }
        }
    }

    @Override
    public String toString() {
        return HexUtil.toHexString(_bytes);
    }

    @Override
    public Iterator<Byte> iterator() {
        return new ImmutableByteArrayIterator(this);
    }
}
