package com.wqy.ganhuo.cache;

import java.util.ArrayList;

/**
 * Created by weiquanyun on 15/11/20.
 */
public interface ICacheUtil {
    /**
     * 清除所有缓存
     */
    void clearAllCache();

    /**
     * 添加缓存
     * @param cache
     *          缓存内容:{@link com.alibaba.fastjson.JSON}格式
     * @param page
     *          所属页数
     */
    void addCache(String cache, int page);

    /**
     * 根据页数查询缓存
     * @param page
     * @return
     */
    ArrayList getCacheByPage(int page);
}
