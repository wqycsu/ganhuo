package com.wqy.ganhuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.wqy.ganhuo.R;
import com.wqy.ganhuo.cache.FavoriteCacheUtil;
import com.wqy.ganhuo.model.ContentItem;
import com.wqy.ganhuo.network.NetworkUtil;
import com.wqy.ganhuo.utils.ShowToast;

import java.util.ArrayList;

/**
 * Created by weiquanyun on 16/2/29.
 */
public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int FOOTER_COUNT = 1;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_FOOTER = VIEW_TYPE_ITEM + 1;
    private ArrayList<? extends ContentItem> items;
    private LayoutInflater inflater;
    private Context context;

    private int page = 1;
    private LoadDataListener listener;
    private OnItemClickListener onItemClickListener;

    public interface LoadDataListener {
        void loadDataFromNet(int page);

        void loadDataFromDB(int page);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public ContentAdapter() {

    }

    public ContentAdapter(Context context, ArrayList<? extends ContentItem> items) {
        this.items = items;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return items.size() + FOOTER_COUNT;
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).materialCircleProgressBar.stopProgress();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return VIEW_TYPE_FOOTER;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            final int clickPosition = position;
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final ContentItem contentItem = items.get(position);
            itemViewHolder.desc.setText(contentItem.getDesc());
            itemViewHolder.who.setText(String.format(context.getResources().getString(R.string.share_from), contentItem.getWho()));
            itemViewHolder.publishAt.setText(contentItem.getPublishedAt().substring(0, 10));
            itemViewHolder.imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowToast.toastLong("分享按钮点击");
                }
            });

            itemViewHolder.imgStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowToast.toastLong("收藏按钮点击");
                    FavoriteCacheUtil.getInstance().addCache(contentItem);
                    contentItem.setIsStared(!contentItem.isStared());
                    if (contentItem.isStared()) {
                        itemViewHolder.imgStar.setImageResource(R.mipmap.ic_star_red_50);
                    } else {
                        itemViewHolder.imgStar.setImageResource(R.drawable.bg_star_button);
                    }
                }
            });

            itemViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(v, clickPosition);
                }
            });

            itemViewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemLongClick(v, clickPosition);
                    return true;
                }
            });

            if (FavoriteCacheUtil.getInstance().getFavoriteAndroidContentItems().contains(contentItem)) {
                itemViewHolder.imgStar.setImageResource(R.mipmap.ic_star_red_50);
            } else {
                itemViewHolder.imgStar.setImageResource(R.drawable.bg_star_button);
            }
        } else if (holder instanceof FooterViewHolder) {
            if (items.size() <= 0) {
                holder.itemView.setVisibility(View.GONE);
            } else {
                holder.itemView.setVisibility(View.VISIBLE);
            }
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.materialCircleProgressBar.startProgress();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.android_content_item, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.recycler_view_footerview, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    public void setLoadDataListener(LoadDataListener loadDataListener) {
        this.listener = loadDataListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void loadFirst() {
        page = 1;
        if (listener != null) {
            listener.loadDataFromDB(page);
        }
    }

    public void onRefresh() {
        page = 1;
        if(listener != null) {
            listener.loadDataFromNet(page);
        }
    }

    /**
     * 根据不同的网络状态选择不同的加载策略
     */
    private void loadDataByNetworkType() {

        if (NetworkUtil.isNetConnected(context)) {
            if(listener!=null)
                listener.loadDataFromNet(page);
        } else {
            if(listener != null) {
                listener.loadDataFromDB(page);
            }
        }

    }

    public void loadNextPage() {
        page++;
        loadDataByNetworkType();
    }
}
