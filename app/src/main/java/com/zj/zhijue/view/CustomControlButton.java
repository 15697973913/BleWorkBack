package com.zj.zhijue.view;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zj.zhijue.R;


public class CustomControlButton extends AppCompatButton{

    private int normalButtonBackGround;
    private int pressedButtonBackGournd;
    private int unclickableButtonBackGround;
    private int unclickableTextColor;
    private int normalTextColor;
    private int pressedTextColor;
    private Context mContext;

    public CustomControlButton(Context context) {
        this(context, null);
    }

    public CustomControlButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomControlButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs(attrs,defStyleAttr);
        initView();
    }

    private void initAttrs(AttributeSet attributeSet, int defStyleRes) {
       /* TintTypedArray tta = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.CustomButton);*/
        TypedArray tta = mContext.obtainStyledAttributes(attributeSet, R.styleable.CustomButton, R.style.customebutton, defStyleRes);
        //两种字体颜色和背景
        normalButtonBackGround = tta.getResourceId(R.styleable.CustomButton_normalbackground, R.drawable.oval_circle_shape);
        pressedButtonBackGournd = tta.getResourceId(R.styleable.CustomButton_pressedbackground,  R.drawable.circle_shape);
        normalTextColor = tta.getColor(R.styleable.CustomButton_normaltextcolor, getResources().getColor(R.color.login_phone_click));
        pressedTextColor = tta.getColor(R.styleable.CustomButton_pressedtextcolor, getResources().getColor(R.color.main_background_color));
        unclickableButtonBackGround = tta.getResourceId(R.styleable.CustomButton_unclickablebackground, R.drawable.unable_oval_circle_shape);
        unclickableTextColor = tta.getColor(R.styleable.CustomButton_unclickabletextcolor, getResources().getColor(R.color.main_body_text_color));
        tta.recycle();

    }

    private void initView() {
        setTextColor(normalTextColor);
        setBackgroundResource(normalButtonBackGround);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
            if (isEnabled()) {
                if (action == MotionEvent.ACTION_DOWN) {
                    setTextColor(pressedTextColor);
                    setBackgroundResource(pressedButtonBackGournd);
                }

                if (action == MotionEvent.ACTION_UP) {
                    setTextColor(normalTextColor);
                    setBackgroundResource(normalButtonBackGround);
                }
            } else {
                setTextColor(unclickableTextColor);
               // setBackgroundResource(unclickableButtonBackGround);
            }
        return super.onTouchEvent(event);
    }

}
