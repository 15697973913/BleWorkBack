package com.zj.zhijue.adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zj.zhijue.R;
import com.zj.zhijue.bean.TrainModeBean;

import java.util.List;

public class TrainModeAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater li;
    private List<TrainModeBean> dataList;

    public TrainModeAdapter(Context ctx, List<TrainModeBean> dataList) {
        this.ctx = ctx;
        this.li = LayoutInflater.from(ctx);
        this.dataList = dataList;
    }
    
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public TrainModeBean getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.fragment_train_mode_item, null);
            new ViewHolder(convertView);
        } 
        ViewHolder holder = (ViewHolder) convertView.getTag();// get convertView's holder

        TrainModeBean trainModeBean = getItem(position);
        holder.trainModeTextView.setText(trainModeBean.getModeText());
        //holder.trainModeImageView.setImageResource(trainModeBean.getImageResourceId());
        return convertView;
    }

     class ViewHolder {
       // ImageView trainModeImageView;
        AppCompatTextView trainModeTextView;
        
        public ViewHolder(View convertView){
            //trainModeImageView = convertView.findViewById(R.id.trainmodeimage);
            trainModeTextView =  convertView.findViewById(R.id.trainmodename);
            convertView.setTag(this);//set a viewholder
        }
    }
}