package com.wqy.ganhuo.utils;

import android.content.Context;
import android.widget.Toast;

import com.wqy.ganhuo.base.AppApplication;

/**
 * Created by weiquanyun on 15/8/11.
 */
public class ShowToast {
    public static Context context = AppApplication.getAppContext();

    public static void toastLong(CharSequence msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    public static void toastShort(CharSequence msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(int msgId){
        Toast.makeText(context,context.getString(msgId),Toast.LENGTH_LONG).show();
    }

    public static void toastShort(int msgId){
        Toast.makeText(context,context.getString(msgId),Toast.LENGTH_SHORT).show();
    }
}
