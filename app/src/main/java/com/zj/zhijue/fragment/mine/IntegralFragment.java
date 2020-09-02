package com.zj.zhijue.fragment.mine;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 积分兑换
 */
public class IntegralFragment extends BaseFragment {

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLayout;

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView titleTextView;

    @BindView(R.id.integral_record_btn)
    AppCompatImageButton integralRecordButton;

    @BindView(R.id.integralconversionrecordlayout)
    LinearLayout integralConversionLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_integral_layout, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        titleTextView.setText("积分兑换");
    }

    private void initData() {

    }

    protected void initListener() {
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        integralConversionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent mIntent = new Intent(getActivity(), IntegralConversionRecordActivity.class);
                startActivity(mIntent);*/
            }
        });
    }
}
