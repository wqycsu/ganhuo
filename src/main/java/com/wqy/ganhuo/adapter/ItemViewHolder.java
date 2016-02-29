package com.wqy.ganhuo.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wqy.ganhuo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by weiquanyun on 15/11/29.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder{

    @Bind(R.id.android_card)
    CardView cardView;
    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.who)
    TextView who;
    @Bind(R.id.publish_at)
    TextView publishAt;
    @Bind(R.id.img_share)
    ImageView imgShare;
    @Bind(R.id.img_star)
    ImageView imgStar;

    public ItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
