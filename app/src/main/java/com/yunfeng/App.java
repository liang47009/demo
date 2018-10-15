package com.yunfeng;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * app
 * Created by xll on 2018/9/30.
 */
public class App extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        System.setProperty("dexmaker.dexcache", this.getDir("dx", Context.MODE_PRIVATE).getAbsolutePath());
    }
}
