package com.zj.zhijue.fragment.mine;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zj.zhijue.BuildConfig;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.EyeSightBean;
import com.zj.zhijue.greendao.greendaobean.ReviewDataEyeSightDBBean;
import com.zj.zhijue.greendao.greenddaodb.ReviewDataEyeSightBeanDaoOpe;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.zj.zhijue.view.ReviewDataLineChartWeekView;
import com.zj.zhijue.view.chart.AreaChart02View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 复查数据，线性图表
 */

public class ReviewDataLineChartFragment extends BaseFragment {
    private List<String> trainTimeStrList = new ArrayList<>();
    private HashMap<String, Float> trainDateAndTime = new HashMap<>();

    private List<ReviewDataEyeSightDBBean> reviewDataEyeSightDBBeanList = null;

    private boolean showLastSevenTimes = true;//显示最后其次的复查数据

    @BindView(R.id.function_item_backlayout)
    LinearLayout functionItemBackLayout;
    @BindView(R.id.areaChart2View)
    AreaChart02View areaChart2View;
    @BindView(R.id.function_item_title_tv)
    TextView function_item_title_tv;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_detail_line_chart_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        initListener();
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView(View view) {

        function_item_title_tv.setText("视力复查");
    }

    private void initData() {
        String memberId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(memberId)) {
            BaseActivity.GotoLoginActivity();
            return;
        }

//        if (BuildConfig.DEBUG) {
//            reviewDataEyeSightDBBeanList = createTestData();
//        } else {
            reviewDataEyeSightDBBeanList = ReviewDataEyeSightBeanDaoOpe.queryAllByMemeberId(MyApplication.getInstance(), memberId);
//        }
        //List<UserInfoDBBean> list = UserInfoBeanDaoOpe.queryUserInfoByServerID(getActivity().getApplicationContext(), memberId);
        //UserInfoDBBean userInfoDBBean = list.get(0);

        if (reviewDataEyeSightDBBeanList != null) {
            List<Double> datasLeft = new ArrayList<>();
            List<Double> datasRight = new ArrayList<>();
            for (ReviewDataEyeSightDBBean dbBean : reviewDataEyeSightDBBeanList) {
                datasLeft.add(dbBean.getLeftEyeSight());
                datasRight.add(dbBean.getRightEyeSight());
                if (datasLeft.size() >= 5) {
                    break;
                }
            }

            areaChart2View.chartDataSet(datasLeft, datasRight);
            areaChart2View.invalidate();

        }

    }

    private List<ReviewDataEyeSightDBBean> createTestData() {
        List<ReviewDataEyeSightDBBean> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ReviewDataEyeSightDBBean reviewDataEyeSightDBBean = new ReviewDataEyeSightDBBean();
            reviewDataEyeSightDBBean.setReviewEyeSightTimes(i + 1);
            reviewDataEyeSightDBBean.setTrainTimeLong(80 * (i + 1));
            reviewDataEyeSightDBBean.setLeftEyeSight(4.1f + 0.1f * i);
            reviewDataEyeSightDBBean.setRightEyeSight(4.2f + 0.1f * i);
            reviewDataEyeSightDBBean.setDoubleEyeSight(4.3f + 0.1f * i);
            list.add(reviewDataEyeSightDBBean);
        }
        return list;
    }

    @Override
    protected void initListener() {
        functionItemBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private void updateSingleViewDate(ReviewDataLineChartWeekView mMySingleChartView, List<EyeSightBean> singlelist, List<String> timesStrList, List<String> dateStrList, RelativeLayout rlSingle, LinearLayout llSingle, boolean isIncrease) {
        mMySingleChartView.clearSelectedRoles();

        getTrainTimeByBottomDate(singlelist, timesStrList, dateStrList, isIncrease);
        mMySingleChartView.setList(singlelist, timesStrList, dateStrList);
        rlSingle.removeView(llSingle);
    }

    /**
     * 获取要显示的复查数据
     *
     * @param eyeSight
     * @param reviewDataTimesList
     * @param trainDurationList
     * @param isIncrease
     */
    private void getTrainTimeByBottomDate(List<EyeSightBean> eyeSight, List<String> reviewDataTimesList, List<String> trainDurationList, boolean isIncrease) {
        int lastTimes = 0;
        /**
         * 当更新的时候，需要检测上次的次数
         */
        if (null != reviewDataTimesList && reviewDataTimesList.size() > 1) {
            String lastTimesStr = null;
            if (isIncrease) {
                lastTimesStr = reviewDataTimesList.get(reviewDataTimesList.size() - 1);
            } else {
                lastTimesStr = reviewDataTimesList.get(1);
            }

            lastTimes = Integer.parseInt(lastTimesStr);
            MLog.d("lastTimes  = " + lastTimes);
        }

        eyeSight.clear();
        reviewDataTimesList.clear();
        trainDurationList.clear();


        int maxRevieTimes = 0;
        if (null != reviewDataEyeSightDBBeanList) {
            maxRevieTimes = reviewDataEyeSightDBBeanList.size();
        }
        reviewDataTimesList.add("0");
        trainDurationList.add("0");

        if (showLastSevenTimes) {
            lastTimes = maxRevieTimes;
        }

        if (isIncrease) {
            int maxTimes = Math.min(maxRevieTimes, lastTimes + 7);
            int minTimes = Math.min(maxTimes - 7, lastTimes + 1);
            minTimes = minTimes > 0 ? minTimes : 0;
            MLog.d(" minTimes = " + minTimes + " maxTimes = " + maxTimes);
            for (int i = minTimes; i < maxTimes; i++) {
                ReviewDataEyeSightDBBean eyeSightDBBean = reviewDataEyeSightDBBeanList.get(i);
                reviewDataTimesList.add(String.valueOf(eyeSightDBBean.getReviewEyeSightTimes()));
                trainDurationList.add(String.valueOf(eyeSightDBBean.getTrainTimeLong()));

                EyeSightBean eyeSightBean = new EyeSightBean();
                eyeSightBean.setLeftEyeSight((float) eyeSightDBBean.getLeftEyeSight());
                eyeSightBean.setRightEyeSight((float) eyeSightDBBean.getRightEyeSight());
                eyeSightBean.setDoubleEyeSight((float) eyeSightDBBean.getDoubleEyeSight());
                eyeSight.add(eyeSightBean);
            }
        } else {
            int minTimes = lastTimes - 7 > 0 ? lastTimes - 7 : 0;
            int maxTimes = Math.min(maxRevieTimes, minTimes + 7);
            MLog.d(" minTimes = " + minTimes + " maxTimes = " + maxTimes);
            for (int i = minTimes; i < maxTimes; i++) {
                ReviewDataEyeSightDBBean eyeSightDBBean = reviewDataEyeSightDBBeanList.get(i);
                reviewDataTimesList.add(String.valueOf(eyeSightDBBean.getReviewEyeSightTimes()));
                trainDurationList.add(String.valueOf(eyeSightDBBean.getTrainTimeLong()));
                EyeSightBean eyeSightBean = new EyeSightBean();
                eyeSightBean.setLeftEyeSight((float) eyeSightDBBean.getLeftEyeSight());
                eyeSightBean.setRightEyeSight((float) eyeSightDBBean.getRightEyeSight());
                eyeSightBean.setDoubleEyeSight((float) eyeSightDBBean.getDoubleEyeSight());
                eyeSight.add(eyeSightBean);
            }
        }
    }

    private float getLeftHourTextWidth() {
        final String maxText = "5.3";
        Paint backgroundLinePaint = new Paint();
        Rect mLeftBound = new Rect();
        backgroundLinePaint.setColor(getResources().getColor(R.color.selectLeftColor));
        backgroundLinePaint.setTextSize(DeviceUtils.dipToPx(MyApplication.getInstance(), 13));
        backgroundLinePaint.setTextAlign(Paint.Align.CENTER);
        backgroundLinePaint.getTextBounds(maxText, 0, maxText.length(), mLeftBound);
        return mLeftBound.width();
    }


}
