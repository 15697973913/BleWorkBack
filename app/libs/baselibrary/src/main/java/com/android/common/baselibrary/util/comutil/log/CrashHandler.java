package com.android.common.baselibrary.util.comutil.log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Process;
import android.util.Log;

import com.android.common.baselibrary.log.SdLogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class CrashHandler implements UncaughtExceptionHandler {
    public static final boolean DEBUG = false;
    private static CrashHandler INSTANCE = null;
    private static final String STACK_TRACE = "STACK_TRACE";
    public static final String TAG = "CrashHandler";
    private static final String VERSION_CODE = "versionCode";
    private static final String VERSION_NAME = "versionName";
    private Context mContext;
    private UncaughtExceptionHandler mDefaultHandler;
    private Properties mDeviceCrashInfo = new Properties();

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    public void init(Context ctx) {
        this.mContext = ctx;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable ex) {
//        if (handleException(ex) || this.mDefaultHandler == null) {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                Log.e(TAG, "Error : ", e);
//            }
//            Process.killProcess(Process.myPid());
//            System.exit(10);
//            return;
//        }
//        this.mDefaultHandler.uncaughtException(thread, ex);
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            Log.w(TAG, "handleException --- ex==null");
            return true;
        } else if (ex.getLocalizedMessage() == null) {
            return false;
        } else {
            collectCrashDeviceInfo(this.mContext);
            saveCrashInfoToFile(ex);
            return true;
        }
    }

    private String saveCrashInfoToFile(Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);
        for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        String result = info.toString();
        printWriter.close();
        this.mDeviceCrashInfo.put("EXEPTION", ex.getLocalizedMessage());
        this.mDeviceCrashInfo.put(STACK_TRACE, result);
        try {
            File logFile = new File(getCrashLogFileName());
            prepareLogFile(logFile);
            FileOutputStream trace = new FileOutputStream(logFile, true);
            this.mDeviceCrashInfo.store(trace, "");
            trace.flush();
            trace.close();
            return logFile.getPath();
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing report file...", e);
            return null;
        }
    }

    public void collectCrashDeviceInfo(Context ctx) {
        try {
            PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                this.mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);
                this.mDeviceCrashInfo.put(VERSION_CODE, "" + pi.versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "Error while collect package info", e);
        }
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                this.mDeviceCrashInfo.put(field.getName(), "" + field.get(null));
            } catch (Exception e2) {
                Log.e(TAG, "Error while collect crash info", e2);
            }
        }
    }

    private static void prepareLogFile(File logFile) throws IOException {
        if (!logFile.exists()) {
            File parent = logFile.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IOException("Fail to make directories");
            } else if (!logFile.createNewFile()) {
                throw new IOException("Failed to create file " + logFile.getPath());
            }
        }
    }

    public static String getCrashLogFileName() {
        String dateSection = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
        return SdLogUtil.getLogDirPath() + dateSection + "/crash_" + dateSection + ".log";
    }
}
