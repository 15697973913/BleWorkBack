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
import com.google.gson.Gson;
import com.huige.library.utils.SharedPreferencesUtils;
import com.litesuits.android.log.Log;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.HtmlCodeReponseBean;
import com.zj.zhijue.config.Constants;
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


/**
 * Created by Administrator on 2018/11/29.
 */

public class ProductIntroduceFragment extends BaseFragment {

    @BindView(R.id.function_item_title_tv)
    TextView functionItemTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout functionItemBackLayout;

    @BindView(R.id.commonwebview)
    ProgressWebView mProgressWebView;

    private HtmlCodeReponseBean curHtmlCodeReponseBean;

    private List<DisposableObserver> disposableObserverList = new ArrayList<>();
    private static final String TAG = "ProductIntroduceFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productintroduce_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        initListener();
        return view;
    }

    private void initView() {
        functionItemTextView.setText(getResources().getText(R.string.product_introduce_text));
        //mProgressWebView.loadUrl("http://www.baidu.com");

        WebSettings webSettings = mProgressWebView.getSettings();
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据。
        //LOAD_DEFAULT: 根据cache-control或者Last-Modified决定是否从网络上取数据。
        //LOAD_CACHE_NORMAL: API level 17中已经废弃，从API level 11开始作用同LOAD_DEFAULT模式
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据。
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。本地没有缓存时才从网络上获取。
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

    }

    private void initData() {
        //从本地取缓存数据
        Gson gson = new Gson();
        HtmlCodeReponseBean htmlCodeReponseBean = gson.fromJson((String) SharedPreferencesUtils.get(Constants.SP_HTMLCODEREPONSE, ""), HtmlCodeReponseBean.class);
        if (htmlCodeReponseBean != null) {
            curHtmlCodeReponseBean = htmlCodeReponseBean;
            showHtmlCode(htmlCodeReponseBean);
        }


        getHtmlCode();
    }

    protected void initListener() {
        functionItemBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private void getHtmlCode() {
        if (!checkNetworkAvaliable()) {
            return;
        }

        if (curHtmlCodeReponseBean == null) {
            showDefualtBlackBackGroundCircleLodingDialog();
        }
        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IGetHtmlCode.CATEGORYID, IGetHtmlCode.PRODUCT_INTRODUCE);

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
                Log.v(TAG, "newStringDataJson", newStringDataJson);
                HtmlCodeReponseBean htmlCodeReponseBean = (HtmlCodeReponseBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HtmlCodeReponseBean>() {
                });

                if (htmlCodeReponseBean == null) return;

                Gson gson = new Gson();
                if (curHtmlCodeReponseBean != null) {
                    //如果本地保存的数据和网络返回的不一致，就清除本地数据
                    if (!htmlCodeReponseBean.getData().getCategoryId().equals(curHtmlCodeReponseBean.getData().getCategoryId()) && !htmlCodeReponseBean.getData().getContent().equals(curHtmlCodeReponseBean.getData().getContent())) {
                        showHtmlCode(htmlCodeReponseBean);
                        SharedPreferencesUtils.remove(Constants.SP_OPTOMETRYDATA);
                        SharedPreferencesUtils.put(Constants.SP_HTMLCODEREPONSE, gson.toJson(htmlCodeReponseBean));
                    }

                } else {
                    SharedPreferencesUtils.put(Constants.SP_HTMLCODEREPONSE, gson.toJson(htmlCodeReponseBean));
                    showHtmlCode(htmlCodeReponseBean);
                }

                dismissDefualtBlackBackGroundCircleLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                disposableObserverList.remove(this);
                ToastUtil.showShort("获取数据失败");
                //dismissCustomCommonCircleLodingDialog();
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
    }


}
