package com.wqy.ganhuo.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wqy.ganhuo.utils.Constants;

import java.util.ArrayList;

/**
 * Created by weiquanyun on 15/8/30.
 */
public class IOSContentItem extends ContentItem{

    private boolean isStared;

    public boolean isStared() {
        return isStared;
    }

    public void setIsStared(boolean isStared) {
        this.isStared = isStared;
    }

    public static String getRequestUrl(int page) {
        return Constants.IOS_REQUEST_URL + "20/" + page;
    }

    public static String getRequestUrl(int count, int page) {
        return Constants.IOS_REQUEST_URL + count+ "/" + page;
    }

    public static ArrayList<IOSContentItem> parseCache(JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }
        ArrayList<IOSContentItem> items = new ArrayList<IOSContentItem>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            IOSContentItem iosContentItem = new IOSContentItem();
            iosContentItem.setIsStared(jsonObject.getBoolean("isStared"));
            iosContentItem.setCreatedAt(jsonObject.getString("createdAt"));
            iosContentItem.setDesc(jsonObject.getString("desc"));
            iosContentItem.setObjectId(jsonObject.getString("objectId"));
            iosContentItem.setPublishedAt(jsonObject.getString("publishedAt"));
            iosContentItem.setType(jsonObject.getString("type"));
            iosContentItem.setWho(jsonObject.getString("who"));
            iosContentItem.setUrl(jsonObject.getString("url"));
            iosContentItem.setUpdatedAt(jsonObject.getString("updatedAt"));
            iosContentItem.setUsed(jsonObject.getBoolean("used"));
            items.add(iosContentItem);
        }
        return items;
    }

}
