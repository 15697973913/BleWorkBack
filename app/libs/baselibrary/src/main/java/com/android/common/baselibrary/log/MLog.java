package com.android.common.baselibrary.log;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.shitec.bleglasses.baselibrary.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class MLog {

    public static final String TAG = "bleGlassesTag";

    private final static String timeformat_s = "HH:mm:ss:SSS";
    public final static SimpleDateFormat sdft = new SimpleDateFormat(timeformat_s);
    public static String customTagPrefix = TAG; // 自定义Tag的前缀，可以是作者名
    private static final boolean isSaveLog = false; // 是否把保存日志到SD卡中
    public static final String ROOT = Environment.getExternalStorageDirectory()
            .getPath() + "/" + customTagPrefix + "/"; // SD卡中的根目录
    private static final String PATH_LOG_INFO = ROOT + "info/";
    private final static String null_s = new String(new byte[]{110, 117, 108, 108});//null

    // 容许打印日志的类型，默认是true，设置为false则不打印
    public static boolean allowD = true;
    public static boolean allowE = true;
    public static boolean allowI = true;
    public static boolean allowV = true;
    public static boolean allowW = true;
    public static boolean allowWtf = true;

    private MLog() {
    }

    static {
        if (!BuildConfig.DEBUG) {
            allowD = false;
            allowE = false;
            allowI = true;
            allowV = false;
            allowW = false;
            allowWtf = false;
        }
    }


    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(Line:%d)"; // 占位符
        String callerClazzName = caller.getClassName(); // 获取到类名
        callerClazzName = callerClazzName.substring(callerClazzName
                .lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(),
                caller.getLineNumber()); // 替换
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":"
                + tag;
        return tag;
    }

    /**
     * 自定义的logger
     */
    public static CustomLogger customLogger;

    public interface CustomLogger {
        void d(String tag, String content);

        void d(String tag, String content, Throwable tr);

        void e(String tag, String content);

        void e(String tag, String content, Throwable tr);

        void i(String tag, String content);

        void i(String tag, String content, Throwable tr);

        void v(String tag, String content);

        void v(String tag, String content, Throwable tr);

        void w(String tag, String content);

        void w(String tag, String content, Throwable tr);

        void w(String tag, Throwable tr);

        void wtf(String tag, String content);

        void wtf(String tag, String content, Throwable tr);

        void wtf(String tag, Throwable tr);
    }

    public static void i(String content) {

        if (null == content) content = null_s;
        //Log.i(mytag, "["+sdft.format(new Date(System.currentTimeMillis()))+"]"+content);


        if (!allowI)
            return;
        if (null == content) {
            content = null_s;
        }

        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.i(tag, content);
        } else {
            Log.i(tag, content);
        }
        if (isSaveLog) {
            point(PATH_LOG_INFO, tag, content);
        }
    }


    public static void i(Object object) {

        if (!allowI)
            return;

        String content = null;

        if (null != object) {
            content = object.toString();
        }

        if (null == content) {
            content = null_s;
        }

        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.i(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        } else {
            Log.i(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        }

    }

    public static void d(Integer paramint) {

        if (!allowD)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.d(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + String.valueOf(paramint));
        } else {
            Log.d(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + String.valueOf(paramint));
        }

    }

    public static void d(long paramlong) {
        d(String.valueOf(paramlong));
    }

    public static void d(int paramint) {
        d(String.valueOf(paramint));
    }

    public static void d(boolean paramboolean) {
        d(String.valueOf(paramboolean));
    }

    public static void d(String content) {

        i(content);

        if (!allowD)
            return;
        if (null == content)
            content = null_s;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.d(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        } else {
            Log.d(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        }
    }

    public static void d(Object object) {

        if (!allowD)
            return;

        String content = null;

        if (null != object) {
            content = object.toString();
        }

        if (null == content)
            content = null_s;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.d(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        } else {
            Log.d(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        }
    }

    public static void d(String content, Throwable tr) {
        if (!allowD)
            return;
        if (null == content)
            content = null_s;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.d(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content, tr);
        } else {
            Log.d(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content, tr);
        }
    }

    public static void e(Throwable content) {

        if (!allowE)
            return;

        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.e(tag, content.getMessage());
        } else {
            Log.e(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + (null != content.getMessage() ? content.getMessage() : ""));
        }
        if (isSaveLog) {
            point(PATH_LOG_INFO, tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + (null != content.getMessage() ? content.getMessage() : ""));
        }
    }

    public static void e(String content, Throwable tr) {

        if (!allowE)
            return;
        if (null == content)
            content = null_s;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.e(tag, content, tr);
        } else {
            Log.e(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content, tr);
        }
        if (isSaveLog) {
            point(PATH_LOG_INFO, tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + (null != tr.getMessage() ? tr.getMessage() : ""));
        }
    }

    public static void e(String content) {

        if (!allowE)
            return;
        if (null == content)
            content = null_s;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.e(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        } else {
            Log.e(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        }

    }


    public static void i(String tagParam, String content) {
        if (!allowI)
            return;
        if (null == content)
            content = null_s;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.i(tag + tagParam, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        } else {
            Log.i(tag + tagParam, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        }

    }

    public static void v(String content) {
        if (!allowV)
            return;
        if (null == content)
            content = null_s;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.v(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        } else {
            Log.v(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        }
    }

    public static void v(String content, Throwable tr) {
        if (!allowV)
            return;
        if (null == content)
            content = null_s;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.v(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content, tr);
        } else {
            Log.v(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content, tr);
        }
    }

    public static void w(String content) {

        if (!allowW)
            return;
        if (null == content)
            content = null_s;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.w(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        } else {
            Log.w(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        }
    }

    public static void w(String content, Throwable tr) {
        if (!allowW)
            return;
        if (null == content)
            content = null_s;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.w(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content, tr);
        } else {
            Log.w(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content, tr);
        }
    }

    public static void w(Throwable tr) {
        if (!allowW)
            return;

        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.w(tag, tr);
        } else {
            Log.w(tag, tr);
        }
    }

    public static void wtf(String content) {
        if (null == content)
            content = null_s;
        if (!allowWtf)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.wtf(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        } else {
            Log.wtf(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content);
        }
    }

    public static void wtf(String content, Throwable tr) {
        if (!allowWtf)
            return;
        if (null == content)
            content = null_s;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.wtf(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content, tr);
        } else {
            Log.wtf(tag, "[" + sdft.format(new Date(System.currentTimeMillis())) + "]" + content, tr);
        }
    }

    public static void wtf(Throwable tr) {
        if (!allowWtf)
            return;

        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.wtf(tag, tr);
        } else {
            Log.wtf(tag, tr);
        }
    }

    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static void point(String path, String tag, String msg) {
        if (isSDAva()) {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("",
                    Locale.SIMPLIFIED_CHINESE);
            dateFormat.applyPattern("yyyy");
            path = path + dateFormat.format(date) + "/";
            dateFormat.applyPattern("MM");
            path += dateFormat.format(date) + "/";
            dateFormat.applyPattern("dd");
            path += dateFormat.format(date) + ".log";
            dateFormat.applyPattern("[yyyy-MM-dd HH:mm:ss]");
            String time = dateFormat.format(date);
            File file = new File(path);
            if (!file.exists())
                createDipPath(path);
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file, true)));
                out.write(time + " " + tag + " " + msg + " ");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据文件路径 递归创建文件
     *
     * @param file
     */
    public static void createDipPath(String file) {
        String parentFile = file.substring(0, file.lastIndexOf("/"));
        File file1 = new File(file);
        File parent = new File(parentFile);
        if (!file1.exists()) {
            parent.mkdirs();
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A little trick to reuse a formatter in the same thread
     */
    private static class ReusableFormatter {

        private Formatter formatter;
        private StringBuilder builder;

        public ReusableFormatter() {
            builder = new StringBuilder();
            formatter = new Formatter(builder);
        }

        public String format(String msg, Object... args) {
            formatter.format(msg, args);
            String s = builder.toString();
            builder.setLength(0);
            return s;
        }
    }

/*    private static final ThreadLocal<ReusableFormatter> thread_local_formatter = new ThreadLocal<ReusableFormatter>() {
        protected ReusableFormatter initialValue() {
            return new ReusableFormatter();
        }
    };

    public static String format(String msg, Object... args) {
        ReusableFormatter formatter = thread_local_formatter.get();
        return formatter.format(msg, args);
    }*/

    public static boolean isSDAva() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)
                || Environment.getExternalStorageDirectory().exists()) {
            return true;
        } else {
            return false;
        }
    }
    /*
     * -assumenosideeffects class LogUtils{ <fields> ; public
     * static void d(...); public static void w(...); public static void e(...);
     * public static void wtf(...); private static StackTraceElement
     * getCallerStackTraceElement(); public static void point(...); public
     * static void createDipPath(...); private static String generateTag(...);
     * private static class ReusableFormatter; public interface CustomLogger;
     * private static final ThreadLocal<*> *; public static String format(...);
     * public static boolean isSDAva(); }
     */
}