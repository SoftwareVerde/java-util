package com.softwareverde.constable.map.mutable;

import com.softwareverde.constable.UnsafeVisitor;
import com.softwareverde.constable.Visitor;
import com.softwareverde.constable.map.ImmutableJavaMapIterator;
import com.softwareverde.constable.set.JavaSetWrapper;
import com.softwareverde.constable.set.Set;
import com.softwareverde.constable.set.mutable.JavaIterableSetWrapper;
import com.softwareverde.util.Tuple;

import java.util.Iterator;

public abstract class MutableJavaMapWrapper<Key, Value> implements MutableMap<Key, Value> {
    protected final java.util.Map<Key, Value> _map;

    protected MutableJavaMapWrapper(final java.util.Map<Key, Value> map) {
        _map = map;
    }

    @Override
    public void put(final Key key, final Value value) {
        _map.put(key, value);
    }

    @Override
    public Value remove(final Key key) {
        return _map.remove(key);
    }

    @Override
    public void clear() {
        _map.clear();
    }

    @Override
    public Integer getCount() {
        return _map.size();
    }

    @Override
    public Value get(final Key key) {
        return _map.get(key);
    }

    @Override
    public Boolean containsKey(final Key key) {
        return _map.containsKey(key);
    }

    @Override
    public Set<Key> getKeys() {
        return new JavaSetWrapper<>(_map.keySet());
    }

    @Override
    public Set<Value> getValues() {
        final java.util.Collection<Value> javaCollection = _map.values();
        return new JavaIterableSetWrapper<>(javaCollection, _map.size());
    }

    @Override
    public void mutableVisit(final MutableVisitor<Key, Value> visitor) {
        MutableMapUtil.mutableVisit(_map, visitor);
    }

    @Override
    public void visit(final UnsafeVisitor<Tuple<Key, Value>> visitor) {
        MutableMapUtil.visit(_map, visitor);
    }

    @Override
    public Iterator<Tuple<Key, Value>> mutableIterator() {
        return new MutableJavaMapIterator<>(_map);
    }

    @Override
    public Iterator<Tuple<Key, Value>> iterator() {
        return new ImmutableJavaMapIterator<>(_map);
    }

    @Override
    public java.util.Map<Key, Value> unwrap() {
        return _map;
    }
}
