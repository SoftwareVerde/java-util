package com.softwareverde.constable.list.mutable;

public class MutableLinkedList<T> extends MutableJavaListWrapper<T> {
    public MutableLinkedList(final java.util.Collection<T> items) {
        super(new java.util.LinkedList<>(items));
    }

    public MutableLinkedList(final Iterable<T> items) {
        super(new java.util.LinkedList<>());
        for (final T item : items) {
            _items.add(item);
        }
    }

    public MutableLinkedList() {
        super(new java.util.LinkedList<>());
    }
}
