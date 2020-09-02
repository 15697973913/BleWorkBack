package com.zj.zhijue.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;

import com.zj.zhijue.R;
import com.zj.zhijue.view.dialog.annotation.AnimType;


/**
 * 作者：Alex
 * 时间：2016/12/7 13 22
 * 简述：
 */

public abstract class SimpleDialog<D extends SimpleDialog> extends BaseDialog<D> {
    public SimpleDialog(Context context) {
        super(context, R.style.alex_dialog_base_light_style);
    }

    public SimpleDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 显示对话框，无动画
     */
    @Override
    public void show() {
        if (Gravity.CENTER == gravity) {
            show(animType);
        } else {
            super.show();
        }
    }

    /**
     * 显示对话框，强制转换对话框的动画类型
     */
    public void show(@AnimType int animType) {
        Window window = getWindow();
        /*如果根据  AnimType 的类型，强制选择Dialog出现的位置*/
        if (AnimType.CENTER_NORMAL == animType) {
            setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.alex_dialog_anim_alpha);
        }
        super.show();
    }

}
