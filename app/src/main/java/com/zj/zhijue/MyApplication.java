package com.zj.zhijue;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.multidex.MultiDex;

import com.android.common.baselibrary.app.BaseApplication;
import com.facebook.stetho.Stetho;
import com.huige.library.HGUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.uuzuche.lib_zxing.BuildConfig;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zj.common.http.OkHttpUtil;
import com.zj.common.http.UploadUtils;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.greendao.greenddaodb.DatabaseManager;
import com.zj.zhijue.greendao.greenddaodb.DatabaseManagerImpl;
import com.zj.zhijue.model.GlassesBleDataCmdModel;
import com.zj.zhijue.model.GlassesBleDataModel;
import com.zj.zhijue.model.TrainModel;
import com.zj.zhijue.model.UploadDeviceInfoModel;
import com.zj.zhijue.util.UserInfoUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/6/5.
 */

public class MyApplication extends BaseApplication {
    private static MyApplication mApplication;
    private DatabaseManager databaseManager = new DatabaseManagerImpl();
    public static int H, W;

    @Override
    public void onCreate() {
        super.onCreate();
        getScreen(this);
        createDatabaseManagerImpl();
        mApplication = this;
        initData();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MyApplication getInstance() {
        return mApplication;
    }

    private void initData() {
        OkHttpUtil.init();
        //SdLogUtil.init();
//        initCrashHandler();
        ZXingLibrary.initDisplayOpinion(this);
        BleDeviceManager.getInstance().init(getApplicationContext());
        //Stetho调试工具初始化
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
        HGUtils.init(this);
        initBugly();
        initUMShare();
        uploadCrashLogZipFile();
        registerNetWorkStatusCallBack();
        GlassesBleDataModel.getInstance();
        GlassesBleDataCmdModel.getInstance();
        if (UserInfoUtil.checkLogin()) {
            postDeviceInfoData();
            postTrainBleDataFromDBO();
        }

        //极光推送
        JPushInterface.setDebugMode(false);// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);// 初始化 JPush
    }

    private void createDatabaseManagerImpl() {
        databaseManager.startup(this);
    }

    public DatabaseManager getDataBaseManager() {
        return databaseManager;
    }

    private void uploadCrashLogZipFile() {
        UploadUtils.uploadLogFile();
    }

    private void postDeviceInfoData() {
        UploadDeviceInfoModel uploadDeviceInfoModel = new UploadDeviceInfoModel();
        uploadDeviceInfoModel.postDeviInfo();
    }

    private void postTrainBleDataFromDBO() {
        TrainModel.getInstance().handleBleDataInDB2Server();

        /**
         * 准备眼镜的训练数据
         */
        TrainModel.getInstance().prepareGlassesTrainData(false, false, false);

    }

    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H = dm.heightPixels;
        W = dm.widthPixels;
    }

    //初始化友盟分享
    private void initUMShare() {
        UMConfigure.init(this, "5ef804da978eea088379e324", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        PlatformConfig.setWeixin("wx2522e4905bb26de4", "85718f8fd8a5923b649d6297b4337e9c");
//        PlatformConfig.setQQZone("1110284873","04emfmgob3KHoRY9");
    }

    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), "396998e7e8", BuildConfig.DEBUG);
    }


}
