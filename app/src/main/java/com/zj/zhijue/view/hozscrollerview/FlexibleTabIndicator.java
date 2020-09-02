package com.zj.zhijue.view.hozscrollerview;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;


import com.zj.zhijue.util.view.ui.OSUtils;

import java.util.ArrayList;
import java.util.List;



public class FlexibleTabIndicator extends BaseFlexibleTabIndicator implements PageIndicator {
    private OnPageChangeListener mListener;
    protected ViewPager mViewPager;

    public FlexibleTabIndicator(Context context) {
        this(context, null);
    }

    public FlexibleTabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onPageScrollStateChanged(int arg0) {
        if (this.mListener != null) {
            this.mListener.onPageScrollStateChanged(arg0);
        }
    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (this.mListener != null) {
            this.mListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    public void onPageSelected(int i) {
        setCurrentItem(i);
        if (this.mListener != null) {
            this.mListener.onPageSelected(i);
        }
    }

    public void setViewPager(ViewPager view) {
        if (this.mViewPager != view) {
            if (this.mViewPager != null) {
                this.mViewPager.setOnPageChangeListener(null);
            }
            if (view.getAdapter() == null) {
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            }
            this.mViewPager = view;
            view.setOnPageChangeListener(this);
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        updateView();
        setCurrentItem(getSelectedPosition());
    }

    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
        if (this.mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        } else if (OSUtils.isLollipopLetv()) {
            this.mViewPager.setCurrentItem(item, true);
        } else {
            this.mViewPager.setCurrentItem(item, false);
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mListener = listener;
    }

    protected List<CharSequence> getTitleList() {
        if (this.mViewPager != null) {
            PagerAdapter adapter = this.mViewPager.getAdapter();
            if (adapter != null) {
                int count = adapter.getCount();
                List<CharSequence> arrayList = new ArrayList(count);
                for (int i = 0; i < count; i++) {
                    arrayList.add(adapter.getPageTitle(i));
                }
                return arrayList;
            }
        }
        return null;
    }
}
