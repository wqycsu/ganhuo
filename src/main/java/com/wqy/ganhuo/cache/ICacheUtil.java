package com.wqy.ganhuo.cache;

import com.wqy.ganhuo.model.ContentItem;

import java.util.ArrayList;
import java.util.List;

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
     *          缓存内容:List&lt;{@link ContentItem}&gt;格式
     * @param page
     *          所属页数
     */
    void addCache(List<? extends ContentItem> cache, int page);

    /**
     * 添加缓存
     * @param cache
     *          缓存内容:{@link ContentItem}格式
     * @param page
     *          所属页数
     */
    void addCache(ContentItem cache, int page);

    /**
     * 添加缓存
     * @param cache
     *          缓存内容:{@link ContentItem}格式
     */
    void addCache(ContentItem cache);

    /**
     * 根据页数查询缓存
     * @param page
     * @return
     */
    List<? extends ContentItem> getCacheByPage(int page);
}
