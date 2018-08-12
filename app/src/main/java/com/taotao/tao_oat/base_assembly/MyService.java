package com.taotao.tao_oat.base_assembly;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @package com.taotao.tao_oat
 * @file MyService
 * @date 2018/5/27  下午2:45
 * @autor wangxiongfeng
 * @org www.miduo.com
 */
public class MyService extends Service {
    final MyBinder mBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }
}
