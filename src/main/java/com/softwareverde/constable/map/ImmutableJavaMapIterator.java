package com.softwareverde.constable.map;

import com.softwareverde.util.Tuple;

import java.util.Iterator;

public class ImmutableJavaMapIterator<Key, Value> implements Iterator<Tuple<Key, Value>> {
    protected final Iterator<java.util.Map.Entry<Key, Value>> _javaEntryIterator;

    public ImmutableJavaMapIterator(final java.util.Map<Key, Value> javaMap) {
        final java.util.Set<java.util.Map.Entry<Key, Value>> entrySet = javaMap.entrySet();
        _javaEntryIterator = entrySet.iterator();
    }

    @Override
    public boolean hasNext() {
        return _javaEntryIterator.hasNext();
    }

    @Override
    public Tuple<Key, Value> next() {
        final java.util.Map.Entry<Key, Value> javaEntry = _javaEntryIterator.next();
        return new Tuple<>(javaEntry.getKey(), javaEntry.getValue());
    }

    @Deprecated @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
