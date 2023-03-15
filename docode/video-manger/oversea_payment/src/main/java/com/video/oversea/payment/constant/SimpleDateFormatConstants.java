package com.video.oversea.payment.constant;

import java.text.SimpleDateFormat;

public class SimpleDateFormatConstants {
    private static final ThreadLocal<SimpleDateFormat> SHORT_DATE_FORMAT = new ThreadLocal<>();

    private static final ThreadLocal<SimpleDateFormat> LONG_DATE_FORMAT = new ThreadLocal<>();

    private static final ThreadLocal<SimpleDateFormat> LONG_TIME_FORMAT = new ThreadLocal<>();

    public static SimpleDateFormat getShortDateFormat() {
        SimpleDateFormat shortDateFormat = SHORT_DATE_FORMAT.get();
        if (shortDateFormat == null) {
            shortDateFormat = new SimpleDateFormat("yyyyMMdd");
            SHORT_DATE_FORMAT.set(shortDateFormat);
        }
        return shortDateFormat;
    }

    public static SimpleDateFormat getLongDateFormat() {
        SimpleDateFormat longDateFormat = LONG_DATE_FORMAT.get();
        if (longDateFormat == null) {
            longDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            LONG_DATE_FORMAT.set(longDateFormat);
        }
        return longDateFormat;
    }

    public static SimpleDateFormat getLongTimeFormat() {
        SimpleDateFormat longTimeFormat = LONG_TIME_FORMAT.get();
        if (longTimeFormat == null) {
            longTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LONG_TIME_FORMAT.set(longTimeFormat);
        }
        return longTimeFormat;
    }

    public static void clearShortDateFormat() {
        SHORT_DATE_FORMAT.remove();
    }

    public static void clearLongDateFormat() {
        LONG_DATE_FORMAT.remove();
    }

    public static void clearLongTimeFormat() {
        LONG_TIME_FORMAT.remove();
    }
}
