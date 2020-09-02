package com.zj.zhijue.fragment.feedback;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.alibaba.fastjson.TypeReference;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.fragment.mine.FeedBackFragment;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IAddFeedBack;
import com.zj.zhijue.http.response.HttpResponseAddFeedBackBean;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * 填写意见界面
 */
public class FeedbackInputFragment extends BaseFragment {

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLinearLayout;

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView titleTexView;

   /* @BindView(R.id.adviselayout)
    LinearLayout adviseLinearLayout;*/

    @BindView(R.id.adviseinputlayout)
    LinearLayout adviseInputLayout;

    @BindView(R.id.adviseinputscrollview)
    ScrollView adviseScrollView;

    @BindView(R.id.advisetitleedt)
    AppCompatEditText adviseTitleEdit;

    @BindView(R.id.adviseedittext)
    AppCompatEditText adviseInputEidtText;

    @BindView(R.id.advisesubmitbtn)
    Button adviseButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_wait_for_replyed_layout, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        titleTexView.setText("意见反馈");
    }

    private void initData() {

    }

    @Override
    protected void initListener() {
        backLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        adviseScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //通知父控件请勿拦截本控件touch事件
                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        editTextRequestFocus();
                        showSoftInput();
                        break;
                }
                return false;
            }
        });

        adviseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyAdviseText()) {
                    postFeedBack();
                } else {
                    ToastUtil.showShortToast("意见标题或详细内容不能为空！");
                }
            }
        });

    }

    private String getAdviseContentText() {
        String adviseText = adviseInputEidtText.getText().toString();
        return adviseText;
    }

    private String getAdviseTitle() {
        String adviseTitle = adviseTitleEdit.getText().toString();
        return adviseTitle;
    }

    private boolean verifyAdviseText() {
        String adviseTitle = getAdviseTitle();
        String adviseText = getAdviseContentText();

        if (!CommonUtils.isEmpty(adviseTitle)
                && !CommonUtils.isEmpty(adviseText)) {

            return true;
        }
        return false;
    }

    private void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(adviseInputEidtText, 0);

    }

    private void editTextRequestFocus() {
        adviseInputEidtText.setFocusable(true);
        adviseInputEidtText.setFocusableInTouchMode(true);
        adviseInputEidtText.requestFocus();
    }

    private void postFeedBack() {
        String serverId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(serverId)) {
            return;
        }

        List<UserInfoDBBean> userInfoDBBean = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), serverId);

        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IAddFeedBack.TITLE, getAdviseTitle());
        bodyMap.put(IAddFeedBack.CONTENT, getAdviseContentText());
        bodyMap.put(IAddFeedBack.PLATFORM, IAddFeedBack.PLATFORM_VALUE_ANDROID);
        bodyMap.put(IAddFeedBack.CREATEBY, userInfoDBBean.get(0).getLogin_name());
        bodyMap.put(IAddFeedBack.MEMBER_ID, userInfoDBBean.get(0).getServerId());

        TrainSuscribe.postFeedBack(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HttpResponseAddFeedBackBean httpResponseDeviceInfoBean = (HttpResponseAddFeedBackBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseAddFeedBackBean>() {});
                if (null != httpResponseDeviceInfoBean && IAddFeedBack.SUCCESS.equalsIgnoreCase(httpResponseDeviceInfoBean.getStatus())) {
                    goToFeedBackList();
                    ToastUtil.showShort(getResources().getString(R.string.post_feedback_success_tip_text));
                } else {
                    ToastUtil.showShort(getResources().getString(R.string.post_feedback_fail_tip_text));
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(getResources().getString(R.string.post_feedback_fail_tip_text));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void goToFeedBackList() {
        Activity activity = getActivity();
        if (null != activity) {
            activity.setResult(FeedBackFragment.ADD_FEEDBACK_RESULT_SUCCESS);
            activity.finish();
        }
    }
}
