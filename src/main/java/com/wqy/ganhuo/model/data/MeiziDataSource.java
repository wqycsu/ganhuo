package com.wqy.ganhuo.model.data;

import com.wqy.ganhuo.model.data.results.MeiziData;

import rx.Observable;

/**
 * Created by weiquanyun on 16/6/26.
 */
public interface MeiziDataSource {
    Observable<MeiziData> getMeizis(int page);
    void refreshMeizis();
}
