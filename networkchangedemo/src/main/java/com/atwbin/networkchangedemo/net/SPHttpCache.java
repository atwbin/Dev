package com.atwbin.networkchangedemo.net;

/**
 * Author：Created by Wbin on 2018/10/25
 *
 * Description：
 */
public class SPHttpCache {

    public void saveCache(String finalUrl, String resultJson) {
        PreferenceUtils.getInstance().saveParams(finalUrl, resultJson);
    }


    public String getCache(String finalUrl) {

        return (String) PreferenceUtils.getInstance().getObject(finalUrl);
    }


}

