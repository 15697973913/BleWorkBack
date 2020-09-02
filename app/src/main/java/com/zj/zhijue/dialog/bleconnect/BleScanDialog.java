package com.zj.zhijue.dialog.bleconnect;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.dialog.CenterScaleDialog;
import com.zj.zhijue.util.view.ui.DeviceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 手动输入，或者扫描二维码的提示 Dialog
 */
public class BleScanDialog extends CenterScaleDialog<BleScanDialog> {
    @BindView(R.id.mainrelativelayout)
    RelativeLayout mainLayout;

    private int screenWidth;
    private int screenHeight;

    private DialogButtonClickListener mDialogButtonClickListener;

    private Context mContext;

    public BleScanDialog(Context context) {
        super(context);
        setCancelable(true);
        ButterKnife.bind(this);
        mContext = context;
        initView(context);
        resetSize();
    }

    private void initView(Context context) {
        int[] wh = DeviceUtils.getDeviceSize(context);
        screenWidth = wh[0];
        screenHeight = wh[1];
    }

    private void resetSize() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainLayout.getLayoutParams();
        layoutParams.width = screenWidth * 4 / 5;
        layoutParams.height = screenHeight * 2 / 5;
        mainLayout.setLayoutParams(layoutParams);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_scan_or_input_tip_layout;
    }

    @Override
    public void onCreateData(Context context) {

    }

    public void showTip() {
        show();
    }

    @Override
    public void onClick(View v, int id) {
        callBackButtonClickListener(id);

        switch (id) {

            case R.id.blescanbtn:
                dismiss();
                break;

            case R.id.searchbledevicebtn:
                dismiss();
                break;

            case R.id.inputdeviceidbtn:
                dismiss();
                break;

            default:
                break;
        }
    }

    public void setDialogButtonClickListener(DialogButtonClickListener dialogButtonClickListener) {
        mDialogButtonClickListener = dialogButtonClickListener;
    }

    private void callBackButtonClickListener(int resourceId) {
        if (null != mDialogButtonClickListener) {
            mDialogButtonClickListener.onButtonClick(resourceId);
        }
    }
}
