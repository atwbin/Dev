package com.atwbin.butterknife;

import android.app.Activity;
import android.view.View;

/**
 * Author：Created by Wbin on 2018/11/1
 *
 * Description：
 */
public class Utils {

    public static final <T extends View> T findViewById(Activity activity, int viewId) {
        return activity.findViewById(viewId);
    }
}
