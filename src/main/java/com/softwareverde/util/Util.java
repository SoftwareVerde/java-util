package com.softwareverde.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util {
    protected static final NumberFormat _numberFormat = NumberFormat.getNumberInstance(java.util.Locale.US);

    public static Integer parseInt(final String numberString) {
        if (numberString == null) { return null; }
        try { return _numberFormat.parse(numberString.trim()).intValue(); }
        catch (final Exception e) { return 0; }
    }

    public static Long parseLong(final String numberString) {
        if (numberString == null) { return null; }
        try { return _numberFormat.parse(numberString.trim()).longValue(); }
        catch (final Exception e) { return 0L; }
    }

    public static Float parseFloat(final String numberString) {
        if (numberString == null) { return null; }
        try { return _numberFormat.parse(numberString.trim()).floatValue(); }
        catch (final Exception e) { return 0.0F; }
    }

    public static Double parseDouble(final String numberString) {
        if (numberString == null) { return null; }
        try { return _numberFormat.parse(numberString.trim()).doubleValue(); }
        catch (final Exception e) { return 0.0D; }
    }

    public static Boolean parseBool(final String numberString) {
        if (numberString == null) { return null; }
        final String trimmedNumberString = numberString.trim();
        if (trimmedNumberString.equals("0")) { return false; }
        if (Util.parseInt(trimmedNumberString) > 0) { return true; }
        return Boolean.parseBoolean(trimmedNumberString);
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
     * Performs a null-safe equality check.
     */
    public static <T> Boolean areEqual(final T a, final T b) {
        if (a == b) { return true; }
        if ( (a == null) || (b == null) ) { return false; }
        return a.equals(b);
    }

}