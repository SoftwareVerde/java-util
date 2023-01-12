package com.softwareverde.util.bytearray;

import com.softwareverde.constable.bytearray.*;
import com.softwareverde.util.HexUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ByteArrayBuilder implements ByteArray {
    protected final List<byte[]> _byteArrays = new ArrayList<byte[]>();
    protected Long _totalByteCount = 0L;

    protected void _appendBytes(final byte[] bytes, final Endian endian) {
        if (bytes.length == 0) { return; }

        final byte[] copiedBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; ++i) {
            final int readIndex = ( (endian == Endian.BIG) ? (i) : ((bytes.length - i) - 1) );
            copiedBytes[i] = bytes[readIndex];
        }

        _byteArrays.add(copiedBytes);
        _totalByteCount += bytes.length;
    }

    protected void _appendByte(final byte b) {
        _byteArrays.add(new byte[] { b });
        _totalByteCount += 1;
    }

    protected byte _getByte(final long index) {
        if (index >= _totalByteCount) { throw new IndexOutOfBoundsException(); }

        int bytesSkipped = 0;
        for (final byte[] byteArray : _byteArrays) {
            if ((index - bytesSkipped) >= byteArray.length) {
                bytesSkipped += byteArray.length;
                continue;
            }

            final int readIndex = (int) (index - bytesSkipped);
            return byteArray[readIndex];
        }

        throw new IndexOutOfBoundsException();
    }

    public byte[] _getBytes(final long index, final int byteCount) throws IndexOutOfBoundsException {
        final long endIndex = ((index + byteCount) - 1);
        if (endIndex >= _totalByteCount) { throw new IndexOutOfBoundsException(); }

        final byte[] byteBuffer = new byte[byteCount];

        int bytesRemaining = byteCount;
        int writeIndex = 0;
        int bytesSkipped = 0;
        for (final byte[] byteArray : _byteArrays) {
            final long readIndexLong = (index - bytesSkipped);
            if (readIndexLong >= byteArray.length) {
                bytesSkipped += byteArray.length;
                continue;
            }

            final int readIndex = (int) readIndexLong;
            final int readByteCount = Math.min(bytesRemaining, (byteArray.length - readIndex));
            System.arraycopy(byteArray, readIndex, byteBuffer, writeIndex, readByteCount);

            bytesSkipped += readIndex;
            writeIndex += readByteCount;
            bytesRemaining -= readByteCount;

            if (bytesRemaining < 1) {
                return byteBuffer;
            }
        }

        throw new IndexOutOfBoundsException();
    }

    protected byte[] _build() {
        if (_totalByteCount > Integer.MAX_VALUE) { throw new RuntimeException("Byte count surpasses max byte[] size."); }
        final byte[] data = new byte[_totalByteCount.intValue()];

        int offset = 0;
        for (final byte[] value : _byteArrays) {
            for (int i = 0; i < value.length; ++i) {
                data[offset + i] = value[i];
            }
            offset += value.length;
        }

        return data;
    }

    public void appendBytes(final byte[] bytes, final Endian endian) {
        _appendBytes(bytes, endian);
    }

    public void appendBytes(final byte[] bytes) {
        _appendBytes(bytes, Endian.BIG);
    }

    public void appendBytes(final ByteArray bytes, final Endian endian) {
        _appendBytes(bytes.getBytes(), endian);
    }

    public void appendBytes(final ByteArray byteArray) {
        _appendBytes(byteArray.getBytes(), Endian.BIG);
    }

    public void appendByte(final byte b) {
        _appendByte(b);
    }

    public byte[] build() {
        return _build();
    }

    @Override
    public byte getByte(final int index) throws IndexOutOfBoundsException {
        return _getByte(index);
    }

    public byte getByte(final long index) throws IndexOutOfBoundsException {
        return _getByte(index);
    }

    @Override
    public byte[] getBytes(final int index, final int byteCount) throws IndexOutOfBoundsException {
        return _getBytes(index, byteCount);
    }

    public byte[] getBytes(final long index, final int byteCount) throws IndexOutOfBoundsException {
        return _getBytes(index, byteCount);
    }

    @Override
    public byte[] getBytes() {
        return _build();
    }

    @Override
    public ByteArray toReverseEndian() {
        final MutableByteArray mutableByteArray = MutableByteArray.wrap(_build());
        mutableByteArray.reverseEndian();
        return mutableByteArray;
    }

    @Override
    public int getByteCount() {
        if (_totalByteCount > Integer.MAX_VALUE) { return -1; }
        return _totalByteCount.intValue();
    }

    public long getLongByteCount() {
        return _totalByteCount;
    }

    @Override
    public boolean isEmpty() {
        return (_totalByteCount == 0);
    }

    @Override
    public boolean getBit(final long index) throws IndexOutOfBoundsException {
        final long byteIndex = (index >>> 3);
        if (byteIndex >= _totalByteCount) { throw new IndexOutOfBoundsException(); }

        final ByteArray bytes = MutableByteArray.wrap(new byte[] { _getByte((int) byteIndex) });
        return ByteArrayCore.getBit(bytes, (index - (byteIndex * 8)));
    }

    @Override
    public ImmutableByteArray asConst() {
        return new ImmutableByteArray(_build());
    }

    @Override
    public String toString() {
        final byte[] bytes = _build();
        return HexUtil.toHexString(bytes);
    }

    @Override
    public boolean equals(final Object object) {
        if (! (object instanceof ByteArray)) { return false; }

        final ByteArray byteArray = (ByteArray) object;
        if (byteArray.getByteCount() != _totalByteCount) { return false; }

        for (int i = 0; i < _totalByteCount; ++i) {
            final byte b0 = _getByte(i);
            final byte b1 = byteArray.getByte(i);
            if (b0 != b1) { return false; }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return ByteArrayCore.hashCode(this);
    }

    public void clear() {
        _totalByteCount = 0L;
        _byteArrays.clear();
    }

    @Override
    public Iterator<Byte> iterator() {
        return new ImmutableByteArrayIterator(this);
    }
}