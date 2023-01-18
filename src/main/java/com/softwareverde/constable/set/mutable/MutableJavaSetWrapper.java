package com.softwareverde.constable.set.mutable;

import com.softwareverde.util.Container;

import java.util.Iterator;

public class MutableJavaSetWrapper<T> implements MutableSet<T> {

    protected final java.util.Set<T> _set;

    protected MutableJavaSetWrapper(final java.util.Set<T> hashSet) {
        _set = hashSet;
    }

    @Override
    public int getCount() {
        return _set.size();
    }

    @Override
    public boolean add(final T item) {
        return _set.add(item);
    }

    @Override
    public boolean remove(final T item) {
        return _set.remove(item);
    }

    @Override
    public void clear() {
        _set.clear();
    }

    @Override
    public void mutableVisit(final MutableVisitor<T> visitor) {
        final Iterator<T> mutableIterator = _set.iterator();

        MutableHashSet<T> newEntries = null;
        while (mutableIterator.hasNext()) {
            final T value = mutableIterator.next();

            final Container<T> container = new Container<>(value);
            final boolean shouldContinue = visitor.run(container);

            if (container.value == null) {
                mutableIterator.remove();
            }
            else if (container.value != value) {
                if (newEntries == null) { newEntries = new MutableHashSet<>(); }

                mutableIterator.remove();
                newEntries.add(container.value);
            }

            if (! shouldContinue) { break; }
        }

        if (newEntries != null) {
            for (final T newItem : newEntries) {
                _set.add(newItem);
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return _set.iterator();
    }

    @Override
    public java.util.Set<T> unwrap() {
        return _set;
    }
}
