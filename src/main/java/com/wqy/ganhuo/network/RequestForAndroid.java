package com.wqy.ganhuo.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.wqy.ganhuo.model.AndroidContentItem;
import com.wqy.ganhuo.utils.JSONParserUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by weiquanyun on 15/8/23.
 */
public class RequestForAndroid extends Request<ArrayList<AndroidContentItem>>{

    private Response.Listener<ArrayList<AndroidContentItem>> mListener;

    public RequestForAndroid(String url, Response.Listener<ArrayList<AndroidContentItem>> responseListener, Response.ErrorListener listener) {
        super(Method.GET, url, listener);
        this.mListener = responseListener;
    }

    @Override
    protected void deliverResponse(ArrayList<AndroidContentItem> response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<ArrayList<AndroidContentItem>> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    (ArrayList<AndroidContentItem>) JSONParserUtil.pareseJSON(jsonString,AndroidContentItem.class),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Request<?> setRequestQueue(RequestQueue requestQueue) {
        return super.setRequestQueue(requestQueue);
    }

}
