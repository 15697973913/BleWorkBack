package com.zj.zhijue.fragment.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.http.request.IInputReviewData;
import com.zj.zhijue.http.response.HttpResponseInputOtherDataBean;
import com.zj.zhijue.model.AppUpdateModel;
import com.zj.zhijue.util.jsonutil.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * 录入其他数据
 */
public class InputOtherDataFragment extends BaseFragment {
    @BindView(R.id.function_item_title_tv)
    TextView functionItemTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout functionItemBackLayout;

    @BindView(R.id.inputotherdatausernametextview)
    AppCompatEditText inputOtherDataUserNameEditTv;

    @BindView(R.id.inputotherdatabirthdaydatetextview)
    AppCompatEditText inputOtherDataUserBirthDayEditTv;

    @BindView(R.id.inputotherdatamobilephonetextview)
    AppCompatEditText inputOtherDataUserPhoneEditTv;

    @BindView(R.id.inputotherdatalefteyerefraction)//左眼度数
    AppCompatEditText inputOtherDataLeftEyeDegreeEditTv;

    @BindView(R.id.inputotherdatarighteyerefraction)//右眼度数
    AppCompatEditText inputOtherDatarightEyeDegreeEditTv;

    @BindView(R.id.inputotherdatalefteyeastigmatism)//左眼散光
    AppCompatEditText inputOtherDataleftEyeAstigmatismEditTv;

    @BindView(R.id.inputotherdatalefteyeaxialeidttext)//左眼轴向
    AppCompatEditText inputOtherDataLeftEyeAxialEditTv;

    @BindView(R.id.inputotherdatarighteyeastigmatism)//右眼散光
    AppCompatEditText inputOtherDataRightEyeAstigmatismEditTv;

    @BindView(R.id.inputotherdatarighteyeaxialeidttext)//右眼轴向
    AppCompatEditText inputOtherDataRightEyeAxialEditTv;

    @BindView(R.id.inputotherdata_complete_button)
    AppCompatButton inputCompleteBtn;

    private List<DisposableObserver> httpDispList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUpdateModel.getInstance().setContentNull();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_other_data_layout, container, false);
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
        functionItemTextView.setText(getResources().getText(R.string.input_other_data_text));
    }

    private void initData() {

    }


    @Override
    protected void initListener() {
        inputCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verityAllInputRight()) {
                    postInputOtherData();
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
    }

    private void postInputOtherData() {
        HashMap<String, Object> headerMap = new HashMap<>();

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IInputReviewData.USERNAME, getUserNameText());

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

                HttpResponseInputOtherDataBean httpResponseInputOtherDataBean = null;
                if (!CommonUtils.isEmpty(newStringDataJson)) {
                    httpResponseInputOtherDataBean = (HttpResponseInputOtherDataBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseInputOtherDataBean>() {
                    });
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
        TrainSuscribe.bindDevice(headerMap, bodyMap, disposableObserver);
    }


    private String getUserNameText() {
        return inputOtherDataUserNameEditTv.getText().toString();
    }

    private String getUserBirtDayText() {
        return inputOtherDataUserBirthDayEditTv.getText().toString();
    }

    private String getMobilePhoneText(){
        return inputOtherDataUserPhoneEditTv.getText().toString();
    }

    private String getLeftEyeDegreeText() {
        return inputOtherDataLeftEyeDegreeEditTv.getText().toString();
    }

    private String getRightEyeDegreeText() {
        return inputOtherDatarightEyeDegreeEditTv.getText().toString();
    }

    private String getLeftEyeAstigmatismText() {
        return inputOtherDataleftEyeAstigmatismEditTv.getText().toString();
    }

    private String getRightEyeAstigmatismText() {
        return inputOtherDataRightEyeAstigmatismEditTv.getText().toString();
    }

    private String getLeftEyeAxialText() {
        return inputOtherDataLeftEyeAxialEditTv.getText().toString();
    }

    private String getRightEyeAxialText() {
        return inputOtherDataRightEyeAxialEditTv.getText().toString();
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

    private boolean verityAllInputRight() {
        boolean userNameRight = verifyUserName();
        boolean userBirthDayRight = verifyBirdDay();
        boolean userPhoneRight = verifyUserPhone();

        boolean leftEyeRight = verifyLeftEyeDegree();
        boolean rightEyeRight = verifyRightEyeDegree();

        boolean leftEyeAstigmtismRight = verifyLeftEyeAstigmatism();
        boolean rightEyeAstigmtismRight = verifyRightEyeAstigmatism();

        boolean leftEyeAxiaRight = verifyLeftEyeAxial();
        boolean rightEyeAxiaRight = verifyRightEyeAxial();

        if (userNameRight && userBirthDayRight && userPhoneRight
                && leftEyeRight && rightEyeRight
                && leftEyeAstigmtismRight && rightEyeAstigmtismRight
                && leftEyeAxiaRight && rightEyeAxiaRight) {
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
