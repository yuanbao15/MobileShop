package com.idaniu.maga.shopping.manager;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.lang.ref.WeakReference;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.idaniu.maga.shopping.Constant;
import com.idaniu.maga.shopping.DESUtil;
import com.idaniu.maga.shopping.ShoppingApplication;
import com.idaniu.maga.shopping.bean.LoginBean;
import com.idaniu.maga.shopping.bean.UserBean;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yuanbao15 on 2017/11/2.
 *
 * 用户管理类，单例模式
 * 包含用户登录、登出、注册的逻辑，管理用户信息
 */
public class UserManager {
    private static final String TAG = "UserManager";

    private static final String PREF_NAME = "user";
    private static final String TOKEN_KEY = "token";
    private static final String USER_KEY = "user";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    private volatile static UserManager sInstance;

    private SharedPreferences mSharedPreferences;
    private UserBean mUserInfo;
    private String mToken;
    private WeakReference<OnLoginListener> mLoginListener;
    private WeakReference<OnRegisterListener> mRegisterListener;

    public static UserManager getInstance(){
        if(sInstance == null){
            synchronized (UserManager.class){
                if(sInstance == null){
                    sInstance = new UserManager();
                }
            }
        }
        return sInstance;
    }

    private UserManager(){
        mSharedPreferences = ShoppingApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        //获取保存的用户信息
        mToken = mSharedPreferences.getString(TOKEN_KEY, "");
        if(TextUtils.isEmpty(mToken)){
            mToken = null;
        }else {
            String json = mSharedPreferences.getString(USER_KEY, "");
            if(TextUtils.isEmpty(json)){
                mToken = null;
            }else {
                Gson gson = new Gson();
                UserBean data = gson.fromJson(json, UserBean.class);
                if(data == null){
                    mToken = null;
                }else {
                    mUserInfo = data;
                }
            }
        }
    }

    /**
     * 登录方法模块
     *
     * @param phone 电话号码
     * @param password 密码
     * @param listener 登录监听
     */
    public void login(final String phone, final String password, OnLoginListener listener){
        if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)){
            return;
        }
        mLoginListener = new WeakReference<OnLoginListener>(listener);
        OkHttpClient okHttpClient = new OkHttpClient();

        String params = "phone=" + phone + "&password=" + DESUtil.encode(Constant.DES_KEY, password);

        Request request = new Request.Builder()
                .url(Constant.LOGIN_URL)
                .post(RequestBody.create(Constant.CONTENT_TYPE, params))
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(mLoginListener != null && mLoginListener.get() != null){
                    mLoginListener.get().onError(e.getMessage());
                }
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response == null || response.body() == null){
                    onFailure(call, new IOException("response is empty"));
                    return;
                }

                String result = response.body().string();
                Gson gson = new Gson();
                LoginBean data = gson.fromJson(result, LoginBean.class);
                if(data.getStatus() != 1){
                    onFailure(call, new IOException(data.getMessage()));
                    return;
                }

                saveUser(data.getData(), phone, password);
                saveToken(data.getToken());

                if(mLoginListener != null && mLoginListener.get() != null){
                    mLoginListener.get().onLogin();
                }
            }
        });
    }

    /**
     * 注册方法模块
     *
     * @param phone 电话号码
     * @param password 密码
     * @param listener 注册侦听器
     */
    public void register(final String phone, final String password, OnRegisterListener listener){
        if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)){
            return;
        }

        mRegisterListener = new WeakReference<>(listener);

        OkHttpClient okHttpClient = new OkHttpClient();

        String params = "phone=" + phone + "&password=" + DESUtil.encode(Constant.DES_KEY, password);

        Request request = new Request.Builder()
                .url(Constant.REGISTER_URL)
                .post(RequestBody.create(Constant.CONTENT_TYPE, params))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(mRegisterListener != null && mRegisterListener.get() != null){
                    mRegisterListener.get().onError(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response == null || response.body() == null){
                    onFailure(call, new IOException("response is empty"));
                    return;
                }

                String result = response.body().string();
                Gson gson = new Gson();
                LoginBean data = gson.fromJson(result, LoginBean.class);
                if(data.getStatus() != 1){
                    onFailure(call, new IOException(data.getMessage()));
                    return;
                }

                saveUser(data.getData(), phone, password);
                saveToken(data.getToken());

                if(mRegisterListener != null && mRegisterListener.get() != null){
                    mRegisterListener.get().onRegister();
                }
            }
        });
    }


    //电话号码
    public String getPhone(){
        return mSharedPreferences.getString(USERNAME_KEY, "");
    }

    //用户密码
    public String getPassword(){
        return mSharedPreferences.getString(PASSWORD_KEY, "");
    }

    //用户id
    public int getId(){
        if(mUserInfo != null){
            return (int) mUserInfo.getId();
        }
        return 0;
    }

    //用户名
    public String getUserName(){
        if(mUserInfo != null){
            return mUserInfo.getUsername();
        }
        return null;
    }

    //头像
    public String getAvatar(){
        if(mUserInfo != null){
            return mUserInfo.getLogo_url();
        }
        return null;
    }

    //token
    public String getToken(){
        return mToken;
    }

    //是否登录
    public boolean isLogin(){
        return mUserInfo != null && !TextUtils.isEmpty(mToken);
    }


    //保存登录后获取的用户数据
    private void saveUser(UserBean user, String phone, String password){
        mUserInfo = user;
        Gson gson = new Gson();
        String json = gson.toJson(mUserInfo, UserBean.class);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(USER_KEY, json);
        editor.putString(USERNAME_KEY, phone);
        editor.putString(PASSWORD_KEY, password);
        editor.apply();
    }

    //保存token 信息
    private void saveToken(String token){
        mToken = token;
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }


    //侦听器
    public interface OnLoginListener{
        void onLogin();
        void onError(String msg);
    }

    public interface OnRegisterListener{
        void onRegister();
        void onError(String msg);
    }

}
