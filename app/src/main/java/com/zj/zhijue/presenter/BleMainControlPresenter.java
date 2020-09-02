package com.zj.zhijue.presenter;

import androidx.annotation.NonNull;

import com.android.common.baselibrary.util.ToastUtil;
import com.zj.zhijue.R;
import com.zj.zhijue.bean.bledata.send.SendUserInfoBleCmdBean;
import com.zj.zhijue.bean.bledata.send.SendUserInfoControlBleCmdBean;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.contracts.BleMainControlContract;
import com.zj.zhijue.data.source.BleGlassesRespository;
import com.zj.zhijue.event.CmdBleDataEvent;
import com.zj.zhijue.http.response.HttpResponseGlassInitDataBackBean;
import com.zj.zhijue.model.GlassesBleDataModel;
import com.zj.zhijue.model.TrainModel;
import com.vise.baseble.model.BluetoothLeDevice;

import org.greenrobot.eventbus.EventBus;

public class BleMainControlPresenter implements BleMainControlContract.Presenter {
    private final BleGlassesRespository bleGlassesRespository;
    private final BleMainControlContract.View bleMainControlContractView;
    private BleDeviceManager bleDeviceManager;

    public BleMainControlPresenter(@NonNull BleGlassesRespository bleGlassesRespository, @NonNull BleMainControlContract.View bleMainControlContractView) {
        this.bleGlassesRespository = bleGlassesRespository;
        this.bleMainControlContractView = bleMainControlContractView;
        bleMainControlContractView.setPresenter(this);
        bleDeviceManager = BleDeviceManager.getInstance();
        initData();
    }

    @Override
    public void initData() {

    }

    @Override
    public void sendUserInfoWithTrainMode(int trainMode) {
        synchronized (bleDeviceManager) {
            if (!checkBleConnected()) {
                return;
            }
            GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
            sendUserInfoAgain(trainMode);
            bleMainControlContractView.changeBleGlassesConnectStatus(false);
            bleMainControlContractView.changeBleLightConnectStatus(false);
        }

    }

    @Override
    public void startScan() {
        BleDeviceManager.getInstance().startScan(null);
    }

    @Override
    public void stopScan() {
        BleDeviceManager.getInstance().stopScan();
    }

    @Override
    public void stopScanByMac() {
        BleDeviceManager.getInstance().stopScanByMac();
    }

    @Override
    public void startAction() {
        synchronized (bleDeviceManager) {
            if (!checkBleConnected()) {
                return;
            }
            GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
            EventBus.getDefault().post(new CmdBleDataEvent(getStartActionOrder()));
            bleMainControlContractView.changeBleGlassesConnectStatus(true);
            bleMainControlContractView.changeBleLightConnectStatus(true);
        }
    }

    @Override
    public void stopAction() {
        synchronized (bleDeviceManager) {
            if (!checkBleConnected()) {
                return;
            }
            GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
            EventBus.getDefault().post(new CmdBleDataEvent(getStopActionOrder()));
            bleMainControlContractView.changeBleGlassesConnectStatus(false);
            bleMainControlContractView.changeBleLightConnectStatus(false);
        }
    }

    @Override
    public void pauseAction() {
        synchronized (bleDeviceManager) {
            if (!checkBleConnected()) {
                return;
            }
            GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
            EventBus.getDefault().post(new CmdBleDataEvent(getPauseActionOrder()));
            bleMainControlContractView.changeBleGlassesConnectStatus(false);
            bleMainControlContractView.changeBleLightConnectStatus(false);
        }
    }

    @Override
    public void continueAction() {
        synchronized (bleDeviceManager) {
            if (!checkBleConnected()) {
                return;
            }
            GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
            EventBus.getDefault().post(new CmdBleDataEvent(getContinueOrder()));
            bleMainControlContractView.changeBleGlassesConnectStatus(true);
            bleMainControlContractView.changeBleLightConnectStatus(true);
        }
    }

    /**
     * 干预
     */
    @Override
    public void interveneAction() {
        synchronized (bleDeviceManager) {
            if (!checkBleConnected()) {
                return;
            }
            GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
            EventBus.getDefault().post(new CmdBleDataEvent(getInterveneOrder()));
            bleMainControlContractView.changeBleGlassesConnectStatus(true);
            bleMainControlContractView.changeBleLightConnectStatus(true);
        }
    }

    @Override
    public void closeBleDeviceBattery() {
        synchronized (bleDeviceManager) {
            if (!checkBleConnected()) {
                return;
            }
            GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
            EventBus.getDefault().post(new CmdBleDataEvent(getCloseBleDeviceBattery()));
            bleMainControlContractView.changeBleGlassesConnectStatus(true);
            bleMainControlContractView.changeBleLightConnectStatus(true);
        }
    }

    @Override
    public void connectBleGlasses(BluetoothLeDevice bluetoothLeDevice) {
        BleDeviceManager.getInstance().setmGlassesBluetoothLeDevice(bluetoothLeDevice);
        BleDeviceManager.getInstance().connectGlassesBleDevice();
    }

    @Override
    public void connectBleGlassesByMac(String mac) {
        BleDeviceManager.getInstance().connectGlassesByMac(mac);
    }

    @Override
    public void connectBleLightByMac(String mac) {
        BleDeviceManager.getInstance().connectLightByMac(mac);
    }

    @Override
    public void connectBleLight(BluetoothLeDevice bluetoothLeDevice) {
        BleDeviceManager.getInstance().setmLightBluetoothLeDevice(bluetoothLeDevice);
        BleDeviceManager.getInstance().connectLightBleDevice();
    }

    @Override
    public boolean isBleGlassesConnected() {
        return BleDeviceManager.getInstance().isGlassesBleDeviceConnected();
    }

    @Override
    public boolean isBleGlassesConnecting() {
        return BleDeviceManager.getInstance().isGlassesBleDeviceConnecting();
    }

    @Override
    public boolean isBleLightConnected() {
        return BleDeviceManager.getInstance().isLightBleDeviceConnected();
    }

    @Override
    public boolean isBleLightConnecting() {
        return BleDeviceManager.getInstance().isLightBleDeviceConnecting();
    }

    @Override
    public void sendBleData(byte[] bleDataArray) {
        //bleDeviceManager.writeData(bleDataArray);
        EventBus.getDefault().post(new CmdBleDataEvent(bleDataArray));
    }

    @Override
    public void sendGlassesBleData(byte[] bleDataArray) {
        //bleDeviceManager.writeData2GlassesBleDevice(bleDataArray);
        EventBus.getDefault().post(new CmdBleDataEvent(bleDataArray));
    }

    @Override
    public void sendLightBleData(byte[] bleDataArray) {
        bleDeviceManager.writeData2LightBleDevice(bleDataArray);
    }

    @Override
    public void disconnectGlassesBle(boolean clearMacValue) {
        bleDeviceManager.disconnectGlassesBleDevice(true);
    }

    /**
     * 获取开始指令
     *
     * @return
     */
    private byte[] getStartActionOrder() {
        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.START_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }

    /**
     * 获取停止指令
     *
     * @return
     */
    private byte[] getStopActionOrder() {

        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.STOP_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }

    /**
     * 获取暂停指令
     *
     * @return
     */
    private byte[] getPauseActionOrder() {
        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.PASUSE_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }

    /**
     * 获取继续指令
     *
     * @return
     */
    private byte[] getContinueOrder() {
        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.CONTINUE_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }

    /**
     * 关闭蓝牙眼镜电源
     * @return
     */
    private byte[] getCloseBleDeviceBattery() {
        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.POWER_OFF_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }

    /**
     * 获取干预指令
     *
     * @return
     */
    private byte[] getInterveneOrder() {
        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.INTEVENE_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }

    private void sendUserInfoAgain(int trainMode) {
        /**
         * 自动连接上蓝牙的时候，会自动发送一次用户数据，点击开始的时候，会再次发送一次用户数据（防止用户修改了训练模式）
         */
        HttpResponseGlassInitDataBackBean httpResponseGlassInitDataBackBean = TrainModel.getInstance().getHttpResponseGlassInitDataBackBean();
        int isNewUser = 0;
        if (null != httpResponseGlassInitDataBackBean) {
            HttpResponseGlassInitDataBackBean.DataBean dataBean = httpResponseGlassInitDataBackBean.getData();
            if (null != dataBean) {
                isNewUser = dataBean.getIsNewUser();
            }
        }
        SendUserInfoBleCmdBean sendUserInfoBleCmdBean = TrainModel.getInstance().createUserInfoBleHexDataForTrain(trainMode, isNewUser, 1);
        EventBus.getDefault().post(new CmdBleDataEvent(sendUserInfoBleCmdBean.buildCmdByteArray()));
    }

    private boolean checkBleConnected() {
        boolean glassesbleConnected = isBleGlassesConnected();
        if (!glassesbleConnected) {
            ToastUtil.showShort(R.string.glass_not_connect_status_tip_text);
        }
        return glassesbleConnected;
    }

}
