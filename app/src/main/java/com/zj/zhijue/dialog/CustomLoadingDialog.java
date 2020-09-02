package com.zj.zhijue.dialog;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ProgressBar;

import com.zj.zhijue.R;
import com.zj.zhijue.view.CommonLoadingView;

public class CustomLoadingDialog extends BaseDialog {

    CommonLoadingView commonLoadingView;
    private Context mContext;

    public CustomLoadingDialog(Context context) {
        super(context);
        mContext = context;
        initView(context);
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

    private void initView(Context context) {
        commonLoadingView = rootView.findViewById(R.id.loadingView);
        ProgressBar progressBar = new ProgressBar(context);
        progressBar.getIndeterminateDrawable().setColorFilter(mContext.getResources().getColor(R.color.right_eye_sight_color), PorterDuff.Mode.SRC_IN);
        commonLoadingView.setLoadingView(progressBar);
        commonLoadingView.load();
    }

    public void setMessage(String msg) {
        commonLoadingView.setMessage(msg);
    }
}
