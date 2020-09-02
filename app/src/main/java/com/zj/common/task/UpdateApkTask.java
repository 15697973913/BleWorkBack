package com.zj.common.task;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import android.widget.RemoteViews;


import com.blankj.utilcode.util.LogUtils;
import com.zj.common.listener.CheckApkVersionCallBack;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.bean.UpdateInfoBean;
import com.vise.utils.file.FileUtil;
import com.android.common.baselibrary.util.FileUtils;
import com.zj.zhijue.util.AppUtils;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.android.common.baselibrary.util.ThreadPoolUtils;
import com.android.common.baselibrary.util.comutil.security.Md5Util;
import com.android.common.baselibrary.util.comutil.security.SignatureUtil;
import com.zj.common.http.ContinueDownloader;
import com.zj.zhijue.dialog.DLoadingProcessDialog;
import com.zj.zhijue.receiver.UpdateReceiver;


import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * 检测 App 是否有版本更新，如果有则进行弹窗提示
 */
public class UpdateApkTask {
    private WeakReference<Service> serviceWeakReference = null;
    private static MyHandler myHandler = null;
    private ContinueDownloader continueDownloader = null;
    private final String NEWAPKDIR = "ShiXingNewApkDir";
    public final static String UPDATEINFOBEAN_KEY = "updateInfoBean";
    public final static String channelId = "channel_first";
    private final int UPDATE_TIP = 0;
    private final int UPDATE_PROGRESS = 1;
    private DLoadingProcessDialog processDialog = null;
    private NotificationManager notificationManager = null;
    private NotificationCompat.Builder builder = null;
    private Notification notification = null;
    private volatile AtomicInteger progressCount = new AtomicInteger(0);
    private final int NOTIFICATION_UPDATE_FREQUENCY = 20;//下载时，更新通知的频率
    private File apkFile = null;

    private static UpdateApkTask updateApkTask;

    private volatile boolean isRequestingServerVersionInfo = false;

    private static class SingleTon {
        private final static UpdateApkTask single = new UpdateApkTask();
    }

    public static UpdateApkTask getInstance() {
        if (null == updateApkTask) {
            updateApkTask = SingleTon.single;
        }
        return updateApkTask;
    }


    private class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            LogUtils.d("DownloadService handleMessage() msg = " + msg);
            //处理对应的逻辑
            switch (msg.what) {
                case UPDATE_TIP:
                    LogUtils.i("UPDATE_TIP ");
                    UpdateInfoBean updateInfoBean = (UpdateInfoBean) msg.obj;
                    if (updateInfoBean.isHaveNewVersion()
                            && !CommonUtils.isEmpty(updateInfoBean.getDownloadUrl())
                            && updateInfoBean.getDownloadUrl().startsWith("http")) {
                        promptUpdate(MyApplication.getInstance(), updateInfoBean);
                    } else {

                    }
                    break;

                case UPDATE_PROGRESS:
                    break;
                default:
                    break;
            }

        }
    }

    public void checkoutApkVersion() {
        if (isRequestingServerVersionInfo) {
            return;
        }
        isRequestingServerVersionInfo = true;
        myHandler = new MyHandler(Looper.getMainLooper());
        checkApkNewVersion(new CheckApkVersionCallBack() {
            @Override
            public void checkResult(UpdateInfoBean updateInfoBeanParam) {
                if (null != updateInfoBeanParam && updateInfoBeanParam.isHaveNewVersion()) {
                    if (null != myHandler) {
                        Message message = myHandler.obtainMessage();
                        message.what = UPDATE_TIP;
                        message.obj = updateInfoBeanParam;
                        message.sendToTarget();
                    }
                }
            }
        });
    }

    private void checkApkNewVersion(final CheckApkVersionCallBack checkApkVersionCallBack) {
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                UpdateInfoBean updateInfoBean = parseResult(requestServer());
                if (null != checkApkVersionCallBack) {
                    checkApkVersionCallBack.checkResult(updateInfoBean);
                }
            }
        });
    }

    private UpdateInfoBean parseResult(String result) {
        UpdateInfoBean updateInfoBean = null;
        if (!CommonUtils.isEmpty(result)) {
            updateInfoBean = new UpdateInfoBean();

        }

        updateInfoBean = new UpdateInfoBean();
        updateInfoBean.setHaveNewVersion(true);
        updateInfoBean.setVersionCode("25325");
        updateInfoBean.setDownloadUrl("");
        return updateInfoBean;
    }


    private String requestServer() {
        String result = "";
        return result;
    }

    public void startDownload(UpdateInfoBean updateInfoBean) {
        if (null == continueDownloader) {
            String apkAbsPath = getApkSavePath(updateInfoBean.getVersionCode());
            LogUtils.d("apkAbsPath = " + apkAbsPath);

            apkFile = new File(apkAbsPath);
            FileUtils.deleteFiles(apkFile.getParentFile());

            if (!apkFile.getParentFile().exists()) {
                apkFile.getParentFile().mkdirs();
            }

            LogUtils.d("apkFile.getParentFile().exists() = " + apkFile.getParentFile().exists());
            int avaiableCupNum = CommonUtils.getNumAvailableCores();
            LogUtils.d("avaiableCupNum = " + avaiableCupNum);

            continueDownloader = new ContinueDownloader(avaiableCupNum, apkFile, updateInfoBean.getDownloadUrl(), new ContinueDownloader.DownloadListener() {
                @Override
                public void downloadFailed(String str, Throwable th) {
                    LogUtils.d("downloadFailed str =" + str);
                }

                @Override
                public void downloadSuccessed() {
                    LogUtils.d("downloadSuccessed");
                   /* message message = myHandler.obtainMessage();
                    message.what = UPDATE_PROGRESS;
                    message.obj = Double.valueOf( 1);
                    message.sendToTarget();*/
                }

                @Override
                public void handleContentLength(int i) {
                    LogUtils.d("handleContentLength i = " + i);
                }

                @Override
                public void handleProgress(int i) {

                    if (i != 0) {
                        synchronized (progressCount) {
                            int downloadTotal = continueDownloader.getContentLength();
                            // LogUtils.i("downloadTotal = " + downloadTotal);
                            int perSize = downloadTotal / NOTIFICATION_UPDATE_FREQUENCY;
                            if (0 != downloadTotal) {
                                boolean needUpdate = false;

                                if (perSize != 0) {
                                    int planCount = i / perSize;
                                    if (planCount > progressCount.get()) {
                                        progressCount.set(planCount);
                                        needUpdate = true;
                                    }
                                } else {
                                    if (i > progressCount.get()) {
                                        progressCount.set(i);
                                        needUpdate = true;
                                    }
                                }

                                if (i == downloadTotal) {
                                    needUpdate = true;
                                }

                                if (needUpdate) {
                                    LogUtils.i("handleProgress progressCount = " + progressCount.get());
                                   /* message message = myHandler.obtainMessage();
                                    message.what = UPDATE_PROGRESS;
                                    message.obj = Double.valueOf(i * 1.00f / downloadTotal);
                                    message.sendToTarget();*/
                                    double currentProgress = Double.valueOf(i * 1.00f / downloadTotal);
                                    updateProgress(currentProgress);
                                    LogUtils.i("UPDATE_PROGRESS currentProgress = " + Double.valueOf(i * 1.00f / downloadTotal));
                                    if (Double.valueOf(i * 1.00f / downloadTotal) == 1) {
                                        clickAndInstallApk(serviceWeakReference.get(), apkFile);
                                    }
                                }

                            }
                        }
                    }
                }
            });
        }
        if (!continueDownloader.isDownloading()) {
            continueDownloader.restoreStateByFile();
        }

        continueDownloader.start();
        initDownloadNotification(serviceWeakReference.get());
    }

    private String getApkSavePath(String versionCode) {
        Context context = MyApplication.getInstance();
        String packageName = context.getPackageName();
        String renameNewApkName = packageName + "_" + versionCode + ".apk";
        return MyApplication.getInstance().getHomePath() + File.separator + NEWAPKDIR + File.separator + renameNewApkName;
    }

    private void showUpdateDialog(final Context activity, final UpdateInfoBean updateInfoBean) {
        new AlertDialog.Builder(activity)
                .setPositiveButton(activity.getResources().getString(R.string.sure_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        startDownload(updateInfoBean);
                        //showProgress(0 , activity);
                    }
                })
                .setNegativeButton(activity.getResources().getString(R.string.cancel_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        myHandler.removeCallbacksAndMessages(null);
                    }
                })
                .setCancelable(false)
                .setMessage(R.string.check_new_version_apk_tip_text)
                .show();
    }

    private void showProgress(double currentProgreesss, Context activity) {
        if (null == processDialog) {
            processDialog = new DLoadingProcessDialog(activity);
            processDialog.show();
        }
        if (!processDialog.isShowing()) {
            // processDialog.show((int) (currentProgreesss * 100));
        }
        //processDialog.setProgrees( (int) (currentProgreesss * 100));
        if (currentProgreesss == 1) {
            processDialog.dismiss();
        }
    }

    //初始化通知
    private void initDownloadNotification(Service service) {
        Context context = service;
        String downloadProgressText = AppUtils.getStringResource(MyApplication.getInstance(), R.string.download_progress_tip_text);
        String downloadStarrText = AppUtils.getStringResource(MyApplication.getInstance(), R.string.download_start_tip_text);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannelId(service, channelId);
            builder = new NotificationCompat.Builder(context, channelId);
        } else {
            builder = new NotificationCompat.Builder(service);
        }

        builder.setContentTitle(downloadStarrText) //设置通知标题
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(service.getResources(), R.mipmap.ic_launcher_round)) //设置通知的大图标
                .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
                .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知的优先级：最大
                .setAutoCancel(false)//设置通知被点击一次是否自动取消
                .setContentText(downloadProgressText + ":" + "0%")
                .setProgress(100, 0, false);
        builder.setDeleteIntent(getDeletePendingIntent(MyApplication.getInstance()));
        notification = builder.build();//构建通知对象

    }


    private synchronized void updateProgress(double progress) {
        builder.setProgress(100, (int) (progress * 100), false);
        String downloadProgressText = AppUtils.getStringResource(MyApplication.getInstance(), R.string.download_progress_tip_text);
        builder.setContentText(downloadProgressText + ":" + (int) (progress * 100) + "%");
        if (progress != 1) {
            builder.setContentIntent(PendingIntent.getActivity(serviceWeakReference.get(), 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
        }
        notification = builder.build();
        notificationManager.notify(1, notification);
    }

    private void clickAndInstallApk(Service service, File apkFile) {
        String downloadCompleteText = AppUtils.getStringResource(service, R.string.download_complete_tip_text);
        String clickInstallText = AppUtils.getStringResource(service, R.string.click_install_tip_text);
        builder.setContentTitle(downloadCompleteText)
                .setContentText(clickInstallText)
                .setAutoCancel(true);//设置通知被点击一次是否自动取消

        try {
            String downloadApkMd5 = Md5Util.getFileMD5String(apkFile);
            //LogUtils.d("downloadApkMd5 = " + downloadApkMd5 + " updateInfoBean.getVerifyMd5() = " + updateInfoBean.getVerifyMd5());
            String selfSignature = SignatureUtil.getCurrentApplicationSignature(MyApplication.getInstance());
            List<String> signatureFromApk = SignatureUtil.getSignaturesFromApk(apkFile);
            if (null != signatureFromApk && null != signatureFromApk.get(0)) {
                if (selfSignature.equals(signatureFromApk.get(0))) {
                } else {
                    FileUtil.deleteFile(apkFile);
                }
            } else {
                FileUtil.deleteFile(apkFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                FileUtil.deleteFile(apkFile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        install(service, apkFile);

    }

    private void promptUpdate(Context context, UpdateInfoBean updateInfoBean) {
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        //String channelId = "channel_second";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannelId(context, channelId);
            builder = new NotificationCompat.Builder(context, channelId);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        builder.setContentTitle(AppUtils.getAppName(context)) //设置通知标题
                .setSmallIcon(R.mipmap.ic_launcher_round)
                // .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher)) //设置通知的大图标
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE) //设置通知的提醒方式： 呼吸灯
                .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知的优先级：最大
                .setAutoCancel(true)//设置通知被点击一次是否自动取消
                .setWhen(System.currentTimeMillis())
                //.setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentText(AppUtils.getStringResource(context, R.string.check_new_version_click_download_tip_text))
                .setContentIntent(getPendingIntent(context, updateInfoBean));
        notification = builder.build();//构建通知对象
        notificationManager.notify(1, notification);
    }

    //自定义notification布局
    public static RemoteViews getRemoteViews(Context context, String info) {
        RemoteViews remoteviews = new RemoteViews(context.getPackageName(), R.layout.download_promp);
        remoteviews.setImageViewResource(R.id.download_promp_icon, R.mipmap.ic_launcher_round);
        remoteviews.setTextViewText(R.id.download_title, AppUtils.getAppName(context));
        remoteviews.setTextViewText(R.id.download_promp_info, info);
        //如果你需要对title中的哪一个小控件添加点击事件可以这样为控件添加点击事件（详情请下载代码进行观看）
        //remoteviews.setOnClickPendingIntent(R.id.download_notification_root,getPendingIntent(context));
        return remoteviews;
    }

    /**
     * 给通知栏添加点击事件，实现具体操作
     *
     * @param context 上下文
     * @return
     */
    private PendingIntent getPendingIntent(Context context, UpdateInfoBean updateInfoBean) {
        LogUtils.d("DownloadService getPendingIntent");
        Intent intent = new Intent(context, UpdateReceiver.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(UPDATEINFOBEAN_KEY, updateInfoBean);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }


    private PendingIntent getDeletePendingIntent(Context context) {
        LogUtils.d("DownloadService getDeletePendingIntent");
        UpdateInfoBean updateInfoBean = new UpdateInfoBean();
        updateInfoBean.setNeedStopDownLoad(true);
        Intent intent = new Intent(context, UpdateReceiver.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(UPDATEINFOBEAN_KEY, updateInfoBean);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    public void clearStatus() {
        if (null != myHandler) {
            myHandler.removeCallbacksAndMessages(null);
            myHandler = null;
        }

    }

    public void install(Context context, File apkFile) {
        LogUtils.d("install");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", apkFile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile),
                    "application/vnd.android.package-archive");
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        notification = builder.setContentIntent(pendingIntent).build();
        notificationManager.notify(1, notification);
    }

    /**
     * 创建分类
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannelId(Context context, String channel) {
        NotificationChannelGroup ncGroup = new NotificationChannelGroup("group_second", "通知渠道");
        //NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannelGroup(ncGroup);
        NotificationChannel chan = new NotificationChannel(channel, "通知渠道", NotificationManager.IMPORTANCE_DEFAULT);
        //锁屏的时候是否展示通知
        // VISIBILITY_PRIVATE : 显示基本信息，如通知的图标，但隐藏通知的全部内容；
        // VISIBILITY_PUBLIC : 显示通知的全部内容；
        // VISIBILITY_SECRET : 不显示任何内容，包括图标。
        chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        notificationManager.createNotificationChannel(chan);
    }

    public void pasueDownLoading() {
        if (null != continueDownloader) {
            continueDownloader.pause();
        }
    }


}
