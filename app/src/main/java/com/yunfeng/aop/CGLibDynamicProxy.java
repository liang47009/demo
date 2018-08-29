package com.yunfeng.aop;

import android.util.Log;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib
 * Created by xll on 2018/8/24.
 */
public class CGLibDynamicProxy implements MethodInterceptor {

    private static final CGLibDynamicProxy instance = new CGLibDynamicProxy();

    private CGLibDynamicProxy() {
    }

    public static CGLibDynamicProxy getInstance() {
        return instance;
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Enhancer.create(clazz, this);
    }

    private void after() {
        Log.d("app", "invoke after");
    }

    private void before() {
        Log.d("app", "invoke before");
    }

//    @Override
//    public Object intercept(Object object, Object[] args, MethodProxy methodProxy) throws Exception {
//        before();
//        Object obj = methodProxy.invokeSuper(object, args);
//        after();
//        return obj;
//    }

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        Object obj = proxy.invokeSuper(target, args);
        after();
        return obj;
    }
}
