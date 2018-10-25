package com.atwbin.networkchangedemo.simple;

import android.content.Context;

import com.atwbin.networkchangedemo.net.HttpCallBack;

import java.util.Map;

/**
 * Author：Created by Wbin on 2018/10/25
 *
 * Description：
 */
public interface IHttpRequest {

    <T> void post(Context context, Map<String, Object> params, String url, final boolean cache, final HttpCallBack<T> callback);

    <T> void get(Context context, Map<String, Object> params, String url, final boolean cache, final HttpCallBack<T> callback);
}
