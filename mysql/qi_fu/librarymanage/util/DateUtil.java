package com.mysql.qi_fu.librarymanage.util;

import android.annotation.SuppressLint;

import com.mysql.qi_fu.librarymanage.info.CustomDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Meiko on 2017/1/17.
 */
public class DateUtil {
    private static SimpleDateFormat sf;

    /*获取系统时间 格式为："yyyy/MM/dd "*/
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm E");
        return sf.format(d);
    }

    public static int getCurrentYear() {
        String currentDate = DateUtil.getCurrentDate();
        String[] split = currentDate.split("-");
        int year = Integer.parseInt(split[0]);
        return year;
    }

    public static Date string2Date(String ymd) {
        Date d = new Date();
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(ymd);
        } catch (Exception e) {
            System.out.println(e);
        }
        return d;
    }

    public static int getCurrentMonth() {
        String currentDate = DateUtil.getCurrentDate();
        String[] split = currentDate.split("-");
        int month = Integer.parseInt(split[1]);
        return month;
    }

    public static int getCurrentDay() {
        String currentDate = DateUtil.getCurrentDate();
        currentDate = currentDate.substring(0, 10);
        String[] split = currentDate.split("-");
        int day = Integer.parseInt(split[2]);
        return day;
    }

    /*时间戳转换成日期*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sf.format(d);
    }

    /*时间戳转换成日期*/
    public static String getDateToString(long time, int type) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }


    /*将字符串转为时间戳*/
    public static long getStringToTimeLine(String time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date.getTime();
    }

    //得到系统的时间戳
    public static long getCurrenTimeline() {
        return getStringToTimeLine(getCurrentDate());
    }


    /**
     * 计算获得时间差
     *
     * @param newtime：本次获取的时间戳
     * @param oldtime：         上次获取的时间
     * @return
     */
    public static String getTimeDistance(long newtime, String oldtime) {

        String s = getDateToString(newtime);
        // 计算的时间差
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String msg = oldtime.trim();
        String timedistance = null;
        try {
            Date d1 = df.parse(s);
            Date d2 = df.parse(msg);
            // 这样得到的差值是微秒级别
            long diff = d1.getTime() - d2.getTime() > 0 ? d1.getTime() - d2.getTime() : d2.getTime() - d1.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24))
                    / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                    * (1000 * 60 * 60))
                    / (1000 * 60);
            if (days == 0) {
                if (hours == 0) {
                    timedistance = "" + minutes + "分";
                    if (minutes == 0) {
                        timedistance = "1分";
                    }
                } else {
                    if (minutes == 0) {
                        timedistance = "" + hours + "小时";
                    } else {
                        timedistance = "" + hours + "小时" + minutes + "分";
                    }
                }
            } else {
                if (hours == 0) {
                    if (minutes == 0) {
                        timedistance = "" + days + "天";
                    }else {
                        timedistance = "" + days + "天" + minutes + "分";
                    }
                }else {
                    if (minutes == 0) {
                        timedistance = "" + days + "天" + hours + "小时";
                    } else {
                        timedistance = "" + days + "天" + hours + "小时" + minutes + "分";
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timedistance;
    }

    public static String[] weekName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    //获得第一天的星期
    public static int getFirstDayWeek(int year, int month) {
        int c = year / 100;
        int y = year - c * 100;
        if (month == 1) {
            month = 13;
        }
        if (month == 2) {
            month = 14;
        }
        int w = y + (int) (y / 4) + (int) (c / 4) - 2 * c + (26 * (int) ((month + 1) / 10)) - 1;
        return w;
    }

    //得到月的天数
    public static int getMonthDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int mothIndex = calendar.get(Calendar.DAY_OF_MONTH);
        int yearIndex = calendar.get(Calendar.DAY_OF_YEAR);
        int days = getMonthDays(yearIndex, mothIndex);
        return days;
    }

    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;

        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29; // 闰年2月29天
        }

        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }

        return days;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }


    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static CustomDate getNextSunday() {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 7 - getWeekDay() + 1);
        CustomDate date = new CustomDate(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        return date;
    }

    public static int[] getWeekSunday(int year, int month, int day, int pervious) {
        int[] time = new int[3];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.DAY_OF_MONTH, pervious);
        time[0] = c.get(Calendar.YEAR);
        time[1] = c.get(Calendar.MONTH) + 1;
        time[2] = c.get(Calendar.DAY_OF_MONTH);
        return time;

    }

    public static int getWeekDayFromDate(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return week_index;
    }

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(int year, int month) {
        String dateString = year + "-" + (month > 9 ? month : ("0" + month))
                + "-01";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    /*
    * 判断是否是今天
    * */
    public static boolean isToday(CustomDate date) {
        return (date.year == DateUtil.getYear() &&
                date.month == DateUtil.getMonth()
                && date.day == DateUtil.getCurrentMonthDay());
    }

    public static boolean isCurrentMonth(CustomDate date) {
        return (date.year == DateUtil.getYear() &&
                date.month == DateUtil.getMonth());
    }

    /**
     * 返回当天日期
     * String 格式： "yyyy-MM-dd" "yyyy-MM-dd HH:mm:ss:SS"等
     */
    public static String getNowData(Date data, String dateformat) {
//	Date data = new Date();
        SimpleDateFormat format = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        String now = format.format(data);
        return now;
    }

    /**
     * 传入字符串
     *
     * @param point1 起始日期
     * @param point2 终止日期
     * @return
     */
    public static String getDays(String point1, String point2, boolean flag) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(point1);
            Date mydate = myFormatter.parse(point2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
            if (flag)
                day = Math.abs(day);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    public static Calendar getDate(int day, int month, int year) {
        Calendar calDate = Calendar.getInstance();
        calDate.clear();
        calDate.set(Calendar.YEAR, year);
        calDate.set(Calendar.MONTH, month);
        calDate.set(Calendar.DAY_OF_MONTH, day);
        return calDate;
    }

    /**
     * 传入Date对象计算相隔天数
     */
    public static int getDays(Calendar fromCalendar, Calendar toCalendar) {
        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    public Calendar getDate2(Date date, int day, int month, int year) {
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);
        calDate.set(Calendar.YEAR, year);
        calDate.set(Calendar.MONTH, month);
        calDate.set(Calendar.DAY_OF_MONTH, day);
        return calDate;
    }

    /*
    * 将时间搠转变为星期
    * */
    public static String getTimeLine2Weekday(Long timeLine) {
        String time = DateUtil.getDateToString(timeLine);
        String[] split = time.split("-");
        int i = Integer.parseInt(split[0], 10);
        int m = Integer.parseInt(split[1], 10);
        int n = Integer.parseInt(split[2].substring(0, 2), 10);
        String week = DateToWeek(getDate(n, m, i).getTime());
        return week;
    }

    /**
     * 日期变量转成对应的星期字符串
     *
     * @param date
     * @return
     */
    public static String DateToWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > 7) {
            return null;
        }

        return weekName[dayIndex - 1];
    }

    /**
     * 日期变量转成对应的星期字符串
     *
     * @param date
     * @return
     */
    public static String DateToString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        int mothIndex = calendar.get(Calendar.DAY_OF_MONTH);
        int yearIndex = calendar.get(Calendar.DAY_OF_YEAR);
        if (dayIndex < 1 || dayIndex > 7) {
            return null;
        }
        if (mothIndex < 12 || mothIndex > 0) {

        }

        return yearIndex + "" + mothIndex + weekName[dayIndex - 1];
    }

    public static int dayForWeek(String pTime) throws Throwable {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date tmpDate = format.parse(pTime);

        Calendar cal = new GregorianCalendar();

        cal.set(tmpDate.getYear(), tmpDate.getMonth(), tmpDate.getDay());

        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 将日期转变为星期
     *
     * @param stringTime 包含年月日的String
     * @return
     */
    public static String getTime2Week(String stringTime) {
        String time = String.format(stringTime, "yyyy-MM-dd");
        String[] split = time.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        Calendar calendar = Calendar.getInstance();//获得一个日历
        calendar.set(year, month - 1, day);//设置当前时间,月份是从0月开始计算
        int number = calendar.get(Calendar.DAY_OF_WEEK);//星期表示1-7，是从星期日开始，
        return month + "月" + day + "日" + weekName[number - 1];
    }

    /**
     * 得到下一天得时间
     *
     * @param stringTime
     * @return
     */
    public static String getNextdata(String stringTime) {
        String time = String.format(stringTime, "yyyy-MM-dd");
        String[] split = time.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        if (day > getMonthDays(year, month) - 1) {
            if (month == 12) {
                year = year + 1;
                month = 1;
                day = 1;
            } else {
                month = month + 1;
                day = 1;
            }
        } else {
            day = day + 1;
        }
        return year + "-" + month + "-" + day;
    }

    /**
     * 得到上一天得时间
     *
     * @param stringTime
     * @return
     */
    public static String getBefordata(String stringTime) {
        String time = String.format(stringTime, "yyyy-MM-dd");
        String[] split = time.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        if (day == 1) {
            if (month == 1) {
                year = year - 1;
                month = 12;
                day = 30;
            } else {
                month = month - 1;
                day = getMonthDays(year, month);
            }
        } else {
            day = day - 1;
        }
        return year + "-" + month + "-" + day;
    }
}