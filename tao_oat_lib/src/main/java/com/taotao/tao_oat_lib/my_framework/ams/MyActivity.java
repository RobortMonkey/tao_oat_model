package com.taotao.tao_oat_lib.my_framework.ams;

import android.os.IBinder;
import android.os.RemoteException;

import com.taotao.tao_oat_lib.my_framework.IMyAidlInterface;

/**
 * @package com.taotao.tao_oat_lib.my_framework.ams
 * @file MyActivity
 * @date 2019/4/25  5:26 PM
 * @autor wangxiongfeng
 * <p>
 * 客户端
 * <p>
 * 代理类： 构造对象 发送数据
 * <p>
 * 获取到服务对象 就是 poxy 代理类   调用 服务提供到方法
 */
public class MyActivity {

    public void main() throws RemoteException {
        IBinder server = MyActivityManager.getServer();
        IMyAidlInterface anInterface = IMyAidlInterface.Stub.asInterface(server);
        anInterface.basicTypes(0, 0, true, 0.1f, 0.1, "0");
    }

}
