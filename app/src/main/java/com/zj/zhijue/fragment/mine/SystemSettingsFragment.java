package com.zj.zhijue.fragment.mine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.log.BleDataLog;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.FileUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.android.common.baselibrary.util.comutil.security.SignatureUtil;
import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.adapter.SystemSettingRecyclerViewAdapter;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.MemberLoginResponseBean;
import com.zj.zhijue.bean.SystemSettingBean;
import com.zj.zhijue.bean.bledata.send.SendFireDFUModeBleCmdBeaan2;
import com.zj.zhijue.bean.bledata.send.SendMachineBleCmdBeaan;
import com.zj.zhijue.bean.bledata.send.SendUserInfoBleCmdBean;
import com.zj.zhijue.bean.bledata.send.SendUserInfoControlBleCmdBean;
import com.zj.zhijue.bean.response.HttpResponseGlassesRunParamBean;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.ble.BleOptHelper;
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
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.http.request.IDeleteTrainInfo;
import com.zj.zhijue.http.request.IgetLastedSystemUpdate;
import com.zj.zhijue.http.response.DeleteTrainInfoResponseBean;
import com.zj.zhijue.http.response.HttpResponseApkVersionInfoBean;
import com.zj.zhijue.http.response.HttpResponseFiremwareVersionInfoBean;
import com.zj.zhijue.http.response.HttpResponseGlassInitDataBackBean;
import com.zj.zhijue.http.response.OauthLogoutBean;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.listener.OnItemClickListener;
import com.zj.zhijue.model.AppUpdateModel;
import com.zj.zhijue.model.GlassesBleDataModel;
import com.zj.zhijue.model.TrainModel;
import com.zj.zhijue.service.DfuService;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.UserInfoUtil;
import com.zj.zhijue.util.downloadutil.DownloadUtils;
import com.zj.zhijue.util.downloadutil.JsDownloadListener;
import com.zj.zhijue.util.jsonutil.GsonTools;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.zj.zhijue.view.recyclerview.GloriousRecyclerView;
import com.zj.zhijue.view.recyclerview.GridItemDecoration;
import com.vise.baseble.core.BluetoothGattChannel;
import com.vise.baseble.core.DeviceMirror;
import com.vise.baseble.model.BluetoothLeDevice;

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
public class SystemSettingsFragment extends BaseFragment {
    @BindView(R.id.function_item_title_tv)
    TextView functionItemTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout functionItemBackLayout;

    @BindView(R.id.system_settingrecyclerview)
    GloriousRecyclerView systemSettingGloriousRecyclerView;

    @BindView(R.id.exitloginbtn)
    Button exitLoginButton;

    SystemSettingRecyclerViewAdapter systemSettingRecyclerViewAdapter;

    List<SystemSettingBean> systemSettingBeans = new ArrayList<>();

    AppUpdateDialog appUpdateDialog = null;
    private DLoadingNumProcessDialog dLoadingNumProcessDialog = null;
    private DLoadingNumProcessDialog firmwareDownloadNumProgressDialog;

    private FirmwareUpdateDialog firmwareUpdateDialog = null;
    private UpdateFiremwareStopGlassesDialog mUpdateFiremwareStopGlassesDialog;
    private DefaultLoadingDialog mFiremwareUpdateDialog;

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
    public static final boolean isDebugMode = false;
    private String fireFilePath = null;
    private boolean updateFireware = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUpdateModel.getInstance().setContentNull();
        registerEventBus();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_systemsettings_layout, container, false);
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

    private void initView(View view) {
        functionItemTextView.setText(getResources().getText(R.string.system_settings_text));
        initRecyclerView();
    }

    private void initData() {
        systemSettingBeans.clear();
        String[] settings = getResources().getStringArray(R.array.system_settings);
        SystemSettingBean versionBean = new SystemSettingBean();
        versionBean.setItemPrefixImageResourceId(R.mipmap.banbengengxin);
        versionBean.setItemTilte(settings[0]);
        versionBean.setItemSufixImageResource(R.mipmap.jiantou);
        versionBean.setCurrentVersion(getApkVersion());//  AppUtils.getVersionName(getActivity())


      /*  SystemSettingBean apkupdateBean = new SystemSettingBean();

        apkupdateBean.setItemPrefixImageResourceId(R.mipmap-xhdpi.appgengxin);
        apkupdateBean.setItemTilte(settings[1]);
        apkupdateBean.setItemSufixImageResource(R.mipmap-xhdpi.jiantou);*/

        SystemSettingBean firmwareBean = new SystemSettingBean();

        firmwareBean.setItemPrefixImageResourceId(R.mipmap.gujiangengxin);
        firmwareBean.setItemTilte(settings[2]);
        firmwareBean.setItemSufixImageResource(R.mipmap.jiantou);

        systemSettingBeans.add(versionBean);
        //systemSettingBeans.add(apkupdateBean);
        systemSettingBeans.add(firmwareBean);

        if (isDebugMode) {
            SystemSettingBean userInfoBean = new SystemSettingBean();

            userInfoBean.setItemPrefixImageResourceId(R.mipmap.gujiangengxin);
            userInfoBean.setItemTilte(settings[3]);
            userInfoBean.setItemSufixImageResource(R.mipmap.jiantou);

            SystemSettingBean machineInfoBean = new SystemSettingBean();

            machineInfoBean.setItemPrefixImageResourceId(R.mipmap.gujiangengxin);
            machineInfoBean.setItemTilte(settings[4]);
            machineInfoBean.setItemSufixImageResource(R.mipmap.jiantou);
            systemSettingBeans.add(userInfoBean);
            systemSettingBeans.add(machineInfoBean);

            /**
             * 添加蓝牙读写日志的查看
             */
            SystemSettingBean bleReadWriteInfoBean = new SystemSettingBean();
            bleReadWriteInfoBean.setItemPrefixImageResourceId(R.mipmap.gujiangengxin);
            bleReadWriteInfoBean.setItemTilte(settings[5]);
            bleReadWriteInfoBean.setItemSufixImageResource(R.mipmap.jiantou);
            systemSettingBeans.add(bleReadWriteInfoBean);

            /**
             * 添加删除云端训练数据的调试按钮
             */
            SystemSettingBean deleteServerTrainInfoBean = new SystemSettingBean();
            deleteServerTrainInfoBean.setItemPrefixImageResourceId(R.mipmap.gujiangengxin);
            deleteServerTrainInfoBean.setItemTilte(settings[6]);
            deleteServerTrainInfoBean.setItemSufixImageResource(R.mipmap.jiantou);
            systemSettingBeans.add(deleteServerTrainInfoBean);
        }

        SystemSettingBean debugModeSwitchBean = new SystemSettingBean();
        debugModeSwitchBean.setItemPrefixImageResourceId(R.mipmap.gujiangengxin);
        debugModeSwitchBean.setItemTilte(settings[7]);
        debugModeSwitchBean.setItemSufixImageResource(R.mipmap.jiantou);
        systemSettingBeans.add(debugModeSwitchBean);
    }

    private void initRecyclerView() {
        /**
         * 初始化 RecyclerView
         */

        Activity activity = getActivity();
        if (null == activity) {
            return;
        }

        final GridLayoutManager manager = new GridLayoutManager(activity, 1);

        systemSettingRecyclerViewAdapter = new SystemSettingRecyclerViewAdapter(this);
        systemSettingGloriousRecyclerView.setAdapter(systemSettingRecyclerViewAdapter);

        GridItemDecoration gridItemDecoration = new GridItemDecoration.Builder(activity)
                .size((int) DeviceUtils.dipToPx(activity, 4))
                .color(R.color.assit_view_division_color)
                .margin(0, 0)
                .isExistHead(false)
                .showHeadDivider(false)
                .showLastDivider(false)
                .build();

        systemSettingGloriousRecyclerView.addItemDecoration(gridItemDecoration);
        systemSettingGloriousRecyclerView.setLayoutManager(manager);
        systemSettingRecyclerViewAdapter.setDatas(systemSettingBeans);
    }

    protected void initListener() {
        functionItemBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        systemSettingRecyclerViewAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                SystemSettingBean systemSettingBean = systemSettingBeans.get(position);
                handleItemClick(position);

            }
        });

        exitLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutToken();
            }
        });
    }

    /**
     * 保存删除云端训练数据菜单的点击时间
     */

    // 两次点击按钮之间的点击间隔
    private static final int MIN_CLICK_DELAY_TIME = 500;

    private int count = 0;//点击次数
    private long firstClick = 0;//第一次点击时间
    private long secondClick = 0;//第二次点击时间


    private void handleItemClick(int itemIndex) {
        switch (itemIndex) {
            case 0:
                AppUpdateModel.getInstance().setContext(getActivity());
                AppUpdateModel.getInstance().setOnlyShowDiff(false);
                AppUpdateModel.getInstance().getAppVersion();
                break;

            case 1:
                if (isDebugMode) {
                    showManualSelectFileDialog();
                } else {
                    if (false) {
                        copyBinFileFromAssets();
                        break;
                    } else {
                        getFirewareVersionFromGlasse();
                    }
                }
                break;

            case 2:
                showUserInfoLog();
                break;

            case 3:
                showMachineInfoLog();
                break;

            case 4:
                showBleDataDialog();
                break;

            case 5:
                count++;
                if (1 == count) {
                    firstClick = System.currentTimeMillis();//记录第一次点击时间
                    ToastUtil.showLongWithMsgColor("双击删除云端训练数据！", getResources().getColor(R.color.red_dard_hard_color));
                } else if (2 == count) {
                    secondClick = System.currentTimeMillis();//记录第二次点击时间
                    if (secondClick - firstClick < MIN_CLICK_DELAY_TIME) {//判断二次点击时间间隔是否在设定的间隔时间之内
                        showDeleteServerTrainInfoDialogTip();
                        count = 0;
                        firstClick = 0;
                    } else {
                        firstClick = secondClick;
                        count = 1;
                    }
                    secondClick = 0;
                }
                break;

            default:
                break;
        }
    }

    /**
     *
     * @param newVersion
     */
    private void updateFirewareVersion(String newVersion) {
        if (!CommonUtils.isEmpty(newVersion)) {
            int titleSize = systemSettingBeans.size();
            if (titleSize >= 2) {
                SystemSettingBean systemSettingBean = systemSettingBeans.get(1);
                systemSettingBean.setCurrentVersion(newVersion);
                /**
                 * 只调整固件版本信息的 Item
                 */
                systemSettingGloriousRecyclerView.getAdapter().notifyItemChanged(1);//(1);
            }
        }
    }

    private void showAppUpdateDialog(String newVersionInfo, final boolean isNewVersion,final HttpResponseApkVersionInfoBean httpResponseApkVersionInfoBean) {

        if (null == httpResponseApkVersionInfoBean || null == httpResponseApkVersionInfoBean.getData()
                || !httpResponseApkVersionInfoBean.getStatus().equals(IBaseRequest.SUCCESS)) {
            return;
        }
        if (appDownloading.get()) {
            ToastUtil.showShort("正在下载中");
            return;
        }

        if (null == appUpdateDialog) {
            appUpdateDialog = new AppUpdateDialog(getActivity());
            appUpdateDialog.setDialogButtonClickListener(new DialogButtonClickListener() {
                @Override
                public void onButtonClick(int resourceId) {
                    if (resourceId == R.id.updatenewappversionbtn) {
                        if (isNewVersion) {
                            handleAppUpdate(httpResponseApkVersionInfoBean);
                            appUpdateDialog.dismiss();
                        } else {
                            appUpdateDialog.dismiss();
                        }
                    }
                }
            });
        }
        String appupdateVersionInfo = null;
        if (isNewVersion) {
            appUpdateDialog.setNewVersion(true);
            appupdateVersionInfo = getString(R.string.app_update_verion_info_text);
        } else {
            appUpdateDialog.setNewVersion(false);
            appupdateVersionInfo = newVersionInfo;
        }
        appUpdateDialog.showTip(appupdateVersionInfo);
    }

    private void handleAppUpdate(HttpResponseApkVersionInfoBean httpResponseApkVersionInfoBean) {
        //ToastUtil.showShort(httpResponseGetLastedSystemUpdateBean.toString());
        if (null  != httpResponseApkVersionInfoBean &&
                httpResponseApkVersionInfoBean.getStatus().equalsIgnoreCase(IBaseRequest.SUCCESS)) {
            HttpResponseApkVersionInfoBean.DataBean dataBean = httpResponseApkVersionInfoBean.getData();
            if (null != dataBean) {
                String version = dataBean.getVersion();
                String versionFileStr = getApkVersion();
                if (!versionFileStr.equalsIgnoreCase(version)) {
                    /**
                     * 通知栏中提示有版本更新
                     */
                    String apkDownLoadPath = dataBean.getFilePath();
                    apkDownLoadPath = CommonUtils.startWithHttp(apkDownLoadPath) ? apkDownLoadPath : URLConstant.BASE_URL + apkDownLoadPath;
                    String host = "";
                    try {
                        URL url = new URL(apkDownLoadPath);
                        host = url.getHost();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    String baseUrl = URLConstant.HTTP_PREFIX + host + File.separator;
                    MLog.d("baseUrl = " + baseUrl);
                    final String apkAbsPath = getApkSavePath(dataBean.getVersion());
                    MLog.d("apkAbsPath = " + apkAbsPath);

                    final File apkFile = new File(apkAbsPath);
                    FileUtils.deleteFiles(apkFile.getParentFile());

                    if (!apkFile.getParentFile().exists()) {
                        apkFile.getParentFile().mkdirs();
                    }
                    createApkHandler();
                    showAppDownloadingProgress.set(true);
                    showNumDloadingDialog(0, true);
                    DownloadUtils downloadUtils = new DownloadUtils(baseUrl, new JsDownloadListener() {
                        @Override
                        public void onStartDownload(long length) {
                            //MLog.d("length = " + length + " thread.id = " + Thread.currentThread().getId());
                            appTotalLength = length;
                            appDownloading.set(true);
                        }

                        @Override
                        public void onProgress(int progress) {
                            //MLog.d("progress = " + progress + "Thread.id = " + Thread.currentThread().getId());
                            if (showAppDownloadingProgress.get()) {
                                if (null != apkHandler) {
                                    Message message = apkHandler.obtainMessage();
                                    if (null != message) {
                                        message.what = UPDATE_DOWNLOAD_PROGREESS;
                                        message.obj = Integer.valueOf((int) (progress * 1.0f * 100 / appTotalLength));
                                        message.sendToTarget();
                                    }
                                }
                            } else {
                                if (null != apkHandler) {
                                    Message message = apkHandler.obtainMessage();
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
                            dismissDloadingNumDialog();
                            appDownloading.set(false);
                        }
                    });

                    Observable observable = downloadUtils.getDownApi(apkDownLoadPath);//HttpMethods.getInstance().getHttpApi().downloadFile(new HashMap<String, Object>(), TrainSuscribe.createNewRequestBody(new HashMap<String, String>()), exeUrl);
                    downloadUtils.download(apkFile, observable, new DisposableObserver<InputStream>() {
                        @Override
                        public void onNext(InputStream responseBody) {
                            MLog.d("onNext = responseBody" + responseBody);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            MLog.d("onError = " + e.getMessage());
                            dismissDloadingNumDialog();
                            appDownloading.set(false);
                        }

                        @Override
                        public void onComplete() {
                            appDownloading.set(false);
                            dismissDloadingNumDialog();
                            MLog.d("onComplete = file = " + apkFile.length() + " thread.id = " + Thread.currentThread().getId() );

                            String selfSignature = SignatureUtil.getCurrentApplicationSignature(MyApplication.getInstance());
                            List<String> signatureFromApk = null;
                            try {
                                signatureFromApk = SignatureUtil.getSignaturesFromApk(apkFile);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (null != signatureFromApk && signatureFromApk.size() > 0
                                    && !CommonUtils.isEmpty(signatureFromApk.get(0))
                                    && signatureFromApk.get(0).equals(selfSignature)) {
                                //ToastUtil.showShort("签名一致");
                                installApk(apkFile);
                            } else {
                                ToastUtil.showShort("签名不一致");
                                try {
                                    apkFile.delete();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });


                }
            }
        }
    }

    private void handleFirmwareUpdate(HttpResponseFiremwareVersionInfoBean httpResponseFiremwareVersionInfoBean) {
        if (null  != httpResponseFiremwareVersionInfoBean &&
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
                        MLog.d("onComplete = binfile = " + binFile.length() + " thread.id = " + Thread.currentThread().getId() );
                        sendBin2Glasses(binFile);
                    }
                });

            }
        }
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

        String sourceZipName = "app_test1_v1_20191214152853.zip";// app_test1_v1_20191214145610.zip  app_test2_v1_20191214145526.zip
        if (!CommonUtils.isEmpty(glassname)) {
            if (glassname.toLowerCase().contains("test2")) {
                sourceZipName = "app_test1_v1_20191214152853.zip";
            } else {
                sourceZipName = "app_test2_v1_20191214152925.zip";
            }
        } else {
            sourceZipName = "app_test1_v1_20191214152853.zip";
        }

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


    private void showFirmwareUpdateDialog(String newVersionInfo, final boolean isNewVersion,final HttpResponseFiremwareVersionInfoBean httpResponseFiremwareVersionInfoBean) {
        if (null == httpResponseFiremwareVersionInfoBean || null == httpResponseFiremwareVersionInfoBean.getData()
                || !httpResponseFiremwareVersionInfoBean.getStatus().equals(IBaseRequest.SUCCESS)) {
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
                            //ToastUtil.showShort("开始下载新版本");
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
            firemwareVersionInfo = getString(R.string.firmware_update_verion_info_text);
        } else {
            firmwareUpdateDialog.setNewVersion(false);
            firemwareVersionInfo = "已是最新版本";
            //firemwareVersionInfo = newVersionInfo;
        }
        if(getActivity() != null){
            firmwareUpdateDialog.showTip(firemwareVersionInfo);
        }

    }

    private void dimissAppUpdateDialog() {
        if (null != appUpdateDialog) {
            appUpdateDialog.dismiss();
        }
    }

    private void dismissfirmwareDialog() {
        if (null != firmwareUpdateDialog) {
            firmwareUpdateDialog.dismiss();
        }
    }

    /**
     * 删除云端训练数据（调试用）
     */
    private void deletServerTrainInfoByDebug() {
        String memberId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(memberId)) {
            ToastUtil.showLong("用户ID为空，无法操作！");
            return;
        }
        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryRawUserInfoByServerID(MyApplication.getInstance(), memberId);
        String areaCode = null;
        if (null != userInfoDBBeanList && userInfoDBBeanList.size() > 0) {
            areaCode = userInfoDBBeanList.get(0).getAreaCode();
        }

        HashMap<String, Object> headerMap = new HashMap<>();
        headerMap.put(IDeleteTrainInfo.MEMBERID, memberId);
        headerMap.put(IDeleteTrainInfo.AREACODE, areaCode);

        LoginSubscribe.deleteServerTrainInfoWithDebug(headerMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String responseStr = null;
                try {
                    responseStr = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!CommonUtils.isEmpty(responseStr)) {
                    DeleteTrainInfoResponseBean deletTrainInfoBean = (DeleteTrainInfoResponseBean) JsonUtil.json2objectWithDataCheck(responseStr, new TypeReference<DeleteTrainInfoResponseBean>() {});
                    if (!CommonUtils.isEmpty(deletTrainInfoBean.getStatus()) && deletTrainInfoBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                        ToastUtil.showLong("删除" + deletTrainInfoBean.getMessage());
                    } else {
                        ToastUtil.showLong(responseStr);
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
                OauthLogoutBean oauthLogoutBean = (OauthLogoutBean) JsonUtil.json2objectWithDataCheck(responseStr, new TypeReference<OauthLogoutBean>() {});
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
                    BaseActivity.GotoLoginActivity();
                }else {
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

    private String getApkVersion() {
        byte[] apkVersion = FileUtils.readFileContentOfAssets(MyApplication.getInstance(), "commit-msg.txt");
        if (null != apkVersion) {
            return new String(apkVersion);
        }
        return "";
    }

    /**
     *  获取固件最新的版本信息
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
                HttpResponseFiremwareVersionInfoBean httpResponseFiremwareVersionInfoBean = (HttpResponseFiremwareVersionInfoBean) JsonUtil.json2objectWithDataCheck(jsonBody, new TypeReference<HttpResponseFiremwareVersionInfoBean>() {});
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

    private void handlerApkVersionInfo(HttpResponseApkVersionInfoBean httpResponseApkVersionInfoBean) {
        if (null != httpResponseApkVersionInfoBean && httpResponseApkVersionInfoBean.getStatus().equals(IBaseRequest.SUCCESS)) {
            HttpResponseApkVersionInfoBean.DataBean dataBean = httpResponseApkVersionInfoBean.getData();
            String currentVersion = getApkVersion();
            if (!CommonUtils.isEmpty(dataBean.getVersion())
                    && !CommonUtils.isEmpty(currentVersion)
                    && !dataBean.getVersion().equals(currentVersion)) {
                showAppUpdateDialog(dataBean.getVersion() , true, httpResponseApkVersionInfoBean);
            } else {
                showAppUpdateDialog(currentVersion , false, httpResponseApkVersionInfoBean);
            }
        } else {
            ToastUtil.showShort("获取应用版本信息失败");
        }

    }

    private void handleFiremwareVersionInfo(HttpResponseFiremwareVersionInfoBean httpResponseFiremwareVersionInfoBean, String glassesFirewareVersion) {
        /***
         * 通过蓝牙协议查询当前固件信息，比对线上存储的固件版本信息
         */
        if (null != httpResponseFiremwareVersionInfoBean && httpResponseFiremwareVersionInfoBean.getStatus().equals(IBaseRequest.SUCCESS)) {
            HttpResponseFiremwareVersionInfoBean.DataBean dataBean = httpResponseFiremwareVersionInfoBean.getData();
            String currentFiremwareVersion = glassesFirewareVersion;//getCurrentFiremwareVersion();
            if (!CommonUtils.isEmpty(dataBean.getVersion())
                && !CommonUtils.isEmpty(currentFiremwareVersion)
                && dataBean.getVersion().compareToIgnoreCase(currentFiremwareVersion) == 1) {
                showFirmwareUpdateDialog(dataBean.getVersion(), true, httpResponseFiremwareVersionInfoBean);
            } else {
                showFirmwareUpdateDialog(currentFiremwareVersion, false, httpResponseFiremwareVersionInfoBean);
            }
        }else {
            ToastUtil.showShort("获取固件版本信息失败");
        }

    }

    private void createApkHandler() {

        if (null == apkHandler) {
            apkHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case UPDATE_DOWNLOAD_PROGREESS:
                            showNumDloadingDialog((Integer) msg.obj, false);
                            break;

                        case DIMISS_APP_DOWNLOAING_DIALOG:
                            dismissDloadingNumDialog();
                            break;
                    }
                }
            };
        }

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

    private void showNumDloadingDialog(int progress, boolean forceShow) {
        if (null == dLoadingNumProcessDialog) {
            dLoadingNumProcessDialog = new DLoadingNumProcessDialog(getActivity());
            dLoadingNumProcessDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    showAppDownloadingProgress.set(false);
                }
            });

            dLoadingNumProcessDialog.setCanceledOnTouchOutside(true);
            dLoadingNumProcessDialog.show(progress);
        }

        if (forceShow) {
            dLoadingNumProcessDialog.show();
        }

        if (dLoadingNumProcessDialog.isShowing()) {
            dLoadingNumProcessDialog.setProgrees(progress);
        }
    }

    private void dismissDloadingNumDialog() {
        if (null != dLoadingNumProcessDialog) {
            dLoadingNumProcessDialog.dismiss();
        }
    }

    private void dismissFirewareDownloadProgressDialog() {
        if (null != firmwareDownloadNumProgressDialog) {
            firmwareDownloadNumProgressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        unregisterEventBus();
        AppUpdateModel.getInstance().setContentNull();
        dismissFiremwareUpdateProgressDialog();
        dismissfirmwareDialog();
        dismissFirewareDownloadProgressDialog();
        dismissDeleteTrainInfoDialog();
        dismissStopGlassesDialog();
        super.onDestroy();
        removeAllDisposableObserver();
    }

    private void removeAllDisposableObserver() {
        for (DisposableObserver disposableObserver : disposableObserverList) {
            if (null != disposableObserver) {
                disposableObserver.dispose();
            }
        }
    }

    private String getApkSavePath(String versionCode) {
        Context context = MyApplication.getInstance();
        String packageName = context.getPackageName();
        String renameNewApkName = packageName + "_" + versionCode + ".apk";
        return MyApplication.getInstance().getHomePath() + File.separator + NEWAPKDIR + File.separator + renameNewApkName;
    }

    private String getBinSavePath(HttpResponseFiremwareVersionInfoBean.DataBean dataBean) {
        String versionCode = dataBean.getVersion();
        String sourceFileName = dataBean.getFileName();
        Context context = MyApplication.getInstance();
        String packageName = context.getPackageName();
        String renameNewApkName = !CommonUtils.isEmpty(sourceFileName) ? sourceFileName : packageName + "_" + versionCode + ".zip";
        return MyApplication.getInstance().getHomePath() + File.separator + NEWAPKDIR + File.separator + renameNewApkName;
    }

    private void installApk(File apkFile) {
        MLog.d(Thread.currentThread().getId() + " installApk = " + apkFile.getAbsolutePath() + " ");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(MyApplication.getInstance(), MyApplication.getInstance().getPackageName() + ".fileprovider", apkFile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile),
                    "application/vnd.android.package-archive");
        }
        MyApplication.getInstance().startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveDfuMode(DfuModeEvent2 dfuModeEvent2) {
        if (null != dfuModeEvent2 && dfuModeEvent2.isDfuModeSuccess()) {
            if (!CommonUtils.isEmpty(fireFilePath)) {
                startUpdate(fireFilePath);
            }
        } else {
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
                if (null != characteristic ) {
                    if(characteristic.getUuid().toString().equalsIgnoreCase(BleSppGattAttributes.BLE_SPP_Firmware_Revision_String_Characteristic)) {
                        byte[] dataByte = firewareversion.getData();
                        if (null != dataByte) {
                            String versionStr = new String(dataByte);
                            MLog.i("firewareversion =" + versionStr);
                            BleOptHelper.unBindReadChannel(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
                            updateFirewareVersion(versionStr);
                            if (!isDebugMode) {
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

    private String getFirewareVersionFromGlasse() {
        if (!BleDeviceManager.getInstance().isGlassesBleDeviceConnected()) {
            ToastUtil.showLong("眼镜未连接，无法查询固件信息！");
        }
        BleOptHelper.bindReadChannel(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
        BleDeviceManager.getInstance().readGlassData();
        return null;
    }

    /**
     * 将 bin 文件的内容发送给蓝牙眼镜，执行更新固件操作
     */
    private void sendBin2Glasses(File binFile) {
        String zipFilePath = binFile.getAbsolutePath();
        fireFilePath = zipFilePath;
        MLog.i("zipFilePath = " + zipFilePath);
        if (BleDeviceManager.getInstance().isGlassesBleDeviceConnected()) {
            if (isStopStatus()) {
                sendUpdateFireModeCmd();
            } else {
                showStopGlassesDialog(true);
            }
        } else {
            ToastUtil.showLong("未连接眼镜，请连接眼镜之后再尝试！");
        }
    }

    private void sendUpdateFireModeCmd() {
        SendFireDFUModeBleCmdBeaan2 sendFireDFUModeBleCmdBeaan2 = new SendFireDFUModeBleCmdBeaan2();
        EventBus.getDefault().post(new CmdBleDataEvent(sendFireDFUModeBleCmdBeaan2.buildCmdByteArray()));
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
     *
     * @return
     */
    private String macAddOne(String sourceMac) {
        String[] perValue = sourceMac.split("\\:");
        int macLength = perValue.length;
        for (int i = macLength -1; i >=0; i--) {
            String oneValue = perValue[i];
            int intValue =Integer.parseInt(oneValue, 16);
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

    /**
     * 在进行固件更新之前，先检测眼镜当前状态是否为停止
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

    private void dismissStopGlassesDialog() {
        if (null != mUpdateFiremwareStopGlassesDialog) {
            mUpdateFiremwareStopGlassesDialog.dismiss();
        }
    }

    private final DfuProgressListener dfuProgressListener = new DfuProgressListener() {
        @Override
        public void onDeviceConnecting(String deviceAddress) {
//          progressBar.setIndeterminate(true);
//          mTextPercentage.setText(R.string.dfu_status_connecting);
            Log.i("TEST", "onDeviceConnecting: " + deviceAddress);
            showFiremwareUpdateProgressDialog(getResources().getString(R.string.fireware_update_progress_connecting_tip));
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
            showFiremwareUpdateProgressDialog("固件升级进度 "  + percent + "%");
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
            dismissFiremwareUpdateProgressDialog();
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

    private void showUserInfoLog() {
        String json = FileUtils.read(MyApplication.getInstance(), TrainModel.HTTP_LOGIN_USER_INFO);
        HttpResponseGlassInitDataBackBean httpResponseGlassInitDataBackBean = TrainModel.getInstance().getHttpResponseGlassInitDataBackBean();
        int isNewUser = -1;
        if (null != httpResponseGlassInitDataBackBean) {
            HttpResponseGlassInitDataBackBean.DataBean dataBean = httpResponseGlassInitDataBackBean.getData();
            if (null != dataBean) {
                isNewUser = dataBean.getIsNewUser();
            }
        }

        SendUserInfoBleCmdBean sendUserInfoBleCmdBean = TrainModel.getInstance().createUserInfoBleHexDataForTrain(Config.getConfig().getLastSelectedTrainMode() + 1, isNewUser, 1);
        String sendUserInfo = sendUserInfoBleCmdBean.toString();
        long currentUserId = GlassesBleDataModel.getInstance().getCurrentUserId();
        int currentGlassesStatus = GlassesBleDataModel.getInstance().getGlassesCurrentStatus();

        if (!CommonUtils.isEmpty(json)) {
            MemberLoginResponseBean memberLoginResponseBean =  GsonTools.changeGsonToBean(json, MemberLoginResponseBean.class);
            if (null != memberLoginResponseBean) {
                showBleDataLogDialog(memberLoginResponseBean.toString() +  "\n发送给眼镜的用户数据\n" + sendUserInfo + "\n" + createShowGlassesStatusInfo(currentUserId, currentGlassesStatus));
                return;
            }
        }
        showBleDataLogDialog("发送给眼镜的用户数据\n" + sendUserInfo + "\n" + createShowGlassesStatusInfo(currentUserId, currentGlassesStatus));
    }

    private String createShowGlassesStatusInfo(long glassesUserID, int glassRunStatus) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("眼镜存储的用户ID:" + glassesUserID + "\n");
        final String prefix = "蓝牙连接时查询到的眼镜的状态为：";

        switch (glassRunStatus) {
            case SendUserInfoControlBleCmdBean.START_OPERATION_CODE:
                stringBuilder.append( prefix+ "开始 " + glassRunStatus);
                break;

            case SendUserInfoControlBleCmdBean.PASUSE_OPERATION_CODE:
                stringBuilder.append(prefix+ "暂停 " + glassRunStatus);
                break;

            case SendUserInfoControlBleCmdBean.CONTINUE_OPERATION_CODE:
                stringBuilder.append(prefix+ "继续 " + glassRunStatus);
                break;

            case SendUserInfoControlBleCmdBean.STOP_OPERATION_CODE:
                stringBuilder.append(prefix+ "停止 " + glassRunStatus);
                break;

            case SendUserInfoControlBleCmdBean.INTEVENE_OPERATION_CODE:
                stringBuilder.append(prefix+ "干预 " + glassRunStatus);
                break;

            case SendUserInfoControlBleCmdBean.POWER_OFF_OPERATION_CODE:
                stringBuilder.append(prefix+ "关闭电源 " + glassRunStatus);
                break;

                default:
                    stringBuilder.append(prefix+ "未知 " + glassRunStatus);
                    break;
        }
        stringBuilder.append("\n\n");
        return stringBuilder.toString();
    }

    private void showMachineInfoLog() {
        String json = FileUtils.read(MyApplication.getInstance(), TrainModel.HTTP_GLASSES_INIT_MACHINE_FILE_NAME);

        HttpResponseGlassInitDataBackBean machineData = TrainModel.getInstance().getHttpResponseGlassInitDataBackBean();
        String machineDataStr = "初始机器数据为空";
        if (null != machineData && null != machineData.getData()) {
            SendMachineBleCmdBeaan sendMachineBleCmdBeaan = TrainModel.getInstance().createSendMachineBleHexDataForTrain();
            machineDataStr = sendMachineBleCmdBeaan.toString();
        }

        HttpResponseGlassesRunParamBean httpResponseGlassesRunParamBean = TrainModel.getInstance().getHttpResponseGlassesRunParamBean();

        String runParamStr = "运行参数数据为空";
        if (null != httpResponseGlassesRunParamBean) {
            runParamStr = httpResponseGlassesRunParamBean.toString();
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n发送机器数据为：\n" + machineDataStr + "\n发送的运行参数数据为:\n" + runParamStr);


        if (!CommonUtils.isEmpty(json)) {
            HttpResponseGlassInitDataBackBean httpResponseGlassInitDataBackBean = GsonTools.changeGsonToBean(json, HttpResponseGlassInitDataBackBean.class);
            if (null != httpResponseGlassInitDataBackBean) {
                showBleDataLogDialog(httpResponseGlassInitDataBackBean.toString() + stringBuffer.toString());
                return;
            }
        }
        showBleDataLogDialog(  stringBuffer.toString());
    }


    private void showBleDataLogDialog(String content) {
        if (null == bleDataLogDialog) {
            bleDataLogDialog = new BleDataLogDialog(getActivity());
        }
        bleDataLogDialog.showTip(content);
    }

    private void dimissBleDataDialog() {
        if (null != bleDataLogDialog) {
            bleDataLogDialog.dismiss();
        }
    }

    private void showBleDataDialog() {
        String bleLogPath = BleDataLog.getLogDirPath() + BleDataLog.getCommonLogFileName();
        String content = FileUtils.readTextFile(new File(bleLogPath));
        if (!CommonUtils.isEmpty(content)) {
            int textLength = content.length();
            if (textLength > 1024 * 1024) {
                showBleDataLogDialog(content.substring(textLength - 1024 * 1024) + "\n" + "已经到底了（日志文件写入较慢，可以关闭之后，再次打开查看，可能会有新数据写入，日志文件的读写顺序可能会出现颠倒的情况）");
            } else {
                showBleDataLogDialog(content + "\n" + "已经到底了（日志文件写入较慢，可以关闭之后，再次打开查看，可能会有新数据写入，日志文件的读写顺序可能会出现颠倒的情况）");
            }
        } else {
            showBleDataLogDialog("没有数据");
        }
    }

    private void showDeleteServerTrainInfoDialogTip() {
        if (null == mDeleteTrainInfoConfirmDialog) {
            mDeleteTrainInfoConfirmDialog = new DeleteTrainInfoConfirmDialog(getActivity());
            mDeleteTrainInfoConfirmDialog.setDialogButtonClickListener(new DialogButtonClickListener() {
                @Override
                public void onButtonClick(int resourceId) {
                    if (resourceId == R.id.deletesurebtn) {
                        deletServerTrainInfoByDebug();
                        dismissDeleteTrainInfoDialog();
                    }
                }
            });
        }
        mDeleteTrainInfoConfirmDialog.show();
    }

    private void dismissDeleteTrainInfoDialog() {
        if (null != mDeleteTrainInfoConfirmDialog) {
            mDeleteTrainInfoConfirmDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();

            if (uri == null)
                return;
            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                fireFilePath = uri.getPath();

            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                fireFilePath = getPath(getActivity(), uri);

            } else {//4.4以下下系统调用方法
                fireFilePath = getRealPathFromURI(uri);

            }

            MLog.d("fireFilePath =  " + fireFilePath);
            if (!CommonUtils.isEmpty(fireFilePath)) {
                if (requestCode == 1)  {
                    if (BleDeviceManager.getInstance().isGlassesBleDeviceConnected()) {
                        SendFireDFUModeBleCmdBeaan2 sendFireDFUModeBleCmdBeaan2 = new SendFireDFUModeBleCmdBeaan2();
                        EventBus.getDefault().post(new CmdBleDataEvent(sendFireDFUModeBleCmdBeaan2.buildCmdByteArray()));
                    } else {
                        ToastUtil.showLong("未连接眼镜，请连接眼镜之后再尝试！");
                    }
                }
            } else {
                ToastUtil.showLong("未选择固件文件，无法升级！");
            }
        }
    }


    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {


        Cursor cursor = null;

        final String column = "_data";

        final String[] projection = {column};


        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
