package com.zj.zhijue.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.huige.library.utils.SharedPreferencesUtils;
import com.huige.library.utils.ToastUtils;
import com.litesuits.android.log.Log;
import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.BuildConfig;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.bindglasses.BindPersonalInfoActivity;
import com.zj.zhijue.activity.mine.AccountManagerActivity;
import com.zj.zhijue.activity.mine.MsgActivity;
import com.zj.zhijue.activity.mine.PersonalInfoActivity;
import com.zj.zhijue.activity.mine.RechargeTimeActivity;
import com.zj.zhijue.activity.mine.TimeRecordActivity;
import com.zj.zhijue.activity.mine.WebActivity;
import com.zj.zhijue.adapter.AccountManagerRecyclerViewAdapter;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.AccountManagerBean;
import com.zj.zhijue.bean.SignInResponseBean;
import com.zj.zhijue.bean.UseInfoBean;
import com.zj.zhijue.bean.event.PortraitUpdateEventBean;
import com.zj.zhijue.config.Constants;
import com.zj.zhijue.event.CallbackDataEvent;
import com.zj.zhijue.event.EventConstant;
import com.zj.zhijue.event.PayStateEventBean;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IAddSignIn;
import com.zj.zhijue.listener.OnItemClickListener;
import com.zj.zhijue.pay.PaymentUtil;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.DateUtil;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.zj.zhijue.view.circleimageview.CircleImageView;
import com.zj.zhijue.view.grideviewsection.PullToRefreshView;
import com.zj.zhijue.view.recyclerview.GloriousRecyclerView;
import com.zj.zhijue.view.recyclerview.GridItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;


public class MineFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,
        PullToRefreshView.OnFooterRefreshListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = MineFragment.class.getName();

    @BindView(R.id.person_portrait_image)
    CircleImageView portraitImageView;
    @BindView(R.id.nametextview)
    AppCompatTextView userNameTextView;
    //    @BindView(R.id.signinlayout)
//    LinearLayout signInLayout;
    @BindView(R.id.accountmanagerrecyclerview)
    GloriousRecyclerView gloriousRecyclerView;
    @BindView(R.id.ivMessage)
    ImageView ivMessage;
    @BindView(R.id.ivSetting)
    ImageView ivSetting;
    @BindView(R.id.tv_remain_time)
    TextView tvRemainTime;
    @BindView(R.id.tv_recharge_time)
    TextView tvRechargeTime;
    @BindView(R.id.iv_bargain)
    ImageView ivBargain;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private boolean isRefreshing;

    AccountManagerRecyclerViewAdapter accountManagerRecyclerViewAdapter;
    List<AccountManagerBean> accountManagerBeanList = new ArrayList<>();

    public static final int REVIEW_DATA_INDEX = 0;//复查数据

    public static final int PRODUCT_INTRODUCE_INDEX = 1;//产品介绍
    public static final int INTERVENE_KEY_INTRODUCE_INDEX = 2;//干预键介绍
    public static final int TEAM_INDEX = 3;//个人团队
    public static final int FEEDBACK_INDEX = 4;//意见反馈
    public static final int COMMON_PROBLEM_INDEX = 5;//常见问题
    public static final int CONTACT_CUSTOMER_SERVICE_INDEX = 6;//联系客服
    public static final int SYSTEM_SETTINGS_KEY = 18;
    public static final int BLUETOOTHLOG_INDEX = 7;
    public static final int MANAGER_INPUT_DATA_SETTINGS_KEY = 17;//录入数据

    public static final int INPUT_REVIEW_DATA_INDEX = 8;//录入复查数据
    public static final int INPUT_OTHER_DATA_INDEX = 9;//录入其他数据

    public static final int GUARDIAN_ACCOUNT_INDEX = 10;


    public static final int VERSION_INFO_INDEX = 11;
    public static final int PERSONAL_INFO_INDEX = 12;
    //public static final int AWARD_INFO_INDEX = 13;
    public static final int OPTOMETRY_INFO_INDEX = 14;//验光信息
    public static final int USER_AGREEMENT_INDEX = 15;//用户协议
    public static final int CUSTOM_DATA_INDEX = 16;//数据定制

    private List<DisposableObserver> disposableObserverList = new ArrayList<>();
    private UseInfoBean.UseBean useInfoBean;

    private UserInfoDBBean userInfoDBBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MLog.d("MineFragment onActivityCreated()");
        initListener();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MLog.d(TAG + " onActivityResult() requestCode = " + requestCode + " resultCode = " + resultCode);
    }

    /**
     * 处理更新头像的操作
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleUpdatePortraitImage(PortraitUpdateEventBean portraitUpdateEventBean) {
        Glide.with(this).load(com.zj.zhijue.util.CommonUtils.diffAvatar(URLConstant.BASE_URL_FIRST_PARTY, portraitUpdateEventBean.newImageUrl)).apply(new RequestOptions().placeholder(R.mipmap.qidongtubiao).error(R.mipmap.qidongtubiao)).into(portraitImageView);

    }

    /**
     * 处理支付成功后的回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payStateChange(PayStateEventBean payStateEventBean) {
        if (payStateEventBean.payState == PaymentUtil.PAYMENT_SUCCESS) {
            ToastUtil.showShort(getString(R.string.string_paysuccess));
        }

        onRefresh();

    }


    @Override
    public void onRefresh() {
        getUseInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
//        getUseInfo(userInfoDBBean.getPhone());
    }

    private void initData() {
        Context context = getActivity();

        if (null == context) {
            return;
        }
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this);

        String userServerId = Config.getConfig().getUserServerId();
        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(context.getApplicationContext(), userServerId);
        if (null != userInfoDBBeanList && userInfoDBBeanList.size() > 0) {
            userInfoDBBean = userInfoDBBeanList.get(0);
        }
        getUseInfo();
        //initRecyclerViewData();
        initFunctionRecyclerViewItem();
    }

    private void updateUI(UseInfoBean.UseBean useBean) {
        if (useBean == null) {
            String portraitUrl = userInfoDBBean.getPortrait_image_url();
            if (!CommonUtils.isEmpty(portraitUrl)) {
                String loadUrl = CommonUtils.startWithHttp(portraitUrl) ? portraitUrl : URLConstant.BASE_URL + portraitUrl;
                MLog.d("portraiturl = " + loadUrl);
                Glide.with(this).load(com.zj.zhijue.util.CommonUtils.diffAvatar(URLConstant.BASE_URL_FIRST_PARTY, loadUrl)).apply(new RequestOptions().placeholder(R.mipmap.qidongtubiao).error(R.mipmap.qidongtubiao)).into(portraitImageView);
            }
            userNameTextView.setText(userInfoDBBean.getPhone());
        } else {
            Glide.with(this)
                    .load(com.zj.zhijue.util.CommonUtils.diffAvatar(URLConstant.BASE_URL_FIRST_PARTY, useBean.getFace()))
                    .apply(new RequestOptions().placeholder(R.mipmap.qidongtubiao).error(R.mipmap.qidongtubiao))
                    .into(portraitImageView);
            userNameTextView.setText(useBean.getPhone());
            if (useBean.getTotalTime() != null && useBean.getUsedTime() != null) {
                tvRemainTime.setText("剩余时长：" + DateUtil.secondToTime(Long.parseLong(useBean.getTotalTime()) - Long.parseLong(useBean.getUsedTime())));
            } else if (useBean.getTotalTime() != null && useBean.getUsedTime() == null) {
                tvRemainTime.setText("剩余时长：" + DateUtil.secondToTime(Long.parseLong(useBean.getTotalTime())));
            } else {
                tvRemainTime.setText("剩余时长：0 小时");
            }
        }

    }

    private void getUseInfo() {
        String serverId = Config.getConfig().getUserServerId();

        swipeRefresh.setRefreshing(true);
        isRefreshing = true;
        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
//        bodyMap.put("phone", phone);

        bodyMap.put("id", serverId);
        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                disposableObserverList.remove(this);
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MLog.e("=============" + newStringDataJson);

                UseInfoBean useInfo = (UseInfoBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<UseInfoBean>() {
                });
                if (swipeRefresh != null) {
                    if (useInfo != null) {
                        if (useInfo.getStatus().equals("success")) {
                            SharedPreferencesUtils.put(Constants.USER_INFO, new Gson().toJson(useInfo));
                            useInfoBean = useInfo.getData();
                            updateUI(useInfoBean);
                        } else {
//                            disposeFlag(listBaseEntity.getCode(), listBaseEntity.getMsg());
                            ToastUtil.showShort(useInfo.getMessage());
                        }
                    }
                    if (null != swipeRefresh) {
                        swipeRefresh.setRefreshing(false);
                        isRefreshing = false;
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (swipeRefresh != null) {
                    disposableObserverList.remove(this);
                    ToastUtil.showShort(e.getMessage());
                    swipeRefresh.setRefreshing(false);
                    isRefreshing = false;
                }

            }

            @Override
            public void onComplete() {

            }
        };
        disposableObserverList.add(disposableObserver);
        TrainSuscribe.getPersonalInfo(headerMap, bodyMap, disposableObserver);
    }

    private void initFunctionRecyclerViewItem() {
        accountManagerBeanList.clear();

        AccountManagerBean visionDataBean = new AccountManagerBean();
        visionDataBean.setPrefixImageResourceId(R.mipmap.ic_xitongshezhi);
        visionDataBean.setItemTitle("视力信息");
        visionDataBean.setLight(true);
        visionDataBean.setSufixImageResourceId(R.mipmap.jiantou);

        AccountManagerBean reviewDataBean = new AccountManagerBean();
        reviewDataBean.setPrefixImageResourceId(R.mipmap.ic_fucha_shuju);
        reviewDataBean.setItemTitle("视力复查");
        reviewDataBean.setLight(true);
        reviewDataBean.setSufixImageResourceId(R.mipmap.jiantou);

        AccountManagerBean productDataBean = new AccountManagerBean();
        productDataBean.setPrefixImageResourceId(R.mipmap.ic_product_introduce);
        productDataBean.setItemTitle("产品介绍");
        productDataBean.setLight(true);
        productDataBean.setSufixImageResourceId(R.mipmap.jiantou);

        AccountManagerBean ganYuJianDataBean = new AccountManagerBean();
        ganYuJianDataBean.setPrefixImageResourceId(R.mipmap.ic_ganyu_five_hands);
        ganYuJianDataBean.setLight(true);
        ganYuJianDataBean.setItemTitle("干预键介绍");
        ganYuJianDataBean.setSufixImageResourceId(R.mipmap.jiantou);

        AccountManagerBean teamDataBean = new AccountManagerBean();
        teamDataBean.setPrefixImageResourceId(R.mipmap.ic_mine_team);
        teamDataBean.setLight(true);
        teamDataBean.setItemTitle("个人团队");
        teamDataBean.setSufixImageResourceId(R.mipmap.jiantou);

        AccountManagerBean sugestionDataBean = new AccountManagerBean();
        sugestionDataBean.setPrefixImageResourceId(R.mipmap.ic_feekback_yijian);
        sugestionDataBean.setLight(true);
        sugestionDataBean.setItemTitle("意见反馈");
        sugestionDataBean.setSufixImageResourceId(R.mipmap.jiantou);

        AccountManagerBean commonProblemsDataBean = new AccountManagerBean();
        commonProblemsDataBean.setPrefixImageResourceId(R.mipmap.ic_common_problems);
        commonProblemsDataBean.setLight(true);
        commonProblemsDataBean.setItemTitle("常见问题");
        commonProblemsDataBean.setSufixImageResourceId(R.mipmap.jiantou);

        AccountManagerBean contactServiceDataBean = new AccountManagerBean();
        contactServiceDataBean.setPrefixImageResourceId(R.mipmap.ic_contact_service);
        contactServiceDataBean.setItemTitle("联系客服");
        contactServiceDataBean.setLight(true);
        contactServiceDataBean.setSufixImageResourceId(R.mipmap.jiantou);


        AccountManagerBean systemSettingsDataBean = new AccountManagerBean();
        systemSettingsDataBean.setPrefixImageResourceId(R.mipmap.ic_xitongshezhi);
        systemSettingsDataBean.setItemTitle("系统设置");
        systemSettingsDataBean.setLight(true);
        systemSettingsDataBean.setSufixImageResourceId(R.mipmap.jiantou);

        if (BuildConfig.DEBUG) {
            AccountManagerBean bluetoothLog = new AccountManagerBean();
            systemSettingsDataBean.setPrefixImageResourceId(R.mipmap.ic_xitongshezhi);
            systemSettingsDataBean.setItemTitle("蓝牙日志");
            systemSettingsDataBean.setLight(true);
            systemSettingsDataBean.setSufixImageResourceId(R.mipmap.jiantou);
        }

        accountManagerBeanList.add(visionDataBean);
        accountManagerBeanList.add(reviewDataBean);
        accountManagerBeanList.add(productDataBean);
        accountManagerBeanList.add(ganYuJianDataBean);
        accountManagerBeanList.add(teamDataBean);
        accountManagerBeanList.add(sugestionDataBean);
        accountManagerBeanList.add(commonProblemsDataBean);
        accountManagerBeanList.add(contactServiceDataBean);
        accountManagerBeanList.add(systemSettingsDataBean);

       /* AccountManagerBean reviewInputDataBean = new AccountManagerBean();
        reviewInputDataBean.setPrefixImageResourceId(R.mipmap-xhdpi.fucha_shuju);
        reviewInputDataBean.setItemTitle(getResources().getString(R.string.input_data_text));
        reviewInputDataBean.setLight(true);
        reviewInputDataBean.setSufixImageResourceId(R.mipmap-xhdpi.jiantou);
        accountManagerBeanList.add(reviewInputDataBean);*/

    }

    private void initView(View view) {
        //砍价h5
        ivBargain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("phone", userInfoDBBean.getPhone());
                intent.putExtra("url", "http://zj.zhuojiacn.net/zjwap/pages/index/haggle?phone=" + userInfoDBBean.getPhone());
                startActivity(intent);
            }
        });
        //时长记录
        tvRemainTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TimeRecordActivity.class));
            }
        });
        //充值时长
        tvRechargeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RechargeTimeActivity.class);
                startActivity(intent);
            }
        });
        //消息
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MsgActivity.class));
            }
        });
        //设置
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(getActivity(), PersonalInfoActivity.class);
                mIntent.putExtra(PersonalInfoActivity.FRAGMENT_INDEX_KEY, SYSTEM_SETTINGS_KEY);
                startActivity(mIntent);
            }
        });

        //MLog.d("userInfoDBBean = " + userInfoDBBean);
//        String portraitUrl = userInfoDBBean.getPortrait_image_url();
//        MLog.d("portraiturl0 = " + portraitUrl);
//        if (!CommonUtils.isEmpty(portraitUrl)) {
//            String loadUrl = CommonUtils.startWithHttp(portraitUrl) ? portraitUrl : URLConstant.BASE_URL + portraitUrl;
//            MLog.d("portraiturl1 = " + loadUrl);
//            Glide.with(this).load(loadUrl).into(portraitImageView);
//        }
//        userNameTextView.setText(userInfoDBBean.getName());
        initRecyclerView();
    }

    private void initRecyclerView() {
        Activity activity = getActivity();
        if (null == activity) {
            return;
        }

        final GridLayoutManager manager = new GridLayoutManager(activity, 1);

        accountManagerRecyclerViewAdapter = new AccountManagerRecyclerViewAdapter(this);
        gloriousRecyclerView.setAdapter(accountManagerRecyclerViewAdapter);

        GridItemDecoration gridItemDecoration = new GridItemDecoration.Builder(activity)
                .size((int) DeviceUtils.dipToPx(activity, 0.5f))
                .color(R.color.bleglasses_mine_item_divide_color)
                .margin((int) DeviceUtils.dipToPx(activity, 15), (int) DeviceUtils.dipToPx(activity, 15))
                .isExistHead(false)
                .showHeadDivider(false)
                .showLastDivider(false)
                .build();

        gloriousRecyclerView.addItemDecoration(gridItemDecoration);
        gloriousRecyclerView.setLayoutManager(manager);
        accountManagerRecyclerViewAdapter.setDatas(accountManagerBeanList);
    }

    @Override
    protected void initListener() {
        super.initListener();

 /*       mAccountLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到个人账户信息界面
                //ToastDialogUtil.showShortToast("账户信息");
                Intent mIntent = new Intent(getActivity(), PersonalInfoActivity.class);
                mIntent.putExtra(PersonalInfoActivity.FRAGMENT_INDEX_KEY, MineFragment.PERSONAL_INFO_INDEX);
                startActivity(mIntent);
            }
        });*/

        portraitImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initConfig(true);
                goPersonInfo();

            }
        });

//        signInLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /**
//                 * 签到
//                 */
//                String serverId = Config.getConfig().getUserServerId();
//                if (!CommonUtils.isEmpty(serverId)) {
//                    postSignInInfo(serverId, null, null, null);
//                } else {
//                    //**
//                    ToastUtil.showShortToast("退出重新登录");
//                    ActivityStackUtil.getInstance().finishAllActivity();
//                    Intent mIntent = new Intent(getActivity(), PhoneLoginActivity.class);
//                    startActivity(mIntent);
//                }
//            }
//        });

        accountManagerRecyclerViewAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                //添加一个视力信息 position = 0 ，为不影响以前逻辑-1
                if (position == 0) {
                    //视力信息
                    Intent mIntent = new Intent(getActivity(), BindPersonalInfoActivity.class);
                    mIntent.putExtra("useInfoBean", useInfoBean);
                    startActivity(mIntent);
                    return;
                }

                position--;

                AccountManagerBean accountManagerBean = accountManagerBeanList.get(position);
                if (accountManagerBean.isLight()) {
                   /* Intent mIntent = new Intent(getActivity(), AccountManagerActivity.class);
                    mIntent.putExtra(AccountManagerActivity.MINE_FRAGMENT_INDEX_KEY, position);
                    startActivity(mIntent);*/

                    Intent mIntent = new Intent(getActivity(), PersonalInfoActivity.class);
                    mIntent.putExtra(PersonalInfoActivity.FRAGMENT_INDEX_KEY, position);
                    startActivity(mIntent);
                }
            }
        });
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        ToastUtil.showShort("onFooterRefresh");
        //mPullToRefreshView.onFooterRefreshComplete();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        ToastUtil.showShort("onHeaderRefresh");
        //mPullToRefreshView.onHeaderRefreshComplete();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void goPersonInfo() {
        Intent mIntent = new Intent(getActivity(), AccountManagerActivity.class);
        mIntent.putExtra(AccountManagerActivity.MINE_FRAGMENT_INDEX_KEY, AccountManagerActivity.PERSONINFO_PFRAGMENT_INDEX);
        startActivity(mIntent);
    }

    private void postSignInInfo(String memberId, String loginName, String nickName, String name) {
        /**
         * member_id	是	string	用户编号
         login_name	否	string	姓名
         nick_name	否	string	昵称
         name	否	string	真实姓名
         */

        final HashMap<String, String> headerMap = new HashMap<>(1);
        final HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put(IAddSignIn.MEMBER_ID, memberId);
        bodyMap.put(IAddSignIn.LOGIN_NAME, loginName);
        bodyMap.put(IAddSignIn.NICK_NAME, nickName);
        bodyMap.put(IAddSignIn.NAME, name);
        //bodyMap.put(IMemberTrainTime.VERSION_CODE, String.valueOf(AppUtils.getVersionCode()));

        LoginSubscribe.postSignInInfo(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //SdLogUtil.writeCommonLog("postSignInInfo newStringDataJson= " + newStringDataJson);
                SignInResponseBean signInResponseBean = (SignInResponseBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<SignInResponseBean>() {
                });
                //SdLogUtil.writeCommonLog("signInResponseBean = " + signInResponseBean);
                if (null != signInResponseBean && null != signInResponseBean.getData()) {
                    ToastUtil.showShortToast("" + signInResponseBean.getData().getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShortToast(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

}
