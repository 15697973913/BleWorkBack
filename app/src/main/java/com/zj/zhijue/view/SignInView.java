package com.zj.zhijue.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.android.common.baselibrary.util.DateUtil;
import com.zj.zhijue.R;


import java.util.ArrayList;
import java.util.List;


public class SignInView extends View {
    private static final int DEF_HEIGHT = 85;
    private static final int DEF_PADDING = 10;
    private static final int TEXT_MARGIN_TOP = 13;
    private static final float SECTION_SCALE = 1.5F / 2;
    private static final float SIGN_IN_BALL_SCALE = 1F / 6;
    private static final float SIGN_BG_RECT_SCALE = 1F / 4;//连线的高度比
    private static final long DEF_ANIM_TIME = 1000;

    private int unsignInBgColor;
    private int signInBgNormalHollowCircleColor;
    private int signInnerPbColor;
    private int signInCheckColor;
    private int singInTextColor;
    private int singInTextSize;

    private Paint signInBgPaint;
    private Paint signInNormalHollowPaint;
    private Paint signInPbPaint;
    private Paint signInCheckPaint;
    private Paint signInTextPaint;
    private Paint topUnSignCirclePaint;

    private int viewWidth;
    private int viewHeight;
    private int viewPadding;
    private int signInBallRadio;
    private int topSignInBallRadius;//顶部圆形球的半径大小
    private int signRectHeight;

    private RectF signInBgRectF;
    private int circleY;
    private int descY;
    private int bottomDescY;

    private List<String> topViewData;
    private List<String> bottomViewData;
    private List<Point> circlePoints;
    private List<Point> descPoints;
    private List<Point> bottomDescPoints;
    private List<Path> signInPaths;
    private List<RectF> signInPbRectFs;
    private List<String[]> yearDateList;


    private int currentSignTag;
    private ValueAnimator valueAnimator;
    private float persent;
    private RectF progressRectF;

    public SignInView(Context context) {
        this(context, null);
    }

    public SignInView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignInView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("--TAG---", "构造函数--->>");
        initAttrs(context, attrs, defStyleAttr);
        initToolsAndData();
        //setBackgroundColor(Color.WHITE);
        setBackground(getResources().getDrawable(R.drawable.bg_signin_seven_day));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("--TAG---", "onSizeChanged--->>");
        viewPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_PADDING, getResources().getDisplayMetrics());
        int textMarginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TEXT_MARGIN_TOP, getResources().getDisplayMetrics());

        viewWidth = w;
        viewHeight = h;
        Log.e("--TAG---", "onSizeChanged--- viewWidth = " + viewWidth + "  viewHeight = " + viewHeight);
        signInBallRadio = (int) (viewHeight * SIGN_IN_BALL_SCALE / 2);
        topSignInBallRadius = signInBallRadio * 2;
        signRectHeight = (int) (signInBallRadio * SIGN_BG_RECT_SCALE);

        signInBgRectF = new RectF(0, viewHeight * SECTION_SCALE - signInBallRadio - signRectHeight, viewWidth, viewHeight * SECTION_SCALE - signInBallRadio);

        circleY = (int) (signInBgRectF.top + signRectHeight / 2);
        descY = 2 * topSignInBallRadius;
        bottomDescY = (int) (viewHeight * SECTION_SCALE) + signInBallRadio + textMarginTop;
        calculateCirclePoints(topViewData);
        if (null != signInPbRectFs && signInPbRectFs.size() != 0) {
            progressRectF = signInPbRectFs.get(0);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("--TAG---", "onMeasure--->>");

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureHeight;

        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            measureHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_HEIGHT, getResources().getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSignInBgRect(canvas);//灰色连线（未签到的状态）
        //drawSignInPbRectNoAnim(canvas);//签到之后的线段
        drawSignInNormalCircle(canvas);//未签到实心圆形
        drawSignInNormalHollowCircle(canvas);//未签到正情况下的空心圆形
        drawSingInCheckCircle(canvas);//签到之后的圆形（打钩图形）
        drawSingInCheckHollowCircle(canvas);//签到之后的蓝色空心圆形
        drawBttomDesc(canvas);
    }

    private boolean isNeedReturn() {
        return currentSignTag < 0 || currentSignTag >= topViewData.size();
    }

    private void drawSingInCheckCircle(Canvas canvas) {

        int bottomViewDataCount = bottomViewData.size();
        if (bottomViewDataCount <= 0) {
            return;
        }
        for (int i = -1; i < bottomViewDataCount - 1; i++) {
            String bottomDateStr = bottomViewData.get(i + 1);
            if (theDateIsSignIn(bottomDateStr)) {
                Point p = circlePoints.get(i + 1);
                //Path path = signInPaths.get(i + 1);
                canvas.drawCircle(p.x, p.y, signInBallRadio * 0.5f, signInPbPaint);
            }
            //canvas.drawPath(path, signInCheckPaint);
        }
    }

    /**
     * 绘制签到之后的蓝色空心圆
     * @param canvas
     */
    private void drawSingInCheckHollowCircle(Canvas canvas) {
       /* if (isNeedReturn()) {
            return;
        }*/
        int bottomViewDataCount = bottomViewData.size();
        if (bottomViewDataCount <= 0) {
            return;
        }
        signInCheckPaint.setColor(getResources().getColor(R.color.center_signin_hollow_circle_color));
        signInCheckPaint.setStrokeWidth(signInBallRadio * 0.5f);
        for (int i = -1; i < bottomViewDataCount - 1; i++) {
            String bottomDateStr = bottomViewData.get(i + 1);
            if (theDateIsSignIn(bottomDateStr)) {
                Point p = circlePoints.get(i + 1);
                //Path path = signInPaths.get(i + 1);
                canvas.drawCircle(p.x, p.y, signInBallRadio * 0.75f, signInCheckPaint);
            }
            //canvas.drawPath(path, signInCheckPaint);
        }
    }

    private void drawBttomDesc(Canvas canvas) {
        signInTextPaint.setColor(getResources().getColor(R.color.bottom_text_color));
        if (null != bottomViewData && bottomViewData.size() > 0) {
            for (int i = 0; i < bottomViewData.size(); i++) {
                Point p = bottomDescPoints.get(i);
                String bottomDesText = bottomViewData.get(i);
                if (bottomDesText.equals(DateUtil.getTodayDateStr())) {
                    bottomDesText = "今天";
                } else if (bottomDesText.equals(DateUtil.getTommorrowDateStr())) {
                    //bottomViewData.add("明天");
                    bottomDesText = "明天";
                }
                canvas.drawText(bottomDesText, p.x, p.y, signInTextPaint);
            }
        }
        drawUnSignTopCircle(canvas);
        drawSignInTopCircle(canvas);
    }

    private void drawUnSignTopCircle(Canvas canvas) {
        topUnSignCirclePaint = creatPaint(getResources().getColor(R.color.top_unsignin_circle_color), 0, Paint.Style.FILL, 0);
        signInTextPaint.setColor(getResources().getColor(R.color.top_unsignin_text_color));
        if (null != topViewData && topViewData.size() > 0) {
            for (int i = 0; i < topViewData.size(); i++) {
                Point textPoint = descPoints.get(i);
                Point circle = circlePoints.get(i);
                canvas.drawCircle( circle.x, textPoint.y, topSignInBallRadius, topUnSignCirclePaint);
                String textStr = topViewData.get(i);
                canvas.drawText(textStr, textPoint.x, textPoint.y - getTextHeight() / 2, signInTextPaint);
            }
        }
    }

    private void drawSignInTopCircle(Canvas canvas) {
       /* if (isNeedReturn()) {
            return;
        }
*/
        topUnSignCirclePaint.setColor(getResources().getColor(R.color.top_signin_circle_color));
        signInTextPaint.setColor(Color.WHITE);
        int bottomViewDataCount = bottomViewData.size();

        if (bottomViewDataCount <= 0) {
            return;
        }

        if (null != topViewData && topViewData.size() > 0) {
            for (int i = -1; i < bottomViewDataCount - 1; i++) {
                String bottomDateStr = bottomViewData.get(i + 1);
                if (theDateIsSignIn(bottomDateStr)) {
                    Point textPoint = descPoints.get(i + 1);
                    Point circle = circlePoints.get(i + 1);
                    canvas.drawCircle( circle.x, textPoint.y, topSignInBallRadius, topUnSignCirclePaint);
                    String textStr = topViewData.get(i + 1);
                    canvas.drawText(textStr, textPoint.x, textPoint.y - getTextHeight() / 2, signInTextPaint);
                }
            }
        }
    }

    private float getTextHeight() {
        float textHeight = signInTextPaint.ascent()+ signInTextPaint.descent();
        return textHeight;
    }

    private void drawSignInNormalCircle(Canvas canvas) {
        if (circlePoints != null && circlePoints.size() > 0) {
            for (Point p : circlePoints) {
                canvas.drawCircle(p.x, p.y, signInBallRadio * 0.5f, signInBgPaint);
            }
        }
    }

    /**
     * 未签到情况下的，空心圆
     */
    private void drawSignInNormalHollowCircle(Canvas canvas) {
        signInNormalHollowPaint.setStrokeWidth(signInBallRadio * 0.5f);
        signInNormalHollowPaint.setColor(getResources().getColor(R.color.center_unsignin_hollow_circle_color));
        if (circlePoints != null && circlePoints.size() > 0) {
            for (Point p : circlePoints) {
                canvas.drawCircle(p.x, p.y, signInBallRadio * 0.75f, signInNormalHollowPaint);
            }
        }
    }

    private void drawSignInBgRect(Canvas canvas) {
        canvas.drawRect(signInBgRectF, signInBgPaint);
    }

    private Paint creatPaint(int paintColor, int textSize, Paint.Style style, int lineWidth) {
        Paint paint = new Paint();
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(lineWidth);
        paint.setDither(true);
        paint.setTextSize(textSize);
        paint.setStyle(style);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        return paint;
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SignInView, defStyleAttr, R.style.def_sign_in_style);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.SignInView_unsign_in_bg_clor:
                    unsignInBgColor = typedArray.getColor(attr, Color.BLACK);
                    break;

                case R.styleable.SignInView_sign_in_normal_hollow_circle:
                    signInBgNormalHollowCircleColor = typedArray.getColor(attr, Color.BLACK);
                    break;

                case R.styleable.SignInView_sign_in_pb_clor:
                    signInnerPbColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.SignInView_sign_in_check_clor:
                    signInCheckColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.SignInView_sign_in_text_clor:
                    singInTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.SignInView_sign_in_text_size:
                    singInTextSize = typedArray.getDimensionPixelSize(attr, 0);
                    break;
            }
        }
        typedArray.recycle();
    }

    private void initToolsAndData() {
        circlePoints = new ArrayList<>();
        descPoints = new ArrayList<>();
        bottomDescPoints = new ArrayList<>();
        signInPaths = new ArrayList<>();
        signInPbRectFs = new ArrayList<>();
        currentSignTag = -1;
        valueAnimator = getValA(DEF_ANIM_TIME);

        signInBgPaint = creatPaint(unsignInBgColor, 0, Paint.Style.FILL, 0);
        signInNormalHollowPaint = creatPaint(signInBgNormalHollowCircleColor, 0, Paint.Style.STROKE, 0);
        signInPbPaint = creatPaint(signInnerPbColor, 0, Paint.Style.FILL, 0);
        signInCheckPaint = creatPaint(signInCheckColor, 0, Paint.Style.STROKE, 3);
        signInTextPaint = creatPaint(singInTextColor, singInTextSize, Paint.Style.FILL, 0);
    }

    public void setSignInData(List<String> signInData, List<String> bottomViewData) {
        Log.e("--TAG---", "外界设置数据--->>");
        if (null != signInData) {
            topViewData = signInData;
        }
        if (null != bottomViewData) {
            this.bottomViewData = bottomViewData;
        }
    }

    public void signInEvent(List<String[]> yearDateListParam) {
        yearDateList = yearDateListParam;

        currentSignTag++;
        if (currentSignTag >= topViewData.size()) {
            currentSignTag = topViewData.size() - 1;
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                persent = Float.valueOf(valueAnimator.getAnimatedValue().toString());
                invalidate();
                Log.e("TAG--->>", "persent--->" + persent);
            }
        });
        valueAnimator.start();
        invalidate();
    }

    public void resetSignView() {
        currentSignTag = -1;
        invalidate();
    }

    private void calculateCirclePoints(List<String> viewData) {
        if (null != viewData) {
            int intervalSize = viewData.size() - 1;
            int onePiece = (viewWidth - 2 * viewPadding - signInBallRadio * 2 * viewData.size()) / intervalSize;
            for (int i = 0; i < viewData.size(); i++) {
                String bottomDesText = bottomViewData.get(i);
                if (bottomDesText.equals(DateUtil.getTodayDateStr())) {
                    bottomDesText = "今天";
                } else if (bottomDesText.equals(DateUtil.getTommorrowDateStr())) {
                    //bottomViewData.add("明天");
                    bottomDesText = "明天";
                }

                Point circleP = new Point(viewPadding + i * onePiece + ((i + 1) * 2 - 1) * signInBallRadio, circleY);//画小圆形
                //画文字
                Point descP = new Point((int) (viewPadding + i * onePiece + ((i + 1) * 2 - 1) * signInBallRadio - signInTextPaint.measureText(viewData.get(i)) / 2), descY);
                Point bottomDescP = new Point((int) (viewPadding + i * onePiece + ((i + 1) * 2 - 1) * signInBallRadio - signInTextPaint.measureText(bottomDesText) / 2), bottomDescY);

                Path signInPath = new Path();/** 画√*/
                signInPath.moveTo(circleP.x - signInBallRadio / 2, circleP.y);
                signInPath.lineTo(circleP.x, circleP.y + signInBallRadio / 2);
                signInPath.lineTo(circleP.x + signInBallRadio / 2, circleP.y - signInBallRadio + signInBallRadio / 2);

                //画签到之后的连线
                RectF signInPbRectF = new RectF(viewPadding + signInBallRadio, viewHeight * SECTION_SCALE - signInBallRadio - signRectHeight, circleP.x, viewHeight * SECTION_SCALE - signInBallRadio);

                signInPaths.add(signInPath);
                circlePoints.add(circleP);
                descPoints.add(descP);
                bottomDescPoints.add(bottomDescP);
                signInPbRectFs.add(signInPbRectF);
            }
        }
    }

    private ValueAnimator getValA(long countdownTime) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.F);
        valueAnimator.setDuration(countdownTime);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(0);
        return valueAnimator;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("--TAG---", "onAttachedToWindow--->>");
    }

    /**
     *
     * @param dateStr 月.日
     * @return
     */
    private boolean theDateIsSignIn(String dateStr) {
        if (CommonUtils.isEmpty(dateStr) || null == yearDateList || yearDateList.size() == 0) {
            return false;
        }

        for (String[] dateYearStr : yearDateList) {

            if (null == dateYearStr || dateYearStr.length == 0) {
                continue;
            }

            String yearStr = dateYearStr[0];
            String monthStr = dateYearStr[1];
            String dayStr = dateYearStr[2];
            String weekStr = dateYearStr[3];

            String[] dateStrSplit = dateStr.split("\\.");
            if (Integer.parseInt(monthStr) == Integer.parseInt(dateStrSplit[0])) {
                if (Integer.parseInt(dayStr) == Integer.parseInt(dateStrSplit[1])) {
                    return true;
                }
            }
        }
        return false;
    }
}
