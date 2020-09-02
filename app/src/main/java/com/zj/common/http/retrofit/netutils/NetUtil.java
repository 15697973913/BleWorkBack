package com.zj.common.http.retrofit.netutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.android.common.baselibrary.log.MLog;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.util.CommonUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


/**
 *
 * 网络请求工具类
 */
public class NetUtil {

    /**
     * 判断是否有网络连接
     *
     * @return
     */
    public static boolean isNetworkConnected() {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager
                .getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }

        return false;
    }

    /**
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApplication.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    public static boolean isWiFiActive() {
        ConnectivityManager connectivity = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信息
     *
     * @return
     */
    public static int getConnectedType() {
        if (MyApplication.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApplication.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    public static boolean isNetworkAvalible(Context context) {

        return CommonUtils.isNetWorkConnected(context);
    }

    public static String getIpAddress(Context context) {

        ConnectivityManager connect = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connect) {
            //检查网络连接
            NetworkInfo info = connect.getActiveNetworkInfo();
            if (null != info) {
                int netType = info.getType();
                int netSubtype = info.getSubtype();

                if (netType == ConnectivityManager.TYPE_WIFI) {  //WIFI
                    return getWifiIpAddress(context);
                } else if (netType == ConnectivityManager.TYPE_MOBILE ) {   //MOBILE
                    return getLocalIpAddress();
                }
            }
        }
        return "127.0.0.1";
    }

    //使用wifi
    public static String getWifiIpAddress(Context context) {

        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        //             if (!wifiManager.isWifiEnabled()) {
        //      <span style="white-space:pre">    </span>   wifiManager.setWifiEnabled(true);
        //             }
        if (null != wifiManager) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (null != wifiInfo) {
                int ipAddress = wifiInfo.getIpAddress();
                String ip = intToIp(ipAddress);
                return ip;
            }
        }

        return "127.0.0.1";
    }

    private static String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    //使用GPRS
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                if (null != intf) {
                    for (Enumeration<InetAddress> enumIpAddr = intf
                            .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        if (null != enumIpAddr) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (null != inetAddress && !inetAddress.isLoopbackAddress()) {
                                return inetAddress.getHostAddress().toString();
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            MLog.e("Exception" + ex.toString());
        }
        return "127.0.0.1";
    }

}
