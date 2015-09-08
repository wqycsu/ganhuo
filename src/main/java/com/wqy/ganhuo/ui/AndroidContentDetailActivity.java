package com.wqy.ganhuo.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.wqy.ganhuo.R;
import com.wqy.ganhuo.base.WebViewActivity;

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
        loadUrl();
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
            if(iconWidth>0){
                titleWidth = toolbarWidth - 2 * iconWidth;
                textView.setMinWidth(titleWidth);
                textView.getLayoutParams().width = titleWidth;
            }
        }
    }
}
