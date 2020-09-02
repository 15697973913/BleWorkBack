package com.zj.zhijue.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;


import com.zj.zhijue.constant.BleSppGattAttributes;
import com.zj.zhijue.event.EventConstant;
import com.vise.baseble.common.PropertyType;
import com.vise.baseble.core.DeviceMirror;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class BleOptHelper {
    private static final String FEE0 = "fee0";
    private static final String FEE1 = "fee1";
    private static final String FFE0 = "fee0";
    private static final String FEE2 = "fee2";
    public static boolean turnOnBluetooth() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        return defaultAdapter != null ? defaultAdapter.enable() : false;
    }

    public static void bindWriteChannel(int deviceType) {
        DeviceMirror deviceMirror = null;
        if (deviceType == EventConstant.GLASS_BLUETOOTH_EVENT_TYPE) {
            deviceMirror = BleDeviceManager.getInstance().getGlassesDeviceMirror();
            //SdLogUtil.writeCommonLog("bindWriteChannel GLASS_BLUETOOTH_EVENT_TYPE deviceMirror = " + deviceMirror);
            BluetoothGattService bleGattService = null;
            BluetoothGattCharacteristic bletGattServiceCharacter = null;
            if (deviceMirror != null) {
                bleGattService = deviceMirror.getGattService(UUID.fromString(BleSppGattAttributes.BLE_SPP_Service));
                //SdLogUtil.writeCommonLog("bindWriteChannel GLASS_BLUETOOTH_EVENT_TYPE bleGattService = " + bleGattService);

                if (bleGattService != null) {
                    bletGattServiceCharacter = bleGattService.getCharacteristic(UUID.fromString(BleSppGattAttributes.BLE_SPP_Write_Characteristic));
                    if (null == bletGattServiceCharacter) {
                        bletGattServiceCharacter = bleGattService.getCharacteristic(UUID.fromString(BleSppGattAttributes.BLE_SPP_Notify_Characteristic));
                    }

                    if (bletGattServiceCharacter != null) {
                        BleDeviceManager.getInstance().bindGlassesChannel(PropertyType.PROPERTY_WRITE, bleGattService.getUuid(), bletGattServiceCharacter.getUuid(), null);
                        //SdLogUtil.writeCommonLog("bindWriteChannel BleDeviceManager.getInstance().bindGlassesChannel");
                    }
                }
            }

        } else if (deviceType == EventConstant.LIGHT_BLUETOOTH_EVENT_TYPE) {
            BluetoothGattService bleGattService = null;
            BluetoothGattCharacteristic bletGattServiceCharacter = null;
            deviceMirror = BleDeviceManager.getInstance().getLightDeviceMirror();
            //SdLogUtil.writeCommonLog("bindWriteChannel LIGHT_BLUETOOTH_EVENT_TYPE deviceMirror = " + deviceMirror);
            if (deviceMirror != null) {
                for (BluetoothGattService bluetoothGattService : deviceMirror.getBluetoothGatt().getServices()) {
                    //SdLogUtil.writeCommonLog("bindWriteChannel LIGHT_BLUETOOTH_EVENT_TYPE bluetoothGattService.getUuid().toString() = " + bluetoothGattService.getUuid().toString());
                    if (bluetoothGattService.getUuid().toString().contains(FEE0.toLowerCase())) {
                        bleGattService = bluetoothGattService;
                        break;
                    }
                }

                if (bleGattService != null) {
                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bleGattService.getCharacteristics()) {
                        //SdLogUtil.writeCommonLog("bindWriteChannel LIGHT_BLUETOOTH_EVENT_TYPE bluetoothGattCharacteristic.getUuid().toString() = " + bluetoothGattCharacteristic.getUuid().toString());
                        if (bluetoothGattCharacteristic.getUuid().toString().contains(FEE1.toLowerCase())) {
                            bletGattServiceCharacter = bluetoothGattCharacteristic;
                            break;
                        }
                    }

                    if (bletGattServiceCharacter != null) {
                        BleDeviceManager.getInstance().bindLightChannel(PropertyType.PROPERTY_WRITE, bleGattService.getUuid(), bletGattServiceCharacter.getUuid(), null);
                        //SdLogUtil.writeCommonLog("bindWriteChannel LIGHT_BLUETOOTH_EVENT_TYPE BleDeviceManager.getInstance().registerLightNotify");
                    }
                }
            }
        }

    }

    public static void bindNotifyChannel(int deviceType) {
        DeviceMirror deviceMirror = null;
        if (deviceType == EventConstant.GLASS_BLUETOOTH_EVENT_TYPE) {
            deviceMirror = BleDeviceManager.getInstance().getGlassesDeviceMirror();
            //SdLogUtil.writeCommonLog("bindNotifyChannel GLASS_BLUETOOTH_EVENT_TYPE deviceMirror = " + deviceMirror);
            BluetoothGattService bleGattService = null;
            BluetoothGattCharacteristic bleGattServiceCharacter = null;
            if (deviceMirror != null) {
                bleGattService = deviceMirror.getGattService(UUID.fromString(BleSppGattAttributes.BLE_SPP_Service));
                //SdLogUtil.writeCommonLog("bindNotifyChannel GLASS_BLUETOOTH_EVENT_TYPE bleGattService = " + bleGattService);

                if (bleGattService != null) {
                    bleGattServiceCharacter = bleGattService.getCharacteristic(UUID.fromString(BleSppGattAttributes.BLE_SPP_Notify_Characteristic));
                    if (null == bleGattServiceCharacter) {
                        bleGattServiceCharacter = bleGattService.getCharacteristic(UUID.fromString(BleSppGattAttributes.BLE_SPP_Write_Characteristic));
                    }

                    if (bleGattServiceCharacter != null) {
                        BleDeviceManager.getInstance().bindGlassesChannel(PropertyType.PROPERTY_NOTIFY, bleGattService.getUuid(), bleGattServiceCharacter.getUuid(), null);
                        BleDeviceManager.getInstance().registerGlassesNotify(false);
                        //SdLogUtil.writeCommonLog("bindNotifyChannel GLASS_BLUETOOTH_EVENT_TYPE BleDeviceManager.getInstance().registerGlassesNotify");
                    }
                }
            }
        } else if (deviceType == EventConstant.LIGHT_BLUETOOTH_EVENT_TYPE) {
            deviceMirror = BleDeviceManager.getInstance().getLightDeviceMirror();
            //SdLogUtil.writeCommonLog("bindNotifyChannel LIGHT_BLUETOOTH_EVENT_TYPE deviceMirror = " + deviceMirror);
            BluetoothGattService bleGattService = null;
            BluetoothGattCharacteristic bleGattServiceCharacter = null;
            if (deviceMirror != null) {
                for (BluetoothGattService bluetoothGattService : deviceMirror.getBluetoothGatt().getServices()) {
                    //SdLogUtil.writeCommonLog("bindNotifyChannel LIGHT_BLUETOOTH_EVENT_TYPE bluetoothGattService.getUuid().toString() = " + bluetoothGattService.getUuid().toString());
                    if (bluetoothGattService.getUuid().toString().contains(FFE0.toLowerCase())) {
                        bleGattService = bluetoothGattService;
                        break;
                    }
                }

                if (bleGattService != null) {
                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bleGattService.getCharacteristics()) {
                        //SdLogUtil.writeCommonLog("bindNotifyChannel LIGHT_BLUETOOTH_EVENT_TYPE bluetoothGattCharacteristic.getUuid().toString() = " + bluetoothGattCharacteristic.getUuid().toString());
                        if (bluetoothGattCharacteristic.getUuid().toString().contains(FEE2.toLowerCase())) {
                            bleGattServiceCharacter = bluetoothGattCharacteristic;
                            break;
                        }
                    }

                    if (bleGattServiceCharacter != null) {
                        BleDeviceManager.getInstance().bindLightChannel(PropertyType.PROPERTY_NOTIFY, bleGattService.getUuid(), bleGattServiceCharacter.getUuid(), null);
                        BleDeviceManager.getInstance().registerLightNotify(false);
                        //SdLogUtil.writeCommonLog("bindNotifyChannel LIGHT_BLUETOOTH_EVENT_TYPE BleDeviceManager.getInstance().registerLightNotify");
                    }
                }
            }
        }
    }

    public static void bindReadChannel(int deviceType) {
        DeviceMirror deviceMirror = null;
        if (deviceType == EventConstant.GLASS_BLUETOOTH_EVENT_TYPE) {
            deviceMirror = BleDeviceManager.getInstance().getGlassesDeviceMirror();
            //SdLogUtil.writeCommonLog("bindNotifyChannel GLASS_BLUETOOTH_EVENT_TYPE deviceMirror = " + deviceMirror);
            BluetoothGattService bleGattService = null;
            BluetoothGattCharacteristic bleGattServiceCharacter = null;
            if (deviceMirror != null) {
                bleGattService = deviceMirror.getGattService(UUID.fromString(BleSppGattAttributes.BLE_SPP_READ_Characteristic));
                if (bleGattService != null) {
                    bleGattServiceCharacter = bleGattService.getCharacteristic(UUID.fromString(BleSppGattAttributes.BLE_SPP_Firmware_Revision_String_Characteristic));

                    if (bleGattServiceCharacter != null) {
                        BleDeviceManager.getInstance().bindGlassesChannel(PropertyType.PROPERTY_READ, bleGattService.getUuid(), bleGattServiceCharacter.getUuid(), null);
                    }
                }
            }
        }
    }

    public static void unBindReadChannel(int deviceType) {
        DeviceMirror deviceMirror = null;
        if (deviceType == EventConstant.GLASS_BLUETOOTH_EVENT_TYPE) {
            deviceMirror = BleDeviceManager.getInstance().getGlassesDeviceMirror();
            BluetoothGattService bleGattService = null;
            BluetoothGattCharacteristic bleGattServiceCharacter = null;
            if (deviceMirror != null) {
                bleGattService = deviceMirror.getGattService(UUID.fromString(BleSppGattAttributes.BLE_SPP_READ_Characteristic));
                if (bleGattService != null) {
                    bleGattServiceCharacter = bleGattService.getCharacteristic(UUID.fromString(BleSppGattAttributes.BLE_SPP_Firmware_Revision_String_Characteristic));

                    if (bleGattServiceCharacter != null) {
                        BleDeviceManager.getInstance().unbindGlassesChannel(PropertyType.PROPERTY_READ, bleGattService.getUuid(), bleGattServiceCharacter.getUuid(), null);
                    }
                }
            }
        }
    }

    public static String[] convert10To16(byte[] bArr) {
        String[] strArr = new String[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            strArr[i] = Integer.toHexString(bArr[i]);
        }
        return strArr;
    }

    private boolean isHexData(String str) {
        if (str == null) {
            return false;
        }
        char[] chars = str.toCharArray();
        if ((chars.length & 1) != 0) {//个数为奇数，直接返回false
            return false;
        }
        for (char ch : chars) {
            if (ch >= '0' && ch <= '9') continue;
            if (ch >= 'A' && ch <= 'F') continue;
            if (ch >= 'a' && ch <= 'f') continue;
            return false;
        }
        return true;
    }

    /**
     * 数据分包
     *
     * @param data
     * @return
     */
    private Queue<byte[]> splitPacketFor20Byte(byte[] data) {
        Queue<byte[]> dataInfoQueue = new LinkedList<>();
        if (data != null) {
            int index = 0;
            do {
                byte[] surplusData = new byte[data.length - index];
                byte[] currentData;
                System.arraycopy(data, index, surplusData, 0, data.length - index);
                if (surplusData.length <= 20) {
                    currentData = new byte[surplusData.length];
                    System.arraycopy(surplusData, 0, currentData, 0, surplusData.length);
                    index += surplusData.length;
                } else {
                    currentData = new byte[20];
                    System.arraycopy(data, index, currentData, 0, 20);
                    index += 20;
                }
                dataInfoQueue.offer(currentData);
            } while (index < data.length);
        }
        return dataInfoQueue;
    }
}
