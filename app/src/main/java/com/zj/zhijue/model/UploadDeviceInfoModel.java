package com.zj.zhijue.model;

import android.Manifest;
import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.util.PermissionUtil;
import com.zj.zhijue.util.AppUtils;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.http.request.IDeviceInfoRequest;
import com.zj.zhijue.http.response.HttpResponseDeviceInfoBean;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.litesuits.common.utils.TelephoneUtil;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;


public class UploadDeviceInfoModel {


    private HashMap<String, Object> getDeviceInfoParamMap(Context context) {
        HashMap<String, Object> hashMap = new HashMap<>();
        String imei = null;
        String imsi = null;
        if (PermissionUtil.hasPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            imei = DeviceUtils.getIMEI(context);
            imsi = TelephoneUtil.getIMSI(context);
        }
        int android_sdk = DeviceUtils.getSDK_INI();
        String android_release = DeviceUtils.getDeviceSystemVersion();
        long apkVersion = AppUtils.getVersionCode();
        int screenWidth = DeviceUtils.getDeviceWidth(context);
        int screenHeight = DeviceUtils.getDeviceHeight(context);
        float density = DeviceUtils.getDeviceDensity(context);
        String manufacturer = DeviceUtils.getDeviceManufacturer();
        String product = DeviceUtils.getProduct();
        String brand = DeviceUtils.getBrand();
        String model = DeviceUtils.getModel();
        String board = DeviceUtils.getBoard();
        String device = DeviceUtils.getDevice();
        String servial = DeviceUtils.getServial();
        String ram = DeviceUtils.getRAMInfo(context);
        String mac = DeviceUtils.getMac(context);
        String appName = AppUtils.getAppName(context);

        String rom = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            rom = DeviceUtils.getStorageInfo(context,DeviceUtils.INTERNAL_STORAGE) + ";" + DeviceUtils.getStorageInfo(context, DeviceUtils.EXTERNAL_STORAGE);
        }

        hashMap.put(IDeviceInfoRequest.IMEI, imei);
        hashMap.put(IDeviceInfoRequest.ANDROIDSDK, String.valueOf(android_sdk));
        hashMap.put(IDeviceInfoRequest.ANDROIDRELEASE, android_release);
        hashMap.put(IDeviceInfoRequest.APKVERSION, String.valueOf(apkVersion));
        hashMap.put(IDeviceInfoRequest.SCREENWIDTH, String.valueOf(screenWidth));
        hashMap.put(IDeviceInfoRequest.SCREENHEIGHT, String.valueOf(screenHeight));
        hashMap.put(IDeviceInfoRequest.DENSITY, String.valueOf(density));
        hashMap.put(IDeviceInfoRequest.MANUFACTURER, manufacturer);
        hashMap.put(IDeviceInfoRequest.PRODUCT, product);
        hashMap.put(IDeviceInfoRequest.BRAND, brand);
        hashMap.put(IDeviceInfoRequest.MODEL, model);
        hashMap.put(IDeviceInfoRequest.BOARD, board);
        hashMap.put(IDeviceInfoRequest.DEVICE, device);
        hashMap.put(IDeviceInfoRequest.SERIALS, servial);
        hashMap.put(IDeviceInfoRequest.IMSI, imsi);
        hashMap.put(IDeviceInfoRequest.RAM, ram);
        hashMap.put(IDeviceInfoRequest.ROM, rom);
        hashMap.put(IDeviceInfoRequest.MAC, mac);
        hashMap.put(IDeviceInfoRequest.ANDROIDID, DeviceUtils.getAndroid_ID());
        hashMap.put(IDeviceInfoRequest.DEVICEID, DeviceUtils.getDevice_ID());
        hashMap.put(IDeviceInfoRequest.UTDID, AppUtils.getUtdId());
        hashMap.put(IDeviceInfoRequest.PKGNAME, appName);
        return hashMap;
    }

    public void postDeviInfo() {
        HashMap<String, Object> headerMap = new HashMap<>();
        TrainSuscribe.postDeivceInfo(headerMap, getDeviceInfoParamMap(MyApplication.getInstance()), new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HttpResponseDeviceInfoBean httpResponseDeviceInfoBean = (HttpResponseDeviceInfoBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseDeviceInfoBean>() {});
                //MLog.d("httpResponseDeviceInfoBean = " + httpResponseDeviceInfoBean);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


}
