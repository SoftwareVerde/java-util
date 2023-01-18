package com.softwareverde.constable.map.mutable;

import com.softwareverde.constable.map.Map;
import com.softwareverde.util.Tuple;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMutableHashMap<Key, Value> extends MutableJavaMapWrapper<Key, Value> {
    public ConcurrentMutableHashMap() {
        super(new ConcurrentHashMap<>());
    }

    public ConcurrentMutableHashMap(final int initialCapacity) {
        super(new ConcurrentHashMap<>(initialCapacity));
    }

    public ConcurrentMutableHashMap(final int initialCapacity, final float loadCapacity) {
        super(new ConcurrentHashMap<>(initialCapacity, loadCapacity));
    }

    public ConcurrentMutableHashMap(final Map<Key, Value> map) {
        this(map.getCount());
        for (final Tuple<Key, Value> entry : map) {
            _map.put(entry.first, entry.second);
        }
    }
}
