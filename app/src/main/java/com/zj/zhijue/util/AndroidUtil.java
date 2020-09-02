package com.zj.zhijue.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Build.VERSION;
import androidx.core.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.android.common.baselibrary.util.comutil.SDInfo;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

public class AndroidUtil {
    public static final int PROGRESS_MAX_VALUE = 100;

    private static String getPublicKey(Context applicationContext, String pkgName) {
        try {
            if (TextUtils.isEmpty(pkgName)) {
                pkgName = applicationContext.getPackageName();
            }
            return ((RSAPublicKey) ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(applicationContext.getPackageManager().getPackageInfo(pkgName, 64).signatures[0].toByteArray()))).getPublicKey()).getModulus().toString(16);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getPublicKey(Context applicationContext) {
        return getPublicKey(applicationContext, null);
    }

    public static boolean checkApkCorrect(Context applicationContext, String path, int versionCode) {
        try {
            PackageInfo pi = applicationContext.getPackageManager().getPackageArchiveInfo(path, 0);
            if (pi != null && versionCode == pi.versionCode) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    public static int getVersionCode(Context applicationContext) {
        try {
            return applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 16384).versionCode;
        } catch (NameNotFoundException e) {
            return 0;
        }
    }

    public static String getVersionName(Context applicationContext, String pkgName) {
        try {
            return applicationContext.getPackageManager().getPackageInfo(pkgName, 16384).versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    public static String getVersionName(Context applicationContext) {
        try {
            return applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 16384).versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    public static int[] getScreenParams(Context applicationContext) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) applicationContext.getSystemService("window")).getDefaultDisplay().getMetrics(dm);
            int widthPixels = dm.widthPixels;
            int heightPixels = dm.heightPixels;
            return new int[]{widthPixels, heightPixels};
        } catch (Exception e) {
            return new int[]{0, 0};
        }
    }

    public static int dipToPixels(Context applicationContext, int dip) {
        return (int) TypedValue.applyDimension(1, (float) dip, applicationContext.getResources().getDisplayMetrics());
    }

    public static int px2dip(Context applicationContext, float pxValue) {
        return (int) ((pxValue / applicationContext.getResources().getDisplayMetrics().density) + 0.5f);
    }


    public static boolean isAppIsInBackground(Context context, String packageName) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        if (VERSION.SDK_INT > 20) {
            for (RunningAppProcessInfo processInfo : am.getRunningAppProcesses()) {
                if (processInfo.importance == 100) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(packageName)) {
                            isInBackground = false;
                        }
                    }
                }
            }
            return isInBackground;
        } else if (((RunningTaskInfo) am.getRunningTasks(1).get(0)).topActivity.getPackageName().equals(context.getPackageName())) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isTopActivitySelf(Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        List<RunningTaskInfo> tasksInfo = ((ActivityManager) context.getApplicationContext().getSystemService("activity")).getRunningTasks(1);
        if (tasksInfo.size() <= 0 || !packageName.equals(((RunningTaskInfo) tasksInfo.get(0)).topActivity.getPackageName())) {
            return false;
        }
        return true;
    }

    public static String uriToFilePath(Activity context, Uri uri) {
        Cursor actualimagecursor = context.managedQuery(uri, new String[]{"_data"}, null, null, null);
        int actualImageColumnIndex = actualimagecursor.getColumnIndexOrThrow("_data");
        actualimagecursor.moveToFirst();
        return actualimagecursor.getString(actualImageColumnIndex);
    }

    public static String getDeviceId(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return ((TelephonyManager) context.getApplicationContext().getSystemService("phone")).getDeviceId();
        }
        return null;
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        try {
            if (!ViewConfiguration.get(context).hasPermanentMenuKey()) {
                return 0;
            }
            Resources resources = context.getResources();
            int visibleResId = resources.getIdentifier("config_showNavigationBar", "bool", "android");
            if (visibleResId != 0 && resources.getBoolean(visibleResId)) {
                result = resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", "dimen", "android"));
            }
            return result;
        } catch (Exception e) {
        }
        return result;
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo state : info) {
                    if (state.getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isForeground(Context context) {
        for (RunningAppProcessInfo appProcess : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == 100) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static boolean hasPermission(Context context, String item) {
        if (context.getPackageManager().checkPermission(item, context.getPackageName()) == 0) {
            return true;
        }
        return false;
    }

    public static boolean checkSdcardSpace() {
        if (SDInfo.hasSDCard()) {
            if (SDInfo.hasEnoughAvailableSize()) {
                return true;
            }
        }
        if (SDInfo.hasEnoughAvailableSizeInternal()) {
            return true;
        }
        return false;
    }
}
