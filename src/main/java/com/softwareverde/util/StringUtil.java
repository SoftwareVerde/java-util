package com.softwareverde.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    protected static final NumberFormat _numberFormat = NumberFormat.getNumberInstance(java.util.Locale.US);

    /**
     * Returns a new string from UTF-8 bytes.
     *  NOTE: Returns an empty string if the conversion fails.
     */
    public static String bytesToString(final byte[] bytes) {
        try { return new String(bytes, "UTF-8"); }
        catch (final UnsupportedEncodingException e) { return ""; }
    }

    public static String formatNumberString(final Integer number) {
        if (number == null) return "";
        return _numberFormat.format(number);
    }

    public static List<String> pregMatch(final String regex, final String haystack) {
        final List<String> matches = new ArrayList<String>();
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(haystack);
        if (matcher.find()) {
            for (Integer i=0; i<matcher.groupCount(); ++i) {
                matches.add(matcher.group(i+1));
            }
        }
        return matches;
    }

    /**
     * Replaces question-marks within @string with the params.
     * Note: params must not contain a question-mark character ("?").
     */
    public static String sprintf(final String string, final String... params) {
        String newString = string;
        for (final String param : params) {
            newString = newString.replaceFirst("\\?", "'"+ param.replaceAll("\\?", "") + "'");
        }
        return newString;
    }

    public static String urlEncode(final String string) {
        if (string == null) { return ""; }
        try { return URLEncoder.encode(string, "UTF-8"); }
        catch (final UnsupportedEncodingException e) { return ""; }
    }

    public static int computeLevenshteinDistance (final String s0, final String s1) {
        final int len0 = s0.length() + 1;
        final int len1 = s1.length() + 1;

        int[] cost = new int[len0];
        int[] newcost = new int[len0];
        for (int i = 0; i < len0; i++) cost[i] = i;
        for (int j = 1; j < len1; j++) {
            newcost[0] = j;
            for (int i = 1; i < len0; i++) {
                final int match = ( (s0.charAt(i - 1) == s1.charAt(j - 1)) ? 0 : 1 );
                final int cost_replace = cost[i - 1] + match;
                final int cost_insert  = cost[i] + 1;
                final int cost_delete  = newcost[i - 1] + 1;
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }
            int[] swap = cost; cost = newcost; newcost = swap;
        }
        return cost[len0 - 1];
    }
}
