package com.taotao.tao_oat_library.weight;

import android.support.v4.view.PagerAdapter;

/**
 * Created by space on 2018/7/17 0017.
 */

public final class VerticalViewPagerCompat {
    private VerticalViewPagerCompat() {
    }

    public static void setDataSetObserver(PagerAdapter adapter, DataSetObserver observer) {
        adapter.registerDataSetObserver(observer);
    }

    public static class DataSetObserver extends android.database.DataSetObserver {

    }
}