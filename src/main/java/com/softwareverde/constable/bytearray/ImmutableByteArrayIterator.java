package com.softwareverde.constable.bytearray;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ImmutableByteArrayIterator implements Iterator<Byte> {
    private final ByteArray _byteArray;
    private int _index = 0;

    public ImmutableByteArrayIterator(final ByteArray byteArray) {
        _byteArray = byteArray;
    }

    @Override
    public boolean hasNext() {
        return (_index < _byteArray.getByteCount());
    }

    @Override
    public Byte next() {
        if (! (_index < _byteArray.getByteCount())) {
            throw new NoSuchElementException();
        }

        final Byte item = _byteArray.getByte(_index);
        _index += 1;

        return item;
    }

    @Deprecated @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
