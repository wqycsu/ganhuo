package com.wqy.ganhuo.model;

import com.wqy.ganhuo.utils.Constants;

/**
 * Created by weiquanyun on 15/8/17.
 */
public class AndroidContentItem extends ContentItem {

    private boolean isStared;

    public boolean isStared() {
        return isStared;
    }

    public void setIsStared(boolean isStared) {
        this.isStared = isStared;
    }

    public static String getRequestUrl(int page) {
        return Constants.ANDROID_REQUEST_URL + "20/" + page;
    }

    public static String getRequestUrl(int count, int page) {
        return Constants.ANDROID_REQUEST_URL + count+ "/" + page;
    }
}
