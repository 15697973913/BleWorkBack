package com.zj.zhijue.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.TintTypedArray;

import com.zj.zhijue.R;
import com.zj.zhijue.util.view.ui.DeviceUtils;


public class BatteryView extends View {

	private int mPower = 100;

	int battery_left = 0;
	int battery_top = 0;
	int battery_width = 25;
	int battery_height = 15;

	int battery_head_width = 3;
	int battery_head_height = 3;

	int battery_inside_margin = 3;

	int outRectLineWidth = 0;
	int outerRectColor = Color.BLACK;
	int innerRectColor = Color.BLACK;
	
	public BatteryView(Context context) {
		super(context);
	}
	
	public BatteryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttrs(context, attrs);
	}

	/**
	 * 初始化属性
	 * @param attrs
	 */
	private void initAttrs(Context context, AttributeSet attrs) {
		TintTypedArray tta = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
				R.styleable.BatteryViewAttr);
		mPower = tta.getInteger(R.styleable.BatteryViewAttr_batterlevel, 5);
		outRectLineWidth = tta.getDimensionPixelSize(R.styleable.BatteryViewAttr_outer_rect_line_width, 2);
		battery_left = tta.getDimensionPixelSize(R.styleable.BatteryViewAttr_battery_left, (int) DeviceUtils.dipToPx(context, 0));
		battery_top = tta.getDimensionPixelSize(R.styleable.BatteryViewAttr_battery_top, (int) DeviceUtils.dipToPx(context, 0));
		battery_width = tta.getDimensionPixelSize(R.styleable.BatteryViewAttr_battery_width, (int) DeviceUtils.dipToPx(context, 22));
		battery_height = tta.getDimensionPixelSize(R.styleable.BatteryViewAttr_battery_height, (int) DeviceUtils.dipToPx(context, 10));
		battery_head_width = tta.getDimensionPixelSize(R.styleable.BatteryViewAttr_battery_head_width, (int) DeviceUtils.dipToPx(context, 3));
		battery_head_height = tta.getDimensionPixelSize(R.styleable.BatteryViewAttr_battery_head_height, (int) DeviceUtils.dipToPx(context, 6));
		battery_inside_margin = tta.getDimensionPixelSize(R.styleable.BatteryViewAttr_battery_inside_margin, (int) DeviceUtils.dipToPx(context, 2));


		outerRectColor = tta.getColor(R.styleable.BatteryViewAttr_outer_rect_color, context.getResources().getColor(android.R.color.black));
		innerRectColor = tta.getColor(R.styleable.BatteryViewAttr_inner_rect_color, context.getResources().getColor(android.R.color.black));
		tta.recycle();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//先画外框
		Paint paint = new Paint();
		paint.setColor(outerRectColor);
		paint.setStrokeWidth(outRectLineWidth);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		
		Rect rect = new Rect(battery_left, battery_top,
				battery_left + battery_width, battery_top + battery_height);
		canvas.drawRect(rect, paint);
		
		float power_percent = mPower / 100.0f;
		
		Paint paint2 = new Paint(paint);
		paint2.setStyle(Paint.Style.FILL);
		paint2.setColor(innerRectColor);

		//画电量
		if(power_percent != 0) {
			int p_left = battery_left + battery_inside_margin;
			int p_top = battery_top + battery_inside_margin;
			int p_right = p_left + (int)((battery_width - 2 * battery_inside_margin) * power_percent);
			int p_bottom = p_top + battery_height - battery_inside_margin * 2;
			Rect rect2 = new Rect(p_left, p_top, p_right , p_bottom);
			canvas.drawRect(rect2, paint2);
		}
		
		//画电池头
		int h_left = battery_left + battery_width;
		int h_top = battery_top + battery_height / 2 - battery_head_height / 2;
		int h_right = h_left + battery_head_width;
		int h_bottom = h_top + battery_head_height;
		Rect rect3 = new Rect(h_left, h_top, h_right, h_bottom);
		canvas.drawRect(rect3, paint2);
	}
	
	public void setPower(int power) {
		mPower = power;
		if(mPower < 0) {
			mPower = 0;
		}
		if (mPower > 100) {
			mPower = 100;
		}

		invalidate();
	}

	public void setInnerRectColor(int innerRectColor) {
		this.innerRectColor = innerRectColor;
		invalidate();
	}
}