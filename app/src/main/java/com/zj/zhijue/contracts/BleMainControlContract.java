package com.zj.zhijue.contracts;

import com.zj.zhijue.BasePresenter;
import com.zj.zhijue.BaseView;
import com.vise.baseble.model.BluetoothLeDevice;

/**
 * Created by Administrator on 2018/8/8.
 */

public interface BleMainControlContract {

    interface View extends BaseView<Presenter> {
        void showToastMsg(String tipMsg);

        void showToastMsg(int resourceId);

        void changeBleGlassesConnectStatus(boolean startAnimation);

        void changeBleLightConnectStatus(boolean startAnimation);

        void setStatusText(String statusText);

        void setBleGlassesBatteryText(String bleGlassesBatteryText);

        void setBleLightBatteryText(String bleLightBatteryText);

        void setSelectedScenarioTextView(String selectedScenarioText);

        void setTrainModeText(String trainModeText);

        void setUserName(String userName);
    }

    interface Presenter extends BasePresenter {
        void sendUserInfoWithTrainMode(int trainMode);//用户选择训练模式的时候发送

        void startScan();

        void stopScan();

        void stopScanByMac();

        void startAction();

        void stopAction();

        void pauseAction();

        void continueAction();

        void interveneAction();//干预

        void closeBleDeviceBattery();//关闭蓝牙设备电源

        void connectBleGlasses(BluetoothLeDevice glassesBleDevice);//连接蓝牙眼镜

        void connectBleLight(BluetoothLeDevice lightBleDevice);//连接光通量

        void connectBleGlassesByMac(String mac);

        void connectBleLightByMac(String mac);

        boolean isBleGlassesConnected();//蓝牙眼镜是否已连接

        boolean isBleGlassesConnecting();//蓝牙眼镜是否正在连接中

        boolean isBleLightConnected();//光通量是否已连接

        boolean isBleLightConnecting();//光通量是否正在连接中

        void sendBleData(byte[] bleDataArray);

        void sendGlassesBleData(byte[] bleDataArray);

        void sendLightBleData(byte[] bleDataArray);

        void disconnectGlassesBle(boolean clearMacValue);//默认会断开所有蓝牙设备
    }
}
