package com.zj.zhijue.dialog.function;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatButton;

import com.zj.zhijue.R;
import com.zj.zhijue.dialog.CenterScaleDialog;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.zj.zhijue.view.CommonLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  更新固件之前，检测眼镜当前状态，如果不是停止状态，则进行提示
 */
public class UpdateFiremwareStopGlassesDialog extends CenterScaleDialog<UpdateFiremwareStopGlassesDialog> {
    @BindView(R.id.mainrelativelayout)
    RelativeLayout mainLayout;

    @BindView(R.id.stopandupdatefirmwarebtn)
    AppCompatButton mAppCompatButton;

    @BindView(R.id.showstopdialog)
    LinearLayout mShowStopDialog;

    @BindView(R.id.stop_glasses_progress)
    RelativeLayout mStopProgress;

    @BindView(R.id.loadingView)
    CommonLoadingView commonLoadingView;

    private int screenWidth;
    private int screenHeight;

    private DialogButtonClickListener mDialogButtonClickListener;
    private Context mContext;


    public UpdateFiremwareStopGlassesDialog(Context context) {
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
        return R.layout.dialog_app_firemware_update_layout;
    }

    @Override
    public void onCreateData(Context context) {

    }

    public void showStopProgress() {
        commonLoadingView.setMessage("停止眼镜运行...");
        mStopProgress.setVisibility(View.VISIBLE);
        mShowStopDialog.setVisibility(View.GONE);
        show();
    }

    public void showStopTipDialog() {
        mStopProgress.setVisibility(View.GONE);
        mShowStopDialog.setVisibility(View.VISIBLE);
        show();
    }

    @Override
    public void onClick(View v, int id) {
        callBackButtonClickListener(id);

        switch (id) {
            case R.id.stopandupdatefirmwarebtn:
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
