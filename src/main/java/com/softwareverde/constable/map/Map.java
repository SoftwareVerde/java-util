package com.softwareverde.constable.map;

import com.softwareverde.constable.Visitor;
import com.softwareverde.constable.set.Set;
import com.softwareverde.util.Tuple;

public interface Map<Key, Value> extends Iterable<Tuple<Key, Value>> {
    Integer getCount();

    default Boolean isEmpty() {
        return (this.getCount() == 0);
    }

    Value get(Key key);
    Boolean containsKey(Key key);

    Set<Key> getKeys();
    Set<Value> getValues();

    default void visit(final Visitor<Tuple<Key, Value>> visitor) {
        for (final Tuple<Key, Value> tuple : Map.this) {
            final boolean shouldContinue = visitor.run(tuple);
            if (!shouldContinue) { break; }
        }
    }
}
