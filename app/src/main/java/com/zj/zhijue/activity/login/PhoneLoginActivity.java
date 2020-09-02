package com.zj.zhijue.activity.login;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.app.BaseApplication;
import com.android.common.baselibrary.jnative.SecUtil;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.ActivityStackUtil;
import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.FileUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.security.Md5Util;
import com.blankj.utilcode.util.SPUtils;
import com.vise.utils.view.DialogUtil;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.common.http.retrofit.netutils.NetUtil;
import com.zj.zhijue.BuildConfig;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.BleMainControlActivity;
import com.zj.zhijue.activity.mine.PersonalInfoActivity;
import com.zj.zhijue.activity.register.RegisterByPhoneActivity;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.bean.MemberLoginResponseBean;
import com.zj.zhijue.bean.MemberLoginResponseBeanFirst;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.constant.ConstantString;
import com.zj.zhijue.fragment.MineFragment;
import com.zj.zhijue.fragment.mine.SystemSettings2Fragment;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.http.request.IMemberLogin;
import com.zj.zhijue.http.request.ISendVerficationCode;
import com.zj.zhijue.model.GlassesBleDataModel;
import com.zj.zhijue.model.TrainModel;
import com.zj.zhijue.util.AppUtils;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.UserInfoUtil;
import com.zj.zhijue.util.downloadutil.DownloadUtils;
import com.zj.zhijue.util.downloadutil.JsDownloadListener;
import com.zj.zhijue.util.jsonutil.GsonTools;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.util.view.ui.DeviceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * 登录界面
 */
public class PhoneLoginActivity extends BaseActivity {
    @BindView(R.id.usernameedittext)
    EditText userNameEditText;

    @BindView(R.id.usernameunderline)
    View userNameUnderLineView;

    @BindView(R.id.passwordedittext)
    EditText passwdEditText;

    @BindView(R.id.passwdunderlineview)
    View passwdUnderLineView;

    @BindView(R.id.forgetpasswd)
    TextView forgetPasswdTextView;

    @BindView(R.id.loginbutton)
    Button loginButton;

    @BindView(R.id.registerbutton)
    Button registerButton;
    @BindView(R.id.tv_login_agreement)
    TextView tvLoginAgreement;


    private boolean loginWithoutNet = false;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        checkLoginUser();
        setContentView(R.layout.activity_phone_login);
        ButterKnife.bind(this);
        super.onCreate(bundle);
        initView();
        initListener();

        String[] subArray = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
        };
        requestPermissions(subArray);
    }

    private void initView() {
        restoreUserNameAndPasswd();

        boolean isInvalid = getIntent().getBooleanExtra("isInvalid", false);
        if (isInvalid) {
            FileUtils.deleteFile(TrainModel.HTTP_LOGIN_USER_INFO);
            DialogUtil.showTips(this, "提示", "登录信息失效，请重新登录！", "确定", new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {

                }
            });
        }
    }

    private void initListener() {
       /* visiableCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked){
                    passwdEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);// 输入为密码且可见
                }else {
                    passwdEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);// 设置文本类密码（默认不可见），这两个属性必须同时设置
                }
            }
        });*/

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginWithoutNet) {
                    testLoginByLocalJson();
                    return;
                }
                saveUserNameAndPasswd();
                if (verifyInputText()) {
                    login(getUserNameText(), getPasswdText());
                } else {
                    ToastUtil.showShortToast("用户名或密码不能为空！");
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(PhoneLoginActivity.this, RegisterByPhoneActivity.class);
                startActivity(mIntent);
            }
        });


        forgetPasswdTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(PhoneLoginActivity.this, ForgetPasswdPasswdActivity.class);
                startActivity(mIntent);
            }
        });

        tvLoginAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(PhoneLoginActivity.this, PersonalInfoActivity.class);
                mIntent.putExtra(PersonalInfoActivity.FRAGMENT_INDEX_KEY, MineFragment.USER_AGREEMENT_INDEX);
                startActivity(mIntent);
            }
        });
    }

    private String getUserNameText() {
        return userNameEditText.getText().toString();
    }

    private String getPasswdText() {
        return passwdEditText.getText().toString();
    }

    private boolean verifyUserNameText() {
        String userName = getUserNameText();
        if (!CommonUtils.isEmpty(userName)) {
            return true;
        }
        return false;
    }

    private boolean verifyPasswdText() {
        String passwed = getPasswdText();
        if (!CommonUtils.isEmpty(passwed)) {
            return true;
        }
        return false;
    }

    private boolean verifyInputText() {
        if (verifyUserNameText() && verifyPasswdText()) {
            return true;
        }
        return false;
    }

    private void saveUserNameAndPasswd() {
        if (true) {
            Config.getConfig().saveRemeberMeStatus(true);
            String userName = getUserNameText();
            String passwd = getPasswdText();
            if (!CommonUtils.isEmpty(userName)) {
                userName = SecUtil.encodeByAES(userName);
            }

            if (!CommonUtils.isEmpty(passwd)) {
                passwd = SecUtil.encodeByAES(passwd);
            }

            Config.getConfig().saveUserName(userName);
            Config.getConfig().savePasswd(passwd);
        } else {
            Config.getConfig().saveRemeberMeStatus(false);
        }
    }

    private void restoreUserNameAndPasswd() {

        String userName = Config.getConfig().getUserName();
        String passwd = Config.getConfig().getPasswd();
        if (!CommonUtils.isEmpty(userName)) {
            userName = SecUtil.decodeByAES(userName);
            if (!CommonUtils.isEmpty(userName)) {
                userNameEditText.setText(userName);
            }
        }

        if (!CommonUtils.isEmpty(passwd)) {
            passwd = SecUtil.decodeByAES(passwd);
            if (!CommonUtils.isEmpty(passwd)) {
                passwdEditText.setText(passwd);
            }
        }

    }

    private void login(final String userName, final String passwd) {
        if (!checkNetworkAvaliable()) {
            return;
        }
        changeLoginButtonStatus(false);
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();
//        bodyMap.put(IMemberLogin.VERSION_CODE, AppUtils.getVersionCode2Str());
        bodyMap.put(IMemberLogin.LOGIN_NAME, userName);
        bodyMap.put(IMemberLogin.PASSWORD, passwd);
//        bodyMap.put(IMemberLogin.PASSWORD, Md5Util.getMD5String(SecUtil.getRSAsinatureWithLogin(passwd)));
        bodyMap.put(IMemberLogin.IMEI, DeviceUtils.getIMEI());
        bodyMap.put(IMemberLogin.IP, NetUtil.getIpAddress(MyApplication.getInstance()));
        bodyMap.put(IMemberLogin.PLATFORMTYPE, String.valueOf(ISendVerficationCode.PLATFORM_VALUE_ANDROID));
        bodyMap.put(IMemberLogin.VERSIONNAME, AppUtils.getGitCommitVersionName(MyApplication.getInstance()));

//        //甲方登录
//        bodyMap.put("grantType", "password");
//        bodyMap.put("username", userName);
//        bodyMap.put("clientId", "zhijue-ad");
//        bodyMap.put("clientSecret", "123456");
//        bodyMap.put("password", Md5Util.getMD5String(SecUtil.getRSAsinatureWithLogin(passwd)));
//        bodyMap.put("utdid", "utdid");


        //bodyMap.put(ISendVerficationCode.PLATFORM, String.valueOf(ISendVerficationCode.PLATFORM_VALUE_ANDROID));
        //mVerificationCodResponseBean = null;
        LoginSubscribe.loginAccount(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                changeLoginButtonStatus(true);
                MLog.d("login onNext");


                String jsonBody = null;
                try {
                    jsonBody = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject=JSONObject.parseObject(jsonBody);
                jsonObject.getString("code");

                MLog.d("Server login jsonBody = " + jsonBody);
                MemberLoginResponseBean memberLoginResponseBean = (MemberLoginResponseBean) JsonUtil.json2objectWithDataCheck(jsonBody, new TypeReference<MemberLoginResponseBean>() {
                });
                //甲方登录
//                MemberLoginResponseBeanFirst memberLoginResponseBean1 = (MemberLoginResponseBeanFirst) JsonUtil.json2objectWithDataCheck(jsonBody, new TypeReference<MemberLoginResponseBean>() {
//                });
//                MemberLoginResponseBean memberLoginResponseBean=new MemberLoginResponseBean();
//                memberLoginResponseBean.getData().setAccess_token(memberLoginResponseBean1.getData().getTokenInfo().getAccess_token());
//                memberLoginResponseBean.getData().setId(memberLoginResponseBean1.getData().getUserInfo().getId());
//                memberLoginResponseBean.getData().setMemberId(memberLoginResponseBean1.getData().getUserInfo().getId());
//                memberLoginResponseBean.getData().setNickname(memberLoginResponseBean1.getData().getUserInfo().getNickname());
//                memberLoginResponseBean.getData().setAreaCode(memberLoginResponseBean1.getData().getUserInfo().getAreaCode());
//                memberLoginResponseBean.getData().setBorn_date(memberLoginResponseBean1.getData().getUserInfo().getBornDate());
//                memberLoginResponseBean.getData().setAge(memberLoginResponseBean1.getData().getUserInfo().getAge());
//                memberLoginResponseBean.getData().setFace(memberLoginResponseBean1.getData().getUserInfo().getFace());
//                memberLoginResponseBean.getData().setIsnewuser(memberLoginResponseBean1.getData().getUserInfo().getIsNewUser()==1);

                if (BaseApplication.isDebugMode) {
                    FileUtils.write(MyApplication.getInstance(), TrainModel.HTTP_LOGIN_USER_INFO, GsonTools.createGsonString(memberLoginResponseBean));
                    String json = FileUtils.read(MyApplication.getInstance(), TrainModel.HTTP_LOGIN_USER_INFO);
                    MLog.d("login_response_file_json = " + json);
                }
                jumpNewActivity(memberLoginResponseBean);
            }

            @Override
            public void onError(Throwable e) {
                MLog.d("login onError " + e.getMessage());
                changeLoginButtonStatus(true);
                e.printStackTrace();

            }

            @Override
            public void onComplete() {
                MLog.d("login on complete ");

            }
        });


    }

    private void changeLoginButtonStatus(boolean clickable) {
        if (null != loginButton) {
            loginButton.setClickable(clickable);
        }
    }

    private void jumpNewActivity(MemberLoginResponseBean memberLoginResponseBeanParam) {
        if (null != memberLoginResponseBeanParam) {
            if (memberLoginResponseBeanParam.getStatus().equals(IBaseRequest.SUCCESS)) {
                MemberLoginResponseBean.DataBean mDataBean = memberLoginResponseBeanParam.getData();
                if (null != mDataBean) {
                    Config.getConfig().saveUserServerId(mDataBean.getId());
                    //接口没有返回直接用用户填写的 2020-06-21 21:41:31
                    mDataBean.setLogin_name(userNameEditText.getText().toString());
                    SPUtils.getInstance().put(SPUtils.KEY_ACCESS_TOKEN, mDataBean.getAccess_token());
                    UserInfoUtil.loginSuccess(mDataBean.getAccess_token(), mDataBean.getPhone());
//                    if (haveBindDeviceAndEyeInfo(mDataBean)) {
                    Intent mIntent = new Intent(PhoneLoginActivity.this, BleMainControlActivity.class);
                    mIntent.putExtra(ConstantString.KEY_MAIN_CONTR0L_FROM_LOGIN, true);
                    startActivity(mIntent);
                    insertUserInfo2DB(memberLoginResponseBeanParam);
                    finish();
                    //屏蔽跳转到录入视力信息页面 2020-07-12 23:46:25
//                    } else {
//                        Intent mIntent = new Intent(PhoneLoginActivity.this, BindPersonalInfoActivity.class);
//                        startActivity(mIntent);
//                        insertUserInfo2DB(memberLoginResponseBeanParam);
//                        finish();
//                    }
                }
            } else {
                ToastUtil.showShort(memberLoginResponseBeanParam.getMessage());
            }
        }
    }

    private void updateUserInfo(MemberLoginResponseBean memberLoginResponseBeanParam) {
        if (null != memberLoginResponseBeanParam) {
            if (memberLoginResponseBeanParam.getStatus().equals(IBaseRequest.SUCCESS)) {
                MemberLoginResponseBean.DataBean mDataBean = memberLoginResponseBeanParam.getData();
                if (null != mDataBean) {
                    Config.getConfig().saveUserServerId(mDataBean.getId());
                    UserInfoUtil.loginSuccess(mDataBean.getAccess_token(), mDataBean.getPhone());
                    insertUserInfo2DB(memberLoginResponseBeanParam);
                }
            } else {
                ToastUtil.showShort(memberLoginResponseBeanParam.getMessage());
                if (memberLoginResponseBeanParam.getStatus().equals(IBaseRequest.LOGIN_ERROR_WITH_USERNAME_PASSW)) {
                    /**
                     * 用户名或密码不正确
                     */
                    Config.getConfig().savePasswd(null);
                    Config.getConfig().saveFreshToken(null);
                    Config.getConfig().saveAccessToken(null);
                    BleDeviceManager.getInstance().stopScan();
                    BleDeviceManager.getInstance().stopScanByMac();
                    BleDeviceManager.getInstance().disconnectGlassesBleDevice(true);
                    GlassesBleDataModel.getInstance().clearModelData();
                    TrainModel.getInstance().clearTrainModelData();
//                    //要求用户直接登录
//                    BaseActivity.GotoLoginActivity();
                }
            }
        }
    }

    private void insertUserInfo2DB(MemberLoginResponseBean memberLoginResponseBean) {
        MemberLoginResponseBean.DataBean dataBean = memberLoginResponseBean.getData();
        String serverId = dataBean.getId();
        if (CommonUtils.isEmpty(serverId)) {
            throw new NullPointerException("serverId can not be null");
        }
        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), serverId);
        UserInfoDBBean userInfoDBBean = null;
        boolean execUpdate = false;
        if (null != userInfoDBBeanList && userInfoDBBeanList.size() > 0) {
            userInfoDBBean = userInfoDBBeanList.get(0);
        }

        if (null != userInfoDBBean && !CommonUtils.isEmpty(userInfoDBBean.getServerId())) {
            /**
             * 本地保存的有用户的视力信息
             */
            execUpdate = true;
        } else {
            /**
             * 本地未保存的有用户的视力信息
             */
            execUpdate = false;
            userInfoDBBean = new UserInfoDBBean();
            userInfoDBBean.setServerId(dataBean.getId());
        }

        userInfoDBBean.setGuardian_id(dataBean.getGuardian_id());
        userInfoDBBean.setLogin_name(dataBean.getLogin_name());
        userInfoDBBean.setNickname(dataBean.getNickname());
        userInfoDBBean.setName(dataBean.getName());
        userInfoDBBean.setDiopter_state(dataBean.getDiopter_state());

        userInfoDBBean.setLeft_eye_degree(dataBean.getLeft_eye_degree());
        userInfoDBBean.setRight_eye_degree(dataBean.getRight_eye_degree());
        userInfoDBBean.setLeft_front_com_degree(dataBean.getLeft_front_com_degree());
        userInfoDBBean.setRight_front_com_degree(dataBean.getRight_front_com_degree());
        userInfoDBBean.setInterpupillary(dataBean.getInterpupillary());


        userInfoDBBean.setCorrect_left_eye_degree(dataBean.getCorrect_left_eye_degree());
        userInfoDBBean.setCorrect_right_eye_degree(dataBean.getCorrect_right_eye_degree());
        userInfoDBBean.setCorrect_binoculus_degree(dataBean.getCorrect_binoculus_degree());


        userInfoDBBean.setNaked_left_eye_degree(dataBean.getNaked_left_eye_degree());
        userInfoDBBean.setNaked_right_eye_degree(dataBean.getNaked_right_eye_degree());
        userInfoDBBean.setNaked_binoculus_degree(dataBean.getNaked_binoculus_degree());

        userInfoDBBean.setAge(dataBean.getAge());
        userInfoDBBean.setBorn_date(dataBean.getBorn_date());
        userInfoDBBean.setAreaCode(dataBean.getAreaCode());
        userInfoDBBean.setCredentials_card(dataBean.getCredentials_card());
        userInfoDBBean.setCredentials_type(dataBean.getCredentials_type());
        userInfoDBBean.setExpiration_time(dataBean.getExpiration_time());

        userInfoDBBean.setPortrait_image_url(dataBean.getFace());

        if (!CommonUtils.isEmpty(dataBean.getPhone())) {
            userInfoDBBean.setPhone(dataBean.getPhone());
        }

        userInfoDBBean.setSex(dataBean.getSex());
        userInfoDBBean.setStatus(dataBean.getStatus());


        userInfoDBBean.setLeft_astigmatism_degree(dataBean.getLeft_astigmatism_degree());
        userInfoDBBean.setRight_astigmatism_degree(dataBean.getRight_astigmatism_degree());
        userInfoDBBean.setLeft_eye_train_degree(dataBean.getLeft_handle_degree());
        userInfoDBBean.setRight_eye_train_degree(dataBean.getRight_handle_degree());

        /**
         *      * isnewuser	bool	是否新用户
         *      * total_money	float	总充值金额
         *      * used_money	float	已使用金额
         *      * total_time	float	总累计小时数
         *      * used_time	float	已使用小时数
         *      * total_score	float	累计总积分
         *      * used_score	float	已使用积分
         *      * status	int	状态0审核中 1审核通过 2审核不通过
         */

        userInfoDBBean.setIsnewuser(dataBean.isIsnewuser());
        userInfoDBBean.setTotal_money(dataBean.getTotal_money());
        userInfoDBBean.setUsed_money(dataBean.getUsed_money());
        userInfoDBBean.setTotal_time(dataBean.getTotal_time());
        userInfoDBBean.setUsed_time(dataBean.getUsed_time());
        userInfoDBBean.setTotal_score(dataBean.getTotal_score());
        userInfoDBBean.setUsed_score(dataBean.getUsed_score());

        userInfoDBBean.setLeft_axial(dataBean.getLeft_axial());
        userInfoDBBean.setRight_axial(dataBean.getRight_axial());

        /**
         * 添加训练时长信息
         */
        userInfoDBBean.setTrainTimeYear(dataBean.getTrainTimeYear());
        userInfoDBBean.setTrainTimeMonth(dataBean.getTrainTimeMonth());
        userInfoDBBean.setTrainTimeDay(dataBean.getTrainTimeDay());
        userInfoDBBean.setTrainTimeHour(dataBean.getTrainTimeHour());
        userInfoDBBean.setTrainTimeMinute(dataBean.getTrainTimeMinute());
        userInfoDBBean.setTrainTimeSecond(dataBean.getTrainTimeSecond());

        if (execUpdate) {
            UserInfoBeanDaoOpe.insertOrReplaceData(MyApplication.getInstance(), userInfoDBBean);
        } else {
            userInfoDBBean.setLocalid(UUID.randomUUID().toString());
            UserInfoBeanDaoOpe.insertData(MyApplication.getInstance(), userInfoDBBean);
        }

    }

    private boolean haveBindDeviceAndEyeInfo(MemberLoginResponseBean.DataBean dataBean) {
        if (null != dataBean) {
            if (dataBean.isHasEyeData()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void checkLoginUser() {


        boolean loginSuccess = lastLoginSuccess();
        if (BuildConfig.DEBUG) {
            MLog.d("loginSuccess = " + loginSuccess);
        }
        if (loginSuccess) {
            loginInBackGround();
            Intent mIntent = new Intent(PhoneLoginActivity.this, BleMainControlActivity.class);
            startActivity(mIntent);
            finish();
        }

    }

    private boolean lastLoginSuccess() {
        String serverId = Config.getConfig().getUserServerId();
        if (!CommonUtils.isEmpty(serverId)) {
            List<UserInfoDBBean> listBean = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), serverId);
            if (null != listBean && listBean.size() > 0) {
                UserInfoDBBean userInfoDBBean = listBean.get(0);
                if (null != userInfoDBBean && (!CommonUtils.isEmpty(userInfoDBBean.getPhone()) || !CommonUtils.isEmpty(userInfoDBBean.getLogin_name()))
                        && !CommonUtils.isEmpty(userInfoDBBean.getName())
                        && !CommonUtils.isEmpty(userInfoDBBean.getBorn_date())) {

                    String userName = Config.getConfig().getUserName();
                    String passwd = Config.getConfig().getPasswd();
                    if (!CommonUtils.isEmpty(userName) && !CommonUtils.isEmpty(passwd)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void loginInBackGround() {
        String userName = Config.getConfig().getUserName();
        String passwd = Config.getConfig().getPasswd();

        if (!CommonUtils.isEmpty(userName)) {
            userName = SecUtil.decodeByAES(userName);
        }

        if (!CommonUtils.isEmpty(passwd)) {
            passwd = SecUtil.decodeByAES(passwd);
        }

        loginWithBackGround(userName, passwd);
    }

    private void loginWithBackGround(final String userName, final String passwd) {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();

        bodyMap.put(IMemberLogin.LOGIN_NAME, userName);
//        bodyMap.put(IMemberLogin.PASSWORD, Md5Util.getMD5String(SecUtil.getRSAsinatureWithLogin(passwd)));
        bodyMap.put(IMemberLogin.PASSWORD, passwd);
        bodyMap.put(IMemberLogin.IMEI, DeviceUtils.getIMEI());
        bodyMap.put(IMemberLogin.IP, NetUtil.getIpAddress(MyApplication.getInstance()));
        bodyMap.put(IMemberLogin.PLATFORMTYPE, String.valueOf(ISendVerficationCode.PLATFORM_VALUE_ANDROID));
        bodyMap.put(IMemberLogin.VERSIONNAME, AppUtils.getGitCommitVersionName(MyApplication.getInstance()));

        LoginSubscribe.loginAccount(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String jsonBody = null;
                try {
                    jsonBody = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MemberLoginResponseBean memberLoginResponseBean = (MemberLoginResponseBean) JsonUtil.json2objectWithDataCheck(jsonBody, new TypeReference<MemberLoginResponseBean>() {
                });
                updateUserInfo(memberLoginResponseBean);
                if (BaseApplication.isDebugMode) {
                    FileUtils.write(MyApplication.getInstance(), TrainModel.HTTP_LOGIN_USER_INFO, GsonTools.createGsonString(memberLoginResponseBean));
                    String json = FileUtils.read(MyApplication.getInstance(), TrainModel.HTTP_LOGIN_USER_INFO);
                    MLog.d("login_response_file_json = " + json);
                }
            }

            @Override
            public void onError(Throwable e) {
                MLog.d("login onError " + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                MLog.d("login on complete ");
            }
        });
    }

    private void testLoginByLocalJson() {
        String loginResponseJson = "{\"status\":\"success\",\"message\":\"成功\",\"data\":{\"id\":\"bf46fb55-e2cc-4251-b0a0-4c1ad23d961a\",\"guardian_id\":null,\"login_name\":\"13987654321_P\",\"password\":null,\"nickname\":\"13987654321\",\"name\":\"某某\",\"age\":30,\"sex\":1,\"born_date\":\"1989-12-06T16:00:00.000+0000\",\"credentials_type\":0,\"credentials_card\":\"420607198912072819\",\"phone\":\"13987654321\",\"diopter_state\":0,\"left_eye_degree\":200.0,\"right_eye_degree\":300.0,\"integererpupillary\":66.0,\"left_astigmatism_degree\":30.0,\"right_astigmatism_degree\":60.0,\"astigmatism_degree\":0.0,\"left_handle_degree\":115.0,\"right_handle_degree\":180.0,\"right_column_mirror\":null,\"left_column_mirror\":null,\"right_axial\":30.0,\"left_axial\":60.0,\"naked_left_eye_degree\":4.3,\"naked_right_eye_degree\":4.6,\"naked_binoculus_degree\":5.1,\"correct_left_eye_degree\":5.2,\"correct_right_eye_degree\":5.3,\"correct_binoculus_degree\":5.1,\"isnewuser\":null,\"total_money\":null,\"used_money\":null,\"total_time\":null,\"used_time\":null,\"total_score\":null,\"used_score\":null,\"status\":null,\"expiration_time\":null,\"wearingHours\":0,\"face\":null},\"cursor\":null}";
        MemberLoginResponseBean memberLoginResponseBean = (MemberLoginResponseBean) JsonUtil.json2objectWithDataCheck(loginResponseJson, new TypeReference<MemberLoginResponseBean>() {
        });
        jumpNewActivity(memberLoginResponseBean);
    }

    private void testDownload() {
        String baseUrl = "http://softdown1.hao123.com/";
        //String exeUrl = "http://softdown1.hao123.com/hao123-soft-online-bcs/soft/2018_1_3_Setup.exe";
        String extUrl = "hao123-soft-online-bcs/soft/2018_1_3_Setup.exe";

        DownloadUtils downloadUtils = new DownloadUtils(baseUrl, new JsDownloadListener() {
            @Override
            public void onStartDownload(long length) {
                MLog.d("length = " + length + " thread.id = " + Thread.currentThread().getId());

            }

            @Override
            public void onProgress(int progress) {
                //MLog.d("progress = " + progress);
            }

            @Override
            public void onFail(String errorInfo) {
                MLog.d("errorInfo = " + errorInfo);
            }
        });

        Observable observable = downloadUtils.getDownApi(extUrl);//HttpMethods.getInstance().getHttpApi().downloadFile(new HashMap<String, Object>(), TrainSuscribe.createNewRequestBody(new HashMap<String, String>()), exeUrl);
        File dirFile = getDir("test", Context.MODE_APPEND);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        final File file = new File(dirFile.getAbsolutePath() + File.separator + "aa.exe");
        if (file.exists()) {
            file.delete();
        }
        downloadUtils.download(file, observable, new DisposableObserver<InputStream>() {
            @Override
            public void onNext(InputStream responseBody) {
                MLog.d("onNext = responseBody" + responseBody);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                MLog.d("onError = " + e.getMessage());
            }

            @Override
            public void onComplete() {
                MLog.d("onComplete = file = " + file.length());

            }
        });
    }

    @Override
    public void onBackPressed() {
        ActivityStackUtil.getInstance().finishAllActivity();
        super.onBackPressed();
    }
}
