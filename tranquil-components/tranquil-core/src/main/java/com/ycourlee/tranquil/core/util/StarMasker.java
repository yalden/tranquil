package com.ycourlee.tranquil.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

/**
 * @author yooonn
 * @date 2021.12.10
 */
public class StarMasker {

    private static final Logger log      = LoggerFactory.getLogger(StarMasker.class);
    private static final String asterisk = "*";

    private StarMasker() {
    }

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

    /**
     * 对文本的打星处理
     *
     * @param sensitive 敏感文本
     * @param begin     此位置开始打星
     * @param end       此位置停止打星
     * @param briefly   若为true, 则星号部分的长度是3
     * @return 打星的字符串
     */
    public static String mask(@Nullable String sensitive, int begin, int end, boolean briefly) {
        if (sensitive == null) {
            return "null";
        }
        if (sensitive.isEmpty()) {
            return sensitive;
        }
        if (begin > sensitive.length() || begin > end) {
            log.warn("nothing to mask: {}", begin);
            return sensitive;
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
