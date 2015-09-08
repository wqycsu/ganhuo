package com.wqy.ganhuo.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.wqy.ganhuo.base.AppApplication;
import com.wqy.ganhuo.utils.Constants;

/**
 * Created by weiquanyun on 15/8/11.
 */
public class RequestManager {
    public static RequestQueue queue = Volley.newRequestQueue(AppApplication.getAppContext());

    private RequestManager(){

    }

    public static void addRequest(Request<?> request, Object tag){
        if(tag!=null){
            request.setTag(tag);
        }
        queue.add(request);
    }

    public static void canAllByTag(Object tag){
        Log.d(Constants.TAG,"queue:"+queue);
        queue.cancelAll(tag);
    }
}
