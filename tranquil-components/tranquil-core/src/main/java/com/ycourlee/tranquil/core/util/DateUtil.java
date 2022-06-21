package com.ycourlee.tranquil.core.util;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yooonn
 * @date 2020.09.29
 */
public class DateUtil {

    public static final String                        FULL                         = "yyyy-MM-dd HH:mm:ss";
    public static final String                        NO_SECOND                    = "yyyy-MM-dd HH:mm";
    public static final String                        NO_HOUR                      = "yyyy-MM-dd";
    public static final String                        BEGIN_OF_DAY                 = "yyyy-MM-dd 00:00:00";
    public static final String                        END_OF_DAY                   = "yyyy-MM-dd 23:59:59";
    public static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_NO_HOUR   = ThreadLocal.withInitial(() -> new SimpleDateFormat(NO_HOUR));
    public static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_NO_SECOND = ThreadLocal.withInitial(() -> new SimpleDateFormat(NO_SECOND));
    public static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_FULL      = ThreadLocal.withInitial(() -> new SimpleDateFormat(FULL));

    public static String now() {
        return SIMPLE_DATE_FORMAT_NO_HOUR.get().format(new Date());
    }

    public static String format(Date date) {
        return SIMPLE_DATE_FORMAT_FULL.get().format(date);
    }

    public static String format(Date date, String targetFormat) {
        Assert.notNull(date, "date cannot be null.");
        Assert.notBlank(targetFormat, "target format can not blank.");
        SimpleDateFormat sdf = fastSdfElection(targetFormat);
        return sdf.format(date);
    }

    public static String format(String date, String sourceFormat, String targetFormat) {
        return format(parse(date, sourceFormat), targetFormat);
    }

    @Nullable
    public static Date parse(String date, String itFormat) {
        Assert.notBlank(date, "date can not blank.");
        Assert.notBlank(date, "it's format can not blank.");
        SimpleDateFormat sdf = fastSdfElection(itFormat);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SimpleDateFormat fastSdfElection(String format) {
        if (FULL.equals(format)) {
            return SIMPLE_DATE_FORMAT_FULL.get();
        } else if (NO_SECOND.equals(format)) {
            return SIMPLE_DATE_FORMAT_NO_SECOND.get();
        } else if (NO_HOUR.equals(format)) {
            return SIMPLE_DATE_FORMAT_NO_HOUR.get();
        } else {
            return new SimpleDateFormat(format);
        }
    }
}
