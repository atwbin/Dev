package com.atwbin.annotation_demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author：Created by Wbin on 2018/10/28
 *
 * Description： @interface代表注解
 * Target 表示放在哪里  FIELD：属性   METHOD：方法   TYPE:类
 * Retention(RetentionPolicy.RUNTIME)表示什么时候起作用    RUNTIME：运行时，  ClASS：编译时
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {

    int value(); // 表示不放参数
//    int[] value(); // 表示放多个参数
//    String value();    //表示放的类型 String
}
