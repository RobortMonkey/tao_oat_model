package com.taotao.tao_oat_lib.net.retrofit;


import com.taotao.tao_oat_lib.net.NetAddressConstant;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author wangxiongfeng
 * @date 2017/7/27 0027 15:00
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());


        try {
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashMap<String, String> cookie = NetAddressConstant.cookie;
                if (cookie == null) {
                    cookie = new HashMap<String, String>();
                }

                for (String header : originalResponse.headers("Set-Cookie")) {
                    if (header.contains("MDSESSION")) {
                        cookie.put("MDSESSION", (header.substring(0, header.indexOf(";")).split("=")[1]));
                    }
                }
                NetAddressConstant.cookie = cookie;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return originalResponse;
    }
}
