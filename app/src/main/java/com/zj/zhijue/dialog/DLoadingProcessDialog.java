package com.zj.zhijue.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.TextView;

import com.zj.zhijue.R;


public class DLoadingProcessDialog extends AlertDialog {
    private TextView processImg;
    private boolean cancleByBackKey = false;
    //帧动画
    private AnimationDrawable aimation;
    public DLoadingProcessDialog(Context context){
        super(context, R.style.DLoadingProgressDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_process_drawable_dialog_layout);

        processImg = (TextView) findViewById(R.id.loading_process_dialog);
        //点击imageView外侧区域不会消失
        setCanceledOnTouchOutside(false);
        //加载动画资源
       // aimation = (AnimationDrawable)processImg.getDrawable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //aimation.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
       /// aimation.stop();
    }

    public void setCancleByBackKey(boolean cancleByBackKey) {
        this.cancleByBackKey = cancleByBackKey;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(!cancleByBackKey){
            if(keyCode == KeyEvent.KEYCODE_BACK){
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 显示加载的文字内容
     */
    public void setLoadingText(String loadingText) {
        processImg.setText(loadingText);
    }
}
