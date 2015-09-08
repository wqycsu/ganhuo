package com.wqy.ganhuo.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wqy.ganhuo.model.ContentItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiquanyun on 15/8/19.
 */
public class JSONParserUtil {

    static final String RESULTS = "results";

    public static <T> List<T> pareseJSON(String jsonStr, Class<T> clazz) {
        String result = extractResult(jsonStr);
        List<T> list = null;
        if (!TextUtils.isEmpty(result)) {
            list = JSON.parseArray(result, clazz);
        }
        if (list == null)
            list = new ArrayList<>();
        return list;
    }

    private static String extractResult(String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        if (jsonObject != null && jsonObject.containsKey(RESULTS)) {
            return jsonObject.get(RESULTS).toString();
        }
        return null;
    }
}
