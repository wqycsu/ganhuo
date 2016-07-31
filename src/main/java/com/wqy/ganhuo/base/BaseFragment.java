package com.wqy.ganhuo.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wqy.ganhuo.network.RequestManager;
import com.wqy.ganhuo.ui.MainDrawerActivity;
import com.wqy.ganhuo.utils.ShowToast;

/**
 * Created by weiquanyun on 15/8/11.
 */
public abstract class BaseFragment extends Fragment implements MainDrawerActivity.OnRefreshListener{

    protected Toolbar toolbar;
    protected Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
        if (activity instanceof MainDrawerActivity) {
            toolbar = ((MainDrawerActivity) activity).getToolbar();
        } else {
            toolbar = null;
        }
    }

    protected void executeRequest(Request request) {
        RequestManager.addRequest(request, this);
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ShowToast.toastLong(error.getMessage());
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.canAllByTag(this);
    }

    @Override
    public abstract void onRefresh();
}
