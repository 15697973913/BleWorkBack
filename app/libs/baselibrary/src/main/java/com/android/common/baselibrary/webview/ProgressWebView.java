package com.android.common.baselibrary.webview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class ProgressWebView extends WebView {
    private WebViewProgressBar progressBar;
    private Handler handler;
    private WebView mWebView;
    private Context mContext;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        progressBar = new WebViewProgressBar(context);
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));;
        progressBar.setVisibility(GONE);
        addView(progressBar);
        handler = new Handler();
        mWebView = this;
        initSettings();
    }

    private void initSettings() {
        //初始化设置
        WebSettings mSetting = this.getSettings();

        //不显示webview缩放按钮
        //mSetting.setDisplayZoomControls(false);

        mSetting.setJavaScriptEnabled(true);
        mSetting.setDomStorageEnabled(true);
        mSetting.setDefaultTextEncodingName("UTF-8");//设置字符编码
        //设置web页面
        mSetting.setAllowContentAccess(true);//设置支持文件流
        mSetting.setSupportZoom(true);
        mSetting.setBuiltInZoomControls(true);
        mSetting.setUseWideViewPort(true);//调整到适合webView 大小
        mSetting.setLoadWithOverviewMode(true);//调整到适合webView 大小
        mSetting.setDefaultZoom(WebSettings.ZoomDensity.FAR);//屏幕自适应网页，如果没有这个，在低分辨率的手机显示异常
        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，再进行加载图片
        mSetting.setBlockNetworkImage(true);
        mSetting.setAppCacheEnabled(true);
        setWebViewClient(new MyWebClient());
        setWebChromeClient(new MyWebChromeClient());

        //WebSettings settings = this.getSettings();
        mSetting.setAllowFileAccess(true);
        mSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //mSetting.setSupportZoom(true);
        //mSetting.setBuiltInZoomControls(true);
        //mSetting.setUseWideViewPort(true);
        mSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        //mSetting.setAppCacheEnabled(true);
        mSetting.setDatabaseEnabled(true);
        //mSetting.setDomStorageEnabled(true);
        //mSetting.setJavaScriptEnabled(true);
        //mSetting.setGeolocationEnabled(true);
        mSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        mSetting.setAppCachePath(mContext.getDir("appcache", 0).getPath());
        mSetting.setDatabasePath(mContext.getDir("databases", 0).getPath());
        mSetting.setGeolocationDatabasePath(mContext.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        mSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);

        mSetting.setDefaultTextEncodingName("UTF-8");//设置字符编码
        //设置web页面
        //mSetting.setAllowContentAccess(true);//设置支持文件流
        //settings.setSupportZoom(false);
        //mSetting.setLoadWithOverviewMode(true);//调整到适合webView 大小
        //mSetting.setDefaultZoom(WebSettings.ZoomDensity.FAR);//屏幕自适应网页，如果没有这个，在低分辨率的手机显示异常
        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，再进行加载图片
        mSetting.setBlockNetworkImage(false);

    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setProgress(100);
                handler.postDelayed(runnable, 200);// 0.2 秒后隐藏进度条
            } else if (progressBar.getVisibility() == GONE) {
                progressBar.setVisibility(VISIBLE);
            }
            if (newProgress < 10) {
                newProgress = 10;
            }
            //不断更新进度
            progressBar.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }

    private class MyWebClient extends WebViewClient {
        /**
         * 加载过程中，拦截加载的地址 URL
         * @param view
         * @param url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("TAG","shouldOverrideUrlLoading url = " + url);


            return super.shouldOverrideUrlLoading(view, url);
        }

        /**
         * 页面加载过程中，加载资源回调的方法
         * @param view
         * @param url
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.d("TAG","onLoadResource url = " + url);
        }

        /**
         * 页面加载完成回调的方法
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //关闭图片加载阻塞
            Log.d("TAG","onPageFinished url = " + url);
            view.getSettings().setBlockNetworkImage(false);
        }

        /**
         * 页面开始加载调用的方法
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("TAG","onPageStarted url = " + url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.d("TAG","onReceivedError onReceivedError = " + error);
        }

        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
            ProgressWebView.this.requestFocus();
            ProgressWebView.this.requestFocusFromTouch();
        }
    }



    /**
     *刷新界面（此处为加载完成后进度消失）
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            progressBar.setVisibility(View.GONE);
        }
    };


}
