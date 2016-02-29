package com.wqy.ganhuo.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by weiquanyun on 15/11/30.
 */
public class FragmentPageItem {
    private final String mTitle;
    private final int mIndicatorColor;
    private final int mDividerColor;
    private final Class mFragment;
    private final Bundle argument;
    private Fragment fragment;

    public FragmentPageItem(String title, int indicatorColor, int dividerColor, Class<? extends Fragment> fragment, Bundle argument){
        this.mTitle = title;
        this.mDividerColor = dividerColor;
        this.mIndicatorColor = indicatorColor;
        this.mFragment = fragment;
        this.argument = argument;
    }

    public Fragment createFragment(Class<? extends Fragment> fragment, Bundle argument){
        if(this.fragment != null) {
            return this.fragment;
        }
        if(argument==null)
            return createFragmentWithNullArg(fragment);
        Fragment f = null;
        try {
            Method method = fragment.getDeclaredMethod("newInstance", Bundle.class);
            method.setAccessible(true);
            try {
                Object object = fragment.newInstance();
                f = (Fragment)method.invoke(object,argument);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.fragment = f;
        return f;
    }

    public int getIndicatorColor(){
        return mIndicatorColor;
    }

    public int getDividerColor(){
        return mDividerColor;
    }

    public String getTitle(){
        return mTitle;
    }

    public Class<Fragment> getFragment(){
        return mFragment;
    }

    public Bundle getArgument(){
        return argument;
    }

    private Fragment createFragmentWithNullArg(Class<? extends Fragment> fragment){
        Fragment f = null;
        try {
            Method method = fragment.getDeclaredMethod("newInstance");
            method.setAccessible(true);
            try {
                Object object = fragment.newInstance();
                f = (Fragment)method.invoke(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.fragment = f;
        return f;
    }

}
