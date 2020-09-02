package com.zj.zhijue.dialog;

import android.content.Context;
import android.view.View;

import com.android.common.baselibrary.log.MLog;
import com.zj.zhijue.R;
import com.zj.zhijue.view.CommonLoadingView;
import com.zj.zhijue.view.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

public class SexSelectDialog extends BaseDialog {

    private WheelView wheelView;

    public SexSelectDialog(Context context, OnSelectedListener listener) {
        super(context);

        onSelectedListener = listener;
        initView();

    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_sex_select;
    }

    @Override
    public void onCreateData(Context context) {

    }

    @Override
    public void onClick(View v, int id) {

    }

    private void initView() {
        wheelView = findViewById(R.id.mWheelView);
        wheelView.setOverScrollMode(View.OVER_SCROLL_NEVER);

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
                    onSelectedListener.onSelected(wheelView.getSeletedIndex(), "");
                }
            }
        });


        List<String> datas = new ArrayList<>();
        datas.add("女");
        datas.add("男");

        wheelView.setItems(datas);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                MLog.d("selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });
    }

    private OnSelectedListener onSelectedListener;

    public int getSelectedItemPosition() {
        return wheelView.getSeletedIndex();
    }

    public interface OnSelectedListener {

        void onSelected(int selectedIndex, String item);
    }

    public void setSelection(int selection) {
        wheelView.setSeletion(selection);
    }
}
