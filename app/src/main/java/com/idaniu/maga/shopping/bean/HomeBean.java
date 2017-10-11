package com.idaniu.maga.shopping.bean;

/**
 * frag1主页的瀑布流的数据，每条对应一个homebean对象
 * Created by yuanbao15 on 2017/10/10.
 */
public class HomeBean {

    private long id;

    private String title;
    private HomeItemBean cpOne;     //三个对象，每个对象里都有一张图片、id、标题
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


    /*
    //test用 没有下一级子对象图片的简单情况
    private long id;
    private String name;
    private String imgUrl;

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

    @Override
    public String toString() {
        return id+","+name+","+imgUrl+"\n";
    }*/
}
