package com.zj.zhijue.model;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.core.content.FileProvider;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.FileUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.android.common.baselibrary.util.comutil.security.SignatureUtil;
import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.dialog.DLoadingNumProcessDialog;
import com.zj.zhijue.dialog.function.AppUpdateDialog;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.http.request.IgetLastedSystemUpdate;
import com.zj.zhijue.http.response.HttpResponseApkVersionInfoBean;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.util.downloadutil.DownloadUtils;
import com.zj.zhijue.util.downloadutil.JsDownloadListener;
import com.zj.zhijue.util.jsonutil.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 *  APP 版本检测更新
 */
public class AppUpdateModel {
    private List<DisposableObserver> disposableObserverList = new ArrayList<>();
    private static AtomicBoolean appDownloading = new AtomicBoolean(false);
    private AtomicBoolean showAppDownloadingProgress = new AtomicBoolean(true);
    private final String NEWAPKDIR = "ShiXingNewApkDir";
    private long appTotalLength = 0;

    private final int UPDATE_DOWNLOAD_PROGREESS = 1;
    private final int DIMISS_APP_DOWNLOAING_DIALOG = 2;
    private DLoadingNumProcessDialog dLoadingNumProcessDialog = null;


    private Handler apkHandler = null;
    private Context mContext;

    private AppUpdateDialog appUpdateDialog = null;
    private boolean onlyShowDiff = true;//进入主控界面，无版本更新是否不提示
    private AppUpdateModel() {
    }

    private static class Single {
        private static AppUpdateModel single = new AppUpdateModel();
    }

    public static AppUpdateModel getInstance() {
        return Single.single;
    }

    /** Context 的作用，主要是用于显示 Dialog
     * 注意内存泄漏，在Activity 的 onDestroy 方法里面，置空该 Context
     * @param context
     */
    public void setContext(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
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

    /**
     *  获取app最新的版本信息
     */
    public void getAppVersion() {
        if (null == getContext()) {
            throw  new NullPointerException("mContext can not be null!");
        }

        if (appDownloading.get()) {
            ToastUtil.showShort("正在下载中");
            return;
        }

        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("type", IgetLastedSystemUpdate.FILETYPE_ANDROID);
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
                HttpResponseApkVersionInfoBean httpResponseGetLastedSystemUpdateBean = (HttpResponseApkVersionInfoBean) JsonUtil.json2objectWithDataCheck(jsonBody, new TypeReference<HttpResponseApkVersionInfoBean>() {});
                handlerApkVersionInfo(httpResponseGetLastedSystemUpdateBean);
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

        TrainSuscribe.getAppVersion(headerMap, bodyMap, disposableObserver);
    }

    public void getApkVersionInfo() {
        if (null == getContext()) {
            throw  new NullPointerException("mContext can not be null!");
        }

        if (appDownloading.get()) {
            ToastUtil.showShort("正在下载中");
            return;
        }

        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IgetLastedSystemUpdate.FILETYPE, IgetLastedSystemUpdate.FILETYPE_ANDROID);
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
                HttpResponseApkVersionInfoBean httpResponseGetLastedSystemUpdateBean = (HttpResponseApkVersionInfoBean) JsonUtil.json2objectWithDataCheck(jsonBody, new TypeReference<HttpResponseApkVersionInfoBean>() {});
                handlerApkVersionInfo(httpResponseGetLastedSystemUpdateBean);
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
            MLog.d("dataBean  = " + dataBean.toString());
            if (!CommonUtils.isEmpty(dataBean.getVersion())
                    && !CommonUtils.isEmpty(currentVersion)
                    && !dataBean.getVersion().equals(currentVersion)) {
                showAppUpdateDialog(dataBean.getVersion() , true, httpResponseApkVersionInfoBean);
            } else {
                showAppUpdateDialog(currentVersion , false, httpResponseApkVersionInfoBean);
            }
        } else {
            MLog.e("获取应用版本信息失败");
//            ToastUtil.showShort(R.string.get_app_version_error);
        }

    }

    private String getApkVersion() {
        byte[] apkVersion = FileUtils.readFileContentOfAssets(MyApplication.getInstance(), "commit-msg.txt");
        if (null != apkVersion) {
            return new String(apkVersion);
        }
        return "";
    }

    private void showAppUpdateDialog(String newVersionInfo, final boolean isNewVersion,final HttpResponseApkVersionInfoBean httpResponseApkVersionInfoBean) {

        if (null == getContext()) {
            return;
        }

        if (!isNewVersion && onlyShowDiff) {
            /**
             * 无新版本时，从主控界面进入的不提示
             */
            return;
        }

        if (null == httpResponseApkVersionInfoBean || null == httpResponseApkVersionInfoBean.getData()
                || !httpResponseApkVersionInfoBean.getStatus().equals(IBaseRequest.SUCCESS)) {
            return;
        }
        if (appDownloading.get()) {
            ToastUtil.showShort("正在下载中");
            return;
        }

        if (null == appUpdateDialog) {
            appUpdateDialog = new AppUpdateDialog(getContext());
            appUpdateDialog.setDialogButtonClickListener(new DialogButtonClickListener() {
                @Override
                public void onButtonClick(int resourceId) {
                    if (resourceId == R.id.updatenewappversionbtn) {
                        if (isNewVersion) {
                            handleAppUpdate(httpResponseApkVersionInfoBean);
                        }
                        appUpdateDialog.dismiss();
                    } else {
                        appUpdateDialog.dismiss();
                    }
                }
            });
        }
        String appupdateVersionInfo = null;
        if (isNewVersion) {
            appUpdateDialog.setNewVersion(true);
            appupdateVersionInfo = getContext().getString(R.string.app_update_verion_info_text);
        } else {
            appUpdateDialog.setNewVersion(false);
            appupdateVersionInfo = String.format(getContext().getString(R.string.app_current_version_tip_text), newVersionInfo);
        }
        appUpdateDialog.showTip(appupdateVersionInfo);
    }

    private void handleAppUpdate(HttpResponseApkVersionInfoBean httpResponseApkVersionInfoBean) {
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

    private String getApkSavePath(String versionCode) {
        Context context = MyApplication.getInstance();
        String packageName = context.getPackageName();
        String renameNewApkName = packageName + "_" + versionCode + ".apk";
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

    private void dismissDloadingNumDialog() {
        if (null != dLoadingNumProcessDialog) {
            dLoadingNumProcessDialog.dismiss();
        }
    }

    private void dismissAppUpdateDialog() {
        if (null != appUpdateDialog) {
            appUpdateDialog.dismiss();
        }
    }

    private void showNumDloadingDialog(int progress, boolean forceShow) {
        if (null == dLoadingNumProcessDialog) {
            dLoadingNumProcessDialog = new DLoadingNumProcessDialog(getContext());
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

    /**
     *  在 Acivity Destory 方法里面，置空 Dialog
     */
    public void setContentNull() {
        if (null != apkHandler) {
            apkHandler.removeCallbacksAndMessages(null);
        }
        onlyShowDiff = true;
        setContext(null);
        dismissAppUpdateDialog();
        dismissDloadingNumDialog();
        /**
         * 置空的目的是，使用新的 Context 构建 Dialog
         * */
        appUpdateDialog = null;
        dLoadingNumProcessDialog = null;
    }

    public boolean isOnlyShowDiff() {
        return onlyShowDiff;
    }

    public void setOnlyShowDiff(boolean onlyShowDiff) {
        this.onlyShowDiff = onlyShowDiff;
    }
}
