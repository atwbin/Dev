package com.atwbin.butterknife;

import android.support.annotation.UiThread;

/**
 * Author：Created by Wbin on 2018/11/1
 *
 * Description：
 */
public interface Unbinder {

    @UiThread
    void unbind();


    Unbinder EMPTY = new Unbinder() {
        @Override
        public void unbind() {

        }
    };

}
