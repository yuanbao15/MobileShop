package com.idaniu.maga.shopping.bean;

/**
 * 购物车的javabean类，继承了商品信息ProductBean类，并增加数量和是否被选中
 * Created by yuanbao15 on 2017/12/4.
 */
public class ShoppingCartBean extends ProductBean{
    private int count;  //购买数量
    private boolean checked;    //是否被选中

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    //构造器，以商品信息为参量导入
    public ShoppingCartBean(ProductBean bean){
        this.setId(bean.getId());
        this.setName(bean.getName());
        this.setPrice(bean.getPrice());
        this.setImgUrl(bean.getImgUrl());
        this.setDescription(bean.getDescription());
        this.setCount(0);
        this.setChecked(false);
    }

}
