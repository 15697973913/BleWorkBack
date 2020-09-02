package com.zj.zhijue.adapter;

import android.content.Context;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.bean.SystemSettingBean;
import com.zj.zhijue.listener.OnChildItemClickListener;
import com.zj.zhijue.listener.OnItemClickListener;
import com.zj.zhijue.util.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统设置适配器
 */
public class SystemSettingRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Fragment mFragment;
    private List<SystemSettingBean> mDatas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private OnChildItemClickListener mOnChildItemClickListener;
    private int index = -1;


    public SystemSettingRecyclerViewAdapter(Fragment fragment) {
        mFragment = fragment;
        mContext = fragment.getActivity();
    }

    public void addDatas(List<SystemSettingBean> datas) {

        if (null != datas && datas .size() > 0) {
            mDatas.addAll(datas);
        }
    }

    public void setDatas(List<SystemSettingBean> datas) {
        if (null != datas && datas.size() > 0) {
            mDatas.clear();
            mDatas.addAll(datas);
        }
    }

    public List<SystemSettingBean> getDatas() {
        return mDatas;
    }



    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_systemsettings_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SystemSettingBean systemSettingBean = mDatas.get(position);
        String title = systemSettingBean.getItemTilte();
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.prefixImageView.setImageResource(systemSettingBean.getItemPrefixImageResourceId());
        viewHolder.titleTextView.setText(title);
        viewHolder.sufixImageView.setImageResource(systemSettingBean.getItemSufixImageResource());
        if (title.equals("调试模式")) {
            viewHolder.sufixImageView.setVisibility(View.GONE);
            viewHolder.switchCompat.setVisibility(View.VISIBLE);
            setSwitchColor(viewHolder.switchCompat);
            viewHolder.switchCompat.setChecked(Config.getConfig().getDebugModeSwitch());
            viewHolder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Config.getConfig().saveDebugModeSwitch(isChecked);
                }
            });
        } else {
            if (!CommonUtils.isEmpty(systemSettingBean.getCurrentVersion())) {
                viewHolder.currentVersionTv.setVisibility(View.VISIBLE);
                viewHolder.currentVersionTv.setText(systemSettingBean.getCurrentVersion());
            } else {
                viewHolder.currentVersionTv.setVisibility(View.INVISIBLE);
            }

            viewHolder.sufixImageView.setVisibility(View.VISIBLE);
            viewHolder.switchCompat.setVisibility(View.GONE);
            viewHolder.systemSettingsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != mOnItemClickListener) {
                        mOnItemClickListener.OnItemClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        ImageView prefixImageView;
        AppCompatTextView titleTextView;
        AppCompatTextView currentVersionTv;
        ImageView sufixImageView;
        LinearLayout systemSettingsLayout;
        SwitchCompat switchCompat;

        ViewHolder(View view) {
            super(view);
            prefixImageView = view.findViewById(R.id.systemsetings_prefix_img);
            titleTextView = view.findViewById(R.id.function_system_settings_recyclerview_item_tv);
            currentVersionTv = view.findViewById(R.id.currentversion_tv);
            sufixImageView = view.findViewById(R.id.systemsettings_item_sufiximg);
            systemSettingsLayout = view.findViewById(R.id.systemsettings_item_layout);
            switchCompat = view.findViewById(R.id.debugmodeswitch);
        }
    }

    public void setSwitchColor(SwitchCompat v) {
        v.setThumbDrawable(createThumbStateDrawable());
        v.setTrackDrawable(createTrackStateDrawable());
    }

    private StateListDrawable createTrackStateDrawable() {
        Context context = MyApplication.getInstance();
        return newSelector(context.getResources().getDrawable(R.drawable.switch_ios_track_off), context.getResources().getDrawable(R.drawable.switch_ios_track_on));
    }

    private StateListDrawable createThumbStateDrawable() {
        Context context = MyApplication.getInstance();
        return newSelector(context.getResources().getDrawable(R.drawable.switch_ios_thumb), context.getResources().getDrawable(R.drawable.switch_ios_thumb));
    }

    public StateListDrawable newSelector(Drawable normal, Drawable selected) {
        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_checked}, selected);
        bg.addState(new int[0], normal);
        return bg;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnChildItemClickListener(OnChildItemClickListener mOnChildItemClickListener) {
        this.mOnChildItemClickListener = mOnChildItemClickListener;
    }
}
