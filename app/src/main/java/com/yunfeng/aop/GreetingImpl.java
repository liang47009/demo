package com.yunfeng.aop;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.yunfeng.Const;
import com.yunfeng.demo.R;

/**
 * impl
 * Created by xll on 2018/8/24.
 */
public class GreetingImpl implements Greeting {
    @Override
    public void sayHello(Context context) {
        Log.d(Const.TAG, "hello aop!");
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            TextView textView = activity.findViewById(R.id.greeting_tv);
            textView.setText("hello aop: " + this.getClass().getName());
        }
    }
}
