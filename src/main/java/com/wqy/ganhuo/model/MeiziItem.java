package com.wqy.ganhuo.model;

import com.wqy.ganhuo.greendao.PictureCache;
import com.wqy.ganhuo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiquanyun on 16/6/26.
 */
public class MeiziItem extends ContentItem {

    public PictureCache contentImToPictureCache(int page) {
        PictureCache pictureCache = new PictureCache();
        pictureCache.setCreatedAt(createdAt);
        pictureCache.setDesc(desc);
        pictureCache.setObjectId(_id);
        pictureCache.setPage(page);
        pictureCache.setPlatformType(Constants.PLATFORM_TYPE_ANDROID);
        pictureCache.setPublishedAt(publishedAt);
        pictureCache.setType(type);
        pictureCache.setUrl(url);
        pictureCache.setUsed(used);
        pictureCache.setWho(who);
        return pictureCache;
    }

    public static List<MeiziItem> parseCache(List<PictureCache> meiziCacheList) {
        if (meiziCacheList == null) {
            return new ArrayList<MeiziItem>(0);
        }
        ArrayList<MeiziItem> items = new ArrayList<MeiziItem>(meiziCacheList.size());
        PictureCache meiziCache;
        for (int i = 0; i < meiziCacheList.size(); i++) {
            meiziCache = meiziCacheList.get(i);
            MeiziItem meiziItem = new MeiziItem();
            meiziItem.setCreatedAt(meiziCache.getCreatedAt());
            meiziItem.setDesc(meiziCache.getDesc());
            meiziItem.set__id(meiziCache.getObjectId());
            meiziItem.setPublishedAt(meiziCache.getPublishedAt());
            meiziItem.setType(meiziCache.getType());
            meiziItem.setWho(meiziCache.getWho());
            meiziItem.setUrl(meiziCache.getUrl());
            meiziItem.setUpdatedAt(meiziCache.getUpdatedAt());
            meiziItem.setUsed(meiziCache.getUsed());
            items.add(meiziItem);
        }
        return items;
    }

    public static MeiziItem parseContentItem2MeiziItem(FavoriteContentItem item) {
        MeiziItem meiziItem = new MeiziItem();
        meiziItem.setIsStared(true);
        meiziItem.setCreatedAt(item.getCreatedAt());
        meiziItem.setDesc(item.getDesc());
        meiziItem.set__id(item.get__id());
        meiziItem.setPublishedAt(item.getPublishedAt());
        meiziItem.setType(item.getType());
        meiziItem.setWho(item.getWho());
        meiziItem.setUrl(item.getUrl());
        meiziItem.setUpdatedAt(item.getUpdatedAt());
        meiziItem.setUsed(item.getUsed());
        return meiziItem;
    }
}
