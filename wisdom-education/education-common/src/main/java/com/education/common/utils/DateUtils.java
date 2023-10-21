package com.education.common.utils;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 *
 * @author zengjintao
 * @version 1.0
 * @create 2018-08-04 15:01
 **/
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static final SimpleDateFormat FORMAT = getDateFormat("yyy-MM-dd HH:mm");

    public static final SimpleDateFormat YEAR = getDateFormat("yyyy-MMdd");

    public static final SimpleDateFormat SECOND = getDateFormat("yyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat MONTH = getDateFormat("yyyy-MM");

    public static final SimpleDateFormat HOUR = getDateFormat("HH:mm");

    public static final SimpleDateFormat CHINA_YEAR = getDateFormat("yyy年MM月dd日");

    public static final SimpleDateFormat DATE_CODE = getDateFormat("yyy-MM-ddHHmmss");

    public static final SimpleDateFormat DAY = getDateFormat("yyy-MM-dd");

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 返回指定日期格式
     *
     * @param format
     * @return
     */
    public static SimpleDateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 将日期转换成 yyy-MM-dd HH:mm 字符串形式
     *
     * @return
     */
    public static String getFormatDate(Date date) {
        return FORMAT.format(date);
    }

    public static String secondToHourMinute(long second) {
        return secondToHourMinute(second, null);
    }

    public static String secondToHourMinute(long second, String separator) {
        long h = second / 3600;
        long m = (second % 3600) / 60;
        long s = (second % 3600) % 60;
        StringBuilder time = new StringBuilder();
        if (StrUtil.isBlank(separator)) {
            if (h > 0) {
                time.append(h + "小时");
            }
            if (m > 0) {
                time.append(m + "分");
            }
            if (s > 0 && s <= 9) {
                time.append("0" + s + "秒");
            } else {
                time.append(s + "秒");
            }
        } else {
            if (h > 0) {
                time.append(h).append(separator);
            }
            time.append(m).append(separator);
            if (s > 0 && s <= 9) {
                time.append("0" + s);
            } else {
                time.append(s);
            }
        }
        return time.toString();
    }

    public static Date parseDateForChina(String date) {
        try {
            return CHINA_YEAR.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将日期转换成 yyy-MM-dd HH:mm:ss 字符串形式
     *
     * @return
     */
    public synchronized static String getSecondDate(Date date) {
        return SECOND.format(date);
    }

    /**
     * 将日期转换成 yyy-MM-dd字符串形式
     *
     * @return
     */
    public synchronized static String getDayDate(Date date) {
        return DAY.format(date);
    }

    /**
     * 将字符串转换成日期对象
     *
     * @param dateTime
     * @return
     * @throws Exception
     */
    public static Date toDate(String dateTime) {
        try {
            return SECOND.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateStrWithMinute(Date date) {
        return FORMAT.format(date);
    }

    public static Date toDay(String dateTime) {
        try {
            return DAY.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将日期转成字符串
     *
     * @return
     */
    public static String toLocaleString() {
        DateFormat formatter = DateFormat.getDateTimeInstance();
        return formatter.format(getDate());
    }

    public static String getChinaYearDate(Date date) {
        return CHINA_YEAR.format(date);
    }

    public static String getYearStr(Date date) {
        return YEAR.format(date);
    }

    public static String getDateCode() {
        return DATE_CODE.format(new Date());
    }

    //获取当天的开始时间
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获取当天的结束时间
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 获取昨天的开始时间
     * @return
     */
    public static Date getBeginDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取昨天的结束时间
     * @return
     */
    public static Date getEndDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取明天的开始时间
     * @return
     */
    public static Date getBeginDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    /**
     * 获取明天的结束时间
     * @return
     */
    public static Date getEndDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取本周的开始时间
     * @return
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            dayOfWeek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayOfWeek);
        return getDayStartTime(cal.getTime());
    }

    /**
     * 获取本周的结束时间
     * @return
     */
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    //获取上周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }

    //获取上周的结束时间
    public static Date getEndDayOfLastWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    //获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    //获取上月的开始时间
    public static Date getBeginDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取上月的结束时间
    public static Date getEndDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 2, day);
        return getDayEndTime(calendar.getTime());
    }

    //获取本年的开始时间
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }

    //获取本年的结束时间
    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    //获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    //两个日期相减得到的天数
    public static int getDiffDays(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }
        long diff = (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24);
        int days = new Long(diff).intValue();
        return days;
    }

    /**
     * 获取间隔时间，精确到秒
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String getIntervalTime(Date startDate, Date endDate) {
        StringBuilder builder = new StringBuilder();
        if (startDate == null) {
            throw new NullPointerException("startDate can not be null");
        }
        if (endDate == null) {
            throw new NullPointerException("endDate can not be null");
        }

        long time = endDate.getTime() - startDate.getTime();
        long dayTime = 1000 * 60 * 60 * 24; //一天的毫秒数
        long remainder = time % dayTime;

        if (time / dayTime > 0) {
            String day = time / dayTime + "天";
            builder.append(day);
        }

        long hour = remainder / 1000 / 60 / 60;
        if (hour > 0) {
            builder.append(hour + "时");
        }

        long minute = remainder / 1000 / 60 % 60;
        if (minute > 0) {
            builder.append(minute + "分钟前");
        }
//        long second = minute % 60;
//        if (second > 0) {
//            builder.append(second + "秒");
//        }
        return builder.toString();
    }

    public static String getIntervalTime(long time) {
        long dayTime = 1000 * 60 * 60 * 24; //一天的毫秒数
        long remainder = time % dayTime;
        StringBuilder builder = new StringBuilder();
        String day = null;
        if (time / dayTime > 0) {
            day = time / dayTime + "天";
            builder.append(day);
        }
        int dayToHour = 0;
        if (ObjectUtils.isNotEmpty(day)) {
            dayToHour = Integer.parseInt(day) * 24;
        }


        long hour = remainder / 1000 / 60 / 60;
        if (hour > 0) {
            builder.append(dayToHour + hour + "时");
        }

        long minute = remainder / 1000 / 60 % 60;
        if (minute > 0) {
            builder.append(minute + "分钟");
        } else {
            builder.append(time / 1000 + "秒");
        }
        return builder.toString();
    }

    //两个日期相减得到的毫秒数
    public static long dateDiff(Date beginDate, Date endDate) {
        long startTime = beginDate.getTime();
        long endTime = endDate.getTime();
        return endTime - startTime;
    }

    //获取两个日期中的最大日期
    public static Date max(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return beginDate;
        }
        return endDate;
    }

    //获取两个日期中的最小日期
    public static Date min(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return endDate;
        }
        return beginDate;
    }

    //返回某月该季度的第一个月
    public static Date getFirstSeasonDate(Date date) {
        final int[] SEASON = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = SEASON[cal.get(Calendar.MONTH)];
        cal.set(Calendar.MONTH, sean * 3 - 3);
        return cal.getTime();
    }

    //返回某个日期下几天的日期
    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
        return cal.getTime();
    }

    //返回某个日期前几天的日期
    public static Date getFrontDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
        return cal.getTime();
    }

    //获取某年某月到某年某月按天的切片日期集合（间隔天数的集合）
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List getTimeList(int beginYear, int beginMonth, int endYear,
                                   int endMonth, int k) {
        List list = new ArrayList();
        if (beginYear == endYear) {
            for (int j = beginMonth; j <= endMonth; j++) {
                list.add(getTimeList(beginYear, j, k));
            }
        } else {
            for (int j = beginMonth; j < 12; j++) {
                list.add(getTimeList(beginYear, j, k));
            }
            for (int i = beginYear + 1; i < endYear; i++) {
                for (int j = 0; j < 12; j++) {
                    list.add(getTimeList(i, j, k));
                }
            }
            for (int j = 0; j <= endMonth; j++) {
                list.add(getTimeList(endYear, j, k));
            }
        }
        return list;
    }

    /**
     * 获取指定小时之前的时间
     *
     * @param hour
     * @return
     */
    public static String getBeforeByHour(int hour) {
        Calendar calendar = Calendar.getInstance();
        /* HOUR_OF_DAY 指示一天中的小时 */
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        return DateUtils.getSecondDate(calendar.getTime());
    }

    /**
     * 获取指定时间天数之前的日期
     *
     * @param dateStr   (指定时间)
     * @param dayNumber (指定天数)
     * @return
     * @throws ParseException
     */
    public static Date getDateByDayNumber(String dateStr, int dayNumber) {
        Date inputDate;
        try {
            inputDate = SECOND.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(inputDate);
            int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
            cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear - dayNumber);
            return cal.getTime();
        } catch (ParseException e) {
            logger.error("获取时间异常", e);
        }
        return null;
    }

    //获取某年某月按天切片日期集合（某个月间隔多少天的日期集合）
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List getTimeList(int beginYear, int beginMonth, int k) {
        List list = new ArrayList();
        Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
        int max = begincal.getActualMaximum(Calendar.DATE);
        for (int i = 1; i < max; i = i + k) {
            list.add(begincal.getTime());
            begincal.add(Calendar.DATE, k);
        }
        begincal = new GregorianCalendar(beginYear, beginMonth, max);
        list.add(begincal.getTime());
        return list;
    }

    public static List getPart(String orderBy, int day) {
        if (orderBy.equals("hour")) {
            return getSectionByOneHour(new Date());
        } else if (orderBy.equals("day")) {
            return getSectionByOneDay(day);
        }
        return null;
    }


    /**
     * 获取当天凌晨据当前时间每一个小时的时间段
     *
     * @return
     */
    public static List<String> getSectionByOneHour(Date endDate) {
        Date startDate = DateUtils.getDayBegin();
        int times = (int) DateUtils.dateDiff(startDate, endDate);
        int hourCount = times / 1000 / 60 / 60;
        //开始时间为0秒，第一次循环为900秒
        int aecondValueZero = 0;
        //开始时间为-900秒，第一次循环为0秒
        int aecondValueNineHundred = -3600;

        //循环时间为00：00 ~ endDate
        List<String> list = new ArrayList();
        for (int i = 0; i <= hourCount; i++) {
            //每次循环之前加1小时
            aecondValueZero = aecondValueZero + 3600;
            //每次循环之前加1小时
            aecondValueNineHundred = aecondValueNineHundred + 3600;
            //初始时间都为00：00
            String timeInitialFront = "00:00";
            String timeInitialAfter = "00:00";
            if (aecondValueNineHundred != 0) {
                //换算成小时
                int hour = (Integer.valueOf(aecondValueNineHundred) / 3600);
                //换算成分钟
                int minute = (Integer.valueOf(aecondValueNineHundred) % 3600 / 60);
                //把生成的时间转化成字符串
                String hourStr = String.valueOf(hour);
                String minuteStr = String.valueOf(minute);
                //如果小时的长度等于1个，在其前面加个0
                if (hourStr.length() == 1) {
                    hourStr = "0" + hourStr;
                }
                //如果小时的到达24点让其初始化为00
                if (hourStr.equals("24")) {
                    hourStr = "00";
                }
                //如果分钟的长度等于1个，在其前面加个0
                if (minuteStr.length() == 1) {
                    minuteStr = "0" + minuteStr;
                }
                //拼接小时和分钟
                timeInitialFront = hourStr + ":" + minute; // + ":" + Asecond;
            }
            //将时间和所有字段放入map中初始为0
            list.add(timeInitialFront);
        }
        return list;
    }

    /**
     * 按每天分成时间段
     *
     * @return
     */
    public static List<String> getSectionByOneDay(int day) {
        String nowTime = DateUtils.getSecondDate(new Date());//当前时间开始前
        Date startDate = DateUtils.getDateByDayNumber(nowTime, day);
        String startTime = DateUtils.getSecondDate(startDate);
    //    int dayNumber = getDiffDays(startDate, new Date());
        String afterDay = null;
        List list = new ArrayList<String>();
        for (int i = 0; i < day; i++) {
            afterDay = getDayAfter(startTime);
            list.add(afterDay);
            startTime = afterDay;
        }
        return list;
    }

    public enum DateType {
        BEFORE, AFTER;
    }

    /**
     * 获取某个时间后一天日期
     *
     * @param dateStr
     * @return
     */
    public static String getDayBeforeOrAfter(String dateStr, DateFormat dateFormat, int dayNumber, DateType dateType) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = dateFormat.parse(dateStr);
            calendar.setTime(date);
            int day = calendar.get(Calendar.DATE);
            if (dateType == DateType.AFTER) {
                calendar.set(Calendar.DATE, day + dayNumber);
            } else {
                calendar.set(Calendar.DATE, day - dayNumber);
            }

            return dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDayBefore(String dateStr, int dayNumber) {
        return getDayBeforeOrAfter(dateStr, DAY, dayNumber, DateType.BEFORE);
    }

    public static String getDayAfter(String dateStr, int dayNumber) {
        return getDayBeforeOrAfter(dateStr, DAY, dayNumber, DateType.AFTER);
    }

    /**
     * 获取某个时间后一天日期
     *
     * @param dateStr
     * @return
     */
    public static String getDayAfter(String dateStr) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = DAY.parse(dateStr);
            calendar.setTime(date);
            int day = calendar.get(Calendar.DATE);
            calendar.set(Calendar.DATE, day + 1);
            return DAY.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStrByDate(Date date) {
        if (date == null) {
            return "很久前";
        }
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return "now";
        } else if (delta < 60L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        } else if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        } else if (delta < 48L * ONE_HOUR) {
            return "昨天";
        } else if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        } else if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    /**
     * 获取时间间隔天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Long getDaySub(Date beginDate, Date endDate) {
        try {
            String beginDateStr = getSecondDate(beginDate);
            String endDateStr = getSecondDate(beginDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(SECOND.parse(beginDateStr));
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            calendar.setTime(SECOND.parse(endDateStr));
            int secondYear = calendar.get(Calendar.YEAR);
            int secondMonth = calendar.get(Calendar.MONTH);
            int differenceYear = Math.abs(secondYear - year);
            int differenceMonth = differenceYear * 12 + Math.abs(secondMonth - month);
            long beginTime = SECOND.parse(beginDateStr).getTime();
            long endTime = SECOND.parse(endDateStr).getTime();
            int differenceDay = (int) (Math.abs(endTime - beginTime) / (1000 * 60 * 60 * 24));
            return (long) differenceDay;
//            System.out.println("相差年: " + y);
//            System.out.println("相差月: " + m);
//            System.out.println("相差天: " + d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 比较两个字符串日期的大小
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean compareTo(String beginTime, String endTime) {
        int result = endTime.compareTo(beginTime);
        return result > 0 ? true : false;
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

    public static String getDateStr(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);// 从0开始
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + (month + 1) + "-" + day + " 00:00:00";
    }

    public static String getDateStr(Calendar calendar, int forward) {
        // 提前forward天
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);// 从0开始
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        day -= forward;
        calendar.set(year, month, day);
        return getDateStr(calendar);
    }

    private static final String DEFAULT_START_TIME = "00:00:00"; // 默认开始时间取当天凌晨
    private static final String DEFAULT_END_TIME = "23:00:00"; // 默认开始时间取当天23:59:59
    private static final int MINUTE = 60;// 默认60分钟 即一小时

    /**
     * 将一天的时间拆分成24小时时间段
     * @return
     */
    public static List<String> getOneHourByDate() {
        return getIntervalTimeList(DEFAULT_START_TIME, DEFAULT_END_TIME, 60);
    }

    public static Map getOneHourByDateToMap() {
        List<String> timeQuantumList = getOneHourByDate();
        Map resultMap = new HashMap<>();
        timeQuantumList.forEach(item -> {
            resultMap.put(item, item);
        });
        return resultMap;
    }

    /**
     * 按今日开始到现在的时间划分时间段
     * @return
     */
    public static List<String> getOneHourByEndBowTime() {
        String hourTime = getHourTime(new Date());
        String timeArray[] = hourTime.split(":");
        String newHourTime = timeArray[0] + ":00";
        return getIntervalTimeList(DEFAULT_START_TIME, newHourTime, MINUTE);
    }

    public static String getHourTime(Date date) {
        return HOUR.format(date);
    }

    public static List<String> getAllMonth(String startTime, String endTime) {
        List<String> date = new ArrayList<String>();
        try {
            Date startDate = MONTH.parse(startTime);//定义起始日期
            Date endDate = MONTH.parse(endTime);//定义结束日期
            Calendar calendar = Calendar.getInstance();//定义日期实例
            calendar.setTime(startDate);//设置日期起始时间
            while (calendar.getTime().before(endDate)) {//判断是否到结束日期
                String str = MONTH.format(calendar.getTime());
                date.add(str);
                calendar.add(Calendar.MONTH, 1);//进行当前日期月份加1
            }
            date.add(MONTH.format(new Date()));;//输出日期结果
        } catch (Exception e){
            System.out.println("异常"+e.getMessage());
        }
        return date;
    }

    /**
     * 按指定时间段拆分时间
     * @param start
     * @param end
     * @param interval (以分为单位)
     * @return
     */
    public static List<String> getIntervalTimeList(String start, String end, int interval) {
        Date startDate = convertString2Date("HH:mm", start);
        Date endDate = convertString2Date("HH:mm", end);
        List<String> list = new ArrayList<>();
        while (startDate.getTime() <= endDate.getTime()) {
            list.add(convertDate2String("HH:mm",startDate));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.MINUTE,interval);
            if (calendar.getTime().getTime() > endDate.getTime()) {
                if (!startDate.equals(endDate)) {
                    list.add(convertDate2String("HH:mm",endDate));
                }
                startDate = calendar.getTime();
            } else {
                startDate = calendar.getTime();
            }
        }
        return list;
    }

    public static String convertDate2String(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static Date convertString2Date(String format, String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间开始，n时间
     * @param year
     * @return
     */
    public static Date getAfterYearDate(int year) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//设置起时间
        cal.add(Calendar.YEAR, year);//增加一年
        return cal.getTime();
    }
}
