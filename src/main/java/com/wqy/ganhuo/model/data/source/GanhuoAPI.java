package com.wqy.ganhuo.model.data.source;

import com.wqy.ganhuo.model.data.results.MeiziData;
import com.wqy.ganhuo.utils.Constants;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by weiquanyun on 16/6/26.
 */
public interface GanhuoAPI {
    @GET("data/福利/" + Constants.ONE_PAGE_SIZE + "/{page}")
    Observable<MeiziData> getMeizhiData(
            @Path("page") int page);
}
