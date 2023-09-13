package com.softwareverde.constable.map;

import com.softwareverde.constable.UnsafeVisitor;
import com.softwareverde.constable.set.Set;
import com.softwareverde.logging.Logger;
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

    default void visit(final UnsafeVisitor<Tuple<Key, Value>> visitor) {
        for (final Tuple<Key, Value> tuple : Map.this) {
            boolean shouldContinue;
            try {
                shouldContinue = visitor.run(tuple);
            }
            catch (final Exception exception) {
                Logger.debug(exception);
                shouldContinue = false;
            }
            finally {
                visitor.andFinally();
            }

            if (! shouldContinue) { break; }
        }
    }
}
