package com.wqy.ganhuo.base;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wqy.ganhuo.R;
import com.wqy.ganhuo.utils.CommonUtil;

public abstract class BaseActivity extends AppCompatActivity {

    private int screenWidth;
    private int screentHeight;
    float startX = 0;
    float distanceX = 0;
    private SystemBarTintManager systemBarTintManager;

    Toolbar toolbar;
    private boolean mIsHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentLayout());
        systemBarTintManager = new SystemBarTintManager(this);
        // enable status bar tint
        systemBarTintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        systemBarTintManager.setNavigationBarTintEnabled(true);
        systemBarTintManager.setTintResource(R.color.primaryDark);
        Point point = CommonUtil.getInstance(getApplicationContext()).getScreenSize();
        screenWidth = point.x;
        screentHeight = point.y;
        int contentLayoutId = provideContentLayout();
        if(contentLayoutId > 0) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            if(toolbar != null) {
                setSupportActionBar(toolbar);
                toolbar.setTitleTextColor(getResources().getColor(R.color.colorNormal));
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(canGoBack()) {
                            finish();
                        }
                    }
                });
            }
        }
        if(canGoBack() && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public abstract int provideContentLayout();

    public abstract boolean canGoBack();

    public void setToolbarAlpha(float alpha) {
        toolbar.setAlpha(alpha);
    }

    protected void hideOrShowToolbar() {
        toolbar.animate()
                .translationY(mIsHidden ? 0 : -toolbar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHidden = !mIsHidden;
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    public void setToolbarTitle(int strId) {
        toolbar.setTitle(strId);
    }

    public void setToolbarTitleTextColor(int color) {
        toolbar.setTitleTextColor(color);
        toolbar.invalidate();
    }

}
