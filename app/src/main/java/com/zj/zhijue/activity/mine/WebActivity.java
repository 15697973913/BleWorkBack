package com.zj.zhijue.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.common.baselibrary.util.ToastUtil;
import com.huige.library.popupwind.PopupWindowUtils;
import com.huige.library.utils.ToastUtils;
import com.litesuits.android.log.Log;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {


    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.function_item_title_tv)
    AppCompatTextView functionItemTitleTv;

    private String mLoadUrl;
    private String phone;
    private PopupWindowUtils popupWindowUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        if(Build.VERSION.SDK_INT>=28){
//            NotchScreenTool.openFullScreenModel(this);
//        }else{
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
        setContentView(R.layout.activity_web);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initView();
    }


    private void initView() {
        cleanCache();
        phone = getIntent().getStringExtra("phone");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavaScriptinterface(this), "smartglass");
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据。
        //LOAD_DEFAULT: 根据cache-control或者Last-Modified决定是否从网络上取数据。
        //LOAD_CACHE_NORMAL: API level 17中已经废弃，从API level 11开始作用同LOAD_DEFAULT模式
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据。
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。本地没有缓存时才从网络上获取。
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    if (url.startsWith("http://") || url.startsWith("https://")) {
                        view.loadUrl(url);
                        return true;

                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }

                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
//                    WebUtils.jumpToBrow(WebBrowActivity.this,url);
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    functionItemTitleTv.setText(title);
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                // 加载网页标题
//                functionItemTitleTv.setText(title);
            }

        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//                WebUtils.jumpToBrow(WebGameActivity.this, url);
            }
        });

        mLoadUrl = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(mLoadUrl)) {
            webView.loadUrl(mLoadUrl);
        }


    }


    private void cleanCache() {
        try {
            webView.clearHistory();
            webView.clearFormData();
            deleteDatabase("WebView.db");
            deleteDatabase("WebViewCache.db");
            getCacheDir().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            cleanCache();
            super.onBackPressed();
        }
    }

    @OnClick(R.id.function_item_backlayout)
    public void onViewClicked() {
        finish();
    }

    public class JavaScriptinterface {
        Context context;

        public JavaScriptinterface(Context c) {
            context = c;
        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void toShare(String parms) {
            Log.e("TAG", "弹出分享" + parms);
            String url = "http://zj.zhuojiacn.net/zjwap/pages/index/haggles?phone=" + phone;
            share(findViewById(R.id.function_item_backlayout), url);
        }

    }

    private void share(View view, String url) {
        if (popupWindowUtils == null) {
            popupWindowUtils = new PopupWindowUtils(this, R.layout.pop_share)
                    .setStyle(R.style.popupWindowAsBottom)
                    .setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setOnClickListenerByViewId(R.id.wechat_ly, view1 -> {
                        shareLink(url, SHARE_MEDIA.WEIXIN);
                        popupWindowUtils.dismiss();
                    })
                    .setOnClickListenerByViewId(R.id.moments_ly, view12 -> {
                        popupWindowUtils.dismiss();
                        shareLink(url, SHARE_MEDIA.WEIXIN_CIRCLE);
                    });
            popupWindowUtils.setOnClickListenerByViewId(R.id.tv_cancel, new PopupWindowUtils.onPopupWindClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindowUtils.dismiss();
                }
            });
        }
        popupWindowUtils.showAtLocation(this, view, Gravity.BOTTOM, 0, 0);
    }

    //分享链接
    private void shareLink(String url, SHARE_MEDIA display) {
        UMWeb web = new UMWeb(url);
        web.setTitle("帮忙砍价"); //标题
        web.setDescription("邀请好友，最高优惠200元"); //描述
        UMImage thumb = new UMImage(this, R.mipmap.qidongtubiao);
        web.setThumb(thumb); //缩略图
        ShareAction shareAction = new ShareAction(this)
                .withMedia(web)
//                .setDisplayList(display)  //使用自带面板时候用这个
                .setPlatform(display)       //自定义面板时候用这个
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        ToastUtils.showToast("正在启动分享,请稍后!");
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        ToastUtils.showToast("分享成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtils.showToast("分享失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        ToastUtils.showToast("您已取消分享");
                    }

                });

        shareAction.share();
    }

}
