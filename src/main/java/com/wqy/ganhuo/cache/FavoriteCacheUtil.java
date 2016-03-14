package com.wqy.ganhuo.cache;

import com.wqy.ganhuo.base.AppApplication;
import com.wqy.ganhuo.greendao.Favorite;
import com.wqy.ganhuo.greendao.FavoriteDao;
import com.wqy.ganhuo.model.AndroidContentItem;
import com.wqy.ganhuo.model.ContentItem;
import com.wqy.ganhuo.model.FavoriteContentItem;
import com.wqy.ganhuo.model.IOSContentItem;
import com.wqy.ganhuo.utils.Constants;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by weiquanyun on 15/12/2.
 */
public class FavoriteCacheUtil extends BaseCacheUtil {

    private static volatile FavoriteCacheUtil instance;
    private static FavoriteDao favoriteDao;
    //收藏的缓存
    private ArrayList<FavoriteContentItem> favoriteContentItems;
    private ArrayList<AndroidContentItem> favoriteAndroidItems = new ArrayList<AndroidContentItem>();
    private ArrayList<IOSContentItem> favoriteIOSItems = new ArrayList<IOSContentItem>();

    private FavoriteCacheUtil() {

    }

    public static FavoriteCacheUtil getInstance() {
        if (instance == null) {
            synchronized (FavoriteCacheUtil.class) {
                if (instance == null) {
                    instance = new FavoriteCacheUtil();
                }
            }
            sDaoSession = AppApplication.getDaoSession();
            favoriteDao = sDaoSession.getFavoriteDao();
        }
        return instance;
    }

    /**
     * @param cache 缓存内容:{@link FavoriteContentItem}格式
     * @param page  not work here
     */
    @Override
    public void addCache(ContentItem cache, int page) {
        Favorite favoriteCache = new Favorite();
        favoriteCache.setCreatedAt(cache.getCreatedAt());
        favoriteCache.setDesc(cache.getDesc());
        favoriteCache.setObjectId(cache.get__id());
        favoriteCache.setPlatformType(cache.getType().equals("Android") ? Constants.PLATFORM_TYPE_ANDROID : Constants.PLATFORM_TYPE_IOS);
        favoriteCache.setPublishedAt(cache.getPublishedAt());
        favoriteCache.setType(cache.getType());
        favoriteCache.setUpdatedAt(cache.getUpdatedAt());
        favoriteCache.setUrl(cache.getUrl());
        favoriteCache.setWho(cache.getWho());
        favoriteCache.setUsed(cache.isUsed());
        favoriteDao.insertOrReplace(favoriteCache);
    }

    @Override
    public void clearAllCache() {
        favoriteDao.deleteAll();
    }

    /**
     * 加载缓存
     * @param page 这里page不需要使用
     * @return 收藏项缓存数据
     */
    @Override
    public ArrayList getCacheByPage(int page) {
        QueryBuilder<Favorite> queryBuilder = favoriteDao.queryBuilder();
        if(queryBuilder != null) {
            favoriteContentItems = FavoriteContentItem.parseCache(queryBuilder.list());
            if(favoriteContentItems != null) {
                for(FavoriteContentItem item : favoriteContentItems) {
                    if(item.getPlatformType() == Constants.PLATFORM_TYPE_ANDROID) {
                        favoriteAndroidItems.add(AndroidContentItem.parseContentItem2AndroidItem(item));
                    } else if(item.getPlatformType() == Constants.PLATFORM_TYPE_IOS) {
                        favoriteIOSItems.add(IOSContentItem.parseContentItem2IOSItem(item));
                    }
                }
            }
            return favoriteContentItems;
        }
        return null;
    }

    public ArrayList<AndroidContentItem> getFavoriteAndroidContentItems() {
        return this.favoriteAndroidItems;
    }

    public ArrayList<IOSContentItem> getFavoriteIOSContentItems() {
        return this.favoriteIOSItems;
    }
}
