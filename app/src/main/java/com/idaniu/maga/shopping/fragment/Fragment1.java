package com.idaniu.maga.shopping.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.idaniu.maga.shopping.R;

/**
 * 第一个fragment，展示主页
 * Created by yuanbao15 on 2017/10/8.
 */
public class Fragment1 extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag1, container, false);
    }
}
