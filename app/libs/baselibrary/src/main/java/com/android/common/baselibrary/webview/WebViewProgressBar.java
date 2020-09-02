package com.android.common.baselibrary.webview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.shitec.bleglasses.baselibrary.R;


public class WebViewProgressBar extends View {
    private int progress = 1;//进度默认为1
    private final static int HEIGHT = 5;//进度条高度
    private Paint paint;//进度条的画笔
    //  渐变颜色数组
    private final static int colors[] = new int[]{0xFF7AD237, 0xFF8AC14A, 0x35B056 }; //int类型颜色值格式：0x+透明值+颜色的rgb
    public WebViewProgressBar(Context context) {
        this (context, null);
    }

    public WebViewProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WebViewProgressBar(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        initPaint(context);
    }

    private void initPaint(Context context) {
        //颜色渐变从colors[0]到colors[2],透明度从0到1
//        LinearGradient shader = new LinearGradient(
//                0, 0,
//                100, HEIGHT,
//                colors,
//                new float[]{0 , 0.5f, 1.0f},
//                Shader.TileMode.MIRROR);
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setStyle(Paint.Style.STROKE);//填充方式为描边
        paint.setStrokeWidth(HEIGHT);//设置画笔的宽度
        paint.setAntiAlias(true);//抗锯齿
        paint.setDither(true);//使用抖动效果
        paint.setColor(context.getResources().getColor(R.color.progress_bar_color));
        // paint.setShader(shader);//画笔设置渐变
    }

    /**
     * 设置进度
     * @param progressParam
     */
    public void setProgress(int progressParam) {
        this.progress = progressParam;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth() * progress / 100, HEIGHT, paint);
    }
}
