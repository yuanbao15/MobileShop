package com.idaniu.maga.shopping.bean;

import java.io.Serializable;

/**
 * 商品信息的javabean类
 * Created by yuanbao15 on 2017/12/4.
 */
public class ProductBean implements Serializable{
    private long id;
    private String name;
    private String imgUrl;
    private String price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
