package com.softwareverde.constable.map.mutable;

import com.softwareverde.constable.map.Map;
import com.softwareverde.util.Tuple;

import java.util.WeakHashMap;

public class MutableWeakHashMap<Key, Value> extends MutableJavaMapWrapper<Key, Value> {
    public MutableWeakHashMap() {
        super(new WeakHashMap<>());
    }

    public MutableWeakHashMap(final int initialCapacity) {
        super(new WeakHashMap<>(initialCapacity));
    }

    public MutableWeakHashMap(final int initialCapacity, final float loadFactor) {
        super(new WeakHashMap<>(initialCapacity, loadFactor));
    }

    public MutableWeakHashMap(final Map<Key, Value> map) {
        this(map.getCount());
        for (final Tuple<Key, Value> entry : map) {
            _map.put(entry.first, entry.second);
        }
    }
}
