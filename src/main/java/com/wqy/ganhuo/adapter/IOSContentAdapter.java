package com.wqy.ganhuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.wqy.ganhuo.R;
import com.wqy.ganhuo.cache.FavoriteCacheUtil;
import com.wqy.ganhuo.model.IOSContentItem;
import com.wqy.ganhuo.network.NetworkUtil;
import com.wqy.ganhuo.utils.ShowToast;

import java.util.ArrayList;

/**
 * Created by weiquanyun on 15/8/30.
 */
public class IOSContentAdapter extends RecyclerView.Adapter<ViewHolder>{
    private ArrayList<IOSContentItem> items;
    private LayoutInflater inflater;
    private Context context;

    private int page = 1;
    private LoadDataListener listener;
    private OnItemClickListener onItemClickListener;

    public interface LoadDataListener{
        void loadData(int page);
        void loadCache(int page);
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public IOSContentAdapter(Context context, ArrayList<IOSContentItem> items){
        this.items = items;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final IOSContentItem contentItem = items.get(position);
        holder.desc.setText(contentItem.getDesc());
        holder.who.setText(String.format(context.getResources().getString(R.string.share_from), contentItem.getWho()));
        holder.publishAt.setText(contentItem.getPublishedAt().substring(0, 10));
        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowToast.toastLong("分享按钮点击");
            }
        });

        holder.imgStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentItem.setIsStared(!contentItem.isStared());
                FavoriteCacheUtil.getInstance().addCache(JSON.toJSONString(contentItem), 0);
                ShowToast.toastLong("收藏按钮点击:"+contentItem.isStared());
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)
                    onItemClickListener.onItemClick(v, position);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener!=null)
                    onItemClickListener.onItemLongClick(v, position);
                return true;
            }
        });

        if(contentItem.isStared()){
            holder.imgStar.setImageResource(R.mipmap.ic_star_red_50);
        }else{
            holder.imgStar.setImageResource(R.drawable.bg_star_button);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.android_content_item, parent, false);
        return new ViewHolder(view);
    }

    public void setLoadDataListener(LoadDataListener loadDataListener){
        this.listener = loadDataListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public void loadFirst(){
        page = 1;
        loadDataByNetworkType();
    }

    /**
     * 根据不同的网络状态选择不同的加载策略
     */
    private void loadDataByNetworkType() {

        if (NetworkUtil.isNetConnected(context)) {
            if(listener!=null)
                listener.loadData(page);
        } else {
            if(listener != null) {
                listener.loadCache(page);
            }
        }

    }

    public void loadNextPage(){
        page++;
        loadDataByNetworkType();
    }
}
