package com.softwareverde.util;

import com.softwareverde.logging.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    public static class Utc {
        public static final TimeZone UTC = TimeZone.getTimeZone("UTC");
        /**
         * Parses the provided datetime string and returns the epoch-equivalent for the UTC timezone.
         *  The expected format for datetime "yyyy-MM-dd HH:mm:ss".
         *
         * @param datetime  The "yyyy-MM-dd HH:mm:ss" date string. If null, return value is null.
         *                      Example: 2000-12-31 23:59:59
         *                      The dateTime string is expected to be in the machine's timezone.
         *
         * @return          Returns the epoch with milliseconds relative to the machine's timezone or null if the value was invalid.
         */
        public static Long datetimeToTimestamp(final String datetime) {
            return DateUtil.datetimeToTimestamp(datetime, UTC);
        }

        /**
         * Parses datetime and returns a Java Date object for the machine's the UTC timezone.
         *  The expected format for datetime is yyyy-MM-dd HH:mm:ss--a Mysql compatible format.
         *
         * @param datetime  The "yyyy-MM-dd HH:mm:ss" date string. If null, return value is null.
         *                      The dateTime string is expected to be in the machine's timezone.
         *
         * @return          Returns the Java Date object relative to the machine's timezone.
         *                      Returns null if the value was invalid.
         */
        public static Date datetimeToDate(final String datetime) {
            return DateUtil.datetimeToDate(datetime, UTC);
        }

        /**
         * Formats the provided timestamp as a string for the machine's the UTC timezone.
         *  The returned format is "yyyy-MM-dd HH:mm:ss"--a Mysql compatible format.
         *
         * @param timestamp Translates a timestamp (with milliseconds) to a "yyyy-MM-dd HH:mm:ss" date string.
         *                      The dateTime string is formatted for the machine's timezone.
         *
         * @return          Returns null if timestamp is null.
         */
        public static String timestampToDatetimeString(final Long timestamp) {
            return DateUtil.timestampToDatetimeString(timestamp, UTC);
        }

        /**
         * Formats the provided timestamp as a time-only string for the UTC timezone.
         *  The returned format is "HH:mm:ss".
         *
         * @param timestamp Translates a timestamp (with milliseconds) to a "HH:mm:ss" time string.
         *                      The time string is formatted for the machine's timezone.
         * @return          Returns null if timestamp is null.
         */
        public static String timestampToTimeString(final Long timestamp) {
            return DateUtil.timestampToTimeString(timestamp, UTC);
        }

        /**
         * Formats the provided date object as a string for the UTC timezone.
         *  The returned format is "yyyy-MM-dd HH:mm:ss"--a Mysql compatible format.
         *
         * @param date      The Java Date to be formatted.
         *
         * @return          Returns null if timestamp is null.
         */
        public static String dateToDatetimeString(final Date date) {
            if (date == null) { return null; }
            return DateUtil.dateToDatetimeString(date, UTC);
        }
    }

    /**
     * Parses datetime and returns a Java Date object for the provided timezone.
     *  The expected format for datetime is yyyy-MM-dd HH:mm:ss--a Mysql compatible format.
     *
     * @param datetime  The "yyyy-MM-dd HH:mm:ss" date string. If null, return value is null.
     *                      The dateTime string is expected to be in the machine's timezone.
     *
     * @param timeZone  The timeZone used when creating the dateTime string.
     *
     * @return          Returns the date object or null if the value was invalid.
     */
    public static Date datetimeToDate(final String datetime, final TimeZone timeZone) {
        if (datetime == null) { return null; }

        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            dateFormat.setTimeZone(timeZone);
            return dateFormat.parse(datetime);
        }
        catch (final Exception exception) {
            Logger.warn("Invalid datetime string: "+ datetime, exception);
            return null;
        }
    }

    /**
     * Formats the provided date object as a string for the provided timezone.
     *  The returned format is "yyyy-MM-dd HH:mm:ss"--a Mysql compatible format.
     *
     * @param date      The Java Date to be formatted.
     *
     * @param timeZone  The Java TimeZone.  Timestamp will be interpreted as being captured within the provided timeZone.
     *
     * @return          Returns null if timestamp is null.
     */
    public static String dateToDatetimeString(final Date date, final TimeZone timeZone) {
        if (date == null) { return null; }

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(date);
    }

    /**
     * Formats the provided timestamp as a string for the provided timezone.
     *  The returned format is "yyyy-MM-dd HH:mm:ss"--a Mysql compatible format.
     *
     * @param timestamp The Java timestamp with milliseconds.
     *
     * @param timeZone  The Java TimeZone.  Timestamp will be interpreted as being captured within the provided timeZone.
     *
     * @return          Returns null if timestamp is null.
     */
    public static String timestampToDatetimeString(final Long timestamp, final TimeZone timeZone) {
        if (timestamp == null) { return null; }

        final Date date = new Date(timestamp);
        return dateToDatetimeString(date, timeZone);
    }

    /**
     * Formats the provided timestamp as a time-only string for the provided timezone.
     *  The returned format is "HH:mm:ss".
     *
     * @param timestamp The Java timestamp with milliseconds.
     *
     * @param timeZone  The Java TimeZone.  Timestamp will be interpreted as being captured within the provided timeZone.
     *
     * @return          Returns null if timestamp is null.
     */
    public static String timestampToTimeString(final Long timestamp, final TimeZone timeZone) {
        if (timestamp == null) { return null; }

        final Date date = new Date(timestamp);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(date);
    }

    /**
     * Parses the provided datetime string and returns the epoch-equivalent for the provided timezone.
     *  The expected format for datetime "yyyy-MM-dd HH:mm:ss".
     *
     * @param datetime  The "yyyy-MM-dd HH:mm:ss" date string. If null, return value is null.
     *                      Example: 2000-12-31 23:59:59
     *                      The dateTime string is expected to be in the machine's timezone.
     *
     * @param timeZone  The timeZone used when creating the dateTime string.
     *
     * @return          Returns the epoch with milliseconds or null if the value was invalid.
     */
    public static Long datetimeToTimestamp(final String datetime, final TimeZone timeZone) {
        final Date date = datetimeToDate(datetime, timeZone);
        if (date == null) { return null; }
        return date.getTime();
    }

    /**
     * Parses the provided datetime string and returns the epoch-equivalent for the machine's local timezone.
     *  The expected format for datetime "yyyy-MM-dd HH:mm:ss".
     *
     * @param datetime  The "yyyy-MM-dd HH:mm:ss" date string. If null, return value is null.
     *                      Example: 2000-12-31 23:59:59
     *                      The dateTime string is expected to be in the machine's timezone.
     *
     * @return          Returns the epoch with milliseconds relative to the machine's timezone or null if the value was invalid.
     */
    public static Long datetimeToTimestamp(final String datetime) {
        return datetimeToTimestamp(datetime, TimeZone.getDefault());
    }

    /**
     * Parses datetime and returns a Java Date object for the machine's local timezone.
     *  The expected format for datetime is yyyy-MM-dd HH:mm:ss--a Mysql compatible format.
     *
     * @param datetime  The "yyyy-MM-dd HH:mm:ss" date string. If null, return value is null.
     *                      The dateTime string is expected to be in the machine's timezone.
     *    
     * @return          Returns the Java Date object relative to the machine's timezone.
     *                      Returns null if the value was invalid.
     */
    public static Date datetimeToDate(final String datetime) {
        return datetimeToDate(datetime, TimeZone.getDefault());
    }

    /**
     * Formats the provided timestamp as a string for the machine's local timezone.
     *  The returned format is "yyyy-MM-dd HH:mm:ss"--a Mysql compatible format.
     *
     * @param timestamp Translates a timestamp (with milliseconds) to a "yyyy-MM-dd HH:mm:ss" date string.
     *                      The dateTime string is formatted for the machine's timezone.
     *
     * @return          Returns null if timestamp is null.
     */
    public static String timestampToDatetimeString(final Long timestamp) {
        return timestampToDatetimeString(timestamp, TimeZone.getDefault());
    }

    /**
     * Formats the provided timestamp as a time-only string for the machine's local timezone.
     *  The returned format is "HH:mm:ss".
     *
     * @param timestamp Translates a timestamp (with milliseconds) to a "HH:mm:ss" time string.
     *                      The time string is formatted for the machine's timezone.
     * @return          Returns null if timestamp is null.
     */
    public static String timestampToTimeString(final Long timestamp) {
        return timestampToTimeString(timestamp, TimeZone.getDefault());
    }

    /**
     * Formats the provided date object as a string for the machine's local timezone.
     *  The returned format is "yyyy-MM-dd HH:mm:ss"--a Mysql compatible format.
     *
     * @param date      The Java Date to be formatted.
     *
     * @return          Returns null if timestamp is null.
     */
    public static String dateToDatetimeString(final Date date) {
        if (date == null) { return null; }
        return dateToDatetimeString(date, TimeZone.getDefault());
    }


}
