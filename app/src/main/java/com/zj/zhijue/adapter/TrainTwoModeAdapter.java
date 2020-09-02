package com.zj.zhijue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.zj.zhijue.R;

import java.util.List;

public class TrainTwoModeAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater li;
    private List<String> dataList;
    private boolean itemSelectable = true;

    public TrainTwoModeAdapter(Context ctx, List<String> dataList) {
        this.ctx = ctx;
        this.li = LayoutInflater.from(ctx);
        this.dataList = dataList;
    }

    public void setDataList(List<String> dataList) {
        if (null != dataList) {
            this.dataList = dataList;
            //this.dataList.addAll(dataList);
        }
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
            convertView = View.inflate(ctx, R.layout.fragment_train_twomode_item, null);
            new ViewHolder(convertView);
        } 
        ViewHolder holder = (ViewHolder) convertView.getTag();// get convertView's holder

        String yearAndMoth = getItem(position);
       /* try {
            yearAndMoth = DateUtil.lineformaterCn.format(new Date(DateUtil.lineformater.parse(yearAndMoth).getTime())) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        holder.trainModeTextView.setText(yearAndMoth);
        if (itemSelectable) {
            holder.trainModeTextView.setTextColor(ctx.getResources().getColor(R.color.res_color_blue_374cff));
        } else {
            holder.trainModeTextView.setTextColor(ctx.getResources().getColor(R.color.bleglasses_register_hint_text_color));
        }

        return convertView;
    }

     class ViewHolder {
        TextView trainModeTextView;
        
        public ViewHolder(View convertView){
            trainModeTextView =  convertView.findViewById(R.id.sextextview);
            convertView.setTag(this);//set a viewholder
        }
    }

    public boolean isItemSelectable() {
        return itemSelectable;
    }

    public void setItemSelectable(boolean itemSelectable) {
        this.itemSelectable = itemSelectable;
    }
}