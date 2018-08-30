package com.yunfeng.aop;

import android.util.Log;

import com.google.dexmaker.stock.ProxyBuilder;

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

    private final HashMap<String, Object> map = new HashMap<>(16);

    private DexMakeDynamicProxy() {
    }

    public static DexMakeDynamicProxy getInstance() {
        return instance;
    }

    private <T> T getProxy(Class<T> clazz) {
        try {
            return ProxyBuilder.forClass(clazz).handler(this).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void after() {
        Log.d("app", "invoke after");
    }

    private void before() {
        Log.d("app", "invoke before");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object o = map.get(proxy.getClass().getName());
        Object result = null;
        if (o != null) {
            before();
            result = ProxyBuilder.callSuper(proxy, method, args);
            after();
        }
        return result;
    }

    public void autoWired(Class<?> clazz) {
        Object o = getProxy(clazz);
        if (o != null) {
            map.put(o.getClass().getName(), o);
        }
    }
}