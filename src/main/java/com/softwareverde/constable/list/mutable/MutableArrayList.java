package com.softwareverde.constable.list.mutable;

import com.softwareverde.constable.list.List;
import com.softwareverde.constable.set.Set;

public class MutableArrayList<T> extends MutableJavaListWrapper<T> {
    public MutableArrayList(final java.util.Collection<T> items) {
        super(new java.util.ArrayList<>(items));
    }

    public MutableArrayList(final Iterable<T> items) {
        super(new java.util.ArrayList<>());
        for (final T item : items) {
            _items.add(item);
        }
    }

    public MutableArrayList(final List<T> items) {
        super(new java.util.ArrayList<>(items.getCount()));
        for (final T item : items) {
            _items.add(item);
        }
    }

    public MutableArrayList() {
        super(new java.util.ArrayList<>());
    }

    public MutableArrayList(final int initialCapacity) {
        super(new java.util.ArrayList<>(initialCapacity));
    }

    @Override
    public void addAll(final Set<T> items) {
        final java.util.ArrayList<T> superItems = (java.util.ArrayList<T>) _items;
        superItems.ensureCapacity(items.getCount());

        super.addAll(items);
    }

    public void ensureCapacity(final int itemCount) {
        final java.util.ArrayList<T> superItems = (java.util.ArrayList<T>) _items;
        superItems.ensureCapacity(itemCount);
    }
}
