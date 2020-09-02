package com.zj.zhijue.dialog.function;

import android.content.Context;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.view.View;
import android.widget.RelativeLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.dialog.CenterScaleDialog;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.util.view.ui.DeviceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 固件更新版本提示
 */
public class FirmwareUpdateDialog extends CenterScaleDialog<FirmwareUpdateDialog> {
    @BindView(R.id.mainrelativelayout)
    RelativeLayout mainLayout;

    @BindView(R.id.appupdateveriontv)
    AppCompatTextView contentTextView;

    @BindView(R.id.noupdatenewappversionbtn)
    AppCompatButton noUpdateButton;

    @BindView(R.id.updatenewappversionbtn)
    AppCompatButton updateButton;

    private int screenWidth;
    private int screenHeight;

    private DialogButtonClickListener mDialogButtonClickListener;

    private Context mContext;
    private String newVersion;
    private boolean isNewVersion;

    public FirmwareUpdateDialog(Context context) {
        super(context);
        init();
    }

    public FirmwareUpdateDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    private void init() {
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
        return R.layout.dialog_app_update_layout;
    }

    @Override
    public void onCreateData(Context context) {

    }

    public void showTip(String content) {
        contentTextView.setText(content);
        if (isNewVersion) {
            updateButton.setVisibility(View.VISIBLE);
            noUpdateButton.setVisibility(View.GONE);
        } else {
            updateButton.setVisibility(View.GONE);
            noUpdateButton.setVisibility(View.VISIBLE);
        }
        show();
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public void setNewVersion(boolean newVersion) {
        isNewVersion = newVersion;
    }

    @Override
    public void onClick(View v, int id) {
        callBackButtonClickListener(id);

        switch (id) {
            case R.id.updatenewappversionbtn:
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
