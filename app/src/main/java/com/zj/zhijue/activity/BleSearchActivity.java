package com.zj.zhijue.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseBleActivity;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.event.EventConstant;
import com.zj.zhijue.event.ScanEvent;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.utils.BleUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/6/11.
 */

public class BleSearchActivity extends BaseBleActivity {

    @BindView(R.id.bledevicerecyclelist)
    RecyclerView bleDeviceRecycleList;

    @BindView(R.id.backbutton)
    Button backButton;

    @BindView(R.id.scanbutton)
    Button scanButton;

    @BindView(R.id.gomaincontrolswitch)
    Switch goMainControlSwitch;

    private String glassmac;
    private List<String> list = new ArrayList();

    private BaseQuickAdapter baseQuickAdapter;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setNewTheme();
        setContentView(R.layout.blesearch_layout);
        super.onCreate(bundle);
        glassmac = MainActivity.qrCode;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initView();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScanEvent(ScanEvent scanEvent) {
        dismissLoading();
        if (scanEvent.isSuccess()) {
            BluetoothLeDevice bluetoothLeDevice = scanEvent.getBluetoothLeDevice();
            String address = bluetoothLeDevice.getAddress();
            if (!StringUtils.isEmpty(address) && !this.list.contains(address)) {
                this.list.add(address);
                //ToastUtils.showLong(" 扫到设备： = " + address);
                this.baseQuickAdapter.addData(bluetoothLeDevice);
                return;
            }
            return;
        }
        ToastUtil.showShort((CharSequence) "扫描超时");
    }

    private void initView() {
        initStatusBar(R.color.main_background_color);

        this.bleDeviceRecycleList.setLayoutManager(new LinearLayoutManager(this));
        baseQuickAdapter = new BaseQuickAdapter<BluetoothLeDevice, BaseViewHolder>(R.layout.ble_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, BluetoothLeDevice item) {

                ImageView imageView = helper.getView(R.id.image);
                TextView textView = helper.getView(R.id.tv_content);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(item.getAddress());
                stringBuilder.append("\n");
                stringBuilder.append(item.getName());
                textView.setText(stringBuilder.toString());
                if (BleSearchActivity.this.glassmac.equals(item.getAddress())) {
                    imageView.setBackgroundResource(R.mipmap.blue_train);
                    textView.setTextColor(BleSearchActivity.this.getResources().getColor(R.color.blue));
                    return;
                }
                imageView.setBackgroundResource(R.mipmap.black_train);
                textView.setTextColor(BleSearchActivity.this.getResources().getColor(R.color.black));
            }
        };
        bleDeviceRecycleList.setAdapter(baseQuickAdapter);
    }

    private void initListener() {
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetoothAndLocationServiceOk()) {
                    BleDeviceManager.getInstance().startScan(BleSearchActivity.this.glassmac);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                mIntent.putExtra(BleMainControlActivity.BLUETOOTH_DEVICE_KEY, "");
                setResult(1, mIntent);
                BleSearchActivity.this.finish();
            }
        });

        this.baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BluetoothLeDevice bluetoothLeDevice = (BluetoothLeDevice) baseQuickAdapter.getItem(i);
                //if (BleSearchActivity.this.glassmac.equals(((BluetoothLeDevice) baseQuickAdapter.getItem(i)).getAddress())) {
                if (null != bluetoothLeDevice && (isGlassesBleDevice(bluetoothLeDevice) || isLightBleDevice(bluetoothLeDevice))) {
                    Intent mIntent = new Intent(BleSearchActivity.this, BleMainControlActivity.class);
                    if (goMainControlSwitch.isChecked()) {
                        //mIntent = new Intent(BleSearchActivity.this, BleMainControlActivity.class);
                        mIntent.putExtra(BleMainControlActivity.BLUETOOTH_DEVICE_KEY, bluetoothLeDevice);
                        setResult(0, mIntent);
                        finish();
                        return;
                    } else if (!goMainControlSwitch.isChecked()){
                        mIntent = new Intent(BleSearchActivity.this, SendReceiveBleDataActivity.class);
                        mIntent.putExtra(BleMainControlActivity.BLUETOOTH_DEVICE_KEY, bluetoothLeDevice);
                        setResult(0, mIntent);
                        finish();
                        return;
                    }

                    if (isGlassesBleDevice(bluetoothLeDevice)) {
                        mIntent.putExtra(BleMainControlActivity.BLUETOOTH_DEVICE_TYPE_KEY, EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
                    } else if (isLightBleDevice(bluetoothLeDevice)) {
                        mIntent.putExtra(BleMainControlActivity.BLUETOOTH_DEVICE_TYPE_KEY, EventConstant.LIGHT_BLUETOOTH_EVENT_TYPE);
                    }
                    mIntent.putExtra(BleMainControlActivity.BLUETOOTH_DEVICE_KEY, bluetoothLeDevice);
                    BleSearchActivity.this.startActivity(mIntent);
                    BleSearchActivity.this.finish();
                    return;
                }
                ToastUtil.showShort("请连接指定设备");
            }
        });

    }


    private void enableBluetoothDialog() {
        if (BleUtil.isBleEnable(this)) {
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
        BleUtil.enableBluetooth(this, 1);
    }


    private boolean isGlassesBleDevice(BluetoothLeDevice bluetoothLeDevice) {
        if (null != bluetoothLeDevice) {
            String macAddress = bluetoothLeDevice.getAddress();
            if (!CommonUtils.isEmpty(macAddress)) {
                //FIXME
                /**
                 * 过滤条件有待调整
                 */
                if (macAddress.indexOf(":") != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isLightBleDevice(BluetoothLeDevice bluetoothLeDevice) {
        if (null != bluetoothLeDevice) {
            String macAddress = bluetoothLeDevice.getAddress();
            if (!CommonUtils.isEmpty(macAddress)) {
                if (macAddress.equalsIgnoreCase("7c:0c:a6:fb:e2:ac")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent mIntent = new Intent();
        mIntent.putExtra(BleMainControlActivity.BLUETOOTH_DEVICE_KEY, "");
        setResult(1, mIntent);
        BleSearchActivity.this.finish();
    }
}
