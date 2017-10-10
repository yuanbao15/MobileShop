package com.idaniu.maga.shopping.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.item_image);
            textView = (TextView) view.findViewById(R.id.item_title);
        }
    }

    //构造函数
    public HomeRecyclerAdapter(Context context, List<HomeBean> homeBeanList) {
        this.context = context;
        this.homeBeanList = homeBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        HomeBean homeBean = homeBeanList.get(i);
        holder.textView.setText(homeBean.getTitle());
//        holder.imageView.setImageResource(homeBean.getId());  //本地图片就用这种方式，网络的话用Picasso缓存库
        Picasso.with(context).load(homeBean.getCpOne().getImgUrl()).into(holder.imageView);    //这里需要上下文


    }

    @Override
    public int getItemCount() {
        if (homeBeanList == null) return 0;
        return homeBeanList.size();
    }
}
