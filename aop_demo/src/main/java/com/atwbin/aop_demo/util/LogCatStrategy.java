package com.atwbin.aop_demo.util;

import android.util.Log;

import com.orhanobut.logger.LogStrategy;

/**
 * Author：Created by Wbin on 2018/10/26
 *
 * Description：
 */
public class LogCatStrategy implements LogStrategy {


    @Override
    public void log(int priority, String tag, String message) {
        Log.println(priority, randomKey() + tag, message);
    }

    private int last;

    private String randomKey() {
        int random = (int) (10 * Math.random());
        if (random == last) {
            random = (random + 1) % 10;
        }
        last = random;
        return String.valueOf(random);
    }
}
