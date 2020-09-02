package com.zj.zhijue.dialog;

import android.content.Context;
import android.view.View;

import com.zj.zhijue.R;
import com.zj.zhijue.view.CommonLoadingView;

public class DefaultLoadingDialog extends BaseDialog {

    CommonLoadingView commonLoadingView;

    public DefaultLoadingDialog(Context context) {
        super(context);
        initView();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_default_view;
    }

    @Override
    public void onCreateData(Context context) {

    }

    @Override
    public void onClick(View v, int id) {

    }

    private void initView() {
        commonLoadingView = rootView.findViewById(R.id.loadingView);
        commonLoadingView.load();
    }

    public void setMessage(String msg) {
        commonLoadingView.setMessage(msg);
    }

}
