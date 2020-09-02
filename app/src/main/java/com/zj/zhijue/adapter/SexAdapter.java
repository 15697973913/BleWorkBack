package com.zj.zhijue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.zj.zhijue.R;

import java.util.List;

public class SexAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater li;
    private List<String> dataList;

    public SexAdapter(Context ctx, List<String> dataList) {
        this.ctx = ctx;
        this.li = LayoutInflater.from(ctx);
        this.dataList = dataList;
    }
    
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public String getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.activity_register_sex_item, null);
            new ViewHolder(convertView);
        } 
        ViewHolder holder = (ViewHolder) convertView.getTag();// get convertView's holder

        String sex = getItem(position);
        holder.trainModeTextView.setText(sex);

        return convertView;
    }

     class ViewHolder {
        TextView trainModeTextView;
        
        public ViewHolder(View convertView){
            trainModeTextView =  convertView.findViewById(R.id.sextextview);
            convertView.setTag(this);//set a viewholder
        }
    }
}