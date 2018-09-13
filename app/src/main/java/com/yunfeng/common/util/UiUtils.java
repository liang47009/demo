package com.yunfeng.common.util;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * ui
 * Created by xll on 2018/9/11.
 */
public class UiUtils {

    public static void createButton(LinearLayout layout, String name, View.OnClickListener listener) {
        Button button = new Button(layout.getContext());
        button.setTag(name);
        final LinearLayout.LayoutParams paramsBtn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(paramsBtn);
        button.setText(name);
        button.setOnClickListener(listener);
        layout.addView(button);
    }

    public static int getIdentifier(Context context, String name, String type) {
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }
}
