package com.softwareverde.constable.list.mutable;

import com.softwareverde.constable.iterator.ImmutableIterator;
import com.softwareverde.constable.list.List;
import com.softwareverde.constable.list.immutable.ImmutableList;
import com.softwareverde.constable.set.Set;
import com.softwareverde.util.Container;
import com.softwareverde.util.Util;

import java.util.Comparator;
import java.util.Iterator;

public abstract class MutableJavaListWrapper<T> extends MutableList<T> {
    protected MutableJavaListWrapper(final java.util.List<T> list) {
        super(list);
    }

    @Override
    public void add(final T item) {
        _items.add(item);
    }

    @Override
    public void add(final int index, final T item) {
        _items.add(index, item);
    }

    @Override
    public void set(final int index, final T item) {
        _items.set(index, item);
    }

    @Override
    public void addAll(final Set<T> items) {
        for (final T item : items) {
            _items.add(item);
        }
    }

    @Override
    public void addAll(final java.util.Collection<T> items) {
        _items.addAll(items);
    }

    @Override
    public T remove(final int index) {
        return _items.remove(index);
    }

    @Override
    public void clear() {
        _items.clear();
    }

    @Override
    public void mutableVisit(final MutableVisitor<T> visitor) {
        final Iterator<T> mutableIterator = _items.iterator();

        int index = 0;
        while (mutableIterator.hasNext()) {
            final T value = mutableIterator.next();

            final Container<T> container = new Container<>(value);
            final boolean shouldContinue = visitor.run(container);

            if (container.value == null) {
                mutableIterator.remove();
            }
            else if (container.value != value) {
                _items.set(index, container.value);
            }

            if (! shouldContinue) { break; }
            ++index;
        }
    }

    @Override
    public Iterator<T> mutableIterator() {
        return _items.iterator();
    }

    @Override
    public void sort(final Comparator<T> comparator) {
        _items.sort(comparator);
    }

    @Override
    public T get(final int index) {
        return _items.get(index);
    }

    @Override
    public int getCount() {
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
        return new ImmutableList<>(this);
    }

    @Override
    public Iterator<T> iterator() {
        return new ImmutableIterator<>(this);
    }

    @Override
    public boolean equals(final Object object) {
        if (object == this) { return true; }
        if (object == null) { return false; }
        if (! (object instanceof List)) { return false; }

        final int size = _items.size();

        final List<?> list = (List<?>) object;
        if (size != list.getCount()) { return false; }
        for (int i = 0; i < size; ++i) {
            final T item0 = _items.get(i);
            final Object item1 = list.get(i);
            if (! Util.areEqual(item0, item1)) { return false; }
        }

        return true;
    }
}
