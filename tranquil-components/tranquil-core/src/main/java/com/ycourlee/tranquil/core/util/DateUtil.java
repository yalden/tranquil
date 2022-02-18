package com.ycourlee.tranquil.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author yooonnjiang
 * @date 2020.09.29
 */
public class DateUtil {

    public static final  String                        FULL                         = "yyyy-MM-dd HH:mm:ss";
    public static final  String                        NO_SECOND                    = "yyyy-MM-dd HH:mm";
    public static final  String                        NO_HOUR                      = "yyyy-MM-dd";
    public static final  String                        BEGIN_OF_DAY                 = "yyyy-MM-dd 00:00:00";
    public static final  String                        END_OF_DAY                   = "yyyy-MM-dd 23:59:59";
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_NO_HOUR   = new ThreadLocal<>();
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_NO_SECOND = new ThreadLocal<>();
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_FULL      = new ThreadLocal<>();

    public static String now() {
        return getNoHourSimpleDateFormat().format(new Date());
    }

    public static String format(Date date) {
        return getFullSimpleDateFormat().format(date);
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

    /**
     * 单位分钟
     * d2表示时间-d1表示时间
     *
     * @param arg1 date1
     * @param arg2 date2
     * @return interval
     */
    public static long minutesOfArg2SubArg1(Date arg1, Date arg2) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(arg1);
        long d1Mills = calendar.getTimeInMillis();
        calendar.setTime(arg2);
        return (calendar.getTimeInMillis() - d1Mills) / (60 * 1000);
    }

    public static long minutesOfArg2SubArg1(String arg1, String arg2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FULL);
        return minutesOfArg2SubArg1(sdf.parse(arg1), sdf.parse(arg2));
    }

    /**
     * date1表示的时间是否更早
     *
     * @param arg1 date1
     * @param arg2 date2
     * @return true, date1 earlier than date2 or equal to date2;
     */
    public static boolean arg1IsEarlierOrEqualed(Date arg1, Date arg2) {
        return minutesOfArg2SubArg1(arg1, arg2) >= 0;
    }

    public static boolean arg1IsEarlierOrEqualed(String arg1, String arg2) throws ParseException {
        return minutesOfArg2SubArg1(arg1, arg2) >= 0;
    }

    private static SimpleDateFormat fastSdfElection(String format) {
        if (FULL.equals(format)) {
            return getFullSimpleDateFormat();
        } else if (NO_SECOND.equals(format)) {
            return getNoSecondSimpleDateFormat();
        } else if (NO_HOUR.equals(format)) {
            return getNoHourSimpleDateFormat();
        } else {
            return new SimpleDateFormat(format);
        }
    }

    public static SimpleDateFormat getNoHourSimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = SIMPLE_DATE_FORMAT_NO_HOUR.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(NO_HOUR, Locale.getDefault());
            SIMPLE_DATE_FORMAT_NO_HOUR.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }

    public static SimpleDateFormat getNoSecondSimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = SIMPLE_DATE_FORMAT_NO_SECOND.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(NO_SECOND, Locale.getDefault());
            SIMPLE_DATE_FORMAT_NO_SECOND.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }

    public static SimpleDateFormat getFullSimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = SIMPLE_DATE_FORMAT_FULL.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(FULL, Locale.getDefault());
            SIMPLE_DATE_FORMAT_FULL.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }
}
