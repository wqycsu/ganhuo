package com.wqy.ganhuo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wqy.ganhuo.R;
import com.wqy.ganhuo.ui.views.MaterialCircleProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by weiquanyun on 16/2/28.
 */
public class FooterViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.load_more_progress_bar)
    MaterialCircleProgressBar materialCircleProgressBar;

    public FooterViewHolder(View itemView) {
        super(itemView);
        materialCircleProgressBar = ButterKnife.findById(itemView, R.id.load_more_progress_bar);
    }
}
