package com.zj.zhijue.activity.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;

import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.jnative.SecUtil;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.DateUtil;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.bindglasses.BindPersonalInfoActivity;
import com.zj.zhijue.activity.login.PhoneLoginActivity;
import com.zj.zhijue.activity.mine.PersonalInfoActivity;
import com.zj.zhijue.base.BaseBleActivity;
import com.zj.zhijue.bean.MemberRegisterResponseBean;
import com.zj.zhijue.bean.VerficationCodeResponseBean;
import com.zj.zhijue.fragment.MineFragment;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.android.common.baselibrary.util.ThreadPoolUtilsLocal;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.android.common.baselibrary.util.comutil.security.Md5Util;
import com.zj.zhijue.dialog.register.RegisterTipDialog;
import com.zj.zhijue.http.request.IMemberRegister;
import com.zj.zhijue.http.request.ISendVerficationCode;
import com.zj.zhijue.util.Config;
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


public class RegisterByPhoneActivity extends BaseBleActivity {

    @BindView(R.id.backlayout)
    LinearLayout backLayout;

    @BindView(R.id.register_phoneedittext)
    EditText phoneEditText;

    @BindView(R.id.register_verifycode_edittext)
    EditText verifyCodeEditText;

    @BindView(R.id.register_verifycode_buttton)
    AppCompatButton sendVerifyCodeButton;

    @BindView(R.id.register_first_passwd_edittext)
    EditText firstPasswdEditText;

    @BindView(R.id.passwd_visiablebtn)
    AppCompatCheckBox passWorkVisiableCheckBox;

    @BindView(R.id.register_second_passwd_edittext)
    EditText secondPasswdEditText;

    @BindView(R.id.grantedcheckbox)
    AppCompatCheckBox grantedCheckBox;

    @BindView(R.id.login_protocoltext)
    AppCompatTextView userProtocolTextView;

    @BindView(R.id.edRecommender)
    EditText edRecommender;

    @BindView(R.id.register_complete_button)
    Button completeButton;

    private static final int COUNTDOWN_MSG = 0;
    private static ScheduledExecutorService executorService;
    private static Integer totalCount = 60;
    private boolean fromFrogetPasswd = false;
    public static boolean autoInputSms = true;//接口收到验证码，自动填充
    private String phoneNum = null;

    private VerficationCodeResponseBean mVerificationCodResponseBean = null;
    private MemberRegisterResponseBean mMemberRegisterResponseBean = null;

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

    private RegisterTipDialog registerTipDialog;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setContentView(R.layout.activity_registerbyphone);
        super.onCreate(bundle);
        initView();

        initListener();
    }

    private void initView() {
        toProtocol();

       /* Drawable up = ContextCompat.getDrawable(RegisterByPhoneActivity.this, R.mipmap-xhdpi.weixuanzhong);
        Drawable drawableUp = DrawableCompat.wrap(up);
        DrawableCompat.setTint(drawableUp, ContextCompat.getColor(RegisterByPhoneActivity.this, R.color.bleglasses_register_hint_text_color));
        grantedCheckBox.setButtonDrawable(up);*/
    }

    private void initListener() {
        passWorkVisiableCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    passWorkVisiableCheckBox.setButtonDrawable(R.mipmap.xianshi);
                    firstPasswdEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);// 输入为密码且可见
                } else {
                    passWorkVisiableCheckBox.setButtonDrawable(R.mipmap.buxianshi);
                    firstPasswdEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);// 设置文本类密码（默认不可见），这两个属性必须同时设置
                }
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

      /*  grantedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Drawable up = ContextCompat.getDrawable(RegisterByPhoneActivity.this,R.mipmap-xhdpi.xuanzhong);
                if (!b) {
                    Drawable drawableUp= DrawableCompat.wrap(up);
                    DrawableCompat.setTint(drawableUp, ContextCompat.getColor(RegisterByPhoneActivity.this, R.color.bleglasses_register_hint_text_color));
                } else {
                    Drawable drawableUp= DrawableCompat.wrap(up);
                    DrawableCompat.setTint(drawableUp, ContextCompat.getColor(RegisterByPhoneActivity.this, R.color.bleglasses_main_top_bg_color));
                }
                grantedCheckBox.setButtonDrawable(up);
            }
        });*/

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean granted = grantedCheckBox.isChecked();
                if (granted) {
                    if (verifyAllRight()) {
                        postRegisterInfo(getPhoneText(), getVerifyCode(), getFirstPassword(), edRecommender.getText().toString());
                    } else {
                        ToastUtil.showShortToast("填写的注册信息不正确！");
                    }
                } else {
                    ToastUtil.showShortToast("请同意注册协议");
                }
            }
        });

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String getPhoneText() {
        return phoneEditText.getText().toString();
    }

    private String getFirstPassword() {
        return firstPasswdEditText.getText().toString();
    }

    private String getSecondPasswd() {
        return secondPasswdEditText.getText().toString();
    }

    private String getVerifyCode() {
        return verifyCodeEditText.getText().toString();
    }

    private boolean verifyPasswd() {
        String firstPassword = getFirstPassword();
        String secondPasswd = getSecondPasswd();

        if (!CommonUtils.isEmpty(firstPassword)
                && !CommonUtils.isEmpty(secondPasswd)
                && firstPassword.equals(secondPasswd)) {
            return CommonUtils.verityPwd(firstPassword);
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
        boolean verifyPhone = verifyPhone();
        boolean verifyCode = true;//verifySmsCode();
        boolean verifyPasswd = verifyPasswd();
        if (verifyPhone && verifyCode && verifyPasswd) {
            return true;
        }
        return false;
    }

    private void toProtocol() {
        userProtocolTextView.setHighlightColor(Color.parseColor("#00ffffff"));
        String protocolText = userProtocolTextView.getText().toString();
        SpannableStringBuilder builder = new SpannableStringBuilder(protocolText);
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(mResource.getColor(R.color.bleglasses_main_top_bg_color));
        int leftIndex = protocolText.indexOf("《");
        builder.setSpan(blueSpan, leftIndex, protocolText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //字体颜色


        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                //ToastUtil.showShortToast("同意协议");
                Intent mIntent = new Intent(RegisterByPhoneActivity.this, PersonalInfoActivity.class);
                mIntent.putExtra(PersonalInfoActivity.FRAGMENT_INDEX_KEY, MineFragment.USER_AGREEMENT_INDEX);
                startActivity(mIntent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
                ds.setColor(mResource.getColor(R.color.bleglasses_main_top_bg_color));
                ds.clearShadowLayer();
            }
        };

        builder.setSpan(clickableSpan, leftIndex, protocolText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        userProtocolTextView.setMovementMethod(LinkMovementMethod.getInstance());
        userProtocolTextView.setText(builder);

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
        //bodyMap.put(ISendVerficationCode.VERSION_CODE, AppUtils.getVersionCode2Str());
        bodyMap.put(ISendVerficationCode.PHONENUM, phoneNum);
        bodyMap.put(ISendVerficationCode.IMEI, DeviceUtils.getIMEI());
        bodyMap.put(ISendVerficationCode.PLATFORM, String.valueOf(ISendVerficationCode.PLATFORM_VALUE_ANDROID));
        bodyMap.put(ISendVerficationCode.SENDTIME, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));

        if (fromFrogetPasswd) {
            bodyMap.put(ISendVerficationCode.SMSTYPE, ISendVerficationCode.SMSTYPE_PASSWORD);
        } else {
            bodyMap.put(ISendVerficationCode.SMSTYPE, ISendVerficationCode.SMSTYPE_REGISTER);
        }
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
                //SdLogUtil.writeCommonLog(" onNext responseBody = " + jsonBody);
                mVerificationCodResponseBean = (VerficationCodeResponseBean) JsonUtil.json2objectWithDataCheck(jsonBody, new TypeReference<VerficationCodeResponseBean>() {
                });
                //SdLogUtil.writeCommonLog(" onNext verficationCodeResponseBean = " + mVerificationCodResponseBean);
                MLog.d(" onNext verficationCodeResponseBean = " + mVerificationCodResponseBean);
                if (null != mVerificationCodResponseBean) {
                    VerficationCodeResponseBean.DataBean mDataBean = mVerificationCodResponseBean.getData();
                    if (null != mDataBean) {
                        ToastUtil.showLongToast("验证码：" + mDataBean.getVerficationCode());
                        if (autoInputSms) {
                            if (null != verifyCodeEditText) {
                                verifyCodeEditText.setText(mDataBean.getVerficationCode());
                            }
                        }
                        ToastUtil.showShortToast(mVerificationCodResponseBean.getStatus());
                    } else {
                        ToastUtil.showShortToast("网络出错，未收到验证码");
                    }
                } else {

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
        dimissRegisterDialog();
        super.onDestroy();
    }


    private void postRegisterInfo(final String phoneNum, String verificationCode, final String passwd, String recommender) {
        if (!checkNetworkAvaliable()) {
            return;
        }
        final HashMap<String, String> bodyMap = new HashMap<>();

        bodyMap.put(IMemberRegister.PHONENUM, phoneNum);
        //bodyMap.put(IMemberRegister.VERSION_CODE, AppUtils.getVersionCode2Str());
//        bodyMap.put(IMemberRegister.PASSWORD, Md5Util.getMD5String(SecUtil.getRSAsinatureWithLogin(passwd)));
        bodyMap.put(IMemberRegister.PASSWORD, passwd);
        if (!recommender.isEmpty()) bodyMap.put(IMemberRegister.RECOMMENDER, recommender);
        bodyMap.put(IMemberRegister.VERFICATIONCODE, verificationCode);
//        bodyMap.put(IMemberRegister.IMEI, DeviceUtils.getIMEI());
        bodyMap.put(IMemberRegister.PLATFORM, String.valueOf(ISendVerficationCode.PLATFORM_VALUE_ANDROID));
        HashMap<String, String> headerMap = new HashMap<>();

        LoginSubscribe.register(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {


                String jsonBody = null;
                try {
                    jsonBody = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //MLog.d("postRegisterInfo jsonBody = " + jsonBody);
                //SdLogUtil.writeCommonLog("ThreadID = " + Thread.currentThread().getId() + " onNext responseBody = " + jsonBody);

                mMemberRegisterResponseBean = (MemberRegisterResponseBean) JsonUtil.json2objectWithDataCheck(jsonBody, new TypeReference<MemberRegisterResponseBean>() {
                });
                //SdLogUtil.writeCommonLog("ThreadID = " + Thread.currentThread().getId() + " onNext mMemberRegisterResponseBean = " + mMemberRegisterResponseBean);
                handleUserNameAndPassword(phoneNum, passwd, mMemberRegisterResponseBean);
                jumpNewActivity(mMemberRegisterResponseBean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void handleUserNameAndPassword(String phoneNum, String passwd, MemberRegisterResponseBean memberRegisterResponseBean) {
        if (null != memberRegisterResponseBean && null != memberRegisterResponseBean.getData()
                && memberRegisterResponseBean.getStatus().equalsIgnoreCase(IBaseRequest.SUCCESS)) {
            if (!CommonUtils.isEmpty(phoneNum)) {
                phoneNum = SecUtil.encodeByAES(phoneNum);
            }

            if (!CommonUtils.isEmpty(passwd)) {
                passwd = SecUtil.encodeByAES(passwd);
            }

            Config.getConfig().saveUserName(phoneNum);
            Config.getConfig().savePasswd(passwd);
        }
    }

    private void jumpNewActivity(MemberRegisterResponseBean memberRegisterResponseBean) {
        if (null != memberRegisterResponseBean) {
            MemberRegisterResponseBean.DataBean dataBean = memberRegisterResponseBean.getData();
            if (null != dataBean) {
                if (memberRegisterResponseBean.getStatus().equalsIgnoreCase(IMemberRegister.STATE_SUCCESS)) {
                    Config.getConfig().saveUserServerId(dataBean.getMemberId());
                    insertUserInfo2DB(phoneNum, dataBean.getMemberId(), phoneEditText.getText().toString());
                    showRegisterTipDialog(true, phoneEditText.getText().toString());
                    return;
                }
            }

            if (memberRegisterResponseBean.getStatus().equalsIgnoreCase(IMemberRegister.STATE_FAILURE)) {
                showRegisterTipDialog(false, null);
                ToastUtil.showShortToast("注册失败！");
                       /* Intent mIntent = new Intent(RegisterByPhoneActivity.this, PhoneLoginActivity.class);
                        startActivity(mIntent);
                        finish();*/
            } else if (memberRegisterResponseBean.getStatus().equalsIgnoreCase(IMemberRegister.STATE_ACCOUNT_EXIST)) {
                ToastUtil.showShortToast("账户已存在！");
            } else if (memberRegisterResponseBean.getStatus().equalsIgnoreCase(IMemberRegister.STATE_INVALID_VERFICATION_CODE)) {
                ToastUtil.showShortToast("无效验证码！");
            } else {
                ToastUtil.showShortToast(memberRegisterResponseBean.getMessage());

                if (memberRegisterResponseBean.getStatus().equals("success")) {
                    finish();
                }
            }

        }
    }


    private void insertUserInfo2DB(String phoneNum, String userServerId, String loginName) {
        if (!CommonUtils.isEmpty(userServerId)) {
            List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(this, userServerId);
            UserInfoDBBean userInfoDBBean = null;
            if (null != userInfoDBBeanList && userInfoDBBeanList.size() > 0) {
                userInfoDBBean = userInfoDBBeanList.get(0);
            }

            if (null != userInfoDBBean) {
                userInfoDBBean.setPhone(phoneNum);
                userInfoDBBean.setLogin_name(loginName);
                UserInfoBeanDaoOpe.insertOrReplaceData(MyApplication.getInstance(), userInfoDBBean);
            } else {
                userInfoDBBean = new UserInfoDBBean();
                userInfoDBBean.setLocalid(UUID.randomUUID().toString());
                userInfoDBBean.setPhone(phoneNum);
                userInfoDBBean.setLogin_name(loginName);
                userInfoDBBean.setServerId(userServerId);
                UserInfoBeanDaoOpe.insertData(MyApplication.getInstance(), userInfoDBBean);
            }
        }
    }

    private void showRegisterTipDialog(final boolean registerSuccess, String newLoginName) {
        if (null == registerTipDialog) {
            registerTipDialog = new RegisterTipDialog(this);
        }

        registerTipDialog.setDialogButtonClickListener(new DialogButtonClickListener() {
            @Override
            public void onButtonClick(int resourceId) {
                if (resourceId == R.id.register_sure_btn) {
                    finish();
                }
            }
        });

        registerTipDialog.showTip(registerSuccess, newLoginName);
    }

    private void dimissRegisterDialog() {
        if (null != registerTipDialog) {
            registerTipDialog.dismiss();
        }
    }
}
