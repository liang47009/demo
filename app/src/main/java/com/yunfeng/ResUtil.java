package com.yunfeng;

import android.content.Context;
import android.util.Log;

/**
 * res
 * Created by xll on 2018/10/12.
 */
public class ResUtil {

    public static final int getResId(String name, String type) {
        Context context = MyContext.getInstance().getContext();
        if (context == null) {
            Log.e(Const.TAG, "context of getResId is null");
            return 0;
        }
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }

    public static final int getLayoutId(String name) {
        return getResId(name, "layout");
    }

    public static final int getViewId(String name) {
        return getResId(name, "id");
    }

    public static final int getDrawableId(String name) {
        return getResId(name, "drawable");
    }

    public static final int getStringId(String name) {
        return getResId(name, "string");
    }

    public static final String getString(String name) {
        Context context = MyContext.getInstance().getContext();
        if (context == null) {
            Log.e(Const.TAG, "context of getString is null");
            return "";
        }
        return context.getString(getResId(name, "string"));
    }
}