package com.zj.zhijue.dialog;

import android.content.Context;

import com.zj.zhijue.R;
import com.zj.zhijue.view.dialog.annotation.AnimType;


/**
 * 作者：Alex
 * 时间：2016年09月10日    17:33
 * 简述：
 */

public abstract class CenterScaleDialog<D extends CenterScaleDialog> extends SimpleDialog<D> {
    public CenterScaleDialog(Context context) {
        super(context, R.style.alex_dialog_base_light_style);

    }

    public CenterScaleDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 显示对话框，无动画
     */
    @Override
    public void show() {
        show(AnimType.CENTER_SCALE);
    }

    /**
     * 显示对话框，强制转换对话框的动画类型
     *
     * @param animType
     */
    @Override
    public void show(@AnimType int animType) {
        super.show(animType);
    }
}
