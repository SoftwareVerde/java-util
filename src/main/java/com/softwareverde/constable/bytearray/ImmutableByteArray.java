package com.softwareverde.constable.bytearray;

import com.softwareverde.constable.Const;
import com.softwareverde.util.ByteUtil;
import com.softwareverde.util.HexUtil;

public class ImmutableByteArray implements ByteArray, Const {
    public static ImmutableByteArray fromHexString(final String hexString) {
        final byte[] bytes = HexUtil.hexStringToByteArray(hexString);
        if (bytes == null) { return null; }

        return new ImmutableByteArray(bytes, false);
    }

    public static ImmutableByteArray copyOf(final byte[] bytes) {
        if (bytes == null) { return null; }

        return new ImmutableByteArray(bytes);
    }

    protected final byte[] _bytes;

    protected ImmutableByteArray(final byte[] bytes, final Boolean copyBytes) {
        _bytes = (copyBytes ? ByteUtil.copyBytes(bytes) : bytes);
    }

    public ImmutableByteArray() {
        _bytes = new byte[0];
    }

    public ImmutableByteArray(final byte[] bytes) {
        _bytes = ByteUtil.copyBytes(bytes);
    }

    public ImmutableByteArray(final ByteArray byteArray) {
        if (byteArray instanceof ImmutableByteArray) {
            _bytes = ((ImmutableByteArray) byteArray)._bytes;
            return;
        }

        _bytes = ByteUtil.copyBytes(byteArray.getBytes());
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

        return ByteArrayCore.getBit(_bytes, index);
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
        return new ImmutableByteArray(ByteUtil.reverseEndian(_bytes));
    }

    @Override
    public ImmutableByteArray asConst() {
        return this;
    }

    @Override
    public int hashCode() {
        long value = 0L;
        for (final byte b : _bytes) {
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
