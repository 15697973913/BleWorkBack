package com.zj.zhijue.dialog.bleconnect;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.ToastUtil;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zj.zhijue.R;
import com.zj.zhijue.bean.event.DialogBleConnectEvent;
import com.zj.zhijue.bean.event.DialogScanEvent;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.dialog.CenterScaleDialog;
import com.zj.zhijue.event.ScanEvent;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.vise.baseble.callback.scan.BleDeviceCompartor;
import com.vise.baseble.model.BluetoothLeDevice;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 搜索蓝牙设备，以列表显示
 */
public class BleDeviceListialog extends CenterScaleDialog<BleDeviceListialog> {
    @BindView(R.id.mainrelativelayout)
    RelativeLayout mainLayout;

    @BindView(R.id.blereyclerviewdig)
    RecyclerView bleRecyclerView;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private int screenWidth;
    private int screenHeight;

    private DialogButtonClickListener mDialogButtonClickListener;

    private Context mContext;

    private String mDeviceID;

    private BaseQuickAdapter baseQuickAdapter;

    private Set<String> deviceSet = new HashSet<>();

    private BleDeviceCompartor bleDeviceCompartor = new BleDeviceCompartor();

    public BleDeviceListialog(Context context) {
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
        initRecyclerView(context);
        initListener();
    }

    private void initRecyclerView(Context context) {
        bleRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        baseQuickAdapter = new BaseQuickAdapter<BluetoothLeDevice, BaseViewHolder>(R.layout.ble_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, BluetoothLeDevice item) {
                AppCompatTextView textView = helper.getView(R.id.tv_content);
                String str = item.getName() +
                        "\n" +
                        item.getAddress() +
                        "," +
                        "信号强度：" +
                        item.getRssi();


                SpannableString spannableString = new SpannableString(str);
                spannableString.setSpan(new AbsoluteSizeSpan(30), item.getName().length(), str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString);

            }
        };


        bleRecyclerView.setAdapter(baseQuickAdapter);
    }

    private void resetSize() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainLayout.getLayoutParams();
        layoutParams.width = screenWidth * 4 / 5;
        layoutParams.height = screenHeight * 2 / 5;
        mainLayout.setLayoutParams(layoutParams);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_ble_device_list_layout;
    }

    @Override
    public void onCreateData(Context context) {
    }

    @Override
    public void show() {
        registerEventBus();
        super.show();
        EventBus.getDefault().post(new DialogScanEvent(true));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScanEvent(ScanEvent scanEvent) {
        if (scanEvent.isSuccess()) {
            BluetoothLeDevice bluetoothLeDevice = scanEvent.getBluetoothLeDevice();
            String address = bluetoothLeDevice.getAddress();

            BluetoothDevice foundDevice = bluetoothLeDevice.getDevice();
            if (foundDevice != null) {
                foundDevice.fetchUuidsWithSdp();
                LogUtils.v("foundDevice.getUuids();" + Arrays.toString(foundDevice.getUuids()));
            }


            if (!StringUtils.isEmpty(address) && !this.deviceSet.contains(address)) {
                this.deviceSet.add(address);
                this.baseQuickAdapter.getData().add(bluetoothLeDevice);
                Collections.sort(this.baseQuickAdapter.getData(), bleDeviceCompartor);
                this.baseQuickAdapter.notifyDataSetChanged();
                //this.baseQuickAdapter.addData(bluetoothLeDevice);
                return;
            }

            return;
        }
        ToastUtil.showShort((CharSequence) "未发现可连接设备");
    }

    @Override
    public void dismiss() {
        mDeviceID = null;
        super.dismiss();
        unRegisterEventBus();
        clearData();
        EventBus.getDefault().post(new DialogScanEvent(false));
    }

    private void clearData() {
        if (null != deviceSet) {
            deviceSet.clear();
        }
        if (null != baseQuickAdapter) {
            baseQuickAdapter.getData().clear();
        }
    }

    @Override
    public void onClick(View v, int id) {
        callBackButtonClickListener(id);
        switch (id) {
            case R.id.researchbtn:
                clearData();
                EventBus.getDefault().post(new DialogScanEvent(true));
                break;

            case R.id.closebtn:
                dismiss();
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

    private void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void unRegisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initListener() {
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BluetoothLeDevice bluetoothLeDevice = (BluetoothLeDevice) baseQuickAdapter.getItem(i);
                if (null != bluetoothLeDevice) {
                    EventBus.getDefault().post(new DialogBleConnectEvent(bluetoothLeDevice));
                    dismiss();
                }
            }
        });
    }
}
