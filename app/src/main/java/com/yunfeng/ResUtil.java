package com.yunfeng;

import android.content.Context;
import android.util.Log;

/**
 * res
 * Created by xll on 2018/10/12.
 */
public class ResUtil {

    public static int getResId(String name, String type) {
        Context context = MyContext.getInstance().getContext();
        if (context == null) {
            Log.e(Const.TAG, "context of getResId is null");
            return 0;
        }
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }

    public static int getLayoutId(String name) {
        return getResId(name, "layout");
    }

    public static int getViewId(String name) {
        return getResId(name, "id");
    }

    public static int getDrawableId(String name) {
        return getResId(name, "drawable");
    }

    public static int getStringId(String name) {
        return getResId(name, "string");
    }

    public static String getString(String name) {
        Context context = MyContext.getInstance().getContext();
        String ret = "";
        if (context == null) {
            Log.e(Const.TAG, "context of getString is null");
        } else {
            int id = getResId(name, "string");
            if (id == 0) {
                Log.e(Const.TAG, String.format("identifier string, name:%s not found!", name));
            } else {
                ret = context.getString(id);
            }
        }
        return ret;
    }
}