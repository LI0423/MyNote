package com.video.oversea.payment.util;

import java.time.*;
import java.util.Date;

public class SimpleDateUtils {

    /* 1秒钟对应的毫秒数 */
    public static final long SECCOND = 1000;

    /* 1分钟对应的毫秒数 */
    public static final long MINUTES = 60 * SECCOND;

    /* 1小时对应的毫秒数 */
    public static final long HOUR = 60 * MINUTES;

    /* 1天对应的毫秒数 */
    public static final long DAY = 24 * HOUR;

    /**
     * 将 <p>Date对象</p> 根据 <p>时区(zone)</p> 转换成对应的ZonedDateTime对象
     * @param date
     * @param zone
     * @return
     */
    public static ZonedDateTime toZonedDateTime(Date date, ZoneId zone) {
        return date.toInstant().atZone(zone);
    }

    /**
     * 将 <p>Date对象</p> 根据 <p>默认时区</p> 转换成对应的ZonedDateTime对象
     * @param date
     * @return
     */
    public static ZonedDateTime toZonedDateTime(Date date) {
        return toZonedDateTime(date, ZoneId.systemDefault());
    }

    /**
     * 将 <p>Date对象</p> 根据 <p>时区(zone)</p> 转换成对应的LocalDateTime对象
     * @param date
     * @param zone
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date, ZoneId zone) {
        return toZonedDateTime(date, zone).toLocalDateTime();
    }

    /**
     * 将 <p>Date对象</p> 根据 <p>默认时区</p> 转换成对应的LocalDateTime对象
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return toLocalDateTime(date, ZoneId.systemDefault());
    }

    /**
     * 将 <p>Date对象</p> 根据 <p>时区(zone)</p> 转换成对应的LocalDate信息
     * @param date
     * @param zone
     * @return
     */
    public static LocalDate toLocalDate(Date date, ZoneId zone) {
        return toZonedDateTime(date, zone).toLocalDate();
    }

    /**
     * 将 <p>Date对象</p> 根据 <p>默认时区</p> 转换成对应的LocalDate信息
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return toLocalDate(date, ZoneId.systemDefault());
    }

    /**
     * 将 <p>Date对象</p> 根据 <p>时区(zone)</p> 转换成对应的LocalTime信息
     * @param date
     * @param zone
     * @return
     */
    public static LocalTime toLocalTime(Date date, ZoneId zone) {
        return toZonedDateTime(date, zone).toLocalTime();
    }

    /**
     * 将 <p>Date对象</p> 根据 <p>默认时区</p> 转换成对应的LocalTime信息
     * @param date
     * @return
     */
    public static LocalTime toLocalTime(Date date) {
        return toLocalTime(date, ZoneId.systemDefault());
    }

    /**
     * 将 <p>ZonedDateTime对象</p> 转换成 <p>Date对象</p>
     * @param zonedDateTime
     * @return
     */
    public static Date toDate(ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 将 <p>ZonedDateTime对象</p> 根据 <p>时区(zone)</p> 转换成对应的 <p>Date对象</p>
     * @param localDateTime
     * @param zone
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime, ZoneId zone) {
        return toDate(localDateTime.atZone(zone));
    }

    /**
     * 将 <p>ZonedDateTime对象</p> 根据 <p>默认时区</p> 转换成对应的 <p>Date对象</p>
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return toDate(localDateTime, ZoneId.systemDefault());
    }

    public static Date addDay(Date date, int days) {
        return new Date(days * DAY + date.getTime());
    }

    public static Date addMinute(Date date, int minute) {
        return new Date(minute * MINUTES + date.getTime());
    }

    /**
     * 获取截断了date参数的时间数据后的Date对象
     * @param date
     * @return
     */
    public static Date toCutoffTimeAccuracyDate(Date date) {
        LocalDate localDate = toLocalDate(date);
        ZonedDateTime zonedDate = localDate.atTime(0, 0, 0, 0).atZone(ZoneId.systemDefault());
        return toDate(zonedDate);
    }

    /**
     * 获取两个Date相距的自然天
     * @param before
     * @param after
     * @return
     */
    public static int diffNaturalDays(Date before, Date after) {
        Date nBefore = toCutoffTimeAccuracyDate(before);
        Date nAfter = toCutoffTimeAccuracyDate(after);
        return Math.toIntExact((nAfter.getTime() - nBefore.getTime()) / DAY);
    }

    public static int diffHour(Date before, Date after) {
        return Math.toIntExact((after.getTime() - before.getTime()) / HOUR);
    }

    /**
     * 判断after时间是不是在before时间之后
     * @param after
     * @param before
     * @return after时间是不是在before时间之后
     */
    public static boolean isAfter(Date after, Date before) {
        return after.getTime() > before.getTime();
    }

    /**
     * 判断before时间是不是在after时间之前
     * @param before
     * @param after
     * @return before时间是不是在after时间之前
     */
    public static boolean isBefore(Date before, Date after) {
        return after.getTime() < before.getTime();
    }
}
