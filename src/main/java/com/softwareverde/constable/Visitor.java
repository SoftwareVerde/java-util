package com.softwareverde.constable;

public interface Visitor<T> extends UnsafeVisitor<T> {
    @Override
    boolean run(T value);
}
