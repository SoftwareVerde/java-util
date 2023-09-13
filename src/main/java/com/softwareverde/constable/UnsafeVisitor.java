package com.softwareverde.constable;

public interface UnsafeVisitor<T> {
    boolean run(T value) throws Exception;
    default void andFinally() { }
}
