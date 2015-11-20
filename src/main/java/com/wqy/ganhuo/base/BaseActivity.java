package com.wqy.ganhuo.base;

import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.TintManager;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wqy.ganhuo.R;
import com.wqy.ganhuo.ui.MainActivity;
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //主Activity不支持滑动返回
        if(!(this instanceof MainActivity)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    distanceX = event.getX() - startX;
                    if(distanceX>=screenWidth/2){
                        super.onBackPressed();
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

}
