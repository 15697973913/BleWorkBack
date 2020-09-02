package com.zj.zhijue.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.bean.GuardianBean;
import com.zj.zhijue.listener.OnChildItemClickListener;
import com.zj.zhijue.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 监护人列表
 */
public class GuardiansRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Fragment mFragment;
    private List<GuardianBean> mDatas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private OnChildItemClickListener mOnChildItemClickListener;
    private int index = -1;


    public GuardiansRecyclerViewAdapter(Fragment fragment) {
        mFragment = fragment;
        mContext = fragment.getActivity();
    }

    public void addDatas(List<GuardianBean> datas) {

        if (null != datas && datas .size() > 0) {
            mDatas.addAll(datas);
        }
    }

    public void setDatas(List<GuardianBean> datas) {
        if (null != datas && datas.size() > 0) {
            mDatas.clear();
            mDatas.addAll(datas);
        }
    }

    public List<GuardianBean> getDatas() {
        return mDatas;
    }



    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_mine_guardian_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        GuardianBean guardianBean = mDatas.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.guardianPortraitImageView.setImageResource(guardianBean.getPorttraitImageId());
        viewHolder.guardianAccountTextview.setText(guardianBean.getAccount());
        viewHolder.guardianRelationShipTextView.setText(guardianBean.getRelationShip());

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
        AppCompatImageView guardianPortraitImageView;
        AppCompatTextView guardianAccountTextview;
        AppCompatTextView guardianRelationShipTextView;


        ViewHolder(View view) {
            super(view);
            rechargeItemBgLayout = view.findViewById(R.id.rechargeitembglayout);
            guardianPortraitImageView = view.findViewById(R.id.guardianportraitimg);
            guardianAccountTextview = view.findViewById(R.id.guardianaccounttv);
            guardianRelationShipTextView = view.findViewById(R.id.guardianrelationshiptv);
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
