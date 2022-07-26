package com.softwareverde.util;

import com.softwareverde.logging.Logger;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {
    protected static final Unsafe UNSAFE;
    static {
        Unsafe unsafe = null;
        try {
            final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);
        }
        catch (final Exception exception) {
            Logger.warn(exception);
        }
        UNSAFE = unsafe;
    }

    protected ReflectionUtil() { }

    public static <T> void setValue(final Object object, final String memberName, final T newValue) {
        Exception lastException;
        Class<?> clazz = object.getClass();
        do {
            try {
                final Field field = clazz.getDeclaredField(memberName);

                if (UNSAFE != null) { // Prefer using the UNSAFE method of reflection for better compatibility with Java 16+
                    final long memoryOffset = UNSAFE.objectFieldOffset(field);
                    UNSAFE.putObject(object, memoryOffset, newValue);
                }
                else { // Fallback to the old version of reflection if the UNSAFE is unavailable (should never happen)
                    field.setAccessible(true);
                    field.set(object, newValue);
                }

                return;
            }
            catch (final Exception exception) {
                lastException = exception;
            }
        } while ((clazz = clazz.getSuperclass()) != null);

        throw (lastException instanceof RuntimeException ? (RuntimeException) lastException : new RuntimeException(lastException));
    }

    public static <T> T getValue(final Object object, final String memberName) {
        return ReflectionUtil.getValue(object.getClass(), object, memberName);
    }

    public static <T> T getStaticValue(final Class<?> objectClass, final String memberName) {
        return ReflectionUtil.getValue(objectClass, null, memberName);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(final Class<?> objectClass, final Object object, final String memberName) {
        Exception lastException;
        Class<?> clazz = objectClass;
        do {
            try {
                final Field field = clazz.getDeclaredField(memberName);

                if (UNSAFE != null) { // Prefer using the UNSAFE method of reflection for better compatibility with Java 16+
                    final long memoryOffset = UNSAFE.objectFieldOffset(field);
                    return (T) UNSAFE.getObject(object, memoryOffset);
                }
                else { // Fallback to the old version of reflection if the UNSAFE is unavailable (should never happen)
                    field.setAccessible(true);
                    return (T) field.get(object);
                }
            }
            catch (final Exception exception) {
                lastException = exception;
            }
        } while ((clazz = clazz.getSuperclass()) != null);

        throw (lastException instanceof RuntimeException ? (RuntimeException) lastException : new RuntimeException(lastException));
    }

    /**
     * This method uses traditional reflection to invoke the methodName function on the provided object, regardless of its access modifier.
     * params may not have null values; use ReflectionUtil.invoke(Object, String, Object[], Class[]) to invoke a method with null parameters.
     *
     * NOTE: Due to restrictions beginning with Java 16, classes within java.*, javax.*, and sun.* packages cannot be accessed with traditional reflection.
     *  For member access, this can be worked around by using the UNSAFE method of reflection; however, the UNSAFE does not provide a method to invoke methods.
     *  Attempting to access these package methods via MethodHandles.Lookup also failed due to the same security violation.
     *  Therefore, attempting to call invoke on a protected class will not work.
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(final Object object, final String methodName, final Object... params) {
        int paramCount = (params != null ? params.length : 0);

        final Class<?>[] parameterTypes = new Class<?>[paramCount];
        for (int i = 0; i < paramCount; ++i) {
            final Object param = params[i];
            parameterTypes[i] = param.getClass();
        }

        Exception lastException;
        Class<?> clazz = object.getClass();
        do {
            try {
                final Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
                method.setAccessible(true);
                return (T) method.invoke(object, params);
            }
            catch (final Exception exception) {
                lastException = exception;
            }
        } while ((clazz = clazz.getSuperclass()) != null);

        throw (lastException instanceof RuntimeException ? (RuntimeException) lastException : new RuntimeException(lastException));
    }

    @SuppressWarnings("unchecked")
    public static <T> T invoke(final Object object, final String methodName, final Object[] params, final Class<?>[] paramClasses) {
        int paramCount = (params != null ? params.length : 0);

        final Class<?>[] parameterTypes = new Class<?>[paramCount];
        for (int i = 0; i < paramCount; ++i) {
            parameterTypes[i] = paramClasses[i];
        }

        Exception lastException;
        Class<?> clazz = object.getClass();
        do {
            try {
                final Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
                method.setAccessible(true);
                return (T) method.invoke(object, params);
            }
            catch (final Exception exception) {
                lastException = exception;
            }
        } while ((clazz = clazz.getSuperclass()) != null);

        throw (lastException instanceof RuntimeException ? (RuntimeException) lastException : new RuntimeException(lastException));
    }

    public static List<String> getFieldNames(final Object object) {
        RuntimeException lastException = null;

        final List<String> fieldNames = new ArrayList<>();

        Class<?> clazz = object.getClass();
        do {
            try {
                final Field[] fields = clazz.getDeclaredFields();
                for (final Field field : fields) {
                    fieldNames.add(field.getName());
                }
            }
            catch (final RuntimeException exception) {
                lastException = exception;
            }
        } while ((clazz = clazz.getSuperclass()) != null);

        if (lastException != null) {
            throw lastException;
        }

        return fieldNames;
    }
}