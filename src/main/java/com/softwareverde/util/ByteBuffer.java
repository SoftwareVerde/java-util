package com.softwareverde.util;

import java.util.LinkedList;
import java.util.List;

public class ByteBuffer {
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

    protected int _bufferSize = 1024;
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
            for (int i=0; i<byteCountFromThisArray; ++i) {
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
        final byte[] discardedPacket = _readContiguousBytes(_byteCount, true);
        // Logger.log("IO: DISCARDED PACKET: "+ HexUtil.toHexString(discardedPacket));
    }

    public ByteBuffer() { }

    public void setBufferSize(final int bufferSize) {
        _bufferSize = bufferSize;
    }

    public Integer getBufferSize() {
        return _bufferSize;
    }

    /**
     * Appends byteBuffer to the ByteBuffer.
     *  - byteBuffer is not copied and is used as a part of the internal representation of this class;
     *      therefore, it is important that any byte[] fed into appendBytes() is not used again outside of this invocation.
     *  - byteBuffer may be kept in memory indefinitely and recycled via getRecycledBuffer().
     *  - byteCount is used to specify the endIndex of byteBuffer.
     */
    public void appendBytes(final byte[] byteBuffer, final int byteCount) {
        // if (byteCount > bytes.length) { throw new RuntimeException("Invalid byteCount. Attempted to add more bytes than was available within byte array."); }
        final int safeByteCount = Math.min(byteBuffer.length, byteCount);

        _byteArrayList.addLast(new Buffer(byteBuffer, 0, safeByteCount));
        _byteCount += safeByteCount;
    }

    public byte[] getRecycledBuffer() {
        if (_recycledByteArrays.isEmpty()) {
            return new byte[_bufferSize];
        }

        final Buffer byteArray = _recycledByteArrays.removeFirst();
        return byteArray.bytes;
    }

    public int getByteCount() {
        return _byteCount;
    }

    public int getBufferCount() {
        return (_recycledByteArrays.size() + _byteArrayList.size());
    }

    public byte[] readBytes(final int byteCount) {
        return _consumeContiguousBytes(byteCount);
    }

    public void clear() {
        _resetBuffer();
    }
}