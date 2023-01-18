package com.softwareverde.constable.map.mutable;

import com.softwareverde.constable.Factory;
import com.softwareverde.constable.Visitor;
import com.softwareverde.constable.map.Map;
import com.softwareverde.util.Tuple;

import java.util.Iterator;

public interface MutableMap<Key, Value> extends Map<Key, Value> {
    interface MutableVisitor<Key, Value> extends Visitor<Tuple<Key, Value>> { }

    void put(Key key, Value value);

    default Value getOrPut(Key key, Factory<Value> valueFactory) {
        final Value value = this.get(key);
        if (value != null) { return value; }

        final Value instance = valueFactory.newInstance();
        this.put(key, instance);
        return instance;
    }

    default void putAll(final Map<Key, Value> map) {
        if (this == map) { return; }
        for (final Tuple<Key, Value> entry : map) {
            this.put(entry.first, entry.second);
        }
    }

    Value remove(Key key);

    void clear();

    void mutableVisit(MutableVisitor<Key, Value> visitor);

    Iterator<Tuple<Key, Value>> mutableIterator();

    java.util.Map<Key, Value> unwrap();
}
