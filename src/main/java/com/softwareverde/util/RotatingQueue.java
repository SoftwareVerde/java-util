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

    protected final Integer _maxItemCount;

    public RotatingQueue(final Integer maxItemCount) {
        this._maxItemCount = maxItemCount;
    }

    protected boolean _addItem(final E item) {
        final boolean itemWasAdded = super.offer(item);

        while (itemWasAdded && this.size() > _maxItemCount) {
            this.remove();
        }

        return itemWasAdded;
    }

    @Override
    public boolean add(final E item) {
        return _addItem(item);
    }

    @Override
    public boolean addAll(final Collection<? extends E> objects) {
        for (final E item : objects) {
            _addItem(item);
        }

        return true;
    }

    @Override
    public boolean offer(final E item) {
        return _addItem(item);
    }
}
