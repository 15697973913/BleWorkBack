package com.zj.zhijue.activity.mine;

import android.os.Bundle;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.adapter.TimeRecordAdapter;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.bean.RecordBean;
import com.zj.zhijue.bean.RecordListBean;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IGetFeedbackPages;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.jsonutil.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * Date:2020/6/21
 * Time:16:21
 * Des:剩余时长记录
 * Author:Sonne
 */
public class TimeRecordActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView functionItemTitleTv;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private UserInfoDBBean mUserInfoDBBean;
    private List<DisposableObserver> disposableObserverList = new ArrayList<>();
    private List<RecordListBean.SurplusTimeBean> useBeanList;
    private TimeRecordAdapter mAdapter;
    private int mPage;
    private int mPageSize = 500;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setContentView(R.layout.activity_time_record);
        super.onCreate(bundle);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        functionItemTitleTv.setText(R.string.string_mine_2);

        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPridmary));
        useBeanList = new ArrayList<>();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TimeRecordAdapter(this, useBeanList);
        mAdapter.setOnLoadMoreListener(this, recycler);
        mAdapter.setEmptyView(R.layout.layout_list_empty, recycler);
        recycler.setAdapter(mAdapter);

        String serverId = Config.getConfig().getUserServerId();
        if (!CommonUtils.isEmpty(serverId)) {
            List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryRawUserInfoByServerID(MyApplication.getInstance(), serverId);
            mUserInfoDBBean = userInfoDBBeanList.get(0);
        }

        swipeRefresh.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        loadList(mPage, mPageSize);
    }

    //加载列表
    private void loadList(int page, int pageSize) {
        if (null == mUserInfoDBBean) {
            return;
        }

        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("memberId", mUserInfoDBBean.getServerId());
//        bodyMap.put(IGetFeedbackPages.PAGENO, page);
//        bodyMap.put(IGetFeedbackPages.PAGESIZE, pageSize);
//        bodyMap.put(IGetFeedbackPages.CREATEBY, mUserInfoDBBean.getLogin_name());
//        bodyMap.put(IGetFeedbackPages.MEMBER_ID, mUserInfoDBBean.getServerId());
        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                disposableObserverList.remove(this);
                if (swipeRefresh != null) {
                    String newStringDataJson = null;
                    try {
                        newStringDataJson = new String(responseBody.bytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    RecordBean recordBean = (RecordBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<RecordBean>() {
                    });
                    if (recordBean != null) {
                        if (recordBean.getStatus().equals("success")) {
                            List<RecordListBean.SurplusTimeBean> tempList=new ArrayList<>();
                            if(recordBean.getData().getUseTimeLogs()!=null&&recordBean.getData().getUseTimeLogs().size()>0)
                            {
                                tempList.addAll(recordBean.getData().getUseTimeLogs());
                            }
                            if(recordBean.getData().getAddTimeLogs()!=null&&recordBean.getData().getAddTimeLogs().size()>0)
                            {
                                tempList.addAll(recordBean.getData().getAddTimeLogs());
                            }
                            showList(tempList);
                        } else {
//                            disposeFlag(listBaseEntity.getCode(), listBaseEntity.getMsg());
                            ToastUtil.showShort(recordBean.getMessage());
                        }
                    }
                    if (swipeRefresh != null) {
                        swipeRefresh.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                disposableObserverList.remove(this);
                if (swipeRefresh != null) {
                    if (mPage == 1) {
                        swipeRefresh.setRefreshing(false);
                    } else {
                        mAdapter.loadMoreFail();
                    }
//                    disposeFlag(e.getCode(), e.getMessage());
                    ToastUtil.showShort(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        };
        disposableObserverList.add(disposableObserver);
        TrainSuscribe.getAddAndUseLogs(headerMap, bodyMap, disposableObserver);
    }


    //显示新数据列表
    private void showList(List<RecordListBean.SurplusTimeBean> list) {
        if (list == null) {
            return;
        }
        if (mPage == 1) {
            useBeanList.clear();
        }
        useBeanList.addAll(list);

        if (list.size() < mPageSize) {
            mAdapter.loadMoreEnd();
        } else {
            mAdapter.loadMoreComplete();
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreRequested() {
        if (useBeanList.size() == 0) {
            mAdapter.loadMoreEnd();
            return;
        }
        loadList(++mPage, mPageSize);
    }

    @OnClick(R.id.function_item_backlayout)
    public void onViewClicked() {
        finish();
    }
}
