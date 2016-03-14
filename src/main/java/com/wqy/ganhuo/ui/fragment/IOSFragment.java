package com.wqy.ganhuo.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wqy.ganhuo.R;
import com.wqy.ganhuo.adapter.IOSContentAdapter;
import com.wqy.ganhuo.base.BaseFragment;
import com.wqy.ganhuo.cache.IOSCacheUtil;
import com.wqy.ganhuo.interfaces.LoadFinishCallback;
import com.wqy.ganhuo.model.AndroidContentItem;
import com.wqy.ganhuo.model.IOSContentItem;
import com.wqy.ganhuo.network.RequestForIOS;
import com.wqy.ganhuo.ui.AndroidContentDetailActivity;
import com.wqy.ganhuo.ui.MainDrawerActivity;
import com.wqy.ganhuo.ui.views.MaterialCircleProgressBar;
import com.wqy.ganhuo.utils.JSONParserUtil;
import com.wqy.ganhuo.utils.ShowToast;
import com.wqy.ganhuo.view.AutoLoadRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IOSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IOSFragment extends BaseFragment implements IOSContentAdapter.LoadDataListener, IOSContentAdapter.OnItemClickListener{

    @Bind(R.id.ios_swipe_refresh_layout)
    SwipeRefreshLayout mSwipRefreshLayout;
    @Bind(R.id.ios_recycler_view)
    AutoLoadRecyclerView mRecyclerView;
    @Bind(R.id.progressbar)
    MaterialCircleProgressBar mProgressBar;

    private ArrayList<IOSContentItem> items = new ArrayList<>();
    private IOSContentAdapter adapter;
    private LoadFinishCallback mLoadFinishCallback;

    public static IOSFragment newInstance() {
        IOSFragment fragment = new IOSFragment();
        return fragment;
    }

    public IOSFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (toolbar == null) {
            throw new RuntimeException("IOSFragment需要attach到MainActivity中！");
        }
        if(activity instanceof MainDrawerActivity) {
            ((MainDrawerActivity) activity).setOnRefreshListener(this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ios, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLoadMoreListener(new AutoLoadRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(adapter!=null)
                    adapter.loadNextPage();
            }
        });
        mSwipRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(adapter!=null)
                    adapter.onRefresh();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new IOSContentAdapter(getActivity(), items);
        mRecyclerView.setAdapter(adapter);
        mLoadFinishCallback = mRecyclerView;
        mRecyclerView.setOnPauseListenerParams(null,true,true);

        adapter.setOnItemClickListener(this);
        adapter.setLoadDataListener(this);
        adapter.loadFirst();
    }

    @Override
    public void loadDataFromNet(final int page) {
        RequestForIOS requestForIOS = new RequestForIOS(IOSContentItem.getRequestUrl(1),
                new Response.Listener<ArrayList<IOSContentItem>>() {
                    @Override
                    public void onResponse(ArrayList<IOSContentItem> response) {
                        addAndRemoveRepeatedData(response);
                        mProgressBar.hide();
                        adapter.notifyDataSetChanged();
                        mSwipRefreshLayout.setRefreshing(false);
                        //load more
                        mLoadFinishCallback.onLoadFinish();
                        IOSCacheUtil.getInstance().addCache(response, page);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressBar.setVisibility(View.GONE);
                        ShowToast.toastLong(error.getMessage());
                        //load more
                        mLoadFinishCallback.onLoadFinish();
                    }
                });
        requestForIOS.setRetryPolicy(new DefaultRetryPolicy());
        executeRequest(requestForIOS);
    }

    @Override
    public void loadDataFromDB(int page) {
        ArrayList<IOSContentItem> cache = IOSCacheUtil.getInstance().getCacheByPage(page);
        if(cache != null) {
            items.addAll(cache);
        }
        loadDataFromNet(page);
    }

    @Override
    public void onItemClick(View view, int position) {
        if(items==null){
            return;
        }
        IOSContentItem contentItem = items.get(position);
        if(contentItem==null){
            return;
        }
        Intent intent = new Intent(getActivity(), AndroidContentDetailActivity.class);
        intent.putExtra("url",contentItem.getUrl());
        intent.putExtra("desc",contentItem.getDesc());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onRefresh() {
        if(adapter != null) {
            mSwipRefreshLayout.setRefreshing(true);
            adapter.loadFirst();
        }
    }

    private void addAndRemoveRepeatedData(ArrayList<IOSContentItem> response) {
        HashSet<IOSContentItem> set = new HashSet<IOSContentItem>(items);
        set.addAll(response);
        items.clear();
        items.addAll(set);
        Collections.sort(items);
    }
}
