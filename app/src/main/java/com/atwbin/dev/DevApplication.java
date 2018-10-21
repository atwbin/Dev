package com.atwbin.dev;

import android.app.Application;

/**
 * Author：Created by Wbin on 2018/10/19
 *
 * Description：
 */
public class DevApplication extends Application {

    private static DevApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }


    /**
     * 获取 DevApplication单例
     *
     * @return
     */
    public static DevApplication getInstance() {
        return mInstance;
    }


}
