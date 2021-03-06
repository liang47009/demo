package com.yunfeng.aop.struct;

import android.util.Log;

import com.yunfeng.Const;

/**
 * cglib
 * Created by xll on 2018/8/24.
 */
public class CGLibAndroidDynamicProxy implements MethodInterceptor {

    private static final CGLibAndroidDynamicProxy instance = new CGLibAndroidDynamicProxy();

    private CGLibAndroidDynamicProxy() {
    }

    public static CGLibAndroidDynamicProxy getInstance() {
        return instance;
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Enhancer.create(clazz, this);
    }

    private void after() {
        Log.d(Const.TAG, "invoke after");
    }

    private void before() {
        Log.d(Const.TAG, "invoke before");
    }

    @Override
    public Object intercept(Object object, Object[] args, MethodProxy methodProxy) throws Exception {
        before();
        Object obj = methodProxy.invokeSuper(object, args);
        after();
        return obj;
    }

//    @Override
//    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//        before();
//        Object obj = proxy.invokeSuper(target, args);
//        after();
//        return obj;
//    }

}
