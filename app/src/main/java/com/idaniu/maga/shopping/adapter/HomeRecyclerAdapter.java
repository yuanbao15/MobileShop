package com.idaniu.maga.shopping.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.item_title);
            imageView1 = (ImageView) view.findViewById(R.id.item_image1);
            imageView2 = (ImageView) view.findViewById(R.id.item_image2);
            imageView3 = (ImageView) view.findViewById(R.id.item_image3);

        }
    }

    //构造函数
    public HomeRecyclerAdapter(Context context, List<HomeBean> homeBeanList) {
        this.context = context;
        this.homeBeanList = homeBeanList;
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        return position%2;  //按照老师的改的
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
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
}
