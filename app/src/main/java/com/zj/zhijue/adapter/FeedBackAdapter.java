package com.zj.zhijue.adapter;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.litesuits.android.log.Log;
import com.zj.zhijue.R;
import com.zj.zhijue.bean.AdviseBean;
import com.zj.zhijue.bean.MsgBean;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class FeedBackAdapter extends BaseQuickAdapter<AdviseBean, BaseViewHolder> {

    private Context mContext;
    private List<AdviseBean> dataList;

    public FeedBackAdapter(Context context, @Nullable List<AdviseBean> data) {
        super(R.layout.recyclerview_item_feedback_advise_layout, data);
        this.mContext=context;
        this.dataList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, AdviseBean item) {
        Log.e("TAG","下标："+helper.getAdapterPosition()) ;
        LinearLayout ll_item=helper.getView(R.id.ll_item);
        AppCompatButton advisebtn=helper.getView(R.id.advisebtn);
        helper.setText(R.id.adviseindextv, item.getTitleIndex())
                .setText(R.id.function_common_problem_recyclerview_item_tv,item.getQuestionTitle());
        if (item.isReplyed()) {
            advisebtn.setText(mContext.getResources().getString(R.string.replyed_text));
            advisebtn.setBackgroundResource(R.mipmap.yihufu);
        } else {
            advisebtn.setBackgroundResource(R.mipmap.daihuifu);
            advisebtn.setText(mContext.getResources().getString(R.string.need_reply_text));
        }
        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

}
