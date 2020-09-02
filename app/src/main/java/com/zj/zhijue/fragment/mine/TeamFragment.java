package com.zj.zhijue.fragment.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.util.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.R;
import com.zj.zhijue.adapter.MsgAdapter;
import com.zj.zhijue.adapter.TeamAdapter;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.MsgBean;
import com.zj.zhijue.bean.TeamBean;
import com.zj.zhijue.bean.TeamInfoBean;
import com.zj.zhijue.http.request.IGetFeedbackPages;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.view.circleimageview.CircleImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * 个人团队
 */

public class TeamFragment extends BaseFragment {

    @BindView(R.id.function_item_backlayout)
    LinearLayout function_item_backlayout;
    @BindView(R.id.function_item_title_tv)
    AppCompatTextView functionItemTitleTv;
    @BindView(R.id.civ_lead_headimg)
    CircleImageView civLeadHeadimg;
    @BindView(R.id.tv_lead_name)
    TextView tvLeadName;
    @BindView(R.id.tv_lead_mobile)
    TextView tvLeadMobile;
    @BindView(R.id.tv_lead_lable)
    TextView tvLeadLable;
    @BindView(R.id.tv_lead_time)
    TextView tvLeadTime;
    @BindView(R.id.rl_lead)
    RelativeLayout rlLead;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private List<DisposableObserver> disposableObserverList = new ArrayList<>();
    private List<TeamInfoBean> teamInfoBeanList;
    private TeamInfoBean teamInfoBean;
    private TeamAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        initListener();
        return view;
    }

    private void initView() {
        functionItemTitleTv.setText("我的团队(0人)");
        teamInfoBeanList = new ArrayList<>();
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TeamAdapter(getActivity(), teamInfoBeanList);
        mAdapter.setEmptyView(R.layout.layout_list_empty, recycler);
        recycler.setAdapter(mAdapter);
    }

    private void updateUI(TeamInfoBean teamInfoBean){
        if(teamInfoBean==null){
            rlLead.setVisibility(View.GONE);
        }else{
            rlLead.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(teamInfoBean.getFace()).apply(new RequestOptions().placeholder(R.mipmap.qidongtubiao).error(R.mipmap.qidongtubiao)).into(civLeadHeadimg);
            tvLeadName.setText(teamInfoBean.getName());
            tvLeadMobile.setText(teamInfoBean.getPhone());
            tvLeadTime.setText(teamInfoBean.getTotalTime()+"小时");

        }

    }

    private void initData() {
        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                disposableObserverList.remove(this);
//                if (swipeRefresh!=null) {
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TeamBean teamBean = (TeamBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<TeamBean>() {
                });
                if (teamBean != null) {
                    if (teamBean.getStatus().equals("success")) {
                        if(null!=teamBean.getData().getInviteAccount()){
                            teamInfoBean=teamBean.getData().getInviteAccount();
                            updateUI(teamInfoBean);
                        }else{
                            rlLead.setVisibility(View.GONE);
                        }

                        if(teamBean.getData().getInviteTeam()!=null){
                            teamInfoBeanList.clear();
                            teamInfoBeanList.addAll(teamBean.getData().getInviteTeam());
                            functionItemTitleTv.setText("我的团队("+teamInfoBeanList.size()+"人)");
                            mAdapter.notifyDataSetChanged();
                        }

                    } else {
                        ToastUtil.showShort(teamBean.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                disposableObserverList.remove(this);
                ToastUtil.showShort(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        disposableObserverList.add(disposableObserver);
        TrainSuscribe.getTeamList(headerMap, bodyMap, disposableObserver);
    }

    protected void initListener() {
        function_item_backlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
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
        dismissDefualtBlackBackGroundCircleLoadingDialog();
    }


}
