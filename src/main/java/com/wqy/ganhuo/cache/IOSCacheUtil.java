package com.wqy.ganhuo.cache;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.wqy.ganhuo.base.AppApplication;
import com.wqy.ganhuo.greendao.IOSCache;
import com.wqy.ganhuo.greendao.IOSCacheDao;
import com.wqy.ganhuo.model.IOSContentItem;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by weiquanyun on 15/12/2.
 */
public class IOSCacheUtil extends BaseCacheUtil {

    private static volatile IOSCacheUtil instance;
    private static IOSCacheDao iosCacheDao;
    private List<IOSCache> iosCacheList;

    private boolean isFirstQueryPageNum = true;
    private static int mLatestPageNumInDB = 0;

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
    public void addCache(String cache, int page) {
        if(isFirstQueryPageNum) {
            mLatestPageNumInDB = getLatestPageInDB();
            isFirstQueryPageNum = false;
        }
        IOSCache iosCache = new IOSCache();
        iosCache.setResult(cache);
        iosCache.setPage(String.valueOf(page + mLatestPageNumInDB));
        iosCacheDao.insert(iosCache);
    }

    @Override
    public void clearAllCache() {
        iosCacheDao.deleteAll();
    }

    @Override
    public ArrayList getCacheByPage(int page) {
        QueryBuilder<IOSCache> queryBuilder = iosCacheDao.queryBuilder().where(IOSCacheDao.Properties.Page.eq(page));
        if(queryBuilder != null && queryBuilder.list().size() > 0) {
            return IOSContentItem.parseCache(JSON.parseArray(queryBuilder.list().get(0).getResult()));
        }
        return null;
    }

    private int getLatestPageInDB() {
        QueryBuilder<IOSCache> queryBuilder = iosCacheDao.queryBuilder();
        if (queryBuilder != null && queryBuilder.list().size() > 0) {
            iosCacheList = queryBuilder.list();
            String page = iosCacheList.get(iosCacheList.size() - 1).getPage();
            if (TextUtils.isDigitsOnly(page)) {
                return Integer.parseInt(page);
            }
        }
        return 0;
    }
}
