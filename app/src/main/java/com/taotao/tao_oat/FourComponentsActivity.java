package com.taotao.tao_oat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @package com.taotao.tao_oat
 * @file FourComponents
 * @date 2018/8/12  下午10:25
 * @autor wangxiongfeng
 * @org www.orbyun.com
 */
public class FourComponentsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_com);
        //广播的静态注册
        Intent intent = new Intent();
        intent.setAction("com.dsw.send");
        intent.putExtra("data", "data");
        sendBroadcast(intent);


        //动态注册广播 组件级别的广播，当activity finish 后将无法接受到广播 升级到应用级别 需要设为 静态类 然后在 组册
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.taotao.tao_oat.broadcaset");
        registerReceiver(new MyInnerBroadcastReceiver(),filter);
//
    }

    //广播的动态注册
//    //创建 广播存内部类
    class MyInnerBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

}
