package com.atwbin.dev.service;

import com.atwbin.dev.service.base.CacheInterceptor;
import com.atwbin.dev.service.base.RequestIntercepter;

import okhttp3.logging.HttpLoggingInterceptor;

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


}
