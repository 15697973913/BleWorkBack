package cn.aigestudio.datepicker.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.aigestudio.datepicker.R;
import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.bizs.themes.DPTManager;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.entities.DPInfo;

/**
 * MonthView
 *
 * @author AigeStudio 2015-06-29
 */
public class MonthView extends View {
    private static final String TAG = "MonthView";
    private final Region[][] MONTH_REGIONS_4 = new Region[4][7];
    private final Region[][] MONTH_REGIONS_5 = new Region[5][7];
    private final Region[][] MONTH_REGIONS_6 = new Region[6][7];

    private final DPInfo[][] INFO_4 = new DPInfo[4][7];
    private final DPInfo[][] INFO_5 = new DPInfo[5][7];
    private final DPInfo[][] INFO_6 = new DPInfo[6][7];

    private final Map<String, List<Region>> REGION_SELECTED = new HashMap<>();

    private DPCManager mCManager = DPCManager.getInstance();
    private DPTManager mTManager = DPTManager.getInstance();

    protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG |
            Paint.LINEAR_TEXT_FLAG);

    protected Paint mPaintDateCircle = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG |
            Paint.LINEAR_TEXT_FLAG);

    protected Paint mWhitePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG |
            Paint.LINEAR_TEXT_FLAG);

    private Scroller mScroller;
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
    private OnDateChangeListener onDateChangeListener;
    private DatePicker.OnDatePickedListener onDatePickedListener;
    private ScaleAnimationListener scaleAnimationListener;

    private DPMode mDPMode = DPMode.MULTIPLE;
    private SlideMode mSlideMode;
    private DPDecor mDPDecor;

    private int circleRadius;
    private int indexYear, indexMonth;
    private int centerYear, centerMonth;
    private int leftYear, leftMonth;
    private int rightYear, rightMonth;
    private int topYear, topMonth;
    private int bottomYear, bottomMonth;
    private int width, height;
    private int sizeDecor, sizeDecor2x, sizeDecor3x;
    private int lastPointX, lastPointY;
    private int lastMoveX, lastMoveY;
    private int criticalWidth, criticalHeight;
    private int animZoomOut1, animZoomIn1, animZoomOut2;

    private float sizeTextGregorian, sizeTextFestival;
    private float offsetYFestival1, offsetYFestival2;

    private boolean isNewEvent,
            isFestivalDisplay = true,
            isHolidayDisplay = true,
            isTodayDisplay = true,
            isDeferredDisplay = true;

    private Map<String, BGCircle> cirApr = new HashMap<>();
    private Map<String, BGCircle> cirDpr = new HashMap<>();

    private List<String> dateSelected = new ArrayList<>();

    private int backgroundcolor = 0;
    private Bitmap mSignBitmap;

    public MonthView(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            scaleAnimationListener = new ScaleAnimationListener();
        }
        mScroller = new Scroller(context);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaintDateCircle.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        } else {
            requestLayout();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (true) {
////
////            /**
////             * 禁用触摸事件
////             */
////            return true;
////        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScroller.forceFinished(true);
                mSlideMode = null;
                isNewEvent = true;
                lastPointX = (int) event.getX();
                lastPointY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isNewEvent) {
                    if (Math.abs(lastPointX - event.getX()) > 100) {
                        mSlideMode = SlideMode.HOR;
                        isNewEvent = false;
                    } else if (Math.abs(lastPointY - event.getY()) > 50) {
                        mSlideMode = SlideMode.VER;
                        isNewEvent = false;
                    }
                }
//                if (mSlideMode == SlideMode.HOR) {
//                    int totalMoveX = (int) (lastPointX - event.getX()) + lastMoveX;
//                    smoothScrollTo(totalMoveX, indexYear * height);
//                } else if (mSlideMode == SlideMode.VER) {
//                    int totalMoveY = (int) (lastPointY - event.getY()) + lastMoveY;
//                    smoothScrollTo(width * indexMonth, totalMoveY);
//                }
                break;
            case MotionEvent.ACTION_UP:
                if (mSlideMode == SlideMode.VER) {
//                    if (Math.abs(lastPointY - event.getY()) > 25) {
//                        if (lastPointY < event.getY()) {
//                            if (Math.abs(lastPointY - event.getY()) >= criticalHeight) {
//                                indexYear--;
//                                centerYear = centerYear - 1;
//                            }
//                        } else if (lastPointY > event.getY()) {
//                            if (Math.abs(lastPointY - event.getY()) >= criticalHeight) {
//                                indexYear++;
//                                centerYear = centerYear + 1;
//                            }
//                        }
//                        buildRegion();
//                        computeDate();
//                        smoothScrollTo(width * indexMonth, height * indexYear);
//                        lastMoveY = height * indexYear;
//                    } else {
//                        defineRegion((int) event.getX(), (int) event.getY());
//                    }
//                } else if (mSlideMode == SlideMode.HOR) {
//                    if (Math.abs(lastPointX - event.getX()) > 25) {
//                        if (lastPointX > event.getX() &&
//                                Math.abs(lastPointX - event.getX()) >= criticalWidth) {
//                            indexMonth++;
//                            centerMonth = (centerMonth + 1) % 13;
//                            if (centerMonth == 0) {
//                                centerMonth = 1;
//                                centerYear++;
//                            }
//                        } else if (lastPointX < event.getX() &&
//                                Math.abs(lastPointX - event.getX()) >= criticalWidth) {
//                            indexMonth--;
//                            centerMonth = (centerMonth - 1) % 12;
//                            if (centerMonth == 0) {
//                                centerMonth = 12;
//                                centerYear--;
//                            }
//                        }
//                        buildRegion();
//                        computeDate();
//                        smoothScrollTo(width * indexMonth, indexYear * height);
//                        lastMoveX = width * indexMonth;
//                    } else {
//                        defineRegion((int) event.getX(), (int) event.getY());
//                    }
                } else {
                    defineRegion((int) event.getX(), (int) event.getY());
                }
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        h = (int) (h * 0.95f);
        width = w;
        height = h;

        criticalWidth = (int) (1F / 5F * width);
        criticalHeight = (int) (1F / 5F * height);

        int cellW = (int) (w / 7F);
        int cellH4 = (int) (h / 4F);
        int cellH5 = (int) (h / 5F);
        int cellH6 = (int) (h / 6F);

        circleRadius = cellW / 2;

        animZoomOut1 = circleRadius;//(int) (cellW * 1.2F);
        animZoomIn1 = circleRadius;//(int) (cellW * 0.8F);
        animZoomOut2 = circleRadius;//(int) (cellW * 1.1F);

        sizeDecor = (int) (cellW / 3F);
        sizeDecor2x = sizeDecor * 2;
        sizeDecor3x = sizeDecor * 3;

        sizeTextGregorian = width / 20F;
        sizeTextGregorian = sizeTextGregorian * 2 / 3;
        mPaint.setTextSize(sizeTextGregorian);

        float heightGregorian = mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top;
        sizeTextFestival = width / 40F;
        mPaint.setTextSize(sizeTextFestival);

        float heightFestival = mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top;
        offsetYFestival1 = (((Math.abs(mPaint.ascent() + mPaint.descent())) / 2F) +
                heightFestival / 2F + heightGregorian / 2F) / 2F;
        offsetYFestival2 = offsetYFestival1 * 2F;

        for (int i = 0; i < MONTH_REGIONS_4.length; i++) {
            for (int j = 0; j < MONTH_REGIONS_4[i].length; j++) {
                Region region = new Region();
                region.set((j * cellW), (i * cellH4), cellW + (j * cellW),
                        cellW + (i * cellH4));
                MONTH_REGIONS_4[i][j] = region;
            }
        }
        for (int i = 0; i < MONTH_REGIONS_5.length; i++) {
            for (int j = 0; j < MONTH_REGIONS_5[i].length; j++) {
                Region region = new Region();
                region.set((j * cellW), (i * cellH5), cellW + (j * cellW),
                        cellW + (i * cellH5));
                MONTH_REGIONS_5[i][j] = region;
            }
        }
        for (int i = 0; i < MONTH_REGIONS_6.length; i++) {
            for (int j = 0; j < MONTH_REGIONS_6[i].length; j++) {
                Region region = new Region();
                region.set((j * cellW), (i * cellH6), cellW + (j * cellW),
                        cellW + (i * cellH6));
                MONTH_REGIONS_6[i][j] = region;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int bgcolor = 0 != backgroundcolor ? backgroundcolor : mTManager.colorBG();
        if (0 != backgroundcolor) {
            canvas.drawColor(bgcolor);
        }

        draw(canvas, width * indexMonth, (indexYear - 1) * height, topYear, topMonth);
        draw(canvas, width * (indexMonth - 1), height * indexYear, leftYear, leftMonth);
        draw(canvas, width * indexMonth, indexYear * height, centerYear, centerMonth);
        draw(canvas, width * (indexMonth + 1), height * indexYear, rightYear, rightMonth);
        draw(canvas, width * indexMonth, (indexYear + 1) * height, bottomYear, bottomMonth);

        drawBGCircle(canvas);
    }

    private void drawBGCircle(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            for (String s : cirDpr.keySet()) {
                BGCircle circle = cirDpr.get(s);
               /* int spliteIndx = s.lastIndexOf("-");
                circle.setDayNum(s.substring(spliteIndx + 1));*/
                drawBGCircle(canvas, circle);
            }
        }
        for (String s : cirApr.keySet()) {
            BGCircle circle = cirApr.get(s);
          /*  int spliteIndx = s.lastIndexOf("-");
            circle.setDayNum(s.substring(spliteIndx + 1));*/
            drawBGCircle(canvas, circle);
        }
    }

    private void drawBGCircle(Canvas canvas, BGCircle circle) {
        // circle.setRadius(circle.getRadius() / 2);
        canvas.save();
        canvas.translate(circle.getX() - circle.getRadius() / 2,
                circle.getY() - circle.getRadius() / 2);
        circle.getShape().getShape().resize(circle.getRadius(), circle.getRadius());
        circle.getShape().draw(canvas);
        canvas.restore();
        //drawWhiteDate(canvas, circle);
    }

    /**
     * 修改选中日期的字体颜色
     */
    private void drawWhiteDate(Canvas canvas, BGCircle circle) {

        mWhitePaint.setTextSize(sizeTextGregorian);

        mWhitePaint.setColor(mTManager.colorBG());
        mWhitePaint.measureText(circle.getDayNum());


        float x = circle.getX() - mWhitePaint.measureText(circle.getDayNum()) / 2;
        float y = circle.getY() + Math.abs(mWhitePaint.ascent()) - (mWhitePaint.descent() - mWhitePaint.ascent()) / 2F;

        canvas.drawText(circle.getDayNum(), x, y, mWhitePaint);

    }

    private void draw(Canvas canvas, int x, int y, int year, int month) {
        canvas.save();
        canvas.translate(x, y);
        DPInfo[][] info = mCManager.obtainDPInfo(year, month);
        DPInfo[][] result;
        Region[][] tmp;
        if (TextUtils.isEmpty(info[4][0].strG)) {
            tmp = MONTH_REGIONS_4;
            arrayClear(INFO_4);
            result = arrayCopy(info, INFO_4);
        } else if (TextUtils.isEmpty(info[5][0].strG)) {
            tmp = MONTH_REGIONS_5;
            arrayClear(INFO_5);
            result = arrayCopy(info, INFO_5);
        } else {
            tmp = MONTH_REGIONS_6;
            arrayClear(INFO_6);
            result = arrayCopy(info, INFO_6);
        }
        //fillDPInfo(year, month, result);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                draw(canvas, tmp[i][j].getBounds(), result[i][j]);
            }
        }
        canvas.restore();
    }

    /**
     * 将每个月中，日期为空的填满（每一行[周]都有日期显示）
     */
    private DPInfo[][] fillDPInfo(int year, int month, DPInfo[][] dpInfos) {

        DPInfo[][] lastMonthDPInfo = getLastMonthDPInfo(year, month);
        DPInfo[][] nextMonthDPInfo = getNextMonthDPInfo(year, month);
        int headEmptyCount = getEmptyCount(dpInfos, true);
        int endEmptyCount = getEmptyCount(dpInfos, false);
        DPInfo[] headDpInfoArray = getMonthRemain(lastMonthDPInfo, false, headEmptyCount);
        DPInfo[] endDpInfoArray = getMonthRemain(nextMonthDPInfo, true, endEmptyCount);

        /**
         * 先填满当月头部
         */
        if (headEmptyCount > 0) {
            int m = 0;
            for (int i = 0; i < dpInfos.length; i++) {
                if (m >= headEmptyCount) {
                    break;
                }

                for (int j = 0; j < dpInfos[i].length; j++) {
                    if (m >= headEmptyCount) {
                        break;
                    }
                    dpInfos[i][j] = headDpInfoArray[m];
                    m++;
                }
            }
        }


        /**
         * 填满当月尾部
         */
        if (endEmptyCount > 0) {
            int n = endEmptyCount;
            for (int i = dpInfos.length - 1; i >= 0; i--) {
                if (n <= 0) {
                    break;
                }

                for (int j = dpInfos[i].length - 1; j >= 0; j--) {

                    if (n <= 0) {
                        break;
                    }
                    dpInfos[i][j] = endDpInfoArray[n - 1];
                    n--;
                }
            }
        }
        return dpInfos;
    }

    /**
     * 获取上个月的（参数为当前年月）
     *
     * @param year
     * @param month
     * @return
     */
    private DPInfo[][] getLastMonthDPInfo(int year, int month) {
        if (month > 1) {
            return mCManager.obtainDPInfo(year, month - 1);
        } else {
            return mCManager.obtainDPInfo(year - 1, 12);
        }
    }

    /**
     * 获取下一个月的（参数为当前年月）
     *
     * @param year
     * @param month
     * @return
     */
    private DPInfo[][] getNextMonthDPInfo(int year, int month) {

        if (month < 12) {
            return mCManager.obtainDPInfo(year, month + 1);
        } else {
            return mCManager.obtainDPInfo(year + 1, 1);
        }
    }


    private int getEmptyCount(DPInfo[][] dpInfos, boolean fromHead) {
        int count = 0;

        if (fromHead) {
            for (int i = 0; i < dpInfos.length; i++) {

                for (int j = 0; j < dpInfos[i].length; j++) {
                    if (TextUtils.isEmpty(dpInfos[i][j].strG)) {
                        count++;
                    } else {
                        return count;
                    }
                }
            }
        } else {

            for (int i = dpInfos.length - 1; i >= 0; i--) {
                for (int j = dpInfos[i].length - 1; j >= 0; j--) {
                    if (TextUtils.isEmpty(dpInfos[i][j].strG)) {
                        count++;
                    } else {
                        return count;
                    }
                }
            }
        }
        return count;
    }


    /**
     * @return 根据需要填补的 count 个数，从 dpInfos 中取出 count 个值
     */
    private DPInfo[] getMonthRemain(DPInfo[][] dpInfos, boolean fromHead, int count) {
        DPInfo[] resultDpInfoList = new DPInfo[count];
        if (fromHead) {
            int m = 0;
            for (int i = 0; i < dpInfos.length; i++) {

                if (m >= count) {
                    break;
                }

                for (int j = 0; j < dpInfos[i].length; j++) {

                    if (m >= count) {
                        break;
                    }

                    DPInfo dpInfo = dpInfos[i][j];
                    if (!TextUtils.isEmpty(dpInfo.strG)) {
                        try {
                            resultDpInfoList[m] = (DPInfo) dpInfo.clone();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                        resultDpInfoList[m].isOtherMonthDate = true;
                        m++;
                    }
                }
            }
        } else {
            int n = count;
            for (int i = dpInfos.length - 1; i >= 0; i--) {
                if (n <= 0) {
                    break;
                }

                for (int j = dpInfos[i].length - 1; j >= 0; j--) {
                    if (n <= 0) {
                        break;
                    }
                    DPInfo dpInfo = dpInfos[i][j];
                    if (!TextUtils.isEmpty(dpInfo.strG)) {
                        try {
                            resultDpInfoList[n - 1] = (DPInfo) dpInfo.clone();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                        resultDpInfoList[n - 1].isOtherMonthDate = true;
                        n--;
                    }
                }
            }
        }
        return resultDpInfoList;
    }

    private void draw(Canvas canvas, Rect rect, DPInfo info) {
        drawBG(canvas, rect, info);
        drawGregorian(canvas, rect, info.strG, info.isWeekend, info.isSignIn, info.isOtherMonthDate, info.isToday);
        if (isFestivalDisplay) {
            drawFestival(canvas, rect, info.strF, info.isFestival);
        }
        drawDecor(canvas, rect, info);
    }


    private void drawBG(Canvas canvas, Rect rect, DPInfo info) {
        if (null != mDPDecor && info.isDecorBG) {
            mDPDecor.drawDecorBG(canvas, rect, mPaint,
                    centerYear + "-" + centerMonth + "-" + info.strG);
        }
        if (info.isSignIn) {
//            mPaintDateCircle.setColor(mTManager.colorLineCircle());
//            mPaintDateCircle.setStrokeWidth(circleRadius * 0.05f);
//            mPaintDateCircle.setStyle(Paint.Style.STROKE);
//            /**
//             * 设置签到或者训练日期的圆形背景颜色
//             */
//            //canvas.drawCircle(rect.centerX(), rect.centerY(), circleRadius / 2F, mPaintDateCircle);
//            canvas.drawCircle(rect.centerX(), rect.centerY(), circleRadius / 2F, mPaintDateCircle);
////            return;

            //设置签到或者训练日期的标签

            if (mSignBitmap == null) {
                mSignBitmap = scaleBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_statement_days), 0.25f);
            }
            // 指定.
            // 图片在屏幕上显示的区域(原图大小)

            canvas.drawBitmap(mSignBitmap, rect.centerX()-(mSignBitmap.getWidth()/2), rect.centerY()+15, mPaintDateCircle);
        }


        if (info.isToday && isTodayDisplay) {
            drawBGToday(canvas, rect);
        } else {
            if (isHolidayDisplay) drawBGHoliday(canvas, rect, info.isHoliday);
            if (isDeferredDisplay) drawBGDeferred(canvas, rect, info.isDeferred);
        }
    }

    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    private void drawBGToday(Canvas canvas, Rect rect) {
        mPaint.setColor(mTManager.colorToday());
        canvas.drawCircle(rect.centerX(), rect.centerY(), circleRadius / 2F, mPaint);
    }

    private void drawBGHoliday(Canvas canvas, Rect rect, boolean isHoliday) {
        mPaint.setColor(mTManager.colorHoliday());
        if (isHoliday) canvas.drawCircle(rect.centerX(), rect.centerY(), circleRadius / 2F, mPaint);
    }

    private void drawBGDeferred(Canvas canvas, Rect rect, boolean isDeferred) {
        mPaint.setColor(mTManager.colorDeferred());
        if (isDeferred)
            canvas.drawCircle(rect.centerX(), rect.centerY(), circleRadius / 2F, mPaint);
    }

    /**
     * 画阳历日期文字内容
     *
     * @param canvas
     * @param rect
     * @param str
     * @param isWeekend
     */
    private void drawGregorian(Canvas canvas, Rect rect, String str, boolean isWeekend, boolean isSignIn, boolean isOtherMonthDate, boolean isToday) {
        mPaint.setTextSize(sizeTextGregorian);
//        if (isWeekend) {
//            mPaint.setColor(mTManager.colorWeekend());
//        } else
        if (isToday) {
            mPaint.setColor(mTManager.colorToDayText());
        } else if (isSignIn) {
            mPaint.setColor(mTManager.colorBG());
        } else {
            mPaint.setColor(mTManager.colorG());
        }

        if (isOtherMonthDate) {
            mPaint.setColor(getResources().getColor(R.color.month_view_other_date_text_color));
        }

        float y = rect.centerY();
        if (!isFestivalDisplay)
            y = rect.centerY() + Math.abs(mPaint.ascent()) - (mPaint.descent() - mPaint.ascent()) / 2F;
        canvas.drawText(str, rect.centerX(), y, mPaint);
    }

    private void drawFestival(Canvas canvas, Rect rect, String str, boolean isFestival) {
        mPaint.setTextSize(sizeTextFestival);
        if (isFestival) {
            mPaint.setColor(mTManager.colorF());
        } else {
            mPaint.setColor(mTManager.colorL());
        }
        if (str.contains("&")) {
            String[] s = str.split("&");
            String str1 = s[0];
            if (mPaint.measureText(str1) > rect.width()) {
                float ch = mPaint.measureText(str1, 0, 1);
                int length = (int) (rect.width() / ch);
                canvas.drawText(str1.substring(0, length), rect.centerX(),
                        rect.centerY() + offsetYFestival1, mPaint);
                canvas.drawText(str1.substring(length), rect.centerX(),
                        rect.centerY() + offsetYFestival2, mPaint);
            } else {
                canvas.drawText(str1, rect.centerX(),
                        rect.centerY() + offsetYFestival1, mPaint);
                String str2 = s[1];
                if (mPaint.measureText(str2) < rect.width()) {
                    canvas.drawText(str2, rect.centerX(),
                            rect.centerY() + offsetYFestival2, mPaint);
                }
            }
        } else {
            if (mPaint.measureText(str) > rect.width()) {
                float ch = 0.0F;
                for (char c : str.toCharArray()) {
                    float tmp = mPaint.measureText(String.valueOf(c));
                    if (tmp > ch) {
                        ch = tmp;
                    }
                }
                int length = (int) (rect.width() / ch);
                canvas.drawText(str.substring(0, length), rect.centerX(),
                        rect.centerY() + offsetYFestival1, mPaint);
                canvas.drawText(str.substring(length), rect.centerX(),
                        rect.centerY() + offsetYFestival2, mPaint);
            } else {
                canvas.drawText(str, rect.centerX(),
                        rect.centerY() + offsetYFestival1, mPaint);
            }
        }
    }

    private void drawDecor(Canvas canvas, Rect rect, DPInfo info) {
        if (!TextUtils.isEmpty(info.strG)) {
            String data = centerYear + "-" + centerMonth + "-" + info.strG;
            if (null != mDPDecor && info.isDecorTL) {
                canvas.save();
                canvas.clipRect(rect.left, rect.top, rect.left + sizeDecor, rect.top + sizeDecor);
                mDPDecor.drawDecorTL(canvas, canvas.getClipBounds(), mPaint, data);
                canvas.restore();
            }
            if (null != mDPDecor && info.isDecorT) {
                canvas.save();
                canvas.clipRect(rect.left + sizeDecor, rect.top, rect.left + sizeDecor2x,
                        rect.top + sizeDecor);
                mDPDecor.drawDecorT(canvas, canvas.getClipBounds(), mPaint, data);
                canvas.restore();
            }
            if (null != mDPDecor && info.isDecorTR) {
                canvas.save();
                canvas.clipRect(rect.left + sizeDecor2x, rect.top, rect.left + sizeDecor3x,
                        rect.top + sizeDecor);
                mDPDecor.drawDecorTR(canvas, canvas.getClipBounds(), mPaint, data);
                canvas.restore();
            }
            if (null != mDPDecor && info.isDecorL) {
                canvas.save();
                canvas.clipRect(rect.left, rect.top + sizeDecor, rect.left + sizeDecor,
                        rect.top + sizeDecor2x);
                mDPDecor.drawDecorL(canvas, canvas.getClipBounds(), mPaint, data);
                canvas.restore();
            }
            if (null != mDPDecor && info.isDecorR) {
                canvas.save();
                canvas.clipRect(rect.left + sizeDecor2x, rect.top + sizeDecor,
                        rect.left + sizeDecor3x, rect.top + sizeDecor2x);
                mDPDecor.drawDecorR(canvas, canvas.getClipBounds(), mPaint, data);
                canvas.restore();
            }
        }
    }

    List<String> getDateSelected() {
        return dateSelected;
    }

    void setOnDateChangeListener(OnDateChangeListener onDateChangeListener) {
        this.onDateChangeListener = onDateChangeListener;
    }

    public void setOnDatePickedListener(DatePicker.OnDatePickedListener onDatePickedListener) {
        this.onDatePickedListener = onDatePickedListener;
    }

    void setDPMode(DPMode mode) {
        this.mDPMode = mode;
    }

    void setDPDecor(DPDecor decor) {
        this.mDPDecor = decor;
    }

    DPMode getDPMode() {
        return mDPMode;
    }

    void setDate(int year, int month) {
        centerYear = year;
        centerMonth = month;
        indexYear = 0;
        indexMonth = 0;
        buildRegion();
        computeDate();
        requestLayout();
        invalidate();
    }

    void setFestivalDisplay(boolean isFestivalDisplay) {
        this.isFestivalDisplay = isFestivalDisplay;
    }

    /**
     * 设置当天是否高亮显示
     *
     * @param isTodayDisplay trueOrFalse
     */
    void setTodayDisplay(boolean isTodayDisplay) {
        this.isTodayDisplay = isTodayDisplay;
    }

    void setHolidayDisplay(boolean isHolidayDisplay) {
        this.isHolidayDisplay = isHolidayDisplay;
    }

    void setDeferredDisplay(boolean isDeferredDisplay) {
        this.isDeferredDisplay = isDeferredDisplay;
    }

    private void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
        //defineSelectedRegion();
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, 500);
        invalidate();
    }

    private BGCircle createCircle(float x, float y) {
        OvalShape circle = new OvalShape();
        circle.resize(0, 0);
        ShapeDrawable drawable = new ShapeDrawable(circle);
        BGCircle circle1 = new BGCircle(drawable);
        circle1.setX(x);
        circle1.setY(y);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            circle1.setRadius(circleRadius);
        }
        drawable.getPaint().setColor(mTManager.colorBGCircle());
        return circle1;
    }

    private void buildRegion() {
        String key = indexYear + ":" + indexMonth;
        if (!REGION_SELECTED.containsKey(key)) {
            REGION_SELECTED.put(key, new ArrayList<Region>());
        }
    }

    private void arrayClear(DPInfo[][] info) {
        for (DPInfo[] anInfo : info) {
            Arrays.fill(anInfo, null);
        }
    }

    private DPInfo[][] arrayCopy(DPInfo[][] src, DPInfo[][] dst) {
        for (int i = 0; i < dst.length; i++) {
            System.arraycopy(src[i], 0, dst[i], 0, dst[i].length);
        }
        return dst;
    }

    private void defineRegion(int x, int y) {
        DPInfo[][] info = mCManager.obtainDPInfo(centerYear, centerMonth);
        Region[][] tmp;
        if (TextUtils.isEmpty(info[4][0].strG)) {
            tmp = MONTH_REGIONS_4;
        } else if (TextUtils.isEmpty(info[5][0].strG)) {
            tmp = MONTH_REGIONS_5;
        } else {
            tmp = MONTH_REGIONS_6;
        }
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                Region region = tmp[i][j];
                if (TextUtils.isEmpty(mCManager.obtainDPInfo(centerYear, centerMonth)[i][j].strG)) {
                    continue;
                }
                if (region.contains(x, y)) {
                    List<Region> regions = REGION_SELECTED.get(indexYear + ":" + indexMonth);
                    if (mDPMode == DPMode.SINGLE) {
                        cirApr.clear();
                        regions.add(region);
                        final String date = centerYear + "-" + centerMonth + "-" +
                                mCManager.obtainDPInfo(centerYear, centerMonth)[i][j].strG;
                        BGCircle circle = createCircle(
                                region.getBounds().centerX() + indexMonth * width,
                                region.getBounds().centerY() + indexYear * height);
                        ValueAnimator animScale1 =
                                ObjectAnimator.ofInt(circle, "radius", 0, animZoomOut1);
                        animScale1.setDuration(250);
                        animScale1.setInterpolator(decelerateInterpolator);
                        animScale1.addUpdateListener(scaleAnimationListener);

                        ValueAnimator animScale2 =
                                ObjectAnimator.ofInt(circle, "radius", animZoomOut1, animZoomIn1);
                        animScale2.setDuration(100);
                        animScale2.setInterpolator(accelerateInterpolator);
                        animScale2.addUpdateListener(scaleAnimationListener);

                        ValueAnimator animScale3 =
                                ObjectAnimator.ofInt(circle, "radius", animZoomIn1, animZoomOut2);
                        animScale3.setDuration(150);
                        animScale3.setInterpolator(decelerateInterpolator);
                        animScale3.addUpdateListener(scaleAnimationListener);

                        ValueAnimator animScale4 =
                                ObjectAnimator.ofInt(circle, "radius", animZoomOut2, circleRadius);
                        animScale4.setDuration(50);
                        animScale4.setInterpolator(accelerateInterpolator);
                        animScale4.addUpdateListener(scaleAnimationListener);

                        AnimatorSet animSet = new AnimatorSet();
                        animSet.playSequentially(animScale1, animScale2, animScale3, animScale4);
                        animSet.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (null != onDatePickedListener) {
                                    onDatePickedListener.onDatePicked(date);
                                }
                            }
                        });
                        animSet.start();
                        cirApr.put(date, circle);
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                            invalidate();
                            if (null != onDatePickedListener) {
                                onDatePickedListener.onDatePicked(date);
                            }
                        }
                    } else if (mDPMode == DPMode.MULTIPLE) {
                        if (regions.contains(region)) {
                            regions.remove(region);
                        } else {
                            regions.add(region);
                        }
                        final String date = centerYear + "-" + centerMonth + "-" +
                                mCManager.obtainDPInfo(centerYear, centerMonth)[i][j].strG;
                        if (dateSelected.contains(date)) {
                            dateSelected.remove(date);
                            BGCircle circle = cirApr.get(date);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                ValueAnimator animScale = ObjectAnimator.ofInt(circle, "radius", circleRadius, 0);
                                animScale.setDuration(250);
                                animScale.setInterpolator(accelerateInterpolator);
                                animScale.addUpdateListener(scaleAnimationListener);
                                animScale.addListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        cirDpr.remove(date);
                                    }
                                });
                                animScale.start();
                                cirDpr.put(date, circle);
                            }
                            cirApr.remove(date);
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                                invalidate();
                            }
                        } else {
                            dateSelected.add(date);
                            BGCircle circle = createCircle(
                                    region.getBounds().centerX() + indexMonth * width,
                                    region.getBounds().centerY() + indexYear * height);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                ValueAnimator animScale1 =
                                        ObjectAnimator.ofInt(circle, "radius", 0, animZoomOut1);
                                animScale1.setDuration(250);
                                animScale1.setInterpolator(decelerateInterpolator);
                                animScale1.addUpdateListener(scaleAnimationListener);

                                ValueAnimator animScale2 =
                                        ObjectAnimator.ofInt(circle, "radius", animZoomOut1, animZoomIn1);
                                animScale2.setDuration(100);
                                animScale2.setInterpolator(accelerateInterpolator);
                                animScale2.addUpdateListener(scaleAnimationListener);

                                ValueAnimator animScale3 =
                                        ObjectAnimator.ofInt(circle, "radius", animZoomIn1, animZoomOut2);
                                animScale3.setDuration(150);
                                animScale3.setInterpolator(decelerateInterpolator);
                                animScale3.addUpdateListener(scaleAnimationListener);

                                ValueAnimator animScale4 =
                                        ObjectAnimator.ofInt(circle, "radius", animZoomOut2, circleRadius);
                                animScale4.setDuration(50);
                                animScale4.setInterpolator(accelerateInterpolator);
                                animScale4.addUpdateListener(scaleAnimationListener);

                                AnimatorSet animSet = new AnimatorSet();
                                animSet.playSequentially(animScale1, animScale2, animScale3, animScale4);
                                animSet.start();
                            }
                            cirApr.put(date, circle);
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                                invalidate();
                            }
                        }
                    } else if (mDPMode == DPMode.NONE) {
                        if (regions.contains(region)) {
                            regions.remove(region);
                        } else {
                            regions.add(region);
                        }
                        final String date = centerYear + "-" + centerMonth + "-" +
                                mCManager.obtainDPInfo(centerYear, centerMonth)[i][j].strG;
                        if (dateSelected.contains(date)) {
                            dateSelected.remove(date);
                        } else {
                            dateSelected.add(date);
                        }
                    }
                }
            }
        }
    }

    private void computeDate() {
        rightYear = leftYear = centerYear;
        topYear = centerYear - 1;
        bottomYear = centerYear + 1;

        topMonth = centerMonth;
        bottomMonth = centerMonth;

        rightMonth = centerMonth + 1;
        leftMonth = centerMonth - 1;

        if (centerMonth == 12) {
            rightYear++;
            rightMonth = 1;
        }
        if (centerMonth == 1) {
            leftYear--;
            leftMonth = 12;
        }
        if (null != onDateChangeListener) {
            onDateChangeListener.onYearChange(centerYear);
            onDateChangeListener.onMonthChange(centerMonth);
        }
    }

    interface OnDateChangeListener {
        void onMonthChange(int month);

        void onYearChange(int year);
    }

    private enum SlideMode {
        VER,
        HOR
    }

    private class BGCircle {
        private float x, y;
        private int radius;
        private String dayNum;
        private ShapeDrawable shape;

        public BGCircle(ShapeDrawable shape) {
            this.shape = shape;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public ShapeDrawable getShape() {
            return shape;
        }

        public void setShape(ShapeDrawable shape) {
            this.shape = shape;
        }

        public String getDayNum() {
            return dayNum;
        }

        public void setDayNum(String dayNum) {
            this.dayNum = dayNum;
        }

    }

    public void nextYear() {
        centerYear = centerYear + 1;
        indexYear++;
        buildRegion();
        computeDate();
        smoothScrollTo(width * indexMonth, height * indexYear);
        lastMoveY = height * indexYear;
    }

    public void nextMonth() {
        indexMonth++;
        centerMonth = (centerMonth + 1) % 13;
        if (centerMonth == 0) {
            centerMonth = 1;
            centerYear++;
        }

        buildRegion();
        computeDate();
        smoothScrollTo(width * indexMonth, indexYear * height);
        lastMoveX = width * indexMonth;
    }

    public void priviousYear() {
        indexYear--;
        centerYear = centerYear - 1;

        buildRegion();
        computeDate();
        smoothScrollTo(width * indexMonth, height * indexYear);
        lastMoveY = height * indexYear;
    }

    public void priviousMonth() {
        indexMonth--;
        centerMonth = (centerMonth - 1) % 12;
        if (centerMonth == 0) {
            centerMonth = 12;
            centerYear--;
        }
        buildRegion();
        computeDate();
        smoothScrollTo(width * indexMonth, indexYear * height);
        lastMoveX = width * indexMonth;
    }

    public void firtShowSelected() {
        //defineSelectedRegion();
    }

    public void defineSelectedRegion() {
        DPInfo[][] info = mCManager.obtainDPInfo(centerYear, centerMonth);
        Region[][] tmp;
        if (TextUtils.isEmpty(info[4][0].strG)) {
            tmp = MONTH_REGIONS_4;
        } else if (TextUtils.isEmpty(info[5][0].strG)) {
            tmp = MONTH_REGIONS_5;
        } else {
            tmp = MONTH_REGIONS_6;
        }
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                Region region = tmp[i][j];
                if (TextUtils.isEmpty(mCManager.obtainDPInfo(centerYear, centerMonth)[i][j].strG)) {
                    continue;
                }

                if (true) {
                    List<Region> regions = REGION_SELECTED.get(indexYear + ":" + indexMonth);
                    if (mDPMode == DPMode.MULTIPLE) {
                        if (!regions.contains(region)) {
                            // regions.remove(region);
                            regions.add(region);
                        }

                        final String date = centerYear + "-" + centerMonth + "-" +
                                mCManager.obtainDPInfo(centerYear, centerMonth)[i][j].strG;
                        if (theDateSignin(date)) {
                            dateSelected.add(date);
                            BGCircle circle = createCircle(
                                    region.getBounds().centerX() + indexMonth * width,
                                    region.getBounds().centerY() + indexYear * height);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                ValueAnimator animScale1 =
                                        ObjectAnimator.ofInt(circle, "radius", 0, animZoomOut1);
                                animScale1.setDuration(250);
                                animScale1.setInterpolator(decelerateInterpolator);
                                animScale1.addUpdateListener(scaleAnimationListener);

                                ValueAnimator animScale2 =
                                        ObjectAnimator.ofInt(circle, "radius", animZoomOut1, animZoomIn1);
                                animScale2.setDuration(100);
                                animScale2.setInterpolator(accelerateInterpolator);
                                animScale2.addUpdateListener(scaleAnimationListener);

                                ValueAnimator animScale3 =
                                        ObjectAnimator.ofInt(circle, "radius", animZoomIn1, animZoomOut2);
                                animScale3.setDuration(150);
                                animScale3.setInterpolator(decelerateInterpolator);
                                animScale3.addUpdateListener(scaleAnimationListener);

                                ValueAnimator animScale4 =
                                        ObjectAnimator.ofInt(circle, "radius", animZoomOut2, circleRadius);
                                animScale4.setDuration(50);
                                animScale4.setInterpolator(accelerateInterpolator);
                                animScale4.addUpdateListener(scaleAnimationListener);

                                AnimatorSet animSet = new AnimatorSet();
                                animSet.playSequentially(animScale1, animScale2, animScale3, animScale4);
                                animSet.start();
                            }
                            cirApr.put(date, circle);
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                                invalidate();
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean theDateSignin(String date) {
        if (date.endsWith("12")) {
            return true;
        }
        return false;
    }

    public int getCenterYear() {
        return centerYear;
    }

    public int getCenterMonth() {
        return centerMonth;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private class ScaleAnimationListener implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            MonthView.this.invalidate();
        }
    }

    public void setBackgroundColor(int color) {
        backgroundcolor = color;
        //setBackgroundColor(backgroundcolor);
        invalidate();
    }
}
