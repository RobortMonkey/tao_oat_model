package com.taotao.tao_oat.VerticalViewpage;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @package com.taotao.tao_oat.VerticalViewpage
 * @file ExtendedWebView
 * @date 2018/8/25  下午5:46
 * @autor wangxiongfeng
 */
public class ExtendedWebView extends WebView {
    public ExtendedWebView(Context context) {
        super(context);
    }

    public ExtendedWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean canScrollVertical(int direction) {
        final int offset = computeVerticalScrollOffset();
        final int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (range == 0) return false;
        else return (direction < 0) ? (offset > 0) : (offset < range - 1);
    }
}