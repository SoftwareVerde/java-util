package com.softwareverde.constable.map.mutable;

import com.softwareverde.constable.UnsafeVisitor;
import com.softwareverde.constable.Visitor;
import com.softwareverde.logging.Logger;
import com.softwareverde.util.Tuple;

import java.util.Iterator;
import java.util.Map;

public class MutableMapUtil {
    public static <Key, Value> void mutableVisit(final java.util.Map<Key, Value> javaMap, final MutableMap.MutableVisitor<Key, Value> visitor) {
        final java.util.Set<java.util.Map.Entry<Key, Value>> mutableEntrySet = javaMap.entrySet();
        final Iterator<Map.Entry<Key, Value>> mutableIterator = mutableEntrySet.iterator();

        while (mutableIterator.hasNext()) {
            final java.util.Map.Entry<Key, Value> mapEntry = mutableIterator.next();

            final Key key = mapEntry.getKey();
            final Value value = mapEntry.getValue();

            final Tuple<Key, Value> entry = new Tuple<>(key, value);

            boolean shouldContinue;
            try {
                 shouldContinue = visitor.run(entry);
            }
            catch (final Exception exception) {
                Logger.debug(exception);
                shouldContinue = false;
            }
            finally {
                visitor.andFinally();
            }

            if (entry.first == null) {
                mutableIterator.remove();
            }
            else if (entry.second != value) {
                mapEntry.setValue(entry.second);
            }

            if (! shouldContinue) { break; }
        }
    }

    public static <Key, Value> void visit(final java.util.Map<Key, Value> javaMap, final UnsafeVisitor<Tuple<Key, Value>> visitor) {
        final java.util.Set<java.util.Map.Entry<Key, Value>> entrySet = javaMap.entrySet();
        for (final java.util.Map.Entry<Key, Value> mapEntry : entrySet) {
            final Key key = mapEntry.getKey();
            final Value value = mapEntry.getValue();

            final Tuple<Key, Value> entry = new Tuple<>(key, value);
            boolean shouldContinue;
            try {
                shouldContinue = visitor.run(entry);
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

    protected MutableMapUtil() { }
}
