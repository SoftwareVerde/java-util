package com.softwareverde.constable.list.mutable;

public abstract class MutableJavaListWrapper<T> extends MutableList<T> {
    protected MutableJavaListWrapper(final java.util.List<T> list) {
        super(list);
    }
}
