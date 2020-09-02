package com.zj.zhijue.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.util.ActivityStackUtil;
import com.android.common.baselibrary.util.DateUtil;
import com.zj.zhijue.util.AppUtils;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.register.RegisterByPhoneActivity;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.bean.MemberFindPasswdResponseBean;
import com.zj.zhijue.bean.VerficationCodeResponseBean;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IBaseRequest;
import com.android.common.baselibrary.util.ThreadPoolUtilsLocal;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.zj.zhijue.http.request.IMemberFindPasswd;
import com.zj.zhijue.http.request.ISendVerficationCode;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.util.view.ui.DeviceUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;



public class ForgetPasswdPasswdActivity extends BaseActivity {

    @BindView(R.id.register_titletv)
    AppCompatTextView findPasswdTextview;

    @BindView(R.id.backlayout)
    LinearLayout backLayout;

    @BindView(R.id.findpasswd_accountedittext)
    EditText accountEditText;

    @BindView(R.id.findpasswd_phoneedittext)
    EditText phoneEditText;

    @BindView(R.id.findpasswd_verifycode_edittext)
    EditText verifyCodeEditText;

    @BindView(R.id.findpasswd_verifycode_buttton)
    AppCompatButton sendVerifyCodeButton;

    @BindView(R.id.findpasswd_complete_button)
    Button completeButton;

    private static final int COUNTDOWN_MSG = 0;
    private static ScheduledExecutorService executorService;
    private static Integer totalCount = 60;
    private boolean fromFrogetPasswd = true;
    private String phoneNum = null;

    private VerficationCodeResponseBean mVerificationCodResponseBean = null;


    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String countText = getText(R.string.retry_send_verification_code_tip_text).toString();
            if (msg.what == COUNTDOWN_MSG) {
                int downcount = (int) msg.obj;
                if (downcount <= 0) {
                    shutdownExecutorService();
                    totalCount = 60;
                    sendVerifyCodeButton.setClickable(true);
                    sendVerifyCodeButton.setText(mResource.getString(R.string.get_verify_code_text));
                    sendVerifyCodeButton.setTextColor(getResources().getColor(R.color.bleglasses_register_hint_text_color));
                } else {
                    sendVerifyCodeButton.setClickable(false);
                    //timeText.setVisibility(View.VISIBLE);
                    sendVerifyCodeButton.setText(String.format(countText, downcount) + mResource.getString(R.string.after_time_get_again_text));
                    sendVerifyCodeButton.setTextColor(getResources().getColor(R.color.bleglasses_main_top_bg_color));
                }
            }

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setContentView(R.layout.activity_findpasswd_layout);
        super.onCreate(bundle);
        initView();
        initListener();
    }

    private void initView() {
        findPasswdTextview.setText("找回密码");

    }

    private void initListener() {
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sendVerifyCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean verifyPhone = verifyPhone();
                if (verifyPhone) {
                    sendVerifyCodeButton.setClickable(false);
                    sendVerifyCodeButton.setText("60s" + mResource.getString(R.string.after_time_get_again_text));
                    sendVerifyCodeButton.setTextColor(getResources().getColor(R.color.bleglasses_main_top_bg_color));
                    startTime();
                    requestVerificationCode(phoneNum = getPhoneText());
                } else {
                    ToastUtil.showShortToast("请输入格式正确的手机号码！");
                }
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyAllRight()) {
                    postRegisterInfoByFroget(getPhoneText(), getVerifyCode());
                } else {
                    ToastUtil.showShortToast("信息填写不正确！");
                }
            }
        });
    }

    private String getAccount() {
        return accountEditText.getText().toString();
    }

    private String getPhoneText() {
        return phoneEditText.getText().toString();
    }


    private String getVerifyCode() {
        return verifyCodeEditText.getText().toString();
    }



    private boolean verifyAccount() {
        String account = getAccount();
        if (!CommonUtils.isEmpty(account)) {
            return true;
        }
        return false;
    }

    private boolean verifyPhone() {
        String phone = getPhoneText();
        if (!CommonUtils.isEmpty(phone)) {
            if (phone.trim().length() == 11) {
                return true;
            }
        }
        return false;
    }

    private boolean verifySmsCode() {
        String verifyCode = getVerifyCode();
        if (!CommonUtils.isEmpty(verifyCode)) {
            if (null != mVerificationCodResponseBean
                    && null != mVerificationCodResponseBean.getData()) {
                String serverCode = mVerificationCodResponseBean.getData().getVerficationCode();
                if (!CommonUtils.isEmpty(serverCode)
                        && serverCode.equals(verifyCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verifyAllRight() {
        //boolean verifyAccount = verifyAccount();
        boolean verifyPhone = verifyPhone();
        boolean verifyCode = verifySmsCode();
        //boolean verifyPasswd = verifyPasswd();
        if (verifyPhone
                && verifyCode) {
            return true;
        }
        return false;
    }

    private void shutdownExecutorService() {
        if (null != executorService) {
            executorService.shutdown();
            executorService = null;
        }
        totalCount = 60;
        sendVerifyCodeButton.setClickable(true);
    }


    private void requestVerificationCode(final String phoneNum) {
        if (!checkNetworkAvaliable()) {
            return;
        }

        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put(ISendVerficationCode.PHONENUM, phoneNum);
        bodyMap.put(ISendVerficationCode.IMEI, DeviceUtils.getIMEI());
        bodyMap.put(ISendVerficationCode.PLATFORM, String.valueOf(ISendVerficationCode.PLATFORM_VALUE_ANDROID));
        bodyMap.put(ISendVerficationCode.UTDID, AppUtils.getUtdId());

        if (fromFrogetPasswd) {
            bodyMap.put(ISendVerficationCode.SMSTYPE, ISendVerficationCode.SMSTYPE_PASSWORD);
        } else {
            bodyMap.put(ISendVerficationCode.SMSTYPE, ISendVerficationCode.SMSTYPE_REGISTER);
        }
        bodyMap.put(ISendVerficationCode.SENDTIME, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
        mVerificationCodResponseBean = null;
        LoginSubscribe.postRegisterWithVerficationCode(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String jsonBody = null;
                try {
                    jsonBody = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mVerificationCodResponseBean = (VerficationCodeResponseBean) JsonUtil.json2objectWithDataCheck(jsonBody, new TypeReference<VerficationCodeResponseBean>() {
                });

                if (null != mVerificationCodResponseBean) {

                    if (mVerificationCodResponseBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                        VerficationCodeResponseBean.DataBean dataBean = mVerificationCodResponseBean.getData();
                        if (RegisterByPhoneActivity.autoInputSms && null != dataBean) {
                            if (null != verifyCodeEditText) {
                                verifyCodeEditText.setText(dataBean.getVerficationCode());
                            }
                        }
                        ToastUtil.showLong(R.string.verify_code_send_success_textt);
                    } else {
                        ToastUtil.showShortToast(mVerificationCodResponseBean.getMessage());
                    }
                } else {
                    ToastUtil.showShort(R.string.net_error_textt);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(R.string.net_error_textt);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void postRegisterInfoByFroget(String phoneNum, String verificationCode) {
        if (!checkNetworkAvaliable()) {
            return;
        }

        final HashMap<String, String> bodyMap = new HashMap<>();

        //bodyMap.put(IMemberFindPasswd.ACCOUNT, account);
        bodyMap.put(IMemberFindPasswd.PHONENUM, phoneNum);
        bodyMap.put(IMemberFindPasswd.VERFICATIONCODE, verificationCode);
        bodyMap.put(IMemberFindPasswd.IMEI, DeviceUtils.getIMEI());
        bodyMap.put(IMemberFindPasswd.PLATFORM, String.valueOf(ISendVerficationCode.PLATFORM_VALUE_ANDROID));

        HashMap<String, String> headerMap = new HashMap<>();

        LoginSubscribe.findPasswd(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {

                String jsonBody = null;
                try {
                    jsonBody = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MemberFindPasswdResponseBean mMemberFindPaswdResponseBean = (MemberFindPasswdResponseBean) JsonUtil.json2objectWithDataCheck(jsonBody, new TypeReference<MemberFindPasswdResponseBean>() {
                });

                jumpNewActivityByFindPasswd(mMemberFindPaswdResponseBean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void startTime() {

        if (null == executorService || executorService.isShutdown() || executorService.isTerminated()) {
            executorService = ThreadPoolUtilsLocal.newScheduledThreadPool();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Message message = mHanlder.obtainMessage();
                    message.what = COUNTDOWN_MSG;
                    message.obj = totalCount;
                    message.sendToTarget();
                    totalCount--;
                }
            };
            executorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
        }
    }

    @Override
    protected void onDestroy() {
        shutdownExecutorService();
        if (null != mHanlder) {
            mHanlder.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    private void jumpNewActivityByFindPasswd(MemberFindPasswdResponseBean memberFindPasswdResponseBean) {
        if (null != memberFindPasswdResponseBean) {
            if (memberFindPasswdResponseBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                ToastUtil.showLong(R.string.verify_success_by_find_passwd_text);
                ActivityStackUtil.getInstance().finishAllActivity();
                Intent mIntent = new Intent(ForgetPasswdPasswdActivity.this, PhoneLoginActivity.class);
                startActivity(mIntent);
            } else {
                ToastUtil.showShort(memberFindPasswdResponseBean.getMessage());
            }
        } else {
            ToastUtil.showShort(R.string.net_error_textt);
        }
    }


    private void insertUserInfo2DB(String phoneNum, String userServerId) {
        if (!CommonUtils.isEmpty(userServerId)) {
            List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(this, userServerId);
            UserInfoDBBean userInfoDBBean = null;
            if (null != userInfoDBBeanList && userInfoDBBeanList.size() > 0) {
                userInfoDBBean = userInfoDBBeanList.get(0);
            }

            if (null != userInfoDBBean) {
                userInfoDBBean.setPhone(phoneNum);
                UserInfoBeanDaoOpe.insertOrReplaceData(MyApplication.getInstance(), userInfoDBBean);
            } else {
                userInfoDBBean = new UserInfoDBBean();
                userInfoDBBean.setLocalid(UUID.randomUUID().toString());
                userInfoDBBean.setPhone(phoneNum);
                userInfoDBBean.setServerId(userServerId);
                UserInfoBeanDaoOpe.insertData(MyApplication.getInstance(), userInfoDBBean);
            }
        }
    }

}
