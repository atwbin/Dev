package com.atwbin.butterknife;

import android.app.Activity;

import java.lang.reflect.Constructor;

/**
 * Author：Created by Wbin on 2018/10/30
 *
 * Description：
 */
public class ButterKnife {

    private ButterKnife() {
        throw new AssertionError("No instances.");
    }

    public static Unbinder bind(Activity activity) {

        String viewBindClassName = activity.getClass().getName() + "_ViewBinding";
        try {
            Class<? extends Unbinder> viewBindingClass = (Class<? extends Unbinder>) Class.forName(viewBindClassName);
            Constructor<? extends Unbinder> viewBindingConstructor = viewBindingClass.getDeclaredConstructor(activity.getClass());
            Unbinder unbinder = viewBindingConstructor.newInstance(activity);
            return unbinder;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Unbinder.EMPTY;
    }
}
