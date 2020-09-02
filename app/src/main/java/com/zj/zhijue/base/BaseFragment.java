package com.zj.zhijue.base;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.common.baselibrary.util.ToastUtil;
import com.zj.common.http.retrofit.netutils.NetUtil;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.bean.bledata.send.SendUserInfoControlBleCmdBean;
import com.zj.zhijue.dialog.CustomLoadingDialog;
import com.zj.zhijue.dialog.DefaultLoadingDialog;
import com.zj.zhijue.dialog.function.BatteryLowTipDialog;
import com.zj.zhijue.event.CmdBleDataEvent;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.model.GlassesBleDataModel;
import com.zj.zhijue.util.Config;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/11/16.
 */

public class BaseFragment extends Fragment {
    public static final int OPERATION_BLE_DEVICE_STOP_INDEX = 0;
    public static final int OPERATION_BLE_DEVICE_PAUSE_INDEX = 1;
    public static final int OPERATION_BLE_DEVICE_INTERVENE_INDEX = 2;

    private CustomLoadingDialog mCustomCircleLoadingDialog;
    private DefaultLoadingDialog mDefaultLoadingDialog;
    private BatteryLowTipDialog mBatteryLowTipDialog;




    protected void initOperationDropButton() {


    }

    protected void initListener() {


    }


    protected void onOperationDropDownItemSelected(int position) {
        switch (position) {
            case OPERATION_BLE_DEVICE_STOP_INDEX:
                doStopOperation();
                break;

            case OPERATION_BLE_DEVICE_PAUSE_INDEX:
                doPauseOperation();
                break;

            case OPERATION_BLE_DEVICE_INTERVENE_INDEX:
                doInterveneOperation();
                break;

            default:
                break;
        }
    }


    /**
     * 停止
     */
    private void doStopOperation() {
        ToastUtil.showShortToast("停止");
    }

    /**
     * 暂停
     */
    private void doPauseOperation() {
        ToastUtil.showShortToast("暂停");
    }

    /**
     * 干预
     */
    private void doInterveneOperation() {
        ToastUtil.showShortToast("干预");
    }

    protected void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    protected void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SdLogUtil.writeCommonLog("[" + this.getClass().getSimpleName() + "] BaseFragment onCreate(Bundle savedInstanceState) savedInstanceState = " + savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        //SdLogUtil.writeCommonLog("[" + this.getClass().getSimpleName() + "] BaseFragment onStart()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //SdLogUtil.writeCommonLog("[" + this.getClass().getSimpleName() + "] BaseFragment onSaveInstanceState() outState = " + outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //SdLogUtil.writeCommonLog("[" + this.getClass().getSimpleName() + "] BaseFragment onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        //SdLogUtil.writeCommonLog("[" + this.getClass().getSimpleName() + "] BaseFragment onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        //SdLogUtil.writeCommonLog("[" + this.getClass().getSimpleName() + "] BaseFragment onStop()");
    }

    @Override
    public void onDestroy() {
        dismissAllDialog();
        super.onDestroy();
        //SdLogUtil.writeCommonLog("[" + this.getClass().getSimpleName() + "] BaseFragment onDestroy()");
    }

    private void dismissAllDialog() {
        dismissCustomCommonCircleLodingDialog();
        dismissDefualtBlackBackGroundCircleLoadingDialog();
        dismissBatteryLowDialog();
    }

    protected boolean checkNetworkAvaliable() {
        if (NetUtil.isNetworkAvalible(MyApplication.getInstance())) {
            return true;
        }
        ToastUtil.showLong(R.string.no_network_error_tip);
        return false;
    }

    protected void showCustomCommonCircleLodingDialog() {
        if (null == mCustomCircleLoadingDialog) {
            mCustomCircleLoadingDialog = new CustomLoadingDialog(getActivity());
        }
        mCustomCircleLoadingDialog.show();
    }

    protected void dismissCustomCommonCircleLodingDialog() {
        if (null != mCustomCircleLoadingDialog) {
            mCustomCircleLoadingDialog.dismiss();
        }
    }

    protected void showDefualtBlackBackGroundCircleLodingDialog() {
        if (null == mDefaultLoadingDialog) {
            mDefaultLoadingDialog = new DefaultLoadingDialog(getActivity());
        }
        mDefaultLoadingDialog.show();
    }

    protected void dismissDefualtBlackBackGroundCircleLoadingDialog() {
        if (null != mDefaultLoadingDialog) {
            mDefaultLoadingDialog.dismiss();
        }
    }

    /**
     * 电量过低时，进行提示
     */
    protected void showBatterLowTipDialog() {
        if (null == mBatteryLowTipDialog) {
            mBatteryLowTipDialog = new BatteryLowTipDialog(getActivity());
            mBatteryLowTipDialog.setDialogButtonClickListener(new DialogButtonClickListener() {
                @Override
                public void onButtonClick(int resourceId) {
                    Config.getConfig().saveBatteryLowTip(false);
                    if (resourceId == R.id.batterylowstopbtn) {
                        sendStopCmd();
                        dismissBatteryLowDialog();
                    }
                }
            });
        }
        mBatteryLowTipDialog.show();
    }

    protected void dismissBatteryLowDialog() {
        if (null != mBatteryLowTipDialog) {
            mBatteryLowTipDialog.dismiss();
        }
    }

    private void sendStopCmd() {
        GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
        EventBus.getDefault().post(new CmdBleDataEvent(getStopActionOrder()));
    }

    private byte[] getStopActionOrder() {
        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.STOP_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }
}
