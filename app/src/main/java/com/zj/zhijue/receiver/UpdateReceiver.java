package com.zj.zhijue.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zj.common.task.UpdateApkTask;
import com.zj.zhijue.bean.UpdateInfoBean;


public class UpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ( null != intent) {
            UpdateInfoBean updateInfoBean = intent.getParcelableExtra(UpdateApkTask.UPDATEINFOBEAN_KEY);
            if (null != updateInfoBean) {
                if (updateInfoBean.isHaveNewVersion()) {
                    UpdateApkTask.getInstance().startDownload(updateInfoBean);
                } else if (updateInfoBean.isNeedStopDownLoad()){
                    UpdateApkTask.getInstance().pasueDownLoading();
                }
            }
        }

    }
}
