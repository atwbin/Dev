package com.atwbin.networkchangedemo.net;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;


/**
 * Author：Created by Wbin on 2018/10/25
 *
 * Description：
 */
public class HttpUtils {

    private HttpRequest mHttpRequest;
    private final int TYPE_POST = 0x0011, TYPE_GET = 0x0022;
    private int mType = TYPE_GET;
    private Map<String, Object> mParams;
    private String mUrl;
    private boolean mCatche;
    private Context mContext;

    public HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils get() {
        mType = TYPE_GET;
        return this;
    }

    private HttpUtils(Context context) {
        mHttpRequest = new HttpRequest();
        mParams = new HashMap<>();
        this.mContext = context;

    }

    public HttpUtils params(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    public HttpUtils catche(boolean catche) {
        mCatche = catche;
        return this;
    }

    public <T> void request(final HttpCallBack<T> callBack) {
        mHttpRequest.get(mContext, mUrl, mParams, true, callBack);
    }

    public <T> void get(Context context, String url, Map<String, Object> params, final boolean cache, final HttpCallBack<T> callback) {
        mHttpRequest.get(context, url, params, cache, callback);
    }


}

