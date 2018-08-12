package com.taotao.tao_oat_library.net.retrofit;


import com.taotao.tao_oat_library.net.NetAddressConstant;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wangxiongfeng
 * @date 2017/7/27 0027 14:59
 */

public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        try {
            HashMap<String, String> preferences = NetAddressConstant.cookie;
            if (preferences != null) {
                builder.addHeader("Cookie", "MDSESSION=" + preferences.get("MDSESSION"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return chain.proceed(builder.build());
    }
}
