package com.zj.zhijue.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.common.baselibrary.log.MLog;
import com.zj.zhijue.BuildConfig;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.mine.PersonalInfoActivity;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.FunctionCategorySubInfoBean;
import com.zj.zhijue.view.sectionedrecyclerview.Child;
import com.zj.zhijue.view.sectionedrecyclerview.SectionHeader;
import com.zj.zhijue.view.sectionedrecyclerview.sectionedrecyclerviewadapter.SectionParameters;
import com.zj.zhijue.view.sectionedrecyclerview.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import com.zj.zhijue.view.sectionedrecyclerview.sectionedrecyclerviewadapter.StatelessSection;

import java.util.ArrayList;
import java.util.List;

public class FunctionFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    List<SectionHeader> sectionHeaders;
    private SectionedRecyclerViewAdapter sectionAdapter;
    private List<FunctionCategorySubInfoBean> functionCategorySubInfoBeanArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function_layout, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void initView(View view) {

        mRecyclerView = view.findViewById(R.id.function_recyclerview);
        initRecyclerViewSectionAdapter();
    }

    private void initData() {

    }

    protected void initListener() {

    }


    private void initFunctionCategorySubInfoBeanArrayList() {
        functionCategorySubInfoBeanArrayList.clear();

        Context context = getActivity();

        FunctionCategorySubInfoBean firstCategoryBean = new FunctionCategorySubInfoBean();
        List<FunctionCategorySubInfoBean.DataBean> firstDataBeanList = new ArrayList<>();


        FunctionCategorySubInfoBean.DataBean repeatCheckDropBean = firstCategoryBean.new DataBean();
        repeatCheckDropBean.setName(context.getString(R.string.review_data_text));
        repeatCheckDropBean.setResourceId(R.drawable.mima);
        firstDataBeanList.add(repeatCheckDropBean);

        FunctionCategorySubInfoBean.DataBean customDataBeaan = firstCategoryBean.new DataBean();
        customDataBeaan.setName(context.getString(R.string.custom_data_text));
        customDataBeaan.setResourceId(R.drawable.mima);

        firstDataBeanList.add(customDataBeaan);

        FunctionCategorySubInfoBean.DataBean productCategoryBean = firstCategoryBean.new DataBean();
        productCategoryBean.setName(context.getString(R.string.product_introduce_text));
        productCategoryBean.setResourceId(R.drawable.mima);

        firstDataBeanList.add(productCategoryBean);

        FunctionCategorySubInfoBean.DataBean ganyuKeyShow = firstCategoryBean.new DataBean();
        ganyuKeyShow.setName(context.getString(R.string.intervene_key_introduce_text));
        ganyuKeyShow.setResourceId(R.drawable.mima);
        firstDataBeanList.add(ganyuKeyShow);

        FunctionCategorySubInfoBean.DataBean feedbaackDropBean = firstCategoryBean.new DataBean();
        feedbaackDropBean.setName(getResources().getString(R.string.feedback_text));
        feedbaackDropBean.setResourceId(R.drawable.mima);
        firstDataBeanList.add(feedbaackDropBean);

        FunctionCategorySubInfoBean.DataBean commonProblemDropBean = firstCategoryBean.new DataBean();
        commonProblemDropBean.setName(getResources().getString(R.string.common_problem_text));
        commonProblemDropBean.setResourceId(R.drawable.mima);
        firstDataBeanList.add(commonProblemDropBean);

        FunctionCategorySubInfoBean.DataBean contactServiceDropBean = firstCategoryBean.new DataBean();
        contactServiceDropBean.setName(getResources().getString(R.string.contact_customer_service_text));
        contactServiceDropBean.setResourceId(R.drawable.mima);
        firstDataBeanList.add(contactServiceDropBean);

        FunctionCategorySubInfoBean.DataBean systemSettingsDropBean = firstCategoryBean.new DataBean();
        systemSettingsDropBean.setName(getResources().getString(R.string.system_setting_text));
        systemSettingsDropBean.setResourceId(R.drawable.mima);
        firstDataBeanList.add(systemSettingsDropBean);

        if (BuildConfig.DEBUG){
            FunctionCategorySubInfoBean.DataBean bluetoothLogDropBean = firstCategoryBean.new DataBean();
            bluetoothLogDropBean.setName(getResources().getString(R.string.bluetooth_log));
            bluetoothLogDropBean.setResourceId(R.drawable.mima);
            firstDataBeanList.add(bluetoothLogDropBean);
        }

        firstCategoryBean.setCategoryName(getResources().getString(R.string.function_section_one_text));
        firstCategoryBean.setIndex(0);
        firstCategoryBean.setData(firstDataBeanList);


        functionCategorySubInfoBeanArrayList.add(firstCategoryBean);


    }

    private void initRecyclerViewSectionAdapter() {
        initFunctionCategorySubInfoBeanArrayList();
        sectionHeaders = new ArrayList<>();
        for (int i = 0; i < functionCategorySubInfoBeanArrayList.size(); i++) {
            FunctionCategorySubInfoBean functionCategorySubInfoBean = functionCategorySubInfoBeanArrayList.get(i);
            List<FunctionCategorySubInfoBean.DataBean> dataBeanList1 = functionCategorySubInfoBean.getData();
            List<Child> childList = new ArrayList<>();

            for (FunctionCategorySubInfoBean.DataBean dataBean : dataBeanList1) {
                Child child = new Child(dataBean.getName());
                child.setResourceId(dataBean.getResourceId());
                childList.add(child);
            }
            sectionHeaders.add(new SectionHeader(childList, functionCategorySubInfoBean.getCategoryName(), functionCategorySubInfoBean.getIndex()));
        }

        sectionAdapter = new SectionedRecyclerViewAdapter();

        int i = 0;
        for (SectionHeader sectionHeader : sectionHeaders) {
            sectionAdapter.addSection(new ChildSection(sectionHeader.getSectionText(), sectionHeader.getChildItems(), i));
            i++;
        }

        Activity activity = getActivity();
        if (null == activity) {
            return;
        }

        GridLayoutManager glm = new GridLayoutManager(activity, 4);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (sectionAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 4;
                    default:
                        return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(glm);
        mRecyclerView.setAdapter(sectionAdapter);
    }

    private class ChildSection extends StatelessSection {
        String title;
        List<Child> list;
        int index;

        ChildSection(String title, List<Child> list, int position) {
            super(SectionParameters.builder()
                    .itemResourceId(R.layout.function_content_item_view)
                    .headerResourceId(R.layout.function_recyclerview_header)
                    .build());

            this.title = title;
            this.list = list;
            this.index = position;
        }

        @Override
        public int getContentItemsTotal() {
            return list.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            final Child child = list.get(position);
            String name = child.getName();
            itemHolder.functionTextView.setText(name);
            itemHolder.functionImageView.setImageResource(child.getResourceId());

            itemHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MLog.d(" onClick index = " + index + " position = " + position);
                    if (index == 0 && position == MineFragment.CUSTOM_DATA_INDEX) {

                    } else {
                        if (index == 0) {
                            Intent mIntent = new Intent(getActivity(), PersonalInfoActivity.class);
                            mIntent.putExtra(PersonalInfoActivity.FRAGMENT_INDEX_KEY, position);
                            startActivity(mIntent);
                        } else if (index == 1) {
                            Intent mIntent = new Intent(getActivity(), PersonalInfoActivity.class);
                            mIntent.putExtra(PersonalInfoActivity.FRAGMENT_INDEX_KEY, MineFragment.COMMON_PROBLEM_INDEX + position);
                            startActivity(mIntent);
                        }
                    }
                }
            });

        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {

            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.tvTitle.setText(title);

        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        public final TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);
            tvTitle = itemView.findViewById(R.id.header_title);

        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout itemLayout;
        public ImageView functionImageView;
        public AppCompatTextView functionTextView;

        ItemViewHolder(View view) {
            super(view);
            itemLayout = view.findViewById(R.id.itemlayout);
            functionImageView = view.findViewById(R.id.function_item_pic);
            functionTextView = view.findViewById(R.id.function_item);
        }
    }


}
