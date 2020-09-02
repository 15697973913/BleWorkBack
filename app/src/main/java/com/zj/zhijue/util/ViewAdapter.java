package com.zj.zhijue.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ViewAdapter {

    public static void setImage(ImageView imageView, String url) {
        //使用Glide框架加载图片
        try {
            Glide.with(imageView.getContext())
                    .load(url)
                    .thumbnail(0.2f)
                    .apply( new RequestOptions())
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
