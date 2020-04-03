package com.jkys.consult.shine.utils;

import hirondelle.date4j.DateTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by JetslyLi on 2015/4/13.
 */
public class DateUtils {
    /**
     * The UTC time zone (often referred to as GMT).
     * This is private as it is mutable.
     */
    private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT+8");

    /** yyyyMMdd */
    public static final String yyyyMMdd = "yyyyMMdd";

    /** MM-dd */
    public static final String MMdd_SPLIT = "MM-dd";

    /**
     * 时间添加分钟
     *
     * @param date   时间
     * @param amount 添加的小时
     * @return
     */
    public static DateTime addHours(final DateTime date, final int amount) {
        if(amount>0) {
            return date.plus(0, 0, 0, 0, amount, 0, 0, DateTime.DayOverflow.Spillover);
        }else {
            return date.minus(0, 0, 0, 0, Math.abs(amount), 0, 0, DateTime.DayOverflow.Spillover);
        }
    }


    public static Date addHours(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制
        return cal.getTime();
    }


        /**
         * 时间添加分钟
         *
         * @param date   时间
         * @param amount 添加的分钟数
         * @return
         */
    public static DateTime addMinutes(final DateTime date, final int amount) {
        if(amount>0) {
            return date.plus(0, 0, 0, 0, amount, 0, 0, DateTime.DayOverflow.Spillover);
        }else {
            return date.minus(0, 0, 0, 0, Math.abs(amount), 0, 0, DateTime.DayOverflow.Spillover);
        }
    }

    /**
     * 时间添加秒
     * @param date
     * @param amount
     * @return
     */
    public static DateTime addSeconds(final DateTime date, final int amount) {
        if(amount>0) {
            return date.plus(0, 0, 0, 0, 0, amount, 0, DateTime.DayOverflow.Spillover);
        }else {
            return date.minus(0, 0, 0, 0, 0, Math.abs(amount), 0, DateTime.DayOverflow.Spillover);
        }
    }

    /**
     * 获取UTC时间
     *
     * @return
     */
    public static DateTime utcDateTime() {
        return DateTime.now(UTC_TIME_ZONE);
    }


    /**
     * 获取Utc的时间
     *
     * @return
     */
    public static long utcMilliseconds() {
        return DateTime.now(UTC_TIME_ZONE).getMilliseconds(UTC_TIME_ZONE);
    }

    /**
     * 获取总秒数
     * @param dateTime
     * @return
     */
    public static long getTotalSeconds(DateTime dateTime) {
        return dateTime.getMilliseconds(DateUtils.UTC_TIME_ZONE)/1000;
    }
    
    public static long getTotalSeconds(DateTime dateTime,TimeZone timeZone) {
        return dateTime.getMilliseconds(timeZone)/1000;
    }
    
	public static Date parseDate(long aMilliseconds){
		Date date = null;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(aMilliseconds);
		date = cal.getTime();
		return date;
	}   

	public static String formatDate(Date date){
         SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		return sdf1.format(date);
	}
	public static String formatDate(Date date, String pattern){
		SimpleDateFormat tmpsdf = new SimpleDateFormat(pattern);
		return tmpsdf.format(date);
	}

    public static Date formatDate(String dateStr){
        SimpleDateFormat sdf1 = new SimpleDateFormat(yyyyMMdd);
        try {
            return sdf1.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

	public static Integer currentSeconds(){
		return ((Number)(System.currentTimeMillis()/1000)).intValue();
	}

    /**
     * 获取指定日期所在月份的第一天日期
     * @param date 格式：201809
     * @return LocalDate
     */
    private static LocalDate formatLocalDate(String date){
        return LocalDate.parse(date + "01", DateTimeFormatter.ofPattern(yyyyMMdd));
    }
    /**
     * 获取指定日期所在月份的第一天日期
     * @param date 格式：201809
     * @return 格式：2018-09-01
     */
    public static String getFirstDayOfMonth(String date){
        return formatLocalDate(date).toString();
    }

    /**
     * 获取指定日期所在月份的最后一天的日期
     * @param date 格式：201809
     * @return 格式：2018-09-30
     */
    public static String getLastDayOfMonth(String date){
        return formatLocalDate(date).with(TemporalAdjusters.lastDayOfMonth()).toString();
    }

    /**
     * 获取指定日期所在月份的下个月第一天的日期
     *
     * @param date 格式：201809
     *
     * @return 格式：2018-10-01
     */
    public static String getFirstDayOfNextMonth(String date) {
        return formatLocalDate(date).with(TemporalAdjusters.lastDayOfMonth()).minusDays(-1).toString();
    }
}