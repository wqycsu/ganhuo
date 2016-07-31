package com.wqy.ganhuo.base;

import android.app.Application;
import android.content.Context;

import com.wqy.ganhuo.cache.FavoriteCacheUtil;
import com.wqy.ganhuo.greendao.DaoMaster;
import com.wqy.ganhuo.greendao.DaoSession;
import com.wqy.ganhuo.utils.Constants;

/**
 * Created by weiquanyun on 15/8/10.
 */
public class AppApplication extends Application {

    private static Context context;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        loadFavoriteCache();
    }

    public static Context getAppContext() {
        return context.getApplicationContext();
    }

    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DaoMaster.EncryptedDevOpenHelper helper = new DaoMaster.EncryptedDevOpenHelper(context, Constants.DB_NAME);
            daoMaster = new DaoMaster(helper.getWritableDatabase("123456"));
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
