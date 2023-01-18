package com.softwareverde.constable;

public interface Visitor<T> {
    boolean run(T value);
}
