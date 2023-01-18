package com.softwareverde.constable.list;

import com.softwareverde.constable.Constable;
import com.softwareverde.constable.list.immutable.ImmutableList;
import com.softwareverde.constable.set.Set;
import com.softwareverde.util.Util;

public interface List<T> extends Set<T>, Constable<ImmutableList<T>> {
    T get(int index);

    default int indexOf(final T item) {
        int i = 0;
        for (final T thisItem : this) {
            if (Util.areEqual(item, thisItem)) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    @Override
    ImmutableList<T> asConst();
}
