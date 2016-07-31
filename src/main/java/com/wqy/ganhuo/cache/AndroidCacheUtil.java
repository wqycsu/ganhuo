package com.wqy.ganhuo.cache;

import com.wqy.ganhuo.base.AppApplication;
import com.wqy.ganhuo.greendao.AndroidCache;
import com.wqy.ganhuo.greendao.AndroidCacheDao;
import com.wqy.ganhuo.model.AndroidContentItem;
import com.wqy.ganhuo.model.ContentItem;
import com.wqy.ganhuo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by weiquanyun on 15/11/20.
 */
public class AndroidCacheUtil extends BaseCacheUtil {

    private static volatile AndroidCacheUtil instance;
    private static AndroidCacheDao androidCacheDao;

    private AndroidCacheUtil() {

    }

    public static AndroidCacheUtil getInstance() {
        if (instance == null) {
            synchronized (AndroidCacheUtil.class) {
                if (instance == null) {
                    instance = new AndroidCacheUtil();
                }
            }
            sDaoSession = AppApplication.getDaoSession();
            androidCacheDao = sDaoSession.getAndroidCacheDao();
        }
        return instance;
    }

    @Override
    public void addCache(ContentItem cache, int page) {
        if(cache instanceof AndroidContentItem) {
            AndroidCache androidCache = ((AndroidContentItem) cache).contentImToAndroidCache(page);
            if (androidCache != null) {
                androidCacheDao.insertOrReplace(androidCache);
            }
        }
    }

    @Override
    public void addCache(List<? extends ContentItem> cache, int page) {
        ArrayList<AndroidCache> androidCaches = new ArrayList<>();
        for(ContentItem contentItem : cache) {
            if(contentItem instanceof AndroidContentItem) {
                androidCaches.add(((AndroidContentItem) contentItem).contentImToAndroidCache(page));
            }
        }
        if(androidCaches.size() > 0) {
            androidCacheDao.insertOrReplaceInTx(androidCaches);
        }
    }

    @Override
    public void clearAllCache() {
        androidCacheDao.deleteAll();
    }

    @Override
    public List<AndroidContentItem> getCacheByPage(int page) {
        QueryBuilder<AndroidCache> queryBuilder = androidCacheDao.queryBuilder()
                .offset(Constants.ONE_PAGE_SIZE * (page - 1))
                .limit(Constants.ONE_PAGE_SIZE)
                .orderDesc(AndroidCacheDao.Properties.PublishedAt);
        if (queryBuilder != null && queryBuilder.list().size() > 0) {
            return AndroidContentItem.parseCache(queryBuilder.list());
        }
        return null;
    }
}
