package com.softwareverde.constable.bytearray;

import com.softwareverde.util.ByteUtil;
import com.softwareverde.util.HexUtil;

public class MutableByteArray implements ByteArray {
    public static MutableByteArray wrap(final byte[] bytes) {
        if (bytes == null) { return null; }
        return new MutableByteArray(bytes);
    }

    public static MutableByteArray copyOf(final byte[] bytes) {
        if (bytes == null) { return null; }
        return new MutableByteArray(ByteUtil.copyBytes(bytes));
    }

    protected byte[] _bytes;

    protected MutableByteArray(final byte[] bytes) {
        _bytes = bytes;
    }

    public MutableByteArray(final ByteArray byteArray) {
        _bytes = ByteUtil.copyBytes(byteArray.getBytes());
    }

    public MutableByteArray(final int byteCount) {
        _bytes = new byte[byteCount];
    }

    @Deprecated
    public void set(final int index, final byte b) throws IndexOutOfBoundsException {
        this.setByte(index, b);
    }

    public void setByte(final int index, final byte b) throws IndexOutOfBoundsException {
        if (index >= _bytes.length) { throw new IndexOutOfBoundsException(); }
        _bytes[index] = b;
    }

    /**
     * Copies the contents of `bytes` into this ByteArray.
     *  If this ByteArray is smaller than the `startIndex` plus the length of `bytes`, then the excess bytes are ignored and no exception is thrown.
     *  For example, MutableByteArray(3)::setBytes(1, {1, 2, 3, 4}) results in a ByteArray with the contents: {0, 1, 2}
     *
     * @param startIndex
     *      The index into this ByteArray where `bytes` is written to.
     *      If the value is outside of the bounds of this ByteArray then an IndexOutOfBoundsException is thrown.
     * @param bytes
     *      The content to copy into this ByteArray.
     */
    public void setBytes(final int startIndex, final byte[] bytes) throws IndexOutOfBoundsException {
        if (startIndex >= _bytes.length) { throw new IndexOutOfBoundsException(); }

        ByteUtil.setBytes(_bytes, bytes, startIndex); // Silently aborts before reaching out of bounds.
    }

    public void setBytes(final int startIndex, final ByteArray bytes) throws IndexOutOfBoundsException {
        if (startIndex >= _bytes.length) { throw new IndexOutOfBoundsException(); }

        final int byteCopyCount = Math.min(_bytes.length - startIndex, bytes.getByteCount());
        for (int i = 0; i < byteCopyCount; ++i) {
            _bytes[startIndex + i] = bytes.getByte(i);
        }
    }

    public void resize(final int newByteCount) {
        final byte[] bytes = _bytes;
        _bytes = new byte[newByteCount];
        ByteUtil.setBytes(_bytes, bytes);
    }

    @Override
    public byte getByte(final int index) throws IndexOutOfBoundsException {
        if (index >= _bytes.length) { throw new IndexOutOfBoundsException(); }

        return _bytes[index];
    }

    @Override
    public boolean getBit(final long index) throws IndexOutOfBoundsException {
        final long byteIndex = (index >>> 3);
        if (byteIndex >= _bytes.length) { throw new IndexOutOfBoundsException(); }

        return ImmutableByteArray._getBit(_bytes, index);
    }

    public void setBit(final long index, final boolean isSet) throws IndexOutOfBoundsException {
        final long byteIndex = (index >>> 3);
        if (byteIndex >= _bytes.length) { throw new IndexOutOfBoundsException(); }

        final int byteIndexInt = (int) byteIndex;

        final byte b = _bytes[byteIndexInt];
        final int bitMask = ( 0x01 << (7 - (0x07 & index)) );

        if (isSet) {
            _bytes[byteIndexInt] = (byte) (b | bitMask);
        }
        else {
            _bytes[byteIndexInt] = (byte) (b & (~bitMask));
        }
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

    public byte[] unwrap() {
        return _bytes;
    }

    @Override
    public ImmutableByteArray asConst() {
        return new ImmutableByteArray(this);
    }

    @Override
    public int hashCode() {
        long value = 0;
        for (byte b : _bytes) {
            value += ByteUtil.byteToLong(b);
        }
        return Long.valueOf(value).hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) { return false; }

        final byte[] bytes;
        {
            if (obj instanceof ByteArray) {
                final ByteArray object = (ByteArray) obj;
                bytes = object.getBytes();
            }
            else if (obj instanceof byte[]) {
                bytes = (byte[]) obj;
            }
            else { return false; }
        }

        return ByteUtil.areEqual(_bytes, bytes);
    }

    @Override
    public String toString() {
        return HexUtil.toHexString(_bytes);
    }
}
