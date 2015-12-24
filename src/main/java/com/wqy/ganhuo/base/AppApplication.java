package com.wqy.ganhuo.base;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wqy.ganhuo.cache.FavoriteCacheUtil;
import com.wqy.ganhuo.greendao.DaoMaster;
import com.wqy.ganhuo.greendao.DaoSession;
import com.wqy.ganhuo.utils.Constants;

/**
 * Created by weiquanyun on 15/8/10.
 */
public class AppApplication extends Application {

    public static final int MEMORY_CACHE_SIZE = 4 * 1024 * 1024;//4Mb
    private static Context context;
    ImageLoader mImageLoader;
    ImageLoaderConfiguration configuration;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        context = this;
        loadFavoriteCache();
    }

    public static Context getAppContext() {
        return context.getApplicationContext();
    }

    /**
     * 初始化Universal Image Loader
     */
    private void initImageLoader() {
        configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCache(new LruMemoryCache(MEMORY_CACHE_SIZE))
                .build();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(configuration);
    }

    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession() {
        if(daoSession==null){
            if(daoMaster==null)
                daoMaster = getDaoMaster();
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /**
     * 为了在Android和IOS界面展示收藏项目,这里使用同步方法,待优化
     */
    private void loadFavoriteCache() {
        FavoriteCacheUtil.getInstance().getCacheByPage(0);
    }
}
