package com.wqy.ganhuo.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wqy.ganhuo.utils.Constants;

import java.util.ArrayList;

/**
 * Created by weiquanyun on 15/8/17.
 */
public class AndroidContentItem extends ContentItem {

    private boolean isStared;

    public boolean isStared() {
        return isStared;
    }

    public void setIsStared(boolean isStared) {
        this.isStared = isStared;
    }

    public static String getRequestUrl(int page) {
        return Constants.ANDROID_REQUEST_URL + "20/" + page;
    }

    public static String getRequestUrl(int count, int page) {
        return Constants.ANDROID_REQUEST_URL + count + "/" + page;
    }

    public static ArrayList<AndroidContentItem> parseCache(JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }
        ArrayList<AndroidContentItem> items = new ArrayList<AndroidContentItem>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            AndroidContentItem androidContentItem = new AndroidContentItem();
            androidContentItem.setIsStared(jsonObject.getBoolean("isStared"));
            androidContentItem.setCreatedAt(jsonObject.getString("createdAt"));
            androidContentItem.setDesc(jsonObject.getString("desc"));
            androidContentItem.setObjectId(jsonObject.getString("objectId"));
            androidContentItem.setPublishedAt(jsonObject.getString("publishedAt"));
            androidContentItem.setType(jsonObject.getString("type"));
            androidContentItem.setWho(jsonObject.getString("who"));
            androidContentItem.setUrl(jsonObject.getString("url"));
            androidContentItem.setUpdatedAt(jsonObject.getString("updatedAt"));
            androidContentItem.setUsed(jsonObject.getBoolean("used"));
            items.add(androidContentItem);
        }
        return items;
    }
}
