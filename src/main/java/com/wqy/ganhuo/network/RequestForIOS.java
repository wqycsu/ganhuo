package com.wqy.ganhuo.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.wqy.ganhuo.model.AndroidContentItem;
import com.wqy.ganhuo.model.IOSContentItem;
import com.wqy.ganhuo.utils.JSONParserUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiquanyun on 15/8/30.
 */
public class RequestForIOS extends Request<List<IOSContentItem>>{

    Response.Listener<List<IOSContentItem>> listener;

    public RequestForIOS(String url, Response.Listener<List<IOSContentItem>> responseListener, Response.ErrorListener listener) {
        super(Method.GET, url, listener);
        this.listener = responseListener;
    }

    @Override
    protected Response<List<IOSContentItem>> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(JSONParserUtil.pareseJSON(jsonString,IOSContentItem.class),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void deliverResponse(List<IOSContentItem> response) {
        listener.onResponse(response);
    }
}
