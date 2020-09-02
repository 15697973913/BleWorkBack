package com.zj.zhijue.base;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;

import com.android.common.baselibrary.log.BleDataLog;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.log.SdLogUtil;
import com.android.common.baselibrary.util.ActivityStackUtil;
import com.android.common.baselibrary.util.PermissionUtil;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.permission.PermissionHelper;
import com.android.common.baselibrary.util.comutil.permission.PermissionInterface;
import com.blankj.utilcode.util.LogUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.huige.library.utils.SharedPreferencesUtils;
import com.zj.common.http.retrofit.netutils.NetUtil;
import com.zj.common.task.UpdateApkTask;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.login.PhoneLoginActivity;
import com.zj.zhijue.config.Constants;
import com.zj.zhijue.data.source.BleGlassesRespository;
import com.zj.zhijue.data.source.local.BleGlassesLocalDataSource;
import com.zj.zhijue.data.source.local.BleGlassesLocalDataSourceImpl;
import com.zj.zhijue.data.source.remote.BleGlassesRemoteDataSource;
import com.zj.zhijue.data.source.remote.BleGlassesRemoteDataSourceImpl;
import com.zj.zhijue.dialog.DLoadingProcessDialog;
import com.zj.zhijue.service.JobSchedulerService;
import com.zj.zhijue.util.AppExecutors;
import com.zj.zhijue.util.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;


public class BaseActivity extends AppCompatActivity implements IBaseView, PermissionInterface {
    protected static final int REQUEST_CODE_ACCESS_COARSE_LOCATION = 1002;
    protected static final int REQUEST_PERMISSIONS_REQUEST_CODE = 10000;
    protected final int BLE_SCAN_QRCODE_REQUEST_CODE = 1201;
    protected final int REQUEST_CODE_OPEN_BLOOTH = 1202;
    public static final int ADD_FEEDBACK_REQUEST_CODE = 1203;

    private DLoadingProcessDialog loadingDialog;
    public BaseActivity mContext;
    protected Resources mResource;
    private PermissionHelper mPermissionHelper;


    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        handleNotEmptyBundle(bundle);
        ButterKnife.bind(this);
        this.mContext = this;
        this.mResource = getResources();

        ActivityStackUtil.getInstance().addActivity(this);
        //requestPermissions(getPermissions());
        changeStatusBarTextColor(true);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //sSdLogUtil.writeCommonLog("[" + this.getClass().getSimpleName() + "]onSaveInstanceState outState = " + outState);
    }

    protected void changeStatusBarTextColor(boolean isBlack) {
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(this).statusBarDarkFont(isBlack).init();
        }
    }

    /**
     * R.color.*
     *
     * @param colorInt
     */
    protected void initStatusBar(int colorInt) {
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(this).statusBarColor(colorInt).init();
        }
    }

    protected void handleNotEmptyBundle(Bundle bundle) {
        if (null != bundle) {
            SdLogUtil.writeCommonLog("handleNotEmptyBundle bundle = " + bundle);
        }
    }

    protected boolean checkNetworkAvaliable() {
        if (NetUtil.isNetworkAvalible(this)) {
            return true;
        }
        ToastUtil.showLong(R.string.no_network_error_tip);
        return false;
    }

    protected void setNewTheme() {
      /*  requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        //setTheme(R.style.AppTheme_Launcher_Show);
    }

    public void showLoading() {
        if (this.loadingDialog == null) {
            this.loadingDialog = new DLoadingProcessDialog(BaseActivity.this);
            this.loadingDialog.setOnDismissListener(new OnDismissListener() {
                public void onDismiss(DialogInterface dialogInterface) {
                    BaseActivity.this.loadingDialog = null;
                }
            });
            loadingDialog.show();
        }
    }

    public void showLoading(String loadingMsg) {
        showLoading();
        loadingDialog.setLoadingText(loadingMsg);

    }

    public void showLoading(boolean z) {

    }

    public void dismissLoading() {
        if (this.loadingDialog != null) {
            this.loadingDialog.dismiss();
            this.loadingDialog = null;
        }
    }

    public void toastMsg(String str) {
        ToastUtil.showShortToast(str);
    }

    protected void onDestroy() {
        super.onDestroy();
        ActivityStackUtil.getInstance().finishActivity(this);
    }

    private void showTaskDialog(String str) {

    }

    public void setTaskDialogContent(String str) {

    }

    public void dismissTaskDialog() {

    }

    public void showExitDialog(String str) {

    }

    public void showInfoDialog(String title, String message) {
        new Builder(BaseActivity.this).setTitle(title).setMessage(message).setPositiveButton((CharSequence) "确定", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    protected BleGlassesRespository getRespository() {
        if (null == BleGlassesRespository.getInstance()) {
            BleGlassesRespository bleGlassesRespository = null;
            synchronized (BaseActivity.class) {
                BleGlassesLocalDataSource localDataSource = BleGlassesLocalDataSourceImpl.getInstance(new AppExecutors());
                BleGlassesRemoteDataSource remoteDataSource = BleGlassesRemoteDataSourceImpl.getInstance();
                bleGlassesRespository = BleGlassesRespository.getInstance(localDataSource, remoteDataSource);
            }
            checkNotNull(bleGlassesRespository);
            return bleGlassesRespository;
        }
        return BleGlassesRespository.getInstance();
    }

    protected void requestPermissions(String[] permissions) {
        if (null == mPermissionHelper) {
            mPermissionHelper = new PermissionHelper(this, this);
        }
        mPermissionHelper.requestPermissions(permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MLog.d("requestPermissions requestCode = " + requestCode);
        if (null != mPermissionHelper && mPermissionHelper.requestPermissonsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public int getPermissonRequestCode() {
        //设置权限请求requestCode，只有不跟onRequestPermissionsResult方法中的其他请求码冲突即可。
        return REQUEST_PERMISSIONS_REQUEST_CODE;
    }

    @Override
    public String[] getPermissions() {
        return new String[]{
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
        };
    }

    @Override
    public void requestPermissionsSuccess(List<String> grantedPermissionsList) {
        //ToastDialogUtil.showShortToast("授权成功");
        if (null != grantedPermissionsList && grantedPermissionsList.size() > 0) {
            for (String permissions : grantedPermissionsList) {
                if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions)) {
                    //SdLogUtil.init();
                    BleDataLog.init();
                    break;
                }
            }
        }
    }

    @Override
    public void requestPermissionsFail(List<String> permissionList) {
        for (String permission : permissionList) {
            LogUtils.i(permission + "  授权失败");
        }
        if (permissionList.size() >= 1) {
            showDialogByPermission(permissionList, permissionList.get(0));
        } else {
            allDialogDismiss();
        }

    }

    protected void showDialogByPermission(List<String> permissionList, String permission) {
        if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
            onlyShowDialog(permissionList, permission, getResources().getText(R.string.denied_write_ext_storage_tip_text).toString());
        } else if (Manifest.permission.READ_PHONE_STATE.equals(permission)) {
            onlyShowDialog(permissionList, permission, getResources().getText(R.string.denied_read_phone_state_tip_text).toString());
        }
    }

    protected void onlyShowDialog(final List<String> permissionList, final String permission, String msg) {
        new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setPositiveButton(getResources().getString(R.string.sure_text), new OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        permissionList.remove(permission);
                        if (permissionList.size() >= 1) {
                            showDialogByPermission(permissionList, permissionList.get(0));
                        } else {
                            allDialogDismiss();
                            PermissionUtil.goAppDetailSettingActivity(MyApplication.getInstance());
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel_text), new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setCancelable(false)
                .setMessage(msg)
                .show();
    }

    @Override
    public void allDialogDismiss() {

    }


    /**
     * 启动版本检测更新的入口
     */
    protected void startUpdateService() {
        boolean isNetWorkOk = NetUtil.isNetworkAvalible(MyApplication.getInstance());
        LogUtils.d("isNetWorkOk = " + isNetWorkOk);
        if (isNetWorkOk) {
            if (PermissionUtil.hasPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                UpdateApkTask.getInstance().checkoutApkVersion();
            }
        } else {
            schedulerJob();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void schedulerJob() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        LogUtils.d("schedulerJob()");

        final int JOB_ID = 1;

        JobScheduler scheduler = (JobScheduler) this.getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, new ComponentName(getApplicationContext(), JobSchedulerService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                /*     .setPeriodic(1000 * 60 * 15)//每15分钟运行一次
                     .setPersisted(true)//保证服务在设备重启后也能按计划运行。*/
                .build();

        scheduler.schedule(jobInfo);
    }

    public static void GotoLoginActivity() {
        Config.getConfig().removeServerId();
        SharedPreferencesUtils.remove(Constants.IS_LOGIN);

        Intent mIntent = new Intent();
        ComponentName componentName = new ComponentName(MyApplication.getInstance(), PhoneLoginActivity.class);
        mIntent.setComponent(componentName);
        mIntent.putExtra("isInvalid", true);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        MyApplication.getInstance().startActivity(mIntent);

        List<Class<?>> keepList = new ArrayList<>();
        keepList.add(PhoneLoginActivity.class);
        ActivityStackUtil.getInstance().finishOthersActivity(keepList);//(keepList);
    }
}
