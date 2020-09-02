package com.zj.zhijue.fragment.mine;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.app.BaseApplication;
import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.ActivityStackUtil;
import com.android.common.baselibrary.util.FileUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.huige.library.utils.SharedPreferencesUtils;
import com.vise.baseble.core.BluetoothGattChannel;
import com.vise.baseble.core.DeviceMirror;
import com.vise.baseble.model.BluetoothLeDevice;
import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.login.PhoneLoginActivity;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.bledata.send.SendCalibrationModeCmdBean2;
import com.zj.zhijue.bean.bledata.send.SendFireDFUModeBleCmdBeaan2;
import com.zj.zhijue.bean.bledata.send.SendSuccessStatusCmdBean2;
import com.zj.zhijue.bean.bledata.send.SendUserInfoControlBleCmdBean;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.ble.BleOptHelper;
import com.zj.zhijue.config.Constants;
import com.zj.zhijue.constant.BleSppGattAttributes;
import com.zj.zhijue.dialog.DLoadingNumProcessDialog;
import com.zj.zhijue.dialog.DefaultLoadingDialog;
import com.zj.zhijue.dialog.bleconnect.BleDataLogDialog;
import com.zj.zhijue.dialog.function.AppUpdateDialog;
import com.zj.zhijue.dialog.function.DeleteTrainInfoConfirmDialog;
import com.zj.zhijue.dialog.function.FirmwareUpdateDialog;
import com.zj.zhijue.dialog.function.UpdateFiremwareStopGlassesDialog;
import com.zj.zhijue.event.CallbackDataEvent;
import com.zj.zhijue.event.CmdBleDataEvent;
import com.zj.zhijue.event.ConnectEvent;
import com.zj.zhijue.event.DfuModeEvent2;
import com.zj.zhijue.event.EventConstant;
import com.zj.zhijue.event.ReceiveRunParamsDoneEvent;
import com.zj.zhijue.fragment.TrainFragment;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.http.request.IgetLastedSystemUpdate;
import com.zj.zhijue.http.response.HttpResponseFiremwareVersionInfoBean;
import com.zj.zhijue.http.response.OauthLogoutBean;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.model.AppUpdateModel;
import com.zj.zhijue.model.GlassesBleDataModel;
import com.zj.zhijue.model.TrainModel;
import com.zj.zhijue.service.DfuService;
import com.zj.zhijue.util.AndroidUtil;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.UserInfoUtil;
import com.zj.zhijue.util.downloadutil.DownloadUtils;
import com.zj.zhijue.util.downloadutil.JsDownloadListener;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.view.CustomSwitch;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuServiceController;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;
import okhttp3.ResponseBody;

/**
 * 系统设置
 */
public class SystemSettings2Fragment extends BaseFragment {

    @BindView(R.id.function_item_backlayout)
    LinearLayout functionItemBacklayout;
    @BindView(R.id.function_item_title_tv)
    AppCompatTextView functionItemTitleTv;
    @BindView(R.id.tv_set_version)
    TextView tvSetVersion;
    @BindView(R.id.ll_set_firmware)
    LinearLayout llSetFirmware;
    @BindView(R.id.ll_set_software)
    LinearLayout llSetSoftware;
    @BindView(R.id.cs_debug)
    CustomSwitch csDebug;
    @BindView(R.id.csCalibrationMode)
    CustomSwitch csCalibrationMode;
    @BindView(R.id.tv_logout)
    TextView tvLogout;

    AppUpdateDialog appUpdateDialog = null;
    private DLoadingNumProcessDialog dLoadingNumProcessDialog = null;
    private DLoadingNumProcessDialog firmwareDownloadNumProgressDialog;

    private FirmwareUpdateDialog firmwareUpdateDialog = null;
    private UpdateFiremwareStopGlassesDialog mUpdateFiremwareStopGlassesDialog;
    private DefaultLoadingDialog mFiremwareUpdateDialog;
    //校准模式
    private DefaultLoadingDialog mCalibrationModeDialog;

    private List<DisposableObserver> disposableObserverList = new ArrayList<>();
    private Handler apkHandler = null;
    private Handler firmWareHandler = null;
    private long appTotalLength = 0;
    private long binTotalLength = 0;
    private final int UPDATE_DOWNLOAD_PROGREESS = 1;
    private final int DIMISS_APP_DOWNLOAING_DIALOG = 2;
    private AtomicBoolean showAppDownloadingProgress = new AtomicBoolean(true);
    private static AtomicBoolean appDownloading = new AtomicBoolean(false);
    private AtomicBoolean showFirmwareDownloadingProgress = new AtomicBoolean(false);
    private static AtomicBoolean firmwareDownloading = new AtomicBoolean(false);

    private final String NEWAPKDIR = "ShiXingNewApkDir";

    private BleDataLogDialog bleDataLogDialog;

    private DeleteTrainInfoConfirmDialog mDeleteTrainInfoConfirmDialog;
    private String fireFilePath = null;
    private boolean updateFireware = false;

    //固件更新是否成功
    private boolean isDfuUpdateSuccess = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUpdateModel.getInstance().setContentNull();
        registerEventBus();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_systemsettings2_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DfuServiceListenerHelper.registerProgressListener(getActivity(), dfuProgressListener);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        DfuServiceListenerHelper.unregisterProgressListener(getActivity(), dfuProgressListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mCalibrationModeDialog = null;
        mDeleteTrainInfoConfirmDialog = null;
        mFiremwareUpdateDialog = null;
        ToastUtil.isDfuUpdate = false;
    }

    private void initView(View view) {
        functionItemTitleTv.setText(getResources().getText(R.string.system_settings_text));
        tvSetVersion.setText(AndroidUtil.getVersionName(getActivity()));
        csDebug.setChecked(BaseApplication.isDebugMode);
    }

    private void initData() {
    }


    protected void initListener() {
        functionItemBacklayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        llSetFirmware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "硬件 " + BaseApplication.isDebugMode);
                //停止轮询数据
                TrainFragment.mTimeCount = -100;

                if (BaseApplication.isDebugMode) {
                    showManualSelectFileDialog();
                } else {
                    //TODO 测试本地固件升级的开关
                    if (false) {
                        copyBinFileFromAssets();
                    } else {
                        getFirewareVersionFromGlasse();
                    }
                }
            }
        });
        llSetSoftware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUpdateModel.getInstance().setContext(getActivity());
                AppUpdateModel.getInstance().setOnlyShowDiff(false);
                AppUpdateModel.getInstance().getAppVersion();
            }
        });
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPreferencesUtils.contains(Constants.SP_OPTOMETRYDATA)) {
                    SharedPreferencesUtils.remove(Constants.SP_OPTOMETRYDATA);
                }
                logoutToken();
            }
        });

        csDebug.setOnCheckedChangeListener(new CustomSwitch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                if (isChecked) {
                    Log.e("TAG", "打开" + isChecked);
                } else {
                    Log.e("TAG", "关闭" + isChecked);
                }

                BaseApplication.isDebugMode = isChecked;
            }
        });


        csCalibrationMode.setOnCheckedChangeListener(new CustomSwitch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                if (isChecked) {
                    Log.e("TAG", "校准打开" + isChecked);
                } else {
                    Log.e("TAG", "校准关闭" + isChecked);
                }

                showCalibrationModeDialog("正在校准请稍后...");
            }
        });
    }

    /**
     * 手动选择固件文件，进行升级
     */
    private void showManualSelectFileDialog() {
        if (!BleDeviceManager.getInstance().isGlassesBleDeviceConnected()) {
            ToastUtil.showLong("眼镜未连接,无法升级固件!");
            return;
        }
        getFirewareVersionFromGlasse();

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType(“image/*”);//选择图片
        //intent.setType(“audio/*”); //选择音频
        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
    }

    /**
     * 固件升级本地测试
     */
    private void copyBinFileFromAssets() {
        String glassname = Config.getConfig().getDfuGlassName();

        String sourceZipName = "app_dfu_package_RMX_001_v1_20200712175522.zip";

        Config.getConfig().saveLastDfuBleGlassesName(sourceZipName);
        MLog.d(glassname + ":" + sourceZipName);
        //ToastUtil.showLong(glassname + ":" + sourceZipName);
        String destBinFile = getActivity().getFilesDir().getAbsolutePath() + File.separator + sourceZipName;
        fireFilePath = destBinFile;
        boolean copySuccess = FileUtils.copyApkFromAssets(getActivity(), sourceZipName, destBinFile);
        MLog.d("copyBinFileFromAssets = " + copySuccess);
        if (copySuccess) {
            MLog.d("destBinFile = " + destBinFile + " fileLength = " + new File(destBinFile).length());
            //startUpdate(destBinFile);
            if (BleDeviceManager.getInstance().isGlassesBleDeviceConnected()) {
                SendFireDFUModeBleCmdBeaan2 sendFireDFUModeBleCmdBeaan2 = new SendFireDFUModeBleCmdBeaan2();
                EventBus.getDefault().post(new CmdBleDataEvent(sendFireDFUModeBleCmdBeaan2.buildCmdByteArray()));
            } else {
                ToastUtil.showLong("未连接眼镜，请连接眼镜之后再尝试！");
            }

        }
    }

    private final DfuProgressListener dfuProgressListener = new DfuProgressListener() {
        @Override
        public void onDeviceConnecting(String deviceAddress) {
//          progressBar.setIndeterminate(true);
//          mTextPercentage.setText(R.string.dfu_status_connecting);
            Log.i("TEST", "onDeviceConnecting: " + deviceAddress);
            if (!isDfuUpdateSuccess) {
                showFiremwareUpdateProgressDialog(getResources().getString(R.string.fireware_update_progress_connecting_tip));
            }
        }

        @Override
        public void onDeviceConnected(String deviceAddress) {
            Log.i("TEST", "onDeviceConnected: " + deviceAddress);
            showFiremwareUpdateProgressDialog(getResources().getString(R.string.fireware_update_progress_connected_tip));
        }

        @Override
        public void onDfuProcessStarting(String deviceAddress) {
//          progressBar.setIndeterminate(true);
//          mTextPercentage.setText(R.string.dfu_status_starting);
            Log.i("TEST", "onDfuProcessStarting: " + deviceAddress);
            showFiremwareUpdateProgressDialog(getResources().getString(R.string.fireware_update_progress_tip));
        }

        @Override
        public void onDfuProcessStarted(String deviceAddress) {
            Log.i("TEST", "onDfuProcessStarted: " + deviceAddress);
            ToastUtil.isDfuUpdate = true;
            showFiremwareUpdateProgressDialog(getResources().getString(R.string.fireware_update_progress_tip));
        }

        @Override
        public void onEnablingDfuMode(String deviceAddress) {
            Log.i("TEST", "onEnablingDfuMode: " + deviceAddress);
        }

        @Override
        public void onProgressChanged(String deviceAddress, int percent, float speed, float avgSpeed, int currentPart, int partsTotal) {
            Log.i("TEST", "onProgressChanged: " + deviceAddress + "百分比" + percent + ",speed "
                    + speed + ",avgSpeed " + avgSpeed + ",currentPart " + currentPart
                    + ",partTotal " + partsTotal);
            //tv_show.setText("升级进度：" + percent + "%");
            showFiremwareUpdateProgressDialog("固件升级进度 " + percent + "%");
        }

        @Override
        public void onFirmwareValidating(String deviceAddress) {
            Log.i("TEST", "onFirmwareValidating: " + deviceAddress);
        }

        @Override
        public void onDeviceDisconnecting(String deviceAddress) {
            Log.i("TEST", "onDeviceDisconnecting: " + deviceAddress);
        }

        @Override
        public void onDeviceDisconnected(String deviceAddress) {
            Log.i("TEST", "onDeviceDisconnected: " + deviceAddress);
            dismissFiremwareUpdateProgressDialog();
        }

        @Override
        public void onDfuCompleted(String deviceAddress) {
            Log.i("TEST", "onDfuCompleted: " + deviceAddress);
//          progressBar.setIndeterminate(true);
            //progressBar.setVisibility(View.GONE);
            showFiremwareUpdateProgressDialog("升级完毕!");
            ToastUtil.isDfuUpdate = false;
            isDfuUpdateSuccess = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissFiremwareUpdateProgressDialog();
                }
            }, 1000);

        }

        @Override
        public void onDfuAborted(String deviceAddress) {
            Log.i("TEST", "onDfuAborted: " + deviceAddress);
            //progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onError(String deviceAddress, int error, int errorType, String message) {
            Log.i("TEST", "onError: " + deviceAddress + ",message:" + message);
            //progressBar.setVisibility(View.GONE);
            ToastUtil.isDfuUpdate = false;
        }
    };

    protected void showFiremwareUpdateProgressDialog(String msg) {
        if (null == mFiremwareUpdateDialog) {
            mFiremwareUpdateDialog = new DefaultLoadingDialog(getActivity());
            mFiremwareUpdateDialog.setCancelable(false);
        }
        mFiremwareUpdateDialog.setMessage(msg);
        mFiremwareUpdateDialog.show();
    }

    protected void dismissFiremwareUpdateProgressDialog() {
        if (null != mFiremwareUpdateDialog) {
            mFiremwareUpdateDialog.dismiss();
        }
    }

    protected void showCalibrationModeDialog(String msg) {
        if (null == mCalibrationModeDialog) {
            mCalibrationModeDialog = new DefaultLoadingDialog(getActivity());
            mCalibrationModeDialog.setCancelable(false);
        }
        mCalibrationModeDialog.setMessage(msg);
        mCalibrationModeDialog.show();

        //先停止轮询查询线程
        TrainFragment.mTimeCount = -100;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendCalibrationModeCmd();
            }
        }, 1200);
    }

    /**
     * 发送校准命令
     */
    private void sendCalibrationModeCmd() {
        SendCalibrationModeCmdBean2 sendCalibrationModeCmdBean2 = new SendCalibrationModeCmdBean2();
        byte[] data = sendCalibrationModeCmdBean2.buildCmdByteArray();
        EventBus.getDefault().post(new CmdBleDataEvent(data));

        MLog.e(Config.getConfig().getDecodeUserName() + " ======== 发送校准命令  " +
                BaseParseCmdBean.bytesToStringWithSpace(data) + "----\n" + sendCalibrationModeCmdBean2.toString());

    }

    protected void dismissCalibrationModeDialog() {
        if (null != mCalibrationModeDialog) {
            mCalibrationModeDialog.dismiss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveDfuMode(DfuModeEvent2 dfuModeEvent2) {
        if (null != dfuModeEvent2 && dfuModeEvent2.isDfuModeSuccess()) {
            if (!CommonUtils.isEmpty(fireFilePath)) {
                startUpdate(fireFilePath);
            }
        } else {
            MLog.e("眼镜进入 DFU 模式失败！");
            ToastUtil.showLong("眼镜进入 DFU 模式失败！");
        }
    }

    //
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveFireVersionEvent(CallbackDataEvent firewareversion) {
        if (null != firewareversion) {
            BluetoothGattChannel bluetoothGattChannel = firewareversion.getBluetoothGattChannel();
            if (null != bluetoothGattChannel) {
                BluetoothGattCharacteristic characteristic = bluetoothGattChannel.getCharacteristic();
                if (null != characteristic) {
                    if (characteristic.getUuid().toString().equalsIgnoreCase(BleSppGattAttributes.BLE_SPP_Firmware_Revision_String_Characteristic)) {
                        byte[] dataByte = firewareversion.getData();
                        if (null != dataByte) {
                            String versionStr = new String(dataByte);
                            MLog.i("firewareversion =" + versionStr);
                            BleOptHelper.unBindReadChannel(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
//                            updateFirewareVersion(versionStr);
                            if (!BaseApplication.isDebugMode) {
                                getFiremwareVersionInfo(versionStr);
                            } else {
                                ToastUtil.showLong("当前固件版本为 " + versionStr);
                            }
                        }
                    }

                }
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveBleDeviceConnnectStatus(ConnectEvent connectEvent) {
        if (null != connectEvent) {
            if (!connectEvent.isSuccess()) {
                dismissStopGlassesDialog();
                dismissFiremwareUpdateProgressDialog();
            }
            DeviceMirror deviceMirror = connectEvent.getDeviceMirror();
            if (null != deviceMirror) {
                BluetoothLeDevice bluetoothLeDevice = deviceMirror.getBluetoothLeDevice();
                if (null != bluetoothLeDevice) {
                    String adviceName = bluetoothLeDevice.getName();
                    String adviceMac = bluetoothLeDevice.getAddress();
                    MLog.d("ConnectEvent adviceName = " + adviceName + " adviceMac = " + adviceMac);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveRunParamDone(ReceiveRunParamsDoneEvent receiveRunParamsDoneEvent) {
        if (null != receiveRunParamsDoneEvent && receiveRunParamsDoneEvent.isReceviewRunParamsDone()) {
            dismissStopGlassesDialog();
            if (updateFireware && !CommonUtils.isEmpty(fireFilePath) && new File(fireFilePath).length() > 0) {
                sendUpdateFireModeCmd();
                updateFireware = false;
            }
        }
    }

    /**
     * 校准模式返回数据
     * 从 GlassesBleDataModel --> handleReceiveBleData 传来
     *
     * @param modeCmdBean2
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveCalibrationModeCmdBean2(SendCalibrationModeCmdBean2 modeCmdBean2) {
        MLog.e("校准模式返回数据=========");
        dismissCalibrationModeDialog();

        //接收到蓝牙返回数据后--向蓝牙发送成功接收命令
        sendSuccessStatusCmd();

    }

    /**
     * APP接收到眼镜命令后，返回接收结果
     */
    private void sendSuccessStatusCmd() {
        SendSuccessStatusCmdBean2 sendSuccessStatusCmdBean2 = new SendSuccessStatusCmdBean2();
        EventBus.getDefault().post(new CmdBleDataEvent(sendSuccessStatusCmdBean2.buildCmdByteArray()));
    }

    private void dismissStopGlassesDialog() {
        if (null != mUpdateFiremwareStopGlassesDialog) {
            mUpdateFiremwareStopGlassesDialog.dismiss();
        }
    }

    private void sendUpdateFireModeCmd() {
        SendFireDFUModeBleCmdBeaan2 sendFireDFUModeBleCmdBeaan2 = new SendFireDFUModeBleCmdBeaan2();
        EventBus.getDefault().post(new CmdBleDataEvent(sendFireDFUModeBleCmdBeaan2.buildCmdByteArray()));
    }

    private String getFirewareVersionFromGlasse() {
        if (!BleDeviceManager.getInstance().isGlassesBleDeviceConnected()) {
            ToastUtil.showLong("眼镜未连接，无法查询固件信息！");
        }
        BleOptHelper.bindReadChannel(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
        BleDeviceManager.getInstance().readGlassData();
        return null;
    }


    private void logoutToken() {
        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
//        bodyMap.put(IOauthToken.ACCESS_TOKEN, Config.getConfig().getAccessToken());
//        bodyMap.put(IOauthToken.REFRESH_TOKEN, Config.getConfig().getFreshToken());
        LoginSubscribe.logoutToken(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String responseStr = null;
                try {
                    responseStr = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                OauthLogoutBean oauthLogoutBean = (OauthLogoutBean) JsonUtil.json2objectWithDataCheck(responseStr, new TypeReference<OauthLogoutBean>() {
                });
                //MLog.d("logoutToken responseStr = " + responseStr);
                if (null != oauthLogoutBean && !CommonUtils.isEmpty(oauthLogoutBean.getStatus())) {
                    Config.getConfig().saveFreshToken(null);
                    Config.getConfig().saveAccessToken(null);
                    Config.getConfig().saveUserName(null);
                    Config.getConfig().savePasswd(null);
                    UserInfoUtil.cleanMember();
                    BleDeviceManager.getInstance().stopScan();
                    BleDeviceManager.getInstance().stopScanByMac();
                    BleDeviceManager.getInstance().disconnectGlassesBleDevice(true);
                    GlassesBleDataModel.getInstance().clearModelData();
                    TrainModel.getInstance().clearTrainModelData();
                    //要求用户直接登录
                    Intent mIntent = new Intent();
                    ComponentName componentName = new ComponentName(MyApplication.getInstance(), PhoneLoginActivity.class);
                    mIntent.setComponent(componentName);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    MyApplication.getInstance().startActivity(mIntent);

                    List<Class<?>> keepList = new ArrayList<>();
                    keepList.add(PhoneLoginActivity.class);
                    ActivityStackUtil.getInstance().finishOthersActivity(keepList);//(keepList);

                } else {
                    if (null != oauthLogoutBean && !CommonUtils.isEmpty(oauthLogoutBean.getMessage())) {
                        ToastUtil.showShort(oauthLogoutBean.getMessage());
                    } else {
                        ToastUtil.showShort(R.string.interface_error_text);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(R.string.net_error_textt);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 获取固件最新的版本信息
     */
    private void getFiremwareVersionInfo(final String glassesFirewareVersion) {

        if (!checkNetworkAvaliable()) {
            return;
        }

        final HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IgetLastedSystemUpdate.FILETYPE, IgetLastedSystemUpdate.FILETYPE_FIRMWARE);
        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                disposableObserverList.remove(this);

                String jsonBody = null;
                try {
                    jsonBody = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HttpResponseFiremwareVersionInfoBean httpResponseFiremwareVersionInfoBean = (HttpResponseFiremwareVersionInfoBean) JsonUtil.json2objectWithDataCheck(jsonBody, new TypeReference<HttpResponseFiremwareVersionInfoBean>() {
                });
                handleFiremwareVersionInfo(httpResponseFiremwareVersionInfoBean, glassesFirewareVersion);
            }

            @Override
            public void onError(Throwable e) {
                disposableObserverList.remove(this);
                ToastUtil.showShort(R.string.net_error_textt);
            }

            @Override
            public void onComplete() {

            }
        };
        disposableObserverList.add(disposableObserver);
        TrainSuscribe.getApkVersionInfo(headerMap, bodyMap, disposableObserver);
    }

    private void handleFiremwareVersionInfo(HttpResponseFiremwareVersionInfoBean httpResponseFiremwareVersionInfoBean, String glassesFirewareVersion) {
        /***
         * 通过蓝牙协议查询当前固件信息，比对线上存储的固件版本信息
         */
        if (null != httpResponseFiremwareVersionInfoBean && httpResponseFiremwareVersionInfoBean.getStatus().equals(IBaseRequest.SUCCESS)) {
            HttpResponseFiremwareVersionInfoBean.DataBean dataBean = httpResponseFiremwareVersionInfoBean.getData();
            String currentFiremwareVersion = glassesFirewareVersion;//getCurrentFiremwareVersion();

            MLog.e("=============" + currentFiremwareVersion + "----" + dataBean.getVersion() + "===" +
                    dataBean.getVersion().compareToIgnoreCase(currentFiremwareVersion));

            if (!CommonUtils.isEmpty(dataBean.getVersion())
                    && !CommonUtils.isEmpty(currentFiremwareVersion)
                    && dataBean.getVersion().compareToIgnoreCase(currentFiremwareVersion) >= 1) {
                showFirmwareUpdateDialog(dataBean.getVersion(), true, httpResponseFiremwareVersionInfoBean);
            } else {
                showFirmwareUpdateDialog(currentFiremwareVersion, false, httpResponseFiremwareVersionInfoBean);
            }
        } else {
            ToastUtil.showShort("获取固件版本信息失败");
        }

    }

    private void showFirmwareUpdateDialog(String newVersionInfo, final boolean isNewVersion, final HttpResponseFiremwareVersionInfoBean httpResponseFiremwareVersionInfoBean) {
        if (null == httpResponseFiremwareVersionInfoBean || null == httpResponseFiremwareVersionInfoBean.getData()
                || !httpResponseFiremwareVersionInfoBean.getStatus().equals(IBaseRequest.SUCCESS)) {
            return;
        }

        if (getActivity() == null) {
            return;
        }

        if (firmwareDownloading.get()) {
            ToastUtil.showShort("正在下载中");
            return;
        }

        if (null == firmwareUpdateDialog) {
            firmwareUpdateDialog = new FirmwareUpdateDialog(getActivity());
            firmwareUpdateDialog.setDialogButtonClickListener(new DialogButtonClickListener() {
                @Override
                public void onButtonClick(int resourceId) {
                    if (resourceId == R.id.updatenewappversionbtn) {
                        if (isNewVersion) {
//                            ToastUtil.showShort("开始下载新版本");
                            handleFirmwareUpdate(httpResponseFiremwareVersionInfoBean);
                            firmwareUpdateDialog.dismiss();
                        } else {
                            firmwareUpdateDialog.dismiss();
                        }
                    } else {
                        firmwareUpdateDialog.dismiss();
                    }
                }
            });
        }
        String firemwareVersionInfo = null;
        if (isNewVersion) {
            firmwareUpdateDialog.setNewVersion(true);
            firemwareVersionInfo = "发现新版本，点击按钮下载更新";
        } else {
            firmwareUpdateDialog.setNewVersion(false);
            firemwareVersionInfo = "已是最新版本";
            //firemwareVersionInfo = newVersionInfo;
        }
        if (getActivity() != null) {
            firmwareUpdateDialog.showTip(firemwareVersionInfo);
        }

    }

    private void handleFirmwareUpdate(HttpResponseFiremwareVersionInfoBean httpResponseFiremwareVersionInfoBean) {
        if (null != httpResponseFiremwareVersionInfoBean &&
                httpResponseFiremwareVersionInfoBean.getStatus().equalsIgnoreCase(IBaseRequest.SUCCESS)) {
            HttpResponseFiremwareVersionInfoBean.DataBean dataBean = httpResponseFiremwareVersionInfoBean.getData();
            if (null != dataBean) {
                /**
                 * 通知栏中提示有版本更新
                 */
                String binDownLoadPath = dataBean.getFilePath();
                binDownLoadPath = CommonUtils.startWithHttp(binDownLoadPath) ? binDownLoadPath : URLConstant.BASE_URL + binDownLoadPath;
                String host = "";
                try {
                    URL url = new URL(binDownLoadPath);
                    host = url.getHost();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                String baseUrl = URLConstant.HTTP_PREFIX + host + File.separator;
                MLog.d("baseUrl = " + baseUrl);
                final String binAbsPath = getBinSavePath(dataBean);
                MLog.d("binAbsPath = " + binAbsPath);

                final File binFile = new File(binAbsPath);
                FileUtils.deleteFiles(binFile.getParentFile());

                if (!binFile.getParentFile().exists()) {
                    binFile.getParentFile().mkdirs();
                }
                createFirewareHander();
                showFirmwareDownloadingProgress.set(true);
                showFireWareDialog(0, true);
                DownloadUtils downloadUtils = new DownloadUtils(baseUrl, new JsDownloadListener() {
                    @Override
                    public void onStartDownload(long length) {
                        binTotalLength = length;
                        firmwareDownloading.set(true);
                    }

                    @Override
                    public void onProgress(int progress) {
                        if (showFirmwareDownloadingProgress.get()) {
                            if (null != firmWareHandler) {
                                Message message = firmWareHandler.obtainMessage();
                                if (null != message) {
                                    message.what = UPDATE_DOWNLOAD_PROGREESS;
                                    message.obj = Integer.valueOf((int) (progress * 1.0f * 100 / binTotalLength));
                                    message.sendToTarget();
                                }
                            }
                        } else {
                            if (null != firmWareHandler) {
                                Message message = firmWareHandler.obtainMessage();
                                if (null != message) {
                                    message.what = DIMISS_APP_DOWNLOAING_DIALOG;
                                    message.sendToTarget();
                                }
                            }
                        }

                    }

                    @Override
                    public void onFail(String errorInfo) {
                        MLog.d("errorInfo = " + errorInfo);
                        dismissFirewareDownloadProgressDialog();
                        firmwareDownloading.set(false);
                    }
                });

                Observable observable = downloadUtils.getDownApi(binDownLoadPath);//HttpMethods.getInstance().getHttpApi().downloadFile(new HashMap<String, Object>(), TrainSuscribe.createNewRequestBody(new HashMap<String, String>()), exeUrl);
                downloadUtils.download(binFile, observable, new DisposableObserver<InputStream>() {
                    @Override
                    public void onNext(InputStream responseBody) {
                        MLog.d("onNext = responseBody" + responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        MLog.d("onError = " + e.getMessage());
                        dismissFirewareDownloadProgressDialog();
                        firmwareDownloading.set(false);
                    }

                    @Override
                    public void onComplete() {
                        firmwareDownloading.set(false);
                        dismissFirewareDownloadProgressDialog();
                        MLog.d("onComplete = binfile = " + binFile.length() + " thread.id = " + Thread.currentThread().getId());
                        sendBin2Glasses(binFile);
                    }
                });

            }
        }
    }

    private void dismissFirewareDownloadProgressDialog() {
        if (null != firmwareDownloadNumProgressDialog) {
            firmwareDownloadNumProgressDialog.dismiss();
        }
    }

    /**
     * 将 bin 文件的内容发送给蓝牙眼镜，执行更新固件操作
     */
    private void sendBin2Glasses(File binFile) {
        String zipFilePath = binFile.getAbsolutePath();
        fireFilePath = zipFilePath;
        MLog.i("zipFilePath = " + zipFilePath);
        if (BleDeviceManager.getInstance().isGlassesBleDeviceConnected()) {

            SendFireDFUModeBleCmdBeaan2 sendFireDFUModeBleCmdBeaan2 = new SendFireDFUModeBleCmdBeaan2();
            EventBus.getDefault().post(new CmdBleDataEvent(sendFireDFUModeBleCmdBeaan2.buildCmdByteArray()));

//            if (isStopStatus()) {
//                sendUpdateFireModeCmd();
//            } else {
//                showStopGlassesDialog(true);
//            }
        } else {
            ToastUtil.showLong("未连接眼镜，请连接眼镜之后再尝试！");
        }
    }

    private void showStopGlassesDialog(boolean showStopTipDialog) {
        if (null == mUpdateFiremwareStopGlassesDialog) {
            mUpdateFiremwareStopGlassesDialog = new UpdateFiremwareStopGlassesDialog(getActivity());
            mUpdateFiremwareStopGlassesDialog.setDialogButtonClickListener(new DialogButtonClickListener() {
                @Override
                public void onButtonClick(int resourceId) {
                    if (resourceId == R.id.stopandupdatefirmwarebtn) {
                        updateFireware = true;
                        sendStopCmd();
                        showStopGlassesDialog(false);
                    }
                }
            });
        }
        if (showStopTipDialog) {
            mUpdateFiremwareStopGlassesDialog.showStopTipDialog();
        } else {
            mUpdateFiremwareStopGlassesDialog.showStopProgress();
        }
    }

    /**
     * 在进行固件更新之前，先检测眼镜当前状态是否为停止
     *
     * @return
     */
    private boolean isStopStatus() {
        int cureentStatus = GlassesBleDataModel.getInstance().getGlassesCurrentStatus();

        if (cureentStatus == SendUserInfoControlBleCmdBean.UNKNOW_OPERATION_CODE
                || cureentStatus == SendUserInfoControlBleCmdBean.STOP_OPERATION_CODE) {
            return true;
        }
        return false;
    }

    /**
     * 发送停止指令
     */
    private void sendStopCmd() {
        GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
        EventBus.getDefault().post(new CmdBleDataEvent(getStopActionOrder()));
    }

    private byte[] getStopActionOrder() {
        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.STOP_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }

    private void createFirewareHander() {
        if (null == firmWareHandler) {
            firmWareHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case UPDATE_DOWNLOAD_PROGREESS:
                            showFireWareDialog((Integer) msg.obj, false);
                            break;

                        case DIMISS_APP_DOWNLOAING_DIALOG:
                            dismissFirewareDownloadProgressDialog();
                            break;
                    }
                }
            };
        }
    }

    private void showFireWareDialog(int progress, boolean forceShow) {
        if (null == firmwareDownloadNumProgressDialog) {
            firmwareDownloadNumProgressDialog = new DLoadingNumProcessDialog(getActivity());
            firmwareDownloadNumProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    showFirmwareDownloadingProgress.set(false);
                }
            });
            firmwareDownloadNumProgressDialog.setCanceledOnTouchOutside(false);
            firmwareDownloadNumProgressDialog.show(progress);
        }

        if (forceShow) {
            firmwareDownloadNumProgressDialog.show();
        }
        if (firmwareDownloadNumProgressDialog.isShowing()) {
            firmwareDownloadNumProgressDialog.setProgrees(progress);
        }

    }

    private String getBinSavePath(HttpResponseFiremwareVersionInfoBean.DataBean dataBean) {
        String versionCode = dataBean.getVersion();
        String sourceFileName = dataBean.getFileName();
        Context context = MyApplication.getInstance();
        String packageName = context.getPackageName();
        String renameNewApkName = !CommonUtils.isEmpty(sourceFileName) ? sourceFileName : packageName + "_" + versionCode + ".zip";
        return MyApplication.getInstance().getHomePath() + File.separator + NEWAPKDIR + File.separator + renameNewApkName;
    }

    public void startUpdate(String filepath) {
        String mac = Config.getConfig().getLastConnectBleGlassesMac();
        String name = Config.getConfig().getLastConnectBleGlassesName();
        if (CommonUtils.isEmpty(mac) || !BleDeviceManager.getInstance().isGlassesBleDeviceConnected()) {
            ToastUtil.showShort("眼镜未连接，无法更新固件！");
            return;
        }


        /*int macLength = mac.length();
        String lastMacValue = mac.substring(macLength - 1);
        String newLastMacValue = Integer.toHexString(Integer.parseInt(lastMacValue, 16) + 1);
        String newMac = mac.substring(0, macLength - 1) + newLastMacValue;*/
        String newMac = macAddOne(mac);
        MLog.d("startUpdate" + "mac = " + mac + " newMac " + newMac + " name = " + name + "filepath = " + filepath);
        try {
            DfuServiceInitiator starter = new DfuServiceInitiator(newMac.toUpperCase())
                    .setDeviceName(name)
                    .setKeepBond(true);
            starter.setUnsafeExperimentalButtonlessServiceInSecureDfuEnabled(true);
            starter.setZip(Uri.fromFile(new File(filepath)), filepath);
            DfuServiceController controller = starter.start(MyApplication.getInstance(), DfuService.class);
            //ToastUtil.showLong("固件更新中，通知栏查看更新进度");
            //ToastUtil.showLong("即将进入固件升级模式");
            showFiremwareUpdateProgressDialog(getResources().getString(R.string.fireware_update_progress_tip));
        } catch (Exception e) {
            e.printStackTrace();
            dismissFiremwareUpdateProgressDialog();
        }
    }

    /**
     * @return
     */
    private String macAddOne(String sourceMac) {
        String[] perValue = sourceMac.split("\\:");
        int macLength = perValue.length;
        for (int i = macLength - 1; i >= 0; i--) {
            String oneValue = perValue[i];
            int intValue = Integer.parseInt(oneValue, 16);
            if (intValue < 0xff) {
                perValue[i] = Integer.toHexString(intValue + 1);
                break;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < perValue.length; j++) {
            if (j == 0) {
                stringBuilder.append(perValue[j]);
            } else {
                stringBuilder.append(":" + perValue[j]);
            }
        }

        return stringBuilder.toString().toUpperCase();
    }

}
