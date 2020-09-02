package com.zj.zhijue.fragment.feedback;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.AdviseBean;
import com.zj.zhijue.activity.function.FeedBackReplyInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 意见已回复
 */
public class FeedbackReplyedFragment extends BaseFragment {

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLinearLayout;

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView titleTexView;

    @BindView(R.id.adviseindextv)
    AppCompatTextView adviseIndexTextview;

    @BindView(R.id.advise_repleyed_title_tv)
    AppCompatTextView replyedTextView;

    @BindView(R.id.avise_replyed_cotent)
    AppCompatTextView adviseReplyedContentTextView;

    AdviseBean adviseBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_replyed_layout, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        titleTexView.setText("意见反馈");
        //adviseButton.setText(getResources().getString(R.string.advise_text));
        //adviseLinearLayout.setVisibility(View.VISIBLE);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (null != bundle) {
            adviseBean = bundle.getParcelable(FeedBackReplyInfoActivity.ADVISE_PARCEABLE_BEAN_KEY);
        }

        if (null != adviseBean) {
            adviseIndexTextview.setText("Q" + (adviseBean.getTitleIndex() + 1));
            replyedTextView.setText(adviseBean.getQuestionTitle());
            adviseReplyedContentTextView.setText(getResources().getString(R.string.avivse_replyed_prefix_text) + adviseBean.getQuestionContent());
        }

    }

    @Override
    protected void initListener() {
        backLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

       /* adviseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                Intent mIntent = new Intent(getActivity(), FeedBackReplyInfoActivity.class);
                mIntent.putExtra(FeedBackReplyInfoActivity.IS_NEW_ADVISE_KEY, true);
                startActivity(mIntent);
            }
        });*/
    }
}
