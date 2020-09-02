package com.zj.zhijue.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zj.zhijue.R;
import com.zj.zhijue.bean.MessageBean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/9.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private List<MessageBean> messageBeanList;

    public MessageAdapter(List<MessageBean> messageBeans) {
        this.messageBeanList = messageBeans;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessageBean messageBean = messageBeanList.get(position);
        if (messageBean.getType() == MessageBean.TYPE_RECEIVED) {
            //接收的消息（左边显示，右边隐藏）
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftTextView.setText(messageBean.getContent());
        } else if (messageBean.getType() == MessageBean.TYPE_SENT) {
            //发送收的消息（右边显示，左边隐藏）
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightTextView.setText(messageBean.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messageBeanList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout leftLayout;
        RelativeLayout rightLayout;
        TextView leftTextView;
        TextView rightTextView;

        public MyViewHolder(View view) {
            super(view);
            leftLayout = view.findViewById(R.id.left_layout);
            rightLayout = view.findViewById(R.id.right_layout);
            leftTextView = view.findViewById(R.id.left_msg);
            rightTextView = view.findViewById(R.id.right_msg);
        }

    }
}
