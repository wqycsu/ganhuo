package com.wqy.ganhuo.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "IOSCACHE".
 */
public class IOSCache {

    private Long id;
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
    private Integer page;

    public IOSCache() {
    }

    public IOSCache(Long id) {
        this.id = id;
    }

    public IOSCache(Long id, String objectId, Integer platformType, String who, String publishedAt, String desc, String type, String url, String createdAt, String updatedAt, Boolean used, Integer page) {
        this.id = id;
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
        this.page = page;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

}
