package com.android.common.baselibrary.util.comutil;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipBoard {
    private static ClipboardManager clipboardManager = null;

    public static void init(Context context) {
        clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
    }

    public static synchronized void setText(String text) {
        synchronized (ClipBoard.class) {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("text", text));
        }
    }
}
