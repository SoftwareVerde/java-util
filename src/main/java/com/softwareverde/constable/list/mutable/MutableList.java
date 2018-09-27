package com.softwareverde.constable.list.mutable;

import com.softwareverde.constable.list.List;
import com.softwareverde.constable.list.immutable.ImmutableList;
import com.softwareverde.constable.list.immutable.ImmutableListIterator;
import com.softwareverde.util.Util;

import java.util.*;

public class MutableList<T> implements List<T> {
    private final ArrayList<T> _items;

    public MutableList(final Collection<T> items) {
        _items = new ArrayList<T>(items);
    }

    public MutableList(final List<T> items) {
        _items = new ArrayList<T>(items.getSize());
        for (final T item : items) {
            _items.add(item);
        }
    }

    public MutableList() {
        _items = new ArrayList<T>();
    }

    public MutableList(final int initialCapacity) {
        _items = new ArrayList<T>(initialCapacity);
    }

    public void add(final T item) {
        _items.add(item);
    }

    public void add(final int index, final T item) {
        _items.add(index, item);
    }

    public void set(final int index, final T item) {
        _items.set(index, item);
    }

    public void addAll(final List<T> items) {
        _items.ensureCapacity(items.getSize());
        for (final T item : items) {
            _items.add(item);
        }
    }

    public T remove(final int index) {
        return _items.remove(index);
    }

    public void clear() {
        _items.clear();
    }

    public Iterator<T> mutableIterator() {
        return _items.iterator();
    }

    public void sort(final Comparator<T> comparator) {
        Collections.sort(_items, comparator);
    }

    @Override
    public T get(final int index) {
        return _items.get(index);
    }

    @Override
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
    public ImmutableList<T> asConst() {
        return new ImmutableList<T>(this);
    }

    @Override
    public Iterator<T> iterator() {
        return new ImmutableListIterator<T>(this);
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
