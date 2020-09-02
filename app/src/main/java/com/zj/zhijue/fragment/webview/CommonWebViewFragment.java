package com.zj.zhijue.fragment.webview;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.common.baselibrary.webview.ProgressWebView;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;

import butterknife.BindView;

public class CommonWebViewFragment extends BaseFragment {

    @BindView(R.id.commonwebview)
    ProgressWebView mProgressWebView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_common_webview_layout, container, false);
        initData();
        initView();
        return view;
    }

    private void initData() {


    }

    private void initView() {
        mProgressWebView.loadUrl("http://www.baidu.com");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }
}
