package com.softwareverde.constable.bytearray;

import com.softwareverde.util.ByteUtil;
import com.softwareverde.util.HexUtil;

public class MutableByteArray extends ByteArrayCore {
    public static MutableByteArray fromHexString(final String hexString) {
        final byte[] bytes = HexUtil.hexStringToByteArray(hexString);
        return MutableByteArray.wrap(bytes);
    }

    public static MutableByteArray copyOf(final byte[] bytes) {
        if (bytes == null) { return null; }
        return new MutableByteArray(ByteUtil.copyBytes(bytes));
    }

    public static MutableByteArray wrap(final byte[] bytes) {
        if (bytes == null) { return null; }
        return new MutableByteArray(bytes);
    }

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

    public void reverseEndian() {
        for (int i = 0; i < (_bytes.length / 2); ++i) {
            final int tailIndex = (_bytes.length - i - 1);
            final byte b0 = _bytes[i];
            final byte b1 = _bytes[tailIndex];
            _bytes[i] = b1;
            _bytes[tailIndex] = b0;
        }
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

    public byte[] unwrap() {
        return _bytes;
    }

}
