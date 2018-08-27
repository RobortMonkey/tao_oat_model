package com.taotao.tao_oat.VerticalViewpage;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.taotao.tao_oat.R;

/**
 * @package com.taotao.tao_oat.VerticalViewpage
 * @file VerticalViewPagerActivity
 * @date 2018/8/27  下午4:48
 * @autor wangxiongfeng
 */
public class VerticalViewPagerActivity extends AppCompatActivity {
    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.75f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage);
        final int[] imagerStr = {R.drawable.biz_ad_new_version1_img0, R.drawable.biz_ad_new_version1_img1,
                R.drawable.biz_ad_new_version1_img2, R.drawable.biz_ad_new_version1_img3};

        VerticalViewpagerNo3 verticalViewPager = (VerticalViewpagerNo3) findViewById(R.id.verticalviewpager);

        verticalViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imagerStr.length;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View inflate = LayoutInflater.from(VerticalViewPagerActivity.this).inflate(R.layout.item_page, null, false);
                ImageView imageView = inflate.findViewById(R.id.iv);
                imageView.setImageResource(imagerStr[position]);
                container.addView(inflate);
                return inflate;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                container.removeViewAt(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        verticalViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.pagemargin));
        verticalViewPager.setPageMarginDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_dark)));

//        verticalViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
//            @Override
//            public void transformPage(View view, float position) {
//                int pageWidth = view.getWidth();
//                int pageHeight = view.getHeight();
//
//                if (position < -1) { // [-Infinity,-1)
//                    // This page is way off-screen to the left.
//                    view.setAlpha(0);
//
//                } else if (position <= 1) { // [-1,1]
//                    // Modify the default slide transition to shrink the page as well
//                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
//                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
//                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
//                    if (position < 0) {
//                        view.setTranslationY(vertMargin - horzMargin / 2);
//                    } else {
//                        view.setTranslationY(-vertMargin + horzMargin / 2);
//                    }
//
//                    // Scale the page down (between MIN_SCALE and 1)
//                    view.setScaleX(scaleFactor);
//                    view.setScaleY(scaleFactor);
//
//                    // Fade the page relative to its size.
//                    view.setAlpha(MIN_ALPHA +
//                            (scaleFactor - MIN_SCALE) /
//                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));
//
//                } else { // (1,+Infinity]
//                    // This page is way off-screen to the right.
//                    view.setAlpha(0);
//                }
//            }
//        });

    }
}
