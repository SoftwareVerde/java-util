package com.softwareverde.util;

import com.softwareverde.logging.Logger;

public class HexUtil {
    private final static char[] HEX_ALPHABET = "0123456789ABCDEF".toCharArray();

    /**
     * Returns an uppercase hex representation of the provided bytes without any prefix.
     */
    public static String toHexString(final byte[] bytes) {
        final char[] hexChars = new char[bytes.length * 2];
        for (int j=0; j<bytes.length; ++j) {
            final int v = (bytes[j] & 0xFF);
            hexChars[j * 2] = HEX_ALPHABET[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ALPHABET[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Returns the decoded bytes from an uppercase (or lowercase) hex representation without any prefix.
     */
    public static byte[] hexStringToByteArray(final String hexString) {
        final Integer stringLength = hexString.length();
        if (stringLength % 2 != 0) { return null; }

        final byte[] data = new byte[stringLength / 2];
        for (int i = 0; i < stringLength; i += 2) {
            final int firstDigit = Character.digit(hexString.charAt(i), 16);
            final int secondDigit = Character.digit(hexString.charAt(i+1), 16);
            if (firstDigit == -1 || secondDigit == -1) {
                Logger.warn("Invalid hexadecimal string: " + hexString);
                return null;
            }
            data[i/2] = (byte) ((firstDigit << 4) + secondDigit);
        }
        return data;
    }
}
