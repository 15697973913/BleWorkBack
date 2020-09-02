package com.zj.zhijue.callback;

import android.app.Dialog;


public interface OnDialogButtonClickListener<D extends Dialog> {
    public void onDialogClick(int buttonId, String buttonText);
}
