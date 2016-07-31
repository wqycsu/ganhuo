package com.wqy.ganhuo.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wqy.ganhuo.R;
import com.wqy.ganhuo.adapter.MeiziAdapter;
import com.wqy.ganhuo.animators.SlideBottomInWithAlphaAnimator;
import com.wqy.ganhuo.base.BaseFragment;
import com.wqy.ganhuo.contracts.meizi.MeiziContracts;
import com.wqy.ganhuo.contracts.meizi.MeiziPresenter;
import com.wqy.ganhuo.model.MeiziItem;
import com.wqy.ganhuo.ui.MainDrawerActivity;
import com.wqy.ganhuo.ui.MeiziDetailActivity;
import com.wqy.ganhuo.ui.views.MaterialCircleProgressBar;
import com.wqy.ganhuo.utils.ShowToast;
import com.wqy.ganhuo.view.AutoLoadRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by weiquanyun on 16/6/26.
 */
public class MeiziFragment extends BaseFragment implements MeiziContracts.View, MeiziAdapter.OnItemClickListener{

    private MeiziContracts.Presenter presenter;
    private Activity activity;

    @Bind(R.id.meizi_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.meizi_recycler_view)
    AutoLoadRecyclerView recyclerView;
    @Bind(R.id.meizi_progressbar)
    MaterialCircleProgressBar circleProgressBar;
    private CoordinatorLayout mainContent;

    private MeiziAdapter meiziAdapter;
    private List<MeiziItem> meiziItemList = new ArrayList<>();

    private int mPage;

    public MeiziFragment() {

    }

    public static MeiziFragment newInstance(Bundle arguments) {
        MeiziFragment meiziFragment = new MeiziFragment();
        meiziFragment.setArguments(arguments);
        return meiziFragment;
    }

    public static MeiziFragment newInstance() {
        return new MeiziFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = 1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meizi, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new SlideBottomInWithAlphaAnimator());
        recyclerView.setHasFixedSize(true);
        circleProgressBar.show();
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MeiziFragment.this.onRefresh();
            }
        });
        swipeRefreshLayout.setRefreshing(true);
        initData();
        return view;
    }

    private void initData() {
        meiziAdapter = new MeiziAdapter(getActivity(), meiziItemList);
        recyclerView.setAdapter(meiziAdapter);
        meiziAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (toolbar == null) {
            throw new RuntimeException("AndroidFragment需要attach到MainActivity中！");
        }
        if(activity instanceof MainDrawerActivity) {
            ((MainDrawerActivity) activity).setOnRefreshListener(this);
            mainContent = ((MainDrawerActivity) activity).getMainContent();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(presenter == null) {
            presenter = new MeiziPresenter(this, getActivity());
        }
        if(meiziItemList.size() > 0) {
            meiziAdapter.notifyDataSetChanged();
            finishLoad();
        } else {
            presenter.loadMeizis(true, mPage);
        }
        Glide.with(getActivity()).resumeRequests();
    }

    @Override
    public void onPause() {
        super.onPause();
        Glide.with(getActivity()).pauseRequests();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        presenter.loadMeizis(true, 1);
    }

    @Override
    public void showMeizis(List<MeiziItem> meiziItemList) {
        if(meiziItemList == null) {
            return;
        }
        meiziAdapter.setDataList(meiziItemList);
    }

    @Override
    public void showError() {
        circleProgressBar.hide();
        swipeRefreshLayout.setRefreshing(false);
        ShowToast.toastLong("加载失败了!");
    }

    @Override
    public void showLoadingIndicator() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void finishLoad() {
        circleProgressBar.hide();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setPresenter(MeiziContracts.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onItemClick(View view) {
        String url = (String) view.getTag(R.id.meizi_image_view);
        if(!TextUtils.isEmpty(url)) {
            Intent intent = MeiziDetailActivity.newIntent(getActivity(), url);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    new Pair<View, String>(view, MeiziDetailActivity.TRANSITION_NAME));
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    }
}
