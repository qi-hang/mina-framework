package com.test.utils;

import java.util.Random;

/**
 * <p>
 * Title: StringUtil
 * </p>
 * <p>
 * Description:
 * </p>
 *
 * @version 1.0
 */
public class StringUtil {
    public static final String Empty = "";

    private StringUtil() {
    }

    /**
     * @param
     * @return
     */
    public static boolean isEmpty(String... args) {
        if (args != null && args.length > 0) {
            for (String s : args) {
                if (s == null || s.trim().length() < 1) {
                    return true;
                }
            }

            return false;
        }

        return true;
    }

    public static String RandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            builder.append(str.charAt(num));
        }
        return builder.toString();
    }
}