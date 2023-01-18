package com.softwareverde.constable.set.mutable;

import com.softwareverde.constable.set.Set;

import java.util.HashSet;

public class MutableHashSet<T> extends MutableJavaSetWrapper<T> {
    public static <T> MutableHashSet<T> wrap(final java.util.HashSet<T> hashSet) {
        return new MutableHashSet<>(hashSet);
    }

    protected MutableHashSet(java.util.HashSet<T> javaSet) {
        super(javaSet);
    }

    public MutableHashSet() {
        this(new java.util.HashSet<>());
    }

    public MutableHashSet(final int initialCapacity) {
        this(new HashSet<>(initialCapacity));
    }

    public MutableHashSet(final Set<T> set) {
        this(set.getCount());
        for (final T item : set) {
            _set.add(item);
        }
    }
}
