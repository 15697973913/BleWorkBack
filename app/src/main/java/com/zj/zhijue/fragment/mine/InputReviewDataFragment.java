package com.zj.zhijue.fragment.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.DateUtil;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.http.request.IInputReviewData;
import com.zj.zhijue.http.response.HttpResponseInputReviewDataBean;
import com.zj.zhijue.model.AppUpdateModel;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.jsonutil.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * 录入复查数据
 */
public class InputReviewDataFragment extends BaseFragment {
    @BindView(R.id.function_item_title_tv)
    TextView functionItemTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout functionItemBackLayout;

    @BindView(R.id.inputdatausernametextview)
    AppCompatEditText inputDataUserNameEditTv;

    @BindView(R.id.inputdatabirthdaydatetextview)
    AppCompatEditText inputDataUserBirthDayEditTv;

    @BindView(R.id.inputdatamobilephonetextview)
    AppCompatEditText inputDataUserPhoneEditTv;

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

    @BindView(R.id.inputdatalefteyerefraction)//左眼度数
    AppCompatEditText inputDataLeftEyeDegreeEditTv;

    @BindView(R.id.inputdatarighteyerefraction)//右眼度数
    AppCompatEditText inputDatarightEyeDegreeEditTv;

    @BindView(R.id.inputdatalefteyeastigmatism)//左眼散光
    AppCompatEditText inputDataleftEyeAstigmatismEditTv;

    @BindView(R.id.inputdatalefteyeaxialeidttext)//左眼轴向
    AppCompatEditText inputDataLeftEyeAxialEditTv;

    @BindView(R.id.inputdatarighteyeastigmatism)//右眼散光
    AppCompatEditText inputDataRightEyeAstigmatismEditTv;

    @BindView(R.id.inputdatarighteyeaxialeidttext)//右眼轴向
    AppCompatEditText inputDataRightEyeAxialEditTv;

    @BindView(R.id.inputdatadoubleeyeastigmatismeidttext)
    AppCompatEditText inputDataDoubleEyeAstigmatismEditTv;//双眼散光

    @BindView(R.id.inputdataleftColumnMirror)
    AppCompatEditText inputDataLeftColumnMirror;//左眼柱镜

    @BindView(R.id.inputdatarightColumnMirror)
    AppCompatEditText inputDataRightColumnMirror;//右眼柱镜

    @BindView(R.id.inputdataleftnaked)
    AppCompatEditText inputDataLeftNaked;//左眼裸眼视力

    @BindView(R.id.inputdatarightnaked)
    AppCompatEditText inputDataRightNaked;//右眼裸眼视力

    @BindView(R.id.inputdatadoublenaked)
    AppCompatEditText inputDataDoubleNaked;//双眼裸眼视力

    @BindView(R.id.inputdataleftcorrect)
    AppCompatEditText inputDataLeftCorrect;//左眼矫正视力

    @BindView(R.id.inputdatarightcorrect)
    AppCompatEditText inputDataRightCorrect;//右眼矫正视力

    @BindView(R.id.inputdatadoublecorrect)
    AppCompatEditText inputDataDoubleCorrect;//双眼矫正视力

    @BindView(R.id.inputdatadoublenumber)
    AppCompatEditText inputDataDoubleAddNum;//双眼提升行数

    @BindView(R.id.inputdataremark)
    AppCompatEditText inputDataRemark;//备注

    @BindView(R.id.inputdata_complete_button)
    AppCompatButton inputCompleteBtn;

    private List<DisposableObserver> httpDispList = new ArrayList<>();
    private List<EditText> editTextList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUpdateModel.getInstance().setContentNull();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_review_data_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void initView(View view) {
        functionItemTextView.setText(getResources().getText(R.string.review_input_data_text));
        eyeStatusRadioGroup.check(shortSightedNessRadioButton.getId());
        judgeEyeStatus(eyeStatusRadioGroup.getCheckedRadioButtonId());
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

    private void initData() {
        //editTextList.add(inputDataUserNameEditTv);
        editTextList.add(inputDataUserPhoneEditTv);

        editTextList.add(inputDataLeftEyeDegreeEditTv);
        editTextList.add(inputDatarightEyeDegreeEditTv);

        editTextList.add(inputDataleftEyeAstigmatismEditTv);
        editTextList.add(inputDataLeftEyeAxialEditTv);
        editTextList.add(inputDataRightEyeAstigmatismEditTv);
        editTextList.add(inputDataRightEyeAxialEditTv);
        editTextList.add(inputDataDoubleEyeAstigmatismEditTv);

        editTextList.add(inputDataLeftColumnMirror);
        editTextList.add(inputDataRightColumnMirror);

        editTextList.add(inputDataLeftNaked);
        editTextList.add(inputDataRightNaked);
        editTextList.add(inputDataDoubleNaked);

        editTextList.add(inputDataLeftCorrect);
        editTextList.add(inputDataRightCorrect);
        editTextList.add(inputDataDoubleCorrect);

        editTextList.add(inputDataDoubleAddNum);
        editTextList.add(inputDataRemark);
    }

    @Override
    protected void initListener() {
        inputCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verityAllInputRight()) {
                    postInputReviewData();
                } else {
                    ToastUtil.showShort("信息输入不完整!");
                }
            }
        });

        functionItemBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        eyeStatusRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                judgeEyeStatus(i);
            }
        });

        for (EditText editText: editTextList) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    changeEditTextColor(editText, true);
                }
            });
        }

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

    private void postInputReviewData() {
        String serverId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(serverId)) {
            BaseActivity.GotoLoginActivity();
            return;
        }

        if (!checkNetworkAvaliable()) {
            return;
        }

        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryRawUserInfoByServerID(getActivity(), serverId);
        UserInfoDBBean userInfoDBBean = userInfoDBBeanList.get(0);

        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        //bodyMap.put(IInputReviewData.USERNAME, getUserNameText());
        bodyMap.put(IInputReviewData.leftEyeDegree, getLeftEyeDegreeText());
        bodyMap.put(IInputReviewData.rightEyeDegree, getRightEyeDegreeText());
        bodyMap.put(IInputReviewData.leftAstigmatismDegree, getLeftEyeAstigmatismText());
        bodyMap.put(IInputReviewData.rightAstigmatismDegree, getRightEyeAstigmatismText());
        bodyMap.put(IInputReviewData.astigmatismDegree, getDoubleEyeAstigmatismeiText());
        bodyMap.put(IInputReviewData.rightColumnMirror, getRightEyeColumonMirrorText());
        bodyMap.put(IInputReviewData.leftColumnMirror, getLeftEyeColumonMirrorText());
        bodyMap.put(IInputReviewData.rightAxial, getRightEyeAxialText());
        bodyMap.put(IInputReviewData.leftAxial, getLeftEyeAxialText());
        bodyMap.put(IInputReviewData.nakedLeftEyeDegree, getLeftEyeNakedText());
        bodyMap.put(IInputReviewData.nakedRightEyeDegree, getRightEyeNakedText());
        bodyMap.put(IInputReviewData.nakedBinoculusDegree, getDoubleEyeNakedText());
        bodyMap.put(IInputReviewData.correctLeftEyeDegree, getLeftEyeCorrectText());
        bodyMap.put(IInputReviewData.correctRightEyeDegree, getRightEyeCorrectText());
        bodyMap.put(IInputReviewData.correctBinoculusDegree, getDoubleEyeCorrectText());
        bodyMap.put(IInputReviewData.binoculusPromoteNumber, getDoubleAddNumText());
        bodyMap.put(IInputReviewData.reviewDate, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
        bodyMap.put(IInputReviewData.remarks, getRemarkText());
        bodyMap.put(IInputReviewData.diopterState, getEyeSightStatus());
        bodyMap.put(IInputReviewData.createBy, userInfoDBBean.getLogin_name());

      /*  Set<Map.Entry<String, Object>>  entrySet = bodyMap.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            MLog.d("[" + i + "]" + " key = " + key + " value = " + value);
            i++;
        }*/

        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                httpDispList.remove(this);

                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                HttpResponseInputReviewDataBean httpResponseInputReviewDataBean = null;
                if (!CommonUtils.isEmpty(newStringDataJson)) {
                    httpResponseInputReviewDataBean = (HttpResponseInputReviewDataBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseInputReviewDataBean>() {
                    });

                    if (null != httpResponseInputReviewDataBean && httpResponseInputReviewDataBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                        ToastUtil.showLong("提交成功!");
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                httpDispList.remove(this);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
        httpDispList.add(disposableObserver);
        TrainSuscribe.postInputReviewData(headerMap, bodyMap, disposableObserver);
    }


    private String getUserNameText() {
        return inputDataUserNameEditTv.getText().toString();
    }

    private String getUserBirtDayText() {
        return inputDataUserBirthDayEditTv.getText().toString();
    }

    private String getMobilePhoneText(){
       return inputDataUserPhoneEditTv.getText().toString();
    }

    private String getLeftEyeDegreeText() {
        return inputDataLeftEyeDegreeEditTv.getText().toString();
    }

    private String getRightEyeDegreeText() {
        return inputDatarightEyeDegreeEditTv.getText().toString();
    }

    private String getLeftEyeAstigmatismText() {
        return inputDataleftEyeAstigmatismEditTv.getText().toString();
    }

    private String getRightEyeAstigmatismText() {
        return inputDataRightEyeAstigmatismEditTv.getText().toString();
    }

    private String getLeftEyeAxialText() {
        return inputDataLeftEyeAxialEditTv.getText().toString();
    }

    private String getRightEyeAxialText() {
        return inputDataRightEyeAxialEditTv.getText().toString();
    }

    //双眼散光
    private String getDoubleEyeAstigmatismeiText() {
        return inputDataDoubleEyeAstigmatismEditTv.getText().toString();
    }

    //左眼柱镜
    private String getLeftEyeColumonMirrorText() {
        return inputDataLeftColumnMirror.getText().toString();
    }

    //右眼柱镜
    private String getRightEyeColumonMirrorText() {
        return inputDataRightColumnMirror.getText().toString();
    }

    //左眼裸眼视力
    private String getLeftEyeNakedText() {
        return inputDataLeftNaked.getText().toString();
    }

    //右眼裸眼视力
    private String getRightEyeNakedText() {
        return inputDataRightNaked.getText().toString();
    }

    //双眼裸眼视力
    private String getDoubleEyeNakedText() {
        return inputDataDoubleNaked.getText().toString();
    }

    //左眼矫正视力
    private String getLeftEyeCorrectText() {
        return inputDataLeftCorrect.getText().toString();
    }

    //右眼矫正视力
    private String getRightEyeCorrectText() {
        return inputDataRightCorrect.getText().toString();
    }

    //双眼矫正视力
    private String getDoubleEyeCorrectText() {
        return inputDataDoubleCorrect.getText().toString();
    }

    //双眼提升行数
    private String getDoubleAddNumText() {
        return inputDataDoubleAddNum.getText().toString();
    }

    //备注
    private String getRemarkText() {
        return inputDataRemark.getText().toString();
    }

    private boolean verifyUserName() {
        String userName = getUserNameText();
        if (!CommonUtils.isEmpty(userName)) {
            return true;
        }
        return false;
    }

    private boolean verifyUserPhone() {
        String phoneText = getMobilePhoneText();
        if (!CommonUtils.isEmpty(phoneText) && phoneText.length() == 11) {
            return true;
        }
        return false;
    }

    private boolean verifyBirdDay() {
        String birdDayText = getUserBirtDayText();
        if (!CommonUtils.isEmpty(birdDayText)) {
            return true;
        }
        return false;
    }

    private boolean verifyLeftEyeDegree() {
        String leftEyeDegree = getLeftEyeDegreeText();
        if (!CommonUtils.isEmpty(leftEyeDegree)) {
            return true;
        }
        return false;
    }

    private boolean verifyRightEyeDegree() {
        String leftEyeDegree = getRightEyeDegreeText();
        if (!CommonUtils.isEmpty(leftEyeDegree)) {
            return true;
        }
        return false;
    }

    private boolean verifyLeftEyeAstigmatism() {
        String leftEyeAstigmatism = getLeftEyeAstigmatismText();
        if (!CommonUtils.isEmpty(leftEyeAstigmatism)) {
            return true;
        }
        return false;
    }

    private boolean verifyRightEyeAstigmatism() {
        String rightEyeAstigmatism = getRightEyeAstigmatismText();
        if (!CommonUtils.isEmpty(rightEyeAstigmatism)) {
            return true;
        }
        return false;
    }

    private boolean verifyLeftEyeAxial() {
        String leftEyeAxial = getLeftEyeAxialText();
        if (!CommonUtils.isEmpty(leftEyeAxial)) {
            return true;
        }
        return false;
    }

    private boolean verifyRightEyeAxial() {
        String rightEyeAxial = getRightEyeAxialText();
        if (!CommonUtils.isEmpty(rightEyeAxial)) {
            return true;
        }
        return false;
    }

    //双眼散光
    private boolean verifyDoubleEyeAstigmatismei() {
        String doubleEyeAstigmatismei = getDoubleEyeAstigmatismeiText();
        if (!CommonUtils.isEmpty(doubleEyeAstigmatismei)) {
            return true;
        }
        return false;
    }

    //左眼柱镜
    private boolean verifyLeftEyeColumonMirror() {
        String leftEyeColumonMirror = getLeftEyeColumonMirrorText();
        if (!CommonUtils.isEmpty(leftEyeColumonMirror)) {
            return true;
        }
        return false;
    }

    //右眼柱镜
    private boolean verifyRightEyeColumonMirror() {
        String rightEyeColumonMirror = getRightEyeColumonMirrorText();
        if (!CommonUtils.isEmpty(rightEyeColumonMirror)) {
            return true;
        }
        return false;
    }

    //左眼裸眼视力
    private boolean verifyLeftEyeNaked() {
        String leftEyeNaked = getLeftEyeNakedText();
        if (!CommonUtils.isEmpty(leftEyeNaked)) {
            return true;
        }
        return false;
    }

    //右眼裸眼视力
    private boolean verifyRightEyeNaked() {
        String rightEyeNaked = getRightEyeNakedText();
        if (!CommonUtils.isEmpty(rightEyeNaked)) {
            return true;
        }
        return false;
    }

    //双眼裸眼视力
    private boolean verifyDoubleEyeNaked() {
        String doubleEyeNaked = getDoubleEyeNakedText();
        if (!CommonUtils.isEmpty(doubleEyeNaked)) {
            return true;
        }
        return false;
    }

    //左眼矫正视力
    private boolean verifyLeftEyeCorrect() {
        String leftEyeCorrect = getLeftEyeCorrectText();
        if (!CommonUtils.isEmpty(leftEyeCorrect)) {
            return true;
        }
        return false;
    }

    //右眼矫正视力
    private boolean verifyRightEyeCorrect() {
        String rightEyeCorrect = getRightEyeCorrectText();
        if (!CommonUtils.isEmpty(rightEyeCorrect)) {
            return true;
        }
        return false;
    }

    //右眼矫正视力
    private boolean verifyDoubleEyeCorrect() {
        String doubleEyeCorrect = getDoubleEyeCorrectText();
        if (!CommonUtils.isEmpty(doubleEyeCorrect)) {
            return true;
        }
        return false;
    }

    //双眼提升行数
    private boolean verifyDoubleAddNum() {
        String doubleEyeCorrect = getDoubleAddNumText();
        if (!CommonUtils.isEmpty(doubleEyeCorrect)) {
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

    private boolean verityAllInputRight() {
        //boolean userNameRight = verifyUserName();
        //changeEditTextColor(inputDataUserNameEditTv, userNameRight);
        //boolean userBirthDayRight = verifyBirdDay();
        boolean userPhoneRight = verifyUserPhone();
        changeEditTextColor(inputDataUserPhoneEditTv, userPhoneRight);

        boolean leftEyeRight = verifyLeftEyeDegree();
        changeEditTextColor(inputDataLeftEyeDegreeEditTv, leftEyeRight);
        boolean rightEyeRight = verifyRightEyeDegree();
        changeEditTextColor(inputDatarightEyeDegreeEditTv, rightEyeRight);

        boolean leftEyeAstigmtismRight = verifyLeftEyeAstigmatism();
        changeEditTextColor(inputDataleftEyeAstigmatismEditTv, leftEyeAstigmtismRight);

        boolean rightEyeAstigmtismRight = verifyRightEyeAstigmatism();
        changeEditTextColor(inputDataRightEyeAstigmatismEditTv, rightEyeAstigmtismRight);

        boolean leftEyeAxiaRight = verifyLeftEyeAxial();
        changeEditTextColor(inputDataLeftEyeAxialEditTv, leftEyeAxiaRight);
        boolean rightEyeAxiaRight = verifyRightEyeAxial();
        changeEditTextColor(inputDataRightEyeAxialEditTv, rightEyeAxiaRight);

        boolean doubleEyeAstigmRight = verifyDoubleEyeAstigmatismei();
        changeEditTextColor(inputDataDoubleEyeAstigmatismEditTv, doubleEyeAstigmRight);

        boolean leftEyeAstigmRight = verifyLeftEyeColumonMirror();
        changeEditTextColor(inputDataLeftColumnMirror, leftEyeAstigmRight);
        boolean rightEyeAstigmRight = verifyRightEyeColumonMirror();
        changeEditTextColor(inputDataRightColumnMirror, rightEyeAstigmRight);

        boolean leftEyeNakedRight = verifyLeftEyeNaked();
        changeEditTextColor(inputDataLeftNaked, leftEyeNakedRight);

        boolean rightEyeNakedRight = verifyRightEyeNaked();
        changeEditTextColor(inputDataRightNaked, rightEyeNakedRight);

        boolean doubleEyeNakedRight = verifyDoubleEyeNaked();
        changeEditTextColor(inputDataDoubleNaked, doubleEyeNakedRight);

        boolean leftEyeCorrectRight = verifyLeftEyeCorrect();
        changeEditTextColor(inputDataLeftCorrect, leftEyeCorrectRight);

        boolean rightEyeCorrectRight = verifyRightEyeCorrect();
        changeEditTextColor(inputDataRightCorrect, rightEyeCorrectRight);

        boolean doubleEyeCorrectRight = verifyDoubleEyeCorrect();
        changeEditTextColor(inputDataDoubleCorrect, doubleEyeCorrectRight);

        boolean doubleAddNumRight = verifyDoubleAddNum();
        changeEditTextColor(inputDataDoubleAddNum, doubleAddNumRight);

        if ( userPhoneRight
            && leftEyeRight && rightEyeRight
            && leftEyeAstigmtismRight && rightEyeAstigmtismRight
            && leftEyeAxiaRight && rightEyeAxiaRight
            && doubleEyeAstigmRight && leftEyeAstigmRight
            && rightEyeAstigmRight && leftEyeNakedRight
            && rightEyeNakedRight && doubleEyeNakedRight
            && leftEyeCorrectRight && rightEyeCorrectRight
            && doubleEyeCorrectRight && doubleAddNumRight) {
            return true;
        }

        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposeObserverable();
    }

    private void disposeObserverable() {
        for (DisposableObserver disposableObserver: httpDispList) {
            if (!disposableObserver.isDisposed()) {
                disposableObserver.dispose();
            }
        }
        httpDispList.clear();
    }
}
