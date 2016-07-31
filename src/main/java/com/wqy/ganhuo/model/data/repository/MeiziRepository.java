package com.wqy.ganhuo.model.data.repository;

import android.support.annotation.NonNull;

import com.wqy.ganhuo.cache.MeiziCacheUtil;
import com.wqy.ganhuo.model.MeiziItem;
import com.wqy.ganhuo.model.data.results.MeiziData;
import com.wqy.ganhuo.model.data.source.local.MeiziLocalDataSource;
import com.wqy.ganhuo.model.data.source.remote.MeiziRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by weiquanyun on 16/6/26.
 */
public class MeiziRepository {

    private static MeiziRepository INSTANCE;
    private MeiziLocalDataSource meiziLocalDataSource;
    private MeiziRemoteDataSource meiziRemoteDataSource;
    private boolean cacheIsValid = false;
    private List<MeiziItem> latestCachedMeiziData;

    private MeiziRepository(@NonNull MeiziRemoteDataSource meiziRemoteDataSource, @NonNull MeiziLocalDataSource meiziLocalDataSource) {
        this.meiziLocalDataSource = meiziLocalDataSource;
        this.meiziRemoteDataSource = meiziRemoteDataSource;
    }

    public static MeiziRepository getInstance(MeiziRemoteDataSource meiziRemoteDataSource, MeiziLocalDataSource meiziLocalDataSource) {
        if(INSTANCE == null) {
            synchronized (MeiziRepository.class) {
                if(INSTANCE == null) {
                    INSTANCE = new MeiziRepository(meiziRemoteDataSource, meiziLocalDataSource);
                }
            }
        }
        return INSTANCE;
    }

    public Observable<MeiziData> getMeizis(boolean forceUpdate, final int page) {

        Observable<MeiziData> localObservable =
                meiziLocalDataSource.getMeizis(page)
                .doOnNext(new Action1<MeiziData>() {
                    @Override
                    public void call(MeiziData meiziData) {
                        if(meiziData != null && (latestCachedMeiziData == null || latestCachedMeiziData.size() > 0)) {
                            latestCachedMeiziData = meiziData.results;
                        }
                        if(meiziData == null || meiziData.results == null || meiziData.results.size() == 0) {
                            cacheIsValid = false;
                        }
                    }
                });
        if(forceUpdate || !cacheIsValid) {
            Observable<MeiziData> remoteObservable =
                    meiziRemoteDataSource.getMeizis(page)
                            .doOnNext(new Action1<MeiziData>() {
                                @Override
                                public void call(MeiziData meiziData) {
                                    if (meiziData != null) {
                                        List<MeiziItem> meiziItemList = meiziData.results;
                                        if (meiziItemList != null) {
                                            MeiziCacheUtil.getInstance().addCache(meiziItemList, page);
                                            checkCacheIsValid(meiziItemList, latestCachedMeiziData);
                                        }
                                    }
                                }
                            });
            return Observable.concat(localObservable, remoteObservable)
                    .first(new Func1<MeiziData, Boolean>() {
                        @Override
                        public Boolean call(MeiziData meiziData) {
                            return meiziData.results != null && meiziData.results.size() > 0;
                        }
                    });
        } else {
            return localObservable;
        }
    }

    private void checkCacheIsValid(List<MeiziItem> meiziItemList, List<MeiziItem> latestCachedMeiziData) {
        if(latestCachedMeiziData != null && latestCachedMeiziData.size() > 0) {
            List<MeiziItem> temp = new ArrayList<MeiziItem>(meiziItemList);
            if(temp.retainAll(latestCachedMeiziData)) {
                cacheIsValid = true;
            }
        }
    }

    public void setCacheIsValid(boolean cacheIsValid) {
        this.cacheIsValid = cacheIsValid;
    }

}
