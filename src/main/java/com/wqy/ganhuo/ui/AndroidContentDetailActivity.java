package com.wqy.ganhuo.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.wqy.ganhuo.R;
import com.wqy.ganhuo.base.WebViewActivity;
import com.wqy.ganhuo.network.NetworkUtil;

public class AndroidContentDetailActivity extends WebViewActivity {

    String url;
    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        desc = getIntent().getStringExtra("desc");
        setSupportActionBar(getToolbar());
        setTitle();
        if (NetworkUtil.isNetConnected(this)) {
            loadUrl();
        } else {
            Snackbar.make(baseContainer, "无网络了,联网再试试吧!", Snackbar.LENGTH_LONG)
                    .setAction("网络设置", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            if(android.os.Build.VERSION.SDK_INT>10){
                                intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            }else{
                                intent = new Intent();
                                ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                                intent.setComponent(component);
                                intent.setAction("android.intent.action.VIEW");
                            }
                            startActivity(intent);
                        }
                    }).show();
        }
    }

    @Override
    public void loadUrl() {
        getWebView().loadUrl(url);
    }

    @Override
    public void setTitle() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textView = (TextView) getToolbar().findViewById(R.id.text_in_toolbar);
        if (textView != null) {
            textView.setText(desc);
            int toolbarWidth = getToolbar().getWidth();
            int titleWidth = textView.getWidth();
            int iconWidth = toolbarWidth - titleWidth;
            if (iconWidth > 0) {
                titleWidth = toolbarWidth - 2 * iconWidth;
                textView.setMinWidth(titleWidth);
                textView.getLayoutParams().width = titleWidth;
            }
        }
    }
}
