package com.ycourlee.tranquil.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

/**
 * @author yongjiang
 * @date 2021.12.10
 */
public class StarMasker {

    private static final Logger log          = LoggerFactory.getLogger(StarMasker.class);
    private static final char   asteriskChar = '*';
    private static final String asterisk     = "*";

    public static String mask(@Nullable String sensitive, int begin) {
        return mask(sensitive, begin, sensitive == null ? 0 : sensitive.length(), true);
    }

    public static String mask(@Nullable String sensitive, int begin, boolean briefly) {
        return mask(sensitive, begin, sensitive == null ? 0 : sensitive.length(), briefly);
    }

    public static String mask(int end, @Nullable String sensitive) {
        return mask(sensitive, 0, end, true);
    }

    public static String mask(int end, @Nullable String sensitive, boolean briefly) {
        return mask(sensitive, 0, end, briefly);
    }

    public static String mask(@Nullable String sensitive, int begin, int end, boolean briefly) {
        if (sensitive == null) {
            return "null";
        }
        if (sensitive.isEmpty()) {
            return sensitive;
        }
        if (begin > sensitive.length()) {
            log.warn("String index out of bounds: {}", begin);
            return sensitive;
        }
        if (begin > end) {
            throw new IllegalArgumentException("Illegal bounds");
        }
        StringBuilder builder = new StringBuilder(sensitive.substring(0, begin));
        int stars = end - begin;
        if (briefly) {
            stars = Math.min(stars, 3);
        }
        for (int i = 0; i < stars; i++) {
            builder.append(asterisk);
        }
        builder.append(sensitive.substring(end));
        return builder.toString();
    }
}
