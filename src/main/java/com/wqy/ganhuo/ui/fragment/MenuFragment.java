package com.wqy.ganhuo.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wqy.ganhuo.R;
import com.wqy.ganhuo.base.BaseFragment;
import com.wqy.ganhuo.model.MenuItem;
import com.wqy.ganhuo.ui.MainActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by weiquanyun on 15/8/11.
 */
public class MenuFragment extends BaseFragment{
    RecyclerView menus;
    RelativeLayout setupLayout;

    RecyclerView.LayoutManager layoutManager;
    MenuAdapter adapter;
    int currentFragmentType = MenuItem.ANDROID;
    MainActivity mainActivity;

    ArrayList<MenuItem> menuList = new ArrayList<>();

    public static MenuFragment newInstance(){
        return new MenuFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof MainActivity){
            mainActivity = (MainActivity)activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_menu,container,false);
        layoutManager = new LinearLayoutManager(getActivity());
        menus = ButterKnife.findById(view,R.id.recycler_view_menu);
        setupLayout = ButterKnife.findById(view,R.id.ll_setup);
        menus.setLayoutManager(layoutManager);
        initMenus(menuList);
        adapter = new MenuAdapter(menuList);
        menus.setAdapter(adapter);
        setupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.closeDrawer();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initMenus(ArrayList<MenuItem> list){
        if(list==null){
            list = new ArrayList<>();
        }else{
            list.clear();
        }
        list.add(new MenuItem("Android",R.mipmap.ic_launcher, MenuItem.ANDROID,AndroidFragment.class));
        list.add(new MenuItem("IOS",R.mipmap.ic_launcher, MenuItem.IOS,AndroidFragment.class));
        list.add(new MenuItem("图片",R.mipmap.ic_launcher, MenuItem.PICTURE,AndroidFragment.class));
        list.add(new MenuItem("视频",R.mipmap.ic_launcher, MenuItem.VIDEO,AndroidFragment.class));
    }

    class MenuAdapter extends RecyclerView.Adapter<ViewHolder>{

        ArrayList<MenuItem> menuList;
        public MenuAdapter(ArrayList<MenuItem> list){
            this.menuList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_menu_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final MenuItem item = menuList.get(position);
            holder.imageView.setImageResource(item.getIconResId());
            holder.textView.setText(item.getTitle());
            holder.menuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentFragmentType!=item.getFragmentType()){
                        Fragment fragment = createFragmentForType(item.getFragmentType());
                        if(fragment!=null){
                            mainActivity.replaceFragment(fragment,R.id.container_main);
                        }
                        currentFragmentType = item.getFragmentType();
                    }
                    mainActivity.closeDrawer();
                }
            });
        }

        @Override
        public int getItemCount() {
            return menuList.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.menu_item)
        RelativeLayout menuItem;
        @Bind(R.id.menu_item_icon)
        ImageView imageView;
        @Bind(R.id.menu_item_text)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private Fragment createFragmentForType(int type){
        Fragment fragment = null;
        switch(type){
            case MenuItem.ANDROID:
                fragment = AndroidFragment.newInstance();
                break;
            case MenuItem.IOS:
                fragment = IOSFragment.newInstance();
                break;
            case MenuItem.PICTURE:
                fragment = AndroidFragment.newInstance();
                break;
            case MenuItem.VIDEO:
                fragment = AndroidFragment.newInstance();
                break;
        }
        return fragment;
    }
}
