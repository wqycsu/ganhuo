package com.wqy.ganhuo.model.data.source.local;

import com.wqy.ganhuo.cache.MeiziCacheUtil;
import com.wqy.ganhuo.model.MeiziItem;
import com.wqy.ganhuo.model.data.MeiziDataSource;
import com.wqy.ganhuo.model.data.results.MeiziData;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by weiquanyun on 16/6/26.
 */
public class MeiziLocalDataSource implements MeiziDataSource {

    private static volatile MeiziLocalDataSource INSTANCE;

    private MeiziLocalDataSource() {

    }

    public static MeiziLocalDataSource getInstance() {
        if(INSTANCE == null) {
            synchronized (MeiziLocalDataSource.class) {
                if(INSTANCE == null) {
                    INSTANCE =  new MeiziLocalDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<MeiziData> getMeizis(final int page) {
        Observable<MeiziData> observable = Observable.create(new Observable.OnSubscribe<MeiziData>() {
            @Override
            public void call(Subscriber<? super MeiziData> subscriber) {
                List<MeiziItem> meiziItemList = MeiziCacheUtil.getInstance().getCacheByPage(page);
                MeiziData meiziData = new MeiziData();
                meiziData.results = meiziItemList;
                subscriber.onNext(meiziData);
                subscriber.onCompleted();
            }
        });
        return observable;
    }

    @Override
    public void refreshMeizis() {

    }

    public void saveMeizis(List<MeiziItem> list) {

    }
}
