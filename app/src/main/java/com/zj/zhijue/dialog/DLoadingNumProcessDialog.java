package com.zj.zhijue.dialog;


import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.TextView;

import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.zj.zhijue.R;


public class DLoadingNumProcessDialog extends AlertDialog {
    private TextView processImg;
    private boolean cancleByBackKey = false;
    private NumberProgressBar hozProgressBar;
    private Context mContext;
    private String message;

    public DLoadingNumProcessDialog(Context context){
        super(context, R.style.DLoadingProgressDialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_process_drawable_dialog_layout);

        processImg = (TextView) findViewById(R.id.loading_process_dialog);
        //processImg.setTextColor(Color.parseColor("#ff00ff"));
        hozProgressBar = (NumberProgressBar) findViewById(R.id.horprogressbar);
        //点击imageView外侧区域不会消失
        //setCanceledOnTouchOutside(false);
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

    @Override
    public void show() {
        super.show();
        if (!CommonUtils.isEmpty(message)) {
            processImg.setText(message);
        }
        if (null != hozProgressBar) {
            hozProgressBar.setProgress(0);
        }
    }

    public void show(int progress) {
        if (!isShowing()) {
            show();
        }
        setProgrees(progress);
    }

    public void setProgrees(int progress) {
        if (null != hozProgressBar) {
            hozProgressBar.setProgress(progress);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        /*if (null != hozProgressBar) {
            hozProgressBar.hide();
        }*/
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

    public void setMessage(String message) {
        this.message = message;
    }
}
