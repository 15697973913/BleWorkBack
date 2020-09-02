package com.zj.zhijue.activity.bindglasses;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.ActivityStackUtil;
import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.EyeAstigmatismDegreeInputFilter;
import com.android.common.baselibrary.util.EyeAxialDegreeInputFilter;
import com.android.common.baselibrary.util.EyeFloatDegreeInputFilter;
import com.android.common.baselibrary.util.EyeSignedNumberDegreeInputFilter;
import com.android.common.baselibrary.util.ToastUtil;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.zhijue.BuildConfig;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.BleMainControlActivity;
import com.zj.zhijue.activity.login.PhoneLoginActivity;
import com.zj.zhijue.adapter.SexAdapter;
import com.zj.zhijue.base.BaseBleActivity;
import com.zj.zhijue.bean.MemberBindDeviceResponseBean;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IMemberBindDevice;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.jsonutil.JsonUtil;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * 输入视力信息
 */

public class BindPersonalInfoScrollViewActivity extends BaseBleActivity {

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView titleTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLayout;

    @BindView(R.id.usernametextview)
    AppCompatEditText userNameEditTextView;

    @BindView(R.id.sexspinner)
    AppCompatSpinner sexSpinner;

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


    @BindView(R.id.leftnakedeyerefraction)//裸眼视力（左眼）
    AppCompatEditText leftNakedEyeRefractionEditText;

    @BindView(R.id.rightnakedeyerefraction)//裸眼视力（右眼）
    AppCompatEditText rightNakedEyeRefractionEditText;

    @BindView(R.id.doublenakedeyerefraction)//裸眼视力（双眼）
    AppCompatEditText doubleNakedEyeRefractionEditText;

    @BindView(R.id.leftcorrecteyeastigmatism)//矫正视力（左眼）
    AppCompatEditText leftCorrectEyeAstigmation;

    @BindView(R.id.rightcorrecteyeastigmatism)//矫正视力（右眼）
    AppCompatEditText rightCorrectEyeAstigmation;

    @BindView(R.id.twocorrecteyeastigmatism)//矫正视力（双眼）
    AppCompatEditText doubleCorrectEyeAstigMationEditText;

    @BindView(R.id.register_complete_button)
    AppCompatButton registeButton;

    private List<EditText> editTextList = new ArrayList<>();
    private List<EditText> bottomEditTextList = new ArrayList<>();
    private int sexSelectedSpinnerIndex = -1;
    private Pattern pattern = Pattern.compile("[^0-9\\-]");
    private final String TAG = BindPersonalInfoScrollViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setContentView(R.layout.activity_bingglasseswithidno_scrollview);
        super.onCreate(bundle);
        initData();
        initView();
        initListener();

    }

    private void initData() {
        editTextList.clear();
        editTextList.add(userNameEditTextView);
        editTextList.add(birthdayDateeEditText);
        editTextList.add(leftEyeRefractionEditText);
        editTextList.add(rightEyeRefractionEditText);
        editTextList.add(leftEyeAstigmation);
        editTextList.add(rightEyeAstigmation);
        editTextList.add(leftEyeAxialeEidtText);
        editTextList.add(rightEyeAxialeEidtText);
        editTextList.add(interpuillaryDistanceEditText);

        bottomEditTextList.clear();
        bottomEditTextList.add(leftNakedEyeRefractionEditText);
        bottomEditTextList.add(rightNakedEyeRefractionEditText);
        bottomEditTextList.add(doubleNakedEyeRefractionEditText);
        bottomEditTextList.add(leftCorrectEyeAstigmation);
        bottomEditTextList.add(rightCorrectEyeAstigmation);
        bottomEditTextList.add(doubleCorrectEyeAstigMationEditText);
    }

    private void initView() {
        //StatusBarCompat.setImmersiveStatusBar(true, mResource.getColor(R.color.main_background_color), this);
        titleTextView.setText("验光数据");
        eyeStatusRadioGroup.check(shortSightedNessRadioButton.getId());
        judgeEyeStatus(eyeStatusRadioGroup.getCheckedRadioButtonId());

        SexAdapter sexAdapter = new SexAdapter(this, Arrays.asList(getResources().getStringArray(R.array.sex)));
        sexSpinner.setAdapter(sexAdapter);
        sexSpinner.setSelection(0);
        sexSelectedSpinnerIndex = 0;
        sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //ToastUtil.showShortToast("position = " + position);
                sexSelectedSpinnerIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initListener() {
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        eyeStatusRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                judgeEyeStatus(i);
            }
        });

        for (final EditText editText: editTextList) {
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


        registeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allInput = allEditTextInput();
                boolean allBottomInput = allBotttomEditTextInput();
                if (!allInput || !allBottomInput) {
                    ToastUtil.showShort(R.string.person_info_no_complete_text);
                    return;
                }
                if (inputAllRight()) {
                    bindGlassesInfo();
                } else {
                    ToastUtil.showShort(R.string.person_info_no_right_text);
                }
            }
        });
        InputFilter[] filters = {new EyeFloatDegreeInputFilter()};

        for (final EditText editText : bottomEditTextList) {
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
                                editText.setText("4.");
                                editText.setSelection(2);
                            } else {
                                Integer integer = Integer.parseInt(content);
                                if (integer < 4) {
                                    editText.setText("4.");
                                    editText.setSelection(2);
                                } else if ( integer > 5) {
                                    editText.setText("5.");
                                    editText.setSelection(2);
                                }
                            }
                        }
                    }
                }
            });
        }


        InputFilter[] filters2 = {new EyeSignedNumberDegreeInputFilter()};
        leftEyeRefractionEditText.setFilters(filters2);
        rightEyeRefractionEditText.setFilters(filters2);

        InputFilter[] filters3 = {new EyeAstigmatismDegreeInputFilter()};
        leftEyeAstigmation.setFilters(filters3);
        rightEyeAstigmation.setFilters(filters3);

        InputFilter[] filters4 = {new EyeAxialDegreeInputFilter()};
        leftEyeAxialeEidtText.setFilters(filters4);
        rightEyeAxialeEidtText.setFilters(filters4);
    }

    private int judgeEyeStatus(int viewID) {
        int i = -1;
        if (viewID == shortSightedNessRadioButton.getId()) {
            ToastUtil.showShort("近视");
            i = 1;
        } else if (viewID == presbyopiaSightedNessRadioButton.getId()) {
            ToastUtil.showShort("老花");
            i = 2;
        } else if (viewID == longSightedNessRadioButton.getId()) {
            ToastUtil.showShort("远视");
            i = 3;
        } else if (viewID == weakSightedNessRadioButton.getId()) {
            ToastUtil.showShort("弱视");
            i = 4;
        }
        return i;
    }

    private String getUserName() {
        return userNameEditTextView.getText().toString();
    }

    /**
     * 获取性别 索引
     * @return
     */
    private String getGender() {
        return String.valueOf(sexSpinner.getSelectedItemPosition());
    }

    private String getBirthDayDate() {
        return birthdayDateeEditText.getText().toString();
    }

    /**
     * 获取视力状态 近视，远视，老花，弱视
     * @return
     */
    private String  getEyeSightStatus() {
        int eyeSightStatus = judgeEyeStatus(eyeStatusRadioGroup.getCheckedRadioButtonId());
        MLog.d("getEyeSightStatus = " + eyeSightStatus);
        return String.valueOf(eyeSightStatus);
    }

    /**
     * 左眼度数
     * @return
     */
    private String getLeftEyeSight() {
        return leftEyeRefractionEditText.getText().toString();
    }

    /**
     * 右眼度数
     * @return
     */
    private String getRightEyeSight() {
        return rightEyeRefractionEditText.getText().toString();
    }

    /**
     * 左眼散光度数
     * @return
     */
    private String getLeftEyeAstigmatism() {
        return  leftEyeAstigmation.getText().toString();
    }

    /**
     * 右眼散光度数
     * @return
     */
    private String getRightEyeAstigmatism() {
        return rightEyeAstigmation.getText().toString();
    }

    /**
     * 左眼轴向
     * @return
     */
    private String getLeftEyeAxialDirection() {
        return leftEyeAxialeEidtText.getText().toString();
    }

    /**
     * 右眼轴向
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
        String birdthDayDate = getBirthDayDate();

        if (!CommonUtils.isEmpty(birdthDayDate)) {
            boolean match = isIDNumber(birdthDayDate);
            return match;
        }
        return false;
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
            if (eyeStatus >=0 && eyeStatus <= 3) {
                return true;
            }
        }
        return false;
    }
    private boolean verifyLeftEyeSight() {
        String leftEyeSight = getLeftEyeSight();
        if (!CommonUtils.isEmpty(leftEyeSight) && Math.abs(Float.parseFloat(leftEyeSight)) <= 700) {
            return true;
        }
        return false;
    }

    private boolean verifyRightEyeSight() {
        String rightEyeSight = getRightEyeSight();
        if (!CommonUtils.isEmpty(rightEyeSight) && Math.abs(Float.parseFloat(rightEyeSight)) <= 700) {
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
        if (!CommonUtils.isEmpty(leftEyeAxialDirection) && (defualtValue = Float.parseFloat(leftEyeAxialDirection)) >= 0 && defualtValue <=360 ) {
            return true;
        }
        return false;
    }

    private boolean verifyRightEyeAxialDirection() {
        String rightEyeAxialDirection = getRightEyeAxialDirection();
        float defualtValue = -1;
        if (!CommonUtils.isEmpty(rightEyeAxialDirection) && (defualtValue = Float.parseFloat(rightEyeAxialDirection)) >= 0 && defualtValue <=360 ) {
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
        for (EditText editText: editTextList) {
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
            MLog.d(TAG + "inputAllRight verifyUserName = " + verifyUserName +  "\n" +
                    "verifyIDNo = "  + verifyBirthDayDate + "\n" +
                    "verifyEyeStatus = "  + verifyEyeStatus + "\n" +
                    "verifyLeftEyeSight = "  + verifyLeftEyeSight + "\n" +
                    "verifyRightEyeSight = "  + verifyRightEyeSight + "\n" +
                    "verifyLefyEyeAstigmatism = "  + verifyLefyEyeAstigmatism + "\n" +
                    "verifyRightEyeAstigmatism = "  + verifyRightEyeAstigmatism + "\n" +
                    "verifyLeftEyeAxial = "  + verifyLeftEyeAxial + "\n" +
                    "verifyRightEyeAxial = "  + verifyRightEyeAxial + "\n" +
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

            if (inputBottomAllRight()) {
                return true;
            }
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


    /**
     * 裸眼视力（左眼）
     *
     * @return
     */
    private String getNakedLeftEyeSight() {
        return leftNakedEyeRefractionEditText.getText().toString();
    }

    /**
     * 裸眼视力（右眼）
     *
     * @return
     */
    private String getNakedRightEyeSight() {
        return rightNakedEyeRefractionEditText.getText().toString();
    }

    /**
     * 裸眼视力（双眼）
     *
     * @return
     */
    private String getNakedDoubleEyeSight() {
        return doubleNakedEyeRefractionEditText.getText().toString();
    }

    /**
     * 矫正视力（左眼）
     *
     * @return
     */
    private String getLeftCorrectEyeAstigmatism() {
        return leftCorrectEyeAstigmation.getText().toString();
    }

    /**
     * 矫正视力（右眼）
     *
     * @return
     */
    private String getRightCorrectEyeAstigmatism() {
        return rightCorrectEyeAstigmation.getText().toString();
    }

    /**
     * 矫正视力（双眼）
     *
     * @return
     */
    private String getDoubleCorrectEyeAstigmatism() {
        return doubleCorrectEyeAstigMationEditText.getText().toString();
    }

    private boolean verifyValue(String editTextValue) {
        Float eyeSight = Float.parseFloat(editTextValue);
        if (eyeSight >= 4.0f && eyeSight <= 5.3f) {
            return true;
        }
        return false;
    }

    private boolean verifyNakedLeftEyeSight() {
        String leftEyeSight = getNakedLeftEyeSight();
        if (!CommonUtils.isEmpty(leftEyeSight) && verifyValue(leftEyeSight)) {
            return true;
        }
        return false;
    }

    private boolean verifyNakedRightEyeSight() {
        String rightEyeSight = getNakedRightEyeSight();
        if (!CommonUtils.isEmpty(rightEyeSight) && verifyValue(rightEyeSight)) {
            return true;
        }
        return false;
    }

    private boolean verifyNakedDoubleEyeSight() {
        String doubleEyeSight = getNakedDoubleEyeSight();
        if (!CommonUtils.isEmpty(doubleEyeSight) && verifyValue(doubleEyeSight)) {
            return true;
        }
        return false;
    }

    private boolean verifyCorrectLefyEyeAstigmatism() {
        String leftEyeAstigmatism = getLeftCorrectEyeAstigmatism();
        if (!CommonUtils.isEmpty(leftEyeAstigmatism) && verifyValue(leftEyeAstigmatism)) {
            return true;
        }
        return false;
    }

    private boolean verifyCorrectRightEyeAstigmatism() {
        String rightEyeAstigmatism = getRightCorrectEyeAstigmatism();
        if (!CommonUtils.isEmpty(rightEyeAstigmatism) && verifyValue(rightEyeAstigmatism)) {
            return true;
        }
        return false;
    }

    private boolean verifyDoubleEyeAstigmationSm() {
        String doubleEyeAstigmatism = getDoubleCorrectEyeAstigmatism();
        if (!CommonUtils.isEmpty(doubleEyeAstigmatism) && verifyValue(doubleEyeAstigmatism)) {
            return true;
        }
        return false;
    }

    private boolean allBotttomEditTextInput() {
        for (EditText editText: bottomEditTextList) {
            String content = editText.getText().toString();
            if (CommonUtils.isEmpty(content)) {
                return false;
            }
        }
        return true;
    }

    private boolean inputBottomAllRight() {
        boolean verifyNakedLeftEye = verifyNakedLeftEyeSight();
        changeEditTextColor(leftNakedEyeRefractionEditText, verifyNakedLeftEye);

        boolean verifyNakeyRightEye = verifyNakedRightEyeSight();
        changeEditTextColor(rightNakedEyeRefractionEditText, verifyNakeyRightEye);

        boolean verifyNakeDoubleEye = verifyNakedDoubleEyeSight();
        changeEditTextColor(doubleNakedEyeRefractionEditText, verifyNakeDoubleEye);

        boolean verifyCorretLeftEye = verifyCorrectLefyEyeAstigmatism();
        changeEditTextColor(leftCorrectEyeAstigmation, verifyCorretLeftEye);

        boolean verifyCorrectRightEye = verifyCorrectRightEyeAstigmatism();
        changeEditTextColor(rightCorrectEyeAstigmation, verifyCorrectRightEye);

        boolean verifyCorrectDoubleEye = verifyDoubleEyeAstigmationSm();
        changeEditTextColor(doubleCorrectEyeAstigMationEditText, verifyCorrectDoubleEye);

        if (verifyNakedLeftEye && verifyNakeyRightEye && verifyNakeDoubleEye
                && verifyCorretLeftEye && verifyCorrectRightEye && verifyCorrectDoubleEye) {
            return true;
        }

        return false;
    }

    private void bindGlassesInfo() {

        String userID = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(userID)) {
            Intent mIntent = new Intent(BindPersonalInfoScrollViewActivity.this, PhoneLoginActivity.class);
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

        String leftEyeDegree = String.valueOf(Float.parseFloat(getLeftEyeSight()));
        String rightEyeDegree = String.valueOf(Float.parseFloat(getRightEyeSight()));
        //String pupillaryDistance =  String.valueOf(eyeInfo[8]);
        String credentialStype = "0";//身份证类型 0身份证、1护照
        String authType = "0";//绑定认证类型，0-本人，1-监护人
        /**
         * 裸眼视力
         */
        String leftOD = String.valueOf(Float.parseFloat(getNakedRightEyeSight()));
        String leftOS = String.valueOf(Float.parseFloat(getNakedLeftEyeSight()));
        String leftOU = String.valueOf(Float.parseFloat(getNakedDoubleEyeSight()));

        /**
         * 最佳视力
         */
        String rightOD = String.valueOf(Float.parseFloat(getRightCorrectEyeAstigmatism()));
        String rightOS = String.valueOf(Float.parseFloat(getLeftCorrectEyeAstigmatism()));
        String rightOU = String.valueOf(Float.parseFloat(getDoubleCorrectEyeAstigmatism()));

        /**
         * 散光
         */
        String right_astigmatism_degree = getRightEyeAstigmatism();
        String left_astigmatism_degree = getLeftEyeAstigmatism();


        final HashMap<String, String> headerMap = new HashMap<>(1);
        final HashMap<String, String> bodyMap = new HashMap<>();

        //bodyMap.put(IMemberBindDevice.VERSION_CODE, String.valueOf(AppUtils.getVersionCode()));
        bodyMap.put(IMemberBindDevice.NAME, getUserName());
        bodyMap.put(IMemberBindDevice.LOGIN_NAME, loginName);
        bodyMap.put(IMemberBindDevice.PHONE, phone);
        //bodyMap.put(IMemberBindDevice.GENDER_KEY, String.valueOf(bindPersonalInfoBean.getGender()));
        bodyMap.put(IMemberBindDevice.MEMBERID, userID);
        bodyMap.put(IMemberBindDevice.AUTHTYPE, authType);
        bodyMap.put(IMemberBindDevice.CREDENTIALSCARD, getBirthDayDate());
        //bodyMap.put(IMemberBindDevice.BIRTHDAY, "");
        //bodyMap.put(IMemberBindDevice.DEVICEID, glassId);
        bodyMap.put(IMemberBindDevice.CREDENTIALSTYPE, credentialStype);
        bodyMap.put(IMemberBindDevice.DIOPTER_STATE, getEyeSightStatus());
        bodyMap.put(IMemberBindDevice.LEFT_EYE_DEGREE, leftEyeDegree);
        bodyMap.put(IMemberBindDevice.RIGHT_EYE_DEGREE, rightEyeDegree);
        //bodyMap.put(IMemberBindDevice.INTERPUPILLARY, pupillaryDistance);

        bodyMap.put(IMemberBindDevice.LEFT_OD_DEGREE, leftOD);
        bodyMap.put(IMemberBindDevice.LEFT_OS_DEGREE, leftOS);
        bodyMap.put(IMemberBindDevice.LEFT_OU_DEGREE, leftOU);

        bodyMap.put(IMemberBindDevice.RIGHT_OD_DEGREE, rightOD);
        bodyMap.put(IMemberBindDevice.RIGHT_OS_DEGREE, rightOS);
        bodyMap.put(IMemberBindDevice.RIGHT_OU_DEGREE, rightOU);
        bodyMap.put(IMemberBindDevice.INTERPUPILLARY, getInterpuillaryDistanceText());

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
        bodyMap.put(IMemberBindDevice.LEFT_AXIAL, getLeftEyeAxialDirection());
        bodyMap.put(IMemberBindDevice.RIGHT_AXIAL, getRightEyeAxialDirection());
/*
        if (1 != 1) {
            Set<Map.Entry<String, String>> entries = bodyMap.entrySet();
            Iterator<Map.Entry<String, String>>  iterator = entries.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                Map.Entry<String, String> mapEntry = iterator.next();
                String key = mapEntry.getKey();
                String value = mapEntry.getValue();
                MLog.d(TAG + "[" + i + "]" + " key = " + key + "   value = " + value);
                i++;
            }
            return;
        }*/

        LoginSubscribe.bindGlassesInfo(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //SdLogUtil.writeCommonLog("bindGlassesInfo newStringDataJson= " + newStringDataJson);
                MemberBindDeviceResponseBean memberBindDeviceResponseBean = (MemberBindDeviceResponseBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<MemberBindDeviceResponseBean>() {
                });
                //SdLogUtil.writeCommonLog("memberBindDeviceResponseBean = " + memberBindDeviceResponseBean.toString());
                startSureActivity(memberBindDeviceResponseBean, bodyMap);
                //MLog.d("newStringDataJson = " + newStringDataJson);
                //ToastDialogUtil.showShortToast(responseBody.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void startSureActivity(MemberBindDeviceResponseBean memberBindDeviceResponseBean, HashMap<String, String> bodyMap) {
        if (null != memberBindDeviceResponseBean) {
            if (memberBindDeviceResponseBean.getStatus().equals(IMemberBindDevice.SUCCESS)) {
                ToastUtil.showShort("成功录入验光数据！");
                insertUserInfoEyeInfo2DB(memberBindDeviceResponseBean, bodyMap);
                Intent mIntent = new Intent(BindPersonalInfoScrollViewActivity.this, BleMainControlActivity.class);
                startActivity(mIntent);
                List<Class<?>> keepList = new ArrayList<>();
                keepList.add(BleMainControlActivity.class);
                ActivityStackUtil.getInstance().finishOthersActivity(keepList);
            } else if (memberBindDeviceResponseBean.getStatus().equals(IMemberBindDevice.ERROR)) {
                ToastUtil.showShort("录入失败！");
                Intent mIntent = new Intent(BindPersonalInfoScrollViewActivity.this, PhoneLoginActivity.class);
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
        userInfoDBBean.setAreaCode(bodyMap.get(IMemberBindDevice.AREACODE));
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
        super.onDestroy();
        editTextList.clear();
    }
}
