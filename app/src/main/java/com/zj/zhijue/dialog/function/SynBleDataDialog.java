package com.zj.zhijue.dialog.function;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.dialog.CenterScaleDialog;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.model.GlassesBleDataModel;
import com.zj.zhijue.model.TrainModel;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.zj.zhijue.view.CommonLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * APP 和眼镜同步数据的 Dialog
 */
public class SynBleDataDialog extends CenterScaleDialog<SynBleDataDialog> {
    @BindView(R.id.mainrelativelayout)
    RelativeLayout mainLayout;

    @BindView(R.id.syn_bledata_progress)
    RelativeLayout synBleDataProgress;

    @BindView(R.id.syn_bledata_userdiff)
    LinearLayout synBleDataUserDiff;

    @BindView(R.id.syn_bledata_fail)
    LinearLayout synBleDataFail;

    @BindView(R.id.showcurrrentusercodetv)
    AppCompatTextView currentUsercodTv;

    @BindView(R.id.loadingView)
    CommonLoadingView mCommonLoadingView;

    @BindView(R.id.synuserinfo2glassbtn)
    AppCompatButton synUserInfoBtn;

    @BindView(R.id.disconnectglassessbtn)
    AppCompatButton synCancelBtn;

    private int screenWidth;
    private int screenHeight;

    private DialogButtonClickListener mDialogButtonClickListener;

    private Context mContext;
    private String newVersion;
    private boolean isNewVersion;

    public SynBleDataDialog(Context context) {
        super(context);
        setCancelable(false);
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
        return R.layout.dialog_app_syn_bledata_layout;
    }

    @Override
    public void onCreateData(Context context) {

    }

    public void showCurrentUserCode(long userCode, String glassStatus) {
        String tipContent = String.format(mContext.getResources().getString(R.string.show_diff_user_tip_text), userCode);
        currentUsercodTv.setText(tipContent);
    }

    public void showSynText(String synTexst) {
        mCommonLoadingView.setMessage(synTexst);
    }

    public void showSynDialog(boolean showSyn) {
        if (showSyn) {
            synBleDataProgress.setVisibility(View.VISIBLE);
            synBleDataUserDiff.setVisibility(View.GONE);
            synBleDataFail.setVisibility(View.GONE);
        } else {
            synBleDataProgress.setVisibility(View.GONE);
            synBleDataUserDiff.setVisibility(View.VISIBLE);
            synBleDataFail.setVisibility(View.GONE);
        }
        show();
    }

    public void showSynFailDialog() {
        synBleDataFail.setVisibility(View.VISIBLE);
        synBleDataProgress.setVisibility(View.GONE);
        synBleDataUserDiff.setVisibility(View.GONE);
        show();
    }

    @Override
    public void onClick(View v, int id) {
        callBackButtonClickListener(id);

        switch (id) {
            case R.id.disconnectglassessbtn:
                disconnectBle();
                dismiss();
                break;
            case R.id.synfailcancelbtn:
                dismiss();
                logoutAgain();
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

    private void logoutAgain() {
        Config.getConfig().savePasswd(null);
        Config.getConfig().saveFreshToken(null);
        Config.getConfig().saveAccessToken(null);
        BleDeviceManager.getInstance().stopScan();
        BleDeviceManager.getInstance().stopScanByMac();
        BleDeviceManager.getInstance().disconnectGlassesBleDevice(true);
        GlassesBleDataModel.getInstance().clearModelData();
        TrainModel.getInstance().clearTrainModelData();

        BaseActivity.GotoLoginActivity();
    }

    private void disconnectBle() {
        BleDeviceManager.getInstance().stopScan();
        BleDeviceManager.getInstance().stopScanByMac();
        BleDeviceManager.getInstance().disconnectGlassesBleDevice(true);
    }
}
