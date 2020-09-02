package com.zj.zhijue.listener;

public interface MainControlActivityListener {
    public void startSearchActivityForResultFromTrainFragment();
    public void changeTrainModeFromTrainFragment(int trainMode);
    public void startActionFromTrainFragment();
    public void pauseActionFromTrainFragment();
    public void interveneActionFromTrainFragment();
    public void stopActionFromTrainFragment();
    public void continueActionFromTrainFragment();
    public void closeBatteryFromTrainFragment();
    public void commonModeFromTrainFragment();
    public void indoorModeFromTrainFragment();
    public void outdoorModeFromTrainFragment();

    public void scanBleDeviceQrCoder();//扫描蓝牙眼镜的二维码，进行连接

    public void quitConnectBleDevice();
    public void retryConnectBleDevice();
    public void connectBleDeviceByMac(String mac);
    public boolean isConnected();

    public boolean checkLocationServiceOk();

    public void updateConnectStatusAndConnect();
}
