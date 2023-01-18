package com.softwareverde.constable.set;

import com.softwareverde.constable.iterator.ImmutableIteratorWrapper;

import java.util.Iterator;

public class JavaSetWrapper<T> implements Set<T> {
    protected final java.util.Collection<T> _set;

    public JavaSetWrapper(final java.util.Collection<T> set) {
        _set = set;
    }

    @Override
    public int getCount() {
        return _set.size();
    }

    @Override
    public Iterator<T> iterator() {
        return new ImmutableIteratorWrapper<>(_set);
    }
}
