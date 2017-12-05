package com.idaniu.maga.shopping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.idaniu.maga.shopping.R;
import com.idaniu.maga.shopping.bean.ProductBean;
import com.idaniu.maga.shopping.bean.ShoppingCartBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Fragment2的适配器，实现下拉刷新、上拉加载的逻辑
 * 上拉加载：当最后一个item被绑定时，说明已经滑动到最下面了，可以开始加载更多数据
 * Created by yuanbao15 on 2017/12/4.
 */
public class HotRecyclerAdapter extends RecyclerView.Adapter<HotRecyclerAdapter.ViewHolder>{

    private static final int TYPE_NORMAL = 0;  //作为判断是处于正常还是加载更多的viewType
    private static final int TYPE_MORE = 1;

    private Context context;
    private List<ProductBean> productBeanList;
    private OnMoreListener onMoreListener; //下拉刷新的接口


    @Override
    public HotRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        if(viewType == TYPE_NORMAL){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hot_item, viewGroup, false);
        }else{
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hot_item, viewGroup, false);
        }
        return new HotRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotRecyclerAdapter.ViewHolder viewHolder, int i) {
        //设置每个控件的内容
        Log.d("Hot页面----","更新到商品信息"+i);
        final ProductBean productBean = productBeanList.get(i);
        viewHolder.textViewTitle.setText(productBean.getName());
        viewHolder.textViewPrice.setText("￥"+productBean.getPrice());
        //商品的图片实现，Picasso
        Picasso.with(context).load(productBean.getImgUrl()).into(viewHolder.imageViewProduct);
        //按键，添加到购物车
        viewHolder.buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingCartBean shoppingCartBean = new ShoppingCartBean(productBean);
                shoppingCartBean.setCount(shoppingCartBean.getCount()+1);
                shoppingCartBean.setChecked(true);
                Toast.makeText(context, "已添加到购物车", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看商品详情页

            }
        });
    }

    @Override
    public int getItemCount() {
        return productBeanList.size();
    }

    //构造器
    public HotRecyclerAdapter(Context context, List<ProductBean> productBeanList, OnMoreListener onMoreListener) {
        this.context = context;
        this.productBeanList = productBeanList;
        this.onMoreListener = onMoreListener;
    }

    //自己写HotRecyclerAdapter的ViewHolder内部类
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewProduct;
        TextView textViewTitle,textViewPrice;
        Button buttonBuy;

        public ViewHolder(View view) {
            super(view);

            //控件初始化
            imageViewProduct = (ImageView) view.findViewById(R.id.iv_product);
            textViewTitle = (TextView) view.findViewById(R.id.tv_title);
            textViewPrice = (TextView) view.findViewById(R.id.tv_price);
            buttonBuy = (Button) view.findViewById(R.id.bt_buy);

        }
    }

    //自己写一个下拉刷新更多的接口
    public interface OnMoreListener{
        void onMore();
    }

}
