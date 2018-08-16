package com.taotao.tao_oat;

import android.app.Application;

/**
 * @package com.taotao.tao_oat
 * @file MyApplication
 * @date 2018/8/12  下午10:04
 * @autor wangxiongfeng
 * @org www.orbyun.com
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public static MyApplication getInstance() {
        return instance;
    }
}
