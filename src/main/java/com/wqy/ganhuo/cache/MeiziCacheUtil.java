package com.wqy.ganhuo.cache;

import com.wqy.ganhuo.base.AppApplication;
import com.wqy.ganhuo.greendao.PictureCache;
import com.wqy.ganhuo.greendao.PictureCacheDao;
import com.wqy.ganhuo.model.ContentItem;
import com.wqy.ganhuo.model.MeiziItem;
import com.wqy.ganhuo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by weiquanyun on 16/6/26.
 */
public class MeiziCacheUtil extends BaseCacheUtil {

    private static volatile MeiziCacheUtil instance;
    private static PictureCacheDao meiziCacheDao;

    private MeiziCacheUtil() {

    }

    public static MeiziCacheUtil getInstance() {
        if (instance == null) {
            synchronized (AndroidCacheUtil.class) {
                if (instance == null) {
                    instance = new MeiziCacheUtil();
                }
            }
            sDaoSession = AppApplication.getDaoSession();
            meiziCacheDao = sDaoSession.getPictureCacheDao();
        }
        return instance;
    }

    @Override
    public void addCache(List<? extends ContentItem> cache, int page) {
        if(cache == null || cache.size() == 0) {
            return;
        }
        List<PictureCache> pictureCaches = new ArrayList<>(cache.size());
        for(ContentItem contentItem : cache) {
            if(contentItem instanceof MeiziItem) {
                pictureCaches.add(((MeiziItem) contentItem).contentImToPictureCache(page));
            }
        }
        meiziCacheDao.insertInTx(pictureCaches);
    }

    @Override
    public void addCache(ContentItem cache, int page) {
        if(cache instanceof MeiziItem) {
            PictureCache meiziChache = ((MeiziItem) cache).contentImToPictureCache(page);
            if (meiziChache != null) {
                meiziCacheDao.insertOrReplace(meiziChache);
            }
        }
    }

    @Override
    public List<MeiziItem> getCacheByPage(int page) {
        QueryBuilder<PictureCache> queryBuilder = meiziCacheDao.queryBuilder()
                .offset(Constants.ONE_PAGE_SIZE * (page - 1))
                .limit(Constants.ONE_PAGE_SIZE)
                .orderDesc(PictureCacheDao.Properties.PublishedAt);
        if (queryBuilder != null && queryBuilder.list().size() > 0) {
            return MeiziItem.parseCache(queryBuilder.list());
        }
        return null;
    }

    @Override
    public void clearAllCache() {
        meiziCacheDao.deleteAll();
    }
}
