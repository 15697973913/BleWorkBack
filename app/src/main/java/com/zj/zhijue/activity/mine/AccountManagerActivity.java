package com.zj.zhijue.activity.mine;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zj.zhijue.R;
import com.zj.zhijue.fragment.mine.AccountRemainFragment;
import com.zj.zhijue.fragment.mine.GuardiansFragment;
import com.zj.zhijue.fragment.mine.IntegralFragment;
import com.zj.zhijue.fragment.mine.OrderConfirmationFragment;
import com.zj.zhijue.fragment.mine.PersonalInfoFragment;
import com.zj.zhijue.fragment.mine.ResetPasswdFragment;
import com.android.common.baselibrary.log.MLog;

public class AccountManagerActivity extends MineBaseActivity {
    private IntegralFragment integralFragment;
    private AccountRemainFragment accountRemainFragment;
    private GuardiansFragment guardiansFragment;
    private PersonalInfoFragment personalInfoFragment;
    private ResetPasswdFragment resetPasswdFragment;
    private OrderConfirmationFragment orderConfirmationFragment;

    public final static String INTEGRAL_FRAGMENT_TAG = "INTEGRAL_FRAGMENT_TAG";
    public final static String ACCOUNT_REMAIN_FRAGMENT_TAG = "ACCOUNT_REMAIN_FRAGMENT_TAG";
    public final static String GUARDIANS_FRAGMENT_TAG = "GUARDIANS_FRAGMENT_TAG";
    public final static String PSERONALINFO_FRAGMENT_TAG = "PSERONALINFO_FRAGMENT_TAG";
    public final static String RESET_PASSWD_FRAGMENT_TAG = "RESET_PASSWD_FRAGMENT_TAG";
    public final static String ORDER_CONFIRMATION_FRAGMENT_TAG = "ORDER_CONFIRMATION_FRAGMENT_TAG";

    public final static String MINE_FRAGMENT_INDEX_KEY = "MINE_FRAGMENT_INDEX_KEY";
    public final static String RECHARGE_MONE_KEY = "RECHARGE_MONE_KEY";
    public final static String RECHARGE_ACCOUNT_KEY = "RECHARGE_ACCOUNT_KEY";
    public final static String REAL_RECHARGE_ACCOUNT_KEY = "REAL_RECHARGE_ACCOUNT_KEY";

    public final static int PERSONINFO_PFRAGMENT_INDEX = 6;
    public final static int RESET_PASSWD_FRAGMENT_INDEX = 7;
    public final static int ORDER_CONFIRMATION_FRAGMENT_INDEX = 8;
    private int rechargeMoney = 0;
    private String rechargeAccount = "";
    private float realRechargeMoney = 0f;


    private int mineOfFragmentIndex = -1;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setContentView(R.layout.activity_function_feedback_layout);
        super.onCreate(bundle);
        changeStatusBarTextColor(true);
        initData();
        initView();
        initListener();
    }


    protected void initView() {
        showIntegralFragment(mineOfFragmentIndex);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MLog.d("PersonalInfoActivity onNewIntent()");
        initData();
        initView();
    }

    private void initData() {
        Intent mIntent = getIntent();
        if (null != mIntent) {
            mineOfFragmentIndex = mIntent.getIntExtra(MINE_FRAGMENT_INDEX_KEY, -1);
            rechargeMoney = mIntent.getIntExtra(RECHARGE_MONE_KEY, 0);
            rechargeAccount = mIntent.getStringExtra(RECHARGE_ACCOUNT_KEY);
            realRechargeMoney = mIntent.getFloatExtra(REAL_RECHARGE_ACCOUNT_KEY, 0);
        }
        MLog.d(" mineOfFragmentIndex  = " + mineOfFragmentIndex);
//        showIntegralFragment(mineOfFragmentIndex);
    }

    protected void initListener() {


    }

    @Override
    protected void handleNotEmptyBundle(Bundle bundle) {
        super.handleNotEmptyBundle(bundle);
        if (null != bundle) {
            FragmentManager manager = getSupportFragmentManager();//重新创建Manager，防止此对象为空
            manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);//弹出所有fragment
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AccountManagerActivity.this.finish();
    }

    private void showIntegralFragment(int showFragmentIndex) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        hideFragment(fragmentManager, fragmentTransaction);
        switch (showFragmentIndex) {
            case 0:
                break;

            case 1://积分兑换
                if (null == integralFragment) {
                    integralFragment = new IntegralFragment();
                    fragmentTransaction.add(R.id.framelayout, integralFragment, INTEGRAL_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(integralFragment);
                }
                break;

            case 2:
                break;

            case 3://账号余额
                if (null == accountRemainFragment) {
                    accountRemainFragment = new AccountRemainFragment();
                    fragmentTransaction.add(R.id.framelayout, accountRemainFragment, ACCOUNT_REMAIN_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(accountRemainFragment);
                }
                break;

            case 4:
                break;

            case 5://监护人创建
                if (null == guardiansFragment) {
                    guardiansFragment = new GuardiansFragment();
                    fragmentTransaction.add(R.id.framelayout, guardiansFragment, GUARDIANS_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(guardiansFragment);
                }
                break;

            case PERSONINFO_PFRAGMENT_INDEX://个人信息界面
                if (null == personalInfoFragment) {
                    personalInfoFragment = new PersonalInfoFragment();
                    fragmentTransaction.add(R.id.framelayout, personalInfoFragment, PSERONALINFO_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(personalInfoFragment);
                }
                break;

            case RESET_PASSWD_FRAGMENT_INDEX://修改密码
                if (null == resetPasswdFragment) {
                    resetPasswdFragment = new ResetPasswdFragment();
                    fragmentTransaction.add(R.id.framelayout, resetPasswdFragment, RESET_PASSWD_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(resetPasswdFragment);
                }
                break;

            case ORDER_CONFIRMATION_FRAGMENT_INDEX://账号充值
                if (null == orderConfirmationFragment) {
                    orderConfirmationFragment = new OrderConfirmationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(RECHARGE_MONE_KEY, rechargeMoney);
                    bundle.putString(RECHARGE_ACCOUNT_KEY,rechargeAccount);
                    bundle.putFloat(REAL_RECHARGE_ACCOUNT_KEY, realRechargeMoney);
                    orderConfirmationFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.framelayout, orderConfirmationFragment, ORDER_CONFIRMATION_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(orderConfirmationFragment);
                }
                break;
        }


        fragmentTransaction.commitAllowingStateLoss();
    }

    private void hideFragment(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction) {
        integralFragment = (IntegralFragment) fragmentManager.findFragmentByTag(INTEGRAL_FRAGMENT_TAG);
        accountRemainFragment = (AccountRemainFragment) fragmentManager.findFragmentByTag(ACCOUNT_REMAIN_FRAGMENT_TAG);
        guardiansFragment = (GuardiansFragment) fragmentManager.findFragmentByTag(GUARDIANS_FRAGMENT_TAG);
        personalInfoFragment = (PersonalInfoFragment) fragmentManager.findFragmentByTag(PSERONALINFO_FRAGMENT_TAG);
        resetPasswdFragment = (ResetPasswdFragment) fragmentManager.findFragmentByTag(RESET_PASSWD_FRAGMENT_TAG);

        //如果此fragment不为空的话就隐藏起来
        if (null != integralFragment) {
            fragmentTransaction.hide(integralFragment);
        }

        if (null != accountRemainFragment) {
            fragmentTransaction.hide(accountRemainFragment);
        }

        if (null != guardiansFragment) {
            fragmentTransaction.hide(guardiansFragment);
        }

        if (null != personalInfoFragment) {
            fragmentTransaction.hide(personalInfoFragment);
        }

        if (null != resetPasswdFragment) {
            fragmentTransaction.hide(resetPasswdFragment);
        }
    }

}
