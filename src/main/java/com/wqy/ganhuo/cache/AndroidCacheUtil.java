package com.wqy.ganhuo.cache;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wqy.ganhuo.base.AppApplication;
import com.wqy.ganhuo.greendao.AndroidCache;
import com.wqy.ganhuo.greendao.AndroidCacheDao;
import com.wqy.ganhuo.model.AndroidContentItem;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by weiquanyun on 15/11/20.
 */
public class AndroidCacheUtil extends BaseCacheUtil {

    private static volatile AndroidCacheUtil instance;
    private static AndroidCacheDao androidCacheDao;

    private List<AndroidCache> androidCacheList;
    private boolean isFirstQueryPageNum = true;
    private static int mLatestPageNumInDB = 0;

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
    public void addCache(String cache, int page) {
        if(isFirstQueryPageNum) {
            mLatestPageNumInDB = getLatestPageInDB();
            isFirstQueryPageNum = false;
        }
        AndroidCache androidCache = new AndroidCache();
        androidCache.setResult(cache);
        androidCache.setPage(String.valueOf(page + mLatestPageNumInDB));
        if (androidCacheList == null || !androidCacheList.contains(androidCache)) {
            androidCacheDao.insert(androidCache);
        }
    }

    @Override
    public void clearAllCache() {
        androidCacheDao.deleteAll();
    }

    @Override
    public ArrayList getCacheByPage(int page) {
        QueryBuilder<AndroidCache> queryBuilder = androidCacheDao.queryBuilder().where(AndroidCacheDao.Properties.Page.eq(String.valueOf(page)));
        if (queryBuilder != null && queryBuilder.list().size() > 0) {
            return AndroidContentItem.parseCache(JSON.parseArray(queryBuilder.list().get(0).getResult()));
        }
        return null;
    }

    private int getLatestPageInDB() {
        QueryBuilder<AndroidCache> queryBuilder = androidCacheDao.queryBuilder();
        if (queryBuilder != null && queryBuilder.list().size() > 0) {
            androidCacheList = queryBuilder.list();
            String page = androidCacheList.get(androidCacheList.size() - 1).getPage();
            if (TextUtils.isDigitsOnly(page)) {
                return Integer.parseInt(page);
            }
        }
        return 0;
    }
}
