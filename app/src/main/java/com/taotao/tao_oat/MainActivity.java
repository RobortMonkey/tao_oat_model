package com.taotao.tao_oat;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//
//        //广播的静态注册
//        Intent intent = new Intent();
//        intent.setAction("com.dsw.send");
//        intent.putExtra("data", "data");
//        sendBroadcast(intent);
//
//
//        //动态注册广播 组件级别的广播，当activity finish 后将无法接受到广播 升级到应用级别 需要设为 静态类 然后在 组册
//        IntentFilter filter=new IntentFilter();
//        filter.addAction("com.taotao.tao_oat.broadcaset");
//        registerReceiver(new MyInnerBroadcastReceiver(),filter);
//
//
//    }
//
//    //广播的动态注册
//    //创建 广播存内部类
//    class MyInnerBroadcastReceiver extends BroadcastReceiver{
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//        }
//    }
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.taotao.tao_oat_library.weight.DirectionalViewPager;


@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements
        OnPageChangeListener {
    private DirectionalViewPager mDirectionalViewPager;
    private int mSize;
    private int mCurrentItem;
    private ImageView mBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|;
//
//        }




        setContentView(R.layout.activity_main);
        // Set up the pager
        mDirectionalViewPager = (DirectionalViewPager) findViewById(R.id.pager);
        mBg = (ImageView) findViewById(R.id.mainBgImage);
        mDirectionalViewPager.setAdapter(new TestFragmentAdapter(
                getSupportFragmentManager()));
        mDirectionalViewPager.setOrientation(DirectionalViewPager.VERTICAL);// 设置方向垂直即可。
        mDirectionalViewPager.setOnPageChangeListener(this);

        int screenHeight = getScreenHeigh();
        Log.i("way", "Screen High = " + screenHeight);
        mSize = (int) (screenHeight * 0.05f);

        Log.i("way", "mSize = " + mSize);
        mCurrentItem = 0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //super.onSaveInstanceState(outState);
    }
    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeigh() {
        try {
            Class mainClass = Class.forName("com.android.internal.R$dimen");
            Object localObject = mainClass.newInstance();
            int statusBarHeightId = Integer.parseInt(mainClass
                    .getField("status_bar_height").get(localObject).toString());
            int statusBarHeight = getResources().getDimensionPixelSize(
                    statusBarHeightId);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getResources().getDimensionPixelSize(R.dimen.status_bar_height);
    }

    public int getScreenHeigh() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.i("way", "onPageScrollStateChanged...  state = " + state
                + ",  mCurrentItem = " + mCurrentItem);
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            mBg.setY(-mCurrentItem * mSize);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        Log.i("way", "onPageScrolled...  position=" + position
                + ", positionOffset=" + positionOffset
                + ", positionOffsetPixels=" + positionOffsetPixels);
        if (positionOffset == 0.0f)
            return;
        mBg.setY(-((position + positionOffset) * mSize));
    }

    @Override
    public void onPageSelected(int position) {
        Log.i("way", "onPageSelected....  position=" + position);
        mCurrentItem = position;
    }
}
