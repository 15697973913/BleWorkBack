package com.zj.zhijue.fragment.mine;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.webview.ProgressWebView;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.ConfigInfoBean;
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

/**
 *  用户注册协议
 */

public class UserAgreementFragment extends BaseFragment {

    @BindView(R.id.function_item_title_tv)
    TextView functionItemTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout functionItemBackLayout;

    @BindView(R.id.commonwebview)
    ProgressWebView mProgressWebView;
    private ConfigInfoBean infoBean;
    private List<DisposableObserver> disposableObserverList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_agreement_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        initListener();
        return view;
    }

    private void initView() {
        functionItemTextView.setText(getResources().getText(R.string.user_agreement_text));
        //mProgressWebView.loadUrl("http://www.baidu.com");

    }

    private void initData() {
//        getHtmlCode();
        postConfigInfo();
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

    private void getHtmlCode() {
        if(!checkNetworkAvaliable()) {
            return;
        }

        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IGetHtmlCode.CATEGORYID, IGetHtmlCode.USER_AGREEMENT);

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
                HtmlCodeReponseBean htmlCodeReponseBean = (HtmlCodeReponseBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HtmlCodeReponseBean>() {});
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

    /**
     * 获取协议信息
     */
    private void postConfigInfo() {
        if(!checkNetworkAvaliable()) {
            return;
        }
        final HashMap<String, String> headerMap = new HashMap<>(1);
        final HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("type", "6");

        LoginSubscribe.postConfigInfo(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                    MLog.e(newStringDataJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                infoBean = (ConfigInfoBean)
                        JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<ConfigInfoBean>() {
                        });
                if (!CommonUtils.isEmpty(infoBean.getData().getRemark())) {
                    mProgressWebView.loadDataWithBaseURL(null, infoBean.getData().getRemark(), "text/html", "utf-8", null);
                }
                dismissDefualtBlackBackGroundCircleLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShortToast(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
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
