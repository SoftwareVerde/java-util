package com.softwareverde.util;

import com.softwareverde.util.numberformat.NumberFormatter;

import java.util.*;

public class Util {
    protected static final NumberFormatter _numberFormatter = new NumberFormatter();

    public static Integer parseInt(final String numberString) {
        if (numberString == null) { return null; }
        try { return _numberFormatter.parse(numberString.trim()).intValue(); }
        catch (final Exception e) { return 0; }
    }

    public static Long parseLong(final String numberString) {
        if (numberString == null) { return null; }
        try { return _numberFormatter.parse(numberString.trim()).longValue(); }
        catch (final Exception e) { return 0L; }
    }

    public static Float parseFloat(final String numberString) {
        if (numberString == null) { return null; }
        try { return _numberFormatter.parse(numberString.trim()).floatValue(); }
        catch (final Exception e) { return 0.0F; }
    }

    public static Double parseDouble(final String numberString) {
        if (numberString == null) { return null; }
        try { return _numberFormatter.parse(numberString.trim()).doubleValue(); }
        catch (final Exception e) { return 0.0D; }
    }

    public static Boolean parseBool(final String numberString) {
        if (numberString == null) { return null; }
        final String trimmedNumberString = numberString.trim();
        if (trimmedNumberString.equals("0")) { return false; }
        if (Util.parseInt(trimmedNumberString) > 0) { return true; }
        return Boolean.parseBoolean(trimmedNumberString);
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

    public static Boolean isBool(final String numberString) {
        if (numberString == null) { return false; }

        final String trimmedNumberString = numberString.trim();

        if (trimmedNumberString.equalsIgnoreCase("true") || trimmedNumberString.equalsIgnoreCase("false")) {
            return true;
        }

        try {
            _numberFormatter.parse(numberString).intValue();
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
     * Performs a null-safe equality check.
     */
    public static <T> Boolean areEqual(final T a, final T b) {
        if (a == b) { return true; }
        if ( (a == null) || (b == null) ) { return false; }
        return a.equals(b);
    }
}
