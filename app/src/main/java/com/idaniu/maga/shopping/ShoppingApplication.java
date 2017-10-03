package com.idaniu.maga.shopping;

import android.app.Application;

/**
 * Created by maga on 2017/10/3.
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
