package com.zj.zhijue.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.util.view.ui.DeviceUtils;



public class CustomerLinearLayout extends LinearLayout {
    private final String TAG = CustomerLinearLayout.class.getSimpleName();

    private Paint backPaint = new Paint();
    private Rect rect = new Rect();

    private int divideCount = 3;

    public CustomerLinearLayout(Context context) {
        super(context);
    }

    public CustomerLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomerLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawRectangle(canvas);
        super.onDraw(canvas);
    }

    /**
     * 画正方形
     */
    private void drawRectangle(Canvas canvas) {
        //MLog.d(TAG + " drawRectangle");
        int width = getWidth();
        int height = getHeight();
        backPaint.setAntiAlias(true);
        backPaint.setColor(getResources().getColor(R.color.bleglasses_main_bg_color));
        backPaint.setStrokeWidth(DeviceUtils.dipToPx(MyApplication.getInstance(), 1));

        /**
         * 最外层的正方形的四条边
         */
        float[] floats = new float[16];

        floats[0] = 0;
        floats[1] = 0;
        floats[2] = width;
        floats[3] = 0;

        floats[4] = width;
        floats[6] = 0;
        floats[6] = width;
        floats[7] = height;


        floats[8] = width;
        floats[9] = height;
        floats[10] = 0;
        floats[11] = height;

        floats[12] = 0;
        floats[13] = height;
        floats[14] = 0;
        floats[15] = 0;

        canvas.drawLines(floats, backPaint);

        float[] verticalFloast = new float[4 * (divideCount - 1)];

        verticalFloast[0] = width / divideCount;
        verticalFloast[1] = 0;
        verticalFloast[2] = width / divideCount;
        verticalFloast[3] = height;

        verticalFloast[4] = width * 2 / divideCount;
        verticalFloast[5] = 0;
        verticalFloast[6] = width * 2 / divideCount;
        verticalFloast[7] = height;

       /* verticalFloast[8] = width * 3 / divideCount;
        verticalFloast[9] = 0;
        verticalFloast[10] = width * 3 / divideCount;
        verticalFloast[11] = height;
*/
        canvas.drawLines(verticalFloast, backPaint);
    }
}
