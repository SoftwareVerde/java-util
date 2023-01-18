package com.softwareverde.constable.set.mutable;

import com.softwareverde.constable.Visitor;
import com.softwareverde.constable.set.Set;
import com.softwareverde.util.Container;

public interface MutableSet<T> extends Set<T> {
    interface MutableVisitor<T> extends Visitor<Container<T>> { }

    boolean add(T item);

    default void addAll(final Iterable<T> items) {
        for (final T item : items) {
            this.add(item);
        }
    }

    boolean remove(T item);

    default void removeAll(final Iterable<T> items) {
        for (final T item : items) {
            this.remove(item);
        }
    }

    void clear();

    void mutableVisit(MutableVisitor<T> visitor);

    java.util.Set<T> unwrap();
}
