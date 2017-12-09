package com.idaniu.maga.shopping.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idaniu.maga.shopping.ShoppingApplication;
import com.idaniu.maga.shopping.bean.ShoppingCartBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车管理类，单例模式。
 *
 * 主要是put(添加)、update(更新数量以及选中状态)、delete(删除)、getAll(获取购物车内容)
 * 这里为了简单，没有使用数据库保存，而是使用的SharedPreferences
 * 由于SharedPreferences不能存储对象，所以将对象转为json格式存储
 *
 * Created by not yuanbao15 but maga on 2017/12/8.
 */
public class CartManager {

    private static final String TAG = "CartManager";
    private static final String CART_KEY = "cart_list";

    private volatile static CartManager sInstance;
    private SharedPreferences mSharedPreferences;
    private SparseArray<ShoppingCartBean> mData;

    //单例模式
    public static CartManager getInstance(){
        if(sInstance == null){
            synchronized (CartManager.class){
                if(sInstance == null){
                    sInstance = new CartManager();
                }
            }
        }
        return sInstance;
    }

    //构造器
    private CartManager(){
        //初始化数据
        mSharedPreferences = ShoppingApplication.getInstance().getSharedPreferences("cart", Context.MODE_PRIVATE);
        mData = new SparseArray<>(10);

        getDataFromLocal();     //加载所有数据
    }

    /**
     * 从SharedPreference中获取数据
     */
    private void getDataFromLocal(){
        String json = mSharedPreferences.getString(CART_KEY, "[]");
        if(!TextUtils.isEmpty(json)){
            Gson gson = new Gson();
            Type type = new TypeToken<List<ShoppingCartBean>>(){}.getType();
            List<ShoppingCartBean> data = gson.fromJson(json, type);
            for(ShoppingCartBean bean : data){
                mData.put((int) bean.getId(), bean);
            }
        }
    }

    /**
     * 将商品添加到购物车
     * 如果有就数量加1，没有就直接添加
     * @param item
     */
    public void put(ShoppingCartBean item){
        if(item == null) return;

        ShoppingCartBean bean = mData.get((int) item.getId());
        if(bean == null){
            bean = item;
        }else {
            bean.setCount(bean.getCount() + 1);
        }

        mData.put((int) item.getId(), bean);
        putDataToLocal();

    }

    /**
     * 更新购物车数据，主要是更新数量和选中状态
     * @param item
     */
    public void update(ShoppingCartBean item) {
        mData.put((int) item.getId(), item);
        putDataToLocal();
    }

    /**
     * 删除数据
     * @param item
     */
    public void delete(ShoppingCartBean item){
        mData.delete((int) item.getId());
        putDataToLocal();
    }

    /**
     * 获取所有数据
     * @return
     */
    public List<ShoppingCartBean> getAll(){
        int size = mData.size();
        ArrayList<ShoppingCartBean> list = new ArrayList<>(size);
        for(int i = 0; i < size; i++){
            list.add(mData.valueAt(i));
        }
        return list;
    }

    /**
     * 把修改后的数据同步到SharedPreference中
     */
    private void putDataToLocal(){
        int size = mData.size();
        ArrayList<ShoppingCartBean> list = new ArrayList<>(size);
        for(int i = 0; i < size; i++){
            list.add(mData.valueAt(i));
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<ShoppingCartBean>>(){}.getType();
        String json = gson.toJson(list, type);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(CART_KEY, json);
        editor.apply();
    }


}