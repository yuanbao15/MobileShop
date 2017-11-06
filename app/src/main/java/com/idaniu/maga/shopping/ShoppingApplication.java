package com.idaniu.maga.shopping;

import android.app.Application;

/**
 * Application 类似单例设计模式   但没有构造函数也没new出新的对象
 * Created by yuanbao15 on 2017/10/3.
 */

public class ShoppingApplication extends Application {

    private static ShoppingApplication sInstance;

    public static ShoppingApplication getInstance(){
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

    }

}
