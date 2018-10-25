package com.atwbin.networkchangedemo.net;

/**
 * Author：Created by Wbin on 2018/10/25
 *
 * Description：
 */
public class PreferenceUtils {

    private static PreferenceUtils mInstance;

    public static PreferenceUtils getInstance() {
        if (mInstance == null) {
            mInstance = new PreferenceUtils();
        }
        return mInstance;
    }

    public Object getObject(Object object) {
        return object;
    }

    public void saveParams(String url, String json) {
    }

}
