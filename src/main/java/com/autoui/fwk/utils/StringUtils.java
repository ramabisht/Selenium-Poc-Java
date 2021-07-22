package com.autoui.fwk.utils;

import java.util.Base64;
import java.util.Random;

/**
 * String utility class
 *
 * @author rama.bisht
 */
public class StringUtils {

    /**
     * Get the corresponding ENUM value from a String value
     *
     * @param <T>       Generic class of type Enum
     * @param enumClass ENUM class
     * @param string    String value for which corresponding ENUM values has to be
     *                  found
     * @return ENUM value
     * @deprecated Code restructure. Use {@link EnumUtils#getEnumFromString(Class, String)}
     */
    @Deprecated
    public static <T extends Enum<T>> T getEnumFromString(Class<T> enumClass, String string) {
        if (enumClass != null && string != null) {
            try {
                return Enum.valueOf(enumClass, string.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
            }
        }
        return null;
    }

    /**
     * Encode String using Base64
     *
     * @param plainString String to be encoded
     * @return Encoded string
     */
    public static String encodeStringToBase64(String plainString) {
        return Base64.getEncoder()
                .encodeToString(plainString.getBytes());
    }

    /**
     * Decode Base64 encoded string
     *
     * @param encodedString String to be decoded
     * @return Decoded String
     */
    public static String decodeBase64String(String encodedString) {
        return new String(Base64.getDecoder().decode(encodedString));}

        /**
         * Generate Random String
         * @return String
         */

    public static String getAlphaNumericString() {
            int length = 5;
            int lowerLimit = 97;
            int upperLimit = 122;
            Random random = new Random();
            StringBuffer r = new StringBuffer(length);
            for (int i = 0; i < length; i++) {
                int nextRandomChar = lowerLimit
                        + (int)(random.nextFloat()
                        * (upperLimit - lowerLimit + 1));
                r.append((char)nextRandomChar);
            }
            return r.toString();
        }
    }

