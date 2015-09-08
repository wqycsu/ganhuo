package com.wqy.ganhuo.utils;

import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by weiquanyun on 15/8/16.
 */
public class CommonUtil {
    private CommonUtil mCommonUtil;
    private static Context mContext;
    static class CommonUtilHolder{
        protected static final CommonUtil INSTANCE = new CommonUtil();
    }

    private CommonUtil(){}

    public static CommonUtil getInstance(Context context){
        mContext = context;
        return CommonUtilHolder.INSTANCE;
    }

    public Point getScreenSize(){
        Point point = new Point();
        WindowManager windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        point.x = metrics.widthPixels;
        point.y = metrics.heightPixels;
        return point;
    }
}
