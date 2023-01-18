package com.softwareverde.constable.set;

import com.softwareverde.constable.Visitor;
import com.softwareverde.util.Util;

public interface Set<T> extends Iterable<T> {
    int getCount();

    default boolean isEmpty() {
        return (this.getCount() == 0);
    }

    default boolean contains(final T item) {
        for (final T thisItem : this) {
            if (Util.areEqual(item, thisItem)) {
                return true;
            }
        }
        return false;
    }

    default void visit(final Visitor<T> visitor) {
        for (final T value : Set.this) {
            final boolean shouldContinue = visitor.run(value);
            if (! shouldContinue) {
                return;
            }
        }
    }
}
