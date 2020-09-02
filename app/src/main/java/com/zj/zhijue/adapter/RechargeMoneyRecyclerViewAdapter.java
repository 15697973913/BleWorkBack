package com.zj.zhijue.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.bean.RechargeBean;
import com.zj.zhijue.listener.OnChildItemClickListener;
import com.zj.zhijue.listener.OnItemClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 账号充值显示的优惠金额
 */
public class RechargeMoneyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Fragment mFragment;
    private List<RechargeBean> mDatas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private OnChildItemClickListener mOnChildItemClickListener;
    private DecimalFormat decimalFormat=new DecimalFormat("0.00");
    private int index = -1;


    public RechargeMoneyRecyclerViewAdapter(Fragment fragment) {
        mFragment = fragment;
        mContext = fragment.getActivity();
    }

    public void addDatas(List<RechargeBean> datas) {

        if (null != datas && datas .size() > 0) {
            mDatas.addAll(datas);
        }
    }

    public void setDatas(List<RechargeBean> datas) {
        if (null != datas && datas.size() > 0) {
            mDatas.clear();
            mDatas.addAll(datas);
        }
    }

    public List<RechargeBean> getDatas() {
        return mDatas;
    }



    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_mine_recharge_money_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RechargeBean rechargeBean = mDatas.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.rechargeTextView.setText(String.valueOf(rechargeBean.getRechargeMoney()) + "元");
        viewHolder.toTheAccountTextView.setText("到账" + String.valueOf(rechargeBean.getToTheAccountMoney())+ "元");
        if (rechargeBean.isSelected()) {
            viewHolder.rechargeItemBgLayout.setBackgroundResource(R.drawable.item_recharge_selected_bg_shape);
        } else {
            viewHolder.rechargeItemBgLayout.setBackgroundResource(R.drawable.item_recharge_unselected_bg_shape);
        }
        viewHolder.rechargeItemBgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mOnItemClickListener){
                    mOnItemClickListener.OnItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout rechargeItemBgLayout;
        AppCompatTextView rechargeTextView;
        AppCompatTextView toTheAccountTextView;


        ViewHolder(View view) {
            super(view);
            rechargeItemBgLayout = view.findViewById(R.id.rechargeitembglayout);
            rechargeTextView = view.findViewById(R.id.rechargetv);
            toTheAccountTextView = view.findViewById(R.id.toaccounttv);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnChildItemClickListener(OnChildItemClickListener mOnChildItemClickListener) {
        this.mOnChildItemClickListener = mOnChildItemClickListener;
    }
}
