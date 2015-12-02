package com.wqy.ganhuo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wqy.ganhuo.model.FragmentPageItem;

import java.util.List;

/**
 * Created by weiquanyun on 15/11/30.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter {

    private List<FragmentPageItem> list;

    public FragmentPageAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    public FragmentPageAdapter(FragmentManager fragmentManager, List<FragmentPageItem> list){
        this(fragmentManager);
        this.list = list;
    }

    public void setPageItems(List<FragmentPageItem> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int i) {
        FragmentPageItem item = list.get(i);
        if(item!=null)
            return item.createFragment(item.getFragment(),item.getArgument());
        else
            return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        FragmentPageItem item = list.get(position);
        if(item!=null)
            return item.getTitle();
        else
            return null;
    }
}
