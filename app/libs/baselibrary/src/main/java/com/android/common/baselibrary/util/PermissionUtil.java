package com.android.common.baselibrary.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.util.ArrayList;

/**
 * 动态权限工具类
 */
public class PermissionUtil {

    /**
     * 判断是否有某个权限
     * @param context
     * @param permission
     * @return
     */
    public static boolean hasPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 弹出对话框请求权限
     * @param activity
     * @param permissions
     * @param requestCode
     */
    public static void requestPermissons(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(permissions, requestCode);
        }
    }

    /**
     * 返回缺失的权限
     * @param context
     * @param permissions
     * @return
     */
    public static String[] getDeniedPermissions(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> denyPermissionList = new ArrayList<>();
            for (String permisson : permissions) {
                if (context.checkSelfPermission(permisson) != PackageManager.PERMISSION_GRANTED) {
                    denyPermissionList.add(permisson);
                }
            }
            int size = denyPermissionList.size();
            if (size > 0) {
                return denyPermissionList.toArray(new String[size]);
            }
        }
        return null;
    }

    public static void goAppDetailSettingActivity(Context context) {
        try {
            String SCHEME = "package";
            //调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本)
            final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
            //调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2)
            final String APP_PKG_NAME_22 = "pkg";
            //InstalledAppDetails所在包名
            final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
            //InstalledAppDetails类名
            final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

            Intent intent = new Intent();
            final int apiLevel = Build.VERSION.SDK_INT;
            if (apiLevel >= 9) { // 2.3（ApiLevel 9）以上，使用SDK提供的接口
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts(SCHEME, context.getPackageName(), null);
                intent.setData(uri);
            } else { // 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）
                // 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。
                final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22
                        : APP_PKG_NAME_21);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setClassName(APP_DETAILS_PACKAGE_NAME,
                        APP_DETAILS_CLASS_NAME);
                intent.putExtra(appPkgName, context.getPackageName());
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
