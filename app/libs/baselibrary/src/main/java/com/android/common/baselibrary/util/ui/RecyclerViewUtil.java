package com.android.common.baselibrary.util.ui;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class RecyclerViewUtil {
    private boolean enable = true;
    private RecyclerView mRecyclerView;
    private OnLoadMoreListener mOnLoadMoreListener;

    private GestureDetector mGestureDetector = null;
    private RecyclerView.SimpleOnItemTouchListener mSimpleOnItemTouchListener;
    private OnItemClickListener mOnItemClickListener = null;
    private OnItemChildClickListener mOnItemChildClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;

    public RecyclerViewUtil(Context mContext, RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;

        mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
            //长按事件
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                if (mOnItemLongClickListener != null) {
                    View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null) {
                        int position = mRecyclerView.getChildLayoutPosition(childView);
                        mOnItemLongClickListener.onItemLongClick(position, childView);
                    }
                }
            }

            //单击事件
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (!(null == mOnItemChildClickListener && null == mOnItemClickListener)) {

                    float xfloat = e.getX();
                    float yfloat = e.getY();
                    float xRawFlaot = e.getRawX();
                    float yRawFlaot = e.getRawY();
                    View childView = mRecyclerView.findChildViewUnder(xfloat, yfloat);

                    if (childView != null) {
                        /*ImageView imageView = childView.findViewById(R.id.collectimageview);
                        boolean isChildCick = isTouchPointInView(imageView, (int) xRawFlaot, (int) yRawFlaot);
                        int position = mRecyclerView.getChildLayoutPosition(childView);
                        if (isChildCick) {
                            if (null != mOnItemChildClickListener) {
                                mOnItemChildClickListener.onItemChildClick(position, imageView);
                            }
                        } else {
                            if (null != mOnItemClickListener) {
                                mOnItemClickListener.onItemClick(position, childView);
                            }
                        }*/
                        return true;
                    }

                }

                return super.onSingleTapUp(e);
            }
        });

        mSimpleOnItemTouchListener = new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (mGestureDetector.onTouchEvent(e)) {
                    return true;
                }
                return false;
            }
        };

        mRecyclerView.addOnItemTouchListener(mSimpleOnItemTouchListener);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mOnLoadMoreListener = listener;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isBottom = isBottom(mRecyclerView);
                //LogUtils.d("isBottom = " + isBottom + "  newState = " + newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isBottom) {
                    if (enable && mOnLoadMoreListener != null) {
                        //LogUtils.d("isBottom = " + isBottom + "  newState = " + newState + " onLoadMore()");
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }

    /**
     * true 打开上拉加载 false，关闭上拉加载
     *
     * @param enable
     */
    public void setLoadMoreEnable(boolean enable) {
        this.enable = enable;
    }

    private boolean isBottom(RecyclerView recyclerView) {
        if (recyclerView == null)
            return false;

        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;

        return false;
    }

    public interface OnLoadMoreListener {
        public void onLoadMore();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        mOnItemChildClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    //长按事件接口
    public interface OnItemLongClickListener {
        public void onItemLongClick(int position, View view);
    }

    //单击事件接口
    public interface OnItemClickListener {
        public void onItemClick(int position, View view);
    }

    //子View单击事件接口
    public interface OnItemChildClickListener {
        public void onItemChildClick(int position, View view);
    }

    //(x,y)是否在view的区域内
    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        //view.isClickable() &&
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }
}