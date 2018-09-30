package com.yunfeng.aop;

import android.util.Log;

import com.google.dexmaker.stock.ProxyBuilder;
import com.yunfeng.Const;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * cglib
 * Created by xll on 2018/8/24.
 */
public class DexMakeDynamicProxy implements InvocationHandler {

    private static final DexMakeDynamicProxy instance = new DexMakeDynamicProxy();

    private DexMakeDynamicProxy() {
    }

    public static DexMakeDynamicProxy getInstance() {
        return instance;
    }

    public <T> T getProxy(Class<T> clazz) {
        try {
            return ProxyBuilder.forClass(clazz).handler(this).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void after() {
        Log.d(Const.TAG, "invoke after");
    }

    private void before() {
        Log.d(Const.TAG, "invoke before");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = ProxyBuilder.callSuper(proxy, method, args);
        after();
        return result;
    }

}
