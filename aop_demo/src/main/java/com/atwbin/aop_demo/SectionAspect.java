package com.atwbin.aop_demo;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.atwbin.aop_demo.util.LoggerUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Author：Created by Wbin on 2018/10/26
 *
 * Description：处理切点
 */
@Aspect
public class SectionAspect {

    /**
     * 找到处理的切点
     * .. 表示可以处理所有的方法
     */
    @Pointcut("execution(@com.atwbin.aop_demo.CheckNet * *(..))")
    public void checkNetBehavoir() {

    }

    /**
     * 处理切面
     *
     * @return
     */
    @Around("checkNetBehavoir()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        LoggerUtils.d("checkNet");
        //此处做日志上传、权限检测....
        //1.获取CheckNet注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckNet checkNet = signature.getMethod().getAnnotation(CheckNet.class);
        //2.判断有没有网络
        if (checkNet != null) {
            Object object = joinPoint.getThis();  //View Activity  Fragment
            Context context = getContext(object);
            if (context != null) {
                if (!isConnected(BaseApplication.getInstance().getApplicationContext())) {
                    //3.没有网络  不往下进行
                    Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "请检测网络", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }

        }
        return joinPoint.proceed();
    }

    /**
     * 通过对象获取上下文
     *
     * @param object
     * @return
     */
    private Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }

    /**
     * 判断网络是否连接
     */
    private boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == cm) {
            return false;
        }

        NetworkInfo info = cm.getActiveNetworkInfo();
        if (null != info && info.isConnected()) {
            if (info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }
}
