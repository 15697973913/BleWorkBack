package com.zj.zhijue.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.common.baselibrary.blebean.BaseCmdBean;
import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.ActivityStackUtil;
import com.android.common.baselibrary.util.BleHexUtil;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.xsnow.permission.OnPermissionCallback;
import com.vise.xsnow.permission.PermissionManager;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseBleActivity;
import com.zj.zhijue.bean.bledata.BleDataBeanConvertUtil;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam1BleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam2BleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam3BleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam4BleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam5BleDataBean;
import com.zj.zhijue.bean.event.DialogBleConnectEvent;
import com.zj.zhijue.bean.event.DialogScanEvent;
import com.zj.zhijue.bean.response.HttpResponseGlassesRunParamBean;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.callback.CheckBleMacByServerCallBack;
import com.zj.zhijue.constant.BleConnectStatus;
import com.zj.zhijue.constant.ConstantString;
import com.zj.zhijue.contracts.BleMainControlContract;
import com.zj.zhijue.event.ConnectEvent;
import com.zj.zhijue.event.EventConstant;
import com.zj.zhijue.event.NotifyDataEvent;
import com.zj.zhijue.fragment.FunctionFragment;
import com.zj.zhijue.fragment.MineFragment;
import com.zj.zhijue.fragment.ReportFormFragment;
import com.zj.zhijue.fragment.TrainFragment;
import com.zj.zhijue.listener.MainControlActivityListener;
import com.zj.zhijue.model.AppUpdateModel;
import com.zj.zhijue.model.TrainModel;
import com.zj.zhijue.presenter.BleMainControlPresenter;
import com.zj.zhijue.service.ForegroundService;
import com.zj.zhijue.util.Config;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/1.
 */

public class BleMainControlActivity extends BaseBleActivity implements BleMainControlContract.View, MainControlActivityListener {
    @BindView(R.id.trainlayout)
    LinearLayout trainLayout;

    @BindView(R.id.reportformslayout)
    LinearLayout reportFormsLayout;

    @BindView(R.id.signinlayout)
    LinearLayout signInLayout;

    @BindView(R.id.minelayout)
    LinearLayout mineLayout;

    @BindView(R.id.trainimage)
    ImageView trainImageView;

    @BindView(R.id.traintext)
    TextView trainTextView;

    @BindView(R.id.reportformsimageview)
    ImageView reportImageView;

    @BindView(R.id.reportformstextview)
    TextView reportFormsTextView;

    @BindView(R.id.signinimage)
    ImageView signInImageView;

    @BindView(R.id.signintext)
    TextView signInTextView;

    @BindView(R.id.mineimage)
    ImageView mineImageView;

    @BindView(R.id.minetext)
    TextView mineTextView;

    private TrainFragment mTrainFragment;
    private ReportFormFragment mReportFormFragment;
    private FunctionFragment mFunctionFragment;
    private MineFragment mMineFragment;

    private BluetoothLeDevice selectedGlassBluetoothLeDevice;
    private BluetoothLeDevice selectedLightBluetoothLeDevice;

    private BleMainControlContract.Presenter presenter;


    public static final String BLUETOOTH_DEVICE_KEY = "BLUETOOTH_DEVICE_KEY";
    public static final String BLUETOOTH_DEVICE_TYPE_KEY = "BLUETOOTH_DEVICE_TYPE_KEY";

    public static final String TRAIN_FRAGMENT_KEY = "TRAIN_FRAGMENT_KEY";
    public static final String REPORT_FORMS_FRAGMENT_KEY = "REPORT_FORMS_FRAGMENT_KEY";
    public static final String FUNCTION_FRAGMENT_KEY = "FUNCTION_FRAGMENT_KEY";
    public static final String MINE_FRAGMENT_KEY = "MINE_FRAGMENT_KEY";

    private int currentSelectedFragmentIndex = 0;
    private boolean fromOnPause = false;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        //首次安装按home键置入后台，从桌面图标点击重新启动的问题
    /*    if (!isTaskRoot()) {
            finish();
            return;
        }*/
        setContentView(R.layout.activity_maincontrol);
        super.onCreate(bundle);
        //handleNotEmptyBundle(bundle);
        initData(bundle);
        createPresenter();
        initView();
        initListener();
        registerEventBus();
        finishOtherActivity();
        fromOnPause = false;
        startForgroundService();
        BleDeviceManager.getInstance().setHaveExitApp(false);
        AppUpdateModel.getInstance().setContext(this);
        AppUpdateModel.getInstance().setOnlyShowDiff(true);
        AppUpdateModel.getInstance().getApkVersionInfo();
        checkWritePermission();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enableBluetooth();
            }
        }, 1500);

    }



    private void initData(Bundle bundle) {

        if (null != bundle) {
            /**
             * 处理状态恢复的情况
             */
            selectedGlassBluetoothLeDevice = bundle.getParcelable(BLUETOOTH_DEVICE_KEY);
            if (null != selectedGlassBluetoothLeDevice) {
                return;
            }
        }
        Intent mIntent = getIntent();
        if (null != mIntent) {
            int bluetoothDeviceType = mIntent.getIntExtra(BLUETOOTH_DEVICE_TYPE_KEY, -1);
            boolean fromLogin = mIntent.getBooleanExtra(ConstantString.KEY_MAIN_CONTR0L_FROM_LOGIN, false);
            if (EventConstant.GLASS_BLUETOOTH_EVENT_TYPE == bluetoothDeviceType) {
                getNetData(fromLogin);
                selectedGlassBluetoothLeDevice = mIntent.getParcelableExtra(BLUETOOTH_DEVICE_KEY);
                selectedLightBluetoothLeDevice = null;
            } else if (EventConstant.LIGHT_BLUETOOTH_EVENT_TYPE == bluetoothDeviceType) {
                selectedLightBluetoothLeDevice = mIntent.getParcelableExtra(BLUETOOTH_DEVICE_KEY);
                selectedGlassBluetoothLeDevice = null;
            } else {
                selectedGlassBluetoothLeDevice = null;
                selectedLightBluetoothLeDevice = null;
            }
        } else {
            selectedGlassBluetoothLeDevice = null;
            selectedLightBluetoothLeDevice = null;
        }

        //TrainModel.getInstance().getGlassesInitData();
        TrainModel.getInstance().prepareGlassesTrainData(false, false, false);
    }

    @Override
    protected void handleNotEmptyBundle(Bundle bundle) {
        super.handleNotEmptyBundle(bundle);
        if (null != bundle) {
            FragmentManager manager = getSupportFragmentManager();//重新创建Manager，防止此对象为空
            //manager.popBackStackImmediate(null,  FragmentManager.POP_BACK_STACK_INCLUSIVE);//弹出所有fragment
            mTrainFragment = (TrainFragment) manager.findFragmentByTag(TRAIN_FRAGMENT_KEY);
            mFunctionFragment = (FunctionFragment) manager.findFragmentByTag(FUNCTION_FRAGMENT_KEY);
            mMineFragment = (MineFragment) manager.findFragmentByTag(MINE_FRAGMENT_KEY);
            mReportFormFragment = (ReportFormFragment) manager.findFragmentByTag(REPORT_FORMS_FRAGMENT_KEY);

//            if (null != mTrainFragment) {
//                Fragment.SavedState savedState = manager.saveFragmentInstanceState(mTrainFragment);
//                mTrainFragment.setInitialSavedState(savedState);
//            }
//
//            if (null != mFunctionFragment) {
//                Fragment.SavedState savedState = manager.saveFragmentInstanceState(mFunctionFragment);
//                mFunctionFragment.setInitialSavedState(savedState);
//            }
//
//            if (null != mMineFragment) {
//                Fragment.SavedState savedState = manager.saveFragmentInstanceState(mMineFragment);
//                mMineFragment.setInitialSavedState(savedState);
//            }
//
//            if (null != mReportFormFragment) {
//                Fragment.SavedState savedState = manager.saveFragmentInstanceState(mReportFormFragment);
//                mReportFormFragment.setInitialSavedState(savedState);
//            }
        }

    }

    private void initView() {
        //checkBluetoothPermission();//检测蓝牙功能，开启蓝牙
//        initStatusBar(R.color.colorPrimary);
        changeButtonSelectedStatus(0);
        showOrHideFragment(0);
    }

    private void changeButtonSelectedStatus(int selectedIndex) {
        if (selectedIndex >= 0 && selectedIndex <= 3) {
            clearAllSeletedStatus();
        }
        switch (selectedIndex) {
            case 0:
                trainImageView.setImageResource(R.mipmap.ic_main_true);
                trainTextView.setTextColor(getResources().getColor(R.color.bleglasses_main_top_bg_color));
                showOrHideFragment(selectedIndex);
                break;

            case 1:
                reportImageView.setImageResource(R.mipmap.ic_statement_true);
                reportFormsTextView.setTextColor(getResources().getColor(R.color.bleglasses_main_top_bg_color));
                showOrHideFragment(selectedIndex);
                break;

            case 2:
                signInImageView.setImageResource(R.mipmap.ic_statement_true);
                signInTextView.setTextColor(getResources().getColor(R.color.bleglasses_main_top_bg_color));
                showOrHideFragment(selectedIndex);
                break;

            case 3:
                mineImageView.setImageResource(R.mipmap.ic_mine_true);
                mineTextView.setTextColor(getResources().getColor(R.color.bleglasses_main_top_bg_color));
                showOrHideFragment(selectedIndex);
                break;

            default:
                break;
        }
    }

    private void clearAllSeletedStatus() {
        trainImageView.setImageResource(R.mipmap.ic_main_false);
        trainTextView.setTextColor(getResources().getColor(R.color.main_bottom_unselected_text_color));

        reportImageView.setImageResource(R.mipmap.ic_statement_false);
        reportFormsTextView.setTextColor(getResources().getColor(R.color.main_bottom_unselected_text_color));

        signInImageView.setImageResource(R.mipmap.ic_statement_false);
        signInTextView.setTextColor(getResources().getColor(R.color.main_bottom_unselected_text_color));

        mineImageView.setImageResource(R.mipmap.ic_mine_false);
        mineTextView.setTextColor(getResources().getColor(R.color.main_bottom_unselected_text_color));
    }

    private void changeBleDeviceConnectStatusView() {

    }

    private void initListener() {
        trainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelectedFragmentIndex = 0;
                changeButtonSelectedStatus(currentSelectedFragmentIndex);
            }
        });

        reportFormsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelectedFragmentIndex = 1;
                changeButtonSelectedStatus(currentSelectedFragmentIndex);
            }
        });

        signInLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelectedFragmentIndex = 2;
                changeButtonSelectedStatus(currentSelectedFragmentIndex);
            }
        });

        mineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelectedFragmentIndex = 3;
                changeButtonSelectedStatus(currentSelectedFragmentIndex);
            }
        });

    }


    private void createPresenter() {
        setPresenter(new BleMainControlPresenter(getRespository(), this));
    }

    @Override
    public void setPresenter(BleMainControlContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showToastMsg(String tipMsg) {
        ToastUtil.showShortToast(tipMsg);
    }

    @Override
    public void changeBleGlassesConnectStatus(boolean startAnimation) {

    }

    @Override
    public void changeBleLightConnectStatus(boolean startAnimation) {

    }

    @Override
    public void setStatusText(String statusText) {

    }

    @Override
    public void setBleGlassesBatteryText(String bleGlassesBatteryText) {

    }

    @Override
    public void setBleLightBatteryText(String bleLightBatteryText) {

    }

    @Override
    public void setSelectedScenarioTextView(String selectedScenarioText) {

    }

    @Override
    public void setTrainModeText(String trainModeText) {

    }

    @Override
    public void setUserName(String userName) {

    }

    @Override
    public boolean isConnected() {
        return presenter.isBleGlassesConnected();
    }

    @Override
    public void updateConnectStatusAndConnect() {
        updateBleConnnectStatus(true);
    }

    @Override
    public boolean checkLocationServiceOk() {
        return bluetoothAndLocationServiceOk();
    }

    @Override
    public void showToastMsg(int resourceId) {
        showToastMsg(getText(resourceId).toString());
    }

    private void connectGlassesBle(BluetoothLeDevice bluetoothLeDeviceParam) {
        if (checkBluetoothEnable()) {
            presenter.connectBleGlasses(bluetoothLeDeviceParam);
            showConnectingDialog(bluetoothLeDeviceParam.getAddress());
        }
    }

    private void connectLightBle(BluetoothLeDevice bluetoothLeDeviceParam) {
        presenter.connectBleLight(bluetoothLeDeviceParam);
    }

    private void connectGlassesBleByMac(String mac) {
        if (checkBluetoothEnable()) {
            presenter.connectBleGlassesByMac(mac);
            showConnectingDialog(mac);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermissions(getPermissions());
    }

    @Override
    protected void onResume() {
        super.onResume();
//        updateBleConnnectStatus(!fromOnPause);

    }

    @Override
    protected void onPause() {
        super.onPause();
        fromOnPause = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BLUETOOTH_DEVICE_KEY, selectedGlassBluetoothLeDevice);
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    private void showExitDialog() {
        AlertDialog dialog = new AlertDialog.Builder(BleMainControlActivity.this, R.style.customHoloLight)
                .setPositiveButton(getResources().getString(R.string.cancel_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {

                    }
                }).setNegativeButton(getResources().getString(R.string.exit_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        doExitApp();
                    }
                })
                .setCancelable(true)
                .setTitle(getResources().getString(R.string.exit_text))
                .setMessage(getResources().getString(R.string.exit_app_sure_tip)).create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {                    //
                Button negativeButton = ((android.app.AlertDialog) dialog)
                        .getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
                Button positiveButton = ((android.app.AlertDialog) dialog)
                        .getButton(android.app.AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.BLUE);
                negativeButton.setTextColor(Color.BLUE);
            }
        });
        dialog.show();
    }


    private DialogBleConnectEvent mDialogBleConnectEvent;

    /**
     * 从 BleListDeviceDialog 中选择的一个蓝牙设备进行连接
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveBleDeviceConnectFromDialogSelected(DialogBleConnectEvent dialogBleConnectEvent) {
        MLog.d("dialogBleConnectEvent = " + dialogBleConnectEvent);

        mDialogBleConnectEvent = dialogBleConnectEvent;
//        queryNewestRunParamBleDataFromServer();

        if (null != mDialogBleConnectEvent) {
            final BluetoothLeDevice bluetoothLeDevice = mDialogBleConnectEvent.getBluetoothLeDevice();
            if (null != bluetoothLeDevice) {
                String mac = bluetoothLeDevice.getAddress();
                String name = bluetoothLeDevice.getName();
                TrainModel.getInstance().checkDeviceBindStatus(mac, null, false, new CheckBleMacByServerCallBack() {
                    @Override
                    public void checkStatus(boolean avaliable, String mac) {
                        if (avaliable) {
                            connectGlassesBle(bluetoothLeDevice);
                        }
                    }
                });
            }
        }
    }


    /**
     * 从 BleListDeviceDialog 中接收扫描蓝牙设备的事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveScanBleDeviceEventFromDialog(DialogScanEvent dialogScanEvent) {
        MLog.e("onReceiveScanBleDeviceEventFromDialog =========== " + dialogScanEvent);
        if (null != dialogScanEvent) {
            if (checkBluetoothEnable()) {
                if (dialogScanEvent.isStartScan()) {
                    presenter.startScan();
                } else {
                    presenter.stopScan();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveBleDeviceConnnectStatus(ConnectEvent connectEvent) {
        dismissLoading();

        //判断是否允许打开Ble连接
        if (!TrainFragment.isAllowleOpen) {
            return;
        }
        MLog.e(TrainFragment.isAllowleOpen + "===========判断是否允许打开Ble连接 " + connectEvent.isSuccess() + " --- " + connectEvent.isDisconnected());

        if (connectEvent.isSuccess()) {
            //MLog.d("onConnectEvent connect success");
            //ToastUtils.showShort("连接成功");
            dismissAllFragmentDialog();
            showConnectSuccessDialog(connectEvent.getDeviceMirror().getBluetoothLeDevice().getAddress());
        } else if (connectEvent.isDisconnected()) {
            //MLog.d("onConnectEvent disconnect success");
            //ToastUtils.showShort( "断开连接成功");
            String mac = Config.getConfig().getLastConnectBleGlassesMac();
            if (!CommonUtils.isEmpty(mac)) {
                showConnectFailDialog(connectEvent.getDeviceMirror().getBluetoothLeDevice().getAddress());
            }

        } else {
            // MLog.d("onConnectEvent connect failure");
            //ToastUtils.showShort((CharSequence) "连接失败");
            String mac = Config.getConfig().getLastConnectBleGlassesMac();
            if (!CommonUtils.isEmpty(mac) && connectEvent.getDeviceMirror() != null
                    && connectEvent.getDeviceMirror().getBluetoothLeDevice() != null) {
                showConnectFailDialog(connectEvent.getDeviceMirror().getBluetoothLeDevice().getAddress());
            }
        }

        updateBleConnnectStatus(false);
        changeBleDeviceConnectStatusView();
    }

    private void updateBleConnnectStatus(boolean showDialog) {

        MLog.e(TrainFragment.isAllowleOpen + "===========判断是否允许打开Ble连接 " + showDialog);

        //判断是否允许打开Ble连接
        if (!TrainFragment.isAllowleOpen) {
            return;
        }

        if (null != mTrainFragment) {
            mTrainFragment.setBleConnectStatus(presenter.isBleGlassesConnected());
            if (!presenter.isBleGlassesConnected()) {
//                String mac = Config.getConfig().getLastConnectBleGlassesMac();
//                if (!CommonUtils.isEmpty(mac)) {
//                    connectBleDeviceByMac(mac);
//                } else {
//                    if (showDialog) {
//                        showScanDialog();
//                    }
//                }
            }
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

    @Override
    protected void onDestroy() {
        clearData();
        super.onDestroy();
    }

    private void clearData() {
        stopForeGroundService();
        AppUpdateModel.getInstance().setContentNull();
        dismissAllFragmentDialog();
        disconnectBle();
        unRegisterEventBus();
    }

    private void showOrHideFragment(int showIndex) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (showIndex) {
            case 0:
                //changeFragmentStatusBarBackGroundColor(false);
                initStatusBar(R.color.res_color_blue_374cff);
                if (null == mTrainFragment) {
                    mTrainFragment = new TrainFragment();
                    fragmentTransaction.add(R.id.framelayout, mTrainFragment, TRAIN_FRAGMENT_KEY);
                } else {
                    fragmentTransaction.show(mTrainFragment);
                }
                break;

            case 1:
                initStatusBar(R.color.res_color_blue_374cff);
                if (null == mReportFormFragment) {
                    mReportFormFragment = new ReportFormFragment();
                    fragmentTransaction.add(R.id.framelayout, mReportFormFragment, REPORT_FORMS_FRAGMENT_KEY);
                } else {
                    fragmentTransaction.show(mReportFormFragment);
                }
                break;

            case 2:
                initStatusBar(R.color.res_color_blue_374cff);
                if (null == mFunctionFragment) {
                    mFunctionFragment = new FunctionFragment();
                    fragmentTransaction.add(R.id.framelayout, mFunctionFragment, FUNCTION_FRAGMENT_KEY);
                } else {
                    fragmentTransaction.show(mFunctionFragment);
                }
                break;

            case 3:
                initStatusBar(R.color.res_color_blue_374cff);
                if (null == mMineFragment) {
                    mMineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.framelayout, mMineFragment, MINE_FRAGMENT_KEY);
                } else {
                    fragmentTransaction.show(mMineFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        //如果此fragment不为空的话就隐藏起来
        if (mTrainFragment != null) {
            fragmentTransaction.hide(mTrainFragment);
        }

        if (null != mReportFormFragment) {
            fragmentTransaction.hide(mReportFormFragment);
        }

        if (mFunctionFragment != null) {
            fragmentTransaction.hide(mFunctionFragment);
        }

        if (mMineFragment != null) {
            fragmentTransaction.hide(mMineFragment);
        }
    }

    private void changeFragmentStatusBarBackGroundColor(boolean isBlue) {
        if (isBlue) {
            initStatusBar(R.color.assit_almost_button_color);
        } else {
            initStatusBar(R.color.main_background_color);
        }
    }

    private void getNetData(boolean fromLogin) {
        if (!fromLogin) {
            /**
             * 当前页面执行登录操作，获取用户信息
             */


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //MLog.d(BleMainControlActivity.class.getSimpleName() + "onActivityResult  requestCode = " + requestCode + "  resultCode = " + resultCode);
        if (resultCode == CaptureActivity.CAPATURE_RESULT_CODE) {
            if (requestCode == BLE_SCAN_QRCODE_REQUEST_CODE) {
                Bundle bundle = data.getExtras();
                if (null != bundle) {
                    int resultType = bundle.getInt(CodeUtils.RESULT_TYPE);
                    if (resultType == CodeUtils.RESULT_SUCCESS) {
                        final String macStr = bundle.getString(CodeUtils.RESULT_STRING);
                        //ToastUtil.showShortToast("解析数据为：" + macStr);
                        MLog.d("macStr = " + macStr);
                        TrainModel.getInstance().checkDeviceBindStatus(macStr, null, false, new CheckBleMacByServerCallBack() {
                            @Override
                            public void checkStatus(boolean avaliable, String mac) {
                                if (avaliable) {
                                    connectBleDeviceByMac(macStr);
                                }
                            }
                        });

                    } else if (resultType == CodeUtils.RESULT_FAILED) {
                        ToastUtil.showShort("解析二维码失败");
                    }
                }
            }
        } else {
            if (requestCode == 1200 && null != data) {
                if (resultCode == 0) {
                    ToastUtil.showShortToast("正在连接");
                    //连接成功(正在连接)
                    selectedGlassBluetoothLeDevice = data.getParcelableExtra(BleMainControlActivity.BLUETOOTH_DEVICE_KEY);
                    selectedGlassBluetoothLeDevice = data.getParcelableExtra(BleMainControlActivity.BLUETOOTH_DEVICE_KEY);
                    if (null != selectedGlassBluetoothLeDevice) {
                        String mac = selectedGlassBluetoothLeDevice.getAddress();
                        String name = selectedGlassBluetoothLeDevice.getName();
                        TrainModel.getInstance().checkDeviceBindStatus(mac, null, false, new CheckBleMacByServerCallBack() {
                            @Override
                            public void checkStatus(boolean avaliable, String mac) {
                                if (avaliable) {
                                    connectGlassesBle(selectedGlassBluetoothLeDevice);
                                }
                            }
                        });
                    }
                    selectedLightBluetoothLeDevice = null;
                } else if (resultCode == 1) {
                    //未搜索到
                    ToastUtil.showShortToast("未搜索到设备");
                }
            }

        }
    }

    private void startScanQrCordActivityForResult() {
        startActivityForResult(new Intent(BleMainControlActivity.this, CaptureActivity.class), BLE_SCAN_QRCODE_REQUEST_CODE);
    }

    @Override
    public void connectBleDeviceByMac(String mac) {
        TrainModel.getInstance().checkDeviceBindStatus(mac, null, false, new CheckBleMacByServerCallBack() {
            @Override
            public void checkStatus(boolean avaliable, String mac) {
                if (avaliable) {
                    connectGlassesBleByMac(mac);
                }
            }
        });

    }

    @Override
    public void quitConnectBleDevice() {
        /**
         * 取消蓝牙设备（眼镜）连接
         */
        presenter.stopScanByMac();
        presenter.disconnectGlassesBle(true);
    }

    @Override
    public void retryConnectBleDevice() {
        final String lastConnectedBleDeviceMac = Config.getConfig().getLastConnectBleGlassesMac();
        if (!CommonUtils.isEmpty(lastConnectedBleDeviceMac)) {
            TrainModel.getInstance().checkDeviceBindStatus(lastConnectedBleDeviceMac, null, false, new CheckBleMacByServerCallBack() {
                @Override
                public void checkStatus(boolean avaliable, String mac) {
                    if (avaliable) {
                        presenter.connectBleGlassesByMac(lastConnectedBleDeviceMac);
                    }
                }
            });

        } else {
            showScanDialog();
        }
    }

    @Override
    public void startSearchActivityForResultFromTrainFragment() {
        Intent mIntent = new Intent(BleMainControlActivity.this, BleSearchActivity.class);
        startActivityForResult(mIntent, 1200);
    }

    @Override
    public void scanBleDeviceQrCoder() {
        PermissionManager.instance().request(this, new OnPermissionCallback() {
            @Override
            public void onRequestAllow(String permissionName) {
                startScanQrCordActivityForResult();
            }

            @Override
            public void onRequestRefuse(String permissionName) {
                ToastUtil.showShort("相机权限被禁用，请在权限管理中开启！");
            }

            @Override
            public void onRequestNoAsk(String permissionName) {
                ToastUtil.showShort("相机权限被禁用，请在权限管理中开启！");
            }
        }, Manifest.permission.CAMERA);

    }

    @Override
    public void requestPermissionsFail(List<String> permissionList) {
        MLog.d("permissionListFail = " + permissionList);
        //super.requestPermissionsFail(permissionList);
        if (permissionList.contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ToastUtil.showShort("位置权限被禁用，蓝牙功能受限！");
            finish();
        }
    }

    @Override
    public void requestPermissionsSuccess(List<String> grantedPermissionsList) {
        //super.requestPermissionsSuccess(grantedPermissionsList);
        MLog.d("grantedPermissionsList = " + grantedPermissionsList);
        if (null != grantedPermissionsList && grantedPermissionsList.size() > 0) {
            if (grantedPermissionsList.contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
              /*  String lastConnectBleDeviceMac = Config.getConfig().getLastConnectBleGlassesMac();
                if  (CommonUtils.isEmpty(lastConnectBleDeviceMac)) {
                    showScanDialog();
                }*/
            }
        }
    }

    @Override
    public void changeTrainModeFromTrainFragment(int trainMode) {
        updateBleConnnectStatus(false);
        //checkBluetoothPermission();
        if (bluetoothAndLocationServiceOk()) {
            presenter.sendUserInfoWithTrainMode(trainMode);
        }
    }

    @Override
    public void startActionFromTrainFragment() {

        updateBleConnnectStatus(false);
        //checkBluetoothPermission();
        if (bluetoothAndLocationServiceOk()) {
            presenter.sendUserInfoWithTrainMode(Config.getConfig().getLastSelectedTrainMode() + 1);
            presenter.startAction();
        }

    }

    @Override
    public void pauseActionFromTrainFragment() {
        updateBleConnnectStatus(false);
        if (bluetoothAndLocationServiceOk()) {
            presenter.pauseAction();
        }
    }

    @Override
    public void interveneActionFromTrainFragment() {
        updateBleConnnectStatus(false);
        if (bluetoothAndLocationServiceOk()) {
            if (presenter.isBleGlassesConnected()) {
                presenter.interveneAction();
            }
        }

    }

    @Override
    public void stopActionFromTrainFragment() {
        updateBleConnnectStatus(false);
        if (bluetoothAndLocationServiceOk()) {
            presenter.stopAction();
        }

           /*  sendReceiveParam1();
        sendReceiveParam2();
        sendReceiveParam3();
        sendReceiveParam4();
        sendReceiveParam5();*/

    }

    @Override
    public void continueActionFromTrainFragment() {
        updateBleConnnectStatus(false);
        if (bluetoothAndLocationServiceOk()) {
            presenter.continueAction();
        }

    }

    @Override
    public void closeBatteryFromTrainFragment() {
        updateBleConnnectStatus(false);
        if (bluetoothAndLocationServiceOk()) {
            presenter.closeBleDeviceBattery();
        }

    }

    @Override
    public void commonModeFromTrainFragment() {
        //处理一般模式
        ToastUtil.showShortToast("一般模式0");
    }

    @Override
    public void indoorModeFromTrainFragment() {
        //处理室内模式
        ToastUtil.showShortToast("室内模式2");
    }

    @Override
    public void outdoorModeFromTrainFragment() {
        //处理户外模式
        ToastUtil.showShortToast("户外模式1");
    }

    private void finishOtherActivity() {
        List<Class<?>> classList = new ArrayList<>();
        classList.add(BleMainControlActivity.class);
        classList.add(MainActivity.class);

        ActivityStackUtil.getInstance().finishOthersActivity(classList);
    }

    /**
     * 显示连接成功 Dialog
     */
    private void showConnectSuccessDialog(String deviceId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment trainFragment = fragmentManager.findFragmentByTag(TRAIN_FRAGMENT_KEY);
        if (null != trainFragment) {
            ((TrainFragment) trainFragment).showConnectStatusDialog(deviceId, BleConnectStatus.Connected);
        }
    }

    /**
     * 显示连接失败 Dialog
     */
    private void showConnectFailDialog(String deviceId) {

        if (TrainFragment.mRemainTime >= 0) {
            ToastUtil.showLong(R.string.please_reconnect);
        }

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment trainFragment = fragmentManager.findFragmentByTag(TRAIN_FRAGMENT_KEY);
//        if (null != trainFragment) {
//            ((TrainFragment) trainFragment).showConnectStatusDialog(deviceId, BleConnectStatus.ConnectedFail);
//        }
    }

    /**
     * 显示连接中 Dialog
     */
    private void showConnectingDialog(String deviceId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment trainFragment = fragmentManager.findFragmentByTag(TRAIN_FRAGMENT_KEY);
        if (null != trainFragment) {
            ((TrainFragment) trainFragment).showConnectStatusDialog(deviceId, BleConnectStatus.Connecting);
        }
    }

    /**
     * 显示扫描二维码，或者手动输入设备 ID 进行连接
     */
    private void showScanDialog() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment trainFragment = fragmentManager.findFragmentByTag(TRAIN_FRAGMENT_KEY);
//        if (null != trainFragment) {
//            ((TrainFragment) trainFragment).showScanTipDialog();
//        }
    }

    /**
     * 关闭所有的 Dialog（考虑 Token 过期之后，在Interceptor 中finish 所有的 Activity  进入登录界面）
     */
    private void dismissAllFragmentDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment trainFragment = fragmentManager.findFragmentByTag(TRAIN_FRAGMENT_KEY);
        if (null != trainFragment) {
            ((TrainFragment) trainFragment).dismissBleInputDeviceIdDialog();
            ((TrainFragment) trainFragment).dismissConnectStatusDialog();
            ((TrainFragment) trainFragment).dismissScanTipDialog();
        }
    }


    private void sendReceiveParam1() {
        NotifyDataEvent runParamDataEvent = new NotifyDataEvent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ReceiveGlassesRunParam1BleDataBean.USER_MONITOR_CMD);
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(BaseCmdBean.decimalism2Hex(i, 2));
        }
        stringBuilder.append("aa");
        byte[] param1 = BleHexUtil.getRealSendData(stringBuilder.toString());
        runParamDataEvent.setData(param1);
        runParamDataEvent.setDeviceType(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
        EventBus.getDefault().post(runParamDataEvent);
    }


    private void sendReceiveParam2() {
        NotifyDataEvent runParamDataEvent = new NotifyDataEvent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ReceiveGlassesRunParam2BleDataBean.USER_MONITOR_CMD);
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(BaseCmdBean.decimalism2Hex(i, 2));
        }
        stringBuilder.append("aa");
        byte[] param2 = BleHexUtil.getRealSendData(stringBuilder.toString());
        runParamDataEvent.setData(param2);
        runParamDataEvent.setDeviceType(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
        EventBus.getDefault().post(runParamDataEvent);

    }

    private void sendReceiveParam3() {
        NotifyDataEvent runParamDataEvent = new NotifyDataEvent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ReceiveGlassesRunParam3BleDataBean.USER_MONITOR_CMD);
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(BaseCmdBean.decimalism2Hex(i, 2));
        }
        stringBuilder.append("aa");
        byte[] param3 = BleHexUtil.getRealSendData(stringBuilder.toString());
        runParamDataEvent.setData(param3);
        runParamDataEvent.setDeviceType(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
        EventBus.getDefault().post(runParamDataEvent);

    }

    private void sendReceiveParam4() {
        NotifyDataEvent runParamDataEvent = new NotifyDataEvent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ReceiveGlassesRunParam4BleDataBean.USER_MONITOR_CMD);
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(BaseCmdBean.decimalism2Hex(i, 2));
        }
        stringBuilder.append("aa");

        byte[] param4 = BleHexUtil.getRealSendData(stringBuilder.toString());
        runParamDataEvent.setData(param4);
        runParamDataEvent.setDeviceType(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
        EventBus.getDefault().post(runParamDataEvent);

    }

    private void sendReceiveParam5() {
        NotifyDataEvent runParamDataEvent = new NotifyDataEvent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ReceiveGlassesRunParam5BleDataBean.USER_MONITOR_CMD);
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(BaseCmdBean.decimalism2Hex(i, 2));
        }
        stringBuilder.append("aa");

        byte[] param5 = BleHexUtil.getRealSendData(stringBuilder.toString());
        runParamDataEvent.setData(param5);
        runParamDataEvent.setDeviceType(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
        EventBus.getDefault().post(runParamDataEvent);

    }

    private byte[] getRunParamResponseByteArrayFromEdit() {
        SparseArray<BaseCmdBean> baseCmdBeanArray = BleDataBeanConvertUtil.httpResponseBleDataBean2BleCmdBean(createRunParamResonseBean());
        byte[] retByteArray = new byte[baseCmdBeanArray.size() * 20];

        for (int i = 0; i < baseCmdBeanArray.size(); i++) {
            BaseCmdBean baseCmdBean = baseCmdBeanArray.get(i);
            byte[] sendByte = baseCmdBean.buildCmdByteArray();
            MLog.d("getLastGlassesRunningParams[" + i + "]" + BaseParseCmdBean.bytesToStringWithSpace(sendByte));
            //System.arraycopy(retByteArray, i * 20, sendByte, 0, 20);
            System.arraycopy(sendByte, 0, retByteArray, i * 20, 20);
        }

        MLog.d("retByteArrayStr =  " + BaseParseCmdBean.bytesToStringWithSpace(retByteArray));
        return retByteArray;
    }

    private HttpResponseGlassesRunParamBean createRunParamResonseBean() {
        HttpResponseGlassesRunParamBean httpResponseGlassesRunParamBean = new HttpResponseGlassesRunParamBean();
        httpResponseGlassesRunParamBean.setMinMinusInterval(1);
        httpResponseGlassesRunParamBean.setMinPlusInterval(2);
        httpResponseGlassesRunParamBean.setCommonNumber(3);
        httpResponseGlassesRunParamBean.setInterveneAccMinute(4);
        httpResponseGlassesRunParamBean.setWeekKeyFre(5);
        httpResponseGlassesRunParamBean.setWeekAccMinute(6);
        httpResponseGlassesRunParamBean.setBackWeekAccMinute0(7);
        httpResponseGlassesRunParamBean.setBackWeekAccMinute1(8);
        httpResponseGlassesRunParamBean.setBackWeekAccMinute2(9);
        httpResponseGlassesRunParamBean.setBackWeekAccMinute3(10);
        httpResponseGlassesRunParamBean.setPlusInterval(11);
        httpResponseGlassesRunParamBean.setMinusInterval(12);
        httpResponseGlassesRunParamBean.setPlusInc(13);
        httpResponseGlassesRunParamBean.setMinusInc(14);
        httpResponseGlassesRunParamBean.setIncPer(15);
        httpResponseGlassesRunParamBean.setRunNumber(16);
        httpResponseGlassesRunParamBean.setRunSpeed(17);
        httpResponseGlassesRunParamBean.setSpeedInc(18);
        httpResponseGlassesRunParamBean.setSpeedSegment(19);
        httpResponseGlassesRunParamBean.setIntervalSegment(20);
        httpResponseGlassesRunParamBean.setBackSpeedSegment(21);
        httpResponseGlassesRunParamBean.setBackIntervalSegment(22);
        httpResponseGlassesRunParamBean.setSpeedKeyFre(23);
        httpResponseGlassesRunParamBean.setInterveneKeyFre(24);
        httpResponseGlassesRunParamBean.setIntervalAccMinute(25);
        httpResponseGlassesRunParamBean.setMinusInterval2(26);
        httpResponseGlassesRunParamBean.setPlusInterval2(27);
        httpResponseGlassesRunParamBean.setMinusInc2(28);
        httpResponseGlassesRunParamBean.setPlusInc2(29);
        httpResponseGlassesRunParamBean.setIncPer2(30);
        httpResponseGlassesRunParamBean.setRunNumber2(31);
        httpResponseGlassesRunParamBean.setRunSpeed2(32);
        httpResponseGlassesRunParamBean.setSpeedSegment2(33);
        httpResponseGlassesRunParamBean.setSpeedInc2(34);
        httpResponseGlassesRunParamBean.setIntervalSegment2(35);
        httpResponseGlassesRunParamBean.setBackSpeedSegment2(36);
        httpResponseGlassesRunParamBean.setBackIntervalSegment2(37);
        httpResponseGlassesRunParamBean.setSpeedKeyFre2(38);
        httpResponseGlassesRunParamBean.setInterveneKeyFre2(39);
        httpResponseGlassesRunParamBean.setIntervalAccMinute2(40);


        httpResponseGlassesRunParamBean.setTrainingState(41);
        httpResponseGlassesRunParamBean.setTrainingState2(42);
        httpResponseGlassesRunParamBean.setAdjustSpeed(43);
        httpResponseGlassesRunParamBean.setMaxRunSpeed(44);
        httpResponseGlassesRunParamBean.setMinRunSpeed(45);
        httpResponseGlassesRunParamBean.setAdjustSpeed2(46);
        httpResponseGlassesRunParamBean.setMaxRunSpeed2(47);
        httpResponseGlassesRunParamBean.setMinRunSpeed2(48);
        httpResponseGlassesRunParamBean.setTxByte12(49);
        httpResponseGlassesRunParamBean.setTxByte13(50);
        httpResponseGlassesRunParamBean.setTxByte14(51);
        httpResponseGlassesRunParamBean.setTxByte15(52);
        httpResponseGlassesRunParamBean.setTxByte16(53);
        httpResponseGlassesRunParamBean.setTxByte17(54);
        httpResponseGlassesRunParamBean.setTxByte18(55);
        httpResponseGlassesRunParamBean.setTxByte19(56);

        return httpResponseGlassesRunParamBean;
    }

    private void startForgroundService() {
        startService(new Intent(this, ForegroundService.class));
    }

    private void stopForeGroundService() {
        stopService(new Intent(this, ForegroundService.class));
    }

    private void disconnectBle() {
        BleDeviceManager.getInstance().setHaveExitApp(true);
        BleDeviceManager.getInstance().disconnectGlassesBleDevice(false);
        BleDeviceManager.getInstance().shutDownScheduledExecutorService();
    }

    private void doExitApp() {
        clearData();
        ActivityStackUtil.getInstance().finishAllActivity();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


}
