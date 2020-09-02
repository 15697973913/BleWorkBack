package com.zj.zhijue.base;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.common.baselibrary.log.BleDataLog;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.ActivityStackUtil;
import com.android.common.baselibrary.util.PermissionUtil;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.litesuits.android.log.Log;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.ble.BleDeviceManager;
import com.vise.xsnow.permission.OnPermissionCallback;
import com.vise.xsnow.permission.PermissionManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BaseBleActivity extends BaseActivity {
    public static final int REQUEST_STORAGE_PERMISSION = 0x1111;
    protected BluetoothAdapter bluetoothAdapter;
    private AlertDialog.Builder showLocationServiceDialog;
    private AlertDialog.Builder showGrantedLocationPermissonDialog;

    protected void onCreate(@Nullable Bundle bundle) {
        initBleAdapter();
        //        enableBluetooth();
        super.onCreate(bundle);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    void showScan() {
        if (!checkBlueSupport()) {
            return;
        }
        // 检查蓝牙权限
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }

    /***
     * 检查蓝牙权限是否授权，是否具备蓝牙功能
     */
    protected void checkBluetoothPermission() {
        if (!checkBlueSupport()) {
            return;
        }

        if (Build.VERSION.SDK_INT < 23) {
            enableBluetooth();
        } else {
            enableBluetooth();
            PermissionManager.instance().request(this, new OnPermissionCallback() {
                public void onRequestAllow(String str) {
                    BaseBleActivity.this.enableBluetooth();
                    checkLocationService();
                }

                public void onRequestRefuse(String str) {
                    showLocationPermissonsDialog();
                }

                public void onRequestNoAsk(String str) {
                    showLocationPermissonsDialog();
                }
            }, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }

    protected void enableBluetooth() {
        if (null != bluetoothAdapter && !bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }

     /*   if (BleUtil.isBleEnable(this)) {
            boolean isSupportBle = BleUtil.isSupportBle(this);
            boolean isBleEnable = BleUtil.isBleEnable(this);
            if (!isSupportBle) {
                ToastUtil.showShort((CharSequence) "不支持蓝牙");
            }
            if (!isBleEnable) {
                ToastUtil.showShort((CharSequence) "请打开蓝牙");
                return;
            }
            return;
        }
        BleUtil.enableBluetooth(this, REQUEST_CODE_OPEN_BLOOTH);*/
    }

    protected boolean checkBluetoothEnable() {
        if (!checkBlueSupport()) {
            return false;
        }

        if (null != bluetoothAdapter && bluetoothAdapter.isEnabled()) {
            return true;
        } else {
            ToastUtil.showLong("蓝牙服务未开启！");
        }
        return false;
    }

    protected boolean bluetoothAndLocationServiceOk() {
        if (!checkBlueSupport()) {
            return false;
        }

        if (!PermissionUtil.hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION});
            return false;
        }

        if (null != bluetoothAdapter && !bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }

        if (!isLocationEnable(this)) {
            showOpenLocationDialog();
            return false;
        }

        return true;
    }

    protected void checkLocationService() {
        if (!isLocationEnable(this)) {
            showOpenLocationDialog();
        }
    }

    private void initBleAdapter() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
    }

    public boolean isLocationEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (null != locationManager) {
            boolean networkProvider = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            boolean gpsProvider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (networkProvider || gpsProvider) {
                return true;
            }
        }

        return false;
    }

    protected void setLocationService() {
        Intent locationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        this.startActivityForResult(locationIntent, REQUEST_CODE_ACCESS_COARSE_LOCATION);
    }

    public boolean checkBlueSupport() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtil.showLongToast(CommonUtils.int2String(R.string.not_support_bluetooth_text));
            return false;
        }
        // 检查设备是否支持蓝牙
        if (bluetoothAdapter == null) {
            ToastUtil.showLongToast(CommonUtils.int2String(R.string.not_support_bluetooth_error_text));
            return false;
        }
        return true;
    }

    @Override
    public String[] getPermissions() {
        String[] array = super.getPermissions();
        String[] subArray = new String[]{
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
        };

        List<String> superList = Arrays.asList(array);
        List<String> subList = Arrays.asList(subArray);
        Set<String> setPermission = new HashSet<>();
        setPermission.addAll(superList);
        setPermission.addAll(subList);

        return setPermission.toArray(new String[setPermission.size()]);
    }

    @Override
    public void requestPermissionsSuccess(List<String> grantedPermissionsList) {
        //ToastDialogUtil.showShortToast("授权成功");
        MLog.d("requestPermissionsSuccess " + grantedPermissionsList.size());
        super.requestPermissionsSuccess(grantedPermissionsList);
    }

    /**
     * 请求读写权限
     */
    protected void checkWritePermission() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // 此处写第一次检查权限且已经拥有权限后的业务
            LogUtils.v("获取了读写权限");
            BleDataLog.init();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            }
        }
    }

    /**
     * 检查权限后的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            LogUtils.v("获取了读写权限");
            BleDataLog.init();
        }
    }

    @Override
    public void requestPermissionsFail(List<String> permissionList) {
        MLog.d("requestPermissionsFail " + permissionList.size());
        for (String permission : permissionList) {
            LogUtils.i(permission + "  授权失败");
        }
        if (permissionList.size() >= 1) {
            showDialogByPermission(permissionList, permissionList.get(0));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ACCESS_COARSE_LOCATION) {
            if (!isLocationEnable(this)) {
                ToastUtil.showShort(getResources().getString(R.string.open_location_failure_please));
            }
        } else if (requestCode == REQUEST_CODE_OPEN_BLOOTH) {
            if (null != bluetoothAdapter && !bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.enable();
            }
        }
    }

    protected void showDialogByPermission(List<String> permissionList, String permission) {
        if (Manifest.permission.ACCESS_COARSE_LOCATION.equals(permission)) {
            onlyShowDialog(permissionList, permission, getResources().getText(R.string.denied_read_location_tip_text).toString());
        } else if (Manifest.permission.CAMERA.equals(permission)) {
            onlyShowDialog(permissionList, permission, getResources().getText(R.string.denied_camera_tip_text).toString());
        } else {
            super.showDialogByPermission(permissionList, permission);
        }
    }

    /**
     * 弹窗提示用户需要手动开启位置服务
     */
    protected void showOpenLocationDialog() {
        if (null == showLocationServiceDialog) {
            showLocationServiceDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)//android.R.style.Theme_Material_Light_Dialog_Alert)
                    .setPositiveButton(getResources().getString(R.string.sure_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            setLocationService();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancel_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityStackUtil.getInstance().finishAllActivity();
                        }
                    })
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.open_location_please));
        }
        showLocationServiceDialog.show();
    }

    /**
     * 点击授权定位权限，否则蓝牙功能无法使用
     */
    protected void showLocationPermissonsDialog() {
        if (null == showGrantedLocationPermissonDialog) {
            showGrantedLocationPermissonDialog = new AlertDialog.Builder(BaseBleActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                    .setPositiveButton(getResources().getString(R.string.sure_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            PermissionUtil.goAppDetailSettingActivity(MyApplication.getInstance());
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancel_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityStackUtil.getInstance().finishAllActivity();
                        }
                    })
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.grantedlocation_permisson_please));
        }

        showGrantedLocationPermissonDialog.show();
    }

    protected void stopBleScanAndAllConnect() {
        BleDeviceManager.getInstance().stopScan();
        BleDeviceManager.getInstance().stopScanByMac();
        BleDeviceManager.getInstance().disconnectGlassesBleDevice(false);
    }

}
