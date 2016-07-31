package com.wqy.ganhuo.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wqy.ganhuo.model.data.source.GanhuoAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by weiquanyun on 16/6/26.
 */
public class RetrofitUtil {
    private final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();
    private final GanhuoAPI ganhuoService;

    private static volatile RetrofitUtil INSTANCE;

    private RetrofitUtil() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit gankRestAdapter = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ganhuoService = gankRestAdapter.create(GanhuoAPI.class);
    }

    public static RetrofitUtil getInstance() {
        if(INSTANCE == null) {
            synchronized (RetrofitUtil.class) {
                if(INSTANCE == null) {
                    INSTANCE = new RetrofitUtil();
                }
            }
        }
        return INSTANCE;
    }

    public GanhuoAPI getGanhuoService() {
        return ganhuoService;
    }
}
