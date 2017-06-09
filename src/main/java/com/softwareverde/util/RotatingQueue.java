package com.softwareverde.util;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RotatingQueue<E> extends ConcurrentLinkedQueue<E> {

    private final Integer _maxItemCount;

    public RotatingQueue(final Integer maxItemCount) {
        this._maxItemCount = maxItemCount;
    }

    @Override
    public boolean add(final E o) {
        final Boolean itemWasAdded = super.add(o);

        while (itemWasAdded && this.size() > _maxItemCount) {
            this.remove();
        }

        return itemWasAdded;
    }
}
