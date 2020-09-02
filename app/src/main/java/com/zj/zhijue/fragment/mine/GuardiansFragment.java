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
import com.zj.zhijue.bean.GuardianBean;
import com.zj.zhijue.listener.OnItemClickListener;
import com.zj.zhijue.adapter.GuardiansRecyclerViewAdapter;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.zj.zhijue.view.recyclerview.GloriousRecyclerView;
import com.zj.zhijue.view.recyclerview.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 监护人列表
 */
public class GuardiansFragment extends BaseFragment {

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLayout;

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView titleTextView;

    @BindView(R.id.integralconversionrecordlayout)
    LinearLayout integralConversionRecordLayout;

    @BindView(R.id.guardiansrecyclerview)
    GloriousRecyclerView gloriousRecyclerView;

    @BindView(R.id.createguardianlayout)
    LinearLayout createGuardianLayout;

    private GuardiansRecyclerViewAdapter guardiansRecyclerViewAdapter;
    private List<GuardianBean> guardianBeanList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guardians_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        integralConversionRecordLayout.setVisibility(View.GONE);
        titleTextView.setText("监护人");
        initRecyclerView();
    }

    private void initRecyclerView() {
        Activity activity = getActivity();
        if (null == activity) {
            return;
        }

        final GridLayoutManager manager = new GridLayoutManager(activity, 1);

        guardiansRecyclerViewAdapter = new GuardiansRecyclerViewAdapter(this);
        gloriousRecyclerView.setAdapter(guardiansRecyclerViewAdapter);

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
        guardiansRecyclerViewAdapter.setDatas(guardianBeanList);
    }

    private void initData() {
        initGuardianListData();
    }

    private void initGuardianListData() {
        guardianBeanList.clear();
        GuardianBean fatherGuardian = new GuardianBean();
        fatherGuardian.setRelationShip("父亲");
        fatherGuardian.setAccount("Father_account");
        fatherGuardian.setPorttraitImageId(R.drawable.mima);
        guardianBeanList.add(fatherGuardian);

        GuardianBean matherGuardian = new GuardianBean();
        matherGuardian.setAccount("Mather_account");
        matherGuardian.setRelationShip("母亲");
        matherGuardian.setPorttraitImageId(R.drawable.mima);
        guardianBeanList.add(matherGuardian);
    }



    protected void initListener() {
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        guardiansRecyclerViewAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

            }
        });

        createGuardianLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardianBean matherGuardian = new GuardianBean();
                guardianBeanList.add(matherGuardian);
                matherGuardian.setAccount("Guardian_account" + guardianBeanList.size());
                matherGuardian.setRelationShip("监护人" + guardianBeanList.size());
                matherGuardian.setPorttraitImageId(R.drawable.mima);
                guardiansRecyclerViewAdapter.setDatas(guardianBeanList);
                gloriousRecyclerView.getAdapter().notifyItemInserted(guardianBeanList.size() - 1);
            }
        });
    }
}
