package com.softwareverde.constable.list.immutable;

import com.softwareverde.constable.Const;
import com.softwareverde.constable.list.List;
import com.softwareverde.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ImmutableList<T> implements List<T>, Const {
    private final java.util.List<T> _items;

    /**
     * Creates an ImmutableList using the provided list as its implementation.
     *  Use this function carefully, as leaking a reference to the provided list will break immutability.
     */
    protected static <T> ImmutableList<T> wrap(final java.util.List<T> list) {
        return new ImmutableList<T>(list);
    }

    private ImmutableList(final java.util.List<T> list) {
        _items = list;
    }

    public ImmutableList(final Collection<T> list) {
        _items = new ArrayList<T>(list);
    }

    public ImmutableList(final List<T> list) {
        _items = new ArrayList<T>(list.getSize());
        for (final T item : list) {
            _items.add(item);
        }
    }

    @Override
    public T get(final int index) {
        return _items.get(index);
    }

    @Override
    @Deprecated
    public int getSize() {
        return _items.size();
    }

    @Override
    public boolean isEmpty() {
        return _items.isEmpty();
    }

    @Override
    public boolean contains(final T item) {
        return _items.contains(item);
    }

    @Override
    public int indexOf(final T item) {
        return _items.indexOf(item);
    }

    @Override
    public Iterator<T> iterator() {
        return new ImmutableListIterator<T>(this);
    }

    @Override
    public ImmutableList<T> asConst() {
        return this;
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null) { return false; }
        if (! (object instanceof List)) { return false; }

        final int size = _items.size();

        final List list = (List) object;
        if (size != list.getSize()) { return false; }
        for (int i = 0; i < size; ++i) {
            final T item0 = _items.get(i);
            final Object item1 = list.get(i);
            if (! Util.areEqual(item0, item1)) { return false; }
        }

        return true;
    }
}
