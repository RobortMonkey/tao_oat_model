package com.taotao.tao_oat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.taotao.tao_oat.modle.ItemBean;
import com.taotao.tao_oat_library.weight.DirectionalViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    List<ItemBean> strList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        strList.add(new ItemBean("ViewPage 垂直滑动", 0));
        strList.add(new ItemBean("Android 四大组件", 1));
        strList.add(new ItemBean("EventBus 使用", 2));
        strList.add(new ItemBean("View 绘制", 3));
        strList.add(new ItemBean("FFmpeg 处理", 4));
        strList.add(new ItemBean("图片处理", 5));
        strList.add(new ItemBean("MPAndroidChart", 6));
        strList.add(new ItemBean("商品详情页面构建", 7));
        strList.add(new ItemBean("DragLayout", 8));


        recycleview.setItemAnimator(new DefaultItemAnimator());
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RecycleViewAdapter adapter = new RecycleViewAdapter(strList,this);
        recycleview.setAdapter(adapter);
    }


}
