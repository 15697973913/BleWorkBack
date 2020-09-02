package com.android.common.baselibrary.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.android.common.baselibrary.constant.BaseConstantString;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.receiver.NetworkChange;
import com.android.common.baselibrary.util.comutil.UIutil;
import com.android.common.baselibrary.log.SdLogUtil;
import com.android.common.baselibrary.util.comutil.log.CrashHandler;

import java.io.File;

public class BaseApplication extends Application {
    public static Activity context;
    private final String TAG = "BaseApplication";
    private static BaseApplication mApplication;
    public static boolean isDebugMode = true;


    @Override
    public void onCreate() {
        super.onCreate();
        UIutil.init();
        mApplication = this;
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d(TAG, "onActivityCreated;" + activity.getLocalClassName());
            }
            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(TAG, "onActivityStarted:" + activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG, "onActivityResumed:" + activity.getLocalClassName());
                context = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d(TAG, "onActivityPaused:" + activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(TAG, "onActivityStopped:" + activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(TAG, "onActivityDestroyed:" + activity.getLocalClassName());
            }
        });
        initData();
    }

    public static BaseApplication getInstance() {
        return mApplication;
    }

    private void initData() {
        SdLogUtil.init();
    }

    public String getHomePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + getInstance().getPackageName();
    }

    public String getUploadPath() {
        return getHomePath() + File.separator + "upload";
    }

    protected void initCrashHandler() {
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }
    /***
     * 监听网络状态改变
     */
    protected void registerNetWorkStatusCallBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            // 请注意这里会有一个版本适配bug，所以请在这里添加非空判断
            if (connectivityManager != null) {
                MLog.i("requestNetwork");
                connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {

                    /**
                     * 网络可用的回调
                     */
                    @Override
                    public void onAvailable(Network network) {
                        super.onAvailable(network);
                        MLog.i("onAvailable");
                        LocalBroadcastManager.getInstance(BaseApplication.getInstance()).sendBroadcast(new Intent(BaseApplication.getInstance().getPackageName() + BaseConstantString.NET_ACTION));

                    }

                    /**
                     * 网络丢失的回调
                     */
                    @Override
                    public void onLost(Network network) {
                        super.onLost(network);
                        MLog.e("onLost");
                    }

                    /**
                     * 当建立网络连接时，回调连接的属性
                     */
                    @Override
                    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                        super.onLinkPropertiesChanged(network, linkProperties);
                        MLog.e("onLinkPropertiesChanged");
                    }

                    /**
                     * 按照官方的字面意思是，当我们的网络的某个能力发生了变化回调，那么也就是说可能会回调多次
                     * <p>
                     * 之后在仔细的研究
                     */
                    @Override
                    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                        super.onCapabilitiesChanged(network, networkCapabilities);
                        //LogUtils.e("onCapabilitiesChanged");
                    }

                    /**
                     * 在网络失去连接的时候回调，但是如果是一个生硬的断开，他可能不回调
                     */
                    @Override
                    public void onLosing(Network network, int maxMsToLive) {
                        super.onLosing(network, maxMsToLive);
                        MLog.e("onLosing");
                    }

                    /**
                     * 按照官方注释的解释，是指如果在超时时间内都没有找到可用的网络时进行回调
                     */
                    @Override
                    public void onUnavailable() {
                        super.onUnavailable();
                        MLog.e("onUnavailable");
                    }

                });
            }
        } else {
            NetworkChange.getInstance().registerNetReceiver();
        }
    }
}
