package com.zj.zhijue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.common.baselibrary.util.DateUtil;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.login.PhoneLoginActivity;
import com.zj.zhijue.base.BaseBleActivity;
import com.zj.zhijue.bean.MemberLoginResponseBean;
import com.zj.zhijue.bean.request.HttpRequestUserTrainTimeInfoBean;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.model.TrainModel;
import com.zj.zhijue.util.Config;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.vise.baseble.ViseBle;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;


public class MainActivity extends BaseBleActivity {
    @BindView(R.id.gowelcome)
    Button goWelcomeButton;

    @BindView(R.id.goblesearch)
    Button goBleSearchButton;

    @BindView(R.id.goscanqrcode)
    Button goScanQRCode;

    @BindView(R.id.goloading)
    Button goLoading;

    @BindView(R.id.gologin)
    Button goLoginButton;

    @BindView(R.id.updatebutton)
    Button updateButton;

    @BindView(R.id.exitapp)
    Button exitButton;

    @BindView(R.id.testedittext)
    EditText mEditText;

    public static String qrCode = "0C:FC:83:9C:EA:18";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        changeStatusBarTextColor(true);
        setNewTheme();
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        requestPermissions(getPermissions());

    }

    @Override
    protected void onResume() {
        super.onResume();
        initListener();
       // requestBlePermissions();

    }

    private void initListener() {
        goWelcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent mIntent = new Intent(MainActivity.this, WelcomeActivity.class);
               startActivity(mIntent);
               //testTrainTimeCollect();
              /*  ScanBleDeviceDialog scanBleDeviceDialog = new ScanBleDeviceDialog(MainActivity.this);
                scanBleDeviceDialog.show();*/
            }
        });
        goBleSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent mIntent = new Intent(MainActivity.this, DBOperationActivity.class);
                startActivity(mIntent);*/
                Intent mIntent = new Intent(MainActivity.this, SendReceiveBleDataActivity.class);
                startActivity(mIntent);
               //stopTrain();

/*                HttpUtil.getToken(new RequestTokenCallBack() {
                    @Override
                    public void getToken(String token) {
                        BaseConstant.TOKEN = token;
                        HashMap<String, String> headerMap = new HashMap<>();
                        //headerMap.put(ISendVerficationCode.BEARER, UrlUtils.encodeUrl(token));
                        //headerMap.put("Authorization", "Bearer " + token);
                        headerMap.put(ISendVerficationCode.AUTHORIZATION, ISendVerficationCode.BEARER + token);
                        HashMap<String, String> bodyMap = new HashMap<>();
                        bodyMap.put(ISendVerficationCode.PHONENUM, "18689486765");
                        bodyMap.put(ISendVerficationCode.IMEI, DeviceUtils.getIMEI());
                        bodyMap.put(ISendVerficationCode.PLATFORM, String.valueOf(ISendVerficationCode.PLATFORM_VALUE_ANDROID));

                        LoginSubscribe.postRegisterWithVerficationCode(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
                            @Override
                            public void onNext(ResponseBody responseBody) {
                                String jsonBody = null;
                                try {
                                    jsonBody = new String(responseBody.bytes());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                SdLogUtil.writeCommonLog("ThreadID = " + Thread.currentThread().getId() + " onNext jsonBody = " + jsonBody);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });



                    }
                });*/
            }
        });
        goScanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 1);
            }
        });
        goLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serverId = Config.getConfig().getUserServerId();
                if (!CommonUtils.isEmpty(serverId)) {
                    startActivityForResult(new Intent(MainActivity.this, BleMainControlActivity.class), 1);
                } else {
                    Intent mIntent = new Intent(MainActivity.this, PhoneLoginActivity.class);
                    startActivity(mIntent);
                }
            }
        });

        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(MainActivity.this, PhoneLoginActivity.class);
                startActivity(mIntent);
                //testJniMethod();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isEnable = mEditText.isEnabled();
                if (isEnable) {
                    mEditText.setEnabled(false);
                    updateButton.setText("不可修改");
                } else {
                    mEditText.setEnabled(true);
                    updateButton.setText("修改");
                }
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* TrainModel trainModel = new TrainModel();
                    trainModel.testUpload();*/
              /*  HttpRequestBleDataBean httpRequestBleDataBean = new HttpRequestBleDataBean();
                httpRequestBleDataBean.setMemberId(Config.getConfig().getUserServerId());
                trainModel.getNewTrainInfo2BleDevice(httpRequestBleDataBean);*/

                TrainModel trainModel = TrainModel.getInstance();
                HttpRequestUserTrainTimeInfoBean trainTimeInfoBean = new HttpRequestUserTrainTimeInfoBean();
                trainTimeInfoBean.setMemberId(Config.getConfig().getUserServerId());
                long agoLong = 365L * 24 * 60 * 60 * 1000;
                trainTimeInfoBean.setBt(DateUtil.tlocalformatter.format(new Date(System.currentTimeMillis() - agoLong)));
                trainTimeInfoBean.setEt(DateUtil.tlocalformatter.format(new Date(System.currentTimeMillis())));
                List<HttpRequestUserTrainTimeInfoBean> httpRequestUserTrainTimeInfoBeanList = trainModel.getUserTrainTimeHttpRequestBean();
                if (null != httpRequestUserTrainTimeInfoBeanList && httpRequestUserTrainTimeInfoBeanList.size() > 0) {
                    for (HttpRequestUserTrainTimeInfoBean bean: httpRequestUserTrainTimeInfoBeanList) {
                        trainModel.getUserTrainTimeinf(bean, null, false);
                    }
                }

               ViseBle.getInstance().disconnect();
               ViseBle.getInstance().clear();
               //System.exit(1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bundle bundle = data.getExtras();
                if (null != bundle) {
                    int resultType = bundle.getInt(CodeUtils.RESULT_TYPE);
                    if (resultType == CodeUtils.RESULT_SUCCESS) {
                        String macStr = bundle.getString(CodeUtils.RESULT_STRING);
                        qrCode = macStr;
                        ToastUtil.showShortToast("解析数据为：" + macStr);
                    } else if (resultType == CodeUtils.RESULT_FAILED) {
                        ToastUtil.showShort("解析二维码失败");
                    }
                }
            }
        }
    }

    private long getStartTime(int times, long interTime) {
        try {
            return DateUtil.localformatter.parse("2019-06-27 12:00:00").getTime() + times * 15L * 60 * 1000 + interTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void jumpNewActivity(MemberLoginResponseBean memberLoginResponseBeanParam) {
        if (null != memberLoginResponseBeanParam) {
            if (memberLoginResponseBeanParam.getStatus().equals(IBaseRequest.SUCCESS)) {
                MemberLoginResponseBean.DataBean mDataBean = memberLoginResponseBeanParam.getData();
                if (null != mDataBean) {
                    Config.getConfig().saveUserServerId(mDataBean.getId());
                    insertUserInfo2DB(memberLoginResponseBeanParam);
                }
            } else {
                ToastUtil.showShort(memberLoginResponseBeanParam.getMessage());
            }
        }
    }

    private void insertUserInfo2DB(MemberLoginResponseBean memberLoginResponseBean) {
        MemberLoginResponseBean.DataBean dataBean = memberLoginResponseBean.getData();
        String serverId = dataBean.getId();
        if (com.android.common.baselibrary.util.CommonUtils.isEmpty(serverId)) {
            throw new NullPointerException("serverId can not be null");
        }
        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), serverId);
        UserInfoDBBean userInfoDBBean = null;
        boolean execUpdate = false;
        if (null != userInfoDBBeanList && userInfoDBBeanList.size() > 0) {
            userInfoDBBean = userInfoDBBeanList.get(0);
        }

        if (null != userInfoDBBean && !com.android.common.baselibrary.util.CommonUtils.isEmpty(userInfoDBBean.getServerId())) {
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

        if (!com.android.common.baselibrary.util.CommonUtils.isEmpty(dataBean.getPhone())) {
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



        if (execUpdate) {
            UserInfoBeanDaoOpe.insertOrReplaceData(MyApplication.getInstance(), userInfoDBBean);
        } else {
            userInfoDBBean.setLocalid(UUID.randomUUID().toString());
            UserInfoBeanDaoOpe.insertData(MyApplication.getInstance(), userInfoDBBean);
        }

    }

}
