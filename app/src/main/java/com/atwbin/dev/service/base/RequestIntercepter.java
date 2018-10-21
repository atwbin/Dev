package com.atwbin.dev.service.base;

import android.text.TextUtils;

import com.atwbin.dev.DevApplication;
import com.atwbin.dev.utils.LoggerUtils;
import com.atwbin.dev.utils.ParamsSignUtil;
import com.atwbin.dev.utils.SystemUtils;
import com.atwbin.dev.utils.Tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author：Created by Wbin on 2018/10/21
 *
 * Description：网络请求拦截器
 */
public class RequestIntercepter implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = onHttpRequestBefore(request);
        Response originalResponse;

        try {
            originalResponse = chain.proceed(request);
            saveCookie(originalResponse);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return originalResponse;
    }

    /**
     * 保存cookie
     *
     * @param originalResponse 原始返回值
     */
    private void saveCookie(Response originalResponse) {
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            List<String> cooikes = originalResponse.headers("Set-Cookie");
            for (String cooike : cooikes) {
                if (cooike.contains("SESSION")) {
                    String sessionId = cooike.split(";")[0];
                }
            }
        }
    }

    /**
     * 发起请求之前
     * 在这里做一些请求服务器的的操作，比如：添加统一的参数和Header或者对参数进行签名
     *
     * @param request
     * @return request
     */
    private Request onHttpRequestBefore(Request request) {
        return processParams(request);
    }

    /**
     * 处理请求参数，比如对请求参数进行签名、添加公用的参数
     *
     * @param originRequest 请求
     * @return
     */
    private Request processParams(Request originRequest) {
        Request.Builder newRequest = originRequest.newBuilder();

        //Header
        Headers.Builder newHeaderBuilder = originRequest.headers().newBuilder();
        Map<String, String> headerMap = getHeaderMap();
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                newHeaderBuilder.add(entry.getKey(), entry.getValue());
            }
            newRequest.headers(newHeaderBuilder.build());
        }

        //query Param
        if ("GET".equals(originRequest.method())) {
            HttpUrl.Builder newUrlBuilder = originRequest.url().newBuilder();
            Map<String, String> queryParamMap = getQueryParamMap(originRequest.url());
            if (queryParamMap != null && !queryParamMap.isEmpty()) {
                for (Map.Entry<String, String> entry : queryParamMap.entrySet()) {
                    newUrlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
                }
                newRequest.url(newUrlBuilder.build());
            }
        } else if ("POST".equals(originRequest.method())) {
            RequestBody body = originRequest.body();
            if (body != null && body instanceof FormBody) {
                // POST Param x-www-form-urlencoded
                FormBody formBody = (FormBody) body;
                Map<String, String> formBodyParamMap = new HashMap<>();
                int bodySize = formBody.size();
                for (int i = 0; i < bodySize; i++) {
                    formBodyParamMap.put(formBody.name(i), formBody.value(i));
                }

                Map<String, String> newFormBodyParamMap = getFormBodyParamMap();
                if (newFormBodyParamMap != null) {
                    formBodyParamMap.putAll(newFormBodyParamMap);
                    FormBody.Builder bodyBuilder = new FormBody.Builder();
                    for (Map.Entry<String, String> entry : formBodyParamMap.entrySet()) {
                        bodyBuilder.add(entry.getKey(), entry.getValue());
                    }
                    newRequest.method(originRequest.method(), bodyBuilder.build());
                }
            } else if (body != null && body instanceof MultipartBody) {
                // POST Param form-data
                MultipartBody multipartBody = (MultipartBody) body;
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                Map<String, String> extraFormBodyParamMap = getFormBodyParamMap();
                for (Map.Entry<String, String> entry : extraFormBodyParamMap.entrySet()) {
                    builder.addFormDataPart(entry.getKey(), entry.getValue());
                }
                List<MultipartBody.Part> parts = multipartBody.parts();
                for (MultipartBody.Part part : parts) {
                    builder.addPart(part);
                }
                newRequest.post(builder.build());
            }
        }

        return newRequest.build();
    }

    /**
     * get 请求添加通过参数
     *
     * @return postParams
     */
    private Map<String, String> getQueryParamMap(HttpUrl url) {
        Map<String, String> params = new HashMap<>();
        String s = url.encodedQuery();
        params.put("clienttype", "ANDROID");
        if (TextUtils.isEmpty(s)) {
            s = "";
            s = s.concat("clienttype=ANDROID");
        } else {
            s = s.concat("&clienttype=ANDROID");
        }
        params.put("signature", ParamsSignUtil.createSignture(s));
        return null;
    }

    /**
     * post 请求添加通过参数
     *
     * @return postParams
     */
    private Map<String, String> getFormBodyParamMap() {
        Map<String, String> postParams = new HashMap<>();
        return postParams;
    }

    /**
     * 添加公共的Header
     *
     * @return header
     */
    private Map<String, String> getHeaderMap() {
        Map<String, String> headers = new HashMap<>();
        String headersRaw = Tools.getInstance().token;
        if (!TextUtils.isEmpty(headersRaw)) {
            headers.put("WCGAUTH", headersRaw);
        }

        try {
            headers.put("WCGVCODE", String.valueOf(SystemUtils.getVersionCode(DevApplication.getInstance())));
            headers.put("device_uuid", SystemUtils.getUniquePsuedoID());

        } catch (Exception e) {
            LoggerUtils.d("添加header失败!" + e.getMessage());
        }
        return headers;
    }
}
