package com.atwbin.dev.utils;

/**
 * Author：Created by Wbin on 2018/10/21
 *
 * Description：
 */
public class Tools {

    private static Tools instance;
    public String token;

    public Tools() {
    }

    public static Tools getInstance() {
        if (instance == null) {
            instance = new Tools();
        }
        return instance;
    }

}
