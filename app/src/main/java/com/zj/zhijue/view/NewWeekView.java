package com.zj.zhijue.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.bean.TrainWeekViewBean;
import com.zj.zhijue.util.view.ui.DeviceUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewWeekView extends View {

    private Context mContext;
    private Paint mPaint, mChartPaint, backgroundLinePaint, mPointPaint;
    private Rect mBound;
    private int mStartWidth, mHeight, mWidth, mChartWidth, mSize;
    private int mPerIntervalWidth = 0;// 每个日期间隔的宽度
    private int lineColor, leftColor, lefrColorBottom, selectLeftColor;
    private float leftVerticalLineWidth = 5;
    private float originalX = DeviceUtils.dipToPx(MyApplication.getInstance(), 30);//坐标原点 X
    private float arrowHeight = DeviceUtils.dipToPx(MyApplication.getInstance(), 6);
    private float arrowWidth = DeviceUtils.dipToPx(MyApplication.getInstance(), 5);
    private float perHighSize = 0;//每秒钟的高度值  *3600 就是每个刻度的高度值
    private List<TrainWeekViewBean> list = new ArrayList<>();
    private getNumberListener listener;
    private int number = 1000;
    private int selectIndex = -1;
    private final int lines = 7;//底部水平线上，竖线的个数（一周7天）
    private final int horizontalLines = 12;
    private float marginBottom;
    private final float mTextSize = 13;
    private final float pointRadius = DeviceUtils.dipToPx(MyApplication.getInstance(), 3);//折线图原点的半径
    private final float verticalLineWidth = DeviceUtils.dipToPx(MyApplication.getInstance(), 7);//
    private final float brokenLinesWidth = DeviceUtils.dipToPx(MyApplication.getInstance(), 1);//
    private List<Integer> xCenterList = new ArrayList<>();
    private List<PointF> pointsConnectList = new ArrayList<>();//训练时长

    private List<Integer> selectIndexRoles = new ArrayList<>();
    private List<String> dateStringList = new ArrayList<>();
    private List<Float> backgroundLinePosition = new ArrayList<>();
    private final String leftMaxText = "10h";
    private final float startEyeSight = 1f;
    private float twoSpaceBetweenMultiple = 1.5f;//(1 到 2 之间) X轴两侧的空白区间的宽度，以左侧的空白为基准，显示右侧的空白距离，小数点部分可以看做右侧的空白距离
    private final float topMargin = DeviceUtils.dipToPx(MyApplication.getInstance(), 5);
    /**
     * 左侧显示时间小时的文字位置
     */
    private List<Float> leftHoursPostion = new ArrayList<>();

    /**
     * 两个集合的长度都必须小于等于7
     * @param list
     * @param dateStringList
     */
    public void setList(List<TrainWeekViewBean> list, List<String> dateStringList) {
        this.list = list;
        this.dateStringList = dateStringList;

        setSize(getWidth() / (2 * lines + 1));
        mStartWidth = (int) getOriginaX();//getWidth() / (lines + 1);
        mPerIntervalWidth = (int) ((getWidth() - mStartWidth * twoSpaceBetweenMultiple) / (lines + 1));
        mChartWidth = mPerIntervalWidth - mSize / 2;
        invalidate();
    }

    public NewWeekView(Context context) {
        this(context, null);
    }

    public NewWeekView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewWeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyChartView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.MyChartView_xyColor:
                    lineColor = array.getColor(attr, getResources().getColor(R.color.time_selected_text_color));//array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyChartView_leftColor:
                    // 默认颜色设置为黑色
                    leftColor = array.getColor(attr, getResources().getColor(R.color.time_selected_text_color));//array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyChartView_leftColorBottom:
                    lefrColorBottom = array.getColor(attr, getResources().getColor(R.color.time_selected_text_color));//array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyChartView_selectLeftColor:
                    // 默认颜色设置为黑色
                    selectLeftColor = array.getColor(attr, getResources().getColor(R.color.time_selected_text_color));//array.getColor(attr, Color.BLACK);
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
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = getWidth();
        mHeight = getHeight() - getPaddingTop();

        perHighSize = (mHeight - marginBottom - topMargin) * 1.0f / (horizontalLines * 60 * 60);
        mStartWidth = (int) getOriginaX();
        mPerIntervalWidth = (int) ((getWidth() - mStartWidth * twoSpaceBetweenMultiple) / (lines + 1));
        mChartWidth = mPerIntervalWidth - mSize / 2;
        setSize(mWidth / (2 * lines + 1));
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mBound = new Rect();

        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);

        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);

        marginBottom = DeviceUtils.dipToPx(mContext, 35);
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
            mStartWidth = (int) getOriginaX();
            mPerIntervalWidth = (int) ((getWidth() - mStartWidth * twoSpaceBetweenMultiple) / (lines + 1));
            mChartWidth = mPerIntervalWidth - mSize / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackgroundLine(canvas);
        drawBottomDateText(canvas);
        drawBrokenLines(canvas);
        drawBottomVerticalLine(canvas);
        /**
         * 复位
         */
        mStartWidth = (int) getOriginaX();
        mPerIntervalWidth = (int) ((getWidth() - mStartWidth * twoSpaceBetweenMultiple) / (lines + 1));
        mChartWidth = mPerIntervalWidth - mSize / 2;
    }

    /**
     * 绘制背景线条
     *
     * @param canvas
     */
    private void drawBackgroundLine(Canvas canvas) {
        createLinePainte();
        getAllLinesPosition();
        backgroundLinePaint.setColor(getResources().getColor(R.color.bleglasses_form_report_week_dy_height__color));
        canvas.drawLines(getAllLinesPosition(), backgroundLinePaint);
        drawLeftHourText(canvas);
        //drawVerticalArrow(canvas);
        //drawHorizontal(canvas);
    }

    /**
     * 通过传入视力值，得到折线图连接原点 Y 轴的位置
     *
     * @param trainTime
     * @return
     */
    private float getPointYPositionByEyeSight(float trainTime) {
        float yPosition = mHeight - (trainTime * perHighSize + marginBottom);

        if (yPosition > mHeight - marginBottom) {
            yPosition = mHeight - marginBottom;
        }
        return yPosition;
    }

    /**
     * 画纵向箭头
     */
    private void drawVerticalArrow(Canvas canvas) {
        float StartX = getOriginaX();

        //终点的箭头
        Path path = new Path();
        path.moveTo(StartX, topMargin);
        path.lineTo(StartX - arrowWidth, arrowHeight + topMargin);
        path.lineTo(StartX + arrowWidth, arrowHeight + topMargin);
        path.close();
        canvas.drawPath(path, backgroundLinePaint);
    }


    /**
     * 画横向的箭头
     *
     * @param canvas
     */
    private void drawHorizontal(Canvas canvas) {
        float StartX = getOriginaX();

        int totalWidth = getWidth();
        int arrowStartX = (int) (totalWidth - (twoSpaceBetweenMultiple - 1) * StartX);
        int arrowStartY = (int) (mHeight - marginBottom);

        //终点的箭头
        Path path = new Path();
        path.moveTo(arrowStartX, arrowStartY);
        path.lineTo(arrowStartX - arrowHeight, arrowStartY - arrowWidth);
        path.lineTo(arrowStartX - arrowHeight, arrowStartY + arrowWidth);
        path.close();
        canvas.drawPath(path, backgroundLinePaint);
    }

    /**
     * 画折线图(从坐标轴原点右侧最近的一个点开始)
     */
    private void drawBrokenLines(Canvas canvas) {
        xCenterList.clear();
        pointsConnectList.clear();

        mStartWidth = (int) getOriginaX();
        int eyeSightBeanCount = list.size();

        {
            xCenterList.add(mStartWidth);
            PointF leftEyeStartPoint = new PointF();
            leftEyeStartPoint.x = mStartWidth;
            leftEyeStartPoint.y = getPointYPositionByEyeSight(0);
            pointsConnectList.add(leftEyeStartPoint);
        }

        mStartWidth += mPerIntervalWidth;

        for (int i = 0; i < lines; i++) {
            if (i >= eyeSightBeanCount) {
                break;
            }

            mChartPaint.setStyle(Paint.Style.FILL);
            if (eyeSightBeanCount > 0) {
                if (selectIndexRoles.contains(i)) {
                    mChartPaint.setColor(selectLeftColor);
                } else {
                    mChartPaint.setColor(selectLeftColor);
                }

                TrainWeekViewBean sightBean = list.get(i);

                PointF point = new PointF();
                point.x = mStartWidth;
                point.y = getPointYPositionByEyeSight(sightBean.getTrainTime());//mHeight - (marginBottom + list.get(i) * perHighSize);
                pointsConnectList.add(point);

                xCenterList.add(mStartWidth);

                mStartWidth += mPerIntervalWidth;
            }
        }

        /**
         * 绘制训练时间的折线图
         */
        List<Float> pointList = new ArrayList<>();

        int pointsSize = pointsConnectList.size();

        for (int i = 0; i < pointsSize; i++) {
            PointF pointF = pointsConnectList.get(i);
            if (i != pointsSize - 1 && i != 0) {
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
        mChartPaint.setStrokeWidth(brokenLinesWidth);
        //canvas.drawLines(floats, mChartPaint);//关闭折线图,若开启需要去掉注释
        //drawPoint(canvas, pointsConnectList);//关闭折线图,若开启需要去掉注释
        drawRectangle(canvas);
    }

    /**
     * 画折线图上的点
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas, List<PointF> listPointF) {
        mPointPaint.setColor(getResources().getColor(R.color.top_unsignin_text_color));
        mPointPaint.setStyle(Paint.Style.FILL);
        float strokWidth = DeviceUtils.dipToPx(mContext, 1);
        //mPointPaint.setStrokeWidth(strokWidth);
        for (int i = 0; i < listPointF.size(); i++) {
            if (i == 0) {
                continue;//不画原点
            }
            PointF pointF = listPointF.get(i);

            if (mHeight - marginBottom < pointF.y + pointRadius) {
                pointF.y = mHeight - marginBottom - pointRadius;
            }

            canvas.drawCircle(pointF.x, pointF.y, pointRadius - strokWidth, mPointPaint);
        }

        drawStrokeCircle(canvas, listPointF);

    }

    /**
     * 画柱状图
     * @param canvas
     */
    private void drawRectangle(Canvas canvas) {
        for (int i = 0; i < list.size(); i++) {
            PointF pointF = pointsConnectList.get(i + 1);

            RectF rectF = new RectF();
            rectF.left = pointF.x - mSize/2;
            rectF.right = pointF.x + mSize/2;
            rectF.bottom = mHeight - marginBottom;
            rectF.top = mHeight - (marginBottom + list.get(i).getTrainTime() * perHighSize);
            canvas.drawRoundRect(rectF, 0, 0, mChartPaint);
        }
    }

    private void drawStrokeCircle(Canvas canvas, List<PointF> listPointF) {

        mPointPaint.setColor(getResources().getColor(R.color.login_title_text_color));
        mPointPaint.setStyle(Paint.Style.STROKE);
        float strokWidth = DeviceUtils.dipToPx(mContext, 1);
        mPointPaint.setStrokeWidth(strokWidth);

        for (int i = 0; i < listPointF.size(); i++) {
            if (i == 0) {
                continue;//不画原点
            }
            PointF pointF = listPointF.get(i);

            if (mHeight - marginBottom < pointF.y + pointRadius) {
                pointF.y = mHeight - marginBottom - pointRadius;
            }

            canvas.drawCircle(pointF.x, pointF.y, pointRadius - strokWidth * 0.5f, mPointPaint);
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
         * horizontalLines - 1 是为了 Y 轴留出箭头的高度
         */
        for (int i = 0; i < horizontalLines - 1; i++) {
            backgroundLinePosition.add(originalX);
            backgroundLinePosition.add(mHeight - marginBottom - (mHeight - marginBottom - topMargin) * (i * 1f / horizontalLines));

            //if (i == 0) {
                /**
                 * 最底部水平线的最右侧的 X 坐标
                 */
                backgroundLinePosition.add((float) mWidth - (twoSpaceBetweenMultiple - 1) * originalX);
          //  }
          /*else {
                backgroundLinePosition.add(originalX + DeviceUtils.dipToPx(MyApplication.getInstance(), leftVerticalLineWidth));
            }*/

            backgroundLinePosition.add(mHeight - marginBottom - (mHeight - marginBottom - topMargin) * (i * 1f / horizontalLines));
           // if (i > 0) {
                leftHoursPostion.add(mHeight - marginBottom - (mHeight - marginBottom - topMargin) * (i * 1f / horizontalLines));
            //}
        }

        /**
         * 添加 Y 轴的坐标位置
         */
        {
         /*   backgroundLinePosition.add(originalX);
            backgroundLinePosition.add(mHeight - marginBottom);
            backgroundLinePosition.add(originalX);
            backgroundLinePosition.add(topMargin);*/
        }


        float[] floats = new float[backgroundLinePosition.size()];
        for (int i = 0; i < floats.length; i++) {
            floats[i] = backgroundLinePosition.get(i);
        }
        return floats;
    }

    /**
     * 绘制左侧的小时文字
     *
     * @param canvas
     */
    private void drawLeftHourText(Canvas canvas) {
        Rect mLeftBound = new Rect();
        float leftMargin = getOriginaX();

        backgroundLinePaint.setColor(getResources().getColor(R.color.time_selected_text_color));
        for (int i = 0; i < leftHoursPostion.size(); i++) {
            backgroundLinePaint.setTextSize(DeviceUtils.dipToPx(mContext, mTextSize));
            backgroundLinePaint.setTextAlign(Paint.Align.RIGHT);
            String dateStr = String.valueOf((int) (i)) + "h";
            //float hourTextHeight = Math.abs(backgroundLinePaint.ascent() + backgroundLinePaint.descent());
            backgroundLinePaint.getTextBounds(leftMaxText, 0, leftMaxText.length(), mLeftBound);
            canvas.drawText(dateStr, leftMargin - mLeftBound.width() * 0.5f, leftHoursPostion.get(i) + mLeftBound.height() * 0.5f, backgroundLinePaint);
        }
        backgroundLinePaint.setColor(getResources().getColor(R.color.bleglasses_form_report_week_dy_height__color));
    }

    /***
     * 根据折线图连接点的 X 轴坐标，画底部纵向短线段
     */
    private void drawBottomVerticalLine(Canvas canvas) {
        float[] lines = new float[4 * pointsConnectList.size()];

        for (int i = 1; i < 8; i++) {
            //原点不绘制，所以 i 从1开始
            if (i * 4 >= lines.length) {
                break;
            }
            lines[0 + 4 * i] = pointsConnectList.get(i).x;
            lines[1 + 4 * i] = mHeight - marginBottom;
            lines[2 + 4 * i] = pointsConnectList.get(i).x;
            lines[3 + 4 * i] = mHeight - marginBottom - DeviceUtils.dipToPx(MyApplication.getInstance(), leftVerticalLineWidth);
        }
        canvas.drawLines(lines, backgroundLinePaint);

    }

    /**
     * 绘制底部日期文字
     *
     * @param canvas
     */
    private void drawBottomDateText(Canvas canvas) {

        mPaint.setColor(lineColor);
        mStartWidth = (int) getOriginaX() + mPerIntervalWidth;
        int showDateCount = dateStringList.size();

        for (int i = 0; i < lines; i++) {
            if (i >= showDateCount) {
                return;
            }


            //画数字
            mPaint.setTextSize(DeviceUtils.dipToPx(mContext, mTextSize));
            mPaint.setTextAlign(Paint.Align.LEFT);
            String dateStr = dateStringList.get(i);
            dateStr = dateStr.substring(dateStr.indexOf(".") + 1);

            mPaint.getTextBounds(dateStr, 0, dateStr.length(), mBound);
            canvas.drawText(dateStr, mStartWidth - mBound.width() * 1 / 2,
                    mHeight - mBound.height() / 2, mPaint);
            mStartWidth += mPerIntervalWidth;//getWidth() / (lines + 1);
        }

    }

    private float getOriginaX() {
        return getLeftHourTextWidth() * 2f;
    }

    private float getBottomTextHeight() {
        mPaint.setTextSize(DeviceUtils.dipToPx(mContext, mTextSize));
        mPaint.setTextAlign(Paint.Align.LEFT);
        String dateStr = "12.31";
        mPaint.getTextBounds(dateStr, 0, dateStr.length(), mBound);
        return mBound.height();
    }

    private float getLeftHourTextWidth() {
        if (null == backgroundLinePaint) {
            backgroundLinePaint = new Paint();
        }

        Rect mLeftBound = new Rect();
        //backgroundLinePaint.setColor(getResources().getColor(R.color.selectLeftColor));
        backgroundLinePaint.setTextSize(DeviceUtils.dipToPx(mContext, mTextSize));
        backgroundLinePaint.setTextAlign(Paint.Align.LEFT);
        backgroundLinePaint.getTextBounds(leftMaxText, 0, leftMaxText.length(), mLeftBound);
        return mLeftBound.width();
    }

    private void createLinePainte() {
        if (null == backgroundLinePaint) {
            backgroundLinePaint = new Paint();
        }
        backgroundLinePaint.setAntiAlias(true);
        //backgroundLinePaint.setColor(getResources().getColor(R.color.selectLeftColor));
        backgroundLinePaint.setStyle(Paint.Style.FILL);
        backgroundLinePaint.setStrokeWidth(2);
    }

    private void setSize(int size) {
        /**
         * 设置柱状图的宽度
         */
        this.mSize = (int) verticalLineWidth;//size  / 4;
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
       /*         for (int i = 0; i < lines + 1; i++) {
                    PointF pointConnected = pointsConnectList.get(i);

                    left = (int)(pointConnected.x - mPerIntervalWidth / 2);
                    right = (int)(pointConnected.x + mPerIntervalWidth / 2);

                    Rect rect = new Rect(left, top, right, bottom);
                    
                    if (rect.contains(x, y)) {
                        if (listener != null){
                            int touchx = xCenterList.get(i);
                            MLog.d(" getHeight() = " + getHeight() + " pointConnected.y = " + pointConnected.y);
                            int touchy = (int)(getHeight() - pointConnected.y + DeviceUtils.dipToPx(MyApplication.getInstance(), 40));// 40dp 是 ReviewDataLineChartWeekView marginBottom 的距离
                            listener.getNumber(i, touchx, touchy);
                            number = i;
                            selectIndex = i;
                            selectIndexRoles.clear();
                            selectIndexRoles.add(selectIndex);
                            invalidate();
                        }
                    }
                }*/
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
        /**
         * 星期的显示名称
         */
        public String week;
        /**
         * 对应的日期
         */
        public String day;

        @Override
        public String toString() {
            return "WeekDay{" +
                    "week='" + week + '\'' +
                    ", day='" + day + '\'' +
                    '}';
        }
    }

    /**
     * 获取文本的宽度
     *
     * @param paint
     * @param str
     * @return
     */
    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }
}
