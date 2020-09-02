package com.zj.zhijue.ble;

import android.content.Context;

import com.zj.zhijue.activity.MainActivity;

/**
 * Created by Administrator on 2018/6/12.
 */

public class BleBizMainHelper {

    public static String processUploadData() {
        return "需要上传数据";
    }

    public static void handleUploadData(Context context, String str) {

    }

    public static void handleRunTime(Context context, String str) {
        if (str != null) {
            if (!"".equals(str)) {

            }
        }
    }

    public static String processRunTime(Context context) {
        String str = "不需要";

        return str;
    }

    public static void handleTask(Context context, String str) {
        if (str != null) {
            if (!"".equals(str)) {

            }
        }
    }

    private static boolean checkTaskByName(MainActivity mainActivity, String str) {
        return false;
    }

    public static String processTask(Context context, int i) {

        return null;
    }

    public static String processTask_dep(Context context) {
        return null;
    }

    public static boolean checkTaskState(Context context) {

        return false;
    }

}
