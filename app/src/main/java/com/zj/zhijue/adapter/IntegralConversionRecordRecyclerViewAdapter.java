package com.zj.zhijue.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zj.zhijue.R;
import com.zj.zhijue.bean.ConversionRecordBean;
import com.zj.zhijue.listener.OnChildItemClickListener;
import com.zj.zhijue.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分兑换记录
 */
public class IntegralConversionRecordRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Fragment mFragment;
    private List<ConversionRecordBean> mDatas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private OnChildItemClickListener mOnChildItemClickListener;
    private int index = -1;


    public IntegralConversionRecordRecyclerViewAdapter(Fragment fragment) {
        mFragment = fragment;
        mContext = fragment.getActivity();
    }

    public void addDatas(List<ConversionRecordBean> datas) {

        if (null != datas && datas .size() > 0) {
            mDatas.addAll(datas);
        }
    }

    public void setDatas(List<ConversionRecordBean> datas) {
        if (null != datas && datas.size() > 0) {
            mDatas.clear();
            mDatas.addAll(datas);
        }
    }

    public List<ConversionRecordBean> getDatas() {
        return mDatas;
    }



    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_mine_integral_conversion_record_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ConversionRecordBean conversionRecordBean = mDatas.get(position);

        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.recordIndexTextView.setText(String.valueOf(conversionRecordBean.getIndex()));
        viewHolder.recordIntegralTextView.setText(conversionRecordBean.getIntegral());
        viewHolder.recordTimeLongTextView.setText(conversionRecordBean.getTime());
        viewHolder.recordDateTextView.setText(conversionRecordBean.getConversionDate());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView recordIndexTextView;
        AppCompatTextView recordIntegralTextView;
        AppCompatTextView recordTimeLongTextView;
        AppCompatTextView recordDateTextView;

        ViewHolder(View view) {
            super(view);
            recordIndexTextView = view.findViewById(R.id.record_indextv);
            recordIntegralTextView = view.findViewById(R.id.record_integraltv);
            recordTimeLongTextView = view.findViewById(R.id.record_timelongtv);
            recordDateTextView = view.findViewById(R.id.record_datetv);
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
