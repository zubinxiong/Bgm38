package me.ewriter.bangumitv.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Zubin on 2016/8/5.
 */
public abstract class BaseWebviewActivity extends AppCompatActivity {

    protected WebView mWebView = null;

    /**获取布局的资源id*/
    protected abstract int getContentViewResId();

    /**获取webview的id*/
    protected abstract int getWebViewResId();

    protected abstract void onPageStarted();
    protected abstract void onPageFinished();

    protected void loadUrl(String url){
        if(mWebView != null){
            mWebView.loadUrl(url);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());

        initWebView();
    }

    private void initWebView() {
        mWebView = (WebView)findViewById(getWebViewResId());

        if(mWebView != null){
            mWebView.setDrawingCacheEnabled(false);
            mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            initWebViewSetting();
            mWebView.setWebViewClient(new AppWebViewClients());
        }
    }

    private void initWebViewSetting() {
        WebSettings webSettings= mWebView.getSettings();
        if (webSettings != null) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            //禁用表单的auto suggestion
            webSettings.setSaveFormData(false);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setSupportZoom(false);
            // 是否允许页面执行js脚本，注意不能去掉
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUseWideViewPort(true);
        }
    }

    private class AppWebViewClients extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            BaseWebviewActivity.this.onPageFinished();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            BaseWebviewActivity.this.onPageStarted();
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.destroy();
        }
        super.onDestroy();
    }
}
