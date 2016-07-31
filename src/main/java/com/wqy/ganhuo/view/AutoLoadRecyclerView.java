package com.wqy.ganhuo.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.wqy.ganhuo.interfaces.LoadFinishCallback;

/**
 * Created by weiquanyun on 15/8/17.
 */
public class AutoLoadRecyclerView extends RecyclerView implements LoadFinishCallback {

    private boolean isLoadingMore;
    private LoadMoreListener loadMoreListener;
    private Context context;

    public interface LoadMoreListener {
        void onLoadMore();
    }

    public AutoLoadRecyclerView(Context context) {
        this(context, null);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        isLoadingMore = false;
        this.context = context;
        addOnScrollListener(new AutoLoadScrollListener(true, true));
    }

    public void setLoadMoreListener(LoadMoreListener listener) {
        this.loadMoreListener = listener;
    }

    public void setOnPauseListenerParams(boolean pauseOnScroll, boolean pauseOnFling) {
        addOnScrollListener(new AutoLoadScrollListener(pauseOnScroll, pauseOnFling));
    }

    class AutoLoadScrollListener extends OnScrollListener {

        private boolean pauseOnScroll;
        private boolean pauseOnFling;

        public AutoLoadScrollListener(boolean pauseOnScroll, boolean pauseOnFling) {
            this.pauseOnScroll = pauseOnScroll;
            this.pauseOnFling = pauseOnFling;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case SCROLL_STATE_DRAGGING:
                    if (pauseOnScroll) {
                        Glide.with(context).pauseRequests();
                    } else {
                        Glide.with(context).resumeRequests();
                    }
                    break;
                case SCROLL_STATE_IDLE:
                    Glide.with(context).resumeRequests();
                    break;
                case SCROLL_STATE_SETTLING:
                    if (pauseOnScroll) {
                        Glide.with(context).pauseRequests();
                    } else {
                        Glide.with(context).resumeRequests();
                    }
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (getLayoutManager() instanceof LinearLayoutManager) {
                int lastVisibleItem = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                int totalItem = AutoLoadRecyclerView.this.getAdapter().getItemCount();
                if (loadMoreListener != null && !isLoadingMore && lastVisibleItem > totalItem - 2 && dy > 0) {
                    loadMoreListener.onLoadMore();
                    isLoadingMore = true;
                }
            }

        }
    }

    @Override
    public void onLoadFinish() {
        isLoadingMore = false;
    }
}
