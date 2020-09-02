package com.zj.zhijue.fragment.mine;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.ChildrenDataBean;
import com.zj.zhijue.bean.FatherDataBean;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IGetQuestionPages;
import com.zj.zhijue.http.response.HttpResponseGetQuestionPageBean;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.view.expandablelistview.CustomExpandableListViewAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;


public class CommonProblemFragment extends BaseFragment {

    @BindView(R.id.function_item_title_tv)
    TextView functionItemTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout functionItemBackLayout;

    @BindView(R.id.alarm_clock_expandablelist)
    ExpandableListView myExpandableListView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private ArrayList<FatherDataBean> datas;
    private CustomExpandableListViewAdapter adapter;
    private List<DisposableObserver> disposableObserverList = new ArrayList<>();
    private UserInfoDBBean mUserInfoDBBean;
    private HttpResponseGetQuestionPageBean.CursorBean mCursorBean;
    private final int PAGE_SIZE = 30;
    private boolean isRequesting = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_problem_layout, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        setAdapter();
        initListener();
        return view;
    }

    private void initView(View view) {
        //myExpandableListView =  view.findViewById(R.id.alarm_clock_expandablelist);
        myExpandableListView.setGroupIndicator(null);
        functionItemTextView.setText(getResources().getText(R.string.common_problem_text));
        functionItemTextView.setTextColor(getResources().getColor(R.color.top_unsignin_text_color));
    }

    /**
     * 自定义setAdapter
     */
    private void setAdapter() {
        if (adapter == null) {
            adapter = new CustomExpandableListViewAdapter(getActivity(), datas);
            myExpandableListView.setAdapter(adapter);
        } else {
            adapter.flashData(datas);
        }
    }

    private void initData() {
        String serverId = Config.getConfig().getUserServerId();
        if (!CommonUtils.isEmpty(serverId)) {
            List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryRawUserInfoByServerID(MyApplication.getInstance(), serverId);
            mUserInfoDBBean = userInfoDBBeanList.get(0);
        }

        if (datas == null) {
            datas = new ArrayList<>();
        }
        datas.clear();
        getCommonProbleList(1);
        //addTestData();
    }

    private void addTestData() {
        // 一级列表中的数据
        String[] problemTitleArray = getResources().getStringArray(R.array.common_problem_title);
        String[] problemContentArray = getResources().getStringArray(R.array.common_problem_content);

        for (int i = 0; i < 6; i++) {
            FatherDataBean fatherData = new FatherDataBean();
            fatherData.setTitle(problemTitleArray[i]);
            // 二级列表中的数据
            ArrayList<ChildrenDataBean> itemList = new ArrayList<>();
            for (int j = 0; j < 1; j++) {
                ChildrenDataBean childrenData = new ChildrenDataBean();
                childrenData.setTitle(problemContentArray[i]);
                //childrenData.setDesc(j + ":30");
                itemList.add(childrenData);
            }
            fatherData.setList(itemList);
            datas.add(fatherData);
        }
    }

    protected void initListener() {
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setFooterHeight(0);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (null != mCursorBean && !mCursorBean.isRequesting()) {
                    mCursorBean.setRequesting(true);
                    getCommonProbleList(mCursorBean.getPageNo() + 1);
                } else {
                    if (!isRequesting()) {
                        getCommonProbleList(1);
                    }
                }

            }
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });


        functionItemBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        // 设置ExpandableListView的监听事件
        // 设置一级item点击的监听器
        myExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                //Toast.makeText(getActivity(), datas.get(arg2).getTitle(), Toast.LENGTH_LONG).show();
                //ToastDialogUtil.showShortToast(datas.get(arg2).getTitle());
                return false;
            }
        });

        // 设置二级item点击的监听器，同时在Adapter中设置isChildSelectable返回值true，同时二级列表布局中控件不能设置点击效果
        myExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2, int arg3, long arg4) {
                // TODO Auto-generated method stub
                //Toast.makeText(getActivity(), datas.get(arg2).getList().get(arg3).getTitle(), Toast.LENGTH_LONG).show();
                //ToastDialogUtil.showShortToast(datas.get(arg2).getList().get(arg3).getTitle());
                return false;
            }
        });
    }

    private void getCommonProbleList(int pageNo) {

        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IGetQuestionPages.PAGENO, pageNo);
        bodyMap.put(IGetQuestionPages.PAGESIZE, PAGE_SIZE);
        bodyMap.put(IGetQuestionPages.MEMBER_ID, null != mUserInfoDBBean ? mUserInfoDBBean.getServerId() : "");

        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                disposableObserverList.remove(this);
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HttpResponseGetQuestionPageBean httpResponseGetQuestionPageBean = (HttpResponseGetQuestionPageBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseGetQuestionPageBean>() {});
                //MLog.d("httpResponseGetQuestionPageBean = " + httpResponseGetQuestionPageBean);
                firstInitData(httpResponseGetQuestionPageBean);
                smartRefreshLayout.finishLoadMore();
                setRequesting(false);
            }

            @Override
            public void onError(Throwable e) {
                smartRefreshLayout.finishLoadMore();
                setRequesting(false);
                disposableObserverList.remove(this);
                ToastUtil.showShort("获取数据失败");
            }

            @Override
            public void onComplete() {

            }
        };
        disposableObserverList.add(disposableObserver);
        setRequesting(true);
        TrainSuscribe.getQuestionList(headerMap, bodyMap, disposableObserver);

    }

    private void firstInitData(HttpResponseGetQuestionPageBean responseGetQuestionPageBean) {
        if (null != responseGetQuestionPageBean && IGetQuestionPages.SUCCESS.equalsIgnoreCase(responseGetQuestionPageBean.getStatus())
                && null != responseGetQuestionPageBean.getData()) {

            List<HttpResponseGetQuestionPageBean.DataBean> dataBeanList = responseGetQuestionPageBean.getData();
            HttpResponseGetQuestionPageBean.CursorBean cursorBean = responseGetQuestionPageBean.getCursor();
            mCursorBean = cursorBean;
            int totalCount = cursorBean.getTotalCount();
            int pageNo = cursorBean.getPageNo();
            int pageSize = cursorBean.getPageSize();

            int oldSize = datas.size();
            int newDataCount = dataBeanList.size();

            for (int i = 0; i < newDataCount; i++) {
                HttpResponseGetQuestionPageBean.DataBean dataBean = dataBeanList.get(i);
                if (null != dataBean) {
                    FatherDataBean fatherData = new FatherDataBean();
                    fatherData.setTitle(dataBean.getTitle());

                    // 二级列表中的数据
                    ArrayList<ChildrenDataBean> itemList = new ArrayList<>();
                    ChildrenDataBean childrenData = new ChildrenDataBean();
                    childrenData.setTitle(dataBean.getContent());

                    itemList.add(childrenData);

                    fatherData.setList(itemList);

                    datas.add(fatherData);
                }
            }
            adapter.notifyDataSetChanged();


            if (pageNo * pageSize >= totalCount) {
                /**
                 * 最后一页
                 */
                smartRefreshLayout.setEnableLoadMore(false);
            }

        }
    }

    public boolean isRequesting() {
        return isRequesting;
    }

    public void setRequesting(boolean requesting) {
        isRequesting = requesting;
    }

    private void disposeAllObserver() {
        synchronized (disposableObserverList) {
            for (DisposableObserver disposableObserver : disposableObserverList) {
                if (null != disposableObserver) {
                    disposableObserver.dispose();
                }
            }
            disposableObserverList.clear();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposeAllObserver();
    }
}
