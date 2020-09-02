package com.zj.zhijue.adapter;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zj.zhijue.R;
import com.zj.zhijue.bean.MsgBean;
import com.zj.zhijue.bean.RecordBean;
import com.zj.zhijue.bean.RecordListBean;
import com.zj.zhijue.util.DateUtil;

import java.util.List;

import androidx.annotation.Nullable;

public class TimeRecordAdapter extends BaseQuickAdapter<RecordListBean.SurplusTimeBean, BaseViewHolder> {

    private Context mContext;
    private List<RecordListBean.SurplusTimeBean> dataList;

    public TimeRecordAdapter(Context context, @Nullable List<RecordListBean.SurplusTimeBean> data) {
        super(R.layout.item_time_record, data);
        this.mContext=context;
        this.dataList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordListBean.SurplusTimeBean item) {
        String str;
        String addOreduce;
        if(item.getMoney()!=null&&Float.parseFloat(item.getMoney())>0){
            str="充值时长";
            addOreduce="+";
        }else{
            str="消耗时长";
            addOreduce="-";
        }
        helper.setText(R.id.tv_title, str)
                .setText(R.id.tv_content,addOreduce+ DateUtil.secondToTime(item.getTime()))
                 .setText(R.id.tv_time,item.getCreateDate());
    }

}
