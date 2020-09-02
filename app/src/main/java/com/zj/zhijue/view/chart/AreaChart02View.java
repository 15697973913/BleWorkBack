/**
 * Copyright 2014  XCL-Charts
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @Project XCL-Charts
 * @Description Android图表基类库
 * @author XiongChuanLiang<br />(xcl_168@aliyun.com)
 * @Copyright Copyright (c) 2014 XCL-Charts (www.xclcharts.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.5
 */
package com.zj.zhijue.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.zj.zhijue.R;
import com.zj.zhijue.util.CommonUtils;

import org.xclcharts.chart.AreaChart;
import org.xclcharts.chart.AreaData;
import org.xclcharts.chart.CustomLineData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XEnum;

import java.util.LinkedList;
import java.util.List;

/**
 * @author XiongChuanLiang<br />(xcl_168@aliyun.com)
 * @ClassName AreaChart02View
 * @Description 平滑面积图例子
 */

public class AreaChart02View extends DemoView {

    private String TAG = "AreaChart02View";

    private AreaChart chart = new AreaChart();
    //标签集合
    private LinkedList<String> mLabels = new LinkedList<String>();
    //数据集合
    private LinkedList<AreaData> mDataset = new LinkedList<AreaData>();

    private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();


    public AreaChart02View(Context context) {
        super(context);
        initView();
    }

    public AreaChart02View(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AreaChart02View(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        chartLabels();
        chartDataSet(new LinkedList<Double>(),new LinkedList<Double>());
        chartRender();

        //綁定手势滑动事件
        this.bindTouch(this, chart);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w, h);
    }

    private void chartRender() {
        try {
            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            int[] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);

            //定义数据轴标签显示格式
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack() {
                @Override
                public String textFormatter(String value) {
                    return CommonUtils.getFloat(value) + "";
                }
            });

            //设定交叉点标签显示格式
            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    return CommonUtils.getInt(value+"") + "";
                }
            });
            //轴数据源
            //标签轴
            chart.setCategories(mLabels);
            chart.setCategoryAxisLocation(XEnum.AxisLocation.BOTTOM);

            //数据轴
            chart.setDataSource(mDataset);
            //仅横向平移
//            chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);
            chart.disablePanMode();


            chart.setCrurveLineStyle(XEnum.CrurveLineStyle.BEELINE);

            //数据轴最大值
            chart.getDataAxis().setAxisMin(4.0);
            chart.getDataAxis().setAxisMax(5.3);
            //数据轴刻度间隔
            chart.getDataAxis().setAxisSteps(0.1);


            //网格
            chart.getPlotGrid().hideHorizontalLines();
            chart.getPlotGrid().hideVerticalLines();
            //把顶轴和右轴隐藏
            //chart.hideTopAxis();
            //chart.hideRightAxis();

            //把轴线和刻度线给隐藏起来
            chart.getDataAxis().hideAxisLine();
            chart.getDataAxis().hideTickMarks();
            chart.getCategoryAxis().hideAxisLine();
            chart.getCategoryAxis().hideTickMarks();

            //设置网格线颜色
            chart.getPlotGrid().getVerticalLinePaint().setColor(Color.parseColor("#50a1a1a1"));
            chart.getPlotGrid().getHorizontalLinePaint().setColor(Color.parseColor("#50a1a1a1"));
            chart.getPlotGrid().setHorizontalLineStyle(XEnum.LineStyle.DASH);
            chart.getPlotGrid().setVerticalLineStyle(XEnum.LineStyle.DASH);

            chart.getPlotGrid().setEvenRowBackgroundColor(Color.parseColor("#000000"));


            //标题
//				chart.setTitle("平滑区域图");
//				chart.addSubtitle("(XCL-Charts Demo)");
            chart.setTitle("");
            chart.addSubtitle("");
            //轴标题
            chart.getAxisTitle().setLowerTitle("");

            //透明度
            chart.setAreaAlpha(240);
            //显示图例
            chart.getPlotLegend().show();

            //激活点击监听
            chart.ActiveListenItemClick();
            //为了让触发更灵敏，可以扩大5px的点击监听范围
            chart.extPointClickRange(5);

            //扩大显示宽度
            //chart.getPlotArea().extWidth(100f);

            //chart.disablePanMode(); //test

//            CustomLineData line1 = new CustomLineData("标识线", 60d, Color.RED, 7);
//            line1.setCustomLineCap(XEnum.DotStyle.CROSS);
//            line1.setLabelHorizontalPostion(Align.CENTER);
//            line1.setLabelOffset(15);
//            line1.getLineLabelPaint().setColor(Color.RED);
//            mCustomLineDataset.add(line1);
//            chart.setCustomLines(mCustomLineDataset);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }

    public void resetChartData() {
        mDataset.clear();
        invalidate();
    }

    public void chartDataSet(List<Double> dataSeries1,  List<Double> dataSeries2 ) {
        //将标签与对应的数据集分别绑定
        //标签对应的数据集
        if(dataSeries1 == null){
            dataSeries1= new LinkedList<>();
        }
        if(dataSeries2 == null){
            dataSeries2= new LinkedList<>();
        }

        AreaData line1 = new AreaData("", dataSeries1,
//                Color.rgb(182, 23, 123),
                Color.parseColor("#48A0DC"),
                Color.rgb(255, 191, 235)
        );

        AreaData line2 = new AreaData("", dataSeries2,
//                Color.rgb(182, 23, 123),
                Color.parseColor("#3BC06B"),
                Color.rgb(255, 191, 235)
        );

        //设置线上每点对应标签的颜色
        line1.getDotLabelPaint().setColor(Color.parseColor("#ffffff"));
        //设置点标签
//        line1.setLabelVisible(true);
//        line1.getDotLabelPaint().setTextAlign(Paint.Align.CENTER);
//        line1.getLabelOptions().setLabelBoxStyle(XEnum.LabelBoxStyle.CIRCLE);
//        line1.getLabelOptions().getBox().getBackgroundPaint().setColor(Color.GREEN);
//        line1.getLabelOptions().setOffsetY(30.f);

        line1.setApplayGradient(true);
        line1.setGradientDirection(XEnum.Direction.VERTICAL);
//        line1.setAreaBeginColor(Color.parseColor("#CCF5FF"));
//        line1.setAreaEndColor(Color.parseColor("#FFFFFF"));
        line1.setAreaBeginColor(getContext().getResources().getColor(R.color.transparent));
        line1.setAreaEndColor(getContext().getResources().getColor(R.color.transparent));

        line2.setApplayGradient(true);
        line2.setGradientDirection(XEnum.Direction.VERTICAL);
        line1.setAreaBeginColor(getContext().getResources().getColor(R.color.transparent));
        line1.setAreaEndColor(getContext().getResources().getColor(R.color.transparent));


        mDataset.add(line1);
        mDataset.add(line2);
    }

    private void chartLabels() {
        mLabels.add("");
        mLabels.add("1h");
        mLabels.add("2h");
        mLabels.add("3h");
        mLabels.add("4h");
    }

    @Override
    public void render(Canvas canvas) {
        try {
            chart.render(canvas);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            triggerClick(event.getX(), event.getY());
        }
        return true;
    }


    //触发监听
    private void triggerClick(float x, float y) {
        PointPosition record = chart.getPositionRecord(x, y);
        if (null == record) return;

        AreaData lData = mDataset.get(record.getDataID());
        Double lValue = lData.getLinePoint().get(record.getDataChildID());

//        Toast.makeText(this.getContext(),
//                record.getPointInfo() +
//                        " Key:" + lData.getLineKey() +
//                        " Label:" + lData.getLabel() +
//                        " Current Value:" + Double.toString(lValue),
//                Toast.LENGTH_SHORT).show();
    }
}

