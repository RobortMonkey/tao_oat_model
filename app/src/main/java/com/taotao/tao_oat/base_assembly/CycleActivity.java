package com.taotao.tao_oat.base_assembly;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

/**
 * @package com.taotao.tao_oat
 * @file CycleActivity
 * @date 2018/5/27  下午2:43
 * @autor wangxiongfeng
 * @org www.miduo.com
 */
public class CycleActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri parse = Uri.parse("com.taotao.tao_oat.base_asembly.MyContentProvider");
        Cursor query = getContentResolver().query(parse, null, null, null, null);
        getContentResolver().registerContentObserver(parse, true, new ContentObserver(new Handler()) {
            @Override
            public boolean deliverSelfNotifications() {
                return super.deliverSelfNotifications();
            }

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
            }
        });

        Intent intent = new Intent(this, MyIntentService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyIntentService myIntentService = ((MyIntentService.MyIBinder) service).getService();

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);


        //本地广播

        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(this);
        instance.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        },new IntentFilter("com.taotao.tao_oat.base_assembly.LocalBroadcastManager"));


        Intent intentBroadcast=new Intent();
        intentBroadcast.setAction("com.taotao.tao_oat.base_assembly.LocalBroadcastManager");
        instance.sendBroadcast(intentBroadcast);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
