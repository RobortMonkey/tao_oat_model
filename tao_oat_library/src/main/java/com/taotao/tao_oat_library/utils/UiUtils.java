package com.taotao.tao_oat_library.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @package com.taotao.tao_oat_library.utils
 * @file UIutils
 * @date 2018/8/29  上午9:20
 * @autor wangxiongfeng
 */
public class UiUtils {
    /**
     * 得到屏幕的高
     *
     * @return
     */

    public static int getScreenHeigh(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static float limitValue(float a, float b) {
        float valve = 0;
        final float min = Math.min(a, b);
        final float max = Math.max(a, b);
        valve = valve > min ? valve : min;
        valve = valve < max ? valve : max;
        return valve;
    }


}
