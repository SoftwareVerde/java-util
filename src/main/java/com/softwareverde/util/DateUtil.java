package com.softwareverde.util;

import com.softwareverde.logging.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    /**
     * @param datetime  - The Y-m-d H:i:s date string. If null, return value is null.
     * @return          - Returns the epoch with milliseconds.
     *                  - Returns null if the value was invalid.
     */
    public static Long datetimeToTimestamp(final String datetime) {
        if (datetime == null) { return null; }

        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            final Date date = dateFormat.parse(datetime);
            return date.getTime();
        }
        catch (final Exception e) {
            Log.error("Invalid date-time.", e);
            return null;
        }
    }

    /**
     * Translates a timestamp (with milliseconds) to a Y-m-d H:i:s date string.
     * Returns null if timestamp is null.
     */
    public static String timestampToDatetimeString(final Long timestamp) {
        if (timestamp == null) { return null; }

        final Date date = new Date(timestamp);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return dateFormat.format(date);
    }

    /**
     * Translates a timestamp (with milliseconds) to a H:i:s time string.
     * Returns null if timestamp is null.
     */
    public static String timestampToTimeString(final Long timestamp) {
        if (timestamp == null) { return null; }

        final Date date = new Date(timestamp);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        return dateFormat.format(date);
    }
}
