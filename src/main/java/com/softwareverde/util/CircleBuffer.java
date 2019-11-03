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

    public synchronized void push(final T item) {
        final int index = (_writeIndex % _items.length);
        _items[index] = item;

        _writeIndex += 1;

        if (_writeIndex - _readIndex > _items.length) {
            _readIndex = (_writeIndex - _items.length);
        }
    }

    public synchronized T pop() {
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

    public synchronized int getCount() {
        return (_writeIndex - _readIndex);
    }

    public int getMaxCount() {
        return _items.length;
    }
}

class ImmutableCircleBufferIterator<T> implements Iterator<T> {
    protected final CircleBuffer<T> _circleBuffer;
    protected final int _originalWriteIndex;
    protected int _index;

    protected void _assertNoConcurrentModification() {
        if (_circleBuffer._writeIndex != _originalWriteIndex) { throw new ConcurrentModificationException(); }
    }

    public ImmutableCircleBufferIterator(final CircleBuffer<T> circleBuffer) {
        _circleBuffer = circleBuffer;
        _index = 0;
        _originalWriteIndex = circleBuffer._writeIndex;
    }

    @Override
    public boolean hasNext() {
        synchronized (_circleBuffer) {
            _assertNoConcurrentModification();

            return (_index < _circleBuffer.getCount());
        }
    }

    @Override
    public T next() {
        synchronized (_circleBuffer) {
            _assertNoConcurrentModification();

            if (! (_index < _circleBuffer.getCount())) {
                throw new NoSuchElementException();
            }

            final T item = _circleBuffer.get(_index);
            _index += 1;

            return item;
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}