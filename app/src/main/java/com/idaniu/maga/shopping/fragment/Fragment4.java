package com.idaniu.maga.shopping.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.idaniu.maga.shopping.R;
import com.idaniu.maga.shopping.adapter.CartRecyclerAdapter;
import com.idaniu.maga.shopping.bean.ShoppingCartBean;
import com.idaniu.maga.shopping.manager.CartManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车页面
 * Created by yuanbao15 on 2017/10/8.
 */
public class Fragment4 extends Fragment{

    private static final String TAG = "CartFragment";

    List<ShoppingCartBean> cartBeanList;

    private CartRecyclerAdapter cartRecyclerAdapter;
    private RecyclerView recyclerView;

    private TextView totalPriceTextView;    //合计总价
    private CheckBox allCheckBox;           //是否全选
    private static int allCheckedFlag = 0;      //全选标志
    private static final int ALL_CHECKED = 1;
    private static final int ALL_NOT_CHECKED = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag4, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        cartBeanList = CartManager.getInstance().getAll();

        //下排的控件
        totalPriceTextView = (TextView) view.findViewById(R.id.tv_cart_totalprice);
        allCheckBox = (CheckBox) view.findViewById(R.id.checkbox_cart_all);
        allCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allCheckedFlag != ALL_CHECKED){
                    allCheckedFlag = ALL_CHECKED;
                    for(ShoppingCartBean bean:cartBeanList){
                        bean.setChecked(true);
                        CartManager.getInstance().update(bean);
                    }
                }else{
                    allCheckedFlag = ALL_NOT_CHECKED;
                    for(ShoppingCartBean bean:cartBeanList){
                        bean.setChecked(false);
                        CartManager.getInstance().update(bean);
                    }
                }
                //更新显示
                updateCartData();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        cartRecyclerAdapter = new CartRecyclerAdapter(getActivity(), cartBeanList, new CartRecyclerAdapter.OnCartUpdateListener() {
            @Override
            public void onUpdateChecked(int i) {
                ShoppingCartBean bean = cartBeanList.get(i);
                CartManager.getInstance().update(bean);
                updateCartData();
            }

            @Override
            public void onUpdatecount(int i) {
                ShoppingCartBean bean = cartBeanList.get(i);
                CartManager.getInstance().update(bean);
                updateCartData();
            }
        });
        recyclerView.setAdapter(cartRecyclerAdapter);

        updateCartData();
    }

    //更新一次购物车信息
    private void updateCartData() {
        cartBeanList.clear();
        cartBeanList.addAll(CartManager.getInstance().getAll());
        cartRecyclerAdapter.notifyDataSetChanged();
        //计算已勾选物品的总价
        float sum = getTotalPrice();
        totalPriceTextView.setText("合计：￥"+sum);
    }

    //计算总价
    public float getTotalPrice() {
        float sum=0.0f; //总价的变量
        for(ShoppingCartBean bean:cartBeanList){
            if(bean.isChecked()){
                float price = Float.parseFloat(bean.getPrice());       //先将这个的String转化为float
                sum += bean.getCount()*price;
            }
        }
        return sum;
    }

    //获取放到购物车的信息  还是订单信息
//    public List<ShoppingCartBean> getCartBeanList(){
//        List<ShoppingCartBean>  cartBeanList = new ArrayList<>();
//        return null;
//    }
}
