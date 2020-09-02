package com.zj.zhijue.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.bean.AccountManagerBean;
import com.zj.zhijue.listener.OnChildItemClickListener;
import com.zj.zhijue.listener.OnItemClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的页面，账号管理
 */
public class AccountManagerRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Fragment mFragment;
    private List<AccountManagerBean> mDatas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private OnChildItemClickListener mOnChildItemClickListener;
    private DecimalFormat decimalFormat=new DecimalFormat("0.00");
    private int index = -1;


    public AccountManagerRecyclerViewAdapter(Fragment fragment) {
        mFragment = fragment;
        mContext = fragment.getActivity();
    }

    public void addDatas(List<AccountManagerBean> datas) {

        if (null != datas && datas .size() > 0) {
            mDatas.addAll(datas);
        }
    }

    public void setDatas(List<AccountManagerBean> datas) {
        if (null != datas && datas.size() > 0) {
            mDatas.clear();
            mDatas.addAll(datas);
        }
    }

    public List<AccountManagerBean> getDatas() {
        return mDatas;
    }



    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_mine_accountmanager_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AccountManagerBean accountManagerBean = mDatas.get(position);

        String title = accountManagerBean.getItemTitle();
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.mineItemPrefixImage.setImageResource(accountManagerBean.getPrefixImageResourceId());
        viewHolder.mineItemTitle.setText(title);
        viewHolder.mineItemContent.setText(accountManagerBean.getItemContent());
        viewHolder.mineItemButton.setImageResource(accountManagerBean.getSufixImageResourceId());

        if (accountManagerBean.isLight()) {
            viewHolder.mineItemButton.setColorFilter(mContext.getResources().getColor(R.color.login_title_text_color));
        } else {
            viewHolder.mineItemButton.clearColorFilter();
        }

        viewHolder.mineItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mOnItemClickListener) {
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
        LinearLayout mineItemLayout;
        ImageView mineItemPrefixImage;
        AppCompatTextView mineItemTitle;
        AppCompatTextView mineItemContent;

        AppCompatImageButton mineItemButton;

        ViewHolder(View view) {
            super(view);
            mineItemPrefixImage = view.findViewById(R.id.mine_item_prefiximg);
            mineItemTitle = view.findViewById(R.id.mine_item_titletv);
            mineItemContent = view.findViewById(R.id.mine_item_contenttv);
            mineItemLayout = view.findViewById(R.id.mine_item_layout);
            mineItemButton = view.findViewById(R.id.mine_item_sufiximg);
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
