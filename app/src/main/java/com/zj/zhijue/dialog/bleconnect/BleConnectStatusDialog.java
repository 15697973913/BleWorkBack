package com.zj.zhijue.dialog.bleconnect;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.ThreadPoolUtilsLocal;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zj.zhijue.R;
import com.zj.zhijue.bean.event.DialogBleConnectEvent;
import com.zj.zhijue.bean.event.DialogScanEvent;
import com.zj.zhijue.constant.BleConnectStatus;
import com.zj.zhijue.event.ScanEvent;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.util.Config;
import com.vise.baseble.callback.scan.BleDeviceCompartor;
import com.vise.baseble.model.BluetoothLeDevice;
import com.zj.zhijue.dialog.CenterScaleDialog;
import com.zj.zhijue.util.view.ui.DeviceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 蓝牙设备连接状态 Dialog
 */
public class BleConnectStatusDialog extends CenterScaleDialog<BleConnectStatusDialog> {
    @BindView(R.id.mainrelativelayout)
    RelativeLayout mainLayout;

    @BindView(R.id.ble_connectingimg)
    ImageView bleConnectImageView;

    @BindView(R.id.ble_connectinglayout)
    LinearLayout bleConnectingLayout;

    @BindView(R.id.ble_connect_successlayout)
    LinearLayout bleConnectSuccessLayout;

    @BindView(R.id.dialog_ble_connect_fail_content)
    LinearLayout bleConnectFailLayout;

    @BindView(R.id.dialog_ble_search_content)
    LinearLayout bleSearchListDeviceLayout;

    @BindView(R.id.ble_connecting_device_nametv)
    AppCompatTextView connectingDeviceNameTextView;

    @BindView(R.id.deviceidtext)
    AppCompatTextView connectingDeviceIDTextView;

    @BindView(R.id.ble_connect_success_device_nametv)
    AppCompatTextView connectSuccessDeviceNameTextView;

    @BindView(R.id.ble_connect_success_deviceidtext)
    AppCompatTextView connectSuccessDeviceIDTextView;

    @BindView(R.id.ble_connect_fail_device_nametv)
    AppCompatTextView bleConnecFailtBleDeviceTextView;

    @BindView(R.id.ble_connect_fail_deviceidtext)
    AppCompatTextView connectFailDeviceIDTextView;

    @BindView(R.id.blereyclerviewdig)
    RecyclerView bleRecyclerView;

    @BindView(R.id.tv_title)
    TextView tv_title;

    private BaseQuickAdapter baseQuickAdapter;
    private BleDeviceCompartor bleDeviceCompartor = new BleDeviceCompartor();
    private Set<String> deviceSet = new HashSet<>();

    private int screenWidth;
    private int screenHeight;

    private DialogButtonClickListener mDialogButtonClickListener;

    private Context mContext;

    private String mDeviceID;

    private ScheduledExecutorService executorService = null;
    private AtomicInteger threadIdInterger = new AtomicInteger(0);

    private final int CLOSE_CONNECTED_SUCCESS_DIALOG_WHAT = 1;

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CLOSE_CONNECTED_SUCCESS_DIALOG_WHAT:
                    dismiss();
                    break;

                default:
                    break;
            }
        }
    };

    public BleConnectStatusDialog(Context context) {
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

        bleConnectImageView.setColorFilter(getContext().getResources().getColor(R.color.res_color_blue_374cff));
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
        return R.layout.dialog_ble_connect_status_layout;
    }

    @Override
    public void onCreateData(Context context) {

    }

    public void showTip(BleConnectStatus bleConnectStatus) {
        shutDownScheduledExecutorService();
        registerEventBus();
        clearData();
        switch (bleConnectStatus) {
            case Connecting:
                showConnectingView();
                break;

            case Connected:
                createScheduleSerivce();
                showConnectSuccessView();
                break;

            case ConnectedFail:
                showConnectFailView();
                break;

            case SearchListView:
                EventBus.getDefault().post(new DialogScanEvent(true));
                MLog.e("===========开始搜索 111 ");
                showSearchBleDeviceListView();
                break;
        }
        show();
    }

    public void setConnectDeviceIdText(String deviceID) {
        this.mDeviceID = deviceID;
    }

    @Override
    public void dismiss() {
        MLog.d("dimiss1");
        shutDownScheduledExecutorService();
        clearHandler();
        stopImageRotate(bleConnectImageView);
        mDeviceID = null;
        clearData();
        unRegisterEventBus();
        super.dismiss();
    }

    @Override
    public void onClick(View v, int id) {
        callBackButtonClickListener(id);

        switch (id) {
            case R.id.connectingcancelbtn:
                /**
                 * 连接中，取消按钮
                 */
                dismiss();
                break;

            case R.id.ble_connect_success_sure_btn:
                /** 连接成功确认按钮
                 * */
                dismiss();
                break;

            case R.id.ble_connect_fail_cancel_btn:
                /**
                 * 连接失败，取消按钮
                 */
                dismiss();
                break;

            case R.id.ble_connect_fail_retry_btn:
                /**
                 * 连接失败，重新连接按钮
                 */
                showConnectingView();
                dismiss();
                break;

            case R.id.researchbtn:
                clearData();
                EventBus.getDefault().post(new DialogScanEvent(true));
                break;

            case R.id.closebtn:
                EventBus.getDefault().post(new DialogScanEvent(false));
                dismiss();
                break;

            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScanEvent(ScanEvent scanEvent) {
        if (scanEvent.isSuccess()) {
            BluetoothLeDevice bluetoothLeDevice = scanEvent.getBluetoothLeDevice();
            String address = bluetoothLeDevice.getAddress();
            if (!StringUtils.isEmpty(address) && !this.deviceSet.contains(address)) {
                this.deviceSet.add(address);
                this.baseQuickAdapter.getData().add(bluetoothLeDevice);
                Collections.sort(this.baseQuickAdapter.getData(), bleDeviceCompartor);
                this.baseQuickAdapter.notifyDataSetChanged();

                return;
            }
            return;
        }
        ToastUtil.showShort("未发现可连接设备");
    }


    private void clearData() {
        if (null != deviceSet) {
            deviceSet.clear();
        }
        if (null != baseQuickAdapter) {
            baseQuickAdapter.getData().clear();
        }
    }

    private void clearHandler() {
        if (null != myHandler) {
            myHandler.removeCallbacksAndMessages(null);
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

    private void showConnectingView() {
        connectingDeviceIDTextView.setText(mDeviceID);
        connectingDeviceNameTextView.setText(getShowDeviceNameAndId(mDeviceID));
        bleConnectingLayout.setVisibility(View.VISIBLE);
        bleConnectFailLayout.setVisibility(View.GONE);
        bleConnectSuccessLayout.setVisibility(View.GONE);
        bleSearchListDeviceLayout.setVisibility(View.GONE);

        startRotate(bleConnectImageView);
    }

    private void showConnectSuccessView() {
        stopImageRotate(bleConnectImageView);
        connectSuccessDeviceIDTextView.setText(mDeviceID);
        connectSuccessDeviceNameTextView.setText(getShowDeviceNameAndId(mDeviceID));

        bleConnectingLayout.setVisibility(View.GONE);
        bleConnectFailLayout.setVisibility(View.GONE);
        bleSearchListDeviceLayout.setVisibility(View.GONE);
        bleConnectSuccessLayout.setVisibility(View.VISIBLE);

    }

    private void showConnectFailView() {
        stopImageRotate(bleConnectImageView);
        connectFailDeviceIDTextView.setText(mDeviceID);
        bleConnecFailtBleDeviceTextView.setText(getShowDeviceNameAndId(mDeviceID));
        bleConnectingLayout.setVisibility(View.GONE);
        bleConnectSuccessLayout.setVisibility(View.GONE);
        bleSearchListDeviceLayout.setVisibility(View.GONE);
        bleConnectFailLayout.setVisibility(View.VISIBLE);
    }

    private void showSearchBleDeviceListView() {
        stopImageRotate(bleConnectImageView);
        bleSearchListDeviceLayout.setVisibility(View.VISIBLE);
        bleConnectingLayout.setVisibility(View.GONE);
        bleConnectSuccessLayout.setVisibility(View.GONE);
        bleConnectFailLayout.setVisibility(View.GONE);
    }

    private String getShowDeviceNameAndId(String deviceId) {
        if (!CommonUtils.isEmpty(deviceId)) {
            String mac = Config.getConfig().getLastConnectBleGlassesMac();
            if (!CommonUtils.isEmpty(mac)) {
                if (mac.equals(deviceId)) {
                    String name = Config.getConfig().getLastConnectBleGlassesName();
                    return name;
                }
            }
        }
        return "";
    }


    /**
     * 开始旋转动画
     */
    private void startRotate(ImageView imageView) {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_round_rotate);
        LinearInterpolator linearInterpolator = new LinearInterpolator();//设置匀速旋转，在 xml 里面设置会卡顿
        animation.setInterpolator(linearInterpolator);
        if (null != imageView) {
            imageView.startAnimation(animation);
        }
    }

    private void stopImageRotate(ImageView imageView) {
        if (null != imageView) {
            imageView.clearAnimation();
        }
    }

    private void initListener() {
        //点击蓝牙设备进行连接
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

    private void createScheduleSerivce() {
        if (null == executorService || executorService.isTerminated() || executorService.isShutdown()) {
            executorService = ThreadPoolUtilsLocal.newScheduledThreadPool();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (hashCode() == threadIdInterger.get()) {
                        MLog.d("run close success");
                        Message message = myHandler.obtainMessage();
                        message.what = CLOSE_CONNECTED_SUCCESS_DIALOG_WHAT;
                        message.sendToTarget();
                    }
                }
            };
            threadIdInterger.set(runnable.hashCode());
            executorService.schedule(runnable, 0, TimeUnit.SECONDS);
        }
    }

    private void shutDownScheduledExecutorService() {
        if (null != executorService) {
            executorService.shutdownNow();
        }
        threadIdInterger.set(0);
    }
}
