package com.zj.zhijue.service;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.PermissionUtil;
import com.zj.common.task.UpdateApkTask;


public class JobSchedulerService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        MLog.d("onStartJob");
        //UploadUtils.uploadLogFile();
        if (PermissionUtil.hasPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            UpdateApkTask.getInstance().checkoutApkVersion();
        }
        jobFinished(jobParameters, false);
        //任务执行完毕后，就可以调用jobFinished(JobParameters, boolean)方法通知结果，如果该方法的第二个参数传入true的话，就等于说：“事情这次做不完了，请计划在下次某个时间继续吧。”
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        MLog.d("onStopJob");
        //返回 true 表示：“任务应该计划在下次继续。”返回 false 表示：“不管怎样，事情就到此结束吧，不要计划下次了。”
        return false;
    }


}
