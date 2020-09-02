package com.zj.zhijue.util;

import android.content.Context;

public class MyGlobal {
    private static int UpdateBinRunMode = 0;

    public static int GetUpdateBinRunMode(Context con) {
        return UpdateBinRunMode;
    }

    public static void SetUpdateBinRunMode(Context con, int v) {
        UpdateBinRunMode = v;
    }
}
