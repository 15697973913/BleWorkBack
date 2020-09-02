package com.zj.zhijue.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.zj.zhijue.R;

import java.util.ArrayList;
import java.util.List;

public class RemoteControllerView extends View {
    private static final String TAG = "RemoteControllerView";
    private static final float SCALE_OF_PADDING = 40.F / 320;
    private static final float SCALE_OF_BIG_CIRCLE = 288.F / 320;
    private static final float SCALE_OF_SMALL_CIRCLE = 100.F / 320;
    private static final float DEF_VIEW_SIZE = 300;

    private OnRemoteControllerClickListener remoteControllerClickListener;

    private int rcvViewHeight;
    private int rcvViewWidth;
    private int rcvPadding;
    private int viewContentHeight;
    private int viewContentWidht;
    private Point centerPoint;
    private int rcvTextColor;
    private int rcvShadowColor;
    private int rcvStrokeColor;
    private int rcvStrokeWidth;
    private int rcvTextSize;
    private int rcvDegree;
    private int rcvOtherDegree;
    private Paint rcvTextPaint;
    private Paint rcvBlackTextPaint;
    private Paint rcvShadowPaint;
    private Paint rcvStrokePaint;
    private Paint rcvWhitePaint;


    private RectF ovalRectF;
    private List<Path> ovalPaths;
    private List<Float> startDegree;
    private List<Region> ovalRegions;
    private List<Paint> ovalPaints;
    private int seleced = -1;
    private Point textPointInView;
    private Point leftTextBlackPointInView;
    private Point topTextBlackPointInView;
    private Point rightTextBlackPointInView;
    private Point bottomTextBlackPointInView;

    private Resources mResources;
    private Bitmap mBigBackgroundBitMap;
    private int mBigBackWidth, mBigBackHeight;
    private Rect mBigBackGroundSrcRect, mBigBackGroundDesRect;
    private float innerCircleRadius = 0;

    private int mBackHeight = 0;
    private String innectText;
    private String leftText;
    private String topText;
    private String rightText;
    private String bottomText;

    private int mCurrentTrainMode = -1;

    public RemoteControllerView(Context context) {
        this(context, null);
    }

    public RemoteControllerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RemoteControllerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mResources = getResources();
        initAttribute(context, attrs, defStyleAttr);
        initPaints();
        initBackgroundBitMap();
        initText();
    }

    private void initText() {

        innectText = mResources.getText(R.string.intervene_text).toString();
        leftText = mResources.getText(R.string.start_text).toString();
        topText = mResources.getText(R.string.pause_text).toString();
        rightText = mResources.getText(R.string.stop_text).toString();
        bottomText = mResources.getText(R.string.continue_text).toString();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //MLog.d("onSizeChanged w = " + w + " h = " + h);
        centerPoint = new Point(w / 2, h / 2);

        ovalPaths = new ArrayList<>();
        ovalRegions = new ArrayList<>();
        ovalPaints = new ArrayList<>();
        startDegree = new ArrayList<>();

        rcvViewWidth = w;
        rcvViewHeight = h;
        rcvPadding = (int) (Math.min(w, h) * SCALE_OF_PADDING);
        viewContentWidht = rcvViewWidth - rcvPadding;
        viewContentHeight = rcvViewHeight - rcvPadding;
        textPointInView = getTextPointInView(rcvTextPaint, innectText, 0, 0);
        leftTextBlackPointInView = getTextPointInView(rcvBlackTextPaint, innectText, (int)-(rcvViewWidth * 0.6f) , 0);
        topTextBlackPointInView = getTextPointInView(rcvBlackTextPaint, topText, 0 , (int)-(rcvViewWidth * 0.6f));
        rightTextBlackPointInView = getTextPointInView(rcvBlackTextPaint, rightText, (int)(rcvViewWidth * 0.6f) , 0);
        bottomTextBlackPointInView = getTextPointInView(rcvBlackTextPaint, bottomText, 0 , (int)(rcvViewWidth * 0.6f));
        // 注意外环的线宽占用的尺寸
        ovalRectF = new RectF(-rcvViewWidth / 2 + rcvStrokeWidth, -rcvViewWidth / 2 + rcvStrokeWidth, rcvViewHeight / 2 - rcvStrokeWidth, rcvViewHeight / 2 - rcvStrokeWidth);

        for (int i = 0; i < 4; i++) {
            Region tempRegin = new Region();
            Path tempPath = new Path();

            float tempStarAngle = 0;
            float tempSweepAngle;
            if (i % 2 == 0) {
                tempSweepAngle = rcvDegree;
            } else {
                tempSweepAngle = rcvOtherDegree;
            }
            // 计算扇形的开始角度,这里不能用canvas旋转的方法
            // 因为设计到扇形点击,如果画布旋转,会因为角度问题,导致感官上看上去点击错乱的问题,
            // 其实点击的区域是正确的,就是因为旋转角度导致的,注意,

            // 这块需要一个n的公式,
            switch (i) {
                case 0:
                    tempStarAngle = -rcvDegree / 2;
                    break;
                case 1:
                    tempStarAngle = rcvDegree / 2;
                    break;
                case 2:
                    tempStarAngle = rcvDegree / 2 + rcvOtherDegree;
                    break;
                case 3:
                    tempStarAngle = rcvDegree / 2 + rcvOtherDegree + rcvDegree;
                    break;

            }

            tempPath.moveTo(0, 0);
            tempPath.lineTo(viewContentWidht / 2, 0);
            tempPath.addArc(ovalRectF, tempStarAngle, tempSweepAngle);
            startDegree.add(tempStarAngle);
            tempPath.lineTo(0, 0);
            tempPath.close();
            RectF tempRectF = new RectF();
            tempPath.computeBounds(tempRectF, true);
            tempRegin.setPath(tempPath, new Region((int) tempRectF.left, (int) tempRectF.top, (int) tempRectF.right, (int) tempRectF.bottom));


            ovalPaths.add(tempPath);
            ovalRegions.add(tempRegin);
            ovalPaints.add(creatPaint(Color.WHITE, 0, Paint.Style.FILL, 0));
        }

        Region smallCircleRegion = new Region();
        Path smallCirclePath = new Path();
        smallCirclePath.moveTo(0, 0);
        smallCirclePath.lineTo(Math.min(rcvViewWidth, rcvViewHeight) * SCALE_OF_SMALL_CIRCLE / 2, 0);
        smallCirclePath.addCircle(0, 0, Math.min(rcvViewWidth, rcvViewHeight) * SCALE_OF_SMALL_CIRCLE / 2, Path.Direction.CW);
        smallCirclePath.lineTo(0, 0);
        smallCirclePath.close();
        RectF tempRectF = new RectF();
        smallCirclePath.computeBounds(tempRectF, true);
        smallCircleRegion.setPath(smallCirclePath, new Region((int) tempRectF.left, (int) tempRectF.top, (int) tempRectF.right, (int) tempRectF.bottom));

        ovalPaths.add(smallCirclePath);
        ovalRegions.add(smallCircleRegion);
        ovalPaints.add(creatPaint(Color.WHITE, 0, Paint.Style.FILL, 0));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //MLog.d("onMeasure()");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize;
        int heightSize;

        //MLog.d("widthSize1 = " + MeasureSpec.getSize(widthMeasureSpec));
        //MLog.d("heightSize1 = " + MeasureSpec.getSize(heightMeasureSpec));
        mBackHeight = widthSize = heightSize = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));

        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            //widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_VIEW_SIZE, getResources().getDisplayMetrics());
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
           // MLog.d("widthSize = " + widthSize);
        }

        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            //heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_VIEW_SIZE, getResources().getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
            //MLog.d("heightSize = " + heightSize);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //MLog.d("onDraw()");
        canvas.translate(centerPoint.x, centerPoint.y);
        //canvas.drawCircle(0, 0, Math.min(rcvViewWidth, rcvViewHeight) / 2, rcvStrokePaint);
        drawBigBackGround(canvas);
        drawBiasLine(canvas);
        drawShadow(canvas);

      /*  for (int i = 0; i < ovalRegions.size(); i++) {
            if (i != 4) {
                canvas.drawPath(ovalPaths.get(i), ovalPaints.get(i));
            }
        }*/
        innerCircleRadius = Math.min(rcvViewWidth, rcvViewHeight) * SCALE_OF_SMALL_CIRCLE;
        //canvas.drawCircle(0, 0, Math.min(rcvViewWidth, rcvViewHeight) * SCALE_OF_SMALL_CIRCLE / 2, rcvStrokePaint);
        drawInnerCircle(canvas);
        //canvas.drawText("OK", textPointInView.x, textPointInView.y, rcvTextPaint);
        drawInnerText(canvas);
        drawLeftText(canvas);
        drawTopText(canvas);
        drawRightText(canvas);
        drawBottomText(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       /* if (mCurrentTrainMode == 1) {
            return true;
        }*/
        float x;
        float y;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX() - centerPoint.x;
                y = event.getY() - centerPoint.y;

                for (int i = 0; i < ovalRegions.size(); i++) {
                    Region tempRegin = ovalRegions.get(i);
                    boolean contains = tempRegin.contains((int) x, (int) y);
                    if (contains) {
                        seleced = i;
                    }
                }
                resetPaints();
                //ovalPaints.get(seleced).setColor(rcvShadowColor);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                resetPaints();
                invalidate();
                remoteClickAction();
                seleced = -1;
                break;

        }
        return true;
    }

    private void remoteClickAction() {
        if (remoteControllerClickListener != null) {
            switch (seleced) {
                case 0:
                    remoteControllerClickListener.rightClick();
                    break;
                case 1:
                    remoteControllerClickListener.bottomClick();
                    break;
                case 2:
                    remoteControllerClickListener.leftClick();
                    break;
                case 3:
                    remoteControllerClickListener.topClick();
                    break;
                case 4:
                    remoteControllerClickListener.centerOkClick();
                    break;
            }
        }
    }


    private void initAttribute(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RemoteControllerView, defStyleAttr, R.style.def_remote_controller);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.RemoteControllerView_rcv_text_color:
                    rcvTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.RemoteControllerView_rcv_text_size:
                    rcvTextSize = typedArray.getDimensionPixelSize(attr, 0);
                    break;
                case R.styleable.RemoteControllerView_rcv_shadow_color:
                    rcvShadowColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.RemoteControllerView_rcv_stroke_color:
                    rcvStrokeColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.RemoteControllerView_rcv_stroke_width:
                    rcvStrokeWidth = typedArray.getDimensionPixelOffset(attr, 0);
                    break;
                case R.styleable.RemoteControllerView_rcv_oval_degree:
                    rcvDegree = typedArray.getInt(attr, 0);
                    rcvOtherDegree = (int) ((360 - rcvDegree * 2) / 2.F);
                    break;

            }
        }
        typedArray.recycle();
    }


    private void initPaints() {
        rcvTextPaint = creatPaint(rcvTextColor, rcvTextSize, Paint.Style.FILL, 0);
        rcvBlackTextPaint = creatPaint(mResources.getColor(R.color.login_title), rcvTextSize, Paint.Style.FILL, 0);
        rcvShadowPaint = creatPaint(rcvShadowColor, 0, Paint.Style.FILL, 0);
        rcvStrokePaint = creatPaint(rcvStrokeColor, 0, Paint.Style.STROKE, 0);
        rcvWhitePaint = creatPaint(Color.WHITE, 0, Paint.Style.FILL, 0);
    }

    private void drawBigBackGround(Canvas canvas) {
        //canvas.save();
        Bitmap bitMap = getNewBackBitMap(initBackgroundBitMap(), mBackHeight, mBackHeight);
        bitMap =  tintBitmap(bitMap, mResources.getColor(R.color.blelgasses_main_control_view_bg));
        canvas.drawBitmap(bitMap, - mBackHeight / 2 , - mBackHeight / 2, null);

    }

    /**
     * 画斜线
     * @param canvas
     */
    private void drawBiasLine(Canvas canvas) {
        //circlefengexian
         float newHeight = (float) Math.sqrt(Math.pow(mBackHeight / 2, 2) / 2);
        Bitmap bitMapOne = getNewBackBitMap(initBiasLineBitMap(), newHeight, newHeight);
        Bitmap bitMapTwo = getNewBackBitMap(initBiasLineBitMap(), newHeight, newHeight);
        Bitmap bitMapThree = getNewBackBitMap(initBiasLineBitMap(), newHeight, newHeight);
        Bitmap bitMapFour = getNewBackBitMap(initBiasLineBitMap(), newHeight, newHeight);
        drawRotateBitmap(canvas, null, bitMapOne , 0, -newHeight,0);
        drawRotateBitmap(canvas, null, bitMapTwo , 90,-newHeight * 3 / 2,-newHeight * 3 / 2);
        drawRotateBitmap(canvas, null, bitMapThree , 180, 0,-newHeight * 2);
        drawRotateBitmap(canvas, null, bitMapFour , 270, newHeight / 2,-newHeight / 2);
    }

    private void drawShadow(Canvas canvas) {
        if (seleced >= 0 && seleced < 4) {
            float rotation = (seleced + 1) * 90;

            Bitmap newShadowBitmmap = getNewShadowBitMap(initShadowBitMap(), mBackHeight / 2);
            drawRotateBitmap(canvas, null ,newShadowBitmmap,rotation, -newShadowBitmmap.getWidth() / 2, -newShadowBitmmap.getHeight());

        }
    }

    private void drawRotateBitmap(Canvas canvas, Paint paint, Bitmap bitmap,
                                  float rotation, float posX, float posY) {
        Matrix matrix = new Matrix();
        int offsetX = bitmap.getWidth() / 2;
        int offsetY = bitmap.getHeight();
        matrix.postTranslate(-offsetX, -offsetY);
        matrix.postRotate(rotation);
        matrix.postTranslate(posX + offsetX, posY + offsetY);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    private void drawInnerCircle(Canvas canvas) {
        Bitmap bitMap = getNewBackBitMap(initInnerBitMap(), innerCircleRadius , innerCircleRadius);
        canvas.drawBitmap(bitMap, -innerCircleRadius / 2 , -innerCircleRadius / 2, null);
    }

    private void drawInnerText(Canvas canvas) {
        canvas.drawText(innectText, textPointInView.x, textPointInView.y, rcvTextPaint);
    }

    private void drawLeftText(Canvas canvas) {
        if (seleced == 2) {
            rcvBlackTextPaint.setColor(Color.WHITE);
        } else {
            rcvBlackTextPaint.setColor(mResources.getColor(R.color.login_title));
        }
        canvas.drawText(leftText, leftTextBlackPointInView.x, leftTextBlackPointInView.y, rcvBlackTextPaint);
    }

    private void drawTopText(Canvas canvas) {
        if (seleced == 3) {
            rcvBlackTextPaint.setColor(Color.WHITE);
        } else {
            rcvBlackTextPaint.setColor(mResources.getColor(R.color.login_title));
        }
        canvas.drawText(topText, topTextBlackPointInView.x, topTextBlackPointInView.y, rcvBlackTextPaint);
    }

    private void drawRightText(Canvas canvas) {
        if (seleced == 0) {
            rcvBlackTextPaint.setColor(Color.WHITE);
        } else {
            rcvBlackTextPaint.setColor(mResources.getColor(R.color.login_title));
        }
        canvas.drawText(rightText, rightTextBlackPointInView.x, rightTextBlackPointInView.y, rcvBlackTextPaint);
    }

    private void drawBottomText(Canvas canvas) {
        if (seleced == 1) {
            rcvBlackTextPaint.setColor(Color.WHITE);
        } else {
            rcvBlackTextPaint.setColor(mResources.getColor(R.color.login_title));
        }
        canvas.drawText(bottomText, bottomTextBlackPointInView.x, bottomTextBlackPointInView.y, rcvBlackTextPaint);
    }

    private Bitmap initBiasLineBitMap() {
        Bitmap biasLineBitMap= BitmapFactory.decodeResource(mResources, R.drawable.circlefengexian);
        return tintBitmap(biasLineBitMap, mResources.getColor(R.color.blelgasses_main_control_view_line_color));
    }

    private Bitmap initBackgroundBitMap() {
        mBigBackgroundBitMap = ((BitmapDrawable) mResources.getDrawable(R.mipmap.tuoyuan)).getBitmap();

        Bitmap zhuanPanBitMap= BitmapFactory.decodeResource(mResources, R.mipmap.tuoyuan);
        mBigBackWidth = zhuanPanBitMap.getWidth();
        mBigBackHeight = zhuanPanBitMap.getHeight();
        return zhuanPanBitMap;
    }

    private Bitmap initShadowBitMap() {
        Bitmap shadowBitMap= BitmapFactory.decodeResource(mResources, R.mipmap.tingzhi);
        return tintBitmap(shadowBitMap, mResources.getColor(R.color.bleglasses_main_top_bg_color));
    }

    private Bitmap initInnerBitMap() {
        Bitmap innnerBitMap= BitmapFactory.decodeResource(mResources, R.mipmap.ganyuyuan);
        return tintBitmap(innnerBitMap, mResources.getColor(R.color.bleglasses_main_bg_color));
    }

    private Bitmap getNewBackBitMap(Bitmap srcBitMap,float newWidth, float newHeight) {
       /* MLog.d("getNewBackBitMap srcBitMap = " + srcBitMap);
        MLog.d("newWidth = " + newWidth);
        MLog.d("newHeight = " + newHeight);*/
        // 定义矩阵对象
        Matrix matrix = new Matrix();
        float scale = Math.min(newWidth / srcBitMap.getWidth() , newHeight / srcBitMap.getHeight());
        //MLog.d("scale = " + scale );
        // 缩放原图
        matrix.postScale(scale, scale);
        Bitmap dstbmp = Bitmap.createBitmap(srcBitMap,0,0,srcBitMap.getWidth(),
                srcBitMap.getHeight(),matrix,true);
       // srcBitMap.recycle();

        return dstbmp;
    }

    private Bitmap getNewShadowBitMap(Bitmap srcBitMap, float newHeight) {
       /* MLog.d("getNewBackBitMap srcBitMap = " + srcBitMap);
        MLog.d("newWidth = " + newWidth);
        MLog.d("newHeight = " + newHeight);*/
        // 定义矩阵对象
        Matrix matrix = new Matrix();
        float scale = newHeight / srcBitMap.getHeight();
        //MLog.d("scale = " + scale );
        // 缩放原图
        matrix.postScale(scale, scale);
        Bitmap dstbmp = Bitmap.createBitmap(srcBitMap,0,0,srcBitMap.getWidth(),
                srcBitMap.getHeight(),matrix,true);
        // srcBitMap.recycle();

        return dstbmp;
    }


/*

    private Rect getSrcRect(){
        MLog.d("getSrcRect mBackHeight = " + mBackHeight);
        return mBigBackGroundSrcRect = new Rect(0, 0, mBigBackWidth, mBigBackHeight);
    }

    private Rect getDesRect() {
        MLog.d("getDesRect mBackHeight = " + mBackHeight);
        return mBigBackGroundDesRect = new Rect(0, 0, mBackHeight, mBackHeight);
    }
*/

    private Paint creatPaint(int paintColor, int textSize, Paint.Style style, int lineWidth) {
        Paint paint = new Paint();
        paint.setColor(paintColor);
        paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(lineWidth);
        paint.setDither(true);
        paint.setTextSize(textSize);
        paint.setStyle(style);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        return paint;
    }

    private void resetPaints() {
        for (Paint p : ovalPaints) {
            p.setColor(Color.WHITE);
        }
    }

    private Point getTextPointInView(Paint textPaint, String textDesc, int w, int h) {
        if (null == textDesc) return null;
        Point point = new Point();
        int textW = (w - (int) textPaint.measureText(textDesc)) / 2;
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        int textH = (int) Math.ceil(fm.descent - fm.top);
        point.set(textW, h / 2 + textH / 2 - textH / 4);
        return point;
    }


    public interface OnRemoteControllerClickListener {
        void topClick();

        void leftClick();

        void rightClick();

        void bottomClick();

        void centerOkClick();
    }

    public void setRemoteControllerClickListener(OnRemoteControllerClickListener remoteControllerClickListener) {
        this.remoteControllerClickListener = remoteControllerClickListener;
    }

    public void setmCurrentTrainMode(int mCurrentTrainMode) {
        this.mCurrentTrainMode = mCurrentTrainMode;
    }

    public Bitmap tintBitmap(Bitmap inBitmap , int tintColor) {
      /*  if (inBitmap == null) {
            return null;
        }
        Bitmap outBitmap = Bitmap.createBitmap (inBitmap.getWidth(), inBitmap.getHeight() , inBitmap.getConfig());
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setColorFilter( new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)) ;
        canvas.drawBitmap(inBitmap , 0, 0, paint) ;*/
        return inBitmap ;
    }


    public Bitmap tintBitmapReflect(Bitmap inBitmap , int tintColor) {
       if (inBitmap == null) {
            return null;
        }
        Bitmap outBitmap = Bitmap.createBitmap (inBitmap.getWidth(), inBitmap.getHeight() , inBitmap.getConfig());
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setColorFilter( new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)) ;
        canvas.drawBitmap(inBitmap , 0, 0, paint) ;
        return outBitmap ;
    }
}
