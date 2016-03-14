package com.wqy.ganhuo.model;

import com.wqy.ganhuo.greendao.AndroidCache;
import com.wqy.ganhuo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiquanyun on 15/8/17.
 */
public class AndroidContentItem extends ContentItem {

    public static String getRequestUrl(int page) {
        return Constants.ANDROID_REQUEST_URL + "20/" + page;
    }

    public static String getRequestUrl(int count, int page) {
        return Constants.ANDROID_REQUEST_URL + count + "/" + page;
    }

    public static ArrayList<AndroidContentItem> parseCache(List<AndroidCache> androidCacheList) {
        if (androidCacheList == null) {
            return null;
        }
        ArrayList<AndroidContentItem> items = new ArrayList<AndroidContentItem>(androidCacheList.size());
        AndroidCache androidCache;
        for (int i = 0; i < androidCacheList.size(); i++) {
            androidCache = androidCacheList.get(i);
            AndroidContentItem androidContentItem = new AndroidContentItem();
            androidContentItem.setCreatedAt(androidCache.getCreatedAt());
            androidContentItem.setDesc(androidCache.getDesc());
            androidContentItem.set__id(androidCache.getObjectId());
            androidContentItem.setPublishedAt(androidCache.getPublishedAt());
            androidContentItem.setType(androidCache.getType());
            androidContentItem.setWho(androidCache.getWho());
            androidContentItem.setUrl(androidCache.getUrl());
            androidContentItem.setUpdatedAt(androidCache.getUpdatedAt());
            androidContentItem.setUsed(androidCache.getUsed());
            items.add(androidContentItem);
        }
        return items;
    }

    public static AndroidContentItem parseContentItem2AndroidItem(FavoriteContentItem item) {
        AndroidContentItem androidContentItem = new AndroidContentItem();
        androidContentItem.setIsStared(true);
        androidContentItem.setCreatedAt(item.getCreatedAt());
        androidContentItem.setDesc(item.getDesc());
        androidContentItem.set__id(item.get__id());
        androidContentItem.setPublishedAt(item.getPublishedAt());
        androidContentItem.setType(item.getType());
        androidContentItem.setWho(item.getWho());
        androidContentItem.setUrl(item.getUrl());
        androidContentItem.setUpdatedAt(item.getUpdatedAt());
        androidContentItem.setUsed(item.getUsed());
        return androidContentItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AndroidContentItem that = (AndroidContentItem) o;

        return _id.equals(that._id) && url.equals(that.getUrl());

    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + _id.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }
}
