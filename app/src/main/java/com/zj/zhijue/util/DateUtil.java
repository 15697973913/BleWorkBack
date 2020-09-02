package com.zj.zhijue.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.android.common.baselibrary.log.MLog;
import com.litesuits.android.log.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

/**
 * 日期工具
 *
 * @author xp
 * @describe 日期工具.
 * @date 2017/3/28.
 */

@SuppressLint("SimpleDateFormat")
public class DateUtil {

    /**
     * 获取指定时间
     *
     * @param type -2 HH:mm , -1 yyyy-MM, 0 HH:mm:ss, 1 yyyy-MM-dd, 2 yyyy-MM-dd HH:mm, 3 yyyy-MM-dd HH:mm:ss,4 MM-dd HH:mm,5 dd HH:mm:ss,
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static String getAssignDate(long time, int type) {
        //若是秒的时间戳则乘1000转为毫秒
        if ((time + "").length() == 10) {
            time = time * 1000;
        }

        Date currentTime = new Date(time);
        String format;
        switch (type) {
            case -2:
                format = "HH:mm";
                break;
            case -1:
                format = "yyyy-MM";
                break;
            case 0:
                format = "HH:mm:ss";
                break;
            case 1:
                format = "yyyy-MM-dd";
                break;
            case 2:
                format = "yyyy-MM-dd HH:mm";
                break;
            case 3:
                format = "yyyy-MM-dd HH:mm:ss";
                break;
            case 4:
                format = "MM-dd HH:mm";
                break;
            case 5:
                format = "dd HH:mm:ss";
            default:
                format = "yyyy-MM-dd HH:mm:ss";
                break;
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(currentTime);
    }

    /**
     * 返回指定的时间类型
     * type : 1 年2000
     * type : 2 月8
     * type : 3 日
     * type : 4 时
     * type : 5 分
     * type : 6 秒
     *
     * @return
     */
    public static int getAssignTimeType(int type) {
        String time = getAssignDate(new Date().getTime(), 3);
        if (time.isEmpty() || time.length() < 18) {
            return 0;
        }

//        yyyy-MM-dd HH:mm:ss

        switch (type) {
            case 1:
                time = time.substring(0, 4);
                break;
            case 2:
                time = time.substring(5, 7);
                break;
            case 3:
                time = time.substring(8, 10);
                break;
            case 4:
                time = time.substring(11, 13);
                break;
            case 5:
                time = time.substring(14, 16);
                break;
            case 6:
                time = time.substring(17, 19);
                break;
            default:
                break;
        }

        return CommonUtils.getInt(time);
    }

    /**
     * 获取现在时间
     *
     * @param type 1 yyyy-MM-dd, 2 yyyy-MM-dd HH:mm, 3 yyyy-MM-dd HH:mm:ss
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate(int type) {
        Date currentTime = new Date();
        String format;
        switch (type) {
            case 0:
                format = "HH:mm:ss";
                break;
            case 1:
                format = "yyyy-MM-dd";
                break;
            case 2:
                format = "yyyy-MM-dd HH:mm";
                break;
            case 3:
            default:
                format = "yyyy-MM-dd HH:mm:ss";
                break;
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        return formatter.parse(dateString, pos);
    }

    /**
     * 获取昨天时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getToNdayDate() {
        Date currentTime = new Date();
        currentTime.setTime(currentTime.getTime() - 24 * 60 * 60 * 1000L);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(currentTime);
    }

    /**
     * 得到现在小时
     */
    public static String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 得到现在分钟
     */
    public static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat yyyyMMddhhmmss
     * @return 时间
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        return formatter.format(currentTime);
    }

    /**
     * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     */
    public static String getTwoHour(String st1, String st2) {
        String[] kk;
        String[] jj;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static String getPreTime(String sj1, String jj) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate1 = "";
        try {
            Date date1 = format.parse(sj1);
            long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception ignored) {
        }
        return mydate1;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate;
            Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 判断是否润年
     */
    public static boolean isLeapYear(String ddate) {

        /**
         * 1.被400整除是闰年，否则：
         * 2.不能被4整除则不是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = strToDate(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        return (year % 400) == 0 || (year % 4) == 0 && (year % 100) != 0;
    }

    /**
     * 获取一个月的最后一天
     *
     * @param dat yyyy-MM-dd
     * @return str
     */
    public static String getEndDateOfMonth(String dat) {
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(dat)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }

    /**
     * 判断二个时间是否在同一个周
     *
     * @param date1 date
     * @param date2 date
     * @return boolean
     */
    @SuppressWarnings("WrongConstant")
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && cal2.get(Calendar.MONTH) == 11) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     * @return year + week
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    /**
     * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
     *
     * @param sdate date
     * @param num   0~7
     * @return 几号
     */
    public static String getWeek(String sdate, String num) {
        // 再转换为时间
        Date dd = DateUtil.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num.equals("1")) // 返回星期一所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        else if (num.equals("2")) // 返回星期二所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        else if (num.equals("3")) // 返回星期三所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        else if (num.equals("4")) // 返回星期四所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        else if (num.equals("5")) // 返回星期五所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        else if (num.equals("6")) // 返回星期六所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        else if (num.equals("0")) // 返回星期日所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate yy-mm-dd
     * @return 星期几
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 根据一个日期，返回是0，1，2
     *
     * @param sdate yy-mm-dd
     * @return (星期天 0 ， 星期一 1)
     */
    public static int getWeekInt(String sdate) {
        // 再转换为时间
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week = c.get(Calendar.DAY_OF_WEEK) - 1;
        MLog.e(" ========= getWeekInt  " + sdate + "----" + week);
        return week;
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate yy-mm-dd
     * @return 星期几
     */
    public static String getWeekStr(String sdate) {
        String str = "";
        str = getWeek(sdate);
        if ("1".equals(str)) {
            str = "星期日";
        } else if ("2".equals(str)) {
            str = "星期一";
        } else if ("3".equals(str)) {
            str = "星期二";
        } else if ("4".equals(str)) {
            str = "星期三";
        } else if ("5".equals(str)) {
            str = "星期四";
        } else if ("6".equals(str)) {
            str = "星期五";
        } else if ("7".equals(str)) {
            str = "星期六";
        }
        return str;
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1 date1
     * @param date2 date2
     * @return 天数
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception ignored) {
        }
        assert date != null;
        return (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
     * 此函数返回该日历第一行星期日所在的日期
     *
     * @param sdate date
     * @return 该日历第一行星期日所在的日期
     */
    public static String getNowMonth(String sdate) {
        // 取该时间所在月的一号
        sdate = sdate.substring(0, 8) + "01";

        // 得到这个月的1号是星期几
        Date date = DateUtil.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int u = c.get(Calendar.DAY_OF_WEEK);
        return DateUtil.getNextDay(sdate, (1 - u) + "");
    }

    /**
     * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
     *
     * @param k 表示是取几位随机数，可以自己定
     */

    public static String getNo(int k) {

        return getUserDate("yyyyMMddhhmmss") + getRandom(k);
    }

    /**
     * 返回一个随机数
     *
     * @param i 位数
     * @return 随机数
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        // int suiJiShu = jjj.nextInt(9);
        if (i == 0)
            return "";
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }

    /**
     * 日期格式转换yyyy-MM-dd'T'HH:mm:ss.SSSXXX  (yyyy-MM-dd'T'HH:mm:ss.SSSZ) TO  yyyy-MM-dd
     */
    public static String dealDateFormat(String oldDateStr) {

        try {
            //此格式只有  jdk 1.7才支持  yyyy-MM-dd'T'HH:mm:ss.SSSXXX
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
            Date date = df.parse(oldDateStr);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date date1 = df1.parse(date.toString());
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            return df2.format(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        2017-10-18T16:52:14.566+08:00
        return oldDateStr;
    }

    /**
     * 日期格式转换yyyy-MM-dd'T'HH:mm:ss.SSSXXX  (yyyy-MM-dd'T'HH:mm:ss.SSSZ) TO  yyyy-MM-dd HH:mm:ss
     */
    public static String dealDateFormatToYYYY_MM_DD_hh_mm_ss(String oldDateStr) {

        try {
            //此格式只有  jdk 1.7才支持  yyyy-MM-dd'T'HH:mm:ss.SSSXXX
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
            Date date = df.parse(oldDateStr);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date date1 = df1.parse(date.toString());
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df2.format(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        2017-10-18T16:52:14.566+08:00

        return oldDateStr;
    }

    //字符串转时间戳
    public static long getTime(String timeString) {
        long timeStamp = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d;
        try {
            d = sdf.parse(timeString);
            timeStamp = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    /**
     * 获取时间统计
     *
     * @param timer 时间统计
     * @return 如： 01:10:10
     */
    public static String getTimerHH_MM_SS(int timer, String hourSuffix, String minuteSuffix, String secondSuffix) {

        String text;
        int h = timer / (60 * 60);
        int m = (timer - h * 60 * 60) / 60;
        int s = timer % 60;

        hourSuffix = isEmpty(hourSuffix) ? ":" : hourSuffix;
        minuteSuffix = isEmpty(minuteSuffix) ? ":" : minuteSuffix;
        secondSuffix = isEmpty(secondSuffix) ? "" : secondSuffix;

        text = h >= 10 ? h + hourSuffix : h + hourSuffix;
        text += m >= 10 ? m + minuteSuffix : m + minuteSuffix;
        text += s >= 10 ? s + secondSuffix : s + secondSuffix;

        return text;
    }

    /**
     * 获取时间统计
     *
     * @param timer 时间统计
     * @return 如： 01:10:10
     */
    public static String getTimerHH_MM_SS(int timer, String daySuffix, String hourSuffix, String minuteSuffix, String secondSuffix) {

        String text;
        int h = timer / (60 * 60);
        int day = h / 24;
        int m = (timer - h * 60 * 60) / 60;
        int s = timer % 60;

        hourSuffix = isEmpty(hourSuffix) ? ":" : hourSuffix;
        minuteSuffix = isEmpty(minuteSuffix) ? ":" : minuteSuffix;
        secondSuffix = isEmpty(secondSuffix) ? "" : secondSuffix;

        text = day + daySuffix;
        text += h%24 + hourSuffix;
        text += m + minuteSuffix;
        text += s + secondSuffix;

        return text;
    }

    public static boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }

    /**
     * 获取消息的格式时间
     *
     * @return 当天 10:30，其他 04-22 10:30
     */
    public static String getMessageFormatTime(long time) {
        String timeStr;
        if (TextUtils.equals(getAssignDate(time, 1), getAssignDate(new Date().getTime(), 1))) {
            //当天
            timeStr = getAssignDate(time, -2);
        } else {
            timeStr = getAssignDate(time, 4);
        }

        return timeStr;
    }

    /**
     * 时间转秒
     *
     * @param time 时分秒格式00:00:00
     * @return
     */
    public static long getSecond(String time) {
        long s = 0;
//        if(time.length()==8){ //时分秒格式00:00:00
        if (getCount(time, ":") > 1) {
            int index1 = time.indexOf(":");
            int index2 = time.indexOf(":", index1 + 1);
            s = Integer.parseInt(time.substring(0, index1)) * 3600;//小时
            s += Integer.parseInt(time.substring(index1 + 1, index2)) * 60;//分钟
            s += Integer.parseInt(time.substring(index2 + 1));//秒
        } else if (time.length() > 2) {
//        if(time.length()==5){//分秒格式00:00
            s = Integer.parseInt(time.substring(time.length() - 2)); //秒  后两位肯定是秒
            s += Integer.parseInt(time.substring(0, 2)) * 60;    //分钟
        }
        Log.e("TAG", "获取秒数：" + s);
        return s;
    }

    /**
     * 返回日时分秒
     *
     * @param second
     * @return
     */
    public static String secondToTime(long second) {
        Log.e("TAG", "转换秒数：" + second);
        String str;
//        long days = second / 86400;//转换天数
//        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数
        if (hours > 0) {
            str = hours + "小时" + minutes + "分" + second + "秒";
        } else {
            if (minutes > 0) {
                str = minutes + "分" + second + "秒";
            } else {
                str = second + "秒";
            }
        }
        return str;
    }

    /**
     * 返回日时分秒
     *
     * @param second
     * @return
     */
    public static String secondToTime2(long second) {
        Log.e("TAG", "转换秒数：" + second);
        String str;
        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数
        str = days + "天\n" + hours + "小时" + minutes + "分" + second + "秒";
        return str;
    }

    /**
     * 秒返回时
     *
     * @param second
     * @return
     */
    public static String secondToHH(long second) {
        Log.e("TAG", "转换秒数：" + second);
        String str;
        long hours = second / 3600;//转换小时数
//        second = second % 3600;//剩余秒数
//        long minutes = second / 60;//转换分钟
//        second = second % 60;//剩余秒数
        if (hours > 0) {
            str = hours + " 小时";
        } else {
            str = "0 小时";
        }
        return str;
    }

    /*判断字符ab在字符str中出现的次数*/
    private static int getCount(String str, String compareStr) {
        // 需要对比的源字符串
//        String str = "34abcedfababfffffffabtabrt4444";
        // 需要对比的字符串
//        String compareStr = "ab";
        //字符串查找初始从0开始查找
        int indexStart = 0;
        //获取查找字符串的长度，这里有个规律：第二次查找出字符串的起始位置等于 第一次ab字符串出现的位置+ab的长度
        int compareStrLength = compareStr.length();
        int count = 0;
        while (true) {
            int tm = str.indexOf(compareStr, indexStart);
            if (tm >= 0) {
                count++;
                //  没查找一次就从新计算下次开始查找的位置
                indexStart = tm + compareStrLength;
            } else {
                //直到没有匹配结果为止
                break;
            }
        }
        return count;
    }

    public static int getUTC() {
        int utc = 0;
        try {
            TimeZone tz = TimeZone.getDefault();
            String gmt = tz.getDisplayName(false, TimeZone.SHORT);
            gmt = gmt.replace("GMT", "");

            MLog.e(("TimeZone   " + tz.getDisplayName(false, TimeZone.SHORT)
                    + "  =====   " + gmt));

            List<String> utcList = new ArrayList<>();
            utcList.add("-12:00");
            utcList.add("-11:45");
            utcList.add("-11:30");
            utcList.add("-11:15");
            utcList.add("-11:00");
            utcList.add("-10:45");
            utcList.add("-10:30");
            utcList.add("-10:15");
            utcList.add("-10:00");
            utcList.add("-09:45");
            utcList.add("-09:30");
            utcList.add("-09:15");
            utcList.add("-09:00");
            utcList.add("-08:45");
            utcList.add("-08:30");
            utcList.add("-08:15");
            utcList.add("-08:00");
            utcList.add("-07:45");
            utcList.add("-07:30");
            utcList.add("-07:15");
            utcList.add("-07:00");
            utcList.add("-06:45");
            utcList.add("-06:30");
            utcList.add("-06:15");
            utcList.add("-06:00");
            utcList.add("-05:45");
            utcList.add("-05:30");
            utcList.add("-05:15");
            utcList.add("-05:00");
            utcList.add("-04:45");
            utcList.add("-04:30");
            utcList.add("-04:15");
            utcList.add("-04:00");
            utcList.add("-03:45");
            utcList.add("-03:30");
            utcList.add("-03:15");
            utcList.add("-03:00");
            utcList.add("-02:45");
            utcList.add("-02:30");
            utcList.add("-02:15");
            utcList.add("-02:00");
            utcList.add("-01:45");
            utcList.add("-01:30");
            utcList.add("-01:15");
            utcList.add("-01:00");
            utcList.add("-00:45");
            utcList.add("-00:30");
            utcList.add("-00:15");
            utcList.add("+00:00");
            utcList.add("+00:15");
            utcList.add("+00:45");
            utcList.add("+01:00");
            utcList.add("+01:15");
            utcList.add("+01:30");
            utcList.add("+01:45");
            utcList.add("+02:00");
            utcList.add("+02:15");
            utcList.add("+02:30");
            utcList.add("+02:45");
            utcList.add("+03:00");
            utcList.add("+03:15");
            utcList.add("+03:30");
            utcList.add("+03:45");
            utcList.add("+04:00");
            utcList.add("+04:15");
            utcList.add("+04:30");
            utcList.add("+04:45");
            utcList.add("+05:00");
            utcList.add("+05:15");
            utcList.add("+05:30");
            utcList.add("+05:45");
            utcList.add("+06:00");
            utcList.add("+06:15");
            utcList.add("+06:30");
            utcList.add("+06:45");
            utcList.add("+07:00");
            utcList.add("+07:15");
            utcList.add("+07:30");
            utcList.add("+07:45");
            utcList.add("+08:00");
            utcList.add("+08:15");
            utcList.add("+08:30");
            utcList.add("+08:45");
            utcList.add("+09:00");
            utcList.add("+09:15");
            utcList.add("+09:30");
            utcList.add("+09:45");
            utcList.add("+10:00");
            utcList.add("+10:15");
            utcList.add("+10:30");
            utcList.add("+10:45");
            utcList.add("+11:00");
            utcList.add("+11:15");
            utcList.add("+11:30");
            utcList.add("+11:45");
            utcList.add("+12:00");
            utcList.add("+12:15");
            utcList.add("+12:30");
            utcList.add("+12:45");
            utcList.add("+13:00");
            utcList.add("+13:15");
            utcList.add("+13:30");
            utcList.add("+13:45");
            utcList.add("+14:00");

            for (int i = -48; i < utcList.size() + -48; i++) {
                if (TextUtils.equals(utcList.get(i + 48), gmt)) {
                    utc = i + 1;
                    break;
                }
            }

            MLog.e("======= " + utc + "----" + gmt);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return utc;
    }


}
