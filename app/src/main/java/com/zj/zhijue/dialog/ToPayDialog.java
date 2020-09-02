package com.zj.zhijue.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.common.baselibrary.log.MLog;
import com.zj.zhijue.R;
import com.zj.zhijue.view.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * 无可用时长，请充值！
 */
public class ToPayDialog extends BaseDialog {

    private TextView tvCancel, tvConfirm;

    public ToPayDialog(Context context, OnSelectedListener listener) {
        super(context);

        onSelectedListener = listener;
        initView();

    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_to_pay;
    }

    @Override
    public void onCreateData(Context context) {

    }

    @Override
    public void onClick(View v, int id) {

    }

    private void initView() {

        findViewById(R.id.rlContent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.tvConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onSelectedListener != null) {
                    onSelectedListener.onSelected(1, "");
                }
            }
        });

    }

    private OnSelectedListener onSelectedListener;

    public interface OnSelectedListener {

        void onSelected(int selectedIndex, String item);
    }

}
