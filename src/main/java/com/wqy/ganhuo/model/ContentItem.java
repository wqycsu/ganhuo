package com.wqy.ganhuo.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.wqy.ganhuo.greendao.AndroidCache;
import com.wqy.ganhuo.greendao.IOSCache;
import com.wqy.ganhuo.utils.Constants;

import java.io.Serializable;

/**
 * Created by weiquanyun on 15/8/19.
 */
public abstract class ContentItem implements Serializable, Comparable<ContentItem> {
    String who;
    String publishedAt;
    String desc;
    String type;
    String url;
    boolean used;
    String createdAt;
    @JSONField(name="_id")
    String _id;
    String updatedAt;
    boolean isStared;

    public boolean isStared() {
        return isStared;
    }

    public void setIsStared(boolean isStared) {
        this.isStared = isStared;
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

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String get__id() {
        return _id;
    }

    public void set__id(String _id) {
        this._id = _id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(who).append("-----").append(desc).append("-----").append(url);
        return stringBuffer.toString();
    }

    @Override
    public int compareTo(ContentItem another) {
        return -this.publishedAt.compareTo(another.publishedAt);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + _id.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof ContentItem)) {
            return false;
        }

        if(this == o) {
            return true;
        }

        if(this._id.equals(((ContentItem) o)._id)) {
            return true;
        }

        return false;
    }
}
