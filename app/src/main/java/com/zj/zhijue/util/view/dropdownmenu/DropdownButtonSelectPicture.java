package com.zj.zhijue.util.view.dropdownmenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;

import java.util.List;


public class DropdownButtonSelectPicture extends RelativeLayout implements Checkable, View.OnClickListener, PopWinDownUtil.OnDismissLisener, AdapterView.OnItemClickListener {
    /**
     * 菜单按钮文字内容
     */
    private TextView text;

    private ImageView imageView;
    /**
     * 菜单按钮底部的提示条
     */
    //private View bLine;
    private boolean isCheced;
    private PopWinDownUtil popWinDownUtil;
    private Context mContext;
    private DropDownAdapter adapter;
    /**
     * 传入的数据
     */
    private List<DropBean> drops;
    /**
     * 当前被选择的item位置
     */
    private int selectPosition;

    private View relayView;

    public DropdownButtonSelectPicture(Context context) {
        this(context, null);
    }

    public DropdownButtonSelectPicture(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownButtonSelectPicture(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        //菜单按钮的布局
        View view =  LayoutInflater.from(getContext()).inflate(R.layout.dropdown_tab_button,this, true);
        view.setVisibility(GONE);
        text = view.findViewById(R.id.textView);
        imageView = view.findViewById(R.id.modeimage);

        //bLine = view.findViewById(R.id.bottomLine);
        //点击事件，点击外部区域隐藏popupWindow
        setOnClickListener(this);
    }
    /**
     * 添加数据，默认选择第一项
     * @param dropBeans
     */
    public void setData(List<DropBean> dropBeans) {
        if (dropBeans == null || dropBeans.isEmpty()) {
            return;
        }
        drops = dropBeans;
        drops.get(0).setChoiced(true);
        text.setText(drops.get(0).getName());
        //imageView.setImageResource(drops.get(0).getImageResourceId());
        selectPosition = 0;
        View view = LayoutInflater.from(mContext).inflate(R.layout.dropdown_content, null);
        view.findViewById(R.id.content).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWinDownUtil.hide();
            }
        });
        ListView listView = view.findViewById(R.id.list);
        LinearLayout linearLayout = view.findViewById(R.id.listviewlayout);

        listView.setOnItemClickListener(this);

        adapter = new DropDownAdapter(drops, mContext);
        listView.setAdapter(adapter);

        setListViewBasedOnChildren(listView, linearLayout);

        popWinDownUtil = new PopWinDownUtil(mContext, view, relayView);
        popWinDownUtil.setOnDismissListener(this);
    }

    public void setText(CharSequence content) {
        text.setText(content);
    }

    /**
     * 根据传过来的参数改变状态
     *
     * @param checked
     */
    @Override
    public void setChecked(boolean checked) {
        isCheced = checked;
        Drawable icon;
        if (checked) {
            //icon = getResources().getDrawable(R.drawable.shangpinliebiao_zonghe_xuanzhong);
            //text.setTextColor(getResources().getColor(R.color.classify_content_title_text_color));
            //bLine.setVisibility(VISIBLE);
            popWinDownUtil.show();
        } else {
            //icon = getResources().getDrawable(R.drawable.shangpinliebiao_zonghe_xuanzhong);
            //text.setTextColor(getResources().getColor(R.color.classify_content_title_text_color));
            //bLine.setVisibility(GONE);
            popWinDownUtil.hide();
        }
        //把箭头画到textView右边
        //text.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }

    @Override
    public boolean isChecked() {
        return isCheced;
    }

    @Override
    public void toggle() {
        setChecked(!isCheced);
    }

    @Override
    public void onClick(View v) {
        setChecked(!isCheced);
    }

    @Override
    public void onDismiss() {
        setChecked(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       /* if(selectPosition == position){
            return;
        }*/
        drops.get(selectPosition).setChoiced(false);
        drops.get(position).setChoiced(true);
        text.setText(drops.get(position).getName());
        //imageView.setImageResource(drops.get(position).getImageResourceId());
        adapter.notifyDataSetChanged();
        selectPosition = position;
        popWinDownUtil.hide();
        if (onDropItemSelectListener != null) {
            onDropItemSelectListener.onDropItemSelect(position);
        }
        //ToastUtil.showShortToast(drops.get(position).getName());
    }

    private OnDropItemSelectListener onDropItemSelectListener;

    public void setOnDropItemSelectListener(OnDropItemSelectListener onDropItemSelectListener) {
        this.onDropItemSelectListener = onDropItemSelectListener;
    }

    public interface OnDropItemSelectListener {
        void onDropItemSelect(int Postion);
    }


    class DropDownAdapter extends BaseAdapter {
        private List<DropBean> drops;
        private Context context;

        public DropDownAdapter(List<DropBean> drops, Context context) {
            this.drops = drops;
            this.context = context;
        }

        @Override
        public int getCount() {
            return drops.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.dropdown_select_pic__item, null,false);
                holder.tv = (TextView) convertView.findViewById(R.id.name);
                holder.itemLayout = convertView.findViewById(R.id.popitemlayout);
                //holder.tig = (ImageView) convertView.findViewById(R.id.check);
                resetListViewItemWidth(holder.itemLayout);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            DropBean dropBean = drops.get(position);
            holder.tv.setText(dropBean.getName());
            //holder.tig.setImageResource(dropBean.getImageResourceId());
            if (dropBean.isChoiced()) {
              //  holder.tig.setVisibility(VISIBLE);
                //holder.tv.setTextColor(getResources().getColor(R.color.classify_title_selected_text_color));
            } else {
               // holder.tig.setVisibility(INVISIBLE);
                //holder.tv.setTextColor(getResources().getColor(R.color.classify_content_title_text_color));
            }

            return convertView;
        }

        private class ViewHolder {
            LinearLayout itemLayout;
            TextView tv;
            //ImageView tig;
        }
    }

    public void setListViewBasedOnChildren(ListView listView, LinearLayout linearLayout) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition 
            return;
        }
        int totalHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            int width = listItem.getMeasuredWidth();
            if (width > maxWidth) maxWidth = width;
        }

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listView.getLayoutParams();
        //params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.width = maxWidth;
        listView.setLayoutParams(params);
    }

    private void resetListViewItemWidth(LinearLayout itemLayout) {
        LayoutParams layoutParams = (LayoutParams)itemLayout.getLayoutParams();
        layoutParams.width = MyApplication.W;
    }

    public void setRelayView(View relayView) {
        this.relayView = relayView;
    }
}
