package com.softwareverde.constable.set.mutable;

import com.softwareverde.constable.set.Set;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMutableHashSet<T> extends MutableJavaSetWrapper<T> {
    public ConcurrentMutableHashSet() {
        super(ConcurrentHashMap.newKeySet());
    }

    public ConcurrentMutableHashSet(final int initialCapacity) {
        super(ConcurrentHashMap.newKeySet(initialCapacity));
    }

    public ConcurrentMutableHashSet(final Set<T> set) {
        this(set.getCount());
        for (final T item : set) {
            _set.add(item);
        }
    }
}
