package com.atwbin.annotation_demo.utils;

import android.app.Activity;
import android.view.View;

import com.atwbin.annotation_demo.LogUtils.LoggerUtils;
import com.atwbin.annotation_demo.ViewById;

import java.lang.reflect.Field;

/**
 * Author：Created by Wbin on 2018/10/28
 *
 * Description：
 */
public class ViewUtils {

    public static void inject(Activity activity) {
        //1.获取所有的熟悉
        Field[] fields = activity.getClass().getDeclaredFields();
        //2.过滤ViewById的属性
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                //3.findViewById
                View view = activity.findViewById(viewById.value());
                //4.注入
                field.setAccessible(true);
                try {
                    // activity:代表属性所在类   view:代表属性的值
                    field.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }


}
