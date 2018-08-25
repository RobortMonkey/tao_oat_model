package com.taotao.tao_oat.wedget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @package com.taotao.tao_oat.wedget
 * @file VerticalViewpage
 * @date 2018/8/25  下午2:22
 * @autor wangxiongfeng
 */
public class VerticalViewpage extends ViewGroup {

    public VerticalViewpage(@NonNull Context context) {
        super(context);
    }

    public VerticalViewpage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


    }



}
