package com.wqy.ganhuo.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wqy.ganhuo.base.AppApplication;
import com.wqy.ganhuo.greendao.Favorite;
import com.wqy.ganhuo.greendao.FavoriteDao;
import com.wqy.ganhuo.model.AndroidContentItem;
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
     * @param cache 缓存内容:{@link com.alibaba.fastjson.JSON}格式
     * @param page  not work here
     */
    @Override
    public void addCache(String cache, int page) {
        JSONObject json = JSON.parseObject(cache);
        Favorite favoriteCache = new Favorite();
        favoriteCache.setCreatedAt(json.getString("createdAt"));
        favoriteCache.setDesc(json.getString("desc"));
        favoriteCache.setObjectId(json.getString("objectId"));
        favoriteCache.setPlatformType(json.getString("type").equals("Android") ? Constants.PLATFORM_TYPE_ANDROID : Constants.PLATFORM_TYPE_IOS);
        favoriteCache.setPublishedAt(json.getString("publishedAt"));
        favoriteCache.setType(json.getString("type"));
        favoriteCache.setUpdatedAt(json.getString("updateAt"));
        favoriteCache.setUrl(json.getString("url"));
        favoriteCache.setWho(json.getString("who"));
        favoriteCache.setUsed(json.getBoolean("used"));
        favoriteDao.insert(favoriteCache);
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
