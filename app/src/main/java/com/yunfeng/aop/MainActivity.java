package com.yunfeng.aop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * main
 * Created by xll on 2018/8/24.
 */
public class MainActivity extends Activity {

    private Greeting greeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.setProperty("dexmaker.dexcache", this.getDir("dx", Context.MODE_PRIVATE).getAbsolutePath());
//        Greeting greeting = new JDKDynamicProxy(new GreetingImpl()).getProxy();
//        greeting.sayHello();
//        String cacheDir = this.getDir("dexfiles", Context.MODE_PRIVATE).getAbsolutePath();
//        Enhancer.setCacheDexFilePath(cacheDir);
//        Greeting greeting = CGLibAndroidDynamicProxy.getInstance().getProxy(GreetingImpl.class);
//        greeting.sayHello();

        DexMakeDynamicProxy.getInstance().autoWired(GreetingImpl.class);

        greeting.sayHello();
    }
}
