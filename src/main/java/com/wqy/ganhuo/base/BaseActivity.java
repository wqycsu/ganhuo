package com.wqy.ganhuo.base;

import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.TintManager;
import android.view.MotionEvent;

import com.wqy.ganhuo.ui.MainActivity;
import com.wqy.ganhuo.utils.CommonUtil;

public class BaseActivity extends AppCompatActivity {

    private int screenWidth;
    private int screentHeight;
    float startX = 0;
    float distanceX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TintManager tintManager = TintManager.get(this);
        }
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