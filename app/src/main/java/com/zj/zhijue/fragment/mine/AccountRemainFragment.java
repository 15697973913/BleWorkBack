package com.zj.zhijue.fragment.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.RechargeBean;
import com.zj.zhijue.listener.OnItemClickListener;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.zj.zhijue.activity.mine.AccountManagerActivity;
import com.zj.zhijue.adapter.RechargeMoneyRecyclerViewAdapter;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.zj.zhijue.view.recyclerview.GloriousRecyclerView;
import com.zj.zhijue.view.recyclerview.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 账号余额
 */
public class AccountRemainFragment extends BaseFragment {

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLayout;

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView titleTextView;

    @BindView(R.id.integralconversionrecordlayout)
    LinearLayout integralConversionRecordLayout;

    @BindView(R.id.rechargemonerecyclerview)
    GloriousRecyclerView gloriousRecyclerView;

    @BindView(R.id.rechargebtin)
    Button rechargeButton;

    @BindView(R.id.rechargeaccounttv)
    AppCompatTextView rechargeAccountTextView;//账号

    @BindView(R.id.custommoneyedittv)
    AppCompatEditText customEditText;//自定义金额

    @BindView(R.id.realrechargemonetv)
    AppCompatTextView realRechargeMoneyTextView;//实际到账

    private int rechargeMoney = 0;
    private String rechargeAccount = "";
    private float realRechargeMoney = 0f;

    private RechargeMoneyRecyclerViewAdapter rechargeMoneyRecyclerViewAdapter;
    private List<RechargeBean> rechargeBeanList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remain_money_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        integralConversionRecordLayout.setVisibility(View.GONE);
        titleTextView.setText("账户充值");
        rechargeAccountTextView.setText(String.valueOf(System.currentTimeMillis()));
        initRecyclerView();
    }

    private void initData() {
        initRechargeMoneyRecyclerViewData();
    }

    private void initRechargeMoneyRecyclerViewData() {
        rechargeBeanList.clear();

        RechargeBean rechargeBean0 = new RechargeBean();
        rechargeBean0.setRechargeMoney(10);
        rechargeBean0.setToTheAccountMoney(10.5f);
        rechargeBeanList.add(rechargeBean0);

        RechargeBean rechargeBean1 = new RechargeBean();
        rechargeBean1.setRechargeMoney(30);
        rechargeBean1.setToTheAccountMoney(31);
        rechargeBeanList.add(rechargeBean1);

        RechargeBean rechargeBean2 = new RechargeBean();
        rechargeBean2.setRechargeMoney(50);
        rechargeBean2.setToTheAccountMoney(52);
        rechargeBeanList.add(rechargeBean2);

        RechargeBean rechargeBean3 = new RechargeBean();
        rechargeBean3.setRechargeMoney(100);
        rechargeBean3.setToTheAccountMoney(105);
        rechargeBeanList.add(rechargeBean3);

        RechargeBean rechargeBean4 = new RechargeBean();
        rechargeBean4.setRechargeMoney(200);
        rechargeBean4.setToTheAccountMoney(210);
        rechargeBeanList.add(rechargeBean4);


        RechargeBean rechargeBean5 = new RechargeBean();
        rechargeBean5.setRechargeMoney(500);
        rechargeBean5.setToTheAccountMoney(520);
        rechargeBeanList.add(rechargeBean5);

        RechargeBean rechargeBean6 = new RechargeBean();
        rechargeBean6.setRechargeMoney(1000);
        rechargeBean6.setToTheAccountMoney(1080);
        rechargeBeanList.add(rechargeBean6);

        RechargeBean rechargeBean7 = new RechargeBean();
        rechargeBean7.setRechargeMoney(2000);
        rechargeBean7.setToTheAccountMoney(2120);
        rechargeBeanList.add(rechargeBean7);


        RechargeBean rechargeBean8 = new RechargeBean();
        rechargeBean8.setRechargeMoney(3000);
        rechargeBean8.setToTheAccountMoney(3150);
        rechargeBeanList.add(rechargeBean8);

        RechargeBean rechargeBean9 = new RechargeBean();
        rechargeBean9.setRechargeMoney(5000);
        rechargeBean9.setToTheAccountMoney(5300);
        rechargeBeanList.add(rechargeBean9);

        RechargeBean rechargeBean10 = new RechargeBean();
        rechargeBean10.setRechargeMoney(8000);
        rechargeBean10.setToTheAccountMoney(8500);
        rechargeBeanList.add(rechargeBean10);

        RechargeBean rechargeBean11 = new RechargeBean();
        rechargeBean11.setRechargeMoney(10000);
        rechargeBean11.setToTheAccountMoney(10800);
        rechargeBeanList.add(rechargeBean11);
    }

    protected void initListener() {
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        rechargeMoneyRecyclerViewAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                for (int i = 0; i < rechargeBeanList.size(); i++) {
                    RechargeBean rechargeBean = rechargeBeanList.get(i);
                    if (i == position) {
                        rechargeBean.setSelected(!rechargeBean.isSelected());
                    } else {
                        rechargeBean.setSelected(false);
                    }
                }
                rechargeMoneyRecyclerViewAdapter.setDatas(rechargeBeanList);
                gloriousRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        rechargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (veriryRechargeMoney()) {
                    rechargeAccount = rechargeAccountTextView.getText().toString();
                    Intent mIntent = new Intent(getActivity(), AccountManagerActivity.class);
                    mIntent.putExtra(AccountManagerActivity.MINE_FRAGMENT_INDEX_KEY, AccountManagerActivity.ORDER_CONFIRMATION_FRAGMENT_INDEX);
                    mIntent.putExtra(AccountManagerActivity.RECHARGE_MONE_KEY, rechargeMoney);
                    mIntent.putExtra(AccountManagerActivity.RECHARGE_ACCOUNT_KEY, rechargeAccount);
                    mIntent.putExtra(AccountManagerActivity.REAL_RECHARGE_ACCOUNT_KEY, realRechargeMoney);
                    startActivity(mIntent);
                } else {
                    ToastUtil.showShortToast("请选择充值金额");
                }
            }
        });
    }

    private void initRecyclerView() {
        Activity activity = getActivity();
        if (null == activity) {
            return;
        }

        final GridLayoutManager manager = new GridLayoutManager(activity, 3);

        rechargeMoneyRecyclerViewAdapter = new RechargeMoneyRecyclerViewAdapter(this);
        gloriousRecyclerView.setAdapter(rechargeMoneyRecyclerViewAdapter);

        GridItemDecoration gridItemDecoration = new GridItemDecoration.Builder(activity)
                //.size((int) DeviceUtils.dipToPx(activity, 15))
                .horSize((int) DeviceUtils.dipToPx(activity, 15))
                .verSize((int) DeviceUtils.dipToPx(activity, 15))
                .color(R.color.register_bg_color)
                .margin((int) DeviceUtils.dipToPx(activity, 5), (int) DeviceUtils.dipToPx(activity, 5))
                .isExistHead(false)
                .showHeadDivider(false)
                .showLastDivider(false)
                .build();

        gloriousRecyclerView.addItemDecoration(gridItemDecoration);
        gloriousRecyclerView.setLayoutManager(manager);
        rechargeMoneyRecyclerViewAdapter.setDatas(rechargeBeanList);
    }

    private String getCustomRechargeMoney() {
        return customEditText.getText().toString();
    }

    private boolean veriryRechargeMoney() {
        String customeRechargeMoney = getCustomRechargeMoney();
        for (int i = 0; i < rechargeBeanList.size(); i++) {
            RechargeBean rechargeBean = rechargeBeanList.get(i);
            if (rechargeBean.isSelected()) {
                rechargeMoney = rechargeBean.getRechargeMoney();
                realRechargeMoney = rechargeBean.getToTheAccountMoney();
                return true;
            }
        }

        if (!CommonUtils.isEmpty(customeRechargeMoney)) {
            rechargeMoney = Integer.parseInt(customeRechargeMoney);
            if (rechargeMoney > 0) {
                return true;
            }
        }

        rechargeMoney = 0;
        return false;
    }

}
