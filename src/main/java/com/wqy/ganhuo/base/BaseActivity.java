package com.wqy.ganhuo.base;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wqy.ganhuo.R;
import com.wqy.ganhuo.utils.CommonUtil;

public class BaseActivity extends AppCompatActivity {

    private int screenWidth;
    private int screentHeight;
    float startX = 0;
    float distanceX = 0;
    private SystemBarTintManager systemBarTintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        systemBarTintManager = new SystemBarTintManager(this);
        // enable status bar tint
        systemBarTintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        systemBarTintManager.setNavigationBarTintEnabled(true);
        systemBarTintManager.setTintResource(R.color.primaryDark);
        Point point = CommonUtil.getInstance(getApplicationContext()).getScreenSize();
        screenWidth = point.x;
        screentHeight = point.y;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
