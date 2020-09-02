package com.android.common.baselibrary.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static final SimpleDateFormat localformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat localformatterDay = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat hourMinuteSecondFormat = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat dotformater = new SimpleDateFormat("MM.dd");
    public static final SimpleDateFormat dotformaterAll = new SimpleDateFormat("yyyy.MM.dd");
    public static final SimpleDateFormat lineformater = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat lineformaterCn = new SimpleDateFormat("yyyy年MM月");
    public static final SimpleDateFormat tlocalformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //public static final SimpleDateFormat tlocalformatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");//2019-06-03T11:45:00Z
    public static final SimpleDateFormat tszlocalformatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");//2019-06-16T16:00:00.000+0000
    public static final String startTime = "2019-11-01 00:00:00";
    public static String serverTimeFormat2LocalFormat(String serverTimeFormate) {

        Date date = null;
        try {
            date = formatter.parse(serverTimeFormate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (null != date) {

            String sDate = localformatter.format(date);
            return sDate;
        } else {
            return serverTimeFormate;
        }
    }

    public static Long parseTimeStr(String localTimeStr) {

        try {
            Date date = localformatter.parse(localTimeStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0L;
    }

    /**
     * 解析日期，获取年月日星期
     */
    public static String[] parseDateToYearMonthDayWeek(Date date) {
        String[] yearMonthDayWeek = new String[4];
        //获取默认选中的日期的年月日星期的值，并赋值
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期

        String yearStr = calendar.get(Calendar.YEAR) + "";//获取年份
        int month = calendar.get(Calendar.MONTH) + 1;//获取月份
        String monthStr = month < 10 ? "0" + month : month + "";
        int day = calendar.get(Calendar.DATE);//获取日
        String dayStr = day < 10 ? "0" + day : day + "";
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String weekStr = "";
        /*星期日:Calendar.SUNDAY=1
         *星期一:Calendar.MONDAY=2
         *星期二:Calendar.TUESDAY=3
         *星期三:Calendar.WEDNESDAY=4
         *星期四:Calendar.THURSDAY=5
         *星期五:Calendar.FRIDAY=6
         *星期六:Calendar.SATURDAY=7 */
        switch (week) {
            case 1:
                weekStr = "周日";
                break;
            case 2:
                weekStr = "周一";
                break;
            case 3:
                weekStr = "周二";
                break;
            case 4:
                weekStr = "周三";
                break;
            case 5:
                weekStr = "周四";
                break;
            case 6:
                weekStr = "周五";
                break;
            case 7:
                weekStr = "周六";
                break;
            default:
                break;
        }

        yearMonthDayWeek[0] = yearStr;
        yearMonthDayWeek[1] = monthStr;
        yearMonthDayWeek[2] = dayStr;
        yearMonthDayWeek[3] = weekStr;

        return yearMonthDayWeek;
    }

    /**
     * 得到本周 （周一到周日的日期）
     *
     * @return
     */
    public static String[] getWeekMonday2SunDay() {
        String[] weeksdate = new String[7];
        Calendar cal = new GregorianCalendar();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        int firstDayWeek = cal.getFirstDayOfWeek();
        cal.setTime(new Date());

        for (int i = 0; i < 7; i++) {
            cal.set(Calendar.DAY_OF_WEEK, firstDayWeek + i);
            Date first = cal.getTime();
            weeksdate[i] = dotformater.format(first);
            //MLog.e("[" + i + "]=" + weeksdate[i]);
        }
        return weeksdate;
    }

    public static String getTodayDateStr() {
        return dotformater.format(new Date(System.currentTimeMillis()));
    }

    public static String getTodayLineDateStr() {
        return lineformater.format(new Date(System.currentTimeMillis()));
    }

    public static String getTommorrowDateStr() {
        return dotformater.format(new Date(System.currentTimeMillis() + 24L * 60 * 60 * 1000));
    }

}
