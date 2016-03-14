package com.wqy.ganhuo.model;

import com.wqy.ganhuo.greendao.Favorite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiquanyun on 15/12/2.
 */
public class FavoriteContentItem extends ContentItem{

    private String objectId;
    private Integer platformType;
    private String who;
    private String publishedAt;
    private String desc;
    private String type;
    private String url;
    private String createdAt;
    private String updatedAt;
    private Boolean used;

    public FavoriteContentItem() {
    }

    public FavoriteContentItem(String objectId, Integer platformType, String who, String publishedAt, String desc, String type, String url, String createdAt, String updatedAt, Boolean used) {
        this.objectId = objectId;
        this.platformType = platformType;
        this.who = who;
        this.publishedAt = publishedAt;
        this.desc = desc;
        this.type = type;
        this.url = url;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.used = used;
    }

    public String get__id() {
        return objectId;
    }

    public void set__id(String objectId) {
        this.objectId = objectId;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public static ArrayList<FavoriteContentItem> parseCache(List<Favorite> caches) {
        if(caches == null) {
            return null;
        }
        ArrayList<FavoriteContentItem> list = new ArrayList<FavoriteContentItem>(caches.size());
        for(Favorite cache : caches) {
            FavoriteContentItem contentItem = new FavoriteContentItem();
            contentItem.setUsed(cache.getUsed());
            contentItem.setWho(cache.getWho());
            contentItem.setUrl(cache.getUrl());
            contentItem.setUpdatedAt(cache.getUpdatedAt());
            contentItem.setCreatedAt(cache.getCreatedAt());
            contentItem.setDesc(cache.getDesc());
            contentItem.set__id(cache.getObjectId());
            contentItem.setPlatformType(cache.getPlatformType());
            contentItem.setType(cache.getType());
            contentItem.setPublishedAt(cache.getPublishedAt());
            list.add(contentItem);
        }
        return list;
    }
}
