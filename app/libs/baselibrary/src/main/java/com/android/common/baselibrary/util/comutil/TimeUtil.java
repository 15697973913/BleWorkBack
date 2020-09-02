package com.android.common.baselibrary.util.comutil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeUtil {
    private static SimpleDateFormat YEAR_TO_SEC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
    static String date_days_before = "^[0-9]{1,3}天前$";
    static String date_month = "[0-9]{1,2}月[0-9]{1,2}$";
    static String date_month2 = "[0-9]{1,2}月[0-9]{1,2}日$";
    static String date_week = "^(星期|周)[一,二,三,四,五,六,日]$";
    static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    static String datetime_desc = "^[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}[日,号](\\s*)(凌晨|早上|上午|中午|下午|晚上)(\\s*)[0-9]{1,2}:[0-9]{1,2}$";
    static String datetime_month = "^[0-9]{1,2}月[0-9]{1,2}[日,号](\\s*)[0-9]{1,2}:[0-9]{1,2}$";
    static String datetime_month_desc = "^[0-9]{1,2}月[0-9]{1,2}[日,号](\\s*)(凌晨|早上|上午|中午|下午|晚上)(\\s*)[0-9]{1,2}:[0-9]{1,2}$";
    static String normal_date = "^[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}[日,号]$";
    static String normal_datetime = "^[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}[日,号](\\s*)[0-9]{1,2}:[0-9]{1,2}$";
    static String normal_time = "[0-9]{1,2}:[0-9]{1,2}$";
    static Map<String, Integer> numMap = new HashMap();
    static String time_desc = "^(凌晨|早上|上午|中午|下午|晚上)(\\s*)[0-9]{1,2}:[0-9]{1,2}$";
    static String time_hour_before = "^[0-9]{1,2}小时前$";
    static String time_min_before = "^[0-9]{1,2}分钟前$";
    static String time_week = "^(星期|周)[一,二,三,四,五,六,日](\\s*)[0-9]{1,2}:[0-9]{1,2}$";
    static String time_yesterday = "^昨天(\\s*)[0-9]{1,2}:[0-9]{1,2}$";
    static String time_yesterday_desc = "^昨天(\\s*)(凌晨|早上|上午|中午|下午|晚上)(\\s*)[0-9]{1,2}:[0-9]{1,2}$";

    static {
        numMap.clear();
        numMap.put("日", Integer.valueOf(0));
        numMap.put("一", Integer.valueOf(1));
        numMap.put("二", Integer.valueOf(2));
        numMap.put("三", Integer.valueOf(3));
        numMap.put("四", Integer.valueOf(4));
        numMap.put("五", Integer.valueOf(5));
        numMap.put("六", Integer.valueOf(6));
    }

    public static long getLongTime(String date) {
        try {
            return YEAR_TO_SEC.parse(date).getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    public static int compareFormatTime(String time1, String time2, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(time1).compareTo(dateFormat.parse(time2));
        } catch (Exception e) {
           e.printStackTrace();
            return 99999;
        }
    }

    public static int compareTime(String time1, String time2) {
        long t1 = time2Date(time1);
        long t2 = time2Date(time2);
        if (t1 > t2) {
            return 1;
        }
        if (t1 < t2) {
            return -1;
        }
        return 0;
    }

    public static long time2Date(String time) {
        try {
            Calendar rightnow = Calendar.getInstance();
            rightnow.setTime(new Date());
            if (time == null) {
                return rightnow.getTimeInMillis();
            }
            time = time.trim();
            if (time.matches(date_days_before)) {
                rightnow.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(time.replace("天前", "")));
            } else {
                Date date;
                if (time.matches(time_yesterday)) {
                    date = new Date();
                    rightnow.setTime(datetimeFormat.parse(time.replace("昨天", (date.getYear() + 1900) + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日 ")));
                    rightnow.add(Calendar.DAY_OF_MONTH, -1);
                } else {
                    if (time.matches(time_yesterday_desc)) {
                        date = new Date();
                        boolean isSecondHalfDay = time.matches("(下午|晚上)(.*)");
                        time = time.replaceAll("(凌晨|早上|上午|中午|下午|晚上)", " ");
                        date = datetimeFormat.parse(time.replace("昨天", (date.getYear() + 1900) + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日") + time);
                        rightnow.setTime(date);
                        rightnow.add(Calendar.DAY_OF_MONTH, -1);
                        if (isSecondHalfDay && date.getHours() < 12) {
                            rightnow.add(Calendar.HOUR_OF_DAY, 12);
                        }
                    } else {
                        if (time.matches(normal_datetime)) {
                            rightnow.setTime(datetimeFormat.parse(time));
                        } else {
                            boolean b;
                            if (time.matches(time_desc)) {
                                date = new Date();
                                b = time.matches("(下午|晚上)(.*)");
                                date = datetimeFormat.parse((date.getYear() + 1900) + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日" + time.replaceAll("(凌晨|早上|上午|中午|下午|晚上)", " "));
                                rightnow.setTime(date);
                                if (b && date.getHours() < 12) {
                                    rightnow.add(Calendar.HOUR_OF_DAY, 12);
                                }
                            } else {
                                if (time.matches(normal_time)) {
                                    date = new Date();
                                    rightnow.setTime(datetimeFormat.parse((date.getYear() + 1900) + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日 " + time));
                                } else {
                                    if (time.matches(time_hour_before)) {
                                        rightnow.add(Calendar.HOUR_OF_DAY, -Integer.parseInt(time.replace("小时前", "")));
                                    } else {
                                        if (time.matches(time_min_before)) {
                                            rightnow.add(Calendar.MINUTE, -Integer.parseInt(time.replace("分钟前", "")));
                                        } else {
                                            int nYear;
                                            if (time.matches(datetime_month)) {
                                                int monthPos = time.indexOf("月");
                                                int dayPos = time.indexOf("日");
                                                if (dayPos == -1) {
                                                    dayPos = time.indexOf("号");
                                                }
                                                String strMonth = time.substring(0, monthPos);
                                                String strDay = time.substring(monthPos + 1, dayPos);
                                                String strTime = time.substring(dayPos + 1);
                                                nYear = rightnow.get(Calendar.YEAR);
                                                date = new Date();
                                                rightnow.setTime(datetimeFormat.parse(nYear + "年" + strMonth.trim() + "月" + strDay.trim() + "日 " + strTime.trim()));
                                            } else {
                                                if (time.equals("今天")) {
                                                    rightnow.set(rightnow.get(Calendar.YEAR), rightnow.get(Calendar.MONTH), rightnow.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                                                } else {
                                                    if (time.equals("昨天")) {
                                                        rightnow.add(Calendar.DAY_OF_MONTH, -1);
                                                    } else {
                                                        if (time.equals("前天")) {
                                                            rightnow.add(Calendar.DAY_OF_MONTH, -2);
                                                        } else {
                                                            if (time.matches(date_week)) {
                                                                int weekDay = rightnow.get(Calendar.DAY_OF_WEEK) - 1;
                                                                rightnow.add(Calendar.DAY_OF_WEEK, ((Integer) numMap.get(time.replaceAll("(周|星期)", ""))).intValue() - weekDay);
                                                            } else {
                                                                if (time.matches(time_week)) {
                                                                    String timePart = time;
                                                                    String weekPart = time.replaceAll("(周|星期)", "").replaceAll("(\\s*)[0-9]{1,2}:[0-9]{1,2}", "").trim();
                                                                    timePart = timePart.replaceAll("(星期|周)[一,二,三,四,五,六,日]", "").trim();
                                                                    date = new Date();
                                                                    rightnow.add(Calendar.DAY_OF_WEEK, ((Integer) numMap.get(weekPart)).intValue() - (rightnow.get(Calendar.DAY_OF_WEEK) - 1));
                                                                    nYear = rightnow.get(Calendar.YEAR);
                                                                    int nMonth = rightnow.get(Calendar.MONTH) + 1;
                                                                    rightnow.setTime(datetimeFormat.parse(nYear + "年" + nMonth + "月" + rightnow.get(Calendar.DAY_OF_MONTH) + "日 " + timePart));
                                                                } else {
                                                                    if (time.matches(normal_date)) {
                                                                        rightnow.setTime(datetimeFormat.parse(time));
                                                                    } else {
                                                                        if (time.matches(datetime_desc)) {
                                                                            b = time.matches("(下午|晚上)(.*)");
                                                                            date = datetimeFormat.parse(time.replaceAll("(凌晨|早上|上午|中午|下午|晚上)", " "));
                                                                            rightnow.setTime(date);
                                                                            if (b && date.getHours() < 12) {
                                                                                rightnow.add(Calendar.HOUR_OF_DAY, 12);
                                                                            }
                                                                        } else {
                                                                            if (time.matches(datetime_month_desc)) {
                                                                                date = new Date();
                                                                                b = time.matches("(下午|晚上)(.*)");
                                                                                date = datetimeFormat.parse((date.getYear() + 1900) + "年" + time.replaceAll("(凌晨|早上|上午|中午|下午|晚上)", " "));
                                                                                rightnow.setTime(date);
                                                                                if (b && date.getHours() < 12) {
                                                                                    rightnow.add(Calendar.HOUR_OF_DAY, 12);
                                                                                }
                                                                            } else {
                                                                                if (time.matches(date_month)) {
                                                                                    rightnow.setTime(dateFormat.parse((new Date().getYear() + 1900) + "年" + time + "日"));
                                                                                } else {
                                                                                    if (!time.matches(date_month2)) {
                                                                                        return 0;
                                                                                    }
                                                                                    rightnow.setTime(dateFormat.parse((new Date().getYear() + 1900) + "年" + time));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return rightnow.getTimeInMillis();
        } catch (Exception e) {
            //SdLogUtil.writeExceptionLog(e);
            e.printStackTrace();
            return 0;
        }
    }
}
