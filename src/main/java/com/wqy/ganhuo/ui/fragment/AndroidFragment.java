package com.wqy.ganhuo.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wqy.ganhuo.R;
import com.wqy.ganhuo.adapter.AndroidContentAdapter;
import com.wqy.ganhuo.animators.SlideBottomInWithAlphaAnimator;
import com.wqy.ganhuo.base.BaseFragment;
import com.wqy.ganhuo.cache.AndroidCacheUtil;
import com.wqy.ganhuo.interfaces.LoadFinishCallback;
import com.wqy.ganhuo.model.AndroidContentItem;
import com.wqy.ganhuo.model.IOSContentItem;
import com.wqy.ganhuo.network.RequestForAndroid;
import com.wqy.ganhuo.ui.AndroidContentDetailActivity;
import com.wqy.ganhuo.ui.MainDrawerActivity;
import com.wqy.ganhuo.ui.views.MaterialCircleProgressBar;
import com.wqy.ganhuo.utils.Constants;
import com.wqy.ganhuo.utils.JSONParserUtil;
import com.wqy.ganhuo.utils.ShowToast;
import com.wqy.ganhuo.view.AutoLoadRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AndroidFragment extends BaseFragment implements AndroidContentAdapter.LoadDataListener, AndroidContentAdapter.OnItemClickListener, MainDrawerActivity.OnRefreshListener {

    @Bind(R.id.android_recycler_view)
    AutoLoadRecyclerView recyclerView;
    @Bind(R.id.android_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progressbar)
    MaterialCircleProgressBar contentLoadingProgressBar;

    private ImageLoader imageLoader;
    private ArrayList<AndroidContentItem> items = new ArrayList<>();
    private AndroidContentAdapter adapter;
    private LoadFinishCallback mLoadFinishCallback;

    public AndroidFragment() {
        // Required empty public constructor
    }

    public static AndroidFragment newInstance() {
        return new AndroidFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (toolbar == null) {
            throw new RuntimeException("AndroidFragment需要attach到MainActivity中！");
        }
        if(activity instanceof MainDrawerActivity) {
            ((MainDrawerActivity) activity).setOnRefreshListener(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_android, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new SlideBottomInWithAlphaAnimator());
        recyclerView.setHasFixedSize(true);
        contentLoadingProgressBar.show();
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (adapter != null)
                    adapter.onRefresh();
            }
        });
        recyclerView.setLoadMoreListener(new AutoLoadRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.d(Constants.TAG, "load more");
                adapter.loadNextPage();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new AndroidContentAdapter(getActivity(), items);
        recyclerView.setAdapter(adapter);
        imageLoader = ImageLoader.getInstance();
        recyclerView.setOnPauseListenerParams(imageLoader, true, true);
        mLoadFinishCallback = recyclerView;
        adapter.setLoadDataListener(this);
        adapter.setOnItemClickListener(this);
        adapter.loadFirst();
    }

    @Override
    public void loadDataFromNet(final int page) {
        executeRequest(new RequestForAndroid(AndroidContentItem.getRequestUrl(page), new Response.Listener<ArrayList<AndroidContentItem>>() {
            @Override
            public void onResponse(ArrayList<AndroidContentItem> response) {
                addAndRemoveRepeatedData(response);
                contentLoadingProgressBar.hide();
                mLoadFinishCallback.onLoadFinish();
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
                AndroidCacheUtil.getInstance().addCache(response, page);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                contentLoadingProgressBar.hide();
                ShowToast.toastLong("加载出错了:" + error.getMessage());
                mLoadFinishCallback.onLoadFinish();
            }
        }));
    }

    @Override
    public void loadDataFromDB(int page) {
        ArrayList<AndroidContentItem> cacheData = AndroidCacheUtil.getInstance().getCacheByPage(page);
        if(cacheData != null) {
            items.addAll(cacheData);
        }
        loadDataFromNet(page);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (items == null)
            return;
        AndroidContentItem androidContentItem = items.get(position);
        if (androidContentItem == null)
            return;
        Intent intent = new Intent(getActivity(), AndroidContentDetailActivity.class);
        intent.putExtra("url",androidContentItem.getUrl());
        intent.putExtra("desc",androidContentItem.getDesc());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onRefresh() {
        if(adapter != null) {
            swipeRefreshLayout.setRefreshing(true);
            adapter.loadFirst();
        }
    }

    private void addAndRemoveRepeatedData(ArrayList<AndroidContentItem> response) {
        HashSet<AndroidContentItem> set = new HashSet<AndroidContentItem>(items);
        set.addAll(response);
        items.clear();
        items.addAll(set);
        Collections.sort(items);
    }
}
