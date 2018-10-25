package com.atwbin.networkchangedemo.net;

import android.content.Context;
import android.text.TextUtils;

import com.atwbin.networkchangedemo.utils.Utils;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Author：Created by Wbin on 2018/10/25
 *
 * Description：
 */
public class HttpRequest {

    private SPHttpCache mHttpCache;

    public HttpRequest() {
        mHttpCache = new SPHttpCache();
    }

    public <T> void get(Context context, String url, Map<String, Object> params, final boolean cache, final HttpCallBack<T> callback) {
        OkHttpClient okHttpClient = new OkHttpClient();

        params.put("version_name", "5.7.0");
        params.put("ac", "wifi");
        params.put("device_id", "30036118478");
        params.put("device_brand", "Xiaomi");
        params.put("update_version_code", "5701");
        params.put("manifest_version_code", "570");
        params.put("longitude", "113.000366");
        params.put("latitude", "28.171377");
        params.put("device_platform", "android");

        final String jointUrl = Utils.jointParams(url, params);  //打印
        final String catchJson =mHttpCache.getCache(jointUrl);

        if (cache&& !TextUtils.isEmpty(catchJson)){
            Gson gson = new Gson();
//            T objResult =  gson.fromJson(catchJson,"");
        }

    }
}
