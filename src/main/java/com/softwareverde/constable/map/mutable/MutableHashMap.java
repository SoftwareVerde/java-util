package com.softwareverde.constable.map.mutable;

import com.softwareverde.constable.map.Map;
import com.softwareverde.util.Tuple;

import java.util.HashMap;

public class MutableHashMap<Key, Value> extends MutableJavaMapWrapper<Key, Value> {
    public MutableHashMap() {
        super(new HashMap<>());
    }

    public MutableHashMap(final int initialCapacity) {
        super(new HashMap<>(initialCapacity));
    }

    public MutableHashMap(final int initialCapacity, final float loadFactor) {
        super(new HashMap<>(initialCapacity, loadFactor));
    }

    public MutableHashMap(final Map<Key, Value> map) {
        this(map.getCount());
        for (final Tuple<Key, Value> entry : map) {
            _map.put(entry.first, entry.second);
        }
    }
}
