package com.zj.zhijue.adapter;

import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.zj.zhijue.R;
import com.zj.zhijue.listener.ItemOfViewPagerOnClickListener;
import com.zj.zhijue.helper.TrainTimeAdapterHelper;

import java.util.List;

public class TrainTimeAdapter extends PagerAdapter {
    private List<String> mDataList;
    private ItemOfViewPagerOnClickListener mItemOfViewPagerOnClickListener;
    private TrainTimeAdapterHelper mTrainTimeAdapterHeler;

    public TrainTimeAdapter(List<String> dataList,ItemOfViewPagerOnClickListener itemOfViewPagerOnClickListener) {
        this.mDataList = dataList;
        this.mItemOfViewPagerOnClickListener = itemOfViewPagerOnClickListener;
        initHelper();
    }

    private void initHelper() {
        mTrainTimeAdapterHeler = new TrainTimeAdapterHelper();
        mTrainTimeAdapterHeler.addItemOfViewPagerListener(mItemOfViewPagerOnClickListener);
    }

    @Override
    public int getCount() {
        return null == mDataList ? 0 : mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = createView(container, position);
        mTrainTimeAdapterHeler.addViewListener(view, position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        /*TextView textView = (TextView) object;
        String text = textView.getText().toString();
        int index = mDataList.indexOf(text);
        if (index >= 0) {
            MLog.d("getItemPosition index = " + index + " text = " + text);
            return index;
        }
         */
        //MLog.d("getItemPosition POSITION_NONE = " + POSITION_NONE);
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }

    private View createView(ViewGroup container, int position) {
        View view = null;
        if (position >= 0 && position < mDataList.size()) {

            LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
            switch (position) {
                case 0:
                    view = layoutInflater.inflate(R.layout.fragment_viewpager_item_one, null);
                    mTrainTimeAdapterHeler.initDatePicker(view);
                    break;

                case 1:
                    view = layoutInflater.inflate(R.layout.fragment_viewpager_item_two, null);
                    mTrainTimeAdapterHeler.initSingleView(view);
                    break;

                case 2:
                    view = layoutInflater.inflate(R.layout.fragment_viewpager_item_three, null);
                    mTrainTimeAdapterHeler.initTodayCircleView(view);
                    break;

                case 3:
                    view = layoutInflater.inflate(R.layout.fragment_viewpager_item_four, null);
                    mTrainTimeAdapterHeler.initYesterDayCircleView(view);
                    break;

                default:
                    break;
            }
        }

        return view;
    }


}
