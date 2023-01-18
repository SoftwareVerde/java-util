package com.softwareverde.constable.map.mutable;

import com.softwareverde.constable.map.Map;
import com.softwareverde.util.Tuple;

import java.util.Comparator;
import java.util.TreeMap;

public class MutableTreeMap<Key, Value> extends MutableJavaMapWrapper<Key, Value> {
    public MutableTreeMap() {
        super(new TreeMap<>());
    }

    public MutableTreeMap(final Comparator<? super Key> comparator) {
        super(new TreeMap<>(comparator));
    }

    public MutableTreeMap(final Map<Key, Value> map) {
        this(map instanceof MutableTreeMap ? ((MutableTreeMap<Key, Value>) map).getComparator() : null);
        for (final Tuple<Key, Value> entry : map) {
            _map.put(entry.first, entry.second);
        }
    }

    public Comparator<? super Key> getComparator() {
        return ((TreeMap<Key, ?>) _map).comparator();
    }
}
