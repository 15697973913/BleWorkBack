package com.zj.zhijue.fragment;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.AppCompatTextView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.common.baselibrary.log.MLog;
import com.zj.zhijue.R;
import com.zj.zhijue.base.LazyLoadFragment;
import com.zj.zhijue.bean.CatsItemBean;
import com.zj.zhijue.event.TrainTimeUpdateStatusEvent;
import com.zj.zhijue.listener.ItemOfViewPagerOnClickListener;
import com.zj.zhijue.listener.MainControlActivityListener;
import com.zj.zhijue.listener.TittleChangeListener;
import com.zj.zhijue.model.TrainModel;
import com.zj.zhijue.adapter.TrainTimeAdapter;
import com.zj.zhijue.util.thirdutil.Utils;
import com.zj.zhijue.view.HorizontalScrollSelectedView;
import com.zj.zhijue.view.hozscrollerview.BaseFlexibleTabIndicator;
import com.zj.zhijue.view.hozscrollerview.ImageTabIndicator;
import com.zj.zhijue.view.hozscrollerview.MainTabIndicator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReportFormFragment extends LazyLoadFragment implements ItemOfViewPagerOnClickListener {

    private HorizontalScrollSelectedView hsMain;
    private TrainTimeAdapter trainTimeAdapter;
    private ViewPager viewPager;
    private MainTabIndicator mainTabIndicator;

    @BindView(R.id.tvTabMonth)
    TextView tvTabMonth;
    @BindView(R.id.tvTabWeek)
    TextView tvTabWeek;
    @BindView(R.id.tvTabDay)
    TextView tvTabDay;

    private Handler mHander = new Handler();
    private volatile AtomicBoolean connected = new AtomicBoolean(false);
    private final List<CatsItemBean> mCategoryList = new ArrayList();
    private MainControlActivityListener mainControlActivityListener;

    List<String> strings = new ArrayList<String>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //View view = inflater.inflate(R.layout.fragment_reportforms_layout, container, false);
        ButterKnife.bind(this, getContentView());
        initView(getContentView());
        return getContentView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainControlActivityListener = (MainControlActivityListener) getActivity();
        initData();
        initViewPager();
        initListener();
        initIndicator();
    }

    private void initView(View view) {

        hsMain = view.findViewById(R.id.hd_main);
        mainTabIndicator = view.findViewById(R.id.tabindicator);
        viewPager = view.findViewById(R.id.viewpager);
    }

    private void initData() {
        strings.clear();
        strings.add("月");
//        strings.add("周");
//        strings.add("今天");
        //strings.add("昨天");
        hsMain.setData(strings);
        TrainModel.getInstance().getAllTrainTimeWithLimit();
    }

    private void initIndicator() {
        mCategoryList.clear();

        tvTabMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        tvTabWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        tvTabDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initTabIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initTabIndex(2);

    }

    /**
     * 设置Tab的显示位置
     *
     * @param position
     */
    private void initTabIndex(int position) {

        tvTabMonth.setTextColor(getResources().getColor(R.color.res_color_blue_374cff));
        tvTabWeek.setTextColor(getResources().getColor(R.color.res_color_blue_374cff));
        tvTabDay.setTextColor(getResources().getColor(R.color.res_color_blue_374cff));
        tvTabMonth.setBackground(null);
        tvTabWeek.setBackground(null);
        tvTabDay.setBackground(null);

        switch (position) {
            case 0:
                //月
                tvTabMonth.setTextColor(getResources().getColor(R.color.white));
                tvTabMonth.setBackgroundResource(R.drawable.res_round_blue_10_0_10_0);
                break;
            case 1:
                //周
                tvTabWeek.setTextColor(getResources().getColor(R.color.white));
                tvTabWeek.setBackgroundResource(R.color.res_color_blue_374cff);
                break;
            case 2:
                //日
                tvTabDay.setTextColor(getResources().getColor(R.color.white));
                tvTabDay.setBackgroundResource(R.drawable.res_round_blue_0_10_0_10);
                break;
            default:
                break;
        }
    }

    protected void initListener() {
        hsMain.setTittleChangeListener(new TittleChangeListener() {
            @Override
            public void selectedIndexChanged(int titleIndex) {
                Log.d("MainActivity", "titleIndex = " + titleIndex);
                viewPager.setCurrentItem(titleIndex);
            }
        });
    }

    private void initViewPager() {
        trainTimeAdapter = new TrainTimeAdapter(strings, this);
        viewPager.setAdapter(trainTimeAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != hsMain.getCurrentSelectedIndex()) {
                    hsMain.setTitleIndex(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(2);
    }

    @Override
    public void onClickIndex(int index, int resourceId) {
        Toast.makeText(getActivity(), "index = " + index + "  resourceId = " + resourceId, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveUpdateTrainTime(TrainTimeUpdateStatusEvent trainTimeUpdateStatusEvent) {
        //MLog.d("onReceiveUpdateTrainTime trainTimeUpdateStatusEvent = " + trainTimeUpdateStatusEvent.toString());
        //MLog.d("TrainModel.getInstance().getCurrentUserTrainTimeData().siee = " + TrainModel.getInstance().getCurrentUserTrainTimeData().size());
        /**
         * 刷新 ViewPager，显示新的训练时间
         */
        trainTimeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

    private void startSearchActivity() {
       /* Intent mIntent = new Intent(getActivity(), BleSearchActivity.class);
        startActivityForResult(mIntent, 1200);*/
        if (null != mainControlActivityListener) {
            mainControlActivityListener.startSearchActivityForResultFromTrainFragment();
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_reportforms_layout;
    }

    @Override
    protected void lazyLoad() {
        MLog.d("lazyLoad() firstInitData = " + firstInitData);
        if (firstInitData) {
            firstInitData = false;
            //initView(getContentView());
        } else {
            networkAvailableNotify();
        }
        if (null != trainTimeAdapter) {
            trainTimeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void networkAvailableNotify() {

    }
}
