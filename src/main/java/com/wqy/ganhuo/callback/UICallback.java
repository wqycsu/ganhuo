package com.wqy.ganhuo.callback;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by weiquanyun on 15/11/28.
 * 这个callback的回调在UI线程
 */
public abstract class UICallback implements GanHuoCallback{
    private Handler mUIHandler = new Handler(Looper.getMainLooper());
    @Override
    public void onError(final int code, final String info) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                onErrorInUI(code, info);
            }
        });
    }

    @Override
    public void onProgress(final int progress) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                onProgessInUI(progress);
            }
        });
    }

    @Override
    public void onSuccess(final Object... result) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                onSuccessInUI(result);
            }
        });
    }

    public abstract void onSuccessInUI(Object... result);

    public abstract void onErrorInUI(int code, String info);

    public abstract void onProgessInUI(int progress);
}
