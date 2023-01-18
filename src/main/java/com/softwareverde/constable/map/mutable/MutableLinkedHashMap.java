package com.softwareverde.constable.map.mutable;

import com.softwareverde.constable.map.Map;
import com.softwareverde.util.Tuple;

import java.util.LinkedHashMap;

public class MutableLinkedHashMap<Key, Value> extends MutableJavaMapWrapper<Key, Value> {
    public MutableLinkedHashMap() {
        super(new LinkedHashMap<>());
    }

    public MutableLinkedHashMap(final int initialCapacity) {
        super(new LinkedHashMap<>(initialCapacity));
    }

    public MutableLinkedHashMap(final int initialCapacity, final float loadFactor) {
        super(new LinkedHashMap<>(initialCapacity, loadFactor));
    }

    public MutableLinkedHashMap(final Map<Key, Value> map) {
        this(map.getCount());
        for (final Tuple<Key, Value> entry : map) {
            _map.put(entry.first, entry.second);
        }
    }
}
