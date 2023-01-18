package com.softwareverde.constable.set.mutable;

import com.softwareverde.constable.set.Set;

import java.util.Comparator;

public class MutableTreeSet<T> extends MutableJavaSetWrapper<T> {
    public static <T> MutableTreeSet<T> wrap(final java.util.TreeSet<T> treeSet) {
        return new MutableTreeSet<>(treeSet);
    }

    protected MutableTreeSet(java.util.TreeSet<T> javaSet) {
        super(javaSet);
    }

    public MutableTreeSet() {
        this(new java.util.TreeSet<>());
    }

    public MutableTreeSet(final Comparator<T> comparator) {
        this(new java.util.TreeSet<>(comparator));
    }

    public MutableTreeSet(final Set<T> set) {
        super(new java.util.TreeSet<>());
        for (final T item : set) {
            _set.add(item);
        }
    }
}
