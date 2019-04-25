package com.taotao.tao_oat_lib.my_framework.ams;

import android.os.RemoteException;

import com.taotao.tao_oat_lib.my_framework.IMyAidlInterface;

/**
 * @package com.taotao.tao_oat_lib.my_framework.ams
 * @file MyActivityManagerServer
 * @date 2019/4/25  5:26 PM
 * @autor wangxiongfeng
 *
 * 服务端
 *
 * 实现 功能函数
 * 开启循环 读取数据 然后 调用函数
 *
 */
public class MyActivityManagerServer extends IMyAidlInterface.Stub{

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }
}
