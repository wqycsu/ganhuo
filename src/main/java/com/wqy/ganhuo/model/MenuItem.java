package com.wqy.ganhuo.model;

import android.support.v4.app.Fragment;

/**
 * Created by weiquanyun on 15/8/12.
 */
public class MenuItem {

    public static final int ANDROID = 0x01;
    public static final int IOS = 0x02;
    public static final int PICTURE = 0x03;
    public static final int VIDEO = 0x04;

    private String title;
    private int iconResId;
    private int fragmentType;
    private Class<? extends Fragment> fragment;

    public MenuItem(String title, int iconResId, int type, Class fragment){
        this.title = title;
        this.iconResId = iconResId;
        this.fragmentType = type;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public int getFragmentType() {
        return fragmentType;
    }

    public void setFragmentType(int fragmentType) {
        this.fragmentType = fragmentType;
    }

    public Class<? extends Fragment> getFragment() {
        return fragment;
    }

    public void setFragment(Class<? extends Fragment> fragment) {
        this.fragment = fragment;
    }
}
