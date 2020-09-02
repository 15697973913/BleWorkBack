package com.zj.zhijue.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.common.baselibrary.jnative.SecUtil;
import com.blankj.utilcode.util.LogUtils;
import com.zj.zhijue.MyApplication;


public class Config {
    private Context mContext;
    private SharedPreferences preferences;

    private final static String CHECK_VERSION_INTERVAL_TIME_KEY = "CHECK_VERSION_INTERVAL_TIME_KEY";//检测应用版本信息间隔时间
    private final long DEFAULT_CHECK_VERSION_INTERVAL_TIME = 8L * 60 * 60 * 1000; //默认间隔时间值
    /**
     * 最近一次的请求版本更新接口的时间
     */
    private final static String LAST_CHECK_VERSION_TIME_KEY = "LAST_CHECK_VERSION_TIME_KEY";

    private final String KEY_USER_SERVER_ID = "KEY_USER_SERVER_ID";

    private final String KEY_BLE_GLASSES_MAC = "KEY_BLE_GLASSES_MAC";
    private static final String KEY_BLE_GLASSES_UUID ="KEY_BLE_GLASSES_UUID" ;
    private final String KEY_BLE_GLASSES_NAME = "KEY_BLE_GLASSES_NAME";
    private final String KEY_BLE_DFU_GLASSES_NAME = "KEY_BLE_DFU_GLASSES_NAME";

    private final String KEY_REMBER_ME_LOGIN = "KEY_REMBER_ME_LOGIN";//登录记住我

    private final String KEY_USERNAME = "KEY_USERNAME";
    private final String KEY_PASSWD = "KEY_PASSWD";

    private final String KEY_OAUTHON_ACCESSS_TOKEN = "KEY_OAUTHON_ACCESSS_TOKEN";
    private final String KEY_OAUTHON_FRESH_TOKEN = "KEY_OAUTHON_FRESH_TOKEN";

    private final String KEY_TRAIN_START_TIME = "KEY_TRAIN_START_TIME";//开始训练的时间

    private final String KEY_LAST_BATTEY_LEVEL = "KEY_LAST_BATTEY_LEVEL";//保存上次存储的电池电量

    private final String KEY_LAST_SELECTE_TRAIN_MODE = "KEY_LAST_SELECTE_TRAIN_MODE";// 保存选择的训练模式

    private final String KEY_DEBUG_MODE_SWITCH = "KEY_DEBUG_MODE_SWITCH";//设置里面的 Debug 模式是否开启

    private final String KEY_BATTERY_LOW_TIP = "KEY_BATTERY_LOW_TIP";

    private Config() {

    }

    public static Config getConfig() {
        Config config = SingleTon.single;
        config.mContext = MyApplication.getInstance();
        config.preferences = PreferenceManager.getDefaultSharedPreferences(config.mContext);
        return config;
    }

    private static class SingleTon {
        private final static Config single = new Config();
    }

    public long getNextCheckNewVersionIntervalTime() {
        return this.preferences.getLong(CHECK_VERSION_INTERVAL_TIME_KEY, DEFAULT_CHECK_VERSION_INTERVAL_TIME);
    }

    public void saveNextCheckNewVersionIntervalTime(long intervalTime) {
        this.preferences.edit().putLong(CHECK_VERSION_INTERVAL_TIME_KEY, intervalTime).commit();
    }

    public void saveLastCheckVersionTime(long checkTime) {
        this.preferences.edit().putLong(LAST_CHECK_VERSION_TIME_KEY, checkTime).commit();
    }

    public long getLastCheckVersionTime() {
        return this.preferences.getLong(LAST_CHECK_VERSION_TIME_KEY, 0);
    }

    public String getUserServerId() {
        return this.preferences.getString(KEY_USER_SERVER_ID, null);
    }

    public void saveUserServerId(String userServerId) {
        this.preferences.edit().putString(KEY_USER_SERVER_ID, userServerId).apply();
    }

    public void removeServerId() {
        preferences.edit().remove(KEY_USER_SERVER_ID).apply();
    }

    /**
     * 获取上次连接的蓝牙眼镜 MAC 地址
     *
     * @param macAddress
     */
    public void saveLastConnectBleGlassesMac(String macAddress) {
        this.preferences.edit().putString(KEY_BLE_GLASSES_MAC, macAddress).commit();
    }

    public String getLastConnectBleGlassesMac() {
        return this.preferences.getString(KEY_BLE_GLASSES_MAC, "");
    }

    /**
     * 保存蓝牙的UUID
     *
     * @param deviceUUID
     */
    public void saveLastConnectBleGlassesUUID(String deviceUUID) {
        LogUtils.v("保存的uuid："+deviceUUID);
        this.preferences.edit().putString(KEY_BLE_GLASSES_UUID, deviceUUID).commit();
    }

    /**
     * 获取蓝牙的UUID
     *
     */
    public String getLastConnectBleGlassesUUID() {
        return this.preferences.getString(KEY_BLE_GLASSES_UUID, "");
    }


    /**
     * 获取上次连接的蓝牙眼镜 名字
     *
     * @param glassesName
     */
    public void saveLastConnectBleGlassesName(String glassesName) {
        this.preferences.edit().putString(KEY_BLE_GLASSES_NAME, glassesName).commit();
    }

    public String getLastConnectBleGlassesName() {
        return this.preferences.getString(KEY_BLE_GLASSES_NAME, "");
    }

    public void saveLastDfuBleGlassesName(String glassesName) {
        this.preferences.edit().putString(KEY_BLE_DFU_GLASSES_NAME, glassesName).commit();
    }

    public String getDfuGlassName() {
        return this.preferences.getString(KEY_BLE_DFU_GLASSES_NAME, "");
    }

    public boolean isRemeberMe() {
        return this.preferences.getBoolean(KEY_REMBER_ME_LOGIN, false);
    }

    public void saveRemeberMeStatus(boolean isRememberMe) {
        this.preferences.edit().putBoolean(KEY_REMBER_ME_LOGIN, isRememberMe).commit();
    }

    public String getUserName() {
        return this.preferences.getString(KEY_USERNAME, "");
    }

    public String getDecodeUserName() {
        String userName = this.preferences.getString(KEY_USERNAME, "");
        return SecUtil.decodeByAES(userName);
    }

    public void saveUserName(String userName) {
        this.preferences.edit().putString(KEY_USERNAME, userName).commit();
    }

    public String getPasswd() {
        return this.preferences.getString(KEY_PASSWD, "");
    }

    public void savePasswd(String passwd) {
        this.preferences.edit().putString(KEY_PASSWD, passwd).commit();
    }

    public void saveAccessToken(String accessToken) {
        this.preferences.edit().putString(KEY_OAUTHON_ACCESSS_TOKEN, accessToken).commit();
    }

    public void saveFreshToken(String freshToken) {
        this.preferences.edit().putString(KEY_OAUTHON_FRESH_TOKEN, freshToken).commit();
    }

    public String getAccessToken() {
        return this.preferences.getString(KEY_OAUTHON_ACCESSS_TOKEN, "");
    }

    public String getFreshToken() {
        return this.preferences.getString(KEY_OAUTHON_FRESH_TOKEN, "");
    }

    public String getTrainStartTime() {
        return this.preferences.getString(KEY_TRAIN_START_TIME, "");
    }

    public void saveTrainStartTime(String trainStartTime) {
        this.preferences.edit().putString(KEY_TRAIN_START_TIME, trainStartTime).commit();
    }

    public void saveLastBatterLevel(int battery) {
        this.preferences.edit().putInt(KEY_LAST_BATTEY_LEVEL, battery).commit();
    }

    public int getLastBattteryLevel() {
        return this.preferences.getInt(KEY_LAST_BATTEY_LEVEL, 0);
    }

    public void saveLastSelectedTrainMode(int trainMode) {
        this.preferences.edit().putInt(KEY_LAST_SELECTE_TRAIN_MODE, trainMode).commit();
    }

    public int getLastSelectedTrainMode() {
        /**
         * 返回的是训练模式的 positon 从 0 开始的  默认返回矫正模式的值 1
         */
        return this.preferences.getInt(KEY_LAST_SELECTE_TRAIN_MODE, 1);
    }

    public void saveDebugModeSwitch(boolean openDebugMode) {
        this.preferences.edit().putBoolean(KEY_DEBUG_MODE_SWITCH, openDebugMode).commit();
    }

    public boolean getDebugModeSwitch() {
        return this.preferences.getBoolean(KEY_DEBUG_MODE_SWITCH, false);
    }

    public void saveBatteryLowTip(boolean needTipAgain) {
        this.preferences.edit().putBoolean(KEY_BATTERY_LOW_TIP, needTipAgain).commit();
    }

    public boolean getNeedLowTip() {
        return this.preferences.getBoolean(KEY_BATTERY_LOW_TIP, true);
    }
}