package com.taotao.tao_oat_lib.my_framework.ams;

import android.os.IBinder;

import com.taotao.tao_oat_lib.my_framework.IMyAidlInterface;

/**
 * @package com.taotao.tao_oat_lib.my_framework.ams
 * @file MyActivityManager
 * @date 2019/4/25  5:26 PM
 * @autor wangxiongfeng
 * 客户端
 */
public class MyActivityManager {

    public static IBinder mIBinder;

    public void main() {


        addService("hellow", new MyActivityManagerServer());
    }

    private void addService(String hellow, Object server) {

    }

    public static IBinder getServer() {
        return mIBinder;
    }
}
