package com.zj.zhijue.activity.function;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.fragment.systemsettings.AppUpdateFragment;
import com.zj.zhijue.fragment.systemsettings.AppVersionFragment;
import com.zj.zhijue.fragment.systemsettings.FirewareUpdateFragment;
import com.android.common.baselibrary.log.MLog;

/**
 * 系统设置 Item 选项,公用 Activity
 */
public class SystemSetttingItemActivity extends BaseActivity {

    public final static String SYSTEMSETTINGS_INDEX_KEY = "SYSTEMSETTINGS_INDEX_KEY";
    public final static String APK_VERSION_INFO_FRAGMENT_TAG = "APK_VERSION_INFO_FRAGMENT_TAG";
    public final static String APK_UPDATE_FRAGMENT_TAG = "APK_UPDATE_FRAGMENT_TAG";
    public final static String FIRMWARE_UPDATE_FRAGMENT_TAG = "FIRMWARE_UPDATE_FRAGMENT_TAG";

    public final static String FRAGMENT_INDEX_KEY = "FRAGMENT_INDEX_KEY";

    private int itemIndex = -1;

    private AppVersionFragment appVersionFragment;
    private AppUpdateFragment appUpdateFragment;
    private FirewareUpdateFragment firewareUpdateFragment;


    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setContentView(R.layout.activity_function_feedback_layout);
        super.onCreate(bundle);
        changeStatusBarTextColor(true);
        parseIntentData();
        showFragment(itemIndex);
    }

    private void parseIntentData() {
        Intent mIntent = getIntent();
        itemIndex = mIntent.getIntExtra(SYSTEMSETTINGS_INDEX_KEY, -1);
        MLog.d("itemIndex = " + itemIndex);
    }

    private void showFragment(int fragmentIndex) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);

        switch (fragmentIndex) {

            case 0:
                appVersionFragment = (AppVersionFragment) fragmentManager.findFragmentByTag(APK_VERSION_INFO_FRAGMENT_TAG);
                if (null == appVersionFragment) {
                    appVersionFragment = new AppVersionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(FRAGMENT_INDEX_KEY, itemIndex);
                    appVersionFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.framelayout, appVersionFragment, APK_VERSION_INFO_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(appVersionFragment);
                }
                break;

            case 1:
                appUpdateFragment = (AppUpdateFragment) fragmentManager.findFragmentByTag(APK_UPDATE_FRAGMENT_TAG);
                if (null == appUpdateFragment) {
                    appUpdateFragment = new AppUpdateFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(FRAGMENT_INDEX_KEY, itemIndex);
                    appUpdateFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.framelayout, appUpdateFragment, APK_UPDATE_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(appUpdateFragment);
                }
                break;

            case 2:
                firewareUpdateFragment = (FirewareUpdateFragment) fragmentManager.findFragmentByTag(APK_UPDATE_FRAGMENT_TAG);
                if (null == firewareUpdateFragment) {
                    firewareUpdateFragment = new FirewareUpdateFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(FRAGMENT_INDEX_KEY, itemIndex);
                    firewareUpdateFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.framelayout, firewareUpdateFragment, FIRMWARE_UPDATE_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(firewareUpdateFragment);
                }
                break;

        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    private void hideFragment(FragmentTransaction fragmentTransaction) {
        //如果此fragment不为空的话就隐藏起来
        if (null != appVersionFragment) {
            fragmentTransaction.hide(appVersionFragment);
        }

        if (null != appUpdateFragment) {
            fragmentTransaction.hide(appUpdateFragment);
        }

        if (null != firewareUpdateFragment) {
            fragmentTransaction.hide(firewareUpdateFragment);
        }
    }

}
