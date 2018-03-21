package com.softwareverde.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static String md5(final String s) {
        try {
            final MessageDigest messageDigest = java.security.MessageDigest.getInstance("MD5");
            final byte[] array = messageDigest.digest(s.getBytes());
            final StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                stringBuilder.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return stringBuilder.toString();
        }
        catch (final NoSuchAlgorithmException exception) {
            return null;
        }
    }

    public static String sha256(final String s) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            final byte[] array = messageDigest.digest(s.getBytes());
            final StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                stringBuilder.append(Integer.toHexString((array[i] & 0xFF) | 0x0100).substring(1,3));
            }
            return stringBuilder.toString();
        }
        catch (final NoSuchAlgorithmException exception) {
            return null;
        }
    }
}
