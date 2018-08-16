package com.taotao.tao_oat.viewpagedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.taotao.tao_oat.R;
import com.taotao.tao_oat.utils;
import com.taotao.tao_oat_library.weight.DirectionalViewPager;

/**
 * @package com.taotao.tao_oat
 * @file ViewPageActivity
 * @date 2018/8/12  下午10:10
 * @autor wangxiongfeng
 * @org www.orbyun.com
 */
public class ViewPageActivity extends AppCompatActivity  implements ViewPager.OnPageChangeListener {

    private DirectionalViewPager mDirectionalViewPager;
    private int mCurrentItem;
    private int mSize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewpage);

        mDirectionalViewPager = (DirectionalViewPager) findViewById(R.id.pager);
        mDirectionalViewPager.setAdapter(new TestFragmentAdapter(
                getSupportFragmentManager()));
        mDirectionalViewPager.setOrientation(DirectionalViewPager.VERTICAL);// 设置方向垂直即可。
        mDirectionalViewPager.setOnPageChangeListener(this);

        int screenHeight = utils.getScreenHeigh();
        Log.i("way", "Screen High = " + screenHeight);
        mSize = (int) (screenHeight * 0.05f);

        Log.i("way", "mSize = " + mSize);
        mCurrentItem = 0;
    }


    @Override
    public void onPageScrollStateChanged(int state) {
        Log.i("way", "onPageScrollStateChanged...  state = " + state
                + ",  mCurrentItem = " + mCurrentItem);
        if (state == ViewPager.SCROLL_STATE_IDLE) {
//            mBg.setY(-mCurrentItem * mSize);
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
//        mBg.setY(-((position + positionOffset) * mSize));
    }

    @Override
    public void onPageSelected(int position) {
        Log.i("way", "onPageSelected....  position=" + position);
        mCurrentItem = position;
    }

}
