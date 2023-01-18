package com.softwareverde.constable.iterator;

import java.util.Iterator;

public class ImmutableIteratorWrapper<T> implements Iterator<T> {
    protected final Iterator<T> _iterator;

    protected ImmutableIteratorWrapper(final java.util.Iterator<T> iterator) {
        _iterator = iterator;
    }

    public ImmutableIteratorWrapper(final java.util.Collection<T> items) {
        _iterator = items.iterator();
    }

    @Override
    public boolean hasNext() {
        return _iterator.hasNext();
    }

    @Override
    public T next() {
        return _iterator.next();
    }

    @Deprecated @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
