package com.idaniu.maga.shopping.bean;

import java.util.List;

/**
 * 表示热卖页hot下载的商品数据信息的javabean类
 * Created by yuanbao15 on 2017/12/4.
 */
public class HotResultBean {
    private int totalCount;     //商品数量
    private int currentPage;    //当前页面
    private int totalPage;      //页面数量
    private int pageSize;       //页面中展示商品容量
    private List<ProductBean> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ProductBean> getList() {
        return list;
    }

    public void setList(List<ProductBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "HotResultBean{" +
                "totalCount=" + totalCount +
                ", currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", pageSize=" + pageSize +
                ", list=" + list +
                '}';
    }
}
