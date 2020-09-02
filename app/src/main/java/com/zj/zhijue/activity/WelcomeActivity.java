package com.zj.zhijue.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseBleActivity;
import com.zj.zhijue.view.MyLineChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/6/10.
 */

public class WelcomeActivity extends BaseBleActivity {

    MyLineChartView chartView;
    List<String> xValues;   //x轴数据集合
    //y轴数据集合
    List<Integer> yValues;

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 0) {
                WelcomeActivity.this.redirectPage();
            }
            super.handleMessage(message);
        }
    };
    private ImageView welcomeIMG;

    protected void onCreate(@Nullable Bundle bundle) {
        setNewTheme();
        setContentView(R.layout.activity_welcome);
        super.onCreate(bundle);
        initView();
    }

    public void initView() {
        chartView = findViewById(R.id.mychartview);

        xValues = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            xValues.add("" + i);
        }

        yValues = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            yValues.add(new Random(i).nextInt(200));
        }

        // xy轴集合自己添加数据
        chartView.setXValues(xValues);
        chartView.setYValues(yValues);
    }

    private void redirectPage() {

        finish();
    }
}
