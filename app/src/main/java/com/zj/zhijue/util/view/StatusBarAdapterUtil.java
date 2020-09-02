package com.zj.zhijue.util.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;


import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.log.SdLogUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.zj.zhijue.util.view.ui.OSUtils;
import com.zj.zhijue.util.view.ui.Rom;

import java.lang.reflect.Method;

public class StatusBarAdapterUtil {

    public static void init(Activity activity, boolean forceFullScreen) {

        if (forceFullScreen) {
            StatusBarUtils.with(activity).init();
            return;
        }

        if (OSUtils.isEMUI()) {
            if (!hasNotchInScreen(activity.getApplicationContext())) {
                /**
                 * 没有刘海屏幕的情况
                 */
                StatusBarUtils.with(activity).init();
            }
        } else if (Rom.isOppo()) {
                if (!hasNotchInOppo(activity.getApplicationContext())) {
                    /**
                     * 没有刘海屏幕的情况
                     */
                    StatusBarUtils.with(activity).init();
                }
        } else if (Rom.isVivo()) {
                if (!hasNotchInScreenAtVoio(activity.getApplicationContext())) {
                    /**
                     * 没有刘海屏幕的情况
                     */
                    StatusBarUtils.with(activity).init();
                }
        } else if (Rom.isMiui()) {
            if (!hasNotchScreenAtXiaoMI()) {
                /**
                 * 没有刘海屏幕的情况
                 */
                StatusBarUtils.with(activity).init();
            }
        } else {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {//小于Android7.1(25)
                StatusBarUtils.with(activity).init();
            }
        }
    }

    public static boolean isNotchInScreen(Context activity, boolean forceFullScreen) {


        if (OSUtils.isEMUI()) {
            if (!hasNotchInScreen(activity.getApplicationContext())) {
                /**
                 * 没有刘海屏幕的情况
                 */
               return false;
            }
        } else if (Rom.isOppo()) {
            if (!hasNotchInOppo(activity.getApplicationContext())) {
                /**
                 * 没有刘海屏幕的情况
                 */
                return false;
            }
        } else if (Rom.isVivo()) {
            if (!hasNotchInScreenAtVoio(activity.getApplicationContext())) {
                /**
                 * 没有刘海屏幕的情况
                 */
                return false;
            }
        } else if (Rom.isMiui()) {
            if (!hasNotchScreenAtXiaoMI()) {
                /**
                 * 没有刘海屏幕的情况
                 */
                return false;
            }
        } else {
            if (forceFullScreen) {
                return false;
            }

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {//小于Android7.1(25)
                return false;
            }
        }

        if (forceFullScreen) {
            return false;
        }

        return true;
    }

    public static boolean hasNotchInScreen(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            SdLogUtil.writeExceptionLog(e);
        } catch (NoSuchMethodException e) {
            SdLogUtil.writeExceptionLog(e);
        } catch (Exception e) {
            SdLogUtil.writeExceptionLog(e);
        } finally {
            return ret;
        }
    }

    /**
     * OPPO 手机判断是否有刘海屏
     *
     * @param context
     * @return
     */
    public static boolean hasNotchInOppo(Context context) {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    public static final int NOTCH_IN_SCREEN_VOIO = 0x00000020;//是否有凹槽
    public static final int ROUNDED_IN_SCREEN_VOIO = 0x00000008;//是否有圆角

    /**
     * VIVO 手机是否有刘海屏
     * @param context
     * @return
     */
    public static boolean hasNotchInScreenAtVoio(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class FtFeature = cl.loadClass("com.util.FtFeature");
            Method get = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) get.invoke(FtFeature, NOTCH_IN_SCREEN_VOIO);

        } catch (ClassNotFoundException e) {
           MLog.e("hasNotchInScreen ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            MLog.e("hasNotchInScreen NoSuchMethodException");
        } catch (Exception e) {
            MLog.e("hasNotchInScreen Exception");
        } finally {
            return ret;
        }
    }

    public static boolean hasNotchScreenAtXiaoMI() {
        String propery = OSUtils.getSystemProperty("ro.miui.notch", String.valueOf(0));
        if (!CommonUtils.isEmpty(propery)) {
            if (propery.trim().equals("1")) {
                return true;
            }
        }
        return false;
    }

}
