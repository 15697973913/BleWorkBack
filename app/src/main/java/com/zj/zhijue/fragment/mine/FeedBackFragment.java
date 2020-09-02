package com.zj.zhijue.fragment.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.ui.RecyclerViewUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.adapter.FeedBackAdapter;
import com.zj.zhijue.adapter.MsgAdapter;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.AdviseBean;
import com.zj.zhijue.bean.MsgBean;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IGetFeedbackPages;
import com.zj.zhijue.http.response.HttpResponseGetFeedBackListBean;
import com.zj.zhijue.listener.OnItemClickListener;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.activity.function.FeedBackReplyInfoActivity;
import com.zj.zhijue.adapter.AdviseRecyclerViewAdapter;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.zj.zhijue.view.recyclerview.GloriousRecyclerView;
import com.zj.zhijue.view.recyclerview.GridItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * 意见反馈
 */
public class FeedBackFragment extends BaseFragment {

    @BindView(R.id.function_item_title_tv)
    TextView functionItemTextView;
    @BindView(R.id.tvNotData)
    TextView tvNotData;

    @BindView(R.id.function_item_backlayout)
    LinearLayout functionItemBackLayout;

    @BindView(R.id.advisebutton)
    Button adviseButton;
    @BindView(R.id.function_advise_recyclerview)
    GloriousRecyclerView gloriousRecyclerView;
//    @BindView(R.id.recycler)
//    RecyclerView recycler;
//    @BindView(R.id.swipe_refresh)
//    SwipeRefreshLayout swipeRefresh;

    AdviseRecyclerViewAdapter adviseRecyclerViewAdapter = null;

    List<AdviseBean> adviseBeanList = new ArrayList<>();
//    private FeedBackAdapter mAdapter;
//    private int mPage;
//    private int mPageSize = 10;

    public static final int ADD_FEEDBACK_RESULT_SUCCESS = 10;

    private final String TAG = FeedBackFragment.class.getSimpleName();
    private static final int PAGE_SIZE = 30;
    private UserInfoDBBean mUserInfoDBBean;
    private List<DisposableObserver> disposableObserverList = new ArrayList<>();
    private RecyclerViewUtil recyclerViewUtil = null;
    private HttpResponseGetFeedBackListBean.CursorBean mLastCursorBean;
    private boolean isRequestingData = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_layout, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        functionItemTextView.setText(getResources().getText(R.string.feedback_text));
        initRecyclerView();
//        swipeRefresh.setOnRefreshListener(this);
//        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPridmary));
//        adviseBeanList = new ArrayList<>();
//        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mAdapter = new FeedBackAdapter(getActivity(), adviseBeanList);
//        mAdapter.setOnLoadMoreListener(this, recycler);
//        mAdapter.setEmptyView(R.layout.layout_list_empty, recycler);
//        recycler.setAdapter(mAdapter);
    }

    private void initData() {
        String serverId = Config.getConfig().getUserServerId();
        if (!CommonUtils.isEmpty(serverId)) {
            List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryRawUserInfoByServerID(MyApplication.getInstance(), serverId);
            mUserInfoDBBean = userInfoDBBeanList.get(0);
        }
        adviseBeanList.clear();
        getFeedBackList(1);
        //addTestData();
        adviseRecyclerViewAdapter.setDatas(adviseBeanList);
//        swipeRefresh.setRefreshing(true);
//        onRefresh();
    }

    private void addTestData() {
        String[] problemTitleArray = getResources().getStringArray(R.array.common_problem_title);
        String[] problemContentArray = getResources().getStringArray(R.array.common_problem_content);
        for (int i = 0; i < problemContentArray.length; i++) {
            AdviseBean adviseBean = new AdviseBean();
            adviseBean.setQuestionContent(problemTitleArray[i]);
            adviseBean.setAnswer(problemContentArray[i]);

            if (i % 2 == 0) {
                adviseBean.setReplyed(true);
            }

            adviseBeanList.add(adviseBean);
        }

    }

    protected void initListener() {
        functionItemBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        adviseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToastUtil.showShortToast("提建议");
                Intent mIntent = new Intent(getActivity(), FeedBackReplyInfoActivity.class);
                mIntent.putExtra(FeedBackReplyInfoActivity.IS_NEW_ADVISE_KEY, true);
                //startActivity(mIntent);
                startActivityForResult(mIntent, BaseActivity.ADD_FEEDBACK_REQUEST_CODE);

            }
        });

        adviseRecyclerViewAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                AdviseBean adviseBean = adviseBeanList.get(position);
                Intent mIntent = new Intent(getActivity(), FeedBackReplyInfoActivity.class);
                mIntent.putExtra(FeedBackReplyInfoActivity.IS_FEEDBACK_REPLYED_KEY, adviseBean.isReplyed());
                mIntent.putExtra(FeedBackReplyInfoActivity.ADVISE_PARCEABLE_BEAN_KEY, adviseBean);
                startActivity(mIntent);
            }
        });

        recyclerViewUtil = new RecyclerViewUtil(getActivity(), gloriousRecyclerView);
        recyclerViewUtil.setOnLoadMoreListener(new RecyclerViewUtil.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //MLog.d("onLoadMore()");
                if (null != mLastCursorBean) {
                    if (!mLastCursorBean.isHaveRequested()) {
                        mLastCursorBean.setHaveRequested(true);
                        getFeedBackList(mLastCursorBean.getPageNo() + 1);
                    }
                } else {
                    if (!isRequestingData()) {
                        getFeedBackList(1);
                    }
                }
            }
        });

    }

    private void initRecyclerView() {
        Activity activity = getActivity();
        if (null == activity) {
            return;
        }

        final GridLayoutManager manager = new GridLayoutManager(activity, 1);

        adviseRecyclerViewAdapter = new AdviseRecyclerViewAdapter(this);
        gloriousRecyclerView.setAdapter(adviseRecyclerViewAdapter);

        GridItemDecoration gridItemDecoration = new GridItemDecoration.Builder(activity)
                .size((int) DeviceUtils.dipToPx(activity, 4))
                .color(R.color.register_bg_color)
                .margin(0, 0)
                .isExistHead(false)
                .showHeadDivider(false)
                .showLastDivider(false)
                .build();

        gloriousRecyclerView.addItemDecoration(gridItemDecoration);
        gloriousRecyclerView.setLayoutManager(manager);
        adviseRecyclerViewAdapter.setDatas(null);

    }

    private void getFeedBackList(int pageNo) {
        if (null == mUserInfoDBBean) {
            return;
        }

        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IGetFeedbackPages.PAGENO, pageNo);
        bodyMap.put(IGetFeedbackPages.PAGESIZE, PAGE_SIZE);
        bodyMap.put(IGetFeedbackPages.CREATEBY, mUserInfoDBBean.getLogin_name());
        bodyMap.put(IGetFeedbackPages.MEMBER_ID, mUserInfoDBBean.getServerId());
        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                setRequestingData(false);
                disposableObserverList.remove(this);
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HttpResponseGetFeedBackListBean httpResponseGetFeedBackListBean = (HttpResponseGetFeedBackListBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseGetFeedBackListBean>() {
                });
                //MLog.d("httpResponseGetFeedBackListBean = " + httpResponseGetFeedBackListBean);
                firstInitData(httpResponseGetFeedBackListBean);
            }

            @Override
            public void onError(Throwable e) {
                disposableObserverList.remove(this);
                ToastUtil.showShort("获取数据失败");
                setRequestingData(false);
            }

            @Override
            public void onComplete() {

            }
        };
        disposableObserverList.add(disposableObserver);
        setRequestingData(true);
        TrainSuscribe.getFeedBackList(headerMap, bodyMap, disposableObserver);
    }

    private void firstInitData(HttpResponseGetFeedBackListBean httpResponseGetFeedBackListBean) {
        if (null != httpResponseGetFeedBackListBean
                && null != httpResponseGetFeedBackListBean.getData()
                && null != httpResponseGetFeedBackListBean.getCursor()) {
            List<HttpResponseGetFeedBackListBean.DataBean> dataBeanList = httpResponseGetFeedBackListBean.getData();
            HttpResponseGetFeedBackListBean.CursorBean cursorBean = httpResponseGetFeedBackListBean.getCursor();
            mLastCursorBean = cursorBean;
            int totalCount = cursorBean.getTotalCount();
            int pageNo = cursorBean.getPageNo();
            int pageSize = cursorBean.getPageSize();

            int oldCount = adviseBeanList.size();
            int newCount = dataBeanList.size();

            for (int i = 0; i < newCount; i++) {
                HttpResponseGetFeedBackListBean.DataBean dataBean = dataBeanList.get(i);
                AdviseBean adviseBean = new AdviseBean();
                adviseBean.setQuestionTitle(dataBean.getTitle());
                adviseBean.setQuestionContent(dataBean.getContent() + ("\n创建于：" + dataBean.getCreateDate()));
                adviseBean.setAnswer(dataBean.getAllReplyContent());
                adviseBean.setTitleIndex(oldCount + (i + 1));
                int status = dataBean.getStatus();
                adviseBean.setReplyed(status != 0 ? true : false);
                adviseBeanList.add(adviseBean);
            }

            if (oldCount > 0) {
                oldCount = oldCount - 1;
            }
            gloriousRecyclerView.getAdapter().notifyItemRangeInserted(oldCount, newCount);
            if (pageNo * pageSize >= totalCount) {
                /**
                 * 最后一页
                 */
                recyclerViewUtil.setLoadMoreEnable(false);
            }

            if (adviseBeanList.size() == 0) {
                tvNotData.setVisibility(View.VISIBLE);
            } else {
                tvNotData.setVisibility(View.GONE);
            }
        }
    }

    public boolean isRequestingData() {
        return isRequestingData;
    }

    public void setRequestingData(boolean requestingData) {
        isRequestingData = requestingData;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposeAllObserver();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MLog.d(TAG + " onActivityResult requestCode = " + requestCode + "  resultCode = " + resultCode);
        if (requestCode == BaseActivity.ADD_FEEDBACK_REQUEST_CODE &&
                requestCode == ADD_FEEDBACK_RESULT_SUCCESS) {
            ToastUtil.showLong("添加反馈之后的返回");
        }
    }
}
