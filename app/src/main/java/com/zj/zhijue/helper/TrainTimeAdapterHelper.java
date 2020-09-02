package com.zj.zhijue.helper;

import android.content.Context;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.res.Resources;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.common.baselibrary.log.MLog;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.LogUtils;
import com.facebook.stetho.common.LogUtil;
import com.litesuits.android.log.Log;
import com.zj.zhijue.BuildConfig;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.adapter.TrainTimeModeAdapter;
import com.zj.zhijue.bean.TrainWeekViewBean;
import com.zj.zhijue.greendao.greendaobean.UserInfoTrainTimeDBBean;
import com.zj.zhijue.listener.ItemOfViewPagerOnClickListener;
import com.zj.zhijue.model.TrainModel;
import com.android.common.baselibrary.util.DateUtil;
import com.zj.zhijue.util.CommonUtils;
import com.zj.zhijue.util.MyTimeUtils;
import com.zj.zhijue.view.NewWeekView;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.entities.DPInfo;
import cn.aigestudio.datepicker.listener.onDateChangeListener;
import cn.aigestudio.datepicker.views.DatePicker;


public class TrainTimeAdapterHelper {
    private ItemOfViewPagerOnClickListener mItemOfViewPagerOnClickListener;
    private List<String> trainTimeStrList = new ArrayList<>();
    private Map<String, UserInfoTrainTimeDBBean> trainTimeMap = new HashMap<>();
    private HashMap<String, Float> trainDateAndTime = new HashMap<>();
    public static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private DatePicker picker;
    private Date curDate = null;
    private TextView moreTextView;
    private TextView lastMonthDayTextView;
    private int dateTabIndex = -1;
    private int curYear;
    private int curMonth;
    private List<String> curSelectDay;
    private List<String> curSelectWeek;

    public void addViewListener(View view, int position) {
        switch (position) {
            case 0://月数据
                addFirstViewListener(view);
                break;

            case 1://周数据
                addSecondViewListener(view);
                break;

            case 2://今天数据
                addThirdViewListener(view);
                break;

            case 3://昨天数据
                addFourthViewListener(view);
                break;

            default:
                break;

        }

    }

    public void addFirstViewListener(View view) {
    /*    Button buttonOne = (Button) view.findViewById(R.id.buttonone);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemOfViewPagerOnClickListener.onClickIndex(0, R.id.buttonone);
            }
        });*/
    }

    public void addSecondViewListener(View view) {
       /* Button buttonOne = (Button) view.findViewById(R.id.buttontwo);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemOfViewPagerOnClickListener.onClickIndex(1, R.id.buttontwo);
            }
        });*/
    }

    public void addThirdViewListener(View view) {
       /* Button buttonOne = (Button) view.findViewById(R.id.buttonthree);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemOfViewPagerOnClickListener.onClickIndex(2, R.id.buttonthree);
            }
        });*/

    }

    public void addFourthViewListener(View view) {
      /*  Button buttonOne = (Button) view.findViewById(R.id.buttonfour);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemOfViewPagerOnClickListener.onClickIndex(3, R.id.buttonfour);
            }
        });*/

    }

    public void addItemOfViewPagerListener(ItemOfViewPagerOnClickListener itemOfViewPagerOnClickListener) {
        mItemOfViewPagerOnClickListener = itemOfViewPagerOnClickListener;
    }

    public void initDatePicker(View view) {
        //initTrainTimeList();
    /*    List<String> tmpTL = new ArrayList<>();
        DPCManager.getInstance().setDecorTL(tmpTL);
        List<String> tmpTR = new ArrayList<>();
        DPCManager.getInstance().setDecorTR(tmpTR);*/
        initTrainTimeList();
        try {
            DPCManager.getInstance().addSignInOrTrainTimeCollections(trainTimeStrList);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        picker = view.findViewById(R.id.main_dp);
        String[] todayDate = DateUtil.parseDateToYearMonthDayWeek(new Date(System.currentTimeMillis()));
        picker.setDate(Integer.parseInt(todayDate[0]), Integer.parseInt(todayDate[1]));
        picker.setFestivalDisplay(false);
        picker.setTodayDisplay(true);
        picker.setHolidayDisplay(false);

        picker.setDeferredDisplay(false);
        picker.setMode(DPMode.SINGLE);

        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Log.v("jiege", "date:" + date);

                StringBuilder date1 = new StringBuilder();
                for (String str : date.split("-")) {
                    if (str.length() == 1) {
                        date1.append("0").append(str);
                    } else {
                        date1.append(str);
                    }
                    date1.append(".");
                }
                if (date1.toString().isEmpty()) {
                    return;
                }
                //已经解析的时间
                String date2 = date1.substring(0, date1.length() - 1);

                curSelectWeek = new ArrayList<>();

                curSelectWeek.addAll(MyTimeUtils.getWeekDay(date2, "yyyy.MM.dd"));

                curSelectDay = new ArrayList<>();
                curSelectDay.add(date2);

                switch (dateTabIndex) {
                    case 1://周
                        showTrainingTime(curSelectWeek);
                        break;
                    case 2://日
                        showTrainingTime(curSelectDay);
                        break;
                    default:
                        break;
                }

            }
        });


        //本月训练多少天
        lastMonthDayTextView = view.findViewById(R.id.lastmonthdaystext);
        //本月训练多少小时
        moreTextView = view.findViewById(R.id.themonthtotaltrainshourstv);

        curYear = Integer.parseInt(todayDate[0]);
        curMonth = Integer.parseInt(todayDate[1]);
        setCurrentDateText(curYear, curMonth);
        initDatePickerBottomText(picker, view);

//        AppCompatSpinner trainDateSpinner = view.findViewById(R.id.trainyeardatespinner);
//        List<String> yearAndMonth = YearMonthDay2YearMonth(trainTimeStrList);
//        initSpinner(trainDateSpinner, yearAndMonth, picker);


        tvTabMonth = view.findViewById(R.id.tvTabMonth);
        tvTabWeek = view.findViewById(R.id.tvTabWeek);
        tvTabDay = view.findViewById(R.id.tvTabDay);
        tvDate = view.findViewById(R.id.tvDate);
        tvDate.setText(todayDate[0] + "年" + todayDate[1] + "月");
        initEvent();
    }


    private void initEvent() {
        tvTabMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTabIndex(0);
            }
        });
        tvTabWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTabIndex(1);
            }
        });
        tvTabDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTabIndex(2);
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerView(view.getContext());
            }
        });

        picker.setMode(DPMode.SINGLE);

    }

    private void showPickerView(Context context) {
        Calendar selectedDate = Calendar.getInstance();
        if (curDate != null) {
            selectedDate.setTime(curDate);
        }
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                curDate = date;
                String[] todayDate = DateUtil.parseDateToYearMonthDayWeek(date);
                tvDate.setText(todayDate[0] + "年" + todayDate[1] + "月");

                curYear = Integer.parseInt(todayDate[0]);
                curMonth = Integer.parseInt(todayDate[1]);
                setCurrentDateText(curYear, curMonth);
                picker.setDate(curYear, curMonth);


            }
        })
                .setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText("")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setContentTextSize(17)
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.parseColor("#374CFF"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#374CFF"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();

        pvTime.show();

    }

    private TextView tvTabMonth;
    private TextView tvTabWeek;
    private TextView tvTabDay;
    private TextView tvDate;

    /**
     * 设置Tab的显示位置
     *
     * @param position
     */
    private void initTabIndex(int position) {
        if (position == dateTabIndex) {
            return;
        }

        Resources resources = tvTabMonth.getContext().getResources();

        tvTabMonth.setTextColor(resources.getColor(R.color.res_color_blue_374cff));
        tvTabWeek.setTextColor(resources.getColor(R.color.res_color_blue_374cff));
        tvTabDay.setTextColor(resources.getColor(R.color.res_color_blue_374cff));
        tvTabMonth.setBackground(null);
        tvTabWeek.setBackground(null);
        tvTabDay.setBackground(null);
        switch (position) {
            case 0:
                picker.setDate(curYear, curMonth);

                //月
                tvTabMonth.setTextColor(resources.getColor(R.color.white));
                tvTabMonth.setBackgroundResource(R.drawable.res_round_blue_10_0_10_0);
                break;
            case 1:
                if (curSelectWeek != null) {
                    showTrainingTime(curSelectWeek);
                }

                //周
                tvTabWeek.setTextColor(resources.getColor(R.color.white));
                tvTabWeek.setBackgroundResource(R.color.res_color_blue_374cff);
                break;
            case 2:
                if (curSelectDay != null) {
                    showTrainingTime(curSelectDay);
                }

                //日
                tvTabDay.setTextColor(resources.getColor(R.color.white));
                tvTabDay.setBackgroundResource(R.drawable.res_round_blue_0_10_0_10);
                break;
            default:
                break;
        }
        dateTabIndex = position;

    }


    private void initSpinner(AppCompatSpinner spinner, final List<String> yearAndMonthList, final DatePicker datePicker) {
        Context context = MyApplication.getInstance();
        //final List<String> trainModeArray = Arrays.asList(context.getResources().getStringArray(R.array.eyestatus));
        TrainTimeModeAdapter sexAdapter = new TrainTimeModeAdapter(context, yearAndMonthList);
        spinner.setAdapter(sexAdapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String yearAndMonth = yearAndMonthList.get(position);
                String[] dateArray = yearAndMonth.split("-");
                datePicker.setDate(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * 将格式为 YYYY-MM-dd 转换成 YYYY年MM月
     *
     * @param dateList
     * @return
     */
    private List<String> YearMonthDay2YearMonth(List<String> dateList) {
        LinkedHashSet<String> hashSet = new LinkedHashSet<>();
        hashSet.add(DateUtil.lineformater.format(new Date(System.currentTimeMillis())));

        for (String dateStr : dateList) {
            try {
                hashSet.add(DateUtil.lineformater.format(new Date(DateUtil.localformatterDay.parse(dateStr).getTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>(hashSet);
    }

    private void setCurrentDateText(int year, int month) {
        List<String> currentMonthDate = getCurrentMonthDate(year, month);


        showTrainingTime(currentMonthDate);
    }


    /**
     * 计算当月的训练时间，和天数
     * currentMonthDate 需要计算的时间如：2020.7.20
     */
    private void showTrainingTime(List<String> currentMonthDate) {
        float[] daysAndTimes = calcCurrentMonthTrainTimeAndTimes(currentMonthDate);

        lastMonthDayTextView.setText(String.valueOf((int) daysAndTimes[0]));

        CommonUtils.setMoreTextView((int) (daysAndTimes[1]), moreTextView);

    }


    private void initDatePickerBottomText(DatePicker datePicker, View view) {
        datePicker.setmOnDateChangeListener(new onDateChangeListener() {
            @Override
            public void onDateChange(int year, int month) {
                List<String> currentMonthDate = getCurrentMonthDate(year, month);

                showTrainingTime(currentMonthDate);
            }
        });
    }

    /***
     * 计算当月的训练时间和天数
     * @return
     */
    private float[] calcCurrentMonthTrainTimeAndTimes(List<String> datesList) {
        float[] dayAndTimes = new float[2];
        for (String dateStr : datesList) {
            UserInfoTrainTimeDBBean bean = trainTimeMap.get(dateStr);
            if (null != bean) {
                dayAndTimes[0]++;
                float trainDateLong = bean.getTrainTime();
                dayAndTimes[1] += trainDateLong;
            }
        }
        return dayAndTimes;
    }

    private List<String> getCurrentMonthDate(int yearInt, int monthInt) {
        List<String> currentMonthDate = new ArrayList<>();

        DPInfo[][] dpInfos = DPCManager.getInstance().obtainDPInfo(yearInt, monthInt);
        int rows = dpInfos.length;
        for (int i = 0; i < rows; i++) {
            int columnWeekDay = dpInfos[i].length;
            for (int j = 0; j < columnWeekDay; j++) {
                if (!TextUtils.isEmpty(dpInfos[i][j].strG)) {
                    String monthStr = String.valueOf(monthInt);
                    if (monthInt < 10) {
                        monthStr = "0" + monthInt;
                    }

                    String dayStr = dpInfos[i][j].strG;
                    if (dayStr.length() == 1) {
                        dayStr = "0" + dayStr;
                    }
                    String dateStr = yearInt + "." + monthStr + "." + dayStr;
                    //MLog.d(" dateStr = " + dateStr);
                    currentMonthDate.add(dateStr);
                }
            }
        }
        return currentMonthDate;
    }

    /**
     * 获取最近7天的日期
     */
    private List<String> getLastSevenDaysData() {
        List<String> lastWeekDate = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            long currentTime = System.currentTimeMillis();
            String date = DateUtil.dotformaterAll.format(new Date(currentTime - 24L * 60 * 60 * 1000 * i));
            lastWeekDate.add(date);
        }
        return lastWeekDate;
    }

    /**
     * 初始化单柱柱状图
     */
    public void initSingleView(View view) {
        final LinearLayout llSingle = (LinearLayout) LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.layout_pro_expense, null);
        final AppCompatTextView houtTextView = view.findViewById(R.id.trainweekhouttv);
        final AppCompatTextView minTextView = view.findViewById(R.id.trainweekmintv);

        //Button flushButton = view.findViewById(R.id.flushdatabutton);
        final NewWeekView mMySingleChartView = view.findViewById(R.id.my_single_chart_view);
        ImageView leftImageView = view.findViewById(R.id.leftimage);
        ImageView rightImageView = view.findViewById(R.id.rightimage);
        final List<TrainWeekViewBean> singlelist = new ArrayList<>();
        final RelativeLayout rlSingle = view.findViewById(R.id.rl_single);
        //RelativeLayout.LayoutParams weekViewLayoutParams = (RelativeLayout.LayoutParams) mMySingleChartView.getLayoutParams();
        //final int weekViewMarginTop = weekViewLayoutParams.topMargin;

     /*   List<NewWeekView.WeekDay> list = NewWeekView.getWeekDay(new Date(System.currentTimeMillis()));
        for (NewWeekView.WeekDay weekDay : list) {
            dateStrList.add(weekDay.day);
        }*/
        final List<String> dateStrList = new ArrayList<>(getLastSevenDaysData());

        getTrainTimeByBottomDate(singlelist, dateStrList);
        float[] trainTimeArray = calcTrainTime(singlelist);
        houtTextView.setText(String.valueOf((int) trainTimeArray[0]));
        minTextView.setText(String.valueOf((int) trainTimeArray[1]));
        mMySingleChartView.setList(singlelist, dateStrList);
        rlSingle.removeView(llSingle);
        //原理同双柱
        mMySingleChartView.setListener(new NewWeekView.getNumberListener() {
            @Override
            public void getNumber(int number, int x, int y) {
                //Log.d("SingleView", "x = " + x + " y = " + y);
                rlSingle.removeView(llSingle);

                TextView tvMoney = (TextView) llSingle.findViewById(R.id.tv_shouru_pro);
                tvMoney.setText((number + 1) + "h" + (singlelist.get(number)) + "m");
                llSingle.measure(0, 0);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

                int llsingleWdith = llSingle.getMeasuredWidth();
                int llsingleHeight = llSingle.getMeasuredHeight();
                //Log.d("SingleView", "llSingle.getMeasuredWidth() = " + llsingleWdith);
                params.leftMargin = x - llsingleWdith / 2;
                if (params.leftMargin < 0) {
                    params.leftMargin = 0;
                } else if (params.leftMargin > rlSingle.getWidth() - llsingleWdith) {
                    params.leftMargin = rlSingle.getWidth() - llSingle.getMeasuredWidth();
                }
                int textPaddtingBottom = (int) MyApplication.getInstance().getResources().getDimension(R.dimen.train_week_tip_text_paddingbottom_size);
                int marginBottom = (int) (y + textPaddtingBottom);
                //MLog.d("marginBottom  = " + marginBottom);
                //params.bottomMargin = marginBottom;
                llSingle.setPadding(0, 0, 0, marginBottom);
                llSingle.setLayoutParams(params);
                rlSingle.addView(llSingle);
            }
        });

        leftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSingleViewDate(mMySingleChartView, singlelist, dateStrList, rlSingle, llSingle, false);
                float[] trainTimeArray = calcTrainTime(singlelist);
                houtTextView.setText(String.valueOf((int) trainTimeArray[0]));
                minTextView.setText(String.valueOf((int) trainTimeArray[1]));
            }
        });

        rightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSingleViewDate(mMySingleChartView, singlelist, dateStrList, rlSingle, llSingle, true);
                float[] trainTimeArray = calcTrainTime(singlelist);
                houtTextView.setText(String.valueOf((int) trainTimeArray[0]));
                minTextView.setText(String.valueOf((int) trainTimeArray[1]));
            }
        });

 /*       flushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMySingleChartView.clearSelectedRoles();
                singlelist.clear();
                dateStrList.clear();
                Random random = new Random();
                while (singlelist.size() < 7) {
                    int randomInt = random.nextInt(360);
                    singlelist.add((float) randomInt);
                }

                List<SingleView.WeekDay> list = SingleView.getWeekDay();
                for (SingleView.WeekDay weekDay : list) {
                    dateStrList.add(weekDay.day);
                }

                mMySingleChartView.setList(singlelist, dateStrList);
                rlSingle.removeView(llSingle);
                mMySingleChartView.invalidate();

            }
        });*/


    }

    private void updateSingleViewDate(NewWeekView mMySingleChartView, List<TrainWeekViewBean> singlelist, List<String> dateStrList, RelativeLayout rlSingle, LinearLayout llSingle, boolean isIncrease) {
        if (isIncrease && limitIncrease(dateStrList)) {
            return;
        }

        if (!isIncrease && limitReduce(dateStrList)) {
            return;
        }

        mMySingleChartView.clearSelectedRoles();
        List<NewWeekView.WeekDay> list = null;
        if (null != dateStrList && dateStrList.size() > 0) {
            String lastWeekFirstDayDateStr = dateStrList.get(0);
            dateStrList.clear();
            try {
                Date date = DateUtil.dotformaterAll.parse(lastWeekFirstDayDateStr);
                if (isIncrease) {
                    list = NewWeekView.getWeekDay(new Date(date.getTime() + 7L * 24 * 60 * 60 * 1000));
                } else {
                    list = NewWeekView.getWeekDay(new Date(date.getTime() - 7L * 24 * 60 * 60 * 1000));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            list = NewWeekView.getWeekDay(new Date(System.currentTimeMillis()));
        }


        for (NewWeekView.WeekDay weekDay : list) {
            dateStrList.add(weekDay.day);
        }

        getTrainTimeByBottomDate(singlelist, dateStrList);

        mMySingleChartView.setList(singlelist, dateStrList);
        rlSingle.removeView(llSingle);
        mMySingleChartView.invalidate();
    }

    private boolean limitIncrease(List<String> dateStrList) {
        if (null != dateStrList && dateStrList.size() > 0) {
            String firstDate = dateStrList.get(0);
            try {
                Date date = DateUtil.dotformaterAll.parse(firstDate);
                if (date.getTime() + 7L * 24 * 60 * 60 * 1000 > System.currentTimeMillis()) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            return true;
        }
        return false;
    }

    private boolean limitReduce(List<String> dateStrList) {
        if (null != dateStrList && dateStrList.size() > 0) {
            String firstDate = dateStrList.get(0);
            try {
                Date date = DateUtil.dotformaterAll.parse(firstDate);
                if (date.getTime() < DateUtil.formatter.parse(DateUtil.startTime).getTime()) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            return true;
        }

        return false;
    }

    public void initTodayCircleView(View view) {
        initTrainTimeList();
     /*   TextView moreText = view.findViewById(R.id.moretextview);
        moreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showShortToast("今天查看更多");
            }
        });*/
        AppCompatTextView todayDateTextView = view.findViewById(R.id.todaydatetv);
        AppCompatTextView wearTimeTextView = view.findViewById(R.id.tdweartimestv);//佩戴次数
        AppCompatTextView wearTimeHourLongTextView = view.findViewById(R.id.tdweartimehourlongtv);
        AppCompatTextView wearTimeMinuteLongTextView = view.findViewById(R.id.tdweartimeminutelongtv);
        String todayDate = DateUtil.dotformaterAll.format(new Date(System.currentTimeMillis()));

        String todayDateLineFormate = DateUtil.localformatterDay.format(new Date(System.currentTimeMillis()));
        todayDateTextView.setText(todayDateLineFormate);

        UserInfoTrainTimeDBBean userInfoTrainTimeDBBean = trainTimeMap.get(todayDate);
        int wearTimes = 0;
        int wearTimeHourLong = 0;
        int wearTimeMintueLong = 0;
        if (null != userInfoTrainTimeDBBean) {
            wearTimes = userInfoTrainTimeDBBean.getTrainCount();
            wearTimeHourLong = (int) (userInfoTrainTimeDBBean.getTrainTime() / (60 * 60));
            wearTimeMintueLong = (int) (userInfoTrainTimeDBBean.getTrainTime() / 60f) % 60;
        }

        wearTimeTextView.setText(String.valueOf(wearTimes));
        wearTimeHourLongTextView.setText(String.valueOf(wearTimeHourLong));
        wearTimeMinuteLongTextView.setText(String.valueOf(wearTimeMintueLong));
    }

    public void initYesterDayCircleView(View view) {
       /* TextView moreText = view.findViewById(R.id.moretextview);
        moreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showShortToast("昨天查看更多");
            }
        });*/
        AppCompatTextView wearTimeTextView = view.findViewById(R.id.ydweartimestv);//佩戴次数
        AppCompatTextView wearTimeHourLongTextView = view.findViewById(R.id.ydweartimehourlongtv);
        AppCompatTextView wearTimeMinuteLongTextView = view.findViewById(R.id.ydweartimeminutelongtv);
        String todayDate = DateUtil.dotformaterAll.format(new Date(System.currentTimeMillis()));
        long yesterDayLong = 0;

        try {
            yesterDayLong = DateUtil.dotformaterAll.parse(todayDate).getTime() - 24L * 60 * 60 * 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String yesterdayDate = DateUtil.dotformaterAll.format(new Date(yesterDayLong));
        MLog.d("yesterdayDate = " + yesterdayDate);

        UserInfoTrainTimeDBBean userInfoTrainTimeDBBean = trainTimeMap.get(yesterdayDate);
        MLog.d("userInfoTrainTimeDBBean = " + userInfoTrainTimeDBBean);
        int wearTimes = 0;
        int wearTimeHourLong = 0;
        int wearTimeMintueLong = 0;
        if (null != userInfoTrainTimeDBBean) {
            wearTimes = userInfoTrainTimeDBBean.getTrainCount();
            wearTimeHourLong = (int) (userInfoTrainTimeDBBean.getTrainTime() / (60 * 60));
            wearTimeMintueLong = (int) (userInfoTrainTimeDBBean.getTrainTime() / 60f) % 60;
        }

        wearTimeTextView.setText(String.valueOf(wearTimes));
        wearTimeHourLongTextView.setText(String.valueOf(wearTimeHourLong));
        wearTimeMinuteLongTextView.setText(String.valueOf(wearTimeMintueLong));
    }

    public void initTrainTimeList() {
        MLog.i("initTrainTimeList");
        /**
         * 从网络或者从数据库中读取所有的训练日期
         */
        trainTimeStrList.clear();
        trainTimeMap.clear();
        List<UserInfoTrainTimeDBBean> mTrainTimeList = null;

//            mTrainTimeList = createTestData();//造假数据

        mTrainTimeList = TrainModel.getInstance().getCurrentUserTrainTimeData();

        //设置设置训练日背景
        for (int i = 0; i < mTrainTimeList.size(); i++) {
            UserInfoTrainTimeDBBean trainBean = mTrainTimeList.get(i);
            String dateStr = DateUtil.localformatterDay.format(new Date(trainBean.getTrainDate()));
            trainTimeStrList.add(dateStr);
            trainTimeMap.put(DateUtil.dotformaterAll.format(new Date(trainBean.getTrainDate())), trainBean);
        }
        //设置当前时间的背景
//        String dateStr = DateUtil.localformatterDay.format(new Date(System.currentTimeMillis()));
//        trainTimeStrList.add(dateStr);
    }

    public List<UserInfoTrainTimeDBBean> createTestData() {

        List<UserInfoTrainTimeDBBean> trainTimeDBBeanList = new ArrayList<>();

        List<NewWeekView.WeekDay> list = NewWeekView.getWeekDay(new Date(System.currentTimeMillis()));


        for (int i = 0; i < 7; i++) {
            UserInfoTrainTimeDBBean userInfoTrainTimeDBBean = new UserInfoTrainTimeDBBean();
            userInfoTrainTimeDBBean.setLocalid(UUID.randomUUID().toString());
            try {
                userInfoTrainTimeDBBean.setTrainDate(DateUtil.dotformaterAll.parse(list.get(i).day).getTime());
                userInfoTrainTimeDBBean.setTrainDateStr(list.get(i).day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            userInfoTrainTimeDBBean.setTrainTime(60 * (i + 1));
            userInfoTrainTimeDBBean.setTrainCount(i + 1);

            trainTimeDBBeanList.add(userInfoTrainTimeDBBean);
        }


        return trainTimeDBBeanList;
    }

    /**
     * trainTimeList 的数据填充需要根据底部显示的日期来获取
     *
     * @param trainTimeList
     * @param trainTimeDateList
     */
    private void getTrainTimeByBottomDate(List<TrainWeekViewBean> trainTimeList, List<String> trainTimeDateList) {
        trainTimeList.clear();
        //MLog.d("trainTimeMap.size = " + trainTimeMap.size());
        for (String dateStr : trainTimeDateList) {
            UserInfoTrainTimeDBBean userInfoTrainTimeDBBean = trainTimeMap.get(dateStr);
            TrainWeekViewBean trainWeekViewBean = new TrainWeekViewBean();
            if (null == userInfoTrainTimeDBBean) {
                trainWeekViewBean.setTrainTime(0);
            } else {
                float trainTimeFlot = userInfoTrainTimeDBBean.getTrainTime();
                if (BuildConfig.DEBUG) {
                    if (trainTimeFlot < 60) {
                        trainTimeFlot = 60;
                    }
                }
                //MLog.d(" trainTimeFlot = " + trainTimeFlot);
                trainWeekViewBean.setTrainTime(trainTimeFlot);
            }
            trainWeekViewBean.setDateStr(dateStr);
            trainTimeList.add(trainWeekViewBean);
        }

    }

    private float[] calcTrainTime(List<TrainWeekViewBean> trainWeekViewBeanList) {
        float[] hourAndMinutes = new float[2];
        for (TrainWeekViewBean bean : trainWeekViewBeanList) {
            hourAndMinutes[1] += bean.getTrainTime();
        }
        hourAndMinutes[0] = (int) (hourAndMinutes[1] / (60 * 60));
        hourAndMinutes[1] = (hourAndMinutes[1] / 60) % 60;
        return hourAndMinutes;
    }

}
