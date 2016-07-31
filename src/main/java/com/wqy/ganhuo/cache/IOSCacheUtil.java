package com.wqy.ganhuo.cache;

import com.wqy.ganhuo.base.AppApplication;
import com.wqy.ganhuo.greendao.IOSCache;
import com.wqy.ganhuo.greendao.IOSCacheDao;
import com.wqy.ganhuo.model.ContentItem;
import com.wqy.ganhuo.model.IOSContentItem;
import com.wqy.ganhuo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by weiquanyun on 15/12/2.
 */
public class IOSCacheUtil extends BaseCacheUtil {

    private static volatile IOSCacheUtil instance;
    private static IOSCacheDao iosCacheDao;

    private IOSCacheUtil() {

    }

    public static IOSCacheUtil getInstance() {
        if(instance == null) {
            synchronized (IOSCacheUtil.class) {
                if(instance == null) {
                    instance = new IOSCacheUtil();
                }
            }
            sDaoSession = AppApplication.getDaoSession();
            iosCacheDao = sDaoSession.getIOSCacheDao();
        }
        return instance;
    }

    @Override
    public void addCache(ContentItem cache, int page) {
        if(cache instanceof IOSContentItem) {
            IOSCache androidCache = ((IOSContentItem) cache).contentImToIOSCache(page);
            if (androidCache != null) {
                iosCacheDao.insertOrReplace(androidCache);
            }
        }
    }

    @Override
    public void addCache(List<? extends ContentItem> cache, int page) {
        ArrayList<IOSCache> androidCaches = new ArrayList<>();
        for(ContentItem contentItem : cache) {
            if(contentItem instanceof IOSContentItem) {
                androidCaches.add(((IOSContentItem) contentItem).contentImToIOSCache(page));
            }
        }
        if(androidCaches.size() > 0) {
            iosCacheDao.insertOrReplaceInTx(androidCaches);
        }
    }

    @Override
    public void clearAllCache() {
        iosCacheDao.deleteAll();
    }

    @Override
    public List<IOSContentItem> getCacheByPage(int page) {
        QueryBuilder<IOSCache> queryBuilder = iosCacheDao.queryBuilder()
                .offset(Constants.ONE_PAGE_SIZE * (page - 1))
                .limit(Constants.ONE_PAGE_SIZE)
                .orderDesc(IOSCacheDao.Properties.PublishedAt);
        if (queryBuilder != null && queryBuilder.list().size() > 0) {
            return IOSContentItem.parseCache(queryBuilder.list());
        }
        return null;
    }
}
