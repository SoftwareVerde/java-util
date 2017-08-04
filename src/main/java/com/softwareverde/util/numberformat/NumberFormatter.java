package com.softwareverde.util.numberformat;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * NumberFormatter is a thread-safe number formatter that intelligently handles US numbers (e.g. commas, decimals, etc).
 */
public class NumberFormatter {
    protected static final ThreadLocal<NumberFormat> _threadLocalNumberFormat = new ThreadLocal<NumberFormat>();

    private NumberFormat _getNumberFormat() {
        final NumberFormat numberFormat = _threadLocalNumberFormat.get();
        if (numberFormat != null) {
            return numberFormat;
        }
        else {
            _threadLocalNumberFormat.set(NumberFormat.getNumberInstance(java.util.Locale.US));
            return _threadLocalNumberFormat.get();
        }
    }

    public Number parse(final String numberString) throws ParseException {
        return _getNumberFormat().parse(numberString);
    }

    public String format(final Integer number) {
        return _getNumberFormat().format(number);
    }

    public String format(final Long number) {
        return _getNumberFormat().format(number);
    }

    public String format(final Double number) {
        return _getNumberFormat().format(number);
    }

    public String format(final Float number) {
        return _getNumberFormat().format(number);
    }
}