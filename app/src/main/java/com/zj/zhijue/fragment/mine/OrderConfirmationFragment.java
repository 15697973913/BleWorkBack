package com.zj.zhijue.fragment.mine;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.RechargeTypeBean;
import com.zj.zhijue.listener.OnItemClickListener;
import com.android.common.baselibrary.util.ToastUtil;
import com.zj.zhijue.activity.mine.AccountManagerActivity;
import com.zj.zhijue.adapter.OrderConfirmRechargeTypeRecyclerViewAdapter;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.zj.zhijue.view.recyclerview.GloriousRecyclerView;
import com.zj.zhijue.view.recyclerview.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单确认
 */
public class OrderConfirmationFragment extends BaseFragment {

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLayout;

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView titleTextView;

    @BindView(R.id.integralconversionrecordlayout)
    LinearLayout integralConversionRecordLayout;

    @BindView(R.id.rechargtyperecyclerview)
    GloriousRecyclerView gloriousRecyclerView;

    @BindView(R.id.confirmrechargebtin)
    Button confirmOrderButton;

    @BindView(R.id.rechargeaccounttv)//充值账号
    AppCompatTextView rechargeAccountTextView;

    @BindView(R.id.rechargemoneytv)//充值金额
    AppCompatTextView rechargeMoneyTextView;

    @BindView(R.id.toaccountmoneytv)//实际到账
    AppCompatTextView toAccountMoneyTextView;

    private int rechargeMoney = 0;
    private String rechargeAccount = "";
    private float realRechargeMoney = 0f;

    private OrderConfirmRechargeTypeRecyclerViewAdapter orderConfirmRechargeTypeRecyclerViewAdapter;
    private List<RechargeTypeBean> rechargeTypeBeans = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_confirmation_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        integralConversionRecordLayout.setVisibility(View.GONE);
        titleTextView.setText("订单确认");
        rechargeAccountTextView.setText(rechargeAccount);
        rechargeMoneyTextView.setText(rechargeMoney + "元");
        toAccountMoneyTextView.setText(realRechargeMoney + "元");
        initRecyclerView();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (null != bundle) {
            rechargeMoney = bundle.getInt(AccountManagerActivity.RECHARGE_MONE_KEY);
            rechargeAccount = bundle.getString(AccountManagerActivity.RECHARGE_ACCOUNT_KEY);
            realRechargeMoney = bundle.getFloat(AccountManagerActivity.REAL_RECHARGE_ACCOUNT_KEY);
        }

        if (rechargeMoney == 0) {
            getActivity().finish();
        }

        rechargeTypeBeans.clear();
        RechargeTypeBean zfyRechargeTypeBean = new RechargeTypeBean();
        zfyRechargeTypeBean.setRechargeTypeResourceId(R.drawable.mima);
        rechargeTypeBeans.add(zfyRechargeTypeBean);

        RechargeTypeBean wechatRechargeTypeBean = new RechargeTypeBean();
        wechatRechargeTypeBean.setRechargeTypeResourceId(R.drawable.mima);
        rechargeTypeBeans.add(wechatRechargeTypeBean);

        RechargeTypeBean bankRechargeTypeBean = new RechargeTypeBean();
        bankRechargeTypeBean.setRechargeTypeResourceId(R.drawable.mima);
        rechargeTypeBeans.add(bankRechargeTypeBean);
    }

    private void initRecyclerView() {
        Activity activity = getActivity();
        if (null == activity) {
            return;
        }

        final GridLayoutManager manager = new GridLayoutManager(activity, 1);

        orderConfirmRechargeTypeRecyclerViewAdapter = new OrderConfirmRechargeTypeRecyclerViewAdapter(this);
        gloriousRecyclerView.setAdapter(orderConfirmRechargeTypeRecyclerViewAdapter);

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
        orderConfirmRechargeTypeRecyclerViewAdapter.setDatas(rechargeTypeBeans);
    }

    protected void initListener() {
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        orderConfirmRechargeTypeRecyclerViewAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                for (int i = 0; i < rechargeTypeBeans.size(); i++) {
                    RechargeTypeBean rechargeTypeBean = rechargeTypeBeans.get(i);

                    if (i == position) {
                        rechargeTypeBean.setSelected(!rechargeTypeBean.isSelected());
                    } else {
                        rechargeTypeBean.setSelected(false);
                    }
                }
                orderConfirmRechargeTypeRecyclerViewAdapter.setDatas(rechargeTypeBeans);
                gloriousRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RechargeTypeBean rechargeTypeBean = null;
                for (int i = 0; i < rechargeTypeBeans.size(); i++) {
                    rechargeTypeBean = rechargeTypeBeans.get(i);
                    if (rechargeTypeBean.isSelected()) {
                        break;
                    } else {
                        rechargeTypeBean = null;
                    }
                }

                if (null == rechargeTypeBean) {
                    ToastUtil.showShortToast("请选择支付方式");
                }
            }
        });
    }
}
