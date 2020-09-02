package com.zj.zhijue.fragment.mine;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.webview.ProgressWebView;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.HtmlCodeReponseBean;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.http.request.IGetHtmlCode;
import com.zj.zhijue.util.jsonutil.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;


public class InterveneKeyIntroduceFragment extends BaseFragment {

    @BindView(R.id.function_item_title_tv)
    TextView functionItemTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout functionItemBackLayout;

    @BindView(R.id.commonwebview)
    ProgressWebView mProgressWebView;

    private List<DisposableObserver> disposableObserverList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intervene_key_introduce_layout, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        functionItemTextView.setText(getResources().getText(R.string.intervene_key_introduce_text));
        WebSettings webSettings = mProgressWebView.getSettings();
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据。
        //LOAD_DEFAULT: 根据cache-control或者Last-Modified决定是否从网络上取数据。
        //LOAD_CACHE_NORMAL: API level 17中已经废弃，从API level 11开始作用同LOAD_DEFAULT模式
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据。
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。本地没有缓存时才从网络上获取。
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        initData();
    }

    protected void initListener() {
        functionItemBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

    }

    private void initData() {
        getHtmlCode();
    }

    private void getHtmlCode() {
        if(!checkNetworkAvaliable()) {
            return;
        }
        showDefualtBlackBackGroundCircleLodingDialog();
        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IGetHtmlCode.CATEGORYID, IGetHtmlCode.INTERVENE_KEY_INTRODUCE);

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
                HtmlCodeReponseBean htmlCodeReponseBean = (HtmlCodeReponseBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HtmlCodeReponseBean>() {
                });
                //MLog.d("htmlCodeReponseBean = " + htmlCodeReponseBean);
                showHtmlCode(htmlCodeReponseBean);
                dismissDefualtBlackBackGroundCircleLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                disposableObserverList.remove(this);
                ToastUtil.showShort("获取数据失败");
                dismissDefualtBlackBackGroundCircleLoadingDialog();
            }

            @Override
            public void onComplete() {

            }
        };
        disposableObserverList.add(disposableObserver);
        TrainSuscribe.getHtmlCode(headerMap, bodyMap, disposableObserver);
    }


    private void showHtmlCode(HtmlCodeReponseBean htmlCodeReponseBean) {
        if (null != htmlCodeReponseBean && htmlCodeReponseBean.getStatus().equals(IBaseRequest.SUCCESS)) {
            HtmlCodeReponseBean.DataBean dataBean = htmlCodeReponseBean.getData();
            if (null != dataBean) {
                if (!CommonUtils.isEmpty(dataBean.getContent())) {
                    mProgressWebView.loadDataWithBaseURL(null, dataBean.getContent(), "text/html", "utf-8", null);
                }
            }

        }
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
