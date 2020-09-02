package com.zj.zhijue.view.hozscrollerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zj.zhijue.MyApplication;
import com.zj.zhijue.bean.ImageBean;
import com.zj.zhijue.util.thirdutil.Utils;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.zj.zhijue.util.view.ui.OSUtils;


import java.util.ArrayList;
import java.util.List;


public class ImageTabIndicator extends FlexibleTabIndicator {
    private static final int BG_CORNER_RADII = Utils.dip2px(15.0f);
    private static final int BG_DEFAULT_BEGIN_COLOR = Color.parseColor("#ff700a");//-12340919;
    private static final int BG_DEFAULT_END_COLOR =  Color.parseColor("#fd2c3a");;//-13128772;
    private static final int BG_HORIZONTAL_MARGIN = Utils.dip2px(0.0f);
    private static final int BG_VERTICAL_MARGIN = Utils.dip2px(8.0f);
    private static final int DEFAULT_ICON_HEIGHT = Utils.dip2px(42.0f);
    private static final int DEFAULT_ICON_WIDTH = Utils.dip2px(60.0f);
    private static final int HORIZONTAL_MARGIN = Utils.dip2px(5.0f);
    private static final int VERTICAL_MARGIN = Utils.dip2px(1.0f);
    private static final int ICON_MAX_HEIGHT = (Utils.dip2px(44.0f) - (VERTICAL_MARGIN * 2));
    private static final int divideCount = 3;//等分数目
    private static final int columnWidth = (int)(MyApplication.W * 0.8f*1f/divideCount);
    protected final List<ImageView> mBackgroundList = new ArrayList();
    protected int mDefaultResourceId = -1;
    protected OnDisplayListener mDisplayListener;
    protected final List<ImageView> mImageViewList = new ArrayList();
    private List<IndicatorItemBean> mItemList = new ArrayList();
    protected List<TabView> mTabViewList = new ArrayList();
    private List<CharSequence> mTitleList = new ArrayList();

    public interface OnDisplayListener {
        void onItemDisplay(int i);
    }

    public static class IndicatorItemBean {
        private String defaultFontColor;
        private ImageBean defaultIcon;
        private ImageBean selectedBg;
        private String selectedBgColor;
        private String selectedFontColor;
        private ImageBean selectedIcon;
        private String title;

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ImageBean getDefaultIcon() {
            return this.defaultIcon;
        }

        public void setDefaultIcon(ImageBean defaultIcon) {
            this.defaultIcon = defaultIcon;
        }

        public ImageBean getSelectedIcon() {
            return this.selectedIcon;
        }

        public void setSelectedIcon(ImageBean selectedIcon) {
            this.selectedIcon = selectedIcon;
        }

        public String getDefaultFontColor() {
            return this.defaultFontColor;
        }

        public void setDefaultFontColor(String defaultFontColor) {
            this.defaultFontColor = defaultFontColor;
        }

        public String getSelectedFontColor() {
            return this.selectedFontColor;
        }

        public void setSelectedFontColor(String selectedFontColor) {
            this.selectedFontColor = selectedFontColor;
        }

        public ImageBean getSelectedBg() {
            return this.selectedBg;
        }

        public void setSelectedBg(ImageBean selectedBg) {
            this.selectedBg = selectedBg;
        }

        public String getSelectedBgColor() {
            return this.selectedBgColor;
        }

        public void setSelectedBgColor(String selectedBgColor) {
            this.selectedBgColor = selectedBgColor;
        }
    }

    public ImageTabIndicator(Context context) {
        super(context);
    }

    public ImageTabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void updateData(ArrayList<IndicatorItemBean> list, int defaultSelectedIndex) {
        this.mItemList = list;
        updateView();
        setCurrentItem(defaultSelectedIndex);
    }

    protected void updateView() {
        clearViews();
        if (this.mItemList != null) {
            for (int i = 0; i < this.mItemList.size(); i++) {
                addTab(i, (IndicatorItemBean) this.mItemList.get(i));
            }
        }
    }

    protected List<CharSequence> getTitleList() {
        return this.mTitleList;
    }

    protected void clearViews() {
        this.mTabLayout.removeAllViews();
        this.mTabViewList.clear();
        this.mTitleViewList.clear();
        this.mImageViewList.clear();
        this.mBackgroundList.clear();
    }

    private void addTab(int index, IndicatorItemBean item) {
        if (item != null) {
            if (item.getDefaultIcon() == null || item.getSelectedIcon() == null) {
                addTextTab(index, ((IndicatorItemBean) this.mItemList.get(index)).getTitle(), item.getSelectedBg(), item.getSelectedBgColor());
                this.mImageViewList.add(null);
                return;
            }
            //addIconTab(index, item.getDefaultIcon(), item.getSelectedIcon(), item.getSelectedBg(), item.getSelectedBgColor());
            this.mTitleViewList.add(null);
        }
    }

    private void addIconTab(int index, ImageBean defaultIcon, ImageBean selectedIcon, ImageBean background, String bgColor) {
        TabView tabView = new TabView(getContext());
        tabView.setIndex(index);
        tabView.setFocusable(true);
        tabView.setOnClickListener(this.mOnTabClickListener);
        tabView.setPadding(this.mTabPaddingLeft, this.mTabPaddingTop, this.mTabPaddingRight, this.mTabPaddingBottom);
        ImageView bgView = new ImageView(getContext());
        bgView.setScaleType(ScaleType.FIT_XY);
        setTabBackground(bgView, bgColor, background);

        RelativeLayout.LayoutParams lpBg = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lpBg.addRule(RelativeLayout.CENTER_IN_PARENT);

        tabView.addView(bgView, lpBg);
        this.mBackgroundList.add(bgView);
        ImageView view = new ImageView(getContext());
        view.setScaleType(ScaleType.FIT_CENTER);
       // ImageUtil.with(getContext()).displayImage(view, defaultIcon.getUrl(), null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);//getWiderIconLayoutParams(view, defaultIcon, selectedIcon);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);

        lp.setMargins(HORIZONTAL_MARGIN, VERTICAL_MARGIN, HORIZONTAL_MARGIN, VERTICAL_MARGIN);
        tabView.addView(view, lp);
        this.mTabViewList.add(tabView);
        this.mTabLayout.addView(tabView, new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT));
        tabView.measure(0, 0);
        FrameLayout.LayoutParams newLpBg = (FrameLayout.LayoutParams) bgView.getLayoutParams();
        newLpBg.width = tabView.getMeasuredWidth();
        bgView.setPadding(BG_HORIZONTAL_MARGIN, BG_VERTICAL_MARGIN, BG_HORIZONTAL_MARGIN, BG_VERTICAL_MARGIN);
        bgView.setLayoutParams(newLpBg);
        this.mImageViewList.add(view);
    }

    private void addTextTab(int index, CharSequence charSequence, ImageBean background, String bgColor) {
        TabView tabView = new TabView(getContext());
        tabView.setIndex(index);
        tabView.setFocusable(true);
        tabView.setOnClickListener(this.mOnTabClickListener);
        tabView.setPadding(this.mTabPaddingLeft, this.mTabPaddingTop, this.mTabPaddingRight, this.mTabPaddingBottom);
        ImageView bgView = new ImageView(getContext());
        bgView.setScaleType(ScaleType.FIT_XY);
        setTabBackground(bgView, bgColor, background);
        RelativeLayout.LayoutParams lpBg = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lpBg.addRule(RelativeLayout.CENTER_IN_PARENT);
        tabView.addView(bgView, lpBg);

        this.mBackgroundList.add(bgView);
        TextView titleTextView = createTabTextView(charSequence);
        titleTextView.setWidth((int)DeviceUtils.dipToPx(MyApplication.getInstance(), 40));
        titleTextView.setId(index + 10000);
        this.mTitleViewList.add(titleTextView);
        RelativeLayout.LayoutParams textParam = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        textParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        tabView.addView(titleTextView, textParam);
        titleTextView.measure(0, 0);
        int textWidth = titleTextView.getMeasuredWidth();

        View lineView = new View(getContext());
        lineView.setBackgroundResource(this.mLineDrawable);
        RelativeLayout.LayoutParams lineParam = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, this.mLineHeight);
        lineParam.width = Math.max(this.mLineDeltaWidth + textWidth, 0);
        lineParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        if (this.mIsNotAlignBottom) {
            lineParam.addRule(RelativeLayout.BELOW, index + 10000);
            lineParam.setMargins(0, this.mLineTopMargin, 0, 0);
        } else {
            lineParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }

        tabView.addView(lineView, lineParam);
        RelativeLayout.LayoutParams newLpBg = (RelativeLayout.LayoutParams) bgView.getLayoutParams();
        newLpBg.width = Utils.dip2px(20.0f) + textWidth;
        newLpBg.setMargins(0, BG_VERTICAL_MARGIN, 0, BG_VERTICAL_MARGIN);
        bgView.setLayoutParams(newLpBg);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(columnWidth, FrameLayout.LayoutParams.MATCH_PARENT);//设置每一个滑动Item的宽度
        lp.gravity = Gravity.CENTER;
        this.mTabViewList.add(tabView);
        this.mTabLayout.addView(tabView, lp);
    }

    private void setTabBackgroundImage(final ImageView view, ImageBean selectedBg) {
        final String url = selectedBg.getUrl();
    }

    private void setTabBackgroundColor(ImageView view, int[] colorArr) {
        float[] corners = new float[]{(float) BG_CORNER_RADII, (float) BG_CORNER_RADII, (float) BG_CORNER_RADII, (float) BG_CORNER_RADII, (float) BG_CORNER_RADII, (float) BG_CORNER_RADII, (float) BG_CORNER_RADII, (float) BG_CORNER_RADII};
        GradientDrawable drawable = new GradientDrawable(Orientation.LEFT_RIGHT, colorArr);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadii(corners);
        view.setImageDrawable(drawable);
    }

    private int[] getBgColorArray(String color) {
        int[] colorIntArr = new int[2];
        String[] colorArray = color.split("-");
        if (colorArray == null || colorArray.length <= 0) {
            colorIntArr[0] = BG_DEFAULT_BEGIN_COLOR;
            colorIntArr[1] = BG_DEFAULT_END_COLOR;
        } else if (colorArray.length >= 2) {
            colorIntArr[0] = Utils.parseColor(colorArray[0], "ff");
            colorIntArr[1] = Utils.parseColor(colorArray[1], "ff");
        } else if (colorArray.length == 1) {
            colorIntArr[0] = Utils.parseColor(colorArray[0], "ff");
            colorIntArr[1] = Utils.parseColor(colorArray[0], "ff");
        }
        return colorIntArr;
    }

    private void setTabBackground(ImageView view, String color, ImageBean selectedBg) {
        if (!TextUtils.isEmpty(color)) {
            int[] colorIntArr = getBgColorArray(color);
            if (colorIntArr != null && colorIntArr.length == 2) {
                setTabBackgroundColor(view, colorIntArr);
                return;
            }
        }
       /* if (selectedBg == null || TextUtils.isEmpty(selectedBg.getUrl())) {
            setTabBackgroundColor(view, new int[]{BG_DEFAULT_BEGIN_COLOR, BG_DEFAULT_END_COLOR});
        } else {
            setTabBackgroundImage(view, selectedBg);
        }*/
    }

    public void setCurrentItem(int position) {
        this.mSelectedPosition = position;
        int tabCount = this.mTabLayout.getChildCount();
        int i = 0;
        while (i < tabCount && i < this.mTitleViewList.size()) {
            View child = this.mTabLayout.getChildAt(i);
            if (i == position) {
                child.setSelected(true);
                setTextViewHighlight((TextView) this.mTitleViewList.get(i), Utils.parseColor(((IndicatorItemBean) this.mItemList.get(i)).getSelectedFontColor(), "ff"));
                setImage((ImageView) this.mImageViewList.get(i), ((IndicatorItemBean) this.mItemList.get(i)).getSelectedIcon());
                setSelectedBackgroundVisibility(i, true);
            } else {
                child.setSelected(false);
                setTextViewNormal((TextView) this.mTitleViewList.get(i), Utils.parseColor(((IndicatorItemBean) this.mItemList.get(i)).getDefaultFontColor(), "ff"));
                setImage((ImageView) this.mImageViewList.get(i), ((IndicatorItemBean) this.mItemList.get(i)).getDefaultIcon());
                setSelectedBackgroundVisibility(i, false);
            }
            i++;
        }
        animateToTab(position);
        if (this.mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        } else if (OSUtils.isLollipopLetv()) {
            this.mViewPager.setCurrentItem(position, true);
        } else {
            this.mViewPager.setCurrentItem(position, false);
        }
    }

    public void setDefaultBackground(int resId) {
        this.mDefaultResourceId = resId;
    }

    private void setSelectedBackgroundVisibility(int index, boolean bVisibility) {
        ImageView bgView = (ImageView) this.mBackgroundList.get(index);
        if (bgView != null) {
            bgView.setVisibility(bVisibility ? VISIBLE : INVISIBLE);
        }
    }

    private void setImage(ImageView view, ImageBean image) {
        if (view != null && image != null && image.getUrl() != null) {
            //ImageUtil.with(getContext()).displayImage(view, image.getUrl(), null);
        }
    }

    protected void setTextViewNormal(TextView tv, int color) {
        if (tv != null) {
            tv.setTextColor(color);
            tv.setTypeface(null, Typeface.NORMAL);
        }
    }

    protected void setTextViewHighlight(TextView tv, int color) {
        if (tv != null) {
            tv.setTextColor(color);
            tv.setTypeface(null, Typeface.BOLD);
        }
    }

    public void setOnDisplayListener(OnDisplayListener listener) {
        this.mDisplayListener = listener;
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        int size = this.mTabViewList.size();
        for (int i = 0; i < size; i++) {
            boolean isViewShown = isViewShown((TabView) this.mTabViewList.get(i));
            if (this.mDisplayListener != null && isViewShown) {
                this.mDisplayListener.onItemDisplay(i);
            }
        }
    }

    public boolean isViewShown(View view) {
        if (view.getGlobalVisibleRect(new Rect()) && view.isShown()) {
            return true;
        }
        return false;
    }
}
