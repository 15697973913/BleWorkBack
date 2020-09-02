package com.zj.zhijue.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.bean.RechargeTypeBean;
import com.zj.zhijue.listener.OnChildItemClickListener;
import com.zj.zhijue.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单确认，选择支付方式
 */
public class OrderConfirmRechargeTypeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Fragment mFragment;
    private List<RechargeTypeBean> mDatas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private OnChildItemClickListener mOnChildItemClickListener;
    private int index = -1;


    public OrderConfirmRechargeTypeRecyclerViewAdapter(Fragment fragment) {
        mFragment = fragment;
        mContext = fragment.getActivity();
    }

    public void addDatas(List<RechargeTypeBean> datas) {

        if (null != datas && datas .size() > 0) {
            mDatas.addAll(datas);
        }
    }

    public void setDatas(List<RechargeTypeBean> datas) {
        if (null != datas && datas.size() > 0) {
            mDatas.clear();
            mDatas.addAll(datas);
        }
    }

    public List<RechargeTypeBean> getDatas() {
        return mDatas;
    }



    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_mine_recharge_type_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RechargeTypeBean rechargeBean = mDatas.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.rechargeTypeImageView.setImageResource(rechargeBean.getRechargeTypeResourceId());
        if (rechargeBean.isSelected()) {
            viewHolder.rechrageTypeSelectedStatusImageView.setImageResource(R.drawable.mima);
        } else {
            viewHolder.rechrageTypeSelectedStatusImageView.setImageResource(R.drawable.mima);
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
        RelativeLayout rechargeItemBgLayout;
        AppCompatImageView rechargeTypeImageView;
        AppCompatImageView rechrageTypeSelectedStatusImageView;


        ViewHolder(View view) {
            super(view);
            rechargeItemBgLayout = view.findViewById(R.id.rechargeitembglayout);
            rechargeTypeImageView = view.findViewById(R.id.rechargetypeimg);
            rechrageTypeSelectedStatusImageView = view.findViewById(R.id.rechargetypeselectstatusimg);
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
