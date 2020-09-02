package com.zj.zhijue.adapter;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.litesuits.android.log.Log;
import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.zhijue.R;
import com.zj.zhijue.bean.MsgBean;
import com.zj.zhijue.bean.TeamInfoBean;
import com.zj.zhijue.view.circleimageview.CircleImageView;

import java.util.List;

import androidx.annotation.Nullable;

public class TeamAdapter extends BaseQuickAdapter<TeamInfoBean, BaseViewHolder> {

    private Context mContext;
    private List<TeamInfoBean> dataList;

    public TeamAdapter(Context context, @Nullable List<TeamInfoBean> data) {
        super(R.layout.item_team, data);
        this.mContext = context;
        this.dataList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamInfoBean item) {
        CircleImageView civ_headimg = helper.getView(R.id.civ_headimg);
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_mobile, item.getPhone())
                .setText(R.id.tv_time, item.getTotalTime() + "小时");

        Log.v("TeamAcapter:", "item.getFace():" + item.getFace());
        Glide.with(mContext)
                .load(URLConstant.BASE_URL + item.getFace())
                .apply(new RequestOptions().error(R.mipmap.qidongtubiao))
                .into(civ_headimg);
    }

}
