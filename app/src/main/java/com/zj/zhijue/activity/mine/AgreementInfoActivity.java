package com.zj.zhijue.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.common.baselibrary.util.ToastUtil;
import com.blankj.utilcode.util.StringUtils;
import com.litesuits.android.log.Log;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 协议信息
 */
public class AgreementInfoActivity extends BaseActivity {


    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.function_item_title_tv)
    AppCompatTextView functionItemTitleTv;

    private String mHtmlText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_web);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initView();
    }


    private void initView() {
        functionItemTitleTv.setText(R.string.string_recharge_1);
        WebSettings webSettings = webView.getSettings();
        setDIYWebView(webSettings, getWindowManager());

        mHtmlText = getIntent().getStringExtra("mHtmlText");
        if (!StringUtils.isEmpty(mHtmlText)) {
            setContent(mHtmlText);
        }

    }

    @OnClick(R.id.function_item_backlayout)
    public void onViewClicked() {
        finish();
    }

    /**
     * 设置自适应webview
     *
     * @param webSettings   WebSettings
     * @param windowManager WindowManager
     */
    public void setDIYWebView(WebSettings webSettings, WindowManager windowManager) {

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(false);
        webSettings.setLoadWithOverviewMode(true);

    }

    private void setContent(String text) {
        try {
            StringBuffer sb = new StringBuffer();
            //添加html
            sb.append("<html><head><meta http-equiv='content-type' content='text/html; charset=utf-8'>");
            sb.append("<meta charset='utf-8'  content='1'></head><body style='color: black'><p></p>");
            //< meta http-equiv="refresh" content="time" url="url" >
            //添加文件的内容
            sb.append(text);
            //加载本地文件
            // sb.append("<img src='file:///"+AContext.getFileUtil().getDownloadsPath()+"'>");
            sb.append("</body></html>");
            // webView.loadData(data, mimeType, encoding);
            //设置字符编码，避免乱码
            webView.getSettings().setDefaultTextEncodingName("utf-8");
            webView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
