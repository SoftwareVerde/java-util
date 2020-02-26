package com.softwareverde.util;

public class Tuple<S, T> {
    public S first;
    public T second;

    public Tuple() { }

    public Tuple(final S first, final T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        return ( (this.first == null ? 0 : this.first.hashCode()) + (this.second == null ? 1 : this.second.hashCode()) );
    }

    @Override
    public boolean equals(final Object object) {
        if (object == this) { return true; }
        if (! (object instanceof Tuple)) { return false; }

        final Tuple<?,?> tuple = (Tuple<?,?>) object;
        return (Util.areEqual(this.first, tuple.first) && Util.areEqual(this.second, tuple.second));
    }

    @Override
    public String toString() {
        final String firstClassName = (this.first == null ? "?" : this.first.getClass().getName());
        final String secondClassName = (this.second == null ? "?" : this.second.getClass().getName());
        return "Tuple<" + firstClassName + "," + secondClassName + ">(" + this.first + "," + this.second + ")";
    }
}
