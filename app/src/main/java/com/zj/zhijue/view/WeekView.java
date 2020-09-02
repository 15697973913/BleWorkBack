package com.zj.zhijue.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.util.view.ui.DeviceUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class WeekView extends View {

    private Context mContext;
    private Paint mPaint, mChartPaint, backgroundLinePaint;
    private Rect mBound;
    private int mStartWidth, mHeight, mWidth, mChartWidth, mSize;
    private int mPerIntervalWidth = 0;// 每个日期间隔的宽度
    private int lineColor, leftColor, lefrColorBottom,selectLeftColor;
    private float leftVerticalLineWidth = 10;
    private float originalX = DeviceUtils.dipToPx(MyApplication.getInstance(), 30);//坐标原点 X
    private float perHighSize = 0;//每分钟的高度值
    private float maxMinutes = 0;//最大时长(分钟)
    private List<Float> list = new ArrayList<>();
    private getNumberListener listener;
    private int number = 1000;
    private int selectIndex = -1;
    private final int lines = 7;//底部水平线上，竖线的个数（一周7天）
    private final int horizontalLines = 13;
    private float marginBottom;
    private final float mTextSize = 13;
    private final float pointRadius = DeviceUtils.dipToPx(MyApplication.getInstance(), 2);//折线图原点的半径
    private final float verticalLineWidth = DeviceUtils.dipToPx(MyApplication.getInstance(), 1);//
    private List<Integer> xCenterList = new ArrayList<>();
    private List<PointF> pointsConnectList = new ArrayList<>();
    private List<Integer> selectIndexRoles = new ArrayList<>();
    private List<String> dateStringList = new ArrayList<>();
    private List<Float> backgroundLinePosition = new ArrayList<>();
    /**
     * 左侧显示时间小时的文字位置
     */
    private List<Float> leftHoursPostion = new ArrayList<>();

    public void setList(List<Float> list, List<String> dateStringList) {
        this.list = list;
        this.dateStringList = dateStringList;
        getMax(list);
        setSize(getWidth() / (2 * lines + 1));
        mStartWidth = (int)getOriginaX();//getWidth() / (lines + 1);
        mPerIntervalWidth = (getWidth() - mStartWidth * 2) / (lines + 1);
        mChartWidth = mPerIntervalWidth - mSize / 2;
        invalidate();
    }

    private void getMax(List<Float> list) {
        for (int i = 0; i < list.size(); i++) {
            float tmp = list.get(i);
            if (tmp > maxMinutes) {
                maxMinutes = tmp;
            }
        }

        if (maxMinutes <= 0) {
            maxMinutes = 1;
        }
    }

    public WeekView(Context context) {
        this(context, null);
    }

    public WeekView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyChartView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.MyChartView_xyColor:
                    lineColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyChartView_leftColor:
                    // 默认颜色设置为黑色
                    leftColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyChartView_leftColorBottom:
                    lefrColorBottom = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyChartView_selectLeftColor:
                    // 默认颜色设置为黑色
                    selectLeftColor = array.getColor(attr, Color.BLACK);
                    break;
                default:
                    bringToFront();
            }
        }
        array.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
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

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
       /* mWidth = getWidth();
        mHeight = getHeight();
        Log.d("SingleView","mWidth = " + mWidth + " mHeight = " + mHeight);
        perHighSize = (mHeight - marginBottom) * 1.0f / (horizontalLines * 60);
        mStartWidth = getWidth() / (lines + 1);
        setSize(mWidth / (2 * lines + 1));
        mChartWidth = getWidth() /(lines + 1) - mSize / 2;*/
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = getWidth();
        mHeight = getHeight() - getPaddingTop();
        //Log.d("SingleView","mWidth = " + mWidth + " mHeight = " + mHeight);
        perHighSize = (mHeight - marginBottom) * 1.0f / (horizontalLines * 60);
        mStartWidth = (int)getOriginaX();//getWidth() / (lines + 1);
        mPerIntervalWidth = (getWidth() - mStartWidth * 2) / (lines + 1);
        mChartWidth = mPerIntervalWidth - mSize / 2;
        setSize(mWidth / (2 * lines + 1));

        //MLog.d("SingleView onSizeChanged mStartWidth = " +"" + mStartWidth + "  perHighSize = " + perHighSize);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBound = new Rect();
        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);
        marginBottom = DeviceUtils.dipToPx(mContext, 20);
        //setBackgroundColor(Color.parseColor("#f7d194"));
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {

        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            setSize(getWidth() / (2 * lines + 1));
            mStartWidth = (int)getOriginaX();//getWidth() / (lines + 1);
            mPerIntervalWidth = (getWidth() - mStartWidth * 2) / (lines + 1);
            mChartWidth = mPerIntervalWidth - mSize / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //MLog.d("SingleView onDraw mStartWidth = " +"" + mStartWidth + "  perHighSize = " + perHighSize);
        drawBackgroundLine(canvas);
        mPaint.setColor(lineColor);
        //mStartWidth = (int)getOriginaX();
        mStartWidth +=  mPerIntervalWidth;
        for (int i = 0; i < lines; i++) {

            //画数字
            mPaint.setTextSize(DeviceUtils.dipToPx(mContext, mTextSize));
            mPaint.setTextAlign(Paint.Align.CENTER);
            String dateStr = dateStringList.get(i);
            dateStr = dateStr.substring(dateStr.indexOf(".") + 1);

            mPaint.getTextBounds(dateStr, 0, String.valueOf(i).length(), mBound);
            canvas.drawText(dateStr, mStartWidth - mBound.width() * 1 / 2,
                mHeight - mBound.height() / 2 , mPaint);
            mStartWidth +=  mPerIntervalWidth;//getWidth() / (lines + 1);
        }
        xCenterList.clear();
        pointsConnectList.clear();
        mStartWidth = (int)getOriginaX();
        mStartWidth +=  mPerIntervalWidth;
        for (int i = 0; i < lines; i++) {
            mChartPaint.setStyle(Paint.Style.FILL);
            if (list.size() > 0) {
                if (selectIndexRoles.contains(i)){
                    //mChartPaint.setShader(null);
                    mChartPaint.setColor(selectLeftColor);
                }
                else {
                    /*LinearGradient lg = new LinearGradient(mChartWidth, mChartWidth + mSize, mHeight - 100,
                        (float) (mHeight - 100 - list.get(i) * size), lefrColorBottom, leftColor, Shader.TileMode.MIRROR);
                    mChartPaint.setShader(lg);*/
                    mChartPaint.setColor(selectLeftColor);
                }
                //画柱状图
              /*  RectF rectF = new RectF();
                rectF.left = mStartWidth - mSize/2;
                rectF.right = mStartWidth + mSize/2;;
                rectF.bottom = mHeight - marginBottom;
                rectF.top = mHeight - (marginBottom + list.get(i) * perHighSize);*/

                PointF point = new PointF();
                point.x = mStartWidth;
                point.y = mHeight - (marginBottom + list.get(i) * perHighSize);
                pointsConnectList.add(point);

                //canvas.drawRoundRect(rectF, 0, 0, mChartPaint);
                xCenterList.add(mStartWidth);
                //mChartWidth += getWidth() / (lines + 1);
                mStartWidth += mPerIntervalWidth;
            }
        }

        drawBrokenLines(canvas);

        /**
         * 复位
         */
        mStartWidth = (int)getOriginaX();
        mPerIntervalWidth = (getWidth() - mStartWidth * 2) / (lines + 1);
        mChartWidth = mPerIntervalWidth - mSize / 2;
    }

    /**
     * 绘制背景线条
     * @param canvas
     */
    private void drawBackgroundLine(Canvas canvas) {
        createLinePainte();
        getAllLinesPosition();
        backgroundLinePaint.setColor(getResources().getColor(R.color.selectLeftColor));
        canvas.drawLines(getAllLinesPosition(),backgroundLinePaint);
        drawLeftHourText(canvas);
        drawVerticalArrow(canvas);
        drawHorizontal(canvas);
    }

    /**
     * 画纵向箭头
     */
    private void drawVerticalArrow(Canvas canvas) {
        float StartX = getOriginaX();

        //终点的箭头
        Path path = new Path();
        path.moveTo(StartX, 0);
        path.lineTo(StartX* 3 / 4, StartX/2);
        path.lineTo(StartX + StartX/4, StartX/2);
        path.close();
        canvas.drawPath(path, backgroundLinePaint);
    }


    /**
     * 画横向的箭头
     * @param canvas
     */
    private void drawHorizontal(Canvas canvas) {
        float StartX = getOriginaX();

        int totalWidth = getWidth();
        int arrowStartX = (int) (totalWidth - StartX);
        int arrowStartY = (int) (mHeight - marginBottom);

        //终点的箭头
        Path path = new Path();
        path.moveTo(arrowStartX, arrowStartY);
        path.lineTo(arrowStartX - StartX* 1 / 2, arrowStartY - StartX* 1 / 4 );
        path.lineTo(arrowStartX - StartX* 1 / 2, arrowStartY + StartX* 1 / 4 );
        path.close();
        canvas.drawPath(path, backgroundLinePaint);
    }

    /**
     * 画折线图(从坐标轴原点开始)
     */
    private void drawBrokenLines(Canvas canvas) {
        float originalX = getOriginaX();
        float originalY = mHeight - marginBottom;
        List<Float> pointList = new ArrayList<>();

        pointList.add(originalX);
        pointList.add(originalY);
        int pointsSize = pointsConnectList.size();

        for (int i = 0; i < pointsSize; i++) {
            PointF pointF = pointsConnectList.get(i);
            if (i != pointsSize - 1) {
                pointList.add(pointF.x);
                pointList.add(pointF.y);
                pointList.add(pointF.x);
                pointList.add(pointF.y);
            } else {
                pointList.add(pointF.x);
                pointList.add(pointF.y);
            }
        }

        float[] floats = new float[pointList.size()];

        for (int i = 0; i < floats.length; i++) {
            floats[i] = pointList.get(i);
        }
        mChartPaint.setColor(selectLeftColor);
        canvas.drawLines(floats, mChartPaint);
        drawPoint(canvas);
    }

    /**
     * 画折线图上的点
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {

        for (int i = 0; i < pointsConnectList.size(); i++) {
            PointF pointF = pointsConnectList.get(i);

            if (mHeight - marginBottom < pointF.y + pointRadius) {
                pointF.y = mHeight - marginBottom - pointRadius;
            }

            canvas.drawCircle(pointF.x, pointF.y, pointRadius, mChartPaint);
        }

    }

    private float[] getAllLinesPosition() {
        backgroundLinePosition.clear();
        leftHoursPostion.clear();
        /**
         * 添加横向的背景线
         */
        originalX = getOriginaX();
        /**
         * horizontalLines - 2 是为了 Y 轴留出箭头的高度
         */
        for (int i = 0; i < horizontalLines - 2; i++) {
            backgroundLinePosition.add(originalX);
            backgroundLinePosition.add((float) (mHeight - marginBottom) * (1f - i * 1f / horizontalLines));

            if (i == 0) {
                /**
                 * 最底部水平线的最右侧的 X 坐标
                 */
                backgroundLinePosition.add((float)mWidth - originalX);
            } else {
                backgroundLinePosition.add(originalX + DeviceUtils.dipToPx(MyApplication.getInstance(), leftVerticalLineWidth));
            }

            backgroundLinePosition.add((float) (mHeight - marginBottom) * (1f - i * 1f / horizontalLines));
            if(i > 0) {
                leftHoursPostion.add((float) (mHeight - marginBottom) * (1f - i * 1f / horizontalLines));
            }
        }

        /**
         * 添加 Y 轴的坐标位置
         */
        {
            backgroundLinePosition.add(originalX);
            backgroundLinePosition.add(mHeight - marginBottom);
            backgroundLinePosition.add(originalX);
            backgroundLinePosition.add(0f);
        }


        float[] floats = new float[backgroundLinePosition.size()];
        for (int i = 0; i < floats.length; i++) {
            floats[i] = backgroundLinePosition.get(i);
        }
        return floats;
    }

    /**
     * 绘制左侧的小时文字
     * @param canvas
     */
    private void drawLeftHourText(Canvas canvas) {
        Rect mLeftBound = new Rect();
        backgroundLinePaint.setColor(getResources().getColor(R.color.selectLeftColor));
        for (int i = 0; i < leftHoursPostion.size(); i++) {
            backgroundLinePaint.setTextSize(DeviceUtils.dipToPx(mContext, mTextSize));
            backgroundLinePaint.setTextAlign(Paint.Align.CENTER);
            String dateStr = (i + 1) + "h";
            float hourTextHeight = Math.abs(backgroundLinePaint.ascent() + backgroundLinePaint.descent());
            backgroundLinePaint.getTextBounds("24h", 0, "24h".length(), mLeftBound);
            canvas.drawText(dateStr, mLeftBound.width() * 0.5f, leftHoursPostion.get(i) + mLeftBound.height()* 0.5f, backgroundLinePaint);
        }
    }

    private float getOriginaX() {
        return getLeftHourTextWidth() * 1.1f;
    }

    private float getBottomTextHeight() {
        mPaint.setTextSize(DeviceUtils.dipToPx(mContext, mTextSize));
        mPaint.setTextAlign(Paint.Align.CENTER);
        String dateStr = "12.31";
        mPaint.getTextBounds(dateStr, 0, dateStr.length(), mBound);
        return mBound.height();
    }

    private float getLeftHourTextWidth() {
        if (null == backgroundLinePaint) {
            backgroundLinePaint = new Paint();
        }

        Rect mLeftBound = new Rect();
        backgroundLinePaint.setColor(getResources().getColor(R.color.selectLeftColor));
        backgroundLinePaint.setTextSize(DeviceUtils.dipToPx(mContext, mTextSize));
        backgroundLinePaint.setTextAlign(Paint.Align.CENTER);
        backgroundLinePaint.getTextBounds("24h", 0, "24h".length(), mLeftBound);
        return mLeftBound.width();
    }

    private void createLinePainte() {
        if (null == backgroundLinePaint) {
            backgroundLinePaint = new Paint();
        }
        backgroundLinePaint.setAntiAlias(true);
        backgroundLinePaint.setColor(getResources().getColor(R.color.selectLeftColor));
        backgroundLinePaint.setStyle(Paint.Style.FILL);
        backgroundLinePaint.setStrokeWidth(3);
    }

    private void setSize(int size) {
        /**
         * 设置柱状图的宽度
         */
        this.mSize = (int)verticalLineWidth;//size  / 4;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int left = 0;
        int top = 0;
        int bottom = (int) (mHeight - marginBottom);
        int right = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < lines; i++) {
                    PointF pointConnected = pointsConnectList.get(i);

                    left = (int)(pointConnected.x - mPerIntervalWidth / 2);
                    right = (int)(pointConnected.x + mPerIntervalWidth / 2);

                    Rect rect = new Rect(left, top, right, bottom);
                    
                    if (rect.contains(x, y)) {
                        if (listener != null){
                            int touchx = xCenterList.get(i);
                            Log.d("SingleView", " x = " + x + " touchx = " + touchx);
                            int touchy = (int)(marginBottom + list.get(i) * perHighSize);//(int)(mHeight - 2 * marginBottom - (int)(list.get(i) * perHighSize));

                        /*    PointF pointF = pointsConnectList.get(i);
                            float intervalx = pointF.x - touchx;
                            float intervaly = pointF.y - touchy;
                            MLog.d("intervalx = " + intervalx + "  intervaly = " + intervaly);*/

                            listener.getNumber(i, touchx, touchy);
                            number = i;
                            selectIndex = i;
                            selectIndexRoles.clear();
                            selectIndexRoles.add(selectIndex);
                            invalidate();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    public void setListener(getNumberListener listener) {
        this.listener = listener;
    }

    public void clearSelectedRoles() {
        selectIndexRoles.clear();
    }

    public interface getNumberListener {
        void getNumber(int number, int x, int y);
    }

    public static List<WeekDay> getWeekDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置周一为一周的第一天
        calendar.setTime(date);
        // 获取本周的第一天
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        List<WeekDay> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek + i);
            WeekDay weekDay = new WeekDay();
            // 获取星期的显示名称，例如：周一、星期一、Monday等等
            weekDay.week = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.CHINESE);
            weekDay.day = new SimpleDateFormat("yyyy.MM.dd").format(calendar.getTime());
            ///weekDay.allTime = DateUtil.localformatterDay.format(calendar.getTime());
            list.add(weekDay);
        }

        return list;
    }

    public static class WeekDay {
        /** 星期的显示名称*/
        public String week;
        /** 对应的日期*/
        public String day;

        @Override
        public String toString() {
            return "WeekDay{" +
                    "week='" + week + '\'' +
                    ", day='" + day + '\'' +
                    '}';
        }
    }
}
