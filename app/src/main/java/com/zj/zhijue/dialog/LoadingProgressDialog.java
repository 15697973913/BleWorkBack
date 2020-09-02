package com.zj.zhijue.dialog;


import android.content.Context;
import android.view.View;
;

public class LoadingProgressDialog extends BaseDialog {
    public LoadingProgressDialog(Context context) {
        super(context);
    }

    public LoadingProgressDialog(Context context, int i) {
        super(context, i);
    }

    @Override
    public int getLayoutRes() {
        return 0;
    }

    @Override
    public void onCreateData(Context context) {

    }

    @Override
    public void onClick(View v, int id) {

    }
}
