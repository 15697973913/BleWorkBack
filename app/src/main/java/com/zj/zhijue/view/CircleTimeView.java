package com.zj.zhijue.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zj.zhijue.R;


/**
 * 圆形时间图
 */
public class CircleTimeView extends View {
    private final String TAG = CircleTimeView.class.getSimpleName();

    private float mWidth = 0;
    private float mHeight = 0;
    private float mRadius = 0;
    private float paintWidth = 0;
    private float mStartCircleRadius = 0;
    private float backGroundDegree = 360;
    private int defaultMaxTrainMinutes = 5 * 60;
    private Paint mPaint = null;
    private Paint backGroundPaint = null;
    private Paint mStartCirclePaint = null;
    private int trainMinutes = 30;


    public CircleTimeView(Context context) {
        this(context, null);
    }

    public CircleTimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged w = " + w + " h = " + h + "  oldw = " + oldw + " oldh = " + oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        mRadius = Math.min(mWidth, mHeight) / 2;
        paintWidth = mRadius / 25F;
        mStartCircleRadius = paintWidth * 1.5f;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout left = " + left + " top = " + top + "  right = " + right + " bottom = " + bottom);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width;
        int height;
        int miniWidth = getMinimumWidth();
        int miniHeight = getMinimumHeight();
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = widthSize * 1 / 2;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = heightSize * 1 / 2;
        }
        if (width < miniWidth) {
            width = miniWidth;
        }

        if (height < miniHeight) {
            height = miniHeight;
        }
        width = height = Math.min(width, height);

        int widthMeasure = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int heightMeasure = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasure, heightMeasure);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        createPaint();
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        RectF rectF = new RectF();
        rectF.set((paintWidth / 2F), (paintWidth / 2F), mWidth - mStartCircleRadius, mHeight - (paintWidth / 2F));
        canvas.rotate(90, mWidth / 2 , mHeight / 2);
        canvas.drawArc(rectF, 0, backGroundDegree, false, backGroundPaint);
        //canvas.drawArc(rectF, 0, getDegree(), false, mPaint);
        //createStartCircle(canvas);
    }

    /**
     * 绘制0度时开始的实现小圆心
     */
    private void createStartCircle(Canvas canvas) {
        canvas.drawCircle(mWidth - mStartCircleRadius, mHeight / 2, mStartCircleRadius, mStartCirclePaint);
    }

    private void createPaint() {
        if (null == backGroundPaint) {
            backGroundPaint = new Paint();
        }

        backGroundPaint.setAntiAlias(true);
        backGroundPaint.setColor(getResources().getColor(R.color.top_unsignin_text_color));
        backGroundPaint.setStyle(Paint.Style.FILL);
        backGroundPaint.setStrokeWidth(paintWidth);

        if (null == mPaint) {
            mPaint = new Paint();
        }
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.selectLeftColor));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(paintWidth);

        if (null == mStartCirclePaint) {
            mStartCirclePaint = new Paint();
        }

        mStartCirclePaint.setAntiAlias(true);
        mStartCirclePaint.setColor(getResources().getColor(R.color.selectLeftColor));
        mStartCirclePaint.setStyle(Paint.Style.FILL);
        mStartCirclePaint.setStrokeWidth(paintWidth);
    }

    private float getDegree() {
        return backGroundDegree * (trainMinutes * 1.0f / defaultMaxTrainMinutes);
    }

    public void setTrainMinutes(int trainMinutes) {
        this.trainMinutes = trainMinutes;
    }

    public void setDefaultMaxTime(int maxMinutesTime) {
        if (maxMinutesTime <= 0) {
            return;
        }
        defaultMaxTrainMinutes = maxMinutesTime;
    }
}
