package com.yunfeng;

import android.app.Activity;
import android.content.Context;

/**
 * MyContext
 * Created by xll on 2018/10/12.
 */
public class MyContext {
    private static MyContext engine;
    private Context context;
    private Class<?> sdkAtivityClass;

    public static MyContext getInstance() {
        if (engine == null) {
            engine = new MyContext();
        }
        return engine;
    }

    public final void setContext(Context context) {
        this.context = context;
    }

    public final Context getContext() {
        return this.context;
    }

    public final Activity getActivity() {
        return (Activity) this.context;
    }

    public final Context getApplicationContext() {
        return this.context.getApplicationContext();
    }

    public final void setSDKActivityClass(Class<?> activityClass) {
        this.sdkAtivityClass = activityClass;
    }

    public final Class<?> getSDKActivityClass() {
        return this.sdkAtivityClass;
    }
}
