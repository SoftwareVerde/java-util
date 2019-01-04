package com.softwareverde.constable.bytearray;

import com.softwareverde.constable.Constable;

public interface ByteArray extends Constable<ImmutableByteArray> {
    Integer MAX_BYTE_COUNT = (Integer.MAX_VALUE - 5);

    byte getByte(int index) throws IndexOutOfBoundsException;
    byte[] getBytes(int index, int byteCount) throws IndexOutOfBoundsException;
    byte[] getBytes();

    int getByteCount();
    boolean isEmpty();

    /**
     * Returns true if the bit at the index is set.
     *  The first bit is the MSB within the byte at index 0.
     *      Ex: 0xFFFF0000 has the first bit set. Its last bit is not set.
     *  A ByteArray with multiple bytes accesses its bits sequentially.
     *      Ex: [ 0x00000000, 0x80000000 ] only has its 8th bit set.
     *  If the index is out of bounds, an IndexOutOfBoundsException is thrown.
     */
    boolean getBit(long index) throws IndexOutOfBoundsException;

    @Override
    ImmutableByteArray asConst();
}
