package com.taotao.tao_oat_library.net.retrofit;

import com.taotao.tao_oat_library.net.NetAddressConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @package com.taotao.tao_oat_library.net.retrofit
 * @file RetrofitConfig
 * @date 2018/4/26  下午4:59
 * @autor wangxiongfeng
 * @org www.miduo.com
 */
public class RetrofitConfig {


    private static OkHttpClient.Builder builder;
    private static Retrofit.Builder retrofitBuilder;

    private static OkHttpClient getSingleOKhttp() {
        if (builder == null) {
            synchronized (String.class) {
                builder = new OkHttpClient.Builder();

                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);

                builder.retryOnConnectionFailure(true)
                        .connectTimeout(8, TimeUnit.SECONDS)
                        .addNetworkInterceptor(new AddCookiesInterceptor())
                        .addNetworkInterceptor(new ReceivedCookiesInterceptor())
                        .build();

            }
        }
        return builder.build();
    }

    private static Retrofit.Builder getRetrofitInstance() {
        if (retrofitBuilder == null) {
            synchronized (String.class) {
                retrofitBuilder = new Retrofit.Builder();
                retrofitBuilder.baseUrl(NetAddressConstant.BASE_URL)
                        .client(getSingleOKhttp())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
            }

        }
        return retrofitBuilder;

    }

    public static <T> T configRetrofit(Class<T> service) {
        return getRetrofitInstance().build().create(service);
    }

}
