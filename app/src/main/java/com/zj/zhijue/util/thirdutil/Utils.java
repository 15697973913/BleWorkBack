package com.zj.zhijue.util.thirdutil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.net.UrlQuerySanitizer.ParameterValuePair;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.provider.Settings;
import android.provider.Settings.Secure;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import java.util.function.Predicate;
//import com.android.internal.util.Predicate;
//import com.google.common.base.Predicate;
import com.zj.zhijue.MyApplication;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.zj.zhijue.R;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import static android.os.Environment.MEDIA_MOUNTED;


public class Utils {
    private static Properties CONFIG_PROPERTIES = null;
    private static final Predicate<String> DEFAULT_FILTER = null;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+");
    public static final String MD5KEY = "Tve3TAZgTiOLnOTMUrXRETe1RHkg2SkV";
    public static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]*$");
    public static Map<String, Pattern> PATTERN_MAP = new HashMap();
    public static final Pattern PHONE_PATTERN = Pattern.compile("[1][\\d]{10}");
    public static final String TAG = "Utils";
    private static Handler delayHandler = new Handler();
    private static long lastClickTime;
    public static int sCurrentType = -1;

    public static String format(String source, String... params) {
        String result = source;
        int pos = 0;
        while (pos < params.length) {
            int pos2 = pos + 1;
            pos = pos2 + 1;
            result = result.replaceAll("\\{" + params[pos] + "\\}", params[pos2]);
        }
        return result;
    }

    public static Boolean validateMail(String mail) {
        if (EMAIL_ADDRESS_PATTERN.matcher(mail).matches()) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean validatePwd(String pwd) {
        return Boolean.valueOf(true);
    }

    public static Boolean validatePhone(String phone) {
        if (PHONE_PATTERN.matcher(phone).matches()) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean validateNumber(String num) {
        if (NUMBER_PATTERN.matcher(num).matches()) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static String getMD5Str(String pwd) {
        String s = pwd;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (byte b : messageDigest) {
                hexString.append(Integer.toHexString((b & 255) | 256).substring(1, 3));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String md5(String string) {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 255) < 16) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 255));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e2);
        }
    }

    public static String md5(byte[] bytes) {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(bytes);
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 255) < 16) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 255));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        }
    }




    @SuppressLint({"MissingPermission"})
    public static String getIMEI(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != 0) {
            return null;
        }
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager != null) {
            return manager.getDeviceId();
        }
        return null;
    }

    @SuppressLint({"MissingPermission"})
    public static String getIccid(Context context) {
        String result = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
        return result == null ? "" : result;
    }

    @SuppressLint({"MissingPermission"})
    public static String getIMSI(Context context) {
        String result = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
        return result == null ? "" : result;
    }

    public static String getSrcureId(Context context) {
        try {
            return Secure.getString(context.getContentResolver(),  Settings.System.ANDROID_ID);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getUUID(Context context) {
        try {
            return Installation.id(context);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMobileInfo() {
        return "PhoneInfo:" + Build.MANUFACTURER + "," + Build.MODEL + "," + VERSION.RELEASE;
    }



    public static boolean isWifiConnection(Context context) {
        if (getNetworkStatus(context) == 1) {
            return true;
        }
        return false;
    }

    @SuppressLint({"MissingPermission"})
    public static boolean isMobileDataConnection(Context context) {
        NetworkInfo mobile = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobile == null || mobile.getState() != State.CONNECTED) {
            return false;
        }
        return true;
    }

    @SuppressLint({"MissingPermission"})
    public static String getMobileConnectionStr(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null) {
            return "noConnection";
        }
        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            return "wifi";
        }
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            return "cell";
        }
        return "";
    }

    public static int getVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean isScreenOn(Context context) {
        return ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).isScreenOn();
    }

    public static int getVersion(Context context, String packagename) {
        int i = 0;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packagename, 0);
            String vc = info.versionName;
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return i;
        }
    }



    @SuppressLint({"MissingPermission"})
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            NetworkInfo mNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static long getSafeLong(String s) {
        long num = 0;
        try {
            num = Long.parseLong(s);
        } catch (Exception e) {
        }
        return num;
    }

    @SuppressLint({"MissingPermission"})
    public static String getLocalMacAddress(Context context) {
        WifiInfo info = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
        if (info.getMacAddress() == null) {
            return "";
        }
        return info.getMacAddress();
    }

    public static String getMac() {
        String macSerial = null;
        String str = "";
        try {
            LineNumberReader input = new LineNumberReader(new InputStreamReader(Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ").getInputStream()));
            while (str != null) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return macSerial;
    }

    public static List<ParameterValuePair> getParam(String strUrl) {
        UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
        sanitizer.setAllowUnregisteredParamaters(true);
        sanitizer.parseUrl(strUrl);
        List pairs = sanitizer.getParameterList();
        if (pairs.size() > 0) {
            for (int i = 0; i < pairs.size(); i++) {

            }
        }
        return pairs;
    }

    public static String getAssetsString(Context context, String fn) {
        IOException e;
        Throwable th;
        String outString = "";
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            inputStream = context.getAssets().open(fn);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                byte[] buf = new byte[1024];
                while (true) {
                    int len = inputStream.read(buf);
                    if (len == -1) {
                        break;
                    }
                    outputStream.write(buf, 0, len);
                }
                outString = outputStream.toString("utf-8");
                safeClose(outputStream);
                safeClose(inputStream);
                byteArrayOutputStream = outputStream;
            } catch (IOException e2) {
               e2.printStackTrace();
            } catch (Throwable th3) {
               th3.printStackTrace();
            }
        } catch (IOException e3) {
           e3.printStackTrace();
        } finally {
            safeClose(byteArrayOutputStream);
            safeClose(inputStream);
        }
        return outString;
    }


    public static String getSDPath() {
        File sdDir = null;
        if (Environment.getExternalStorageState().equals(MEDIA_MOUNTED)) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir != null) {
            return sdDir.toString();
        }
        return null;
    }

    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        Resources resources = context.getResources();
        int resIdStatusbarHeight = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resIdStatusbarHeight > 0) {
            return resources.getDimensionPixelSize(resIdStatusbarHeight);
        }
        return 0;
    }

    public static boolean isHttpOrHttpsScheme(String scheme) {
        return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
    }

    public static boolean isStartWithHttpOrHttpsScheme(String scheme) {
        if (CommonUtils.isEmpty(scheme) || scheme.trim().length() < 6) {
            return false;
        }

        return "http".equalsIgnoreCase(scheme.substring(0, 4)) || "https".equalsIgnoreCase(scheme.substring(0, 5));
    }


    public static boolean queryActivityIntent(Context context, Intent intent) {
        List queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        if (queryIntentActivities == null || queryIntentActivities.size() <= 0) {
            return false;
        }
        return true;
    }


    public static String generateJsParamStr(String data) {
        if (TextUtils.isEmpty(data)) {
            return "''";
        }
        StringBuilder jsParamData = new StringBuilder();
        jsParamData.append("'");
        jsParamData.append(escapeString(data));
        jsParamData.append("'");
        return jsParamData.toString();
    }


    public static String escapeString(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        char[] chars = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : chars) {
            switch (c) {
                case '\b':
                    builder.append("\\b");
                    break;
                case '\t':
                    builder.append("\\t");
                    break;
                case '\n':
                    builder.append("\\n");
                    break;
                case '\f':
                    builder.append("\\f");
                    break;
                case '\r':
                    builder.append("\\r");
                    break;
                case '\"':
                    builder.append("\\\"");
                    break;
                case '\'':
                    builder.append("\\'");
                    break;
                case '\\':
                    builder.append("\\\\");
                    break;
                default:
                    builder.append(c);
                    break;
            }
        }
        return builder.toString();
    }


    public static int getScreentHeight(Activity activity) {
        Display d = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        if (VERSION.SDK_INT >= 14 && VERSION.SDK_INT < 17) {
            try {
                heightPixels = ((Integer) Display.class.getMethod("getRawHeight", new Class[0]).invoke(d, new Object[0])).intValue();
            } catch (Exception e) {
            }
        } else if (VERSION.SDK_INT >= 17) {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", new Class[]{Point.class}).invoke(d, new Object[]{realSize});
                heightPixels = realSize.y;
            } catch (Exception e2) {
            }
        }
        return heightPixels;
    }


    @NonNull
    private static String streamToString(@NonNull InputStream input) throws IOException {
        return streamToString(input, DEFAULT_FILTER);
    }

    @NonNull
    private static String streamToString(@NonNull InputStream input, Predicate<String> filter) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input), 1024 * 8);
        try {
            List buffer = new LinkedList();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                } else if (filter.test(line)) {
                    buffer.add(line);
                }
            }
            String join = TextUtils.join("\n", buffer);
            return join;
        } finally {
            safeClose(reader);
        }
    }

    private static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }

    public static String getCurrentProcessName(Context context) {
        String currentProcess = null;
        int pid = Process.myPid();
        List<RunningAppProcessInfo> runningAppProcessInfos = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
        if (runningAppProcessInfos != null) {
            for (RunningAppProcessInfo appProcess : runningAppProcessInfos) {
                if (appProcess.pid == pid) {
                    currentProcess = appProcess.processName;
                    break;
                }
            }
        }
        if (currentProcess == null) {
            try {
                currentProcess = streamToString(new FileInputStream("/proc/self/cmdline")).trim();
            } catch (IOException e) {
                return null;
            }
        }
        return currentProcess;
    }

    public static String getCurrentActivityName(Context context) {
        String activityName = null;
        if (context == null) {
            return null;
        }
        try {
            activityName = ((RunningTaskInfo) ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1).get(0)).topActivity.getClassName();
        } catch (Exception e) {
        }
        return activityName;
    }


    public static int getVersionCode(PackageInfo info) {
        if (info == null) {
            return 0;
        }
        return info.versionCode;
    }



    public static String[] getMatchStr(String str, String pattern) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(pattern)) {
            return null;
        }
        Pattern p = (Pattern) PATTERN_MAP.get(pattern);
        if (p == null) {
            p = Pattern.compile(pattern);
            PATTERN_MAP.put(pattern, p);
        }
        Matcher m = p.matcher(str);
        if (!m.find()) {
            return null;
        }
        String[] result = new String[m.groupCount()];
        for (int i = 0; i < m.groupCount(); i++) {
            result[i] = m.group(i + 1);
        }
        return result;
    }

    public static String[] getMatchStr2(String str, String pattern) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(pattern)) {
            return null;
        }
        Pattern p = (Pattern) PATTERN_MAP.get(pattern);
        if (p == null) {
            p = Pattern.compile(pattern);
            PATTERN_MAP.put(pattern, p);
        }
        Matcher m = p.matcher(str);
        List tempList = new ArrayList();
        while (m.find()) {
            tempList.add(m.group());
        }
        if (tempList.size() <= 0) {
            return null;
        }
        String[] result = new String[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            result[i] = (String) tempList.get(i);
        }
        return result;
    }


    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(800);
    }

    public static boolean isFastDoubleClick(int milliSeconds) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < ((long) milliSeconds)) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static void clearLastDoubleClickTime() {
        lastClickTime = 0;
    }

    public static byte[] gzipText(String str) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte[] logs = str.getBytes("utf-8");
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(logs);
            gzip.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return out.toByteArray();
    }

    public static byte[] gzipBytes(byte[] data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(data);
            gzip.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return out.toByteArray();
    }

    public static byte[] gzipText(File file) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            FileInputStream fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            while (true) {
                int n = fis.read(b);
                if (n == -1) {
                    break;
                }
                gzip.write(b, 0, n);
            }
            gzip.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return bos.toByteArray();
    }

    public static void executeDelayTaskOnUIThread(Runnable runnable, long time) {
        if (runnable != null && delayHandler != null) {
            delayHandler.removeCallbacks(runnable);
            delayHandler.postDelayed(runnable, time);
        }
    }

    @SuppressLint({"MissingPermission"})
    public static String getIpAddress(Context context) {
        String ipAddress = null;
        if (isWifiConnection(context)) {
            ipAddress = intToIp(((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getIpAddress());
        } else if (isMobileDataConnection(context)) {
            try {
                Enumeration en = NetworkInterface.getNetworkInterfaces();
                while (en.hasMoreElements()) {
                    Enumeration enumIpAddr = ((NetworkInterface) en.nextElement()).getInetAddresses();
                    while (enumIpAddr.hasMoreElements()) {
                        InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (SocketException e) {
                return null;
            }
        }
        return ipAddress;
    }

    private static String intToIp(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }


    public static final String nullToBlank(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str;
    }




    public static String buildSmgMd5(Map<String, String> params) {
        if (params == null) {
            return "";
        }
        List<String> paramNames = new ArrayList(params.keySet());
        Collections.sort(paramNames);
        StringBuilder builder = new StringBuilder();
        for (String key : paramNames) {
            if (!TextUtils.isEmpty((CharSequence) params.get(key))) {
                builder.append(key);
                builder.append('=');
                builder.append((String) params.get(key));
                builder.append("&");
            }
        }
        String str = builder.toString();
        return "";//MD5.a(str.substring(0, str.length() - 1));
    }

    public static boolean isInstallShortcut(Context context) {
        boolean result = false;
        Uri uri = Uri.parse("content://com.android.launcher2.settings/favorites?notify=true");
        Cursor c = context.getContentResolver().query(uri, new String[]{"title", "iconResource"}, "title=?", new String[]{context.getString(R.string.app_name)}, null);
        if (c != null && c.getCount() > 0) {
            result = true;
        }
        if (c != null) {
            c.close();
        }

        return result;
    }

    public static void addShortcut(Activity mContext) {
        Intent shortCutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortCutIntent.putExtra("android.intent.extra.shortcut.NAME", mContext.getString(R.string.app_name));
        shortCutIntent.putExtra("duplicate", false);
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.addCategory("android.intent.category.LAUNCHER");
        localIntent.setFlags(270532608);//(270532608);
        localIntent.setClass(mContext, mContext.getClass());
        shortCutIntent.putExtra("android.intent.extra.shortcut.INTENT", localIntent);
        shortCutIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(mContext, R.drawable.ic_launcher_background));
        mContext.sendBroadcast(shortCutIntent);
        //FanliPerference.saveBoolean(mContext, FanliPerference.KEY_HAS_SHORT_CUT, true);
    }

    public static void deleteShortCut(Activity activity, boolean isOld) {
        String appName = activity.getString(R.string.app_name);
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        shortcut.putExtra("android.intent.extra.shortcut.NAME", appName);
        Intent localIntent = new Intent("android.intent.action.MAIN");
        if (isOld) {
            localIntent.setClassName(activity, activity.getClass().getName());
        } else {
            localIntent.addCategory("android.intent.category.LAUNCHER");
            localIntent.setFlags(270532608);//(270532608);
            localIntent.setClass(activity, activity.getClass());
        }
        shortcut.putExtra("android.intent.extra.shortcut.INTENT", localIntent);
        activity.sendBroadcast(shortcut);
    }

    public static boolean isFanliActivityRunning(Context context) {
        List taskInfo = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
        if (taskInfo == null || taskInfo.size() <= 0 || !((RunningTaskInfo) taskInfo.get(0)).topActivity.getClassName().startsWith("com.zzkjyhj.fanli.app")) {
            return false;
        }
        return true;
    }

    public static String appendQuery(String url, String key, String value) {
        value = URLEncoder.encode(value);
        if (UrlUtils.getParamsFromUrl(url).getCount() > 0) {
            return url + "&" + key + "=" + value;
        }
        return url + "?" + key + "=" + value;
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }



    public static int dip2px(float dpValue) {
        if (dpValue == 0.0f) {
            return 0;
        }
        return (int) ((dpValue * (MyApplication.getInstance().getResources().getDisplayMetrics().density)) + 0.5f);
    }

    public static int parseColor(String colorStr, String alpha) {
        return parseColor(colorStr, alpha, 0);
    }

    public static int parseColor(String colorStr, String alpha, int defaultColor) {
        if (!TextUtils.isEmpty(colorStr)) {
            colorStr = colorStr.trim();
            if (!colorStr.startsWith("#")) {
                colorStr = "#" + colorStr;
            }
            int length = colorStr.length();
            if (!TextUtils.isEmpty(alpha) && length == 7) {
                colorStr = colorStr.replace("#", "#" + alpha);
            }
            try {
                defaultColor = Color.parseColor(colorStr);
            } catch (Exception e) {
            }
        }
        return defaultColor;
    }

    public static int computeColor(int startColor, int endColor, float radio) {
        if (radio > 1.0f) {
            radio = 1.0f;
        } else if (radio < 0.0f) {
            radio = 0.0f;
        }
        int startRed = Color.red(startColor);
        int startBlue = Color.blue(startColor);
        int startGreen = Color.green(startColor);
        int startAlpha = Color.alpha(startColor);
        int endRed = Color.red(endColor);
        int endBlue = Color.blue(endColor);
        return Color.argb((int) (((double) startAlpha) + (((double) (((float) (Color.alpha(endColor) - startAlpha)) * radio)) + 0.5d)), (int) (((double) startRed) + (((double) (((float) (endRed - startRed)) * radio)) + 0.5d)), (int) (((double) startGreen) + (((double) (((float) (Color.green(endColor) - startGreen)) * radio)) + 0.5d)), (int) (((double) startBlue) + (((double) (((float) (endBlue - startBlue)) * radio)) + 0.5d)));
    }

    public static SpannableString getTextStyle(String text, int intSize, int decimalSize, int numColor) {
        text = nullToBlank(text);
        SpannableString sp = new SpannableString(text);
        String[] result = getMatchStr2(text, "\\d+(\\.\\d+)?");
        if (result != null && result.length > 0) {
            for (String num : result) {
                int numIndex = text.indexOf(num);
                int dotIndex = num.indexOf(".");
                if (dotIndex == -1) {
                    sp.setSpan(new AbsoluteSizeSpan(intSize, true), numIndex, num.length() + numIndex, 33);
                } else {
                    sp.setSpan(new AbsoluteSizeSpan(intSize, true), numIndex, numIndex + dotIndex, 33);
                    sp.setSpan(new AbsoluteSizeSpan(decimalSize, true), numIndex + dotIndex, num.length() + numIndex, 33);
                }
                sp.setSpan(new ForegroundColorSpan(numColor), numIndex, num.length() + numIndex, 34);
            }
            int colonIndex = text.indexOf(SymbolExpUtil.SYMBOL_COLON);
            if (colonIndex != -1) {
                sp.setSpan(new AbsoluteSizeSpan(intSize, true), colonIndex, colonIndex + 1, 33);
            }
            int percentIndex = text.indexOf(Operators.MOD);
            if (percentIndex != -1) {
                sp.setSpan(new ForegroundColorSpan(numColor), percentIndex, percentIndex + 1, 34);
            }
        }
        return sp;
    }

    public static SpannableString getTextStyleFanliRule(String text, int intSize) {
        text = nullToBlank(text);
        String[] results = getMatchStr2(text, "\\d+(\\.\\d+)?\\%?");
        if (results == null) {
            return new SpannableString(text);
        }
        String result = results[0];
        if (TextUtils.isEmpty(result)) {
            return new SpannableString(text);
        }
        String num = result;
        int numIndex = text.indexOf(num);
        text = text.substring(0, numIndex) + "\n" + text.substring(numIndex);
        numIndex = text.indexOf(num);
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new AbsoluteSizeSpan(intSize, true), numIndex, num.length() + numIndex, 33);
        return sp;
    }

    public static String secToTime(long time) {
        if (time <= 0) {
            return "000000";
        }
        String timeStr;
        long minute = time / 60;
        if (minute < 60) {
            timeStr = "00" + unitFormat(minute) + unitFormat(time % 60);
        } else {
            long hour = minute / 60;
            if (hour > 99) {
                return "995959";
            }
            minute %= 60;
            timeStr = unitFormat(hour) + unitFormat(minute) + unitFormat((time - (3600 * hour)) - (60 * minute));
        }
        return timeStr;
    }

    public static String milliSecToTime(long time) {
        return secToTime(time / 10) + (time % 10);
    }

    public static String unitFormat(long i) {
        if (i < 0 || i >= 10) {
            return "" + i;
        }
        return "0" + Long.toString(i);
    }


    public static void setBold(TextView tv) {
        tv.getPaint().setFakeBoldText(true);
    }

    public static String getFormattedCurrency(String value) {
        if (value == null) {
            return "";
        }
        return value.endsWith(".00") ? (String) value.subSequence(0, value.indexOf(".00")) : value;
    }


    public static boolean isStringEqual(String s1, String s2) {
        if (s1 == null) {
            return s2 == null;
        } else {
            return s1.equalsIgnoreCase(s2);
        }
    }

    public static boolean compareEquals(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        } else {
            return o1.equals(o2);
        }
    }


    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        return !Character.isUpperCase(first) ? Character.toUpperCase(first) + s.substring(1) : s;
    }

    public static Properties getConfigProperties(Context context) {
        if (CONFIG_PROPERTIES == null) {
            CONFIG_PROPERTIES = new Properties();
            try {
                CONFIG_PROPERTIES.load(context.getAssets().open("platform.properties"));
            } catch (IOException e) {
                CONFIG_PROPERTIES = null;
            }
        }
        return CONFIG_PROPERTIES;
    }

    public static int parseInteger(String si) {
        return parseInt(si, 0);
    }

    public static int parseInt(String str, int defaultValue) {
        int result = defaultValue;
        if (TextUtils.isEmpty(str)) {
            return result;
        }
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
        }
        return result;
    }

    public static long parseLong(String sl) {
        return parseLong(sl, 0);
    }

    public static long parseLong(String str, long defaultValue) {
        long result = defaultValue;
        if (TextUtils.isEmpty(str)) {
            return result;
        }
        try {
            result = Long.parseLong(str);
        } catch (NumberFormatException e) {
        }
        return result;
    }

    public static int convertNumberBy750To320(int number) {
        return Math.round((((float) number) * 720.0f) / 1500.0f);
    }

    public static int getBinaryValue(int num, int pos) {
        return (num >> pos) & 1;
    }

    public static String signWithMD5(Map<String, String> params) {
        if (params == null) {
            return null;
        }
        try {
            StringBuilder result = new StringBuilder();
            List<String> paramKeys = new ArrayList(params.keySet());
            Collections.sort(paramKeys);
            for (String key : paramKeys) {
                if (params.get(key) != null) {
                    result.append(key);
                    result.append((String) params.get(key));
                }
            }
            result.append(MD5KEY);
            return "";//MD5.a(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String escapeExprSpecialWord(String text) {
        int i = 0;
        if (!TextUtils.isEmpty(text)) {
            String[] fbsArr = new String[]{"\\", "$", "(", ")", Operators.MUL, Operators.PLUS, ".", Operators.ARRAY_START_STR, Operators.ARRAY_END_STR, "?", "^", Operators.BLOCK_START_STR, "}", SymbolExpUtil.SYMBOL_VERTICALBAR};
            int length = fbsArr.length;
            while (i < length) {
                String key = fbsArr[i];
                if (text.contains(key)) {
                    text = text.replace(key, "\\" + key);
                }
                i++;
            }
        }
        return text;
    }


    @TargetApi(17)
    public static void setLTRDirectionOfViewTree(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                setLTRDirectionOfViewTree(viewGroup.getChildAt(i));
            }
            viewGroup.setLayoutDirection(view.getLayoutDirection());
            return;
        }
        view.setLayoutDirection(view.getLayoutDirection());
        view.setTextDirection(view.getTextDirection());
    }


    public static int getFirstVisibleIndex(LayoutManager layoutManager) {
        int[] firstVisibleIndexes = null;
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            firstVisibleIndexes = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null);
        } else if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else {
            if (layoutManager instanceof GridLayoutManager) {
                return ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
            }
        }
        if (firstVisibleIndexes == null || firstVisibleIndexes.length <= 0) {
            return 0;
        }
        int firstVisibleIndex = firstVisibleIndexes[0];
        for (int i = 1; i < firstVisibleIndexes.length; i++) {
            if (firstVisibleIndex > firstVisibleIndexes[i]) {
                firstVisibleIndex = firstVisibleIndexes[i];
            }
        }
        return firstVisibleIndex;
    }

    public static String transferString(String value, String emptyPrefix) {
        return TextUtils.isEmpty(value) ? emptyPrefix + value : value;
    }

    public static void fixHWInputMethodManagerLeak(Context destContext) {
        if (destContext != null && "huawei".equalsIgnoreCase(Build.BRAND)) {
            InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                try {
                    Field field = imm.getClass().getDeclaredField("mLastSrvView");
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    Object lastSrvView = field.get(imm);
                    if (lastSrvView != null && (lastSrvView instanceof View) && ((View) lastSrvView).getContext() == destContext) {
                        field.set(imm, null);
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }





    public static Drawable getDrawableWithGradientColor(String color, int defaultColor) {
        if (TextUtils.isEmpty(color)) {
            return null;
        }
        String[] colorStrArr = color.split("-");
        if (colorStrArr.length <= 1) {
            return new ColorDrawable(parseColor(color, "ff", defaultColor));
        }
        int[] colorArr = new int[colorStrArr.length];
        for (int i = 0; i < colorStrArr.length; i++) {
            colorArr[i] = parseColor(colorStrArr[i], "ff", defaultColor);
        }
        GradientDrawable drawable = new GradientDrawable(Orientation.LEFT_RIGHT, colorArr);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        return drawable;
    }

    public static Drawable getDrawableWithGradientColor(String color) {
        return getDrawableWithGradientColor(color, 0);
    }

    public static String getPartUUid(String uuid) {
        String result = "";
        if (TextUtils.isEmpty(uuid) || uuid.length() < 8) {
            return result;
        }
        return uuid.substring(0, 8);
    }



    private static int getConnectivityStatus(Context context) {
        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == 1) {
                return 1;
            }
            if (activeNetwork.getType() == 0) {
                return 2;
            }
        }
        return 0;
    }

    public static int getNetworkStatus(Context context) {
        if (sCurrentType == -1) {
            sCurrentType = getConnectivityStatus(context);
        }
        return sCurrentType;
    }

    public void onReceive(Context context, Intent intent) {
        sCurrentType = getConnectivityStatus(context);
    }

    private static String getConnectivityStatusString() {
        if (sCurrentType == 1) {
            return "Wifi enabled";
        }
        if (sCurrentType == 2) {
            return "Mobile data enabled";
        }
        if (sCurrentType == 0) {
            return "Not connected to Internet";
        }
        return null;
    }

    public static boolean isListEqualWithOrder(List list1, List list2) {
        boolean b1;
        boolean b2;
        if (list1 == null) {
            b1 = true;
        } else {
            b1 = false;
        }
        if (list2 == null) {
            b2 = true;
        } else {
            b2 = false;
        }
        if (b1 && b2) {
            return true;
        }
        if (b1 ^ b2) {
            return false;
        }
        int firstSize = list1.size();
        if (firstSize != list2.size()) {
            return false;
        }
        for (int i = 0; i < firstSize; i++) {
            Object o1 = list1.get(i);
            Object o2 = list2.get(i);
            if (o1 == null || !o1.equals(o2)) {
                return false;
            }
        }
        return true;
    }
}
