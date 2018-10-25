package com.atwbin.dev.service;

import com.atwbin.dev.BuildConfig;
import com.atwbin.dev.DevApplication;
import com.atwbin.dev.service.base.CacheInterceptor;
import com.atwbin.dev.service.base.RequestIntercepter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：Created by Wbin on 2018/10/21
 *
 * Description：构建OkHttpClient
 */
public class RxService {

    private static final int TIMEOUT_READ = 20;
    private static final int TIMEOUT_CONNECTION = 10;

    private static RequestIntercepter requestIntercepter = new RequestIntercepter();
    private static CacheInterceptor cacheInterceptor = new CacheInterceptor();
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    /** Retrofit */
    private static Map<String, Object> retrofitServices = new HashMap<>();
    private static Retrofit mRetrofit;


    private static OkHttpClient getOkHttpClient() {
        return getOkHttpClientBuilder().build();
    }

    private static OkHttpClient.Builder getOkHttpClientBuilder() {
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        File httpCacheDirectory = new File(DevApplication.getInstance().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        return new OkHttpClient.Builder()
                .addNetworkInterceptor(cacheInterceptor)   //缓冲拦截器
                .addInterceptor(requestIntercepter)   //请求拦截器
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .cache(cache)
                .retryOnConnectionFailure(true);  //失败重连

    }

    public static <T> T createApi(Class<T> clazz) {
        return createApi(clazz, NetWorkConfig.WCG_HTTP_SERVER_URL_V3);
    }

    private synchronized static <T> T createApi(Class<T> clazz, String url) {
        T retrofitService;
        Object serviceObj = retrofitServices.get(clazz.getName() + url);
        if (serviceObj != null) {
            retrofitService = (T) serviceObj;
            return retrofitService;
        }
        retrofitService = getRetrofit(url).create(clazz);
        retrofitServices.put(clazz.getName() + url, retrofitService);
        return retrofitService;
    }

    /**
     * 获取Retrofit
     *
     * @param url base url
     * @return Retrofit
     */
    private static Retrofit getRetrofit(String url) {
        if (mRetrofit != null && NetWorkConfig.WCG_HTTP_SERVER_URL_V3.equals(url)) {
            return mRetrofit;
        }
        return mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


    }


}
