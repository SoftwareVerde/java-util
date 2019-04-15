package com.softwareverde.util;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircleBuffer<T> implements Iterable<T> {
    protected final T[] _items;
    protected int _writeIndex;
    protected int _readIndex;

    //              [ ] [ ] [ ] // Empty.                           Before: _writeIndex=0, _readIndex=0
    // ::pushItem   [X] [ ] [ ] // First item.                      Before: _writeIndex=0, _readIndex=0; After: _writeIndex=1, _readIndex=0
    // ::popItem    [_] [ ] [ ] // Popped item.                     Before: _writeIndex=1, _readIndex=0; After: _writeIndex=1, _readIndex=1
    // ::pushItem   [_] [X] [ ] //                                  Before: _writeIndex=3; _readIndex=0; After: _writeIndex=4
    // ::pushItem   [_] [X] [X] // First filling.                   Before: _writeIndex=3; _readIndex=0; After: _writeIndex=4
    // ::pushItem   [Y] [X] [X] // First overwriting of index 0.    Before: _writeIndex=4; _readIndex=0; After: _writeIndex=5

    @SuppressWarnings("unchecked")
    public CircleBuffer(final int itemCount) {
        _items = (T[]) new Object[itemCount];
        _writeIndex = 0;
        _readIndex = 0;
    }

    public synchronized void pushItem(final T item) {
        final int index = (_writeIndex % _items.length);
        _items[index] = item;

        _writeIndex += 1;

        if (_writeIndex - _readIndex > _items.length) {
            _readIndex = (_writeIndex - _items.length);
        }
    }

    public synchronized T popItem() {
        if (_readIndex >= _writeIndex) { return null; }

        final int index = (_readIndex % _items.length);
        _readIndex += 1;
        return _items[index];
    }

    public synchronized T get(final int i) {
        if ((_readIndex + i) >= _writeIndex) { return null; }

        final int index = ((_readIndex + i) % _items.length);
        return _items[index];
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return new ImmutableCircleBufferIterator<T>(this);
    }

    public synchronized int getItemCount() {
        return (_writeIndex - _readIndex);
    }

    public int getMaxItemCount() {
        return _items.length;
    }
}

class ImmutableCircleBufferIterator<T> implements Iterator<T> {
    private final CircleBuffer<T> _items;
    private final int _originalWriteIndex;
    private int _index;

    protected void _assertNoConcurrentModification() {
        if (_items._writeIndex != _originalWriteIndex) { throw new ConcurrentModificationException(); }
    }

    public ImmutableCircleBufferIterator(final CircleBuffer<T> items) {
        _items = items;
        _index = 0;
        _originalWriteIndex = items._writeIndex;
    }

    @Override
    public boolean hasNext() {
        _assertNoConcurrentModification();

        return (_index < _items.getItemCount());
    }

    @Override
    public T next() {
        _assertNoConcurrentModification();

        if (! (_index < _items.getItemCount())) {
            throw new NoSuchElementException();
        }

        final T item = _items.get(_index);
        _index += 1;

        return item;
    }

    @Override
    public void remove() {
        _assertNoConcurrentModification();

        throw new UnsupportedOperationException();
    }
}