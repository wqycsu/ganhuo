package com.wqy.ganhuo.callback;

/**
 * Created by weiquanyun on 15/11/28.
 */
public interface GanHuoCallback {
    /**
     * 执行成功的回调
     * @param result
     */
    void onSuccess(Object... result);

    /**
     * 执行失败的回调
     * @param code
     *          失败错误码
     * @param info
     *          失败信息
     */
    void onError(int code, String info);

    /**
     * 进度更新回调
     * @param progress
     *          当前进度
     */
    void onProgress(int progress);
}
