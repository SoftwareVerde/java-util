package com.softwareverde.util;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <p>This class implements a circular buffer with a queue.</p>
 *
 * <p>Items are added until the max size is reached and then the oldest items are replaced in order, wrapping
 * continuously.</p>
 */
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
