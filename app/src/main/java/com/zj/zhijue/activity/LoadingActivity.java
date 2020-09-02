package com.zj.zhijue.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseBleActivity;
import com.zj.zhijue.dialog.CustomLoadingDialog;
import com.zj.zhijue.dialog.DefaultLoadingDialog;

import butterknife.BindView;

public class LoadingActivity extends BaseBleActivity {
    @BindView(R.id.defaultstyle)
    Button defaultStyleButton;

    @BindView(R.id.customstyle)
    Button customStyleButton;

    DefaultLoadingDialog defaultLoadingDialog;

    CustomLoadingDialog customLoadingDialog;



    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setNewTheme();
        setContentView(R.layout.loading_main);
        super.onCreate(bundle);
        initListener();
    }


    private void initListener() {
        defaultStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == defaultLoadingDialog) {
                    defaultLoadingDialog = new DefaultLoadingDialog(LoadingActivity.this);
                }
                defaultLoadingDialog.show();
            }
        });

        customStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == customLoadingDialog) {
                    customLoadingDialog = new CustomLoadingDialog(LoadingActivity.this);
                }
                customLoadingDialog.show();
            }
        });
    }
}
