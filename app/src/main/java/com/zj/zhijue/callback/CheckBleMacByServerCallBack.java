package com.zj.zhijue.callback;


/**
 * 检测蓝牙眼镜是否可用的回调
 */
public interface CheckBleMacByServerCallBack {
    public void checkStatus(boolean avaliable, String mac);
}
