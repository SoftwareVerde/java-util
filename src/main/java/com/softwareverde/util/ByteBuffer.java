package com.softwareverde.util;

import com.softwareverde.constable.bytearray.ByteArray;
import com.softwareverde.constable.bytearray.ImmutableByteArray;
import com.softwareverde.constable.bytearray.MutableByteArray;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ByteBuffer implements ByteArray {
    @Override
    public Iterator<Byte> iterator() {
        return new Iterator<Byte>() {
            private int _index = 0;

            @Override
            public boolean hasNext() {
                return (_index < _byteCount);
            }

            @Override
            public Byte next() {
                final byte b = _getByte(_index);
                _index += 1;
                return b;
            }
        };
    }

    protected static class Buffer {
        public byte[] bytes;
        public int startIndex;
        public int byteCount;

        public Buffer(final byte[] bytes, final int startIndex, final int byteCount) {
            this.bytes = bytes;
            this.startIndex = startIndex;
            this.byteCount = byteCount;
        }

        public void markBytesConsumed(final int byteCount) {
            this.byteCount -= byteCount;
            this.startIndex += byteCount;
        }

        public boolean hasBytesRemaining() {
            return (this.byteCount > 0);
        }
    }

    protected int _pageByteCount = 1024;
    protected int _maxByteCount = Integer.MAX_VALUE;
    protected final LinkedList<Buffer> _recycledByteArrays = new LinkedList<Buffer>();
    protected final LinkedList<Buffer> _byteArrayList = new LinkedList<Buffer>();
    protected int _byteCount = 0;

    protected byte[] _readContiguousBytes(final int desiredByteCount, final boolean shouldConsumeBytes) {
        final byte[] bytes = new byte[desiredByteCount];

        // Make a copy of the list when modifying to prevent concurrent-modification...
        final List<Buffer> byteArrayList = (shouldConsumeBytes ? Util.copyList(_byteArrayList) : _byteArrayList);

        int byteCount = 0;
        for (final Buffer byteArray : byteArrayList) {
            final int byteCountFromThisArray = Math.min((desiredByteCount - byteCount), byteArray.byteCount);
            for (int i = 0; i < byteCountFromThisArray; ++i) {
                bytes[byteCount + i] = byteArray.bytes[byteArray.startIndex + i];
            }
            byteCount += byteCountFromThisArray;

            if (shouldConsumeBytes) {
                byteArray.markBytesConsumed(byteCountFromThisArray);
                if (! byteArray.hasBytesRemaining()) {
                    _byteArrayList.removeFirst();
                    _recycledByteArrays.addLast(byteArray);
                }
            }

            if (byteCount >= desiredByteCount) { break; }
        }

        if (shouldConsumeBytes) {
            _byteCount -= byteCount;
        }

        return bytes;
    }

    protected byte[] _peakContiguousBytes(final int desiredByteCount) {
        return _readContiguousBytes(desiredByteCount, false);
    }

    protected byte[] _consumeContiguousBytes(final int desiredByteCount) {
        return _readContiguousBytes(desiredByteCount, true);
    }

    protected void _resetBuffer() {
        _readContiguousBytes(_byteCount, true);
    }

    protected boolean _shouldAllowNewBuffer(final byte[] byteBuffer, final int byteCount) {
        final int newByteCount = (_byteCount + byteCount);
        return (newByteCount <= _maxByteCount);
    }

    protected byte _getByte(final int index) {
        int i = 0;
        for (final Buffer buffer : _byteArrayList) {
            if (! buffer.hasBytesRemaining()) { continue; }

            if (index < (i + buffer.byteCount)) {
                final int bufferIndex = (buffer.startIndex + (index - i));
                return buffer.bytes[bufferIndex];
            }
            else {
                i += buffer.byteCount;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public ByteBuffer() { }

    /**
     * Appends byteBuffer to the ByteBuffer.
     *  - byteBuffer is not copied and is used as a part of the internal representation of this class;
     *      therefore, it is important that any byte[] fed into appendBytes() is not used again outside of this invocation.
     *  - byteBuffer may be kept in memory indefinitely and recycled via getRecycledBuffer().
     *  - byteCount is used to specify the endIndex of byteBuffer.
     *  - If the ByteBuffer's logical size would exceed the ByteBuffer's maxByteCount then the buffer is not added.
     */
    public void appendBytes(final byte[] byteBuffer, final int byteCount) {
        final int safeByteCount = Math.min(byteBuffer.length, byteCount);
        if (! _shouldAllowNewBuffer(byteBuffer, safeByteCount)) { return; }

        _byteArrayList.addLast(new Buffer(byteBuffer, 0, safeByteCount));
        _byteCount += safeByteCount;
    }

    public byte[] getRecycledBuffer() {
        if (_recycledByteArrays.isEmpty()) {
            return new byte[_pageByteCount];
        }

        final Buffer byteArray = _recycledByteArrays.removeFirst();
        return byteArray.bytes;
    }

    public void recycleBuffer(final byte[] buffer) {
        _recycledByteArrays.addLast(new Buffer(buffer, 0, 0));
    }

    public void setPageByteCount(final int bufferSize) {
        _pageByteCount = bufferSize;
    }

    public void setMaxByteCount(final int maxByteCount) {
        _maxByteCount = maxByteCount;
    }

    public int getPageByteCount() {
        return _pageByteCount;
    }

    public int getMaxByteCount() {
        return _maxByteCount;
    }

    public int getPageCount() {
        return (_recycledByteArrays.size() + _byteArrayList.size());
    }

    @Override
    public byte getByte(final int index) throws IndexOutOfBoundsException {
        return _getByte(index);
    }

    @Override
    public byte[] getBytes(final int index, final int byteCount) throws IndexOutOfBoundsException {
        int bytesRead = 0;
        final byte[] bytes = new byte[byteCount];

        int byteBufferIndex = 0;
        for (final Buffer buffer : _byteArrayList) {
            if (index < (byteBufferIndex + buffer.byteCount)) {
                int bufferIndex = buffer.startIndex;
                if (index > byteBufferIndex) { // index is within this buffer but not at its beginning...
                    final int bufferOffset = (index - byteBufferIndex);
                    byteBufferIndex += bufferOffset;
                    bufferIndex += bufferOffset;
                }

                while ( (bytesRead < byteCount) && (bufferIndex < buffer.byteCount) ) {
                    bytes[bytesRead] = buffer.bytes[bufferIndex];
                    bytesRead += 1;
                    bufferIndex += 1;
                    byteBufferIndex += 1;
                }

                if (bytesRead >= byteCount) { break; }
            }
            else {
                byteBufferIndex += buffer.byteCount;
            }
        }

        if (bytesRead < byteCount) { throw new IndexOutOfBoundsException(); }

        return bytes;
    }

    @Override
    public byte[] getBytes() {
        return _readContiguousBytes(_byteCount, false);
    }

    @Override
    public ByteArray toReverseEndian() {
        final byte[] bytes = _readContiguousBytes(_byteCount, false);
        return MutableByteArray.wrap(bytes).toReverseEndian();
    }

    public int getByteCount() {
        return _byteCount;
    }

    @Override
    public boolean isEmpty() {
        return (_byteCount == 0);
    }

    @Override
    public boolean getBit(final long index) throws IndexOutOfBoundsException {
        final byte b = _getByte((int) (index / 8L));
        return ByteUtil.getBit(b, (int) (index % 8L));
    }

    @Override
    public ImmutableByteArray asConst() {
        final byte[] bytes = _readContiguousBytes(_byteCount, false);
        return new ImmutableByteArray(bytes, false) { };
    }

    public byte[] readBytes(final int byteCount) {
        return _consumeContiguousBytes(byteCount);
    }

    public void clear() {
        _resetBuffer();
    }

    @Deprecated
    public void setBufferSize(final int bufferSize) {
        this.setPageByteCount(bufferSize);
    }

    @Deprecated
    public Integer getBufferSize() {
        return this.getPageByteCount();
    }

    @Deprecated
    public int getBufferCount() {
        return this.getPageCount();
    }
}