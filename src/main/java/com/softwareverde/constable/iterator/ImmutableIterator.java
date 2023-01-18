package com.softwareverde.constable.iterator;

import com.softwareverde.constable.list.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ImmutableIterator<T> implements Iterator<T> {
    private final List<T> _items;
    private int _index = 0;

    public ImmutableIterator(final List<T> items) {
        _items = items;
    }

    @Override
    public boolean hasNext() {
        return (_index < _items.getCount());
    }

    @Override
    public T next() {
        if (! (_index < _items.getCount())) {
            throw new NoSuchElementException();
        }

        final T item = _items.get(_index);
        _index += 1;

        return item;
    }

    @Deprecated @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
