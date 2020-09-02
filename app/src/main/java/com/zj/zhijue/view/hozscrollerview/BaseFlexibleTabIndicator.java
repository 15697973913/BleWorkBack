package com.zj.zhijue.view.hozscrollerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zj.zhijue.util.thirdutil.Utils;

import java.util.ArrayList;
import java.util.List;




public abstract class BaseFlexibleTabIndicator extends HorizontalScrollView {
    protected static final int DEFAULT_SELECTED_INDEX = 0;
    private static final CharSequence EMPTY_TITLE = "";
    private static final String TAG = BaseFlexibleTabIndicator.class.getSimpleName();
    protected static final int TXT_ID = 10000;
    protected int mHighLightColor;
    protected boolean mIsNotAlignBottom;
    protected int mLineDeltaWidth;
    protected int mLineDrawable;
    protected int mLineHeight;
    protected int mLineTopMargin;
    protected int mNormalColor;
    protected OnClickListener mOnTabClickListener;
    private OnTabReselectedListener mOnTabReselectedListener;
    private OnTabSelectedListener mOnTabSelectedListener;
    private Runnable mScrollRunnable;
    protected int mSelectedPosition;
    protected final IcsLinearLayout mTabLayout;
    protected int mTabPaddingBottom;
    protected int mTabPaddingLeft;
    protected int mTabPaddingRight;
    protected int mTabPaddingTop;
    protected float mTextSize;
    protected final List<TextView> mTitleViewList;

    public interface OnTabReselectedListener {
        void onTabReselected(int i);
    }

    public interface OnTabSelectedListener {
        boolean onTabSelected(int i);
    }

    public static class TabView extends RelativeLayout {
        private int mIndex;

        public TabView(Context context) {
            super(context, null);
        }

        public int getIndex() {
            return this.mIndex;
        }

        public void setIndex(int index) {
            this.mIndex = index;
        }
    }

    protected abstract List<? extends CharSequence> getTitleList();

    public BaseFlexibleTabIndicator(Context context) {
        this(context, null);
    }

    public BaseFlexibleTabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mLineHeight = Utils.dip2px(2.0f);
        this.mTitleViewList = new ArrayList();
        this.mOnTabSelectedListener = null;
        this.mOnTabReselectedListener = null;
        this.mOnTabClickListener = new OnClickListener() {
            public void onClick(View view) {
                //NBSActionInstrumentation.onClickEventEnter(view, this);
                int newSelectedIndex = ((TabView) view).getIndex();
                if (BaseFlexibleTabIndicator.this.mSelectedPosition == newSelectedIndex) {
                    if (BaseFlexibleTabIndicator.this.mOnTabReselectedListener != null) {
                        BaseFlexibleTabIndicator.this.mOnTabReselectedListener.onTabReselected(BaseFlexibleTabIndicator.this.mSelectedPosition);
                    }
                    //NBSActionInstrumentation.onClickEventExit();
                    return;
                }
                boolean canTabSelected = true;
                if (BaseFlexibleTabIndicator.this.mOnTabSelectedListener != null) {
                    canTabSelected = BaseFlexibleTabIndicator.this.mOnTabSelectedListener.onTabSelected(newSelectedIndex);
                }
                if (canTabSelected) {
                    BaseFlexibleTabIndicator.this.setCurrentItem(newSelectedIndex);
                }
                //NBSActionInstrumentation.onClickEventExit();
            }
        };
        setHorizontalScrollBarEnabled(false);
        this.mTabLayout = new IcsLinearLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        addView(this.mTabLayout, layoutParams);
    }

    protected void animateToTab(int position) {
        if (position >= 0 && position < this.mTabLayout.getChildCount()) {
            final View tabView = this.mTabLayout.getChildAt(position);
            if (this.mScrollRunnable != null) {
                removeCallbacks(this.mScrollRunnable);
            }
            this.mScrollRunnable = new Runnable() {
                public void run() {
                    BaseFlexibleTabIndicator.this.smoothScrollTo(tabView.getLeft() - ((BaseFlexibleTabIndicator.this.getWidth() - tabView.getWidth()) / 2), 0);
                    BaseFlexibleTabIndicator.this.mScrollRunnable = null;
                }
            };
            post(this.mScrollRunnable);
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mScrollRunnable != null) {
            post(this.mScrollRunnable);
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mScrollRunnable != null) {
            removeCallbacks(this.mScrollRunnable);
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean lockedExpanded = MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY;//1073741824;
        setFillViewport(lockedExpanded);
        int oldWidth = getMeasuredWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int newWidth = getMeasuredWidth();
        if (lockedExpanded && oldWidth != newWidth) {
            setCurrentItem(this.mSelectedPosition);
        }
    }

    public void setCurrentItem(int position) {
        this.mSelectedPosition = position;
        int tabCount = this.mTabLayout.getChildCount();
        int i = 0;
        while (i < tabCount && i < this.mTitleViewList.size()) {
            View child = this.mTabLayout.getChildAt(i);
            if (i == position) {
                child.setSelected(true);
                setTextViewHighlight((TextView) this.mTitleViewList.get(i));
            } else {
                child.setSelected(false);
                setTextViewNormal((TextView) this.mTitleViewList.get(i));
            }
            i++;
        }
        animateToTab(position);
    }

    protected void updateView() {
        this.mTabLayout.removeAllViews();
        this.mTitleViewList.clear();
        List titleList = getTitleList();
        if (titleList != null) {
            for (int i = 0; i < titleList.size(); i++) {
                addTab(i, (CharSequence) titleList.get(i));
            }
        }
    }

    protected void addTab(int index, CharSequence charSequence) {
        TabView tabView = new TabView(getContext());

        tabView.setIndex(index);
        tabView.setFocusable(true);
        tabView.setOnClickListener(this.mOnTabClickListener);
        tabView.setPadding(this.mTabPaddingLeft, this.mTabPaddingTop, this.mTabPaddingRight, this.mTabPaddingBottom);
        TextView titleTextView = createTabTextView(charSequence);
        titleTextView.setId(index + 10000);
        this.mTitleViewList.add(titleTextView);

        RelativeLayout.LayoutParams textParam = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        tabView.addView(titleTextView, textParam);

        titleTextView.measure(0, 0);
        int textWidth = titleTextView.getMeasuredWidth();
        View lineView = new View(getContext());
        lineView.setBackgroundResource(this.mLineDrawable);
        RelativeLayout.LayoutParams lineParam = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, this.mLineHeight);
        lineParam.width = Math.max(this.mLineDeltaWidth + textWidth, 0);
        lineParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        if (this.mIsNotAlignBottom) {
            lineParam.addRule(RelativeLayout.BELOW, index + 10000);
            lineParam.setMargins(0, this.mLineTopMargin, 0, 0);
        } else {
            lineParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
        tabView.addView(lineView, lineParam);
        this.mTabLayout.addView(tabView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
    }

    protected TextView createTabTextView(CharSequence charSequence) {
        TextView titleTextView = new TextView(getContext());
        if (charSequence == null) {
            charSequence = EMPTY_TITLE;
        }
        titleTextView.setGravity(Gravity.CENTER);
        titleTextView.setText(charSequence);
        titleTextView.setTextSize(0, this.mTextSize);
        return titleTextView;
    }

    protected void setTextViewNormal(TextView tv) {
        if (tv != null) {
            tv.setTextColor(this.mNormalColor);
            tv.setTypeface(null, 0);
        }
    }

    protected void setTextViewHighlight(TextView tv) {
        if (tv != null) {
            tv.setTextColor(this.mHighLightColor);
            tv.setTypeface(null, 1);
        }
    }

    public int getSelectedPosition() {
        return this.mSelectedPosition;
    }

    public IcsLinearLayout getTabLayout() {
        return this.mTabLayout;
    }

    public void setHighLightColor(int highLightColor) {
        this.mHighLightColor = highLightColor;
    }

    public void setNormalColor(int normalColor) {
        this.mNormalColor = normalColor;
    }

    public void setLineHeight(int lineHeight) {
        this.mLineHeight = lineHeight;
    }

    public void setLineDrawableId(int lineDrawableId) {
        this.mLineDrawable = lineDrawableId;
    }

    public void setLineDeltaWidth(int lineDeltaWidth) {
        this.mLineDeltaWidth = lineDeltaWidth;
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
    }

    public void setTabPadding(int left, int top, int right, int bottom) {
        this.mTabPaddingLeft = left;
        this.mTabPaddingTop = top;
        this.mTabPaddingRight = right;
        this.mTabPaddingBottom = bottom;
    }

    public void setLineVerticalRule(boolean isNotAlignBottom, int topMargin) {
        this.mIsNotAlignBottom = isNotAlignBottom;
        this.mLineTopMargin = topMargin;
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.mOnTabSelectedListener = onTabSelectedListener;
    }

    public void setOnTabReselectedListener(OnTabReselectedListener onTabReselectedListener) {
        this.mOnTabReselectedListener = onTabReselectedListener;
    }
}
