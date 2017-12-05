package com.idaniu.maga.shopping.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idaniu.maga.shopping.Constant;
import com.idaniu.maga.shopping.R;
import com.idaniu.maga.shopping.ShoppingApplication;
import com.idaniu.maga.shopping.adapter.HotRecyclerAdapter;
import com.idaniu.maga.shopping.bean.HomeBean;
import com.idaniu.maga.shopping.bean.HotResultBean;
import com.idaniu.maga.shopping.bean.ProductBean;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 第二个页面：热卖，recyclerView 展示
 * Created by yuanbao15 on 2017/10/8.
 */
public class Fragment2 extends Fragment{
    private static final String TAG = "HotFragment";
    private static final int PAGE_SIZE = 10;    //hot页面一次下载显示商品的数量

    private int currentPage = 1;            //第多少个page，是个变量，根据下载数据的量决定
    private int totalCount = 0;     //总的商品数量

    private volatile boolean isLoading = false;

    private List<ProductBean> productBeanList = new ArrayList();
    private HotRecyclerAdapter hotRecyclerAdapter;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag2, container, false);
        initView(v);
        return v;
    }

    //frag2中的控件初始化
    private void initView(View v) {
        loadHotData();         //下载 热卖页瀑布流的数据

        //主页瀑布流部分初始化
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_hot);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);   //分成几列
        recyclerView.setLayoutManager(layoutManager);

        hotRecyclerAdapter = new HotRecyclerAdapter(getContext(), productBeanList, new HotRecyclerAdapter.OnMoreListener() {
            @Override
            public void onMore() {
                loadHotData();
                Toast.makeText(ShoppingApplication.getInstance(), "you refresh this page2!", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(hotRecyclerAdapter);
    }

    private void loadMoreData() {

    }


    //下载 热卖页瀑布流的数据，使用okhttp方式。
    private void loadHotData() {
        OkHttpClient client = new OkHttpClient();       //创建网络实例
        //创建请求
        Request request = new Request.Builder()
                .url(Constant.HOT_URL + "?curPage="+currentPage+"&pageSize="+PAGE_SIZE)     //url不对程序就会错
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

                HotResultBean hotResultBean = gson.fromJson(responseData, HotResultBean.class);
                totalCount = hotResultBean.getTotalCount();
                productBeanList.addAll(hotResultBean.getList());

                //检查数据是否接收到
                for(ProductBean bean: productBeanList){
                    System.out.println(bean.toString());
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hotRecyclerAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

}
