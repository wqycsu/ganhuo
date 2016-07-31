package com.wqy.ganhuo.model;

import com.wqy.ganhuo.greendao.IOSCache;
import com.wqy.ganhuo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiquanyun on 15/8/30.
 */
public class IOSContentItem extends ContentItem{

    public static String getRequestUrl(int page) {
        return Constants.IOS_REQUEST_URL + "20/" + page;
    }

    public static String getRequestUrl(int count, int page) {
        return Constants.IOS_REQUEST_URL + count+ "/" + page;
    }

    public static ArrayList<IOSContentItem> parseCache(List<IOSCache> iosCacheList) {
        if (iosCacheList == null) {
            return null;
        }
        ArrayList<IOSContentItem> items = new ArrayList<IOSContentItem>(iosCacheList.size());
        IOSCache androidCache;
        for (int i = 0; i < iosCacheList.size(); i++) {
            androidCache = iosCacheList.get(i);
            IOSContentItem iosContentItem = new IOSContentItem();
            iosContentItem.setCreatedAt(androidCache.getCreatedAt());
            iosContentItem.setDesc(androidCache.getDesc());
            iosContentItem.set__id(androidCache.getObjectId());
            iosContentItem.setPublishedAt(androidCache.getPublishedAt());
            iosContentItem.setType(androidCache.getType());
            iosContentItem.setWho(androidCache.getWho());
            iosContentItem.setUrl(androidCache.getUrl());
            iosContentItem.setUpdatedAt(androidCache.getUpdatedAt());
            iosContentItem.setUsed(androidCache.getUsed());
            items.add(iosContentItem);
        }
        return items;
    }

    public static IOSContentItem parseContentItem2IOSItem(FavoriteContentItem item) {
        IOSContentItem androidContentItem = new IOSContentItem();
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

    public IOSCache contentImToIOSCache(int page) {
        IOSCache cache = new IOSCache();
        cache.setCreatedAt(createdAt);
        cache.setDesc(desc);
        cache.setObjectId(_id);
        cache.setPage(page);
        cache.setPublishedAt(publishedAt);
        cache.setUpdatedAt(updatedAt);
        cache.setType(type);
        cache.setPlatformType(Constants.PLATFORM_TYPE_IOS);
        cache.setUrl(url);
        cache.setUsed(used);
        cache.setWho(who);
        return cache;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IOSContentItem that = (IOSContentItem) o;

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
