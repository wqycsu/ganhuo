package com.wqy.ganhuo.contracts.meizi;

import android.content.Context;
import android.support.annotation.NonNull;

import com.wqy.ganhuo.model.MeiziItem;
import com.wqy.ganhuo.model.data.repository.MeiziRepository;
import com.wqy.ganhuo.model.data.results.MeiziData;
import com.wqy.ganhuo.model.data.source.local.MeiziLocalDataSource;
import com.wqy.ganhuo.model.data.source.remote.MeiziRemoteDataSource;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by weiquanyun on 16/6/26.
 */
public class MeiziPresenter implements MeiziContracts.Presenter {

    private MeiziContracts.View view;
    private Context context;
    private CompositeSubscription compositeSubscription;
    private int mPage;

    public MeiziPresenter() {

    }

    public MeiziPresenter(@NonNull MeiziContracts.View view, @NonNull Context context) {
        this.view = view;
        this.context = context;
        this.compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void loadMeizis(boolean forceUpdate, int page) {
        loadMeizis(forceUpdate, true, page);
    }

    private void loadMeizis(boolean forceUpdate, boolean showLoadingIndicator, int page) {
        this.mPage = page;
        if(showLoadingIndicator) {
            view.showLoadingIndicator();
        }
        MeiziRepository.getInstance(
                MeiziRemoteDataSource.getInstance(),
                MeiziLocalDataSource.getInstance()
        ).getMeizis(forceUpdate, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MeiziData>() {
                    @Override
                    public void onCompleted() {
                        finishLoad();
                    }

                    @Override
                    public void onError(Throwable e) {
                        MeiziPresenter.this.onError();
                    }

                    @Override
                    public void onNext(MeiziData meiziData) {
                        view.showMeizis(meiziData.results);
                    }
                });

    }

    @Override
    public void subscribe() {
        loadMeizis(false, false, mPage);
    }

    @Override
    public void unSubscribe() {
        compositeSubscription.clear();
    }

    @Override
    public void finishLoad() {
        if(view == null) {
            throw new RuntimeException("view is null!");
        }
        view.finishLoad();
    }

    @Override
    public void onError() {
        view.showError();
    }
}
