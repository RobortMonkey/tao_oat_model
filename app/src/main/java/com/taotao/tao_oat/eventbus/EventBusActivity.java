package com.taotao.tao_oat.eventbus;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.taotao.tao_oat.R;
import com.taotao.tao_oat_library.utils.MToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @package com.taotao.tao_oat.eventbus
 * @file EventBusActivity
 * @date 2018/8/16  下午4:02
 * @autor wangxiongfeng
 * @org www.orbyun.com
 */
public class EventBusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        //注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendEvent();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(5000);
                            EventBus.getDefault().post(new MessageTowEvent("扣个一"));

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        }, 2000);
    }




    private void sendEvent() {
        EventBus.getDefault().post(new MessageEvent("hellow  world"));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)   //POSTING    MAIN   BACKGROUND   ASYNC
    public void dealWithEvent(MessageEvent event) {
        MToast.showToast(this, event.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealWithEventTwo(MessageTowEvent event) {
        MToast.showToast(this, event.getMessage());
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }
}
