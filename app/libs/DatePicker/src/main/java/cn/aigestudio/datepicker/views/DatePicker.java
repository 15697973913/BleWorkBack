package cn.aigestudio.datepicker.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import cn.aigestudio.datepicker.R;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.bizs.languages.DPLManager;
import cn.aigestudio.datepicker.bizs.themes.DPTManager;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.listener.onDateChangeListener;
import cn.aigestudio.datepicker.utils.MeasureUtil;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * DatePicker
 *
 * @author AigeStudio 2015-06-29
 */
public class DatePicker extends LinearLayout {
    private DPTManager mTManager;// 主题管理器
    private DPLManager mLManager;// 语言管理器

    private MonthView monthView;// 月视图
    private TextView tvYear, tvMonth;// 年份 月份显示
    private TextView tvEnsure;// 确定按钮显示

    private ImageView mYearLeftImageView;
    private TextView mYearTextView;
    private ImageView mYearRightImageView;


    private ImageView mMonthLeftImageView;
    private TextView mMonthTextView;
    private ImageView mMonthRightImageView;


    private OnDateSelectedListener onDateSelectedListener;// 日期多选后监听

    private onDateChangeListener mOnDateChangeListener;

    /**
     * 日期单选监听器
     */
    public interface OnDatePickedListener {
        void onDatePicked(String date);
    }

    /**
     * 日期多选监听器
     */
    public interface OnDateSelectedListener {
        void onDateSelected(List<String> date);
    }

    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTManager = DPTManager.getInstance();
        mLManager = DPLManager.getInstance();

        // 设置排列方向为竖向
        setOrientation(VERTICAL);

        LayoutParams llParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        // 标题栏根布局
        RelativeLayout rlTitle = new RelativeLayout(context);
        rlTitle.setBackgroundColor(mTManager.colorTitleBG());
        int rlTitlePadding = MeasureUtil.dp2px(context, 10);
        rlTitle.setPadding(rlTitlePadding, rlTitlePadding, rlTitlePadding, 0);

        // 周视图根布局
        LinearLayout llWeek = new LinearLayout(context);
        llWeek.setBackgroundColor(mTManager.colorTitleBG());
        llWeek.setOrientation(HORIZONTAL);
        int llWeekPadding = MeasureUtil.dp2px(context, 5);
        llWeek.setPadding(0, llWeekPadding, 0, 0);
        LayoutParams lpWeek = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpWeek.weight = 1;

        // 标题栏子元素布局参数
        RelativeLayout.LayoutParams lpYear =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpYear.addRule(RelativeLayout.CENTER_VERTICAL);
        RelativeLayout.LayoutParams lpMonth =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpMonth.addRule(RelativeLayout.CENTER_IN_PARENT);
        RelativeLayout.LayoutParams lpEnsure =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpEnsure.addRule(RelativeLayout.CENTER_VERTICAL);
        lpEnsure.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        RelativeLayout.LayoutParams newTitleParams =
                new RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        newTitleParams.addRule(RelativeLayout.ALIGN_END);

        // --------------------------------------------------------------------------------标题栏
        // 年份显示
        tvYear = new TextView(context);
        tvYear.setText("2015");
        tvYear.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tvYear.setTextColor(mTManager.colorTitle());

        // 月份显示
        tvMonth = new TextView(context);
        tvMonth.setText("六月");
        tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tvMonth.setTextColor(mTManager.colorTitle());

        // 确定显示
        tvEnsure = new TextView(context);
        tvEnsure.setText(mLManager.titleEnsure());
        tvEnsure.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tvEnsure.setTextColor(mTManager.colorTitle());
        tvEnsure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onDateSelectedListener) {
                    onDateSelectedListener.onDateSelected(monthView.getDateSelected());
                }
            }
        });

        View newTitleView = LayoutInflater.from(context).inflate(R.layout.fragment_datepicker_title, null);
        initYearMonthClickView(newTitleView);
        initYearMonthClickViewListener();
        newTitleView.setVisibility(GONE);
/*        rlTitle.addView(tvYear, lpYear);
        rlTitle.addView(tvMonth, lpMonth);
        rlTitle.addView(tvEnsure, lpEnsure);*/
        addView(newTitleView, newTitleParams);
        //addView(rlTitle, llParams);


        // --------------------------------------------------------------------------------周视图
        for (int i = 0; i < mLManager.titleWeek().length; i++) {
            TextView tvWeek = new TextView(context);
            tvWeek.setText(mLManager.titleWeek()[i]);
            tvWeek.setGravity(Gravity.CENTER);
            tvWeek.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tvWeek.setTextColor(Color.BLACK);
            llWeek.addView(tvWeek, lpWeek);
        }
        addView(llWeek, llParams);

        /**
         * 在周视图下面添加一条横线
         */

        LayoutParams lineParams = new LayoutParams(LayoutParams.MATCH_PARENT, MeasureUtil.dp2px(context, 1));
        lineParams.topMargin = MeasureUtil.dp2px(context, 2);
        LinearLayout lineLayout = new LinearLayout(context);
        lineLayout.setBackgroundColor(context.getResources().getColor(R.color.bleglasses_form_report_week_dy_height__color));
        addView(lineLayout, lineParams);

        // ------------------------------------------------------------------------------------月视图
        monthView = new MonthView(context);
        monthView.setOnDateChangeListener(new MonthView.OnDateChangeListener() {
            @Override
            public void onMonthChange(int month) {
                mMonthTextView.setText(mLManager.titleMonth()[month - 1]);
                Log.v("jiege","month:"+month);
            }

            @Override
            public void onYearChange(int year) {
                String tmp = String.valueOf(year);
                if (tmp.startsWith("-")) {
                    tmp = tmp.replace("-", mLManager.titleBC());
                }
                Log.v("jiege","year:"+year);
                mYearTextView.setText(tmp + "年");
            }
        });
        monthView.setOnDatePickedListener(new OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Log.v("jiege","date:"+monthView);
            }
        });
        addView(monthView, llParams);
    }


    private void initYearMonthClickView(View view) {
        mYearLeftImageView = view.findViewById(R.id.yearleft);
        mYearTextView = view.findViewById(R.id.yeartext);
        mYearRightImageView = view.findViewById(R.id.yearright);

        mMonthLeftImageView = view.findViewById(R.id.monthleft);
        mMonthTextView = view.findViewById(R.id.monthtext);
        mMonthRightImageView = view.findViewById(R.id.monthright);
    }

    /**
     * 设置初始化年月日期
     *
     * @param year  ...
     * @param month ...
     */
    public void setDate(int year, int month) {
        if (month < 1) {
            month = 1;
        }
        if (month > 12) {
            month = 12;
        }
        monthView.setDate(year, month);
        getCurrentYearAndMonth();
    }

    public void setDPDecor(DPDecor decor) {
        monthView.setDPDecor(decor);
    }

    /**
     * 设置日期选择模式
     *
     * @param mode ...
     */
    public void setMode(DPMode mode) {
        if (mode != DPMode.MULTIPLE) {
            tvEnsure.setVisibility(GONE);
        }
        monthView.setDPMode(mode);
    }

    public void setFestivalDisplay(boolean isFestivalDisplay) {
        monthView.setFestivalDisplay(isFestivalDisplay);
    }

    public void setTodayDisplay(boolean isTodayDisplay) {
        monthView.setTodayDisplay(isTodayDisplay);
    }

    public void setHolidayDisplay(boolean isHolidayDisplay) {
        monthView.setHolidayDisplay(isHolidayDisplay);
    }

    public void setDeferredDisplay(boolean isDeferredDisplay) {
        monthView.setDeferredDisplay(isDeferredDisplay);
    }

    /**
     * 设置单选监听器
     *
     * @param onDatePickedListener ...
     */
    public void setOnDatePickedListener(OnDatePickedListener onDatePickedListener) {
        if (monthView.getDPMode() != DPMode.SINGLE) {
            throw new RuntimeException(
                    "Current DPMode does not SINGLE! Please call setMode set DPMode to SINGLE!");
        }
        monthView.setOnDatePickedListener(onDatePickedListener);
    }

    /**
     * 设置多选监听器
     *
     * @param onDateSelectedListener ...
     */
    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        if (monthView.getDPMode() != DPMode.MULTIPLE) {
            throw new RuntimeException(
                    "Current DPMode does not MULTIPLE! Please call setMode set DPMode to MULTIPLE!");
        }
        this.onDateSelectedListener = onDateSelectedListener;
    }

    public void nextMonth() {
        monthView.nextMonth();
    }

    public void nextYear() {
        monthView.nextYear();
    }

    public void priviousMonth() {
        monthView.priviousMonth();
    }

    public void priviousYear() {
        monthView.priviousYear();
    }

    private void initYearMonthClickViewListener() {
        mYearLeftImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                priviousYear();
                getCurrentYearAndMonth();
            }
        });

        mYearRightImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                nextYear();
                getCurrentYearAndMonth();
            }
        });

        mMonthLeftImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                priviousMonth();
                getCurrentYearAndMonth();
            }
        });

        mMonthRightImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMonth();
                getCurrentYearAndMonth();
            }
        });
    }

    private void getCurrentYearAndMonth() {
        int year = monthView.getCenterYear();
        int month = monthView.getCenterMonth();
        //Log.d("DataPicker"," year = " + year + " month = " + month);
        if (null != mOnDateChangeListener) {
            mOnDateChangeListener.onDateChange(year, month);
        }
    }

    public void setMonthViewBackgrounColor(int color) {
        monthView.setBackgroundColor(color);
    }

    public onDateChangeListener getmOnDateChangeListener() {
        return mOnDateChangeListener;
    }

    public void setmOnDateChangeListener(onDateChangeListener mOnDateChangeListener) {
        this.mOnDateChangeListener = mOnDateChangeListener;
    }
}
