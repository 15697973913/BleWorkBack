package com.zj.zhijue.activity.function;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.bean.AdviseBean;
import com.zj.zhijue.fragment.feedback.FeedbackInputFragment;
import com.zj.zhijue.fragment.feedback.FeedbackReplyedFragment;
import com.android.common.baselibrary.log.MLog;

import butterknife.BindView;

public class FeedBackReplyInfoActivity extends BaseActivity {

    @BindView(R.id.framelayout)
    FrameLayout frameLayout;

    FeedbackReplyedFragment feedbackReplyedFragment;
    FeedbackInputFragment feedbackInputFragment;

    private final String FEEDBACKREPLYEDFRAGMENT_TAG = "FeedbackReplyedFragment";
    private final String FEEDBACKWAITFORREPLYFRAGMENT_TAG = "FeedbackInputFragment";

    public static final String IS_FEEDBACK_REPLYED_KEY = "IS_FEEDBACK_REPLYED_KEY";
    public static final String IS_NEW_ADVISE_KEY = "IS_NEW_ADVISE_KEY";
    public static final String ADVISE_PARCEABLE_BEAN_KEY = "ADVISE_PARCEABLE_BEAN_KEY";
    private AdviseBean adviseBean;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setContentView(R.layout.activity_function_feedback_layout);
        super.onCreate(bundle);
        changeStatusBarTextColor(true);
        parseIntentData();
    }

    private void parseIntentData() {
        Intent intent = getIntent();
        boolean isReplyed = false;
        boolean isNewAdvise = false;
        if (null != intent) {
            isReplyed = intent.getBooleanExtra(IS_FEEDBACK_REPLYED_KEY, false);
            isNewAdvise = intent.getBooleanExtra(IS_NEW_ADVISE_KEY, false);
            adviseBean = intent.getParcelableExtra(ADVISE_PARCEABLE_BEAN_KEY);
        } else {
            finish();
        }
        MLog.d("adviseBean = " + adviseBean);

        if (isNewAdvise) {
            showFeedbackWaitForReplyedFragment();
        } else {
            showFeedbackReplyedFragment();
        }
    }

    private void showFeedbackReplyedFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        feedbackReplyedFragment = (FeedbackReplyedFragment) fragmentManager.findFragmentByTag(FEEDBACKREPLYEDFRAGMENT_TAG);
        hideFragment(fragmentTransaction);
        if (null == feedbackReplyedFragment) {
            feedbackReplyedFragment = new FeedbackReplyedFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(ADVISE_PARCEABLE_BEAN_KEY, adviseBean);
            feedbackReplyedFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.framelayout, feedbackReplyedFragment, FEEDBACKREPLYEDFRAGMENT_TAG);
        } else {
            fragmentTransaction.show(feedbackReplyedFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void showFeedbackWaitForReplyedFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        feedbackInputFragment = (FeedbackInputFragment) fragmentManager.findFragmentByTag(FEEDBACKWAITFORREPLYFRAGMENT_TAG);
        hideFragment(fragmentTransaction);
        if (null == feedbackInputFragment) {
            feedbackInputFragment = new FeedbackInputFragment();
            fragmentTransaction.add(R.id.framelayout, feedbackInputFragment, FEEDBACKWAITFORREPLYFRAGMENT_TAG);
        } else {
            fragmentTransaction.show(feedbackInputFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        //如果此fragment不为空的话就隐藏起来
        if (null != feedbackReplyedFragment) {
            fragmentTransaction.hide(feedbackReplyedFragment);
        }

        if (null != feedbackInputFragment) {
            fragmentTransaction.hide(feedbackInputFragment);
        }
    }


}
