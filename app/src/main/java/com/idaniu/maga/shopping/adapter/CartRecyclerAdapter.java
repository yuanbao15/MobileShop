package com.idaniu.maga.shopping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.idaniu.maga.shopping.R;
import com.idaniu.maga.shopping.bean.ShoppingCartBean;
import com.idaniu.maga.shopping.manager.CartManager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yuanbao15 on 2017/12/8.
 */
public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder> {
    private static final String TAG = "CartRecyclerAdapter";

    private Context context;
    private List<ShoppingCartBean> cartBeanList;
    private OnCartUpdateListener onCartUpdateListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        final ShoppingCartBean bean = cartBeanList.get(i);

        viewHolder.checkBox.setChecked(bean.isChecked());
        Picasso.with(context).load(bean.getImgUrl()).into(viewHolder.imageView);
        viewHolder.titleView.setText(bean.getName());
        viewHolder.priceView.setText(bean.getPrice());
        viewHolder.numEditText.setText(bean.getCount() + ""); //整型转字符串,否则报错

        /*//控件设置监听，用于更新数据
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCartUpdateListener != null){
                    onCartUpdateListener.onUpdateChecked(i);
                }
            }
        });*/
        //为是否勾选控件设置监听
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setChecked(!bean.isChecked());
                //还要更新到Manager中
                onCartUpdateListener.onUpdateChecked(i);
               /* if(bean.isChecked()){       //控件图片变化          //不用写这个系统自动变化，自己写的图形不同了
                    viewHolder.checkBox.setBackgroundResource(R.drawable.icon_check_true);
                }else{
                    viewHolder.checkBox.setBackgroundResource(R.drawable.icon_check_false);
                }*/
                Log.e(TAG, "该项目被勾选了吗?"+bean.isChecked());
            }
        });

        //为加减按钮设置监听
        viewHolder.reduceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setCount(bean.getCount() - 1);
                //还要更新到Manager，并显示
                onCartUpdateListener.onUpdatecount(i);
            }
        });
        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setCount(bean.getCount() + 1);

                onCartUpdateListener.onUpdatecount(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return cartBeanList.size();
    }

    //内部类
    class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        ImageView imageView;
        TextView titleView, priceView;
        EditText numEditText;      //数量
        Button reduceButton, addButton; //加减

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.cart_single_checkbox);
            imageView = (ImageView) itemView.findViewById(R.id.iv_cart_single);
            titleView = (TextView) itemView.findViewById(R.id.tv_cart_single_title);
            priceView = (TextView) itemView.findViewById(R.id.tv_cart_single_price);
            numEditText = (EditText) itemView.findViewById(R.id.et_cart_num);
            reduceButton = (Button) itemView.findViewById(R.id.btn_cart_reduce);
            addButton = (Button) itemView.findViewById(R.id.btn_cart_add);
        }
    }

    //构造器
    public CartRecyclerAdapter(Context context, List<ShoppingCartBean> cartBeanList, OnCartUpdateListener listener) {
        this.context = context;
        this.cartBeanList = cartBeanList;
        this.onCartUpdateListener = listener;
    }


    //写一个更新的接口，包含是否选中、购买数量两个方法
    public interface OnCartUpdateListener{
        void onUpdateChecked(int i);
        void onUpdatecount(int i);
    }
}
