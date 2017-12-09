package com.idaniu.maga.shopping.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idaniu.maga.shopping.Constant;
import com.idaniu.maga.shopping.R;
import com.idaniu.maga.shopping.ShoppingApplication;
import com.idaniu.maga.shopping.activity.BannerActivity;
import com.idaniu.maga.shopping.adapter.HomeRecyclerAdapter;
import com.idaniu.maga.shopping.bean.BannerBean;
import com.idaniu.maga.shopping.bean.HomeBean;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 第一个fragment，展示主页，包含一个图片轮播banner和一个瀑布流视图
 * Created by yuanbao15 on 2017/10/8.
 */
public class Fragment1 extends Fragment{
    private static final String TAG = "HomeFragment";

    private ViewPager mBannerViewPager;       //图片轮播里的ViewPager
    private BannerPagerAdapter mBannerPagerAdapter;
    private LinearLayout mIndicateLinearLayout;     //圆点指示器
    private TextView mBannerTitleTextView;      //图片轮播中每张图片的标题
//    Handler handler = new BannerHandler();       //为了弄手动停止图片轮播，学习handler方式
//    private static final int BANNER_NEXT = 1;
//    private static final int BANNER_PAUSE = 2;
//    private static final int BANNER_RESUME = 3;


    private List<BannerBean> mBannerList;
    private List<ImageView> mIndicateList = new ArrayList<>();   //圆点集合
    private List<ImageView> mBannerViewsList = new ArrayList<>();   //图片展示的集合

    private List<HomeBean> homeBeanList = new ArrayList<>();    //主页瀑布流的部分
    private HomeRecyclerAdapter homeRecyclerAdapter;
    private RecyclerView recyclerView;

    /*
    public static Fragment1 newInstance(String tabName){
        Fragment1 fragment = new Fragment1();
//      fragment.mTabName = tabName;
        return fragment;
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag1, container, false);
        initView(v);
//      getLayoutInflater(savedInstanceState).inflate(R.layout.home_item,container);
// 没有这句报错，因为否则就不知道把哪一个布局放到瀑布流中。也不对，viewHolder里有对R.layout.home_item的引用
        return v;
    }

    private void initView(View v) {
        mBannerViewPager = (ViewPager) v.findViewById(R.id.viewpager_hotnews);
        mBannerTitleTextView =(TextView) v.findViewById(R.id.tv_title_hotnews);
        mIndicateLinearLayout =(LinearLayout) v.findViewById(R.id.ll_hotnews_indicator);


        mBannerPagerAdapter = new BannerPagerAdapter();
        mBannerViewPager.setAdapter(mBannerPagerAdapter);     //给mViewPager绑定这个适配器

        //图片轮播的viewpager在页面改变时候
        mBannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for (int j = 0; j < mIndicateLinearLayout.getChildCount(); j++) { //选到哪页就跳转到哪一页
                    View view = mIndicateLinearLayout.getChildAt(j);
                    if (j == i) {
                        view.setSelected(true);
                        mIndicateList.get(j).setBackgroundResource(R.drawable.indicate_circle_red); //实现指示圆点的状态变化
                    } else {
                        view.setSelected(false);
                        mIndicateList.get(j).setBackgroundResource(R.drawable.indicate_circle_gray);
                    }
                }
                //标题内容也要随之相应的更改
                mBannerTitleTextView.setText(mBannerList.get(i).getName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //这里写长按时停止自动滚动......没有成功。发现：这里的对应着ViewPager本身真实的是否轮播状态，与手指是否滑动无关
                switch (state){
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        //用户正在拖拽，暂停轮播
//                        handler.sendEmptyMessage(BANNER_PAUSE);
//                        Log.d(TAG, "图片轮播手指拖拽中"+state);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        //手指已移开，继续轮播
//                        handler.sendEmptyMessageDelayed(BANNER_NEXT, 3000);
//                        Log.d(TAG, "图片轮播手指已松开" + state);
                        break;
                    default:
	                    break;
                }
            }
        });

//        handler.sendEmptyMessageDelayed(BANNER_NEXT, 3000);

        loadBannerData();       //下载 图片轮播的图片资源

        loadHomeData();         //下载 主页瀑布流的数据

        //主页瀑布流部分初始化
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_home);
        //瀑布流效果
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);   //分成几列
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));      //普通线性展示
//        homeRecyclerAdapter = new HomeRecyclerAdapter(getActivity(), homeBeanList);   //这是没有下拉刷新的，下面的有
        homeRecyclerAdapter = new HomeRecyclerAdapter(getContext(),homeBeanList, new HomeRecyclerAdapter.OnMoreListener() {
            @Override
            public void onMore() {      //没实现
                loadHomeData();
                Toast.makeText(ShoppingApplication.getInstance(), "you refresh this page!", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(homeRecyclerAdapter);
//        Log.d(TAG, "frag1执行到这2");

    }

    //下载主页Fragment1的homebean数据，使用okhttp方式。
    private void loadHomeData() {
        OkHttpClient client = new OkHttpClient();       //创建网络实例
        //创建请求
        Request request = new Request.Builder()
                .url(Constant.HOME_URL + "?type=1")
                .build();
        //返回数据
        client.newCall(request).enqueue(new Callback() {     //书上使用的execute()方法，同步方式。老师的是enqueue(new Callback())这个是没有返回值的，异步方式。
            @Override
            public void onFailure(Call call, IOException e) {
                if(getActivity() == null) return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ShoppingApplication.getInstance(), "load data failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                System.out.println(responseData);
                Gson gson = new Gson();     //用Gson解析Json数据
                Type type = new TypeToken<List<HomeBean>>(){}.getType();  //这句不知道是干嘛，获取类型？!!!!这里要改HomeBean，调试了好久才查出错误产生的并不是HomeBean对象

                //homeBeanList = gson.fromJson(responseData, type);发现这儿的homeBeanList有数据，但外部没有，所以不能这样写
                homeBeanList.clear();
                List<HomeBean> list = gson.fromJson(responseData, type);
                homeBeanList.addAll(list);
                /*//debug
                for (HomeBean homeBean:homeBeanList){
                    Log.d(TAG,homeBean.toString());
                }*/
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        homeRecyclerAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    //下载头图轮播的图片资源，使用okhttp方式。
    private void loadBannerData() {
        OkHttpClient client = new OkHttpClient();       //创建网络实例
        //创建请求
        Request request = new Request.Builder()
                .url(Constant.HEAD_URL + "?type=1")         //接口文档中type为1的数据（广告图片）
                .build();
        //返回数据
        client.newCall(request).enqueue(new Callback() {    //书上使用的execute()方法，同步方式。老师的是enqueue(new Callback())这个是没有返回值的，异步方式。
            @Override
            public void onFailure(Call call, IOException e) {
                if(getActivity() == null) return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ShoppingApplication.getInstance(), "load data failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
//                System.out.println(responseData);
                Gson gson = new Gson();     //用Gson解析Json数据
                Type type = new TypeToken<List<BannerBean>>(){}.getType();  //这句不知道是干嘛，获取类型？
                mBannerList = gson.fromJson(responseData, type);        //gson获取到的数据成为一个个bannerbean对象然后添加到mbannerlist列表中

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mBannerList != null && mBannerList.size()>=0){
                            mIndicateLinearLayout.removeAllViews();     //先清除再重新加载
                            mBannerViewsList.clear();
                            mIndicateLinearLayout.removeAllViews();

                            for (int j=0; j<mBannerList.size(); j++){
                                //图片处理
                                ImageView imageView = new ImageView(getActivity());
                                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT));  //宽高
                                imageView.setBackgroundResource(0);     //这句不懂？ 是撤掉背景？？
                                mBannerViewsList.add(imageView);

                                //圆点指示器处理
                                ImageView indicateView = new ImageView(getActivity());
                                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(18,18);   //20*20的像素大小
                                indicateView.setLayoutParams(lp);
                                indicateView.setBackgroundResource(0);
//                              indicateView.setImageResource(R.drawable.indicate_circle_gray);   //不通过这里展示，否则与onPageSelected()方法里的有重叠，效果不好
//                              indicateView.setPadding(4, 4, 4, 4);    //每个圆点图片的前后左右距离
                                mIndicateLinearLayout.addView(indicateView);
                                mIndicateList.add(indicateView);

                            }
                            mBannerPagerAdapter.notifyDataSetChanged();
                            mIndicateLinearLayout.postDelayed(bannerRunnable, 1000);

                        }
                    }
                });
            }
        });

    }

    //图片自动轮播。但圆点怎么更换图片呢？不在这儿，在那个onchangelistener()监听方法里
    private Runnable bannerRunnable = new Runnable() {
        @Override
        public void run() {
            //圆点指示器随着图片变化而变化
            int index = mBannerViewPager.getCurrentItem();
            if(index == mBannerList.size()-1){      //循环重复
                index = -1;
            }else{
                index = index+1;
            }

            mBannerViewPager.setCurrentItem(index, true);
            mIndicateLinearLayout.postDelayed(bannerRunnable, 3000);     //每过3秒进行切换图片
        }
    };

    //自定义一个BannerPagerAdapter的适配器，给头图配置用
    private class BannerPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            if(mBannerViewsList == null){
                return 0;
            }
            return mBannerViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        //后面两个方法是非必须重写的，然后借鉴老师的。需要！！这里对应着mBannerPagerAdapter.notifyDataSetChanged();
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mBannerViewsList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(getActivity() == null || getActivity().isFinishing()) return null;
            ImageView view = mBannerViewsList.get(position);
            if(view.getParent() != null){
                ((ViewGroup)view.getParent()).removeView(view);
            }
            view.setImageResource(R.mipmap.ic_launcher);
            Picasso.with(getActivity()).load(mBannerList.get(position).getImgUrl()).into(view);
            container.addView(view);

            //yb为bannerView设置点击监听响应。是在这而不是在mViewPager
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "yb点击了首页" + mBannerViewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
                    if (mBannerViewPager.getCurrentItem() == 0) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), BannerActivity.class);
                        startActivity(intent);
                        /*
                        //打开手机手机浏览器访问一个网址
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri data = Uri.parse("https://yuanbao15.github.io");
                        intent.setData(data);
                        startActivity(intent);*/
                    }
                }
            });

            /*//写图片轮播手动时停止轮播，，，，没有成功
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            System.out.println("-----头图被按着了-----");
//                            mIndicateLinearLayout.postDelayed(bannerRunnable, 10000);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            System.out.println("-----手指在头图上移动------");
                            break;
                        case MotionEvent.ACTION_UP:
                            System.out.println("-----手指已拿开------");
                            break;
                        default:
                            break;

                    }
                    return true;
                }
            });
*/

            return view;
        }
    }


    /**
     * yb想写一个handler实现图片轮播在手动拖拽时暂停自动轮播。没有实现。
     */
/*
    private class BannerHandler extends Handler {

        Context context;
        WeakReference<Fragment1> weakReference;

        public BannerHandler(WeakReference<Fragment1> weakReference) {
            this.weakReference = weakReference;
        }


        @Override
        public void handleMessage(Message msg) {
            Fragment1 fragment1 = weakReference.get();
            //fragment1不存在了，就不需要再处理了
            if (fragment1 == null) {
                return;
            }
            //如果已经有消息了，先移除消息
            if (fragment1.handler.hasMessages(BANNER_NEXT)) {
                fragment1.handler.removeMessages(BANNER_NEXT);
            }

            switch (msg.what){
                case BANNER_NEXT:
                    //跳到下一页
                    int currentItem = mBannerViewPager.getCurrentItem();
                    fragment1.mBannerViewPager.setCurrentItem(++currentItem);
                    //3秒后继续轮播
                    fragment1.handler.sendEmptyMessageDelayed(BANNER_NEXT, 3000);
                    break;
                case BANNER_PAUSE:
                    //暂停，不自动轮播
                    break;
                case BANNER_RESUME:
                    //继续轮播
                    fragment1.handler.sendEmptyMessageDelayed(BANNER_NEXT, 3000);
                    break;
            }

            super.handleMessage(msg);
        }
    }*/



}
