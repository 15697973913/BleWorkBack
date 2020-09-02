
package com.zj.zhijue.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.NumberKeyListener;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.Context.ACTIVITY_SERVICE;

@SuppressWarnings("ALL")
public class CommonUtils {

    /**
     * 设置首页今日训练时长
     *
     * @param timeCount
     * @param moreTextView
     */
    public static void setCurDayTimeTextView(int timeCount, TextView moreTextView) {
        String time = com.zj.zhijue.util.DateUtil.getTimerHH_MM_SS(
                timeCount, "小时", "分", "秒"
        );
        if (time != null && time.contains("小时")) {
            SpannableString ss = new SpannableString(time);
            int start1 = 0;
            int end1 = time.indexOf("小时");
            AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(28, true);
            ss.setSpan(absoluteSizeSpan, start1, end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            int start2 = time.indexOf("小时") + 2;
            int end2 = time.indexOf("分");
            AbsoluteSizeSpan absoluteSizeSpan1 = new AbsoluteSizeSpan(28, true);
            ss.setSpan(absoluteSizeSpan1, start2, end2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            int start3 = time.indexOf("分") + 1;
            int end3 = time.indexOf("秒");
            AbsoluteSizeSpan absoluteSizeSpan2 = new AbsoluteSizeSpan(28, true);
            ss.setSpan(absoluteSizeSpan2, start3, end3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            moreTextView.setText(ss);
        }
    }

    /**
     * 设置报表里面消耗时长
     *
     * @param timeCount
     * @param moreTextView
     */
    public static void setMoreTextView(int timeCount, TextView moreTextView) {
        String time = DateUtil.getTimerHH_MM_SS(
                timeCount, "天", "小时", "分", "秒"
        );
        if (time != null && time.contains("小时")) {
            SpannableString ss = new SpannableString(time);
            int start0 = 0;
            int end0 = time.indexOf("天");
            AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(20, true);
            ss.setSpan(absoluteSizeSpan, start0, end0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            int start1 = time.indexOf("天") + 1;
            int end1 = time.indexOf("小时");
            AbsoluteSizeSpan absoluteSizeSpan1 = new AbsoluteSizeSpan(20, true);
            ss.setSpan(absoluteSizeSpan1, start1, end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            int start2 = time.indexOf("小时") + 2;
            int end2 = time.indexOf("分");
            AbsoluteSizeSpan absoluteSizeSpan2 = new AbsoluteSizeSpan(20, true);
            ss.setSpan(absoluteSizeSpan2, start2, end2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            int start3 = time.indexOf("分") + 1;
            int end3 = time.indexOf("秒");
            AbsoluteSizeSpan absoluteSizeSpan3 = new AbsoluteSizeSpan(20, true);
            ss.setSpan(absoluteSizeSpan3, start3, end3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            moreTextView.setText(ss);
        }
    }

    /**
     * 得到SpannableString用于加下划线
     *
     * @param text
     * @param start
     * @param end
     * @return SpannableString
     */
    public static SpannableString getSpannableUnderline(String text, int start, int end) {

        SpannableString msp = new SpannableString(text);
        msp.setSpan(new UnderlineSpan(), start, end, 0);
        return msp;
    }

    /**
     * 得到SpannableString用于修改字符串中的指定字符颜色
     *
     * @param textColor
     * @param text
     * @param start
     * @param end
     * @return SpannableString
     */
    public static SpannableString getSpannableString(String textColor, String text, int start, int end) {

        SpannableString msp = new SpannableString(text);
        msp.setSpan(new ForegroundColorSpan(android.graphics.Color.parseColor(textColor)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }

    /**
     * 得到SpannableString用于修改字符串中的指定字符颜色（多处颜色）
     *
     * @param textColor 字体颜色
     * @param text      文字
     * @param startList 起点list
     * @param endList   终点list （与起点list一一对应）
     * @return SpannableString
     */
    public static SpannableString getSpannableString(String textColor, String text, List<Integer> startList, List<Integer> endList) throws IndexOutOfBoundsException {

        SpannableString msp = new SpannableString(text);
        for (int i = 0; i < startList.size(); i++) {
            int start = startList.get(i);
            int end = endList.get(i);
            msp.setSpan(new ForegroundColorSpan(android.graphics.Color.parseColor(textColor)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return msp;
    }

    /**
     * Activity是否在前台
     *
     * @param context
     * @return
     */
    public static boolean isOnForground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();
        if (appProcessInfoList == null) {
            return false;
        }

        String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo processInfo : appProcessInfoList) {
            if (processInfo.processName.equals(packageName) && processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    public static String md5(String string) {

        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {

        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }


    /**
     * 获取当前的时间 截取掉毫秒
     *
     * @return
     */
    public static String getCurrentTime() {
        String time = new Date().getTime() + "";
        time = time.substring(0, time.length() - 3);
        return time;
    }


    /**
     * 限制只能输入字母、数字，_
     *
     * @param editText
     */
    public static void setEditTextInputType(EditText editText) {
        editText.setKeyListener(new NumberKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT;
            }

            @Override
            protected char[] getAcceptedChars() {

                char[] numberChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
                return numberChars;
            }
        });
    }


    /**
     * 获取屏幕宽度
     *
     * @param context context
     * @return
     */
    public static int getScreenWidth(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int mScreenWidth = outMetrics.widthPixels;

        return mScreenWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @param context context
     * @return
     */
    public static int getScreenHeight(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int mScreenHeight = outMetrics.heightPixels;

        return mScreenHeight;
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }


    /**
     * 得到俩位小数点的String
     *
     * @param num float参数
     * @return String
     */
    public static String getFloatToString_2(float num) {
        DecimalFormat fnum = new DecimalFormat("##0.00");
        return fnum.format(num);
    }

    /**
     * 得到double
     *
     * @param num String
     * @return
     */
    public static double getDouble(String num) {
        double n;
        try {
            n = Double.parseDouble(num);
        } catch (Exception e) {
            e.printStackTrace();
            n = 0;
        }
        return n;
    }


    /**
     * 得到double
     *
     * @param num String
     * @return
     */
    public static long getLong(String num) {
        long n;
        try {
            n = num.contains(".") ? (long) getDouble(num) : Long.parseLong(num);
        } catch (Exception e) {
            e.printStackTrace();
            n = 0;
        }
        return n;
    }

    /**
     * 得到float
     *
     * @param num String
     * @return
     */
    public static float getFloat(String num) {
        float n;
        try {
            n = Float.parseFloat(num);
        } catch (Exception e) {
            e.printStackTrace();
            n = 0;
        }
        return n;
    }

    /**
     * 得到int
     *
     * @param num String
     * @return
     */
    public static int getInt(String num) {
        int n;
        try {
            n = num.contains(".") ? (int) getFloat(num) : Integer.parseInt(num);
        } catch (Exception e) {
            e.printStackTrace();
            n = 0;
        }
        return n;
    }

    /**
     * 获取排序后的List
     *
     * @param iterator
     * @param sortord  1 升序， 2降序
     * @return
     */
    public static List<Integer> getSortList(Iterator<String> iterator, final int sortord) {

        List<Integer> sortList = new ArrayList<>();
        while (iterator.hasNext()) {
            int id = Integer.parseInt(iterator.next());
            sortList.add(id);
        }
        Collections.sort(sortList, new Comparator<Integer>() {
            public int compare(Integer arg0, Integer arg1) {
                return sortord == 1 ? arg1.compareTo(arg0) : arg0.compareTo(arg1);
            }
        });

        return sortList;
    }

    /**
     * 设置隐藏标题栏
     *
     * @param activity
     */
    public static void setNoTitleBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 设置半透明标题栏
     *
     * @param activity
     */
    public static void setTranslucentStatus(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 取消全屏
     *
     * @param activity
     */
    public static void cancelFullScreen(Activity activity) {
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 获取是否存在NavigationBar
     *
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = context.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            boolean result = realSize.y != size.y;
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 获取虚拟功能键高度
     *
     * @param context
     * @return
     */
    public static int getVirtualBarHeigh(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }


    public static void openFlashlight(Camera camera) {
        try {
            camera = Camera.open();
            Camera.Parameters mParameters;
            mParameters = camera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(mParameters);
        } catch (Exception e) {
        }

    }

    public static void closeFlashlight(Camera camera) {
        try {
            Camera.Parameters mParameters;
            mParameters = camera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(mParameters);
            camera.release();
        } catch (Exception e) {
        }
    }

    /**
     * 得到俩位小数点数据
     *
     * @param num String
     * @return 俩位小数点数据1.00
     */
    public static String get2DecimalPointData(String num) {
        String text = num;
        if (num.contains(".")) {
            int v1 = num.length() - num.indexOf(".") - 1;
            switch (v1) {
                case 0:
                    text = num + "00";
                    break;
                case 1:
                    text = num + "0";
                    break;
                default:
                    text = num.substring(0, num.indexOf(".") + 3);
                    break;
            }
        } else {
            text += ".00";
        }

        return text;
    }

    /**
     * 获取处理后的银行卡编号
     *
     * @return **** **** **** 2222
     */
    public static String getBankCardNo(String cardNo) {
        if (cardNo.length() < 4) {
            return cardNo;
        }

        String text = cardNo.substring(cardNo.length() - 4, cardNo.length());
        text = "**** **** **** " + text;
//        int count = (cardNo.length() - 4) / 4;
//        for (int i = 0; i < count; i++) {
//            text = "**** " + text;
//        }
//        count = (cardNo.length() - 4) % 4;
//        String start = "";
//        for (int i = 0; i < count; i++) {
//            start += "*";
//        }
//        text = start + " " + text;

        return text;
    }

    /**
     * 获取URL中参数
     *
     * @param url 地址
     * @return Map
     */
    public static Map<String, String> getURLParams(String url) throws Exception {

        Map<String, String> params = new HashMap<>();
        int start = url.contains("?") ? url.indexOf("?") + 1 : 0;
        String[] texts = url.substring(start, url.length()).split("&");
        for (String text : texts) {
            String[] values = text.split("=");
            params.put(values[0], values[1]);
        }

        return params;
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);//ACTION_CALL
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    public static void hideSoftKeyboard(Context context, List<View> viewList) {
        if (viewList == null) return;

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        for (View v : viewList) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static String diffAvatar(String domain, String url) {
        if (url == null) return "";
        if (url.length() > 4 && url.substring(0, 5).contains("http"))
            return url;
        else
            return domain + url;
    }

}