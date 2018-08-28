package com.taotao.tao_oat.productdetail;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taotao.tao_oat.R;
import com.taotao.tao_oat.VerticalViewpage.VerticalViewPagerActivity;

/**
 * @package com.taotao.tao_oat.productdetail
 * @file ProductDetailActivity
 * @date 2018/8/28  上午8:59
 * @autor wangxiongfeng
 */
public class ProductDetailActivity extends AppCompatActivity {
    private View mInflate;
    final int[] imagerStr = {R.drawable.biz_ad_new_version1_img0, R.drawable.biz_ad_new_version1_img1,
            R.drawable.biz_ad_new_version1_img2, R.drawable.biz_ad_new_version1_img3};
    private MPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);


        mViewPager = findViewById(R.id.view_page);
        mPagerAdapter = new MPagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == imagerStr.length + 1) {
                    if (positionOffset > 0.35) {
                        mPagerAdapter.mMindText.setText("松开查看详情");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mViewPager.setCurrentItem(imagerStr.length - 1);
                            }
                        }, 500);
                    } else if (0.35 <= positionOffset && positionOffset < 0) {
                        mViewPager.setCurrentItem(imagerStr.length - 1);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    final class MPagerAdapter extends PagerAdapter {

        public TextView mMindText;
        private View mMInflate;

        @Override
        public int getCount() {
            return imagerStr.length + 1;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (position <= imagerStr.length - 1) {
                View inflate = LayoutInflater.from(ProductDetailActivity.this).inflate(R.layout.item_page, null, false);
                ImageView imageView = inflate.findViewById(R.id.iv);
                imageView.setImageResource(imagerStr[position]);
                container.addView(inflate);
                return inflate;
            } else {

                mMInflate = LayoutInflater.from(ProductDetailActivity.this).inflate(R.layout.item_load_more, null, false);
                mMindText = mMInflate.findViewById(R.id.remind);

                container.addView(mMInflate);
                return mMInflate;
            }
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }


    }

    ;


}
