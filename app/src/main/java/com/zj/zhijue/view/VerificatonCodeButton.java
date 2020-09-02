package com.zj.zhijue.view;

import android.content.Context;
import android.graphics.Color;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zj.zhijue.R;


/**
 * Created by Administrator on 2018/6/30.
 */

public class VerificatonCodeButton extends AppCompatButton{

    public VerificatonCodeButton(Context context) {
        super(context);
    }

    public VerificatonCodeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (isEnabled()) {
            if (action == MotionEvent.ACTION_DOWN) {
                setTextColor(Color.WHITE);
                setBackgroundResource(R.drawable.hqyzm_xz);
            }

            if (action == MotionEvent.ACTION_UP) {
                setTextColor(Color.parseColor("#333333"));
                setBackgroundResource(R.drawable.hqyzm_wxz);
            }
        }

        return super.onTouchEvent(event);
    }
}
