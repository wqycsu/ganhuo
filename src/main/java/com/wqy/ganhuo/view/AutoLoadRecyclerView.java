package com.wqy.ganhuo.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wqy.ganhuo.interfaces.LoadFinishCallback;
import com.wqy.ganhuo.utils.Constants;

/**
 * Created by weiquanyun on 15/8/17.
 */
public class AutoLoadRecyclerView extends RecyclerView implements LoadFinishCallback {

    private boolean isLoadingMore;
    private LoadMoreListener loadMoreListener;

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
        //ImageLoader为空表示不加载图片
        addOnScrollListener(new AutoLoadScrollListener(null, true, true));
    }

    public void setLoadMoreListener(LoadMoreListener listener) {
        this.loadMoreListener = listener;
    }

    public void setOnPauseListenerParams(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
        addOnScrollListener(new AutoLoadScrollListener(imageLoader, pauseOnScroll, pauseOnFling));
    }

    class AutoLoadScrollListener extends OnScrollListener {

        private ImageLoader imageLoader;
        private boolean pauseOnScroll;
        private boolean pauseOnFling;

        public AutoLoadScrollListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
            this.imageLoader = imageLoader;
            this.pauseOnScroll = pauseOnScroll;
            this.pauseOnFling = pauseOnFling;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (imageLoader != null)
                switch (newState) {
                    case SCROLL_STATE_DRAGGING:
                        if (pauseOnScroll) {
                            imageLoader.pause();
                        } else {
                            imageLoader.resume();
                        }
                        break;
                    case SCROLL_STATE_IDLE:
                        imageLoader.resume();
                        break;
                    case SCROLL_STATE_SETTLING:
                        if (pauseOnFling) {
                            imageLoader.pause();
                        } else {
                            imageLoader.resume();
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
