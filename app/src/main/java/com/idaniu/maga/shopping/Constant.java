package com.idaniu.maga.shopping;

import okhttp3.MediaType;

/**
 * Created by yuanbao15 on 2017/10/9.
 */
public class Constant {
    //首页图片轮播的图片资源地址
//    public static final String HEAD_URL = "http://112.124.22.238:8081/course_api/banner/query";   //初始
    public static final String HEAD_URL = "http://www.yuanbao15.club/ybfile/bannerimage.json";     //yb自己的....成功了。注意json数据格式


    //    主页瀑布流的网络资源地址
//    public static final String HOME_URL = "http://112.124.22.238:8081/course_api/campaign/recommend";   //初始
    public static final String HOME_URL = "http://www.yuanbao15.club/ybfile/home.json";     //yb自己的

    public static final String HOST = "http://train.dolphin.com";
    public static final String HOT_URL = HOST + "/course_api/wares/hot";    //热卖页信息
    public static final String LOGIN_URL = HOST + "/course_api/auth/login";     //登录url；
    public static final String REGISTER_URL = HOST + "/course_api/auth/reg";    //注册url；
    public  static final String DES_KEY="12345670";     //加密算法的密钥。

    public static final MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded");

}
