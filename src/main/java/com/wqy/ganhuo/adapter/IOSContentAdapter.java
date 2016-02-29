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
public class IOSContentAdapter extends ContentAdapter {

    public IOSContentAdapter(Context context, ArrayList<IOSContentItem> items){
        super(context, items);
    }
}
