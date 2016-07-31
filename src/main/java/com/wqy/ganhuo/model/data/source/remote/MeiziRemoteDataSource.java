package com.wqy.ganhuo.model.data.source.remote;

import com.wqy.ganhuo.model.MeiziItem;
import com.wqy.ganhuo.model.data.MeiziDataSource;
import com.wqy.ganhuo.model.data.results.MeiziData;
import com.wqy.ganhuo.utils.RetrofitUtil;

import java.util.List;

import rx.Observable;

/**
 * Created by weiquanyun on 16/6/26.
 */
public class MeiziRemoteDataSource implements MeiziDataSource{

    private static volatile MeiziRemoteDataSource INSTANCE;

    private MeiziRemoteDataSource() {

    }

    public static MeiziRemoteDataSource getInstance() {
        if(INSTANCE == null) {
            synchronized (MeiziRemoteDataSource.class) {
                if(INSTANCE == null) {
                    INSTANCE = new MeiziRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<MeiziData> getMeizis(int page) {
        return RetrofitUtil.getInstance().getGanhuoService().getMeizhiData(page);
    }

    @Override
    public void refreshMeizis() {

    }
}
