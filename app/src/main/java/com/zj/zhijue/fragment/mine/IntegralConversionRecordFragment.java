package com.zj.zhijue.fragment.mine;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.ConversionRecordBean;
import com.android.common.baselibrary.util.DateUtil;
import com.zj.zhijue.adapter.IntegralConversionRecordRecyclerViewAdapter;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.zj.zhijue.view.recyclerview.GloriousRecyclerView;
import com.zj.zhijue.view.recyclerview.GridItemDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 积分兑换记录
 */
public class IntegralConversionRecordFragment extends BaseFragment {

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLayout;

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView titleTextView;

    @BindView(R.id.integralconversionrecordlayout)
    LinearLayout integralLayout;

    @BindView(R.id.integralrecordrecyclerview)
    GloriousRecyclerView gloriousRecyclerView;

    IntegralConversionRecordRecyclerViewAdapter integralConversionRecordRecyclerViewAdapter;
    List<ConversionRecordBean> conversionRecordBeanArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_integral_conversion_record_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        integralLayout.setVisibility(View.GONE);
        titleTextView.setText("兑换记录");
        initRecyclerView();
    }

    private void initRecyclerView() {
        Activity activity = getActivity();
        if (null == activity) {
            return;
        }

        final GridLayoutManager manager = new GridLayoutManager(activity, 1);

        integralConversionRecordRecyclerViewAdapter = new IntegralConversionRecordRecyclerViewAdapter(this);
        gloriousRecyclerView.setAdapter(integralConversionRecordRecyclerViewAdapter);

        GridItemDecoration gridItemDecoration = new GridItemDecoration.Builder(activity)
                .size((int) DeviceUtils.dipToPx(activity, 2))
                .color(R.color.register_bg_color)
                .margin(0, 0)
                .isExistHead(false)
                .showHeadDivider(false)
                .showLastDivider(false)
                .build();

        gloriousRecyclerView.addItemDecoration(gridItemDecoration);
        gloriousRecyclerView.setLayoutManager(manager);
        integralConversionRecordRecyclerViewAdapter.setDatas(conversionRecordBeanArrayList);
    }

    private void initData() {
        initRecyclerViewData();
    }

    private void initRecyclerViewData() {
        conversionRecordBeanArrayList.clear();
        for (int i = 0; i < 10; i++) {
            ConversionRecordBean conversionRecordBean = new ConversionRecordBean();
            conversionRecordBean.setIndex(i + 1);
            conversionRecordBean.setConversionDate(DateUtil.localformatterDay.format(new Date(System.currentTimeMillis())));
            conversionRecordBean.setIntegral(String.valueOf(10 + i * 1000) + "分");
            conversionRecordBean.setTime(String.valueOf(i + 10) + "小时");
            conversionRecordBeanArrayList.add(conversionRecordBean);
        }


    }

    protected void initListener() {
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }
}
