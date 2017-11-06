package com.idaniu.maga.shopping.bean;

/**
 * Created by yuanbao15 on 2017/11/2.
 * 登录结果
 */
public class LoginBean {

    private int status;
    private String message;
    private String token;
    private UserBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }
}
