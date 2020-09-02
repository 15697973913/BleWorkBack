package com.zj.zhijue.activity.bindglasses;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.EyeAstigmatismDegreeInputFilter;
import com.android.common.baselibrary.util.EyeAxialDegreeInputFilter;
import com.android.common.baselibrary.util.EyeSignedNumberDegreeInputFilter;
import com.android.common.baselibrary.util.ToastUtil;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.huige.library.utils.SharedPreferencesUtils;
import com.pnikosis.materialishprogress.BuildConfig;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zj.zhijue.R;
import com.zj.zhijue.adapter.SexAdapter;
import com.zj.zhijue.base.BaseBleActivity;
import com.zj.zhijue.bean.BindPersonalInfoBean;
import com.zj.zhijue.bean.UseInfoBean;
import com.zj.zhijue.config.Constants;
import com.zj.zhijue.dialog.SexSelectDialog;
import com.zj.zhijue.util.CheckIdCardUtils;
import com.zj.zhijue.util.MyStringUtils;
import com.zj.zhijue.util.MyUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;

import static com.blankj.utilcode.util.StringUtils.isEmpty;

/**
 * 输入视力信息
 */

public class BindPersonalInfoActivity extends BaseBleActivity {

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView titleTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLayout;

    @BindView(R.id.usernametextview)
    AppCompatEditText userNameEditTextView;


    @BindView(R.id.birthdaydatetextview)
    AppCompatEditText birthdayDateeEditText;

    @BindView(R.id.eyestatusradiogroup)
    RadioGroup eyeStatusRadioGroup;

    @BindView(R.id.shortsightednesssradiobutton)
    RadioButton shortSightedNessRadioButton;

    @BindView(R.id.longsightednesssradiobutton)
    RadioButton longSightedNessRadioButton;

    @BindView(R.id.presbyopiasightednesssradiobutton)
    RadioButton presbyopiaSightedNessRadioButton;//老花

    @BindView(R.id.weaksightednesssradiobutton)
    RadioButton weakSightedNessRadioButton;

    @BindView(R.id.lefteyerefraction)
    AppCompatEditText leftEyeRefractionEditText;

    @BindView(R.id.righteyerefraction)
    AppCompatEditText rightEyeRefractionEditText;

    @BindView(R.id.lefteyeastigmatism)
    AppCompatEditText leftEyeAstigmation;

    @BindView(R.id.righteyeastigmatism)
    AppCompatEditText rightEyeAstigmation;

    @BindView(R.id.lefteyeaxialeidttext)
    AppCompatEditText leftEyeAxialeEidtText;

    @BindView(R.id.righteyeaxialeidttext)
    AppCompatEditText rightEyeAxialeEidtText;

    @BindView(R.id.interpupillarydistanceedittext)
    AppCompatEditText interpuillaryDistanceEditText;//瞳距

    @BindView(R.id.register_next_page_button)
    AppCompatButton registeNextPageButton;

    @BindView(R.id.etGlassesSN)
    AppCompatEditText etGlassesSN;//眼镜序列号
    @BindView(R.id.ivQRScan)
    ImageView ivQRScan;//扫描眼镜
    @BindView(R.id.etAge)
    AppCompatEditText etAge;
    @BindView(R.id.sexEditText)
    EditText sexEditText;

    private List<EditText> editTextList = new ArrayList<>();
    private Pattern pattern = Pattern.compile("[^0-9\\-]");
    private final String TAG = BindPersonalInfoActivity.class.getSimpleName();

    //用户信息-数据回填用
    private UseInfoBean.UseBean useInfoBean;
    private SexAdapter sexAdapter;
    private SexSelectDialog mSexSelectDialog;


    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setContentView(R.layout.activity_bingglasseswithidno);
        super.onCreate(bundle);
        initData();
        initView();
        initEvent();
        initListener();

        ivQRScan.setColorFilter(Color.parseColor("#999999"));
        useInfoBean = getIntent().getParcelableExtra("useInfoBean");

        setDefaultData();
    }

    private void initEvent() {
        birthdayDateeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (birthdayDateeEditText.length() == 18 && (useInfoBean == null || useInfoBean.getAge() == 0)) {
                    etAge.setText(MyUtil.getAgeByPsptNo(editable.toString()));
                }
            }
        });
    }

    /**
     * 设置默认的数据回填
     */
    private void setDefaultData() {
        if (useInfoBean == null || (useInfoBean.getLeftEyeDegree() == 0
                && useInfoBean.getRightEyeDegree() == 0
                && useInfoBean.getLeftAstigmatismDegree() == 0
                && useInfoBean.getLeftAxial() == 0)) {

            showHistoryData();
            return;
        }

        userNameEditTextView.setText(useInfoBean.getName());
        birthdayDateeEditText.setText(useInfoBean.getCredentialsCard());
        if (useInfoBean.getSex() == 0 || useInfoBean.getSex() == 1) {
            mSexSelectDialog.setSelection(useInfoBean.getSex());
        }
        sexAdapter.notifyDataSetChanged();


        sexEditText.setText(useInfoBean.getSex() == 0 ? "女" : "男");

        etAge.setText(String.valueOf(useInfoBean.getAge()));
//        etGlassesSN.setText(useInfoBean.getCl());
        showEyeStatus(useInfoBean.getDiopterState());

        leftEyeRefractionEditText.setText(String.valueOf((int) useInfoBean.getLeftEyeDegree()));
        rightEyeRefractionEditText.setText(String.valueOf((int) useInfoBean.getRightEyeDegree()));
        leftEyeAstigmation.setText(String.valueOf((int) useInfoBean.getLeftAstigmatismDegree()));
        leftEyeAxialeEidtText.setText(String.valueOf((int) useInfoBean.getLeftAxial()));
        rightEyeAstigmation.setText(String.valueOf((int) useInfoBean.getRightAstigmatismDegree()));
        rightEyeAxialeEidtText.setText(String.valueOf((int) useInfoBean.getRightAxial()));
        interpuillaryDistanceEditText.setText(String.valueOf((int) useInfoBean.getInterpupillary()));
    }

    /**
     * 显示缓存未提交的数据
     */
    private void showHistoryData() {
        Gson gson = new Gson();
        String data = (String) SharedPreferencesUtils.get(Constants.SP_OPTOMETRYDATA, "");
        BindPersonalInfoBean personalInfoBean = gson.fromJson(data, BindPersonalInfoBean.class);
        if (personalInfoBean == null) {
            return;
        }

        userNameEditTextView.setText(personalInfoBean.getUserName());
        birthdayDateeEditText.setText(personalInfoBean.getIdNo());
        sexAdapter.notifyDataSetChanged();

        sexEditText.setText(personalInfoBean.getSex() == 0 ? "女" : "男");

        etAge.setText(String.valueOf(personalInfoBean.getAge()));
        etGlassesSN.setText(personalInfoBean.getGlassesSN());

        if (personalInfoBean.getSex() == 0 || personalInfoBean.getSex() == 1) {
            mSexSelectDialog.setSelection(personalInfoBean.getSex());
        }

        showEyeStatus(personalInfoBean.getEyeStatus());

        leftEyeRefractionEditText.setText(MyStringUtils.zeroToEmptyStr((int) personalInfoBean.getLeftEyeSight()));
        rightEyeRefractionEditText.setText(MyStringUtils.zeroToEmptyStr((int) personalInfoBean.getRightEyeSight()));

        leftEyeAstigmation.setText(MyStringUtils.zeroToEmptyStr((int) personalInfoBean.getLeftEyeAstigmatism()));
        leftEyeAxialeEidtText.setText(MyStringUtils.zeroToEmptyStr((int) personalInfoBean.getLeftEyeAxialDirection()));
        rightEyeAstigmation.setText(MyStringUtils.zeroToEmptyStr((int) personalInfoBean.getRightEyeAstigmatism()));
        rightEyeAxialeEidtText.setText(MyStringUtils.zeroToEmptyStr((int) personalInfoBean.getRightEyeAxialDirection()));
        interpuillaryDistanceEditText.setText(MyStringUtils.zeroToEmptyStr((int) personalInfoBean.getInterpuillaryDistance()));
    }


    private void initData() {
        editTextList.clear();
        editTextList.add(userNameEditTextView);
//        editTextList.add(birthdayDateeEditText);
        //editTextList.add(leftEyeRefractionEditText);
        //editTextList.add(rightEyeRefractionEditText);
        editTextList.add(leftEyeAstigmation);
        editTextList.add(rightEyeAstigmation);
        editTextList.add(leftEyeAxialeEidtText);
        editTextList.add(rightEyeAxialeEidtText);
        editTextList.add(interpuillaryDistanceEditText);
    }

    private void initView() {

        //StatusBarCompat.setImmersiveStatusBar(true, mResource.getColor(R.color.main_background_color), this);
        titleTextView.setText("验光数据");
        eyeStatusRadioGroup.check(shortSightedNessRadioButton.getId());
        judgeEyeStatus(eyeStatusRadioGroup.getCheckedRadioButtonId());

        sexAdapter = new SexAdapter(this, Arrays.asList(getResources().getStringArray(R.array.sex)));

        //跳转扫描眼镜
        ivQRScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(
                        BindPersonalInfoActivity.this,
                        CaptureActivity.class), BLE_SCAN_QRCODE_REQUEST_CODE);

            }
        });


        mSexSelectDialog = new SexSelectDialog(BindPersonalInfoActivity.this,
                new SexSelectDialog.OnSelectedListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        sexEditText.setText(selectedIndex == 0 ? "女" : "男");
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //MLog.d(BleMainControlActivity.class.getSimpleName() + "onActivityResult  requestCode = " + requestCode + "  resultCode = " + resultCode);
        if (resultCode == CaptureActivity.CAPATURE_RESULT_CODE) {
            if (requestCode == BLE_SCAN_QRCODE_REQUEST_CODE) {
                Bundle bundle = data.getExtras();
                if (null != bundle) {
                    int resultType = bundle.getInt(CodeUtils.RESULT_TYPE);
                    if (resultType == CodeUtils.RESULT_SUCCESS) {
                        String glassesSN = bundle.getString(CodeUtils.RESULT_STRING);
                        //ToastUtil.showShortToast("解析数据为：" + macStr);
                        MLog.d("glassesSN = " + glassesSN);
                        etGlassesSN.setText(String.valueOf(glassesSN));
                    } else if (resultType == CodeUtils.RESULT_FAILED) {
                        ToastUtil.showShort("解析二维码失败");
                    }
                }
            }

        }
    }


    private void initListener() {
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.llSex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSexSelectDialog.show();
            }
        });
        sexEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSexSelectDialog.show();
            }
        });


        registeNextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allInput = allEditTextInput();
                if (!allInput) {
                    ToastUtil.showShort(R.string.person_info_no_complete_text);
                    return;
                }
                if (inputAllRight()) {
                    //年龄判断
                    if (isEmpty(etAge.getText().toString())) {
                        ToastUtils.showShort("请输入年龄");
                        return;
                    }

                    //判断身份证、
                    if (!ObjectUtils.isEmpty(getBirthDayDate())) {
                        if (!CheckIdCardUtils.check(getBirthDayDate())) {
                            ToastUtils.showShort("请输入正确身份证");
                            return;
                        }
                    }


                    putIntentData();
                }
            }
        });

        eyeStatusRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                judgeEyeStatus(i);
            }
        });

        for (final EditText editText : editTextList) {
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
                }
            });
        }

        leftEyeRefractionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeEditTextColor(leftEyeRefractionEditText, true);

            }
        });

        rightEyeRefractionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeEditTextColor(rightEyeRefractionEditText, true);
            }
        });

        InputFilter[] filters = {new EyeSignedNumberDegreeInputFilter()};
        leftEyeRefractionEditText.setFilters(filters);
        rightEyeRefractionEditText.setFilters(filters);

        InputFilter[] filters2 = {new EyeAstigmatismDegreeInputFilter()};
        leftEyeAstigmation.setFilters(filters2);
        rightEyeAstigmation.setFilters(filters2);

        InputFilter[] filters3 = {new EyeAxialDegreeInputFilter()};
        leftEyeAxialeEidtText.setFilters(filters3);
        rightEyeAxialeEidtText.setFilters(filters3);
    }

    private void showEyeStatus(int state) {
        switch (state) {
            case 1:
                shortSightedNessRadioButton.setChecked(true);
                break;
            case 2:
                presbyopiaSightedNessRadioButton.setChecked(true);
                break;
            case 3:
                longSightedNessRadioButton.setChecked(true);
                break;
            case 4:
                weakSightedNessRadioButton.setChecked(true);
                break;
            default:
                shortSightedNessRadioButton.setChecked(true);
                break;
        }
    }

    private int judgeEyeStatus(int viewID) {
        int i = -1;
        if (viewID == shortSightedNessRadioButton.getId()) {
            if (BuildConfig.DEBUG) {
                ToastUtil.showShort("近视");
            }
            i = 1;
        } else if (viewID == presbyopiaSightedNessRadioButton.getId()) {
            if (BuildConfig.DEBUG) {
                ToastUtil.showShort("老花");
            }
            i = 2;
        } else if (viewID == longSightedNessRadioButton.getId()) {
            if (BuildConfig.DEBUG) {
                ToastUtil.showShort("远视");
            }
            i = 3;
        } else if (viewID == weakSightedNessRadioButton.getId()) {
            if (BuildConfig.DEBUG) {
                ToastUtil.showShort("弱视");
            }
            i = 4;
        }
        return i;
    }

    private String getUserName() {
        return userNameEditTextView.getText().toString();
    }

    /**
     * 获取性别 索引
     *
     * @return
     */
    private String getGender() {
        return String.valueOf(mSexSelectDialog.getSelectedItemPosition());
    }

    /**
     * 获取身份证
     *
     * @return 身份证
     */
    private String getBirthDayDate() {
        return birthdayDateeEditText.getText().toString();
    }

    /**
     * 获取视力状态 近视，远视，老花，弱视
     *
     * @return
     */
    private String getEyeSightStatus() {
        int eyeSightStatus = judgeEyeStatus(eyeStatusRadioGroup.getCheckedRadioButtonId());
        MLog.d("getEyeSightStatus = " + eyeSightStatus);
        return String.valueOf(eyeSightStatus);
    }

    /**
     * 左眼度数
     *
     * @return
     */
    private String getLeftEyeSight() {
        return leftEyeRefractionEditText.getText().toString().isEmpty() ? "0" : leftEyeRefractionEditText.getText().toString();
    }

    /**
     * 右眼度数
     *
     * @return
     */
    private String getRightEyeSight() {
        return rightEyeRefractionEditText.getText().toString();
    }

    /**
     * 左眼散光度数
     *
     * @return
     */
    private String getLeftEyeAstigmatism() {
        return leftEyeAstigmation.getText().toString();
    }

    /**
     * 右眼散光度数
     *
     * @return
     */
    private String getRightEyeAstigmatism() {
        return rightEyeAstigmation.getText().toString();
    }

    /**
     * 左眼轴向
     *
     * @return
     */
    private String getLeftEyeAxialDirection() {
        return leftEyeAxialeEidtText.getText().toString();
    }

    /**
     * 右眼轴向
     *
     * @return
     */
    private String getRightEyeAxialDirection() {
        return rightEyeAxialeEidtText.getText().toString();
    }

    private String getInterpuillaryDistanceText() {
        return interpuillaryDistanceEditText.getText().toString();
    }

    private boolean verifyUserNameText() {
        String userName = getUserName();
        if (!CommonUtils.isEmpty(userName)) {
            return true;
        }
        return false;
    }

    private boolean verifyGender() {
        String gender = getGender();
        if (!CommonUtils.isEmpty(gender) &&
                (gender.equals("0")
                        || gender.equals("1"))) {
            return true;
        }
        return false;
    }

    private boolean verifyIDNo() {
//        String birdthDayDate = getBirthDayDate();
//
//        if (!CommonUtils.isEmpty(birdthDayDate)) {
//            boolean match = isIDNumber(birdthDayDate);
//            return match;
//        }
        return true;
    }

    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        MLog.d("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    MLog.d("异常:" + IDNumber);
                    return false;
                }
            }

        }
        return matches;
    }

    private boolean verifyEyeSightStatus() {
        String eyeSightStatus = getEyeSightStatus();
        if (!CommonUtils.isEmpty(eyeSightStatus)) {
            int eyeStatus = Integer.parseInt(eyeSightStatus);
            return eyeStatus >= 0 && eyeStatus <= 4;
        }
        return false;
    }

    private boolean verifyLeftEyeSight() {
        String leftEyeSight = getLeftEyeSight();

        float defualtValue;

        if (!CommonUtils.isEmpty(leftEyeSight) && (defualtValue = Float.parseFloat(leftEyeSight)) >= 0 && defualtValue <= 1200){
            return true;
        }
        return false;
    }

    private boolean verifyRightEyeSight() {
        String rightEyeSight = getRightEyeSight();
        float defualtValue;

        if (!CommonUtils.isEmpty(rightEyeSight) && (defualtValue = Float.parseFloat(rightEyeSight)) >= 0 && defualtValue <= 1200) {
            return true;
        }
        return false;
    }

    private boolean verifyLefyEyeAstigmatism() {
        String leftEyeAstigmatism = getLeftEyeAstigmatism();
        if (!CommonUtils.isEmpty(leftEyeAstigmatism)) {
            return true;
        }
        return false;
    }

    private boolean verifyRightEyeAstigmatism() {
        String rightEyeAstigmatism = getRightEyeAstigmatism();
        if (!CommonUtils.isEmpty(rightEyeAstigmatism)) {
            return true;
        }
        return false;
    }

    private boolean verifyLeftEyeAxialDirection() {
        String leftEyeAxialDirection = getLeftEyeAxialDirection();
        float defualtValue = -1;
        if (!CommonUtils.isEmpty(leftEyeAxialDirection) && (defualtValue = Float.parseFloat(leftEyeAxialDirection)) >= 0 && defualtValue <= 360) {
            return true;
        }
        return false;
    }

    private boolean verifyRightEyeAxialDirection() {
        String rightEyeAxialDirection = getRightEyeAxialDirection();
        float defualtValue = -1;
        if (!CommonUtils.isEmpty(rightEyeAxialDirection) && (defualtValue = Float.parseFloat(rightEyeAxialDirection)) >= 0 && defualtValue <= 360) {
            return true;
        }
        return false;
    }

    private boolean verifyInterpuillaryDistance() {
        String interpuillaryDistance = getInterpuillaryDistanceText();
        if (!CommonUtils.isEmpty(interpuillaryDistance)) {
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
        boolean verifyUserName = verifyUserNameText();
        changeEditTextColor(userNameEditTextView, verifyUserName);

        //boolean verifyGender = verifyGender();

        boolean verifyBirthDayDate = verifyIDNo();
        changeEditTextColor(birthdayDateeEditText, verifyBirthDayDate);

        boolean verifyEyeStatus = verifyEyeSightStatus();
        if (!verifyEyeStatus) {
            ToastUtils.showShort("请选择眼睛状况");
        }

        boolean verifyLeftEyeSight = verifyLeftEyeSight();
        changeEditTextColor(leftEyeRefractionEditText, verifyLeftEyeSight);

        boolean verifyRightEyeSight = verifyRightEyeSight();
        changeEditTextColor(rightEyeRefractionEditText, verifyRightEyeSight);

        boolean verifyLefyEyeAstigmatism = verifyLefyEyeAstigmatism();
        changeEditTextColor(leftEyeAstigmation, verifyLefyEyeAstigmatism);

        boolean verifyRightEyeAstigmatism = verifyRightEyeAstigmatism();
        changeEditTextColor(rightEyeAstigmation, verifyRightEyeAstigmatism);

        boolean verifyLeftEyeAxial = verifyLeftEyeAxialDirection();
        changeEditTextColor(leftEyeAxialeEidtText, verifyLeftEyeAxial);

        boolean verifyRightEyeAxial = verifyRightEyeAxialDirection();
        changeEditTextColor(rightEyeAxialeEidtText, verifyRightEyeAxial);

        boolean verifyInterpuillaryDistance = verifyInterpuillaryDistance();
        changeEditTextColor(interpuillaryDistanceEditText, verifyInterpuillaryDistance);

        if (BuildConfig.DEBUG) {
            MLog.d(TAG + "inputAllRight verifyUserName = " + verifyUserName + "\n" +
                    "verifyIDNo = " + verifyBirthDayDate + "\n" +
                    "verifyEyeStatus = " + verifyEyeStatus + "\n" +
                    "verifyLeftEyeSight = " + verifyLeftEyeSight + "\n" +
                    "verifyRightEyeSight = " + verifyRightEyeSight + "\n" +
                    "verifyLefyEyeAstigmatism = " + verifyLefyEyeAstigmatism + "\n" +
                    "verifyRightEyeAstigmatism = " + verifyRightEyeAstigmatism + "\n" +
                    "verifyLeftEyeAxial = " + verifyLeftEyeAxial + "\n" +
                    "verifyRightEyeAxial = " + verifyRightEyeAxial + "\n" +
                    "verifyInterpuillaryDistance = " + verifyInterpuillaryDistance + "\n");
        }

        if (verifyUserName && verifyBirthDayDate
                && verifyEyeStatus
                && verifyLeftEyeSight
                && verifyRightEyeSight
                && verifyLefyEyeAstigmatism
                && verifyRightEyeAstigmatism
                && verifyLeftEyeAxial
                && verifyRightEyeAxial
                && verifyInterpuillaryDistance) {
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

    private void putIntentData() {
        Intent mIntent = new Intent(BindPersonalInfoActivity.this, BindPersonalInfoSecondActivity.class);


        mIntent.putExtra(BindPersonalInfoSecondActivity.BIND_PERSONAL_EYE_INFO_KEY, getBindPersonalData());
        mIntent.putExtra("useInfoBean", useInfoBean);
        startActivity(mIntent);

    }

    public BindPersonalInfoBean getBindPersonalData() {
        BindPersonalInfoBean bindPersonalInfoBean = new BindPersonalInfoBean();
        bindPersonalInfoBean.setUserName(getUserName());
        //bindPersonalInfoBean.setGender(Integer.parseInt(getGender()));
        bindPersonalInfoBean.setIdNo(getBirthDayDate());
        bindPersonalInfoBean.setSex(Integer.parseInt(getGender()));

        bindPersonalInfoBean.setEyeStatus(MyStringUtils.emptyStrToZero(getEyeSightStatus()));

        bindPersonalInfoBean.setLeftEyeSight(Math.abs(MyStringUtils.emptyStrToZero(getLeftEyeSight())));

        bindPersonalInfoBean.setRightEyeSight(Math.abs(MyStringUtils.emptyStrToZero(getRightEyeSight())));


        bindPersonalInfoBean.setLeftEyeAstigmatism(MyStringUtils.emptyStrToZero(getLeftEyeAstigmatism()));

        bindPersonalInfoBean.setRightEyeAstigmatism(MyStringUtils.emptyStrToZero(getRightEyeAstigmatism()));

        bindPersonalInfoBean.setLeftEyeAxialDirection(MyStringUtils.emptyStrToZero(getLeftEyeAxialDirection()));

        bindPersonalInfoBean.setRightEyeAxialDirection(MyStringUtils.emptyStrToZero(getRightEyeAxialDirection()));

        bindPersonalInfoBean.setInterpuillaryDistance(MyStringUtils.emptyStrToZero(getInterpuillaryDistanceText()));
        bindPersonalInfoBean.setGlassesSN(etGlassesSN.getText().toString());
        bindPersonalInfoBean.setAge(com.zj.zhijue.util.CommonUtils.getInt(etAge.getText().toString()));

        return bindPersonalInfoBean;
    }


    @Override
    protected void onDestroy() {
        Gson gson = new Gson();
        if (SharedPreferencesUtils.contains(Constants.SP_OPTOMETRYDATA)) {
            SharedPreferencesUtils.remove(Constants.SP_OPTOMETRYDATA);
        }
        SharedPreferencesUtils.put(Constants.SP_OPTOMETRYDATA, gson.toJson(getBindPersonalData()));


        super.onDestroy();
        editTextList.clear();
    }
}
