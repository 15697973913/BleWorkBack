package com.zj.zhijue.fragment;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.stetho.common.LogUtil;
import com.google.gson.Gson;
import com.huige.library.utils.SharedPreferencesUtils;
import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.bindglasses.BindPersonalInfoActivity;
import com.zj.zhijue.activity.mine.RechargeTimeActivity;
import com.zj.zhijue.adapter.TrainTwoModeAdapter;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.RemindTimeBean;
import com.zj.zhijue.bean.TrainModeBean;
import com.zj.zhijue.bean.UseInfoBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesFeedbackBleDataBean;
import com.zj.zhijue.bean.bledata.send.SendBatteryLevelBean2;
import com.zj.zhijue.bean.bledata.send.SendControlCmdBean2;
import com.zj.zhijue.bean.bledata.send.SendRunningModeBean2;
import com.zj.zhijue.bean.bledata.send.SendTrainRealtimeRecordingBean2;
import com.zj.zhijue.bean.bledata.send.SendTrainTotalRecordingBean2;
import com.zj.zhijue.bean.bledata.send.SendUserInfoControlBleCmdBean;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.config.Constants;
import com.zj.zhijue.constant.BleConnectStatus;
import com.zj.zhijue.dialog.bleconnect.BleConnectStatusDialog;
import com.zj.zhijue.dialog.bleconnect.BleInputDeviceIDDialog;
import com.zj.zhijue.dialog.bleconnect.BleScanDialog;
import com.zj.zhijue.dialog.function.SynBleDataDialog;
import com.zj.zhijue.event.BatteryEvent;
import com.zj.zhijue.event.CmdBleDataEvent;
import com.zj.zhijue.event.DeviceElectricityEvent;
import com.zj.zhijue.event.SynBleDataEvent;
import com.zj.zhijue.event.UpRealLocalTimeEvent;
import com.zj.zhijue.event.UpRealTimeEvent;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IgetDeviceParam;
import com.zj.zhijue.http.request.IrecentlyTrainingDetail;
import com.zj.zhijue.http.response.HttpResponseGetRunParamsBean;
import com.zj.zhijue.http.response.HttpResponseGlassInitDataBackBean;
import com.zj.zhijue.http.response.HttpResponseMemberTrainTimeBean;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.listener.ItemOfViewPagerOnClickListener;
import com.zj.zhijue.listener.MainControlActivityListener;
import com.zj.zhijue.model.GlassesBleDataModel;
import com.zj.zhijue.model.TrainModel;
import com.zj.zhijue.util.AppUtils;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.DateUtil;
import com.zj.zhijue.util.GsonUtil;
import com.zj.zhijue.util.IToast;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.util.view.dropdownmenu.DropBean;
import com.zj.zhijue.view.circleimageview.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/11/16.
 */

public class TrainFragment extends BaseFragment implements ItemOfViewPagerOnClickListener {

//    @BindView(R.id.batteryview)
//    BatteryView mBatteryView;


    @BindView(R.id.ivAvatar)
    CircleImageView ivAvatar;

    @BindView(R.id.trainusernametext)
    TextView userNameTextView;

    @BindView(R.id.traintotalTime)
    TextView trainTotalTimeTv;
    @BindView(R.id.tvTimeRemaining)
    TextView tvTimeRemaining;

    @BindView(R.id.trainmodespinner)
    AppCompatSpinner trainModeSpinner;

    @BindView(R.id.ic_ble_disconnect)
    ImageView ic_ble_disconnect;
    @BindView(R.id.bleconnectstatustv)
    TextView bleConnectStatusTextView;

    @BindView(R.id.llBleStartAndStop)
    LinearLayout llBleStartAndStop;
    @BindView(R.id.ivBleStartAndStop)
    ImageView ivBleStartAndStop;
    @BindView(R.id.tvBleStartAndStop)
    TextView tvBleStartAndStop;

    @BindView(R.id.llBlePauseAndContinue)
    LinearLayout llBlePauseAndContinue;
    @BindView(R.id.ivBlePauseAndContinue)
    ImageView ivBlePauseAndContinue;
    @BindView(R.id.tvBlePauseAndContinue)
    TextView tvBlePauseAndContinue;

    @BindView(R.id.llBleIntervene)
    LinearLayout llBleIntervene;
    @BindView(R.id.ivBleIntervene)
    ImageView ivBleIntervene;
    @BindView(R.id.tvBleIntervene)
    TextView tvBleIntervene;

    @BindView(R.id.connectSwitch)
    Switch connectSwitch;
    @BindView(R.id.tvCurDayTime)
    TextView tvCurDayTime;
    @BindView(R.id.ivElectricity)
    ImageView ivElectricity;

    private LinearLayout mBleConnectLayout;
    TrainTwoModeAdapter trainModeAdapter;
    private TextView mBatteryTextView;

    private List<TrainModeBean> mTrainModeBeanList = new ArrayList<>();
    private List<DropBean> mTrainModeDropBeanList = new ArrayList<>();
    private Handler mHander = new Handler();
    private volatile AtomicBoolean connected = new AtomicBoolean(false);
    private MainControlActivityListener mainControlActivityListener;
    private BleScanDialog bleScanDialog;
    private BleInputDeviceIDDialog bleInputDeviceIDDialog;
    private BleConnectStatusDialog bleConnectStatusDialog;
    private SynBleDataDialog mSynBleDataDialog;

    //private BleDeviceListialog mBleDeviceListialog;
    private int sexSelectedSpinnerIndex = 0;

    //用户信息
    public static UseInfoBean useInfoBean;
    //设备参数信息（发送命令用）
    private HttpResponseGlassInitDataBackBean.DataBean mDeviceInfoBean;

    private BlueToothValueReceiver blueToothValueReceiver;

    //是否允许打开蓝牙连接设备
    public static boolean isAllowleOpen = false;

    //当前的状态
    public static int mCurControlCmd = 0;

    //假设原来眼镜的电量
    private  int oldElectricity=100;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();

        //注册广播，蓝牙状态监听
        blueToothValueReceiver = new BlueToothValueReceiver();
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        getActivity().registerReceiver(blueToothValueReceiver, filter);


        LogUtils.e("===========460914057 : " + Long.parseLong("460914057", 16) + "----07E4 " +
                Integer.parseInt("07E4", 16));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        initListener();

        if (!isAllowleOpen) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setWidgetConnectStatus(false);
                }
            }, 1000);
        }

        return view;
    }

    /**
     * 刷新所有的统计时间
     */
    private void refreshAllTime() {

        String userServerId = Config.getConfig().getUserServerId();
        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(
                getActivity(), userServerId);
        if (null != userInfoDBBeanList && userInfoDBBeanList.size() > 0) {
            UserInfoDBBean userInfoDBBean = userInfoDBBeanList.get(0);
            if (userInfoDBBean != null) {
                getUseInfo(userInfoDBBean.getPhone());
            }
        }

        getUserTrainTimeinf();
    }

    boolean isStart = true;

    @Override
    public void onStart() {
        super.onStart();

        LogUtils.e("===============onStart" + isAllowleOpen);
        if (isStart) {
            isStart = false;
            setWidgetConnectStatus(false);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainControlActivityListener = (MainControlActivityListener) getActivity();
    }

    private HttpResponseGlassInitDataBackBean dataBackBean;

    @Override
    public void onResume() {
        super.onResume();
        changeSpinnerByDebugModeSwitch();

        //若用户重新提交头像，临时处理下
        String loadUrl = SPUtils.getInstance().getString(SPUtils.KEY_UPLOAD_USER_AVATAR, "");
        if (!StringUtils.isEmpty(loadUrl)) {
            Glide.with(this)
                    .load(loadUrl)
                    .apply(new RequestOptions().placeholder(R.mipmap.qidongtubiao).error(R.mipmap.qidongtubiao))
                    .into(ivAvatar);
            SPUtils.getInstance().put(SPUtils.KEY_UPLOAD_USER_AVATAR, "");
        }

        String newStringDataJson = SPUtils.getInstance().getString(SPUtils.KEY_DEVICE_PARAM, "");
        if (mDeviceInfoBean == null && !StringUtils.isEmpty(newStringDataJson)) {
            dataBackBean =
                    (HttpResponseGlassInitDataBackBean) JsonUtil.json2objectWithDataCheck(
                            newStringDataJson, new TypeReference<HttpResponseGlassInitDataBackBean>() {
                            });
            mDeviceInfoBean = dataBackBean == null ? null : dataBackBean.getData();

            checkDeviceInfo();
        }

        if (mToDayTrainTimes == 0) {
            getUserTrainTimeinf();
        }

        String userServerId = Config.getConfig().getUserServerId();
        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(
                getActivity(), userServerId);
        if (null != userInfoDBBeanList && userInfoDBBeanList.size() > 0) {
            UserInfoDBBean userInfoDBBean = userInfoDBBeanList.get(0);
            if (userInfoDBBean != null) {
                getUseInfo(userInfoDBBean.getPhone());
            }
        }

    }

    /**
     * 检查是否填写视力信息，没有则跳转
     */
    private void checkDeviceInfo() {
        if (dataBackBean == null) {
            return;
        }

        //未填写视力信息
        if (TextUtils.equals(dataBackBean.getStatus(), "022")) {
            //视力信息
            ToastUtils.showShort(dataBackBean.getMessage());
            Intent mIntent = new Intent(getActivity(), BindPersonalInfoActivity.class);
            startActivity(mIntent);
        }
        LogUtils.e("==========123456 " + dataBackBean.getStatus() + "----" + dataBackBean.getMessage());

    }

    private long clickTime = 0;

    @SuppressLint("MissingPermission")
    private void initView(View view) {
        List<String> trainModeArray = null;
        int lastSelecteTrainModePostion = Config.getConfig().getLastSelectedTrainMode();
        if (Config.getConfig().getDebugModeSwitch()) {
            trainModeArray = Arrays.asList(getResources().getStringArray(R.array.debugtraintwomode));
        } else {
            trainModeArray = Arrays.asList(getResources().getStringArray(R.array.traintwomode));
            if (lastSelecteTrainModePostion >= trainModeArray.size()) {
                lastSelecteTrainModePostion = trainModeArray.size() - 1;
            }
        }
        trainModeAdapter = new TrainTwoModeAdapter(getActivity(), trainModeArray);
        trainModeSpinner.setAdapter(trainModeAdapter);

        trainModeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //如果正在计时则不允许切换模式
                if (TextUtils.equals("停止", tvBleStartAndStop.getText().toString())) {
                    if (new Date().getTime() - clickTime > 2000) {
                        clickTime = new Date().getTime();
                        ToastUtil.showLong("设备已经开始运行，不能切换模式");
                    }

                    return true;
                }
                return false;
            }
        });

//        trainModeSpinner.setSelection(lastSelecteTrainModePostion);
//        sexSelectedSpinnerIndex = lastSelecteTrainModePostion;
        trainModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //ToastUtil.showShortToast("position = " + position);
                LogUtils.i("onItemSelected position = " + position);
                sexSelectedSpinnerIndex = position;
                Config.getConfig().saveLastSelectedTrainMode(position);

                if (mainControlActivityListener == null || !mainControlActivityListener.isConnected()) {
                    return;
                }

                switch (position) {
                    case 0:
                        //训练模式
                        sendRunningMode(1);
                        break;
                    case 1:
                        //矫正模式
                        sendRunningMode(2);
                        break;
                    case 2:
                        //安全模式
                        sendRunningMode(3);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //showBatterLevel(Config.getConfig().getLastBattteryLevel());

        BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
        showBleConnectStatus(blueadapter.isEnabled());
    }

    private void initData() {
        String serverId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(serverId)) {
            BaseActivity.GotoLoginActivity();
            return;
        }
        Config.getConfig().saveBatteryLowTip(true);

        com.zj.zhijue.util.CommonUtils.setCurDayTimeTextView(0, tvCurDayTime);

        getDurationRemindTime();
    }


    /**
     * 停止设备
     */
    private void stopDevice() {
        llBleStartAndStop.setBackgroundResource(R.drawable.circle_shape_type_start);
        ivBleStartAndStop.setImageResource(R.drawable.ic_ble_type_start);
        tvBleStartAndStop.setText("开始");
        tvBleStartAndStop.setTextColor(Color.parseColor("#384dfe"));

        mCurControlCmd = 2;
        sendControlCmd(4);
        mTimeCount = -100;
    }

    @Override
    protected void initListener() {

        //开始,停止
        llBleStartAndStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainControlActivityListener == null || !mainControlActivityListener.isConnected()) {
                    return;
                }

                if (TextUtils.equals(tvBleStartAndStop.getText().toString(), "开始")) {
                    //开始
                    queryNewestRunParamBleDataFromServer();
                } else {
                    //停止
                    stopDevice();
                }

            }
        });

        //干预
        llBleIntervene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mainControlActivityListener == null || !mainControlActivityListener.isConnected()) {
                    return;
                }

                if (TextUtils.equals(tvBleStartAndStop.getText().toString(), "开始")) {
                    ToastUtil.showLong("请先开始");
                    return;
                }

                mCurControlCmd = 3;
                sendControlCmd(3);
            }
        });


        //暂停，继续
        llBlePauseAndContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mainControlActivityListener == null || !mainControlActivityListener.isConnected()) {
                    return;
                }

                if (TextUtils.equals(tvBleStartAndStop.getText().toString(), "开始")) {
                    ToastUtil.showLong("请先开始");
                    return;
                }

                if (TextUtils.equals(tvBlePauseAndContinue.getText(), "暂停")) {
                    //暂停
                    llBlePauseAndContinue.setBackgroundResource(R.drawable.circle_shape_type_continue);
                    ivBlePauseAndContinue.setImageResource(R.drawable.ic_ble_type_continue);
                    tvBlePauseAndContinue.setText("继续");
                    tvBlePauseAndContinue.setTextColor(Color.parseColor("#384dfe"));

                    mCurControlCmd = 1;
                    sendControlCmd(1);
                    mTimeCount = -100;
                } else {
                    //继续
                    llBlePauseAndContinue.setBackgroundResource(R.drawable.circle_shape_type_pause);
                    ivBlePauseAndContinue.setImageResource(R.drawable.ic_ble_type_pause);
                    tvBlePauseAndContinue.setText("暂停");
                    tvBlePauseAndContinue.setTextColor(Color.parseColor("#29CAA6"));

                    mCurControlCmd = 4;
                    sendControlCmd(2);

                    mTimeCount = -100;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTimeCount = 0;
                            new Thread(mPollRunnable).start();
                        }
                    }, 1200);
                }

            }
        });

//        batteryCloseLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                handleRemoteControlViewClick(5);
//            }
//        });

        //连接
        connectSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                isAllowleOpen = b;
                BleDeviceManager.getInstance().isAllowConn = b;

                if (b) {
                    //开始连接
                    if (mainControlActivityListener == null || !mainControlActivityListener.isConnected()) {
                        BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
                        blueadapter.enable();
                        showScanTipDialog();
                    }
                } else {
                    //关闭连接
                    diConnectDevice();
                }
            }
        });

    }

    /**
     * 断开蓝牙设备
     */
    public void diConnectDevice() {
        ivElectricity.setVisibility(View.GONE);
        BleDeviceManager.getInstance().disconnectLightBleDevice();
        setBleConnectStatus(false);
    }

    private void changeBatteryViewColor(boolean isCharging, int batteryLevel) {
//        mBatteryView.setVisibility(View.VISIBLE);
//
//        if (isCharging) {
//            mBatteryView.setInnerRectColor(getResources().getColor(R.color.right_eye_sight_color));
//        } else {
//            mBatteryView.setInnerRectColor(getResources().getColor(R.color.battery_color));
//        }
//        mBatteryView.setPower(batteryLevel);
        if (batteryLevel <= 1) {
            if (Config.getConfig().getNeedLowTip()) {
                showBatterLowTipDialog();
            }
        } else {
            dismissBatteryLowDialog();
        }
    }

    /**
     * 是否第一次训练模式切换
     */
    private boolean isFirstTrainMode = true;

    /**
     * 向蓝牙发送控制命令
     * 0x00：开始；
     * 0x01：暂停；
     * 0x02：继续；
     * 0x03：干预；
     * 0x04：结束。
     */
    private void sendControlCmd(int cmd) {

        //第一次默认设置训练模式
        if (isFirstTrainMode) {
            isFirstTrainMode = false;
            trainModeSpinner.setSelection(0);
        }

        SendControlCmdBean2 sendControlCmdBean2 = new SendControlCmdBean2();
        sendControlCmdBean2.setCmd(cmd);
        sendControlCmdBean2.setYear(DateUtil.getAssignTimeType(1));
        sendControlCmdBean2.setMonth(DateUtil.getAssignTimeType(2));
        sendControlCmdBean2.setDay(DateUtil.getAssignTimeType(3));
        sendControlCmdBean2.setHour(DateUtil.getAssignTimeType(4));
        sendControlCmdBean2.setMinute(DateUtil.getAssignTimeType(5));
        sendControlCmdBean2.setSeconds(DateUtil.getAssignTimeType(6));
        sendControlCmdBean2.setUtc(DateUtil.getUTC());
        sendControlCmdBean2.setUtcTime(new Date().getTime() / 1000);

        byte[] data = sendControlCmdBean2.buildCmdByteArray();
        EventBus.getDefault().post(new CmdBleDataEvent(data));

        LogUtils.e(Config.getConfig().getDecodeUserName() + " ======== 向蓝牙发送控制命令  " +
                BaseParseCmdBean.bytesToStringWithSpace(data) + "----\n" + sendControlCmdBean2.toString());
    }

    private int runCount = 0;

    /**
     * APP发送眼镜运行模式
     * 0x00：预留
     * 0x01：训练模式
     * 0x02：矫正模式；
     * 0x03：安全模式；
     */
    private void sendRunningMode(int cmd) {
//        //前面两次不处理
//        runCount++;
//        if (runCount < 3) {
//            return;
//        }

        SendRunningModeBean2 sendRunningModeBean2 = new SendRunningModeBean2();
        sendRunningModeBean2.setCmd(cmd);

        byte[] data = sendRunningModeBean2.buildCmdByteArray();
        EventBus.getDefault().post(new CmdBleDataEvent(data));

        LogUtils.e(Config.getConfig().getDecodeUserName() + " ======== APP发送眼镜运行模式  " +
                BaseParseCmdBean.bytesToStringWithSpace(data) + "----\n" + sendRunningModeBean2.toString());



    }


    /**
     * APP发送获取训练实时记录数据
     */
    private void sendTrainRealtimeRecordingBean2() {

        SendTrainRealtimeRecordingBean2 recordingBean2 = new SendTrainRealtimeRecordingBean2();

        byte[] data = recordingBean2.buildCmdByteArray();
        EventBus.getDefault().post(new CmdBleDataEvent(data));

        LogUtils.e(Config.getConfig().getDecodeUserName() + " ======== APP发送获取训练实时记录数据  " +
                BaseParseCmdBean.bytesToStringWithSpace(data));

    }

    /**
     * APP发送获取眼镜训练累积记录数据
     */
    private void sendTrainTotalRecordingBean2() {

        SendTrainTotalRecordingBean2 recordingBean2 = new SendTrainTotalRecordingBean2();

        byte[] data = recordingBean2.buildCmdByteArray();
        EventBus.getDefault().post(new CmdBleDataEvent(data));

        LogUtils.e(Config.getConfig().getDecodeUserName() + " ======== APP发送获取眼镜训练累积记录数据  " +
                BaseParseCmdBean.bytesToStringWithSpace(data));

    }


    @Override
    public void onClickIndex(int index, int resourceId) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveBattery(BatteryEvent batteryEvent) {
        if (null != batteryEvent) {
            //Config.getConfig().saveLastBatterLevel(batteryEvent.getBattery());
            showBatterLevel(batteryEvent.getBattery());

            if (SendUserInfoControlBleCmdBean.STOP_OPERATION_CODE == batteryEvent.getRunStatus()) {
                /**
                 * 停止状态
                 */
                changeSpinnerStatus(true);

            } else {
                /**
                 * 非停止状态
                 */
                changeSpinnerStatus(false);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpRealTimeEvent(UpRealTimeEvent upRealTimeEvent) {
        if (upRealTimeEvent.isSuccess()) {
            //Config.getConfig().saveLastBatterLevel(batteryEvent.getBattery());
            if (mCurControlCmd == 2) {
                /**
                 * 停止状态
                 */
                //刷新所有时间
                refreshAllTime();
            }
        }
    }

    /**
     * 显示蓝牙时间
     *
     * @param upReallocalTimeEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpRealLocalTimeEvent(UpRealLocalTimeEvent upReallocalTimeEvent) {
        if (upReallocalTimeEvent.getTime() != 0) {
            if (mCurControlCmd != 2) {
                com.zj.zhijue.util.CommonUtils.setCurDayTimeTextView(
                        mToDayTrainTimes + upReallocalTimeEvent.getTime(),
                        tvCurDayTime);

                LogUtils.v("显示蓝牙返回时间：Time:mToDayTrainTimes: " + mToDayTrainTimes +
                        "\ntotalTraingSeconds: " + GlassesBleDataModel.totalTraingSeconds);
            }
        }
    }

    /**
     * 当电量变了之后执行的方法
     *
     * @param deviceElectricityEvent
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onElectricityChanageEvent(DeviceElectricityEvent deviceElectricityEvent) {
        ivElectricity.setVisibility(View.VISIBLE);

        int electricity=deviceElectricityEvent.getElectricity();

        if (oldElectricity>20&&electricity <= 20&&electricity>10) {
            IToast.show(getActivity(),"设备电量不足5%，请及时充电");
        }else if (oldElectricity>10&&electricity<= 10&&electricity>5) {
            IToast.show(getActivity(),"设备电量不足5%，请及时充电");
        }else if (oldElectricity>5&&electricity <= 5) {
            IToast.show(getActivity(),"设备电量不足5%，请及时充电");
        }
        oldElectricity=electricity;


        int electricityRatio = electricity / 20;


        if (electricityRatio > 5) {
            electricityRatio = 5;
        }
        if (electricityRatio < 0) {
            electricityRatio = 0;
        }

        int resID = getResources().getIdentifier("ic_electricity" + electricityRatio, "mipmap", "com.zj.zhijue");
        ivElectricity.setImageResource(resID);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveSynStatusEvent(SynBleDataEvent synBleDataEvent) {

        LogUtils.e("======== 222 " + synBleDataEvent);
        if (null != synBleDataEvent) {
            if (null != mainControlActivityListener && mainControlActivityListener.isConnected()) {
                //让电池的电量重新回到100
                oldElectricity=100;


                handleSysDataResult(synBleDataEvent);

                sendRunningMode(sexSelectedSpinnerIndex + 1);
            } else {
                dismissSynDialog();
            }
        }
    }

    private long synTime = 0;

    private void handleSysDataResult(SynBleDataEvent synBleDataEvent) {
        /**
         * 同步数据的时候，关闭蓝牙连接的 Dialog
         */
        dismissConnectStatusDialog();

        if (synBleDataEvent.isStartSyn()) {
            showSynBleDialog(true, -2, null);
        } else {
            if (synBleDataEvent.isSynSuccess()) {
                if (new Date().getTime() - synTime > 2000) {
                    synTime = new Date().getTime();
                    ToastUtil.showLong("同步数据成功");

                    mHander.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //查询电量
                            BleDeviceManager.getInstance().sendBatteryLevelBean2();
                        }
                    },1000);
                }
                dismissSynDialog();
                TrainModel.getInstance().createTimerAndTask();
            } else {
                dismissSynDialog();
            }
        }

    }

    private void showBatterLevel(int batterLevel) {
        StringBuilder batteryBuilder = new StringBuilder();
        //batteryBuilder.append("电量:");
        boolean isCharging = false;
        if (batterLevel >= 128) {
            isCharging = true;
            //batteryBuilder.append(ReceiveGlassesFeedbackBleDataBean.calcBattery(batterLevel) + "%" + "(充电中)");
            batteryBuilder.append(ReceiveGlassesFeedbackBleDataBean.calcBattery(batterLevel) + "%");
        } else {
            isCharging = false;
            batteryBuilder.append(ReceiveGlassesFeedbackBleDataBean.calcBattery(batterLevel) + "%");
        }
//        batteryTextView.setText(batteryBuilder.toString());

        changeBatteryViewColor(isCharging, ReceiveGlassesFeedbackBleDataBean.calcBattery(batterLevel));
    }

    public void setBleConnectStatus(boolean connected) {
        this.connected.set(connected);

        setWidgetConnectStatus(connected);

        LogUtils.e("============== 蓝牙连接状态 " + connected);

        //若蓝牙已经连接则开启
        if (connected) {
            isAllowleOpen = true;
            connectSwitch.setChecked(true);
        } else {
            mTimeCount = -100;
            isAllowleOpen = false;
            connectSwitch.setChecked(false);
            dismissSynDialog();
        }
    }

    private void showBleConnectStatus(boolean bleConnected) {
        if (bleConnected) {
            bleConnectStatusTextView.setText("蓝牙已打开");
            bleConnectStatusTextView.setTextColor(Color.parseColor("#3745FF"));
            ic_ble_disconnect.setColorFilter(Color.parseColor("#3745FF"));
        } else {
            bleConnectStatusTextView.setText("蓝牙未打开");
            bleConnectStatusTextView.setTextColor(Color.parseColor("#ff6666"));
            ic_ble_disconnect.setColorFilter(Color.parseColor("#00000000"));
        }

    }

    /**
     * 设置控件的连接状态
     */
    private void setWidgetConnectStatus(boolean bleConnected) {
        if (bleConnected) {
            llBleStartAndStop.setBackgroundResource(R.drawable.circle_shape_type_start);
            ivBleStartAndStop.setImageResource(R.drawable.ic_ble_type_start);
            ivBleStartAndStop.setColorFilter(Color.parseColor("#00000000"));
            tvBleStartAndStop.setText("开始");
            tvBleStartAndStop.setTextColor(Color.parseColor("#3745FF"));

            llBleIntervene.setBackgroundResource(R.drawable.circle_shape_type_intervene);
            ivBleIntervene.setImageResource(R.drawable.ic_ble_type_intervene);
            ivBleIntervene.setColorFilter(Color.parseColor("#FFA61B"));
            tvBleIntervene.setTextColor(Color.parseColor("#FFA61B"));

            llBlePauseAndContinue.setBackgroundResource(R.drawable.circle_shape_type_pause);
            ivBlePauseAndContinue.setImageResource(R.drawable.ic_ble_type_pause);
            ivBlePauseAndContinue.setColorFilter(Color.parseColor("#00000000"));
            tvBlePauseAndContinue.setText("暂停");
            tvBlePauseAndContinue.setTextColor(Color.parseColor("#29CAA6"));


        } else {
            llBleStartAndStop.setBackgroundResource(R.drawable.circle_shape_type_default);
            ivBleStartAndStop.setColorFilter(Color.parseColor("#D7D6F3"));
            tvBleStartAndStop.setTextColor(Color.parseColor("#D7D6F3"));

            llBleIntervene.setBackgroundResource(R.drawable.circle_shape_type_default);
            ivBleIntervene.setColorFilter(Color.parseColor("#D7D6F3"));
            tvBleIntervene.setTextColor(Color.parseColor("#D7D6F3"));

            llBlePauseAndContinue.setBackgroundResource(R.drawable.circle_shape_type_default);
            ivBlePauseAndContinue.setColorFilter(Color.parseColor("#D7D6F3"));
            tvBlePauseAndContinue.setTextColor(Color.parseColor("#D7D6F3"));

        }
    }

    private void changeSpinnerStatus(boolean enable) {
        trainModeAdapter.setItemSelectable(enable);
        trainModeAdapter.notifyDataSetChanged();
        trainModeSpinner.setEnabled(enable);
    }

    /**
     * 改变Spinner  Item 的个数
     */
    private void changeSpinnerByDebugModeSwitch() {
        List<String> trainModeArray = null;
        int lastSelecteTrainModePostion = Config.getConfig().getLastSelectedTrainMode();
        if (Config.getConfig().getDebugModeSwitch()) {
            trainModeArray = Arrays.asList(getResources().getStringArray(R.array.debugtraintwomode));
        } else {
            trainModeArray = Arrays.asList(getResources().getStringArray(R.array.traintwomode));
            if (lastSelecteTrainModePostion >= trainModeArray.size()) {
                trainModeSpinner.setSelection(trainModeArray.size() - 1);
            }
        }
        trainModeAdapter.setDataList(trainModeArray);
        trainModeAdapter.notifyDataSetChanged();
    }

    public void showSynBleDialog(boolean showSynProgrees, long userCodeId, String glassStatus) {
        if (null == mSynBleDataDialog) {
            mSynBleDataDialog = new SynBleDataDialog(getActivity());
            mSynBleDataDialog.setDialogButtonClickListener(new DialogButtonClickListener() {
                @Override
                public void onButtonClick(int resourceId) {

                    LogUtils.e("========" + resourceId);

                    if (resourceId == R.id.synuserinfo2glassbtn) {
                        /**
                         * 停止眼镜运行，并同步当前用户的信息（机器数据，用户数据，运行参数）
                         */
                        dismissSynDialog();
                        mainControlActivityListener.stopActionFromTrainFragment();
                        GlassesBleDataModel.getInstance().checkGlassesCurrentStatus(true);
                    } else if (resourceId == R.id.synagainbtn) {
                        dismissSynDialog();
                        /**
                         * 同步失败的情况下，不能发送停止指令，因为上传的运行参数，不知道是哪个用户的，必须在获取到用户ID的情况下，才能发送停止指令
                         */
                        //mainControlActivityListener.stopActionFromTrainFragment();
                        GlassesBleDataModel.getInstance().checkGlassesCurrentStatus(true);
                    }
                }
            });
        }

        if (!showSynProgrees) {
            mSynBleDataDialog.showCurrentUserCode(userCodeId, glassStatus);
        }

        mSynBleDataDialog.showSynText("同步数据中...");
        mSynBleDataDialog.showSynDialog(showSynProgrees);
    }

    /**
     * 展示同步失败的 Dialog
     */
    private void showSynFailureDialog() {
        if (null != mSynBleDataDialog) {
            mSynBleDataDialog.showSynFailDialog();
        }
    }

    public void dismissSynDialog() {
        if (null != mSynBleDataDialog) {
            mSynBleDataDialog.dismiss();
        }
    }

    /**
     * 扫描二维码
     */
    private void scanBleDeviceQrcoder() {
        if (null != mainControlActivityListener) {
            mainControlActivityListener.scanBleDeviceQrCoder();
        }
    }

    public void showScanTipDialog() {
        //直接显示蓝牙连接搜索弹框
        showConnectStatusDialog(null, BleConnectStatus.SearchListView);

//        if (null == bleScanDialog) {
//            bleScanDialog = new BleScanDialog(getActivity());
//            bleScanDialog.setDialogButtonClickListener(new DialogButtonClickListener() {
//                @Override
//                public void onButtonClick(int resourceId) {
//                    if (resourceId == R.id.blescanbtn) {
//                        scanBleDeviceQrcoder();
//                    } else if (resourceId == R.id.inputdeviceidbtn) {
//                        dismissScanTipDialog();
//                        showInputDeviceIdDialog();
//                    } else if (resourceId == R.id.searchbledevicebtn) {
//                        if (mainControlActivityListener.checkLocationServiceOk()) {
//                            dismissScanTipDialog();
//                            showConnectStatusDialog(null, BleConnectStatus.SearchListView);
//                        }
//                    }
//                }
//            });
//        }
//        bleScanDialog.showTip();
    }

    public void dismissScanTipDialog() {
        if (null != bleScanDialog) {
            bleScanDialog.dismiss();
        }
    }

    public void dismissBleInputDeviceIdDialog() {
        if (null != bleInputDeviceIDDialog) {
            bleInputDeviceIDDialog.dismiss();
        }
    }

    public void showConnectStatusDialog(String deviceId, BleConnectStatus bleConnectStatus) {
        if (null == bleConnectStatusDialog) {
            bleConnectStatusDialog = new BleConnectStatusDialog(getActivity());
            bleConnectStatusDialog.setDialogButtonClickListener(new DialogButtonClickListener() {
                @Override
                public void onButtonClick(int resourceId) {
                    switch (resourceId) {
                        case R.id.connectingcancelbtn:
                            /***
                             * 连接中 Dialog 中的 取消 按钮
                             */
                            mainControlActivityListener.quitConnectBleDevice();
                            break;

                        case R.id.ble_connect_fail_retry_btn:
                            /**
                             * 连接失败中 Dialog , 重新连接 按钮
                             */
                            mainControlActivityListener.retryConnectBleDevice();
                            break;

                        case R.id.ble_connect_fail_cancel_btn:
                            /**
                             * 连接失败 Dialog 中的 取消 按钮
                             */
                            mainControlActivityListener.quitConnectBleDevice();
                            break;
                        case R.id.closebtn:
                            //点击关闭
                            connectSwitch.setChecked(false);
                            break;

                        default:
                            LogUtils.d("default " + resourceId);
                            break;
                    }
                }
            });
        }
        bleConnectStatusDialog.setConnectDeviceIdText(deviceId);
        bleConnectStatusDialog.showTip(bleConnectStatus);
    }

    public void dismissConnectStatusDialog() {
        if (null != bleConnectStatusDialog) {
            bleConnectStatusDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        unregisterEventBus();
        dismissSynDialog();
        dismissBleInputDeviceIdDialog();
        dismissScanTipDialog();
        getActivity().unregisterReceiver(blueToothValueReceiver);
        super.onDestroy();

        GlassesBleDataModel.saveTrainingList();

        mTimeCount = -100;
    }

    /**
     * 广播监听蓝牙状态
     */
    public class BlueToothValueReceiver extends BroadcastReceiver {
        public int DEFAULT_VALUE_BULUETOOTH = 1000;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, DEFAULT_VALUE_BULUETOOTH);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        LogUtils.d("蓝牙已关闭");
                        showBleConnectStatus(false);
                        break;
                    case BluetoothAdapter.STATE_ON:
                        LogUtils.d("蓝牙已打开");
                        showBleConnectStatus(true);
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        LogUtils.d("正在打开蓝牙");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        LogUtils.d("正在关闭蓝牙");
                        break;
                    default:
                        LogUtils.d("未知状态");
                        break;
                }
            }
        }
    }

    //剩余训练时长
    public static long mRemainTime = 0;

    private void getUseInfo(String phone) {
        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("phone", phone);
        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UseInfoBean useInfo = (UseInfoBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<UseInfoBean>() {
                });
                if (useInfo != null) {
                    if (useInfo.getStatus().equals("success")) {
                        SharedPreferencesUtils.put(Constants.USER_INFO, new Gson().toJson(useInfo));

                        String gs = (String) SharedPreferencesUtils.get(Constants.USER_INFO, "");
                        if (!TextUtils.isEmpty(gs)) {
                            useInfoBean = GsonUtil.GsonToBean(gs, UseInfoBean.class);
                        }

                        GlassesBleDataModel.getInstance().initUserInfo();

                        //初始化用户信息
                        if (null != useInfoBean) {
                            userNameTextView.setText(useInfoBean.getData().getName());
                            Glide.with(getActivity())
                                    .load(com.zj.zhijue.util.CommonUtils.diffAvatar(URLConstant.BASE_URL_FIRST_PARTY, useInfoBean.getData().getFace()))
                                    .apply(new RequestOptions().placeholder(R.mipmap.qidongtubiao).error(R.mipmap.qidongtubiao))
                                    .into(ivAvatar);

                            //训练总时长
                            if (useInfoBean.getData().getUsedTime() != null) {
                                trainTotalTimeTv.setText(DateUtil.secondToTime2(DateUtil.getSecond(useInfoBean.getData().getUsedTime())));
                            } else {
                                trainTotalTimeTv.setText("0天\n0小时0分0秒");
                            }

                            //剩余训练时长
                            mRemainTime = 0;
                            if (useInfoBean.getData().getTotalTime() != null && useInfoBean.getData().getUsedTime() != null) {
                                mRemainTime = DateUtil.getSecond(useInfoBean.getData().getTotalTime()) -
                                        DateUtil.getSecond(useInfoBean.getData().getUsedTime());
                                String time = DateUtil.secondToTime2(mRemainTime);
                                tvTimeRemaining.setText(time);
                            } else if (useInfoBean.getData().getTotalTime() != null && useInfoBean.getData().getUsedTime() == null) {
                                mRemainTime = DateUtil.getSecond(useInfoBean.getData().getTotalTime());
                                tvTimeRemaining.setText(DateUtil.secondToTime2(mRemainTime));
                            } else {
                                tvTimeRemaining.setText("0天\n0小时0分0秒");
                            }

                            //保存极光别名
                            JPushInterface.setAlias(getActivity(), 1, useInfoBean.getData().getPhone());

                        }
                    } else {
                        ToastUtil.showShort(useInfo.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
        TrainSuscribe.getUseInfo(headerMap, bodyMap, disposableObserver);
    }

    /**
     * 是否填写了视力信息 true填写，false未填写
     */
    public static boolean isFillInVisionInfo = false;


    //今天的训练时间 单位s
    private int mToDayTrainTimes = 0;

    /**
     * 获取用户的训练时间数据
     */
    public void getUserTrainTimeinf() {

        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("memberId", Config.getConfig().getUserServerId());
        bodyMap.put("bt", DateUtil.getAssignDate(new Date().getTime(), 1) + " 00:00:00");
        bodyMap.put("et", DateUtil.getAssignDate(new Date().getTime() + 24 * 60 * 60 * 1000, 1) + " 00:00:00");
//        bodyMap.put("deviceId", Config.getConfig().getLastConnectBleGlassesUUID());
        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {

                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                HttpResponseMemberTrainTimeBean trainTimeBean =
                        (HttpResponseMemberTrainTimeBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseMemberTrainTimeBean>() {
                        });

                if (trainTimeBean != null) {
                    GlassesBleDataModel.totalTraingSeconds = 0;
                    for (HttpResponseMemberTrainTimeBean.DataBean dataBean : trainTimeBean.getData()) {
                        mToDayTrainTimes = dataBean.getTrainTimeHour() * 60 * 60 +
                                dataBean.getTrainTimeMinute() * 60 +
                                dataBean.getTrainTimeSecond();
                        com.zj.zhijue.util.CommonUtils.setCurDayTimeTextView(
                                mToDayTrainTimes + GlassesBleDataModel.totalTraingSeconds,
                                tvCurDayTime);
                        LogUtils.v("显示网络返回时间：Time:mToDayTrainTimes: " + mToDayTrainTimes +
                                "\ntotalTraingSeconds: " + GlassesBleDataModel.totalTraingSeconds);
                        break;
                    }
                }

                HttpResponseMemberTrainTimeBean httpResponseMemberTrainTimeBean = (HttpResponseMemberTrainTimeBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseMemberTrainTimeBean>() {
                });
                //LogUtils.d("httpResponseMemberTrainTimeBean = " + httpResponseMemberTrainTimeBean);
                //SdLogUtil.writeCommonLog("httpResponseMemberTrainTimeBean = " + httpResponseMemberTrainTimeBean);
                TrainModel.getInstance().saveTrainTime2DB(httpResponseMemberTrainTimeBean, null, true);

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        };
        LogUtils.v("bodyMap:"+bodyMap);
        TrainSuscribe.getUserTrainTimeInfo(headerMap, bodyMap, disposableObserver);

    }

    /**
     * 用于停止轮询线程
     */
    public static int mTimeCount = 0;

    /**
     * 轮询发送查询命令
     */
    private Runnable mPollRunnable = new Runnable() {
        @Override
        public void run() {
            LogUtils.e("mPollRunnable.run " + mRemainTime);

            while (mTimeCount >= 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                LogUtils.e("=============== mRemainTime: " + mRemainTime);
                //若可用剩余时长为0则断开蓝牙
                if (--mRemainTime <= 0) {
                    mRemainTime--;
                    LogUtils.e("==============000 剩余时长不足，请充值");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showLong("剩余时长不足，请充值");
                            stopDevice();
                        }
                    });


                    diConnectDevice();

                }

                //若可用剩余时长==设定时长则提醒 2020-07-25 16:42:29
                if (mRemindTimeBean != null && mRemainTime == 60 * com.zj.zhijue.util.CommonUtils.getInt(mRemindTimeBean.getData().getRemindTime())
                        && TextUtils.equals(mRemindTimeBean.getData().getIsEnable(), "Y")) {
                    sendTimeRemind(mRemindTimeBean.getData().getRemindTime());
                }

                //每十秒发送一次查询命令
                if (mTimeCount % 10 == 0) {
//                    //设置今日训练时长
//                    if (getActivity() != null) {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (mCurControlCmd != 2) {
//                                    com.zj.zhijue.util.CommonUtils.setCurDayTimeTextView(
//                                            mToDayTrainTimes + GlassesBleDataModel.totalTraingSeconds,
//                                            tvCurDayTime);
//
//                                    LogUtils.e("============ 今日训练时长mToDayTrainTimes: " + mToDayTrainTimes + "---totalTraingSeconds:" + GlassesBleDataModel.totalTraingSeconds);
//                                }
//
//                            }
//                        });
//                    }


                    sendTrainTotalRecordingBean2();
                    //延时发送
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendTrainRealtimeRecordingBean2();

                    //延时发送
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                mTimeCount++;
            }

        }
    };

    /**
     * 查询是否填写了视力信息
     */
    public void queryNewestRunParamBleDataFromServer() {
        final String memeberId = Config.getConfig().getUserServerId();

        if (com.android.common.baselibrary.util.comutil.CommonUtils.isEmpty(memeberId)) {
            return;
        }

        HashMap<String, Object> headerMap = new HashMap<>();

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IrecentlyTrainingDetail.MEMBERID, memeberId);
        bodyMap.put(IrecentlyTrainingDetail.UTDID, AppUtils.getUtdId());

        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String newStringDataJson = null;
                    try {
                        newStringDataJson = new String(responseBody.bytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //LogUtils.d("queryNewestRunParamBleDataFromServer newStringDataJson  = " + newStringDataJson);

                    HttpResponseGetRunParamsBean runParamsBean = (HttpResponseGetRunParamsBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseGetRunParamsBean>() {
                    });


                    if (null != runParamsBean && runParamsBean.getStatus().equals("022")) {
                        //未填写视力信息
                        ToastUtils.showShort(runParamsBean.getMessage());
                        Intent mIntent = new Intent(getActivity(), BindPersonalInfoActivity.class);
                        mIntent.putExtra("useInfoBean", useInfoBean.getData());
                        startActivity(mIntent);
                    } else {
                        //进行下一步
                        getGlassesInitData();
                    }
                    //LogUtils.d("Server mHttpResponseGlassesRunParamBean = " + mHttpResponseGlassesRunParamBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
        TrainSuscribe.queryRunParamData(headerMap, bodyMap, disposableObserver);
    }

    /**
     * 获取眼镜的初始化参数
     */
    public void getGlassesInitData() {
        final String memberId = Config.getConfig().getUserServerId();

        if (com.android.common.baselibrary.util.comutil.CommonUtils.isEmpty(memberId)) {
            return;
        }

        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IgetDeviceParam.MEMBERID, memberId);
        bodyMap.put(IgetDeviceParam.UTDID, AppUtils.getUtdId());

        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String newStringDataJson = null;
                    try {
                        newStringDataJson = new String(responseBody.bytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    SPUtils.getInstance().put(SPUtils.KEY_DEVICE_PARAM, newStringDataJson);

                    HttpResponseGlassInitDataBackBean dataBackBean =
                            (HttpResponseGlassInitDataBackBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseGlassInitDataBackBean>() {
                            });

                    LogUtils.e("=================== " + dataBackBean.getStatus() + "--- " + dataBackBean.getMessage());

                    if (!TextUtils.equals(dataBackBean.getStatus(), "023")) {
                        //剩余时长大于0 可以执行其他操作

                        llBleStartAndStop.setBackgroundResource(R.drawable.circle_shape_type_stop);
                        ivBleStartAndStop.setImageResource(R.drawable.ic_ble_type_stop);
                        tvBleStartAndStop.setText("停止");
                        tvBleStartAndStop.setTextColor(Color.parseColor("#FF3000"));

                        mCurControlCmd = 0;
                        sendControlCmd(0);

                        mTimeCount = -100;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mTimeCount = 0;
                                new Thread(mPollRunnable).start();
                            }
                        }, 1200);
                    } else {
                        ToastUtil.showLong(dataBackBean.getMessage());
                        //剩余时长=0，弹框提示充值
                        Intent intent = new Intent(getActivity(), RechargeTimeActivity.class);
                        startActivity(intent);
                    }
                } finally {
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
        TrainSuscribe.getGlassDeviceInitParam(headerMap, bodyMap, disposableObserver);

    }

    //获取时长提醒时间
    RemindTimeBean mRemindTimeBean;

    /**
     * 获取时长提醒时间
     */
    private void getDurationRemindTime() {
        if (!checkNetworkAvaliable()) {
            return;
        }
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();
        LoginSubscribe.getDurationRemindTime(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {

                String jsonBody = null;
                try {
                    jsonBody = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LogUtils.d("Server login jsonBody = " + jsonBody);
                mRemindTimeBean = (RemindTimeBean) JsonUtil.json2objectWithDataCheck(jsonBody, new TypeReference<RemindTimeBean>() {
                });
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        });

    }

    /**
     * 发送时长不足推送消息
     */
    private void sendTimeRemind(final String time) {
        if (!checkNetworkAvaliable()) {
            return;
        }
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("time", time);//time	String	Y	时间(分钟)
        //mVerificationCodResponseBean = null;
        LoginSubscribe.sendTimeRemind(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        });


    }


}
