package com.taotao.tao_oat_library.net.retrofit;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import com.taotao.tao_oat_library.net.modle.BaseModle;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @package com.guocuhui.gch.network
 * @file BaseCallBack
 * @date 2017/12/18  下午11:51
 * @autor wangxiongfeng
 * @org www.miduo.com
 */

public abstract class BaseCallBack<T extends BaseModle> implements Callback<T> {
    private Callback<T> callback = null;
    private int login = 0;

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        callback = this;
        if (response.raw().code() == 200) {//200是服务器有合理响应
            if (response.body().state == -1) {
                onAutoLogin(call, this);

            } else if (response.body().state == 1) {
                onSucess(response);

            } else {
                onFail(response.body().msg, 0);

            }

        } else {//失败响应
            onFailure(call, new RuntimeException(response.raw().toString()));
        }


    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof SocketTimeoutException) {
            //
        } else if (t instanceof ConnectException) {
            //
        } else if (t instanceof RuntimeException) {
            //
        }
        t.printStackTrace();
        BaseCallBack.this.onFail("", 0);

    }

    public abstract void onSucess(Response<T> response);

    public abstract void onFail(String msg, int code);

    public void onAutoLogin(final Call<T> failCall, final Callback<T> callback) {
//        SharePrefUtil.saveBoolean(MyApplication.getContext(), SharePrefUtil.IS_LOGIN, false);
//
//        synchronized (String.class) {
//
//            if (SharePrefUtil.getBoolean(MyApplication.getContext(), SharePrefUtil.IS_LOGIN, true)) {
//                failCall.cancel();
//                failCall.clone().enqueue(callback);
//            }
//
//            login++;
//            if (login >= 5) {
////                BaseCallBack.this.onFail("重复登录", 500);
//                return;
//            }
//
//            final String tel = SharePrefUtil.getString(MyApplication.getContext(), SharePrefUtil.TEL_NUMBER, null);
//
////            final String code = SharePrefUtil.getString(MyApplication.getContext(), SharePrefUtil.CODE_NUMBER, null);
//            String tokenvalue = SharePrefUtil.getString(MyApplication.getContext(), SharePrefUtil.TOKENVALUE, null);
//            String lastlogintimestamp = SharePrefUtil.getString(MyApplication.getContext(), SharePrefUtil.LASTLOGINTIMESTAMP, null);
//
//
////            if (StringUtil.isEmpty(tel) || StringUtil.isEmpty(code))
////                return;
//
//            final HashMap<String, String> infoMap = new HashMap<>();
//            infoMap.put("mobile", tel);
//            infoMap.put("token", tokenvalue);
//            infoMap.put("clientId", MyApplication.ANDROID_ID);
//            infoMap.put("lastLoginTimeStamp", lastlogintimestamp);
//
//
//            final Call<BaseModle> login = NetUtil.configRetrofit(NetAPI.AccountService.class).reLogin(infoMap);
//
//            final Call<BaseModle> clone = login.clone();
//            clone.enqueue(new Callback<BaseModle>() {
//
//                @Override
//                public void onResponse(Call<BaseModle> call, Response<BaseModle> response) {
//
//                    if (response.raw().code() == 200) {//200是服务器有合理响应
//                        if (response.body().getState() == -1) {
//
//
//                        } else if (response.body().getState() == 1) {
//
//                            SharePrefUtil.saveBoolean(MyApplication.getContext(), SharePrefUtil.IS_LOGIN, true);
//
//                            LOG.out("ACCOUNT_MOBILE ", "response    " + tel);
//
//                            failCall.cancel();
//                            failCall.clone().enqueue(callback);
//                        } else {
//                            final Activity activity = MyApplication.getInstance().getAppManager().currentActivity();
//
//                            activity.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    MToast.showToast(activity, "当前登录状态失效，将跳转到登录页面重新登录");
//                                    new Handler().postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Intent intent = new Intent();
//                                            intent.setClass(activity, LoginActivity.class);
//                                            intent.putExtra("type", LoginActivity.LOAGIN_AGAIN);
//                                            activity.startActivity(intent);
//                                        }
//                                    }, 200);
//                                }
//                            });
//
//
//                        }
//
//                    } else {//失败响应
//                        onFailure(call, new RuntimeException("response error,detail = " + response.raw().toString()));
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<BaseModle> call, Throwable t) {
//                    BaseCallBack.this.onFail("", 0);
//                    MToast.showToast(MyApplication.getContext(), "当前网络不可用");
//
//                }
//            });
//
//        }
    }


}
