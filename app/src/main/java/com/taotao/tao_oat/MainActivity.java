package com.taotao.tao_oat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.taotao.tao_oat_library.weight.DirectionalViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    List<String> strList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        recycleview.setItemAnimator(new DefaultItemAnimator());
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RecycleViewAdapter adapter = new RecycleViewAdapter(strList);
        recycleview.setAdapter(adapter);
    }


}
