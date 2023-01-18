package com.softwareverde.constable.list.mutable;

import com.softwareverde.constable.list.List;
import com.softwareverde.constable.set.Set;

import java.util.Comparator;
import java.util.Iterator;

/**
 * iMutableList will be removed in v3.  Use {@link MutableList} instead.
 */
public interface iMutableList<T> extends List<T> {
    void add(T item);

    void add(int index, T item);

    void set(int index, T item);

    void addAll(Set<T> items);

    void addAll(java.util.Collection<T> items);

    T remove(int index);

    void clear();

    void mutableVisit(MutableList.MutableVisitor<T> visitor);

    Iterator<T> mutableIterator();

    void sort(final Comparator<T> comparator);
}
