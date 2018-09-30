package com.yunfeng;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * skin
 * Created by xll on 2018/9/30.
 */
public class SkinFactoryTwo implements LayoutInflater.Factory2 {
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        Log.d(Const.TAG, "onCreateView with parent: " + name);
        return null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        Log.d(Const.TAG, "onCreateView no parent: " + name);
        return null;
    }
}
