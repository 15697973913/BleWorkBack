package com.zj.zhijue.adapter;


import android.content.Context;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.FileUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.litesuits.android.log.Log;
import com.litesuits.common.io.FilenameUtils;
import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.zhijue.R;
import com.zj.zhijue.bean.TeamInfoBean;
import com.zj.zhijue.view.circleimageview.CircleImageView;

import java.io.File;
import java.util.List;

public class BluetoothLogAdapter extends BaseQuickAdapter<File, BaseViewHolder> {

    private List<File> dataList;

    public BluetoothLogAdapter(@Nullable List<File> data) {
        super(R.layout.item_bluetoothlog, data);
        this.dataList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, File item) {
        helper.setText(R.id.tvFileName, item.getName())
                .setText(R.id.tvFileSize, FileUtils.getFileFormetLength(item)+"   |")
                .setText(R.id.tvTime, FileUtils.getFileLastModifiedTime(item));

    }

}
