package com.yunfeng.demo.utils;

import android.content.Context;

/**
 * ui utils
 * Created by xll on 2018/7/30.
 */
public class UIUtils {
    public static int getIdentifier(Context context, String name, String type) {
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }
}
