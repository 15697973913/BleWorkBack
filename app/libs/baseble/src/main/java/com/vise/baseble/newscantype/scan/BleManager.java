package com.vise.baseble.newscantype.scan;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;


/**
 * Copyright (C) BlakeQu All Rights Reserved <blakequ@gmail.com>
 * <p/>
 * Licensed under the blakequ.com License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * author  : quhao <blakequ@gmail.com> <br>
 * date     : 2016/8/17 11:02 <br>
 * last modify author : <br>
 * version : 1.0 <br>
 * description: scan and connect ble device
 */
@TargetApi(18)
public final class BleManager {
    private static BleParamsOptions configOptions;
    private BleManager(){
    }

    public static BleParamsOptions getBleParamsOptions(){
        if (configOptions == null){
            setBleParamsOptions(BleParamsOptions.createDefault());
        }
        return configOptions;
    }

    /**
     * set config of ble connect and scan
     * @param options
     */
    public static void setBleParamsOptions(@NonNull BleParamsOptions options){
        if (options != null){
            configOptions = options;
            ConnectConfig.maxConnectDeviceNum = options.getMaxConnectDeviceNum();
        }
    }

    /**
     * get scan bluetooth manager
     * @param context
     * @return
     */
    public static BluetoothScanManager getScanManager(@NonNull Context context){
        return BluetoothScanManager.getInstance(context);
    }


    /**
     * Check if Bluetooth LE is supported by this Android device, and if so, make sure it is enabled.
     *
     * @return false if it is supported and not enabled
     * @throws BleNotAvailableException if Bluetooth LE is not supported.  (Note: The Android emulator will do this)
     */
    public static boolean checkAvailability(Context context) throws BleNotAvailableException {
        if (isSDKAvailable()) {
            if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
                throw new BleNotAvailableException("Bluetooth LE not supported by this device");
            } else {
                if (((BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter().isEnabled()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSDKAvailable(){
        if (android.os.Build.VERSION.SDK_INT < 18) {
            throw new BleNotAvailableException("Bluetooth LE not supported by this device");
        }
        return true;
    }
}
