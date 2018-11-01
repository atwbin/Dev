package com.atwbin.weichatpay_demo;

import android.app.Application;

import com.atwbin.joke_annotations.WXPayEntry;
import com.joke_pay.BaseWXPayActivity;

/**
 * Author：Created by Wbin on 2018/11/1
 *
 * Description：
 */
@WXPayEntry(packageName = "com.atwbin.weichatpay_demo",
        entryClass = BaseWXPayActivity.class)
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
