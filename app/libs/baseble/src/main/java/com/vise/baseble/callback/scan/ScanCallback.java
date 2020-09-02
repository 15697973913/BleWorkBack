package com.vise.baseble.callback.scan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.vise.baseble.ViseBle;
import com.vise.baseble.common.BleConfig;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.model.BluetoothLeDeviceStore;
import com.vise.baseble.newscantype.scan.BluetoothScanManager;

/**
 * @Description: 扫描设备回调
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 17/8/1 22:58.
 */
public class ScanCallback implements BluetoothAdapter.LeScanCallback, IScanFilter {
    private final boolean useNewScanType =  false;
    public final static String BLE_DEVICE_NAME_COMMONE_PREFIX = "RMX";//RMX
    protected Handler handler = new Handler(Looper.myLooper());
    protected boolean isScan = true;//是否开始扫描
    protected boolean isScanning = false;//是否正在扫描
    protected BluetoothLeDeviceStore bluetoothLeDeviceStore;//用来存储扫描到的设备
    protected IScanCallback scanCallback;//扫描结果回调

    public ScanCallback(IScanCallback scanCallback) {
        this.scanCallback = scanCallback;
        if (scanCallback == null) {
            throw new NullPointerException("this scanCallback is null!");
        }
        bluetoothLeDeviceStore = new BluetoothLeDeviceStore();
    }

    public ScanCallback setScan(boolean scan) {
        isScan = scan;
        return this;
    }

    public boolean isScanning() {
        return isScanning;
    }

    public void scan(final Context context) {
        if (isScan) {
            if (isScanning) {
                return;
            }
            bluetoothLeDeviceStore.clear();
            if (BleConfig.getInstance().getScanTimeout() > 0) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isScanning = false;

                        if (ViseBle.getInstance().getBluetoothAdapter() != null) {
                            //ViseBle.getInstance().getBluetoothAdapter().stopLeScan(ScanCallback.this);
                            stopScanWithChoice(context);
                        }

                        if (bluetoothLeDeviceStore.getDeviceMap() != null
                                && bluetoothLeDeviceStore.getDeviceMap().size() > 0) {
                            scanCallback.onScanFinish(bluetoothLeDeviceStore);
                        } else {
                            scanCallback.onScanTimeout();
                        }
                    }
                }, BleConfig.getInstance().getScanTimeout());
            }else if (BleConfig.getInstance().getScanRepeatInterval() > 0){
                //如果超时时间设置为一直扫描（即 <= 0）,则判断是否设置重复扫描间隔
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isScanning = false;

                        if (ViseBle.getInstance().getBluetoothAdapter() != null) {
                            //ViseBle.getInstance().getBluetoothAdapter().stopLeScan(ScanCallback.this);
                            stopScanWithChoice(context);
                        }

                        if (bluetoothLeDeviceStore.getDeviceMap() != null
                                && bluetoothLeDeviceStore.getDeviceMap().size() > 0) {
                            scanCallback.onScanFinish(bluetoothLeDeviceStore);
                        } else {
                            scanCallback.onScanTimeout();
                        }
                        isScanning = true;
                        if (ViseBle.getInstance().getBluetoothAdapter() != null) {
                            startScanWithChoice(context);
                            //ViseBle.getInstance().getBluetoothAdapter().startLeScan(ScanCallback.this);
                        }
                        handler.postDelayed(this,BleConfig.getInstance().getScanRepeatInterval());
                    }
                }, BleConfig.getInstance().getScanRepeatInterval());
            }
            isScanning = true;
            if (ViseBle.getInstance().getBluetoothAdapter() != null) {
                //ViseBle.getInstance().getBluetoothAdapter().startLeScan(ScanCallback.this);
                startScanWithChoice(context);
            }
        } else {
            isScanning = false;
            if (ViseBle.getInstance().getBluetoothAdapter() != null) {
                //ViseBle.getInstance().getBluetoothAdapter().stopLeScan(ScanCallback.this);
                stopScanWithChoice(context);
            }
        }
    }

    public ScanCallback removeHandlerMsg() {
        handler.removeCallbacksAndMessages(null);
        bluetoothLeDeviceStore.clear();
        return this;
    }

    private void startScanWithChoice(Context context) {
        if (useNewScanType) {
            /**
             * 设置扫描的过滤条件
             */
            BluetoothScanManager.getInstance(context).addScanFilterCompats(null);
            BluetoothScanManager.getInstance(context).startScanWithNewDef(context, bluetoothLeDeviceStore, scanCallback);
        } else {
            ViseBle.getInstance().getBluetoothAdapter().startLeScan(ScanCallback.this);
        }

    }

    private void stopScanWithChoice(Context context) {
        if (useNewScanType) {
            BluetoothScanManager.getInstance(context).stopCycleScan();
        } else {
            ViseBle.getInstance().getBluetoothAdapter().stopLeScan(ScanCallback.this);
        }
    }

    @Override
    public void onLeScan(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord) {
        Log.e("ScanCallback", bluetoothDevice.getAddress() + " " + bluetoothDevice.getName() + "  threadID = " + Thread.currentThread().getId());
        BluetoothLeDevice bluetoothLeDevice = new BluetoothLeDevice(bluetoothDevice, rssi, scanRecord, System.currentTimeMillis());
        BluetoothLeDevice filterDevice = onFilter(bluetoothLeDevice);
        if (filterDevice != null) {
            bluetoothLeDeviceStore.addDevice(filterDevice);
            scanCallback.onDeviceFound(filterDevice);
        }
    }

    @Override
    public BluetoothLeDevice onFilter(BluetoothLeDevice bluetoothLeDevice) {
        if (null != bluetoothLeDevice) {
            String name = bluetoothLeDevice.getName();
            String mac = bluetoothLeDevice.getAddress();
            if (!TextUtils.isEmpty(name) && name.trim().startsWith(BLE_DEVICE_NAME_COMMONE_PREFIX)) {
                return bluetoothLeDevice;
            }
        }
        return null;
    }

}
