package com.yunfeng.aop;

import android.util.Log;

/**
 * impl
 * Created by xll on 2018/8/24.
 */
public class GreetingImpl implements Greeting {
    @Override
    public void sayHello() {
        Log.d("app", "hello aop!");
    }
}
