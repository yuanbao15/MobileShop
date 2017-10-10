package com.idaniu.maga.shopping.bean;

/**
 * frag1主页的瀑布流的数据，每条对应一个homebean对象
 * Created by yuanbao15 on 2017/10/10.
 */
public class HomeBean {

    private long id;
    private String title;
    private HomeItemBean cpOne;     //活动子对象是干嘛的？
    private HomeItemBean cpTwo;
    private HomeItemBean cpThree;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HomeItemBean getCpOne() {
        return cpOne;
    }

    public void setCpOne(HomeItemBean cpOne) {
        this.cpOne = cpOne;
    }

    public HomeItemBean getCpTwo() {
        return cpTwo;
    }

    public void setCpTwo(HomeItemBean cpTwo) {
        this.cpTwo = cpTwo;
    }

    public HomeItemBean getCpThree() {
        return cpThree;
    }

    public void setCpThree(HomeItemBean cpThree) {
        this.cpThree = cpThree;
    }



    //HomeBean内部类
    public class HomeItemBean{
        private long id;
        private String title;
        private String imgUrl;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

}
