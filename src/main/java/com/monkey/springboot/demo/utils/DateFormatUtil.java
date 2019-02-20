package com.monkey.springboot.demo.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/28.
 */
public class DateFormatUtil {
    /**
     * 字符串转换为java.util.Date<br>
     * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z 如 '2002-1-1 AD at 22:10:59 PSD'<br>
     * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br>
     * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br>
     * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br>
     * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br>
     *
     * @param time String 字符串<br>
     * @return Date 日期<br>
     */
    public static Date stringToDate(String time) {
        SimpleDateFormat formatter;
        int tempPos = time.indexOf("AD");
        time = time.trim();
        formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
        if (tempPos > -1) {
            time = time.substring(0, tempPos) +
                    "公元" + time.substring(tempPos + "AD".length());//china
            formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
        }
        tempPos = time.indexOf("-");
        if (tempPos > -1 && (time.indexOf(" ") < 0)) {
            formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
        } else if ((time.indexOf("/") > -1) && (time.indexOf(" ") > -1)) {
            formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        } else if ((time.indexOf("-") > -1) && (time.indexOf(" ") > -1)) {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if ((time.indexOf("/") > -1) && (time.indexOf("am") > -1) || (time.indexOf("pm") > -1)) {
            formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
        } else if ((time.indexOf("-") > -1) && (time.indexOf("am") > -1) || (time.indexOf("pm") > -1)) {
            formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
        }
        ParsePosition pos = new ParsePosition(0);
        Date ctime = formatter.parse(time, pos);

        return ctime;
    }


    /**
     * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss'(24小时制)<br>
     * 如Sat May 11 17:24:21 CST 2002 to '2002-05-11 17:24:21'<br>
     *
     * @param time Date 日期<br>
     * @return String   字符串<br>
     */


    public static String dateToString(Date time) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ctime = formatter.format(time);

        return ctime;
    }

    public static String dateToNumberString(Date time) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String ctime = formatter.format(time);

        return ctime;
    }

    /**
     * 格式化年月日
     *
     * @param time
     * @return
     */
    public static String dateToDayString(Date time) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("YYYY-MM-dd");
        String ctime = formatter.format(time);

        return ctime;
    }


    /**
     * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss a'(12小时制)<br>
     * 如Sat May 11 17:23:22 CST 2002 to '2002-05-11 05:23:22 下午'<br>
     *
     * @param time Date 日期<br>
     * @param x    int 任意整数如：1<br>
     * @return String 字符串<br>
     */
    public static String dateToString(Date time, int x) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
        String ctime = formatter.format(time);

        return ctime;
    }


    /**
     * 取系统当前时间:返回只值为如下形式
     * 2002-10-30 20:24:39
     *
     * @return String
     */
    public static String Now() {
        return dateToString(new Date());
    }

    /**
     * 取系统当前时间:返回只值为如下形式
     * 2002-10-30 08:28:56 下午
     *
     * @param hour 为任意整数
     * @return String
     */
    public static String Now(int hour) {
        return dateToString(new Date(), hour);
    }


    /**
     * 取系统当前时间:返回值为如下形式
     * 2002-10-30
     *
     * @return String
     */
    public static String getYYYY_MM_DD() {
        return dateToString(new Date()).substring(0, 10);

    }


    /**
     * 取系统给定时间:返回值为如下形式
     * 2002-10-30
     *
     * @return String
     */
    public static String getYYYY_MM_DD(String date) {
        return date.substring(0, 10);

    }

    public static String getMin() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("M");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    public static String getHour() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("H");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    public static String getDay() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("d");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    public static String getMonth() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("M");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    public static String getYear() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    public static String getWeek() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("E");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    /**
     * 格式化时间
     *
     * @param str
     * @param srcFormat
     * @param targetFormat
     * @return
     */
    public static final String getFormatTime(String str, String srcFormat, String targetFormat) {
        if (isNullStr(str)) {
            return "";
        }
        Date date = null;
        String retString = str;
        try {
            SimpleDateFormat srcFormatter = new SimpleDateFormat(srcFormat);
            SimpleDateFormat targetFormatter = new SimpleDateFormat(targetFormat);
            date = srcFormatter.parse(str);
            retString = targetFormatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retString;
    }

    /**
     * 空值检查
     *
     * @param str
     * @return
     */
    public static boolean isNullStr(String str) {
        boolean flag = false;
        if (str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str) || "undefined".equalsIgnoreCase(str)) {
            flag = true;
        }
        return flag;
    }

    public static String getWeekStartDate(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//          return cal.getTime().toString();
            return format.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param dateFormat 格式
     * @param startTime  起止时间
     * @param endTime    结束时间
     * @return int
     * @throws ParseException
     * @Title: getDayTimeDifference
     * @Description: TODO 计算天数差
     */
    public static int getDayTimeDifference(String dateFormat, String startTime, String endTime) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateFormat);
        Long from = simpleFormat.parse(startTime).getTime();
        Long to = simpleFormat.parse(endTime).getTime();
        int days = (int) ((to - from) / (1000 * 60 * 60 * 24));
        return days;
    }

    /**
     * @param dateFormat 格式
     * @param startTime  起止时间
     * @param endTime    结束时间
     * @return int
     * @throws ParseException
     * @Title: getHourTimeDifference
     * @Description: TODO 计算小时差
     */
    public static int getHourTimeDifference(String dateFormat, String startTime, String endTime) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateFormat);
        Long from = simpleFormat.parse(startTime).getTime();
        Long to = simpleFormat.parse(endTime).getTime();
        int hours = (int) ((to - from) / (1000 * 60 * 60));
        return hours;
    }

    /**
     * @param dateFormat 格式
     * @param startTime  起止时间
     * @param endTime    结束时间
     * @return int
     * @throws ParseException
     * @Title: getMinuteTimeDifference
     * @Description: TODO 计算分钟差
     */
    public static int getMinuteTimeDifference(String dateFormat, String startTime, String endTime) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateFormat);
        Long from = simpleFormat.parse(startTime).getTime();
        Long to = simpleFormat.parse(endTime).getTime();
        int minutes = (int) ((to - from) / (1000 * 60));
        return minutes;
    }

    /**
     *  获取多少天之前或者之后的时间日期
     * @param date
     * @param num
     * @return
     */
    public static Date dayCalculate(Date date,int num){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, num);
        Date d = c.getTime();
        return d;
    }

    /**
     *  获取多少月之前或者之后的时间日期
     * @param date
     * @param num
     * @return
     */
    public static Date monthCalculate(Date date,int num){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, num);
        Date d = c.getTime();
        return d;
    }

    /**
     *  获取多少年之前或者之后的时间日期
     * @param date
     * @param num
     * @return
     */
    public static Date yearCalculate(Date date,int num){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, num);
        Date d = c.getTime();
        return d;
    }
}
