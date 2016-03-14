package com.wqy.ganhuo.cache;

import com.wqy.ganhuo.greendao.DaoSession;
import com.wqy.ganhuo.model.ContentItem;

import java.util.List;

/**
 * Created by weiquanyun on 15/11/20.
 */
public abstract class BaseCacheUtil implements ICacheUtil{
    public static DaoSession sDaoSession;

    @Override
    public void addCache(List<? extends ContentItem> cache, int page) {
    }

    @Override
    public void addCache(ContentItem cache, int page) {
    }

    @Override
    public void addCache(ContentItem cache) {
    }
}
