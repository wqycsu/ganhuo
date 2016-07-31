package com.wqy.ganhuo.contracts.meizi;

import com.wqy.ganhuo.contracts.BasePresenter;
import com.wqy.ganhuo.contracts.BaseView;
import com.wqy.ganhuo.model.MeiziItem;

import java.util.List;

/**
 * Created by weiquanyun on 16/6/26.
 */
public interface MeiziContracts {

    public interface View extends BaseView<Presenter> {
        void showMeizis(List<MeiziItem> meiziItemList);
        void showError();
        void showLoadingIndicator();
        void finishLoad();
    }

    public interface Presenter extends BasePresenter{
        void loadMeizis(boolean forceUpdate, int mPage);
        void finishLoad();
        void onError();
    }
}
