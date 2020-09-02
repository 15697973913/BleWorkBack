package com.zj.zhijue.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.litesuits.android.log.Log;
import com.zj.zhijue.R;
import com.zj.zhijue.bean.MsgBean;

import java.util.List;

import androidx.annotation.Nullable;

public class MsgAdapter extends BaseQuickAdapter<MsgBean.DataBean, BaseViewHolder> {

    private Context mContext;
    private List<MsgBean.DataBean> dataList;

    public MsgAdapter(Context context, @Nullable List<MsgBean.DataBean> data) {
        super(R.layout.item_msg, data);
        this.mContext=context;
        this.dataList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgBean.DataBean item) {
        Log.e("TAF","下标："+helper.getAdapterPosition()+"____"+item.getMessageTitle());
        LinearLayout ll_item=helper.getView(R.id.ll_item);
        helper.setText(R.id.tv_title, item.getMessageTitle())
                .setText(R.id.tv_content,item.getMessageContent())
                 .setText(R.id.tv_time,item.getCreateTime());
        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

}
