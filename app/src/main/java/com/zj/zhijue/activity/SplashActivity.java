package com.zj.zhijue.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.util.ToastUtil;
import com.blankj.utilcode.util.ActivityUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.huige.library.utils.SharedPreferencesUtils;
import com.huige.library.utils.ToastUtils;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.login.PhoneLoginActivity;
import com.zj.zhijue.bean.MemberUpateEyeInfoResponseBean;
import com.zj.zhijue.bean.UseInfoBean;
import com.zj.zhijue.config.Constants;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.util.CommonUtils;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.UserInfoUtil;
import com.zj.zhijue.util.jsonutil.JsonUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.ResponseBody;

/**
 * Date:2020/6/29
 * Time:14:19
 * Desc:
 * Author:Sonne
 */
public class SplashActivity extends Activity {

    @BindView(R.id.ic_logo)
    ImageView icLogo;
    @BindView(R.id.banner_guide_content)
    BGABanner bannerGuideContent;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.jump_v)
    View jumpV;
    @BindView(R.id.tv_next_one)
    TextView tvNextOne;
    private boolean mIsNotFirst;
    /**
     * 最大用户等待毫秒数
     */
    private final int MAX_LOAD_MILLISECONDS = 5000;
    private final int MIN_LOAD_MILLISECONDS = 1000;
    private final int TIME_PERIOD = 500;
    private int currentLoadMill = 0;
    private boolean isLoadFinish;
    private List<DisposableObserver> disposableObserverList = new ArrayList<>();


    @SuppressLint({"CheckResult", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //解决Only fullscreen opaque activities can request orientation
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();
            Log.e("TAG", "onCreate fixOrientation when Oreo, result = " + result);
        }
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        StatusBarUtil.setTranslucent(this);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        // 判断是否从推送通知栏打开的
//        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
//        if (click != null) {
//            //从推送通知栏打开-Service打开Activity会重新执行Laucher流程
//            //查看是不是全新打开的面板
//            if (isTaskRoot()) {
//                return;
//            }
//            //如果有面板存在则关闭当前的面板
//            finish();
//            return;
//        }

//        mTvSkip = findViewById(R.id.tv_skip);
//        mTvSkip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityUtils.getInstance().jumpActivity(MainActivity.class);
//                finish();
//                mSubscribe.dispose();
//            }
//        });
        bannerGuideContent.setAutoPlayAble(false);
        mIsNotFirst = (boolean) SharedPreferencesUtils.get(Constants.APP_IS_FIRST, true);
        if (mIsNotFirst) {//首次
            framelayout.setBackgroundColor(Color.WHITE);
            doOnFirst();
        } else {//非首次
            doDelay();
            initUserData();
        }
    }

    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    private boolean fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo) field.get(this);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private void doOnFirst() {
        SharedPreferencesUtils.put(Constants.APP_IS_FIRST, false);
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(1080, 1920, 320, 640);
        // 设置数据源
        bannerGuideContent.setData(localImageSize, ImageView.ScaleType.FIT_XY,
                R.mipmap.gui1,
                R.mipmap.gui2,
                R.mipmap.gui3
        );

        bannerGuideContent.setEnterSkipViewIdAndDelegate(R.id.tv_next_one, R.id.jump_v, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                gotoLogin();
            }
        });
        //设置自动播放
//        bannerGuideContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                if (i == 2) {//最后一页
//                    new Handler().postDelayed(new Runnable() {
//                        public void run() {
//                            //execute the task
//                            gotoLogin();
//                        }
//                    }, 2000);
//
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });/
    }

    private void doDelay() {
        Observable.interval(TIME_PERIOD, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (!isFinishing()) {

                            currentLoadMill += TIME_PERIOD;
                            if (currentLoadMill >= MIN_LOAD_MILLISECONDS && currentLoadMill < MAX_LOAD_MILLISECONDS) {
                                //还在加载用户数据
                                if (isLoadFinish) {
                                    disposable.dispose();
                                    if (UserInfoUtil.needLogin()) {
                                        gotoLogin();
                                    } else {
                                        gotoHome();
                                    }
                                }

                            } else if (currentLoadMill >= MAX_LOAD_MILLISECONDS) {
                                disposable.dispose();
                                gotoLogin();
                            }

                        } else {
                            if (disposable != null) {
                                disposable.dispose();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void initUserData() {
        if (UserInfoUtil.needLogin()) {
            isLoadFinish = true;
            return;
        }
        isLoadFinish = false;
        getMemberInfo();
    }

    //获取/更新用户信息
    private void getMemberInfo() {
        if (!UserInfoUtil.checkLogin()) {
            return;
        }

        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("phone", UserInfoUtil.getPhone());
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
                UseInfoBean useInfo = (UseInfoBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<UseInfoBean>() {
                });
                if (useInfo != null) {
                    if (useInfo.getStatus().equals("success")) {
                        SharedPreferencesUtils.put(Constants.USER_INFO, new Gson().toJson(useInfo));
                    } else {
                        ToastUtils.showToast("token失效，请重新登录");
                        UserInfoUtil.cleanMember();
//                    EventBus.getDefault().post(new MainBtnChangeEvent(MainBtnChangeEvent.FragmentType.home));
                        gotoLogin();
                    }
                }
                isLoadFinish = true;
            }

            @Override
            public void onError(Throwable e) {
                disposableObserverList.remove(this);
                ToastUtil.showShort(e.getMessage());
                isLoadFinish = true;

            }

            @Override
            public void onComplete() {

            }
        };
        disposableObserverList.add(disposableObserver);
        TrainSuscribe.getUseInfo(headerMap, bodyMap, disposableObserver);

//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void gotoLogin() {
        UserInfoUtil.cleanMember();
        startActivity(new Intent(this, PhoneLoginActivity.class));
        finish();
    }

    private void gotoHome() {
        startActivity(new Intent(this, BleMainControlActivity.class));
        finish();
    }
}
