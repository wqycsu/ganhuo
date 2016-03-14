package com.wqy.ganhuo.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wqy.ganhuo.R;
import com.wqy.ganhuo.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class WebViewActivity extends BaseActivity {

    @Bind(R.id.web_base_container_layout)
    protected LinearLayout baseContainer;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.web_progress)
    TextView mWebLoadProgress;
    @Bind(R.id.web_load_progress)
    ProgressBar progressBar;
    @Bind(R.id.web_view)
    WebView mWebView;

    WebClient mWebViewClient;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        setSupportActionBar(toolbar);
        mWebViewClient = new WebClient();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChrome());
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    public abstract void loadUrl();
    public abstract void setTitle();

    public void setUrl(String url){
        this.url = url;
    }

    public WebView getWebView() {
        return mWebView;
    }

    public Toolbar getToolbar(){
        return this.toolbar;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN){
            if(keyCode==KeyEvent.KEYCODE_BACK){
                if(mWebView.canGoBack())
                    mWebView.goBack();
                else
                    onBackPressed();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.action_refresh:
                mWebView.reload();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.onFinishTemporaryDetach();
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null)
            mWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    class WebClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
//            mWebLoadProgress.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            mWebLoadProgress.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    class WebChrome extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
//            mWebLoadProgress.setText(String.format(getResources().getString(R.string.webview_progress), String.valueOf(newProgress))+"%");
            progressBar.setProgress(newProgress);
        }
    }
}
