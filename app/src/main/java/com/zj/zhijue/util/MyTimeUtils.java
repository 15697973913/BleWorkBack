package com.zj.zhijue.util;

import com.facebook.stetho.common.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyTimeUtils {
    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return
     */

    public static String getOldDate(int distanceDay, String strDate, String pattern) {
        SimpleDateFormat dft = new SimpleDateFormat(pattern, Locale.CHINA);

        Date beginDate;
        Date endDate = null;

        try {
            beginDate = dft.parse(strDate);
            if (beginDate != null) {
                Calendar date = Calendar.getInstance();
                date.setTime(beginDate);
                date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
                endDate = dft.parse(dft.format(date.getTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (endDate == null) {
            return strDate;
        }
        return dft.format(endDate);
    }


    public static List<String> getWeekDay(String strDate, String pattern) {
        SimpleDateFormat dft = new SimpleDateFormat(pattern, Locale.CHINA);

        Calendar calendar = Calendar.getInstance();
        try {
            Date beginDate = dft.parse(strDate);
            if (beginDate!=null) {
                calendar.setTime(beginDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 获取本周的第一天
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek + i);

            // 获取星期的显示名称，例如：周一、星期一、Monday等等
//            weekDay.week = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);
//            weekDay.day = new SimpleDateFormat("MM-dd").format(calendar.getTime());

            list.add(new SimpleDateFormat("yyyy.MM.dd").format(calendar.getTime()));
        }

        return list;
    }

}
