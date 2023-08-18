package com.softwareverde.util;

import com.softwareverde.constable.list.List;
import com.softwareverde.constable.list.mutable.MutableArrayList;

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

    protected T _push(final T item) {
        final int index = (_writeIndex % _items.length);
        final T oldItem = _items[index];
        _items[index] = item;

        _writeIndex += 1;

        if (_writeIndex - _readIndex > _items.length) {
            _readIndex = (_writeIndex - _items.length);
        }
        return oldItem;
    }

    @SuppressWarnings("unchecked")
    public CircleBuffer(final int itemCount) {
        _items = (T[]) new Object[itemCount];
        _writeIndex = 0;
        _readIndex = 0;
    }

    public synchronized T push(final T item) {
        return _push(item);
    }

    public synchronized List<T> pushAll(final Iterable<T> items) {
        // TODO: Could gain efficiency by only adding the last _items.length items...
        final MutableArrayList<T> lostItems = new MutableArrayList<>();
        for (final T item : items) {
            final T lostItem = _push(item);
            if (lostItem != null) {
                lostItems.add(lostItem);
            }
        }
        return lostItems;
    }

    public synchronized T pop() {
        if (_readIndex >= _writeIndex) { return null; }

        final int index = (_readIndex % _items.length);
        _readIndex += 1;
        final T item = _items[index];
        _items[index] = null; // Prevent returning as a "lostItem" in the future.
        return item;
    }

    public synchronized T get(final int i) {
        if ((_readIndex + i) >= _writeIndex) { return null; }

        final int index = ((_readIndex + i) % _items.length);
        return _items[index];
    }

    public synchronized boolean contains(final T item) {
        final Iterator<T> iterator = new ImmutableCircleBufferIterator<T>(this);
        while (iterator.hasNext()) {
            final T existingItem = iterator.next();
            if (Util.areEqual(item, existingItem)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void clear() {
        _readIndex = 0;
        _writeIndex = 0;

        for (int i = 0; i < _items.length; ++i) {
            _items[i] = null;
        }
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