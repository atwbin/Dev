package com.atwbin.aop_demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author：Created by Wbin on 2018/10/26
 *
 * Description：注解
 * Target  放哪个位置
 * RUNTIME  运行时期
 * CLASS 编译时期
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNet {

//    String value();
}
