package com.softwareverde.util;

import java.util.Collection;
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
    public boolean add(final E item) {
        final Boolean itemWasAdded = super.add(item);

        while (itemWasAdded && this.size() > _maxItemCount) {
            this.remove();
        }

        return itemWasAdded;
    }

    @Override
    public boolean addAll(final Collection<? extends E> objects) {
        for (final E item : objects) {
            this.add(item);
        }

        return true;
    }

    @Override
    public boolean offer(final E item) {
        return this.add(item);
    }
}
