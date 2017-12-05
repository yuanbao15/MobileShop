package com.idaniu.maga.shopping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.idaniu.maga.shopping.R;
import com.idaniu.maga.shopping.bean.HomeBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 *
 * 应用Picasso，Android图片下载缓存库
 * Created by yuanbao15 on 2017/10/10.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>{

    private Context context;
    private List<HomeBean> homeBeanList;
    private OnMoreListener onMoreListener; //下拉刷新的接口

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
        TextView textView;

        View homeBeanView;  //定义这个视图用于处理recyclerView里的的点击事件

        public ViewHolder(View view) {
            super(view);
            homeBeanView = view;
            textView = (TextView) view.findViewById(R.id.item_title);
            imageView1 = (ImageView) view.findViewById(R.id.item_image1);
            imageView2 = (ImageView) view.findViewById(R.id.item_image2);
            imageView3 = (ImageView) view.findViewById(R.id.item_image3);

        }
    }

    //构造函数1
    public HomeRecyclerAdapter(Context context, List<HomeBean> homeBeanList) {
        this.context = context;
        this.homeBeanList = homeBeanList;
    }
    //增加下拉刷新接口后重写一个构造函数2
    public HomeRecyclerAdapter(Context context, List<HomeBean> homeBeanList, OnMoreListener mOnMoreListener) {
        this.context = context;
        this.homeBeanList = homeBeanList;
        this.onMoreListener = mOnMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        return position%2;  //按照老师的改的
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);

        //设置点击事件监听处理：这里是整个视图的。不设置子项或者点击的不是子项的部分，就会执行整个视图的点击响应
        holder.homeBeanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = holder.getAdapterPosition();
                HomeBean homeBean = homeBeanList.get(i);
                Toast.makeText(v.getContext(),"yb点击了View"+homeBean.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });

        //这里是每个子项的
        holder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = holder.getAdapterPosition();
                HomeBean homeBean = homeBeanList.get(i);
                Toast.makeText(v.getContext(),"yb点击了Image"+homeBean.getCpOne().getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = holder.getAdapterPosition();
                HomeBean homeBean = homeBeanList.get(i);
                Toast.makeText(v.getContext(),"yb点击了Image"+homeBean.getCpTwo().getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = holder.getAdapterPosition();
                HomeBean homeBean = homeBeanList.get(i);
                Toast.makeText(v.getContext(),"yb点击了Image"+homeBean.getCpThree().getTitle(),Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        HomeBean homeBean = homeBeanList.get(i);
        Log.d("RecyclerViewAdapter", "显示图片" + i);
        holder.textView.setText(homeBean.getTitle());
//        holder.imageView1.setImageResource(R.drawable.icon_wechat_72);    //本地图片就用这种方式，网络的话用Picasso缓存库
        Picasso.with(context).load(homeBean.getCpOne().getImgUrl()).into(holder.imageView1);    //这里需要上下文
        Picasso.with(context).load(homeBean.getCpTwo().getImgUrl()).into(holder.imageView2);    //这里需要上下文
        Picasso.with(context).load(homeBean.getCpThree().getImgUrl()).into(holder.imageView3);    //这里需要上下文


    }

    @Override
    public int getItemCount() {
//        if (homeBeanList == null) return 0;
        return homeBeanList.size();
    }

    //下拉刷新更多的接口
    public interface OnMoreListener{
        void onMore();
    }
}
