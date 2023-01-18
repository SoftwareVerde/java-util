package com.softwareverde.constable.set.mutable;

import com.softwareverde.constable.set.Set;

import java.util.Iterator;

public class JavaIterableSetWrapper<T> implements Set<T> {
    protected static class ImmutableIteratorWrapper<T> implements Iterator<T> {
        protected Iterator<T> _iterator;

        public ImmutableIteratorWrapper(final Iterator<T> iterator) {
            _iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return _iterator.hasNext();
        }

        @Override
        public T next() {
            return _iterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    protected final Iterator<T> _iterator;
    protected final Integer _itemCount;

    public JavaIterableSetWrapper(final Iterable<T> iterable, final Integer itemCount) {
        final Iterator<T> iterator = iterable.iterator();
        _iterator = new ImmutableIteratorWrapper<>(iterator);
        _itemCount = itemCount;
    }

    public JavaIterableSetWrapper(final Iterator<T> iterator, final Integer itemCount) {
        _iterator = new ImmutableIteratorWrapper<>(iterator);
        _itemCount = itemCount;
    }

    @Override
    public int getCount() {
        return _itemCount;
    }

    @Override
    public Iterator<T> iterator() {
        return _iterator;
    }
}
