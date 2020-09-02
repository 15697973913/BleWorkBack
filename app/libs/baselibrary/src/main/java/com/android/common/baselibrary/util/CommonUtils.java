package com.android.common.baselibrary.util;

import android.content.Context;



import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.regex.Pattern;

public class CommonUtils {
    public static SimpleDateFormat simpleDateFormatDH = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat simpleDateFormatD = new SimpleDateFormat("yyyy-MM-dd");


    public static int intervalDays(long currentTime, long aheadOfTime) throws ParseException {
        String currentDateStr = simpleDateFormatD.format(new Date(currentTime));
        String aheadOfTimeStr = simpleDateFormatD.format(new Date(aheadOfTime));
        long newCurrentDateTime =simpleDateFormatD.parse(currentDateStr).getTime();
        long newAheadOfTimeDateTime = simpleDateFormatD.parse(aheadOfTimeStr).getTime();
        float intervalDays = Math.abs(newCurrentDateTime - newAheadOfTimeDateTime) / (24 * 60 * 60);

        return (int) intervalDays;
    }

    public static boolean isEmpty(String string) {
        if (null == string || string.trim().length() == 0 || string.trim().equalsIgnoreCase("null")) {
            return true;
        }
        return false;
    }

    public static void invokeMethod(Object object, String method, Object data) {
        if (object != null) {
            try {
                Class<?> classType = object.getClass();
                Class<?> dataClassType = data.getClass();
                if (data == null) {
                    classType.getMethod(method, new Class[0]).invoke(object, new Object[0]);
                    return;
                }
                classType.getMethod(method, new Class[]{dataClassType}).invoke(object, new Object[]{data});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean verityPwd(String pwd) {
        return Pattern.compile("[\\w|!|@|#|$|%|^|&|*|+|-|/|_|{|}|[|]|=|~|<|>|?|(|)|.|;|:]{4,32}").matcher(pwd).matches();
    }

    public static boolean verityEmail(String pwd) {
        return Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(pwd).matches();
    }

    public static boolean verityUserName(String pwd) {
        return Pattern.compile("^[a-zA-Z0-9_]{3,16}$").matcher(pwd).matches();
    }

    public static boolean verifyPhoneNum(String phoneNum) {
        return Pattern.compile("^1(\\d){10}$").matcher(phoneNum).matches();
    }

    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = ((NetworkInterface) en.nextElement()).getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ipAddress = inetAddress.getHostAddress().toString();
                        if (!ipAddress.contains("::")) {
                            return ipAddress;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static void sleep(int microsecond) {
        try {
            Thread.sleep((long) microsecond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean boolValue(String val, String refValue) {
        if (val == null || refValue == null || !refValue.equals(val.toLowerCase())) {
            return false;
        }
        return true;
    }

    public static int convertInt(String data, int defaultData) {
        try {
            defaultData = Integer.parseInt(data);
        } catch (Exception e) {
        }
        return defaultData;
    }

    public static long convertLong(String data, long defaultData) {
        try {
            defaultData = Long.parseLong(data);
        } catch (Exception e) {
        }
        return defaultData;
    }

    public static int extractIntPart(String str) {
        int result = 0;
        int length = str.length();
        for (int offset = 0; offset < length; offset++) {
            char c = str.charAt(offset);
            if ('0' > c || c > '9') {
                break;
            }
            result = (result * 10) + (c - 48);
        }
        return result;
    }

    /**
     * 生成随机验证码
     * @param valideCodeLength
     * @return
     */
    public static String getRandomCode(int valideCodeLength) {
        if (valideCodeLength > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < valideCodeLength; i++) {
                Random random = new Random(System.currentTimeMillis());
                int randomInt = random.nextInt(10);
                stringBuilder.append(randomInt);
            }
            return stringBuilder.toString();

        }
        return String.valueOf(0);
    }

    public static String int2String(Context context, int  resourceid){
        return context.getResources().getString(resourceid);
    }

    public static int getNumAvailableCores() {
        return  Runtime.getRuntime().availableProcessors();
    }
}
