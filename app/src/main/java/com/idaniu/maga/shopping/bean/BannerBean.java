package com.idaniu.maga.shopping.bean;

/**
 * 轮播图片的javabean类
 * Created by yuanbao15 on 2017/10/9.
 */
public class BannerBean {
    private long id;
    private String name;
    private String imgUrl;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return id+","+name+","+imgUrl+","+description;
    }
}
