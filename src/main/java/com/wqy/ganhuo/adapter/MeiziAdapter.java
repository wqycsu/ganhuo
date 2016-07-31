package com.wqy.ganhuo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wqy.ganhuo.R;
import com.wqy.ganhuo.model.MeiziItem;
import com.wqy.ganhuo.ui.fragment.MeiziFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by weiquanyun on 16/6/26.
 */
public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private List<MeiziItem> meiziItemList;
    private Random random;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public MeiziAdapter(Context context, List<MeiziItem> meiziItemList) {
        this.context = context;
        this.meiziItemList = meiziItemList;
        this.random = new Random();
    }

    @Override
    public int getItemCount() {
        return meiziItemList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(MeiziAdapter.ViewHolder holder, int position) {
        String url = meiziItemList.get(position).getUrl();
        holder.imageView.setTag(R.id.meizi_image_view, meiziItemList.get(position).getUrl());
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        if(position % 2 == 0) {
            layoutParams.height = 300;
        } else {
            layoutParams.height = 200;
        }
        holder.imageView.setOnClickListener(this);
        holder.imageView.setLayoutParams(layoutParams);
        Glide.with(context)
                .load(url)
                .centerCrop()
                .dontAnimate()
                .into(holder.imageView);
    }

    @Override
    public MeiziAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meizi_item_layout, parent, false);
        return new ViewHolder(view);
    }

    public void setDataList(List<MeiziItem> meiziItemList) {
        if(meiziItemList != null) {
            for(MeiziItem meiziItem : meiziItemList) {
                if(!this.meiziItemList.contains(meiziItem)) {
                    this.meiziItemList.add(meiziItem);
                }
            }
        }
        Collections.sort(this.meiziItemList);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.meizi_image_view)
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener != null) {
            onItemClickListener.onItemClick(v);
        }
    }
}
