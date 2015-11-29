package com.wqy.ganhuo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wqy.ganhuo.R;
import com.wqy.ganhuo.model.AndroidContentItem;
import com.wqy.ganhuo.network.NetworkUtil;
import com.wqy.ganhuo.utils.ShowToast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by weiquanyun on 15/8/17.
 */
public class AndroidContentAdapter extends RecyclerView.Adapter<ViewHolder>{

    private ArrayList<AndroidContentItem> items;
    private LayoutInflater inflater;
    private Context context;

    private int page = 1;
    private LoadDataListener listener;
    private OnItemClickListener onItemClickListener;

    public interface LoadDataListener{
        void loadData(int page);
        void loadDataFromDB(int page);
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public AndroidContentAdapter(Context context, ArrayList<AndroidContentItem> items){
        this.items = items;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AndroidContentItem contentItem = items.get(position);
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
                ShowToast.toastLong("收藏按钮点击");
                contentItem.setIsStared(!contentItem.isStared());
                if(contentItem.isStared()){
                    holder.imgStar.setImageResource(R.mipmap.ic_star_red_50);
                }else{
                    holder.imgStar.setImageResource(R.drawable.bg_star_button);
                }
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
            if(listener!=null)
                listener.loadDataFromDB(page);
        }

    }

    public void loadNextPage(){
        page++;
        loadDataByNetworkType();
    }

}

class ViewHolder extends RecyclerView.ViewHolder{

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

    public ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
