package com.atwbin.dev.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.atwbin.dev.DevApplication;

/**
 * Author：Created by Wbin on 2018/10/19
 *
 * Description：网络工具
 */
public class NetWorkUtils {


    /**
     * 检测是否有网
     * @return  boolean
     */
    public static boolean isNetWorkConnected() {

        ConnectivityManager manager = (ConnectivityManager) DevApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo==null||networkInfo.isAvailable()){
            return  false;
        }
        return true;
    }

}
