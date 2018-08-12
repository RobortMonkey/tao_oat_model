package com.taotao.tao_oat.base_assembly;

import android.app.IntentService;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by space on 2018/6/5 0005.
 */

public class MyIntentService extends IntentService {
    /**
     * /**
     * <service
     * android:name=".MIntentService"
     * />
     *
     * @param name
     */


    MyIBinder myIBinder=new MyIBinder();
    public MyIntentService(String name) {
        super(name);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myIBinder;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //线程启动后调用该函数 ，执行耗时操作。 可以定义接口 更新主线程ui
    }

    class MyIBinder extends Binder {
        public MyIntentService getService() {
            return MyIntentService.this;
        }
    }

}
