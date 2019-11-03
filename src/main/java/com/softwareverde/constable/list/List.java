package com.softwareverde.constable.list;

import com.softwareverde.constable.Constable;
import com.softwareverde.constable.list.immutable.ImmutableList;

public interface List<T> extends Iterable<T>, Constable<ImmutableList<T>> {
    T get(int index);

    @Deprecated
    int getSize();

    default int getCount() { return this.getSize(); }

    boolean isEmpty();

    boolean contains(T item);
    int indexOf(T item);

    @Override
    ImmutableList<T> asConst();
}
