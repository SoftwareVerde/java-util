package com.softwareverde.util;

import com.softwareverde.logging.Log;
import com.softwareverde.util.numberformat.NumberFormatter;

import java.util.*;

public class Util {
    protected static final NumberFormatter _numberFormatter = new NumberFormatter();

    public static Integer parseInt(final String numberString) {
        if (numberString == null) { return null; }

        try {
            return _numberFormatter.parse(numberString.trim()).intValue();
        }
        catch (final Exception e) {
            Log.warn("Invalid integer string: " + numberString, e);
            return 0;
        }
    }

    public static Long parseLong(final String numberString) {
        if (numberString == null) { return null; }

        try {
            return _numberFormatter.parse(numberString.trim()).longValue();
        }
        catch (final Exception e) {
            Log.warn("Invalid long string: " + numberString, e);
            return 0L;
        }
    }

    public static Float parseFloat(final String numberString) {
        if (numberString == null) { return null; }

        try {
            return _numberFormatter.parse(numberString.trim()).floatValue();
        }
        catch (final Exception e) {
            Log.warn("Invalid float string: " + numberString, e);
            return 0.0F;
        }
    }

    public static Double parseDouble(final String numberString) {
        if (numberString == null) { return null; }

        try {
            return _numberFormatter.parse(numberString.trim()).doubleValue();
        }
        catch (final Exception e) {
            Log.warn("Invalid double string: " + numberString, e);
            return 0.0D;
        }
    }

    public static Boolean parseBool(final String stringValue) {
        if (stringValue == null) { return null; }
        final String trimmedStringValue = stringValue.trim();
        if (trimmedStringValue.equals("0")) { return false; }
        if (Util.parseInt(trimmedStringValue) > 0) { return true; }
        return trimmedStringValue.equalsIgnoreCase("true");
    }

    public static Boolean isInt(final String numberString) {
        try {
            _numberFormatter.parse(numberString.trim()).intValue();
            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    public static Boolean isLong(final String numberString) {
        try {
            _numberFormatter.parse(numberString.trim()).longValue();
            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    public static Boolean isFloat(final String numberString) {
        try {
            _numberFormatter.parse(numberString.trim()).floatValue();
            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    public static Boolean isDouble(final String numberString) {
        try {
            _numberFormatter.parse(numberString.trim()).doubleValue();
            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    /**
     * Returns true if the number should be interpreted as a boolean value.
     *  NOTE: isBool() is more restrictive than parseBool().
     *      For Instance, "1 Angry Duck" returns TRUE for Util.parseBool(),
     *      whereas the same string will return FALSE for Util.isBool()
     */
    public static Boolean isBool(final String numberString) {
        if (numberString == null) { return false; }

        final String trimmedNumberString = numberString.trim();

        if (trimmedNumberString.equalsIgnoreCase("true") || trimmedNumberString.equalsIgnoreCase("false")) {
            return true;
        }

        try {
            Integer.parseInt(numberString);
            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    public static Boolean coalesce(final Boolean bool) { return Util.coalesce(bool, false); }
    public static Float coalesce(final Float f) { return Util.coalesce(f, 0.0F); }
    public static Integer coalesce(final Integer number) { return Util.coalesce(number, 0); }
    public static Long coalesce(final Long number) { return Util.coalesce(number, 0L); }
    public static String coalesce(final String string) { return Util.coalesce(string, ""); }
    public static <T> T coalesce(final T value, final T defaultValue) { return (value != null ? value : defaultValue); }

    /**
     * Returns a shallow-copy of the list as an ArrayList.
     */
    public static <T> List<T> copyList(final List<T> list) {
        final List<T> newList = new ArrayList<T>(list.size());
        for (final T item : list) {
            newList.add(item);
        }
        return newList;
    }

    /**
     * Returns a shallow-copy of the set as a HashSet.
     */
    public static <T> Set<T> copySet(final Set<T> set) {
        final Set<T> newSet = new HashSet<T>(set.size());
        for (final T item : set) {
            newSet.add(item);
        }
        return newSet;
    }

    /**
     * Returns a shallow-copy of the set as a HashMap.
     */
    public static <T, S> Map<T, S> copyMap(final Map<T, S> map) {
        final Map<T, S> newMap = new HashMap<T, S>();
        for (final T key : map.keySet()) {
            final S value = map.get(key);
            newMap.put(key, value);
        }
        return newMap;
    }

    /**
     * Returns a shallow-copy of the array.
     */
    public static <T> T[] copyArray(final T[] array) {
        return array.clone();
    }
    public static byte[] copyArray(final byte[] array) { return array.clone(); }
    public static boolean[] copyArray(final boolean[] array) { return array.clone(); }
    public static int[] copyArray(final int[] array) { return array.clone(); }
    public static float[] copyArray(final float[] array) { return array.clone(); }
    public static long[] copyArray(final long[] array) { return array.clone(); }
    public static double[] copyArray(final double[] array) { return array.clone(); }
    public static char[] copyArray(final char[] array) { return array.clone(); }
    public static short[] copyArray(final short[] array) { return array.clone(); }

    /**
     * Performs a null-safe equality check.
     */
    public static <T> Boolean areEqual(final T a, final T b) {
        if (a == b) { return true; }
        if ( (a == null) || (b == null) ) { return false; }
        return a.equals(b);
    }
}
