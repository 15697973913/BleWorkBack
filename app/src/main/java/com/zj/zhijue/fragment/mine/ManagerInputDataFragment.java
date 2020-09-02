package com.zj.zhijue.fragment.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.zj.zhijue.R;
import com.zj.zhijue.activity.mine.PersonalInfoActivity;
import com.zj.zhijue.adapter.SystemSettingRecyclerViewAdapter;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.SystemSettingBean;
import com.zj.zhijue.fragment.MineFragment;
import com.zj.zhijue.listener.OnItemClickListener;
import com.zj.zhijue.model.AppUpdateModel;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.zj.zhijue.view.recyclerview.GloriousRecyclerView;
import com.zj.zhijue.view.recyclerview.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 数据录入
 */
public class ManagerInputDataFragment extends BaseFragment {
    @BindView(R.id.function_item_title_tv)
    TextView functionItemTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout functionItemBackLayout;

    @BindView(R.id.inputreviewdatarecyclerview)
    GloriousRecyclerView systemSettingGloriousRecyclerView;

    SystemSettingRecyclerViewAdapter systemSettingRecyclerViewAdapter;

    List<SystemSettingBean> systemSettingBeans = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUpdateModel.getInstance().setContentNull();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_input_data_layout, container, false);
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
        functionItemTextView.setText(getResources().getText(R.string.input_data_text));
        initRecyclerView();
    }

    private void initData() {
        systemSettingBeans.clear();
        String[] settings = getResources().getStringArray(R.array.input_review_data_items);
        SystemSettingBean inputReviewDataBean = new SystemSettingBean();
        inputReviewDataBean.setItemPrefixImageResourceId(R.mipmap.banbengengxin);
        inputReviewDataBean.setItemTilte(settings[0]);
        inputReviewDataBean.setItemSufixImageResource(R.mipmap.jiantou);

        SystemSettingBean inputOtherDataBean = new SystemSettingBean();
        inputOtherDataBean.setItemPrefixImageResourceId(R.mipmap.banbengengxin);
        inputOtherDataBean.setItemTilte(settings[1]);
        inputOtherDataBean.setItemSufixImageResource(R.mipmap.jiantou);

        systemSettingBeans.add(inputReviewDataBean);
        systemSettingBeans.add(inputOtherDataBean);
    }

    private void initRecyclerView() {
        /**
         * 初始化 RecyclerView
         */
        Activity activity = getActivity();
        if (null == activity) {
            return;
        }

        final GridLayoutManager manager = new GridLayoutManager(activity, 1);

        systemSettingRecyclerViewAdapter = new SystemSettingRecyclerViewAdapter(this);
        systemSettingGloriousRecyclerView.setAdapter(systemSettingRecyclerViewAdapter);

        GridItemDecoration gridItemDecoration = new GridItemDecoration.Builder(activity)
                .size((int) DeviceUtils.dipToPx(activity, 4))
                .color(R.color.assit_view_division_color)
                .margin(0, 0)
                .isExistHead(false)
                .showHeadDivider(false)
                .showLastDivider(false)
                .build();

        systemSettingGloriousRecyclerView.addItemDecoration(gridItemDecoration);
        systemSettingGloriousRecyclerView.setLayoutManager(manager);
        systemSettingRecyclerViewAdapter.setDatas(systemSettingBeans);
    }

    protected void initListener() {
        functionItemBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        systemSettingRecyclerViewAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                SystemSettingBean systemSettingBean = systemSettingBeans.get(position);
                handleItemClick(position);

            }
        });
    }

    private void handleItemClick(int itemIndex) {
        switch (itemIndex) {
            case 0:
                Intent mIntent = new Intent(getActivity(), PersonalInfoActivity.class);
                mIntent.putExtra(PersonalInfoActivity.FRAGMENT_INDEX_KEY, MineFragment.INPUT_REVIEW_DATA_INDEX);
                startActivity(mIntent);
                break;

            case 1:
                Intent otherIntent = new Intent(getActivity(), PersonalInfoActivity.class);
                otherIntent.putExtra(PersonalInfoActivity.FRAGMENT_INDEX_KEY, MineFragment.INPUT_OTHER_DATA_INDEX);
                startActivity(otherIntent);
                break;

            default:
                break;
        }
    }

}
