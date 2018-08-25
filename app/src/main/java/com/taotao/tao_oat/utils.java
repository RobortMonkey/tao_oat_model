package com.taotao.tao_oat;

import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by space on 2018/7/31 0031.
 */

public class utils {
    public int binarySearch(int[] arr, int key, int low, int hight) {
        if (key < arr[low] || key > arr[hight] || low > hight) {
            return -1;
        }
        int middle = (low + hight) / 2;
        if (arr[middle] < key) {
            return binarySearch(arr, key, low, middle - 1);
        } else if (arr[middle] > key) {
            return binarySearch(arr, key, middle + 1, low);
        } else {
            return middle;
        }
    }

    public void insertSortNo2(int array[]) {
        int j, i, t = 0;
        for (i = 1; i < array.length; i++) {
            t = array[i];
            for (j = i - 1; j >= 0 && t < array[j]; j++) {
                array[j + 1] = array[j];
            }
            array[j + 1] = t;
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeigh() {
        try {
            Class mainClass = Class.forName("com.android.internal.R$dimen");
            Object localObject = mainClass.newInstance();
            int statusBarHeightId = Integer.parseInt(mainClass
                    .getField("status_bar_height").get(localObject).toString());
            int statusBarHeight = MyApplication.getInstance().getResources().getDimensionPixelSize(
                    statusBarHeightId);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MyApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.status_bar_height);
    }

    /**
     * 得到屏幕的高
     *
     * @return
     */

    public static int getScreenHeigh() {
        DisplayMetrics dm = MyApplication.getInstance().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

}
