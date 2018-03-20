package com.softwareverde.util;

public class Container <T> {
    volatile public T value;

    public Container() { }
    public Container(final T initialValue) {
        this.value = initialValue;
    }
}
