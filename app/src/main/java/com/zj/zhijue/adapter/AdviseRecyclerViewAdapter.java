package com.zj.zhijue.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.bean.AdviseBean;
import com.zj.zhijue.listener.OnChildItemClickListener;
import com.zj.zhijue.listener.OnItemClickListener;

import java.util.List;

/**
 * 意见反馈
 */
public class AdviseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Fragment mFragment;
    private List<AdviseBean> mDatas = null;// ;= new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private OnChildItemClickListener mOnChildItemClickListener;
    private int index = -1;


    public AdviseRecyclerViewAdapter(Fragment fragment) {
        mFragment = fragment;
        mContext = fragment.getActivity();
    }

    public void addDatas(List<AdviseBean> datas) {

        if (null != datas && datas .size() > 0) {
            mDatas.addAll(datas);
        }
    }

    public void setDatas(List<AdviseBean> datas) {
        mDatas = datas;
      /*  if (null != datas && datas.size() > 0) {
            mDatas.clear();
            mDatas.addAll(datas);
        }*/
    }

    public List<AdviseBean> getDatas() {
        return mDatas;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_feedback_advise_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AdviseBean adviseBean = mDatas.get(position);

        String title = adviseBean.getQuestionTitle();
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.titleTextView.setText(title);

        if (adviseBean.isReplyed()) {
            viewHolder.adviseButton.setText(mContext.getResources().getString(R.string.replyed_text));
            viewHolder.adviseButton.setBackgroundResource(R.mipmap.yihufu);
        } else {
            viewHolder.adviseButton.setBackgroundResource(R.mipmap.daihuifu);
            viewHolder.adviseButton.setText(mContext.getResources().getString(R.string.need_reply_text));
        }

        viewHolder.adviseIndexTv.setText("Q" + (position + 1));

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
        AppCompatTextView adviseIndexTv;
        AppCompatTextView titleTextView;
        LinearLayout commonProblemLayout;
        AppCompatButton adviseButton;

        ViewHolder(View view) {
            super(view);
            adviseIndexTv = view.findViewById(R.id.adviseindextv);
            titleTextView = view.findViewById(R.id.function_common_problem_recyclerview_item_tv);
            commonProblemLayout = view.findViewById(R.id.commonproblem_item_layout);
            adviseButton = view.findViewById(R.id.advisebtn);
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
