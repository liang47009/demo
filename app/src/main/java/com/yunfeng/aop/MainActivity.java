package com.yunfeng.aop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yunfeng.aop.struct.CGLibAndroidDynamicProxy;
import com.yunfeng.aop.struct.Enhancer;

/**
 * main
 * Created by xll on 2018/8/24.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Greeting greeting = new JDKDynamicProxy(new GreetingImpl()).getProxy();
//        greeting.sayHello();
        String cacheDir = this.getDir("dexfiles", Context.MODE_PRIVATE).getAbsolutePath();
        Enhancer.setCacheDexFilePath(cacheDir);
        Greeting greeting = CGLibAndroidDynamicProxy.getInstance().getProxy(GreetingImpl.class);
        greeting.sayHello();

    }
}
