package com.idaniu.maga.shopping.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idaniu.maga.shopping.R;
import com.idaniu.maga.shopping.fragment.Fragment1;
import com.idaniu.maga.shopping.fragment.Fragment2;
import com.idaniu.maga.shopping.fragment.Fragment3;
import com.idaniu.maga.shopping.fragment.Fragment4;
import com.idaniu.maga.shopping.fragment.Fragment5;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 主页
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;       //用来展示五个fragment的ViewPager
    private FragmentPagerAdapter mAdaper;   //FragmentPager的适配器
    private List<Fragment> fragmentList;	//用来存放几个Fragment

    private LinearLayout ll1;   //五个标题栏
    private LinearLayout ll2;
    private LinearLayout ll3;
    private LinearLayout ll4;
    private LinearLayout ll5;

    //标题栏中的图片和文字控件
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    //布局初始化
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentList = new ArrayList<Fragment>();

        //5个标题，并设置监听
        ll1 = (LinearLayout) findViewById(R.id.ll_1); //下面标题栏的布局，找到这个容器用于添加数字
        ll2 = (LinearLayout) findViewById(R.id.ll_2);
        ll3 = (LinearLayout) findViewById(R.id.ll_3);
        ll4 = (LinearLayout) findViewById(R.id.ll_4);
        ll5 = (LinearLayout) findViewById(R.id.ll_5);
        //为点击这几个标题设置监听
        //创建几个Fragment的实例 添加到fragmentList中
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        Fragment4 fragment4 = new Fragment4();
        Fragment5 fragment5 = new Fragment5();
        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        ll3.setOnClickListener(this);
        ll4.setOnClickListener(this);
        ll5.setOnClickListener(this);

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);
        fragmentList.add(fragment5);

        initTab();  //标题里的控件初始化

        //对Adapter进行设置
        mAdaper = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };

        //为viewpager控件添加适配器
        mViewPager.setAdapter(mAdaper);

        //页面改变监听事件，标题字体的颜色对应着发生变化
        /*mViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                resetTabView();	//先初始化

                switch (position) {
                    case 0:
                        iv1.setImageDrawable(getResources().getDrawable(R.drawable.tab_iv_1_red));
                        tv1.setTextColor(Color.RED);
                        break;
                    case 1:
                        iv2.setImageDrawable(getResources().getDrawable(R.drawable.tab_iv_2_red));
                        tv2.setTextColor(Color.RED);
                        break;
                    case 2:
                        iv3.setImageDrawable(getResources().getDrawable(R.drawable.tab_iv_3_red));
                        tv3.setTextColor(Color.RED);
                        break;
                    case 3:
                        iv4.setImageDrawable(getResources().getDrawable(R.drawable.tab_iv_4_red));
                        tv4.setTextColor(Color.RED);
                        break;
                    case 4:
                        iv5.setImageDrawable(getResources().getDrawable(R.drawable.tab_iv_5_red));
                        tv5.setTextColor(Color.RED);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                //arg0 为position页面码, float arg1为offset页面翻转完成百分比, int arg2为offsetPx页面翻转完成的像素值


             /*
                Log.d("tabline", arg0 + "," + arg1 + "," + arg2);
                //设置tabline的左边距离
                LinearLayout.LayoutParams lllp = (android.widget.LinearLayout.LayoutParams) tabLine.getLayoutParams();
                lllp.leftMargin = (int) (arg0*screen1_3 + arg1*screen1_3);
                tabLine.setLayoutParams(lllp);
             */
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    //标题栏里的控件引用初始化
    private void initTab() {
        iv1 = (ImageView)findViewById(R.id.iv_1);
        iv2 = (ImageView)findViewById(R.id.iv_2);
        iv3 = (ImageView)findViewById(R.id.iv_3);
        iv4 = (ImageView)findViewById(R.id.iv_4);
        iv5 = (ImageView)findViewById(R.id.iv_5);

        tv1 = (TextView)findViewById(R.id.tv_1);
        tv2 = (TextView)findViewById(R.id.tv_2);
        tv3 = (TextView)findViewById(R.id.tv_3);
        tv4 = (TextView)findViewById(R.id.tv_4);
        tv5 = (TextView)findViewById(R.id.tv_5);

        //默认状态。第一个页面
        resetTabView();
        iv1.setImageDrawable(getResources().getDrawable(R.drawable.tab_iv_1_red));
        tv1.setTextColor(Color.RED);
    }

    //标题栏显示初始状态：图片、文字。每次切换时首先恢复初始状态
    private void resetTabView() {
        iv1.setImageDrawable(getResources().getDrawable(R.drawable.tab_iv_1_gray));
        iv2.setImageDrawable(getResources().getDrawable(R.drawable.tab_iv_2_gray));
        iv3.setImageDrawable(getResources().getDrawable(R.drawable.tab_iv_3_gray));
        iv4.setImageDrawable(getResources().getDrawable(R.drawable.tab_iv_4_gray));
        iv5.setImageDrawable(getResources().getDrawable(R.drawable.tab_iv_5_gray));

        tv1.setTextColor(Color.GRAY);
        tv2.setTextColor(Color.GRAY);
        tv3.setTextColor(Color.GRAY);
        tv4.setTextColor(Color.GRAY);
        tv5.setTextColor(Color.GRAY);

    }

    //点击下面标题栏显示不同页面
    @Override
    public void onClick(View v) {
        int index = 0;	//所在页面索引值

        switch (v.getId()) {
            case R.id.ll_1:
                index = 0;
                break;
            case R.id.ll_2:
                index = 1;
                break;
            case R.id.ll_3:
                index = 2;
                break;
            case R.id.ll_4:
                index = 3;
                break;
            case R.id.ll_5:
                index = 4;
                break;
            default:
                break;
        }

        mViewPager.setCurrentItem(index);
    }
}
