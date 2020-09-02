package com.zj.zhijue.activity.bindglasses;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.ActivityStackUtil;
import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.EyeFloatDegreeInputFilter;
import com.android.common.baselibrary.util.ToastUtil;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.google.gson.Gson;
import com.huige.library.utils.SharedPreferencesUtils;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.BleMainControlActivity;
import com.zj.zhijue.activity.login.PhoneLoginActivity;
import com.zj.zhijue.base.BaseBleActivity;
import com.zj.zhijue.bean.BindPersonalInfoBean;
import com.zj.zhijue.bean.MemberBindDeviceResponseBean;
import com.zj.zhijue.bean.UseInfoBean;
import com.zj.zhijue.config.Constants;
import com.zj.zhijue.event.PayStateEventBean;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IMemberBindDevice;
import com.zj.zhijue.pay.PaymentUtil;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.GsonUtil;
import com.zj.zhijue.util.jsonutil.JsonUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * 注册时，填写视力信息第二页
 */

public class BindPersonalInfoSecondActivity extends BaseBleActivity {

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView titleTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLayout;

    @BindView(R.id.lefteyerefraction)//裸眼视力（左眼）
            AppCompatEditText leftEyeRefractionEditText;

    @BindView(R.id.righteyerefraction)//裸眼视力（右眼）
            AppCompatEditText rightEyeRefractionEditText;

    @BindView(R.id.doubleeyerefraction)//裸眼视力（双眼）
            AppCompatEditText doubleEyeRefractionEditText;

    @BindView(R.id.lefteyeastigmatism)//矫正视力（左眼）
            AppCompatEditText leftEyeAstigmation;

    @BindView(R.id.righteyeastigmatism)//矫正视力（右眼）
            AppCompatEditText rightEyeAstigmation;

    @BindView(R.id.twoeyeastigmatism)//矫正视力（双眼）
            AppCompatEditText doubleEyeAstigMationEditText;

    @BindView(R.id.register_complete_button)
    AppCompatButton registeButton;

    private List<EditText> editTextList = new ArrayList<>();

    private final String TAG = BindPersonalInfoSecondActivity.class.getSimpleName();
    public static final String BIND_PERSONAL_EYE_INFO_KEY = "BIND_PERSONAL_EYE_INFO_KEY";

    private BindPersonalInfoBean bindPersonalInfoBean;

    //用户信息-数据回填用
    private UseInfoBean.UseBean useInfoBean;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setContentView(R.layout.activity_bingglasseswithidno_second);
        super.onCreate(bundle);
        initData();
        initView();
        initListener();

        useInfoBean = getIntent().getParcelableExtra("useInfoBean");
        setDefaultData();

    }

    /**
     * 设置默认的数据回填
     */
    private void setDefaultData() {
        if (useInfoBean == null || (useInfoBean.getNakedLeftEyeDegree() == 0 && useInfoBean.getNakedRightEyeDegree() == 0 && useInfoBean.getNakedBinoculusDegree() == 0
                && useInfoBean.getCorrectLeftEyeDegree() == 0 && useInfoBean.getCorrectRightEyeDegree() == 0 && useInfoBean.getCorrectBinoculusDegree() == 0)) {
            Gson gson = new Gson();
            String data = (String) SharedPreferencesUtils.get(Constants.SP_OPTOMETRYDATA, "");
            BindPersonalInfoBean personalInfoBean = gson.fromJson(data, BindPersonalInfoBean.class);


            leftEyeRefractionEditText.setText(personalInfoBean == null || ObjectUtils.isEmpty(personalInfoBean.getNakedLeftEyeDegree()) || personalInfoBean.getNakedLeftEyeDegree().equals("0") ? "4.0" : personalInfoBean.getNakedLeftEyeDegree());
            rightEyeRefractionEditText.setText(personalInfoBean == null || ObjectUtils.isEmpty(personalInfoBean.getNakedRightEyeDegree()) || personalInfoBean.getNakedRightEyeDegree().equals("0") ? "4.0" : personalInfoBean.getNakedRightEyeDegree());
            doubleEyeRefractionEditText.setText(personalInfoBean == null || ObjectUtils.isEmpty(personalInfoBean.getNakedBinoculusDegree()) || personalInfoBean.getNakedBinoculusDegree().equals("0") ? "5.3" : personalInfoBean.getNakedBinoculusDegree());

            leftEyeAstigmation.setText(personalInfoBean == null || ObjectUtils.isEmpty(personalInfoBean.getCorrectLeftEyeDegree()) || personalInfoBean.getCorrectLeftEyeDegree().equals("0") ? "4.0" : personalInfoBean.getCorrectLeftEyeDegree());
            rightEyeAstigmation.setText(personalInfoBean == null || ObjectUtils.isEmpty(personalInfoBean.getCorrectRightEyeDegree()) || personalInfoBean.getCorrectRightEyeDegree().equals("0") ? "4.0" : personalInfoBean.getCorrectRightEyeDegree());
            doubleEyeAstigMationEditText.setText(personalInfoBean == null || ObjectUtils.isEmpty(personalInfoBean.getCorrectBinoculusDegree()) || personalInfoBean.getCorrectBinoculusDegree().equals("0") ? "4.0" : personalInfoBean.getCorrectBinoculusDegree());
            return;
        }

        leftEyeRefractionEditText.setText(String.valueOf(useInfoBean.getNakedLeftEyeDegree()));
        rightEyeRefractionEditText.setText(String.valueOf(useInfoBean.getNakedRightEyeDegree()));
        doubleEyeRefractionEditText.setText(String.valueOf(useInfoBean.getNakedBinoculusDegree()));


        leftEyeAstigmation.setText(String.valueOf(useInfoBean.getCorrectLeftEyeDegree()));
        rightEyeAstigmation.setText(String.valueOf(useInfoBean.getCorrectRightEyeDegree()));
        doubleEyeAstigMationEditText.setText(String.valueOf(useInfoBean.getCorrectBinoculusDegree()));

    }

    private void initData() {
        Intent mIntent = getIntent();
        if (null != mIntent) {
            bindPersonalInfoBean = mIntent.getParcelableExtra(BIND_PERSONAL_EYE_INFO_KEY);
            if (null == bindPersonalInfoBean) {
                finish();
            } else {
                MLog.d("bindPersonalInfoBean = " + bindPersonalInfoBean.toString());
            }
        } else {
            finish();
        }

        editTextList.clear();
        editTextList.add(leftEyeRefractionEditText);
        editTextList.add(rightEyeRefractionEditText);
        editTextList.add(doubleEyeRefractionEditText);
        editTextList.add(leftEyeAstigmation);
        editTextList.add(rightEyeAstigmation);
        editTextList.add(doubleEyeAstigMationEditText);
    }

    private void initView() {
        titleTextView.setText("验光数据");

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initListener() {
        registeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allInput = allEditTextInput();
                if (!allInput) {
                    ToastUtil.showShort(R.string.person_info_no_complete_text);
                    return;
                }
                if (inputAllRight()) {
                    bindGlassesInfo();
                }
            }
        });
        InputFilter[] filters = {new EyeFloatDegreeInputFilter()};

        for (final EditText editText : editTextList) {
            editText.setFilters(filters);
            editText.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    changeEditTextColor(editText, true);
                    String content = editText.getText().toString();

                    if (!CommonUtils.isEmpty(content)) {
                        int length = content.length();
                        if (length == 1) {
                            if (content.equals(".")) {
                                editText.setText("4");
                                editText.setSelection(1);
                            } else {
                                Integer integer = Integer.parseInt(content);
                                if (integer < 4) {
                                    editText.setText("4");
                                    editText.setSelection(1);
                                } else if (integer > 5) {
                                    editText.setText("5.");
                                    editText.setSelection(2);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * 裸眼视力（左眼）
     *
     * @return
     */
    private String getLeftEyeSight() {
        return leftEyeRefractionEditText.getText().toString();
    }

    /**
     * 裸眼视力（右眼）
     *
     * @return
     */
    private String getRightEyeSight() {
        return rightEyeRefractionEditText.getText().toString();
    }

    /**
     * 裸眼视力（双眼）
     *
     * @return
     */
    private String getDoubleEyeSight() {
        return doubleEyeRefractionEditText.getText().toString();
    }

    /**
     * 矫正视力（左眼）
     *
     * @return
     */
    private String getLeftEyeAstigmatism() {
        return leftEyeAstigmation.getText().toString();
    }

    /**
     * 矫正视力（右眼）
     *
     * @return
     */
    private String getRightEyeAstigmatism() {
        return rightEyeAstigmation.getText().toString();
    }

    /**
     * 矫正视力（双眼）
     *
     * @return
     */
    private String getDoubleEyeAstigmatism() {
        return doubleEyeAstigMationEditText.getText().toString();
    }

    private boolean verifyValue(String editTextValue) {
        Float eyeSight = Float.parseFloat(editTextValue);
        if (eyeSight >= 4.0f && eyeSight <= 5.3f) {
            return true;
        }
        return false;
    }

    private boolean verifyLeftEyeSight() {
        String leftEyeSight = getLeftEyeSight();
        if (!CommonUtils.isEmpty(leftEyeSight) && verifyValue(leftEyeSight)) {
            return true;
        }
        return false;
    }

    private boolean verifyRightEyeSight() {
        String rightEyeSight = getRightEyeSight();
        if (!CommonUtils.isEmpty(rightEyeSight) && verifyValue(rightEyeSight)) {
            return true;
        }
        return false;
    }

    private boolean verifyDoubleEyeSight() {
        String doubleEyeSight = getDoubleEyeSight();
        if (!CommonUtils.isEmpty(doubleEyeSight) && verifyValue(doubleEyeSight)) {
            return true;
        }
        return false;
    }

    private boolean verifyLefyEyeAstigmatism() {
        String leftEyeAstigmatism = getLeftEyeAstigmatism();
        if (!CommonUtils.isEmpty(leftEyeAstigmatism) && verifyValue(leftEyeAstigmatism)) {
            return true;
        }
        return false;
    }

    private boolean verifyRightEyeAstigmatism() {
        String rightEyeAstigmatism = getRightEyeAstigmatism();
        if (!CommonUtils.isEmpty(rightEyeAstigmatism) && verifyValue(rightEyeAstigmatism)) {
            return true;
        }
        return false;
    }

    private boolean verifyDoubleEyeAstigmationSm() {
        String doubleEyeAstigmatism = getDoubleEyeAstigmatism();
        if (!CommonUtils.isEmpty(doubleEyeAstigmatism) && verifyValue(doubleEyeAstigmatism)) {
            return true;
        }
        return false;
    }

    private boolean allEditTextInput() {
        for (EditText editText : editTextList) {
            String content = editText.getText().toString();
            if (CommonUtils.isEmpty(content)) {
                return false;
            }
        }
        return true;
    }

    private boolean inputAllRight() {
        boolean verifyNakedLeftEye = verifyLeftEyeSight();
        changeEditTextColor(leftEyeRefractionEditText, verifyNakedLeftEye);

        boolean verifyNakeyRightEye = verifyRightEyeSight();
        changeEditTextColor(rightEyeRefractionEditText, verifyNakeyRightEye);

        boolean verifyNakeDoubleEye = verifyDoubleEyeSight();
        changeEditTextColor(doubleEyeRefractionEditText, verifyNakeDoubleEye);

        boolean verifyCorretLeftEye = verifyLefyEyeAstigmatism();
        changeEditTextColor(leftEyeAstigmation, verifyCorretLeftEye);

        boolean verifyCorrectRightEye = verifyRightEyeAstigmatism();
        changeEditTextColor(rightEyeAstigmation, verifyCorrectRightEye);

        boolean verifyCorrectDoubleEye = verifyDoubleEyeAstigmationSm();
        changeEditTextColor(doubleEyeAstigMationEditText, verifyCorrectDoubleEye);

        if (verifyNakedLeftEye && verifyNakeyRightEye && verifyNakeDoubleEye
                && verifyCorretLeftEye && verifyCorrectRightEye && verifyCorrectDoubleEye) {
            return true;
        }

        return false;
    }

    private void changeEditTextColor(EditText editText, boolean verify) {
        String content = editText.getText().toString();
        if (!CommonUtils.isEmpty(content) && !verify) {
            editText.setTextColor(getResources().getColor(R.color.train_status_disconnect_text_color));
        } else {
            editText.setTextColor(getResources().getColor(R.color.bleglasses_yanguang_input_text_color));
        }
    }


    private void bindGlassesInfo() {
        if (!checkNetworkAvaliable()) {
            return;
        }

        String userID = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(userID)) {
            Intent mIntent = new Intent(BindPersonalInfoSecondActivity.this, PhoneLoginActivity.class);
            startActivity(mIntent);
            finish();
            return;
        }
        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), userID);
        UserInfoDBBean userInfoDBBean = null;
        if (null != userInfoDBBeanList && userInfoDBBeanList.size() == 1) {
            userInfoDBBean = userInfoDBBeanList.get(0);
        }

        assert (null != userInfoDBBean);
        //SdLogUtil.writeCommonLog(" bindGlassesInfo :" + userInfoDBBean);

        String loginName = null;
        String phone = null;
        if (null != userInfoDBBean) {
            loginName = userInfoDBBean.getLogin_name();
            phone = userInfoDBBean.getPhone();
        }

        String leftEyeDegree = String.valueOf(bindPersonalInfoBean.getLeftEyeSight());//getLeftEyeSight();
        String rightEyeDegree = String.valueOf(bindPersonalInfoBean.getRightEyeSight());//getRightEyeSight();
        //String pupillaryDistance =  String.valueOf(eyeInfo[8]);
        String credentialStype = "0";//身份证类型 0身份证、1护照
        String authType = "0";//绑定认证类型，0-本人，1-监护人
        /**
         * 裸眼视力
         */
        String leftOD = String.valueOf(Float.parseFloat(getRightEyeSight()));
        String leftOS = String.valueOf(Float.parseFloat(getLeftEyeSight()));
        String leftOU = String.valueOf(Float.parseFloat(getDoubleEyeSight()));

        /**
         * 最佳视力
         */
        String rightOD = String.valueOf(Float.parseFloat(getRightEyeAstigmatism()));
        String rightOS = String.valueOf(Float.parseFloat(getLeftEyeAstigmatism()));
        String rightOU = String.valueOf(Float.parseFloat(getDoubleEyeAstigmatism()));

        /**
         * 散光
         */
        String right_astigmatism_degree = String.valueOf(bindPersonalInfoBean.getRightEyeAstigmatism());
        String left_astigmatism_degree = String.valueOf(bindPersonalInfoBean.getLeftEyeAstigmatism());


        final HashMap<String, String> headerMap = new HashMap<>(1);
        final HashMap<String, String> bodyMap = new HashMap<>();

        //bodyMap.put(IMemberBindDevice.VERSION_CODE, String.valueOf(AppUtils.getVersionCode()));
        bodyMap.put(IMemberBindDevice.NAME, bindPersonalInfoBean.getUserName());
        bodyMap.put(IMemberBindDevice.LOGIN_NAME, loginName);
        bodyMap.put(IMemberBindDevice.PHONE, phone);
        //bodyMap.put(IMemberBindDevice.GENDER_KEY, String.valueOf(bindPersonalInfoBean.getGender()));
        bodyMap.put(IMemberBindDevice.MEMBERID, userID);
        bodyMap.put(IMemberBindDevice.AUTHTYPE, authType);
        bodyMap.put(IMemberBindDevice.CREDENTIALSCARD, bindPersonalInfoBean.getIdNo());
        //bodyMap.put(IMemberBindDevice.BIRTHDAY, "");
        //bodyMap.put(IMemberBindDevice.DEVICEID, glassId);
        bodyMap.put(IMemberBindDevice.CREDENTIALSTYPE, credentialStype);
        bodyMap.put(IMemberBindDevice.DIOPTER_STATE, String.valueOf(bindPersonalInfoBean.getEyeStatus()));
        bodyMap.put(IMemberBindDevice.LEFT_EYE_DEGREE, leftEyeDegree);
        bodyMap.put(IMemberBindDevice.RIGHT_EYE_DEGREE, rightEyeDegree);
        //bodyMap.put(IMemberBindDevice.INTERPUPILLARY, pupillaryDistance);

        bodyMap.put(IMemberBindDevice.LEFT_OD_DEGREE, leftOD);
        bodyMap.put(IMemberBindDevice.LEFT_OS_DEGREE, leftOS);
        bodyMap.put(IMemberBindDevice.LEFT_OU_DEGREE, leftOU);

        bodyMap.put(IMemberBindDevice.RIGHT_OD_DEGREE, rightOD);
        bodyMap.put(IMemberBindDevice.RIGHT_OS_DEGREE, rightOS);
        bodyMap.put(IMemberBindDevice.RIGHT_OU_DEGREE, rightOU);
        bodyMap.put(IMemberBindDevice.INTERPUPILLARY, String.valueOf(bindPersonalInfoBean.getInterpuillaryDistance()));

        /**
         String BIRTHDAY = "birthday";//生日
         String PROVICE = "province";//省份编号
         String CITY = "city";//市编号
         String AREA = "area";//区编号
         String ADDRESS = "address";//地址
         String OD_CONTRAST_EYE_DEGREE = "contrast_right_eye_degree";
         String OS_CONTRAST_EYE_DEGREE = "contrast_left_eye_degree";
         String OU_CONTRAST_EYE_DEGREE = "contrast_binoculus_degree";
         */
        //bodyMap.put(IMemberBindDevice.BIRTHDAY, "");
   /*     bodyMap.put(IMemberBindDevice.PROVICE, String.valueOf(0));
        bodyMap.put(IMemberBindDevice.CITY, String.valueOf(0));
        bodyMap.put(IMemberBindDevice.AREA, String.valueOf(0));
        bodyMap.put(IMemberBindDevice.ADDRESS, String.valueOf(0));*/
        bodyMap.put(IMemberBindDevice.OD_CONTRAST_EYE_DEGREE, String.valueOf(0));
        bodyMap.put(IMemberBindDevice.OS_CONTRAST_EYE_DEGREE, String.valueOf(0));
        bodyMap.put(IMemberBindDevice.OU_CONTRAST_EYE_DEGREE, String.valueOf(0));
        bodyMap.put(IMemberBindDevice.ASTIGMATISM_DEGREE, String.valueOf(0));
        bodyMap.put(IMemberBindDevice.RIGHT_COLUMN_MIRROR, String.valueOf(0));
        bodyMap.put(IMemberBindDevice.LEFT_COLUMN_MIRROR, String.valueOf(0));


        bodyMap.put(IMemberBindDevice.LEFT_ASTIGMATISM_DEGREE, left_astigmatism_degree);
        bodyMap.put(IMemberBindDevice.RIGHT_ASTIGMATISM_DEGREE, right_astigmatism_degree);
        bodyMap.put(IMemberBindDevice.LEFT_AXIAL, String.valueOf(bindPersonalInfoBean.getLeftEyeAxialDirection()));
        bodyMap.put(IMemberBindDevice.RIGHT_AXIAL, String.valueOf(bindPersonalInfoBean.getRightEyeAxialDirection()));
        //眼镜序列号 2020-06-21 19:44:59
        bodyMap.put(IMemberBindDevice.GLASSES_SN, String.valueOf(bindPersonalInfoBean.getGlassesSN()));
        //性别
        bodyMap.put("sex", String.valueOf(bindPersonalInfoBean.getSex()));
        //年龄
        bodyMap.put("age", String.valueOf(bindPersonalInfoBean.getAge()));

        LogUtils.v("headerMap:" + headerMap);
        LogUtils.v("bodyMap:" + bodyMap);

        LoginSubscribe.bindGlassesInfo(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String newStringDataJson = null;
                JSONObject jsonObject;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                    jsonObject = JSONObject.parseObject(newStringDataJson);
                    if (!jsonObject.getString("status").equals("success")) {
                        ToastUtil.showShort(jsonObject.getString("message"));
                        return;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                saveUserId();
                //SdLogUtil.writeCommonLog("bindGlassesInfo newStringDataJson= " + newStringDataJson);
                MemberBindDeviceResponseBean memberBindDeviceResponseBean = (MemberBindDeviceResponseBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<MemberBindDeviceResponseBean>() {
                });
                //SdLogUtil.writeCommonLog("memberBindDeviceResponseBean = " + memberBindDeviceResponseBean.toString());
                startSureActivity(memberBindDeviceResponseBean, bodyMap);
                //MLog.d("newStringDataJson = " + newStringDataJson);
                //ToastDialogUtil.showShortToast(responseBody.toString());

                PayStateEventBean updateEventBean = new PayStateEventBean(PaymentUtil.PAYMENT_FAIL,3);
                EventBus.getDefault().post(updateEventBean);
            }

            @Override
            public void onError(Throwable e) {
                if (e.getMessage() != null) {
                    ToastUtil.showShort(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void saveUserId() {
        String gs = (String) SharedPreferencesUtils.get(Constants.USER_INFO, "");
        if (!TextUtils.isEmpty(gs)) {
            UseInfoBean infoBean = GsonUtil.GsonToBean(gs, UseInfoBean.class);
            infoBean.getData().setCredentialsCard(useInfoBean.getCredentialsCard());
            if (SharedPreferencesUtils.contains(Constants.USER_INFO)) {
                SharedPreferencesUtils.remove(Constants.USER_INFO);
            }
            SharedPreferencesUtils.put(Constants.USER_INFO, new Gson().toJson(infoBean));

        }

    }

    private void startSureActivity(MemberBindDeviceResponseBean memberBindDeviceResponseBean, HashMap<String, String> bodyMap) {
        if (null != memberBindDeviceResponseBean) {
            if (memberBindDeviceResponseBean.getStatus().equals(IMemberBindDevice.SUCCESS)) {
                ToastUtil.showShort("成功录入验光数据！");
                insertUserInfoEyeInfo2DB(memberBindDeviceResponseBean, bodyMap);
                Intent mIntent = new Intent(BindPersonalInfoSecondActivity.this, BleMainControlActivity.class);
                startActivity(mIntent);
                List<Class<?>> keepList = new ArrayList<>();
                keepList.add(BleMainControlActivity.class);
                ActivityStackUtil.getInstance().finishOthersActivity(keepList);
            } else if (memberBindDeviceResponseBean.getStatus().equals(IMemberBindDevice.ERROR)) {
                ToastUtil.showShort("录入失败！");
                Intent mIntent = new Intent(BindPersonalInfoSecondActivity.this, PhoneLoginActivity.class);
                startActivity(mIntent);
                List<Class<?>> keepList = new ArrayList<>();
                keepList.add(PhoneLoginActivity.class);
                ActivityStackUtil.getInstance().finishOthersActivity(keepList);
            }
        }
    }


    private void insertUserInfoEyeInfo2DB(MemberBindDeviceResponseBean memberBindDeviceResponseBean, HashMap<String, String> bodyMap) {
        String serverId = bodyMap.get(IMemberBindDevice.MEMBERID);
        if (CommonUtils.isEmpty(serverId)) {
            throw new NullPointerException(" serverId can not be null");
        }
        //MLog.d("queryAll.sizeof = " + UserInfoBeanDaoOpe.queryAll(MyApplication.getInstance()).size());
        List<UserInfoDBBean> userInfoBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), serverId);
        UserInfoDBBean userInfoDBBean = null;
        if (null != userInfoBeanList && userInfoBeanList.size() == 1) {
            userInfoDBBean = userInfoBeanList.get(0);
        } else {
            throw new NullPointerException(" userInfoDBBean can not be null");
        }
        MemberBindDeviceResponseBean.DataBean dataBean = memberBindDeviceResponseBean.getData();


        userInfoDBBean.setAge(dataBean.getAge());
        userInfoDBBean.setServerId(dataBean.getMemberId());
        userInfoDBBean.setLogin_name(bodyMap.get(IMemberBindDevice.LOGIN_NAME));
        userInfoDBBean.setName(bodyMap.get(IMemberBindDevice.NAME));
        userInfoDBBean.setCredentials_card(bodyMap.get(IMemberBindDevice.CREDENTIALSCARD));
        //userInfoDBBean.setBorn_date(memberBean.getBorn_date());
        //userInfoDBBean.setProvince(memberBean.getProvince());
        //userInfoDBBean.setCity(memberBean.getCity());
        //userInfoDBBean.setArea(memberBean.getArea());
        //userInfoDBBean.setBinding_time(memberBean.getBinding_time());
        //userInfoDBBean.setName(memberBean.getName());
        //userInfoDBBean.setLogin_name(memberBean.getLogin_name());

        userInfoDBBean.setLeft_eye_train_degree(Double.parseDouble(dataBean.getLeftHandleDegree()));
        userInfoDBBean.setRight_eye_train_degree(Double.parseDouble(dataBean.getRightHandleDegree()));

        //userInfoDBBean.setServerId(bodyMap.get(IMemberBindDevice.MEMBERID));
        userInfoDBBean.setAuthorType(Integer.parseInt(bodyMap.get(IMemberBindDevice.AUTHTYPE)));
        userInfoDBBean.setCredentials_card(bodyMap.get(IMemberBindDevice.CREDENTIALSCARD));
        userInfoDBBean.setAreaCode(dataBean.getAreaCode());
        //userInfoDBBean.setDevice_id(bodyMap.get(IMemberBindDevice.DEVICEID));
        userInfoDBBean.setCredentials_type(Integer.parseInt(bodyMap.get(IMemberBindDevice.CREDENTIALSTYPE)));
        userInfoDBBean.setDiopter_state(Integer.parseInt(bodyMap.get(IMemberBindDevice.DIOPTER_STATE)));
        userInfoDBBean.setLeft_eye_degree(Float.parseFloat(bodyMap.get(IMemberBindDevice.LEFT_EYE_DEGREE)));
        userInfoDBBean.setRight_eye_degree(Float.parseFloat(bodyMap.get(IMemberBindDevice.RIGHT_EYE_DEGREE)));
        userInfoDBBean.setInterpupillary(Float.parseFloat(bodyMap.get(IMemberBindDevice.INTERPUPILLARY)));
        userInfoDBBean.setNaked_right_eye_degree(bodyMap.get(IMemberBindDevice.LEFT_OD_DEGREE));
        userInfoDBBean.setNaked_left_eye_degree(bodyMap.get(IMemberBindDevice.LEFT_OS_DEGREE));
        userInfoDBBean.setNaked_binoculus_degree(bodyMap.get(IMemberBindDevice.LEFT_OU_DEGREE));

        userInfoDBBean.setCorrect_right_eye_degree(bodyMap.get(IMemberBindDevice.RIGHT_OD_DEGREE));
        userInfoDBBean.setCorrect_left_eye_degree(bodyMap.get(IMemberBindDevice.RIGHT_OS_DEGREE));
        userInfoDBBean.setCorrect_binoculus_degree(bodyMap.get(IMemberBindDevice.RIGHT_OU_DEGREE));
        //userInfoDBBean.setBorn_date(bodyMap.get(IMemberBindDevice.BIRTHDAY));
        /*userInfoDBBean.setContrast_right_eye_degree(bodyMap.get(IMemberBindDevice.OD_CONTRAST_EYE_DEGREE));
        userInfoDBBean.setContrast_left_eye_degree(bodyMap.get(IMemberBindDevice.OS_CONTRAST_EYE_DEGREE));
        userInfoDBBean.setContrast_binoculus_degree(bodyMap.get(IMemberBindDevice.OU_CONTRAST_EYE_DEGREE));*/
        userInfoDBBean.setLeft_astigmatism_degree(Float.parseFloat(!CommonUtils.isEmpty(bodyMap.get(IMemberBindDevice.LEFT_ASTIGMATISM_DEGREE)) ? bodyMap.get(IMemberBindDevice.LEFT_ASTIGMATISM_DEGREE) : "0"));
        userInfoDBBean.setRight_astigmatism_degree(Float.parseFloat(!CommonUtils.isEmpty(bodyMap.get(IMemberBindDevice.RIGHT_ASTIGMATISM_DEGREE)) ? bodyMap.get(IMemberBindDevice.RIGHT_ASTIGMATISM_DEGREE) : "0"));
        userInfoDBBean.setLeft_axial(Float.parseFloat(bodyMap.get(IMemberBindDevice.LEFT_AXIAL)));
        userInfoDBBean.setRight_axial(Float.parseFloat(bodyMap.get(IMemberBindDevice.RIGHT_AXIAL)));

        UserInfoBeanDaoOpe.insertOrReplaceData(MyApplication.getInstance(), userInfoDBBean);

        //SdLogUtil.writeCommonLog(" register_db :" + UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), Config.getConfig().getUserServerId()).get(0));
    }


    @Override
    protected void onDestroy() {

        Gson gson = new Gson();

        String data = (String) SharedPreferencesUtils.get(Constants.SP_OPTOMETRYDATA, "");
        BindPersonalInfoBean personalInfoBean = gson.fromJson(data, BindPersonalInfoBean.class);
        if (personalInfoBean == null) personalInfoBean = new BindPersonalInfoBean();
        personalInfoBean.setNakedLeftEyeDegree(getLeftEyeSight());
        personalInfoBean.setNakedRightEyeDegree(getRightEyeSight());
        personalInfoBean.setNakedBinoculusDegree(getDoubleEyeSight());

        personalInfoBean.setCorrectRightEyeDegree(getRightEyeAstigmatism());
        personalInfoBean.setCorrectLeftEyeDegree(getLeftEyeAstigmatism());
        personalInfoBean.setCorrectBinoculusDegree(getDoubleEyeAstigmatism());

        SharedPreferencesUtils.remove(Constants.SP_OPTOMETRYDATA);
        SharedPreferencesUtils.put(Constants.SP_OPTOMETRYDATA, gson.toJson(personalInfoBean));

        super.onDestroy();
        editTextList.clear();
    }
}
