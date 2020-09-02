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
import com.zj.zhijue.bean.CommonProblemBean;
import com.zj.zhijue.listener.OnChildItemClickListener;
import com.zj.zhijue.listener.OnItemClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 常见问题适配器
 */
public class CommonProblemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Fragment mFragment;
    private List<CommonProblemBean> mDatas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private OnChildItemClickListener mOnChildItemClickListener;
    private DecimalFormat decimalFormat=new DecimalFormat("0.00");
    private int index = -1;


    public CommonProblemRecyclerViewAdapter(Fragment fragment) {
        mFragment = fragment;
        mContext = fragment.getActivity();
    }

    public void addDatas(List<CommonProblemBean> datas) {

        if (null != datas && datas .size() > 0) {
            mDatas.addAll(datas);
        }
    }

    public void setDatas(List<CommonProblemBean> datas) {
        if (null != datas && datas.size() > 0) {
            mDatas.clear();
            mDatas.addAll(datas);
        }
    }

    public List<CommonProblemBean> getDatas() {
        return mDatas;
    }



    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_common_problem_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CommonProblemBean commonProblemBean = mDatas.get(position);
        String title = commonProblemBean.getTitle();
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.titleTextView.setText(title);
        viewHolder.commonProblemLayout.setOnClickListener(new View.OnClickListener() {
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
        AppCompatTextView titleTextView;
        LinearLayout commonProblemLayout;

        ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.function_common_problem_recyclerview_item_tv);
            commonProblemLayout = view.findViewById(R.id.commonproblem_item_layout);
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
