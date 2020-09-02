package com.zj.zhijue.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.zj.zhijue.R;

import java.util.List;

public class StringAdapter extends ArrayAdapter {


    private final int resourceId;

    public StringAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String strContent = (String)getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        TextView textView = (TextView) view.findViewById(R.id.listviewitem);//获取该布局内的文本视图
        textView.setText(strContent);
        return view;
    }
}
