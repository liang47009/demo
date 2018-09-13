package com.yunfeng.gui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.yunfeng.common.jni.JNILib;
import com.yunfeng.common.ui.MyGLSurfaceView;
import com.yunfeng.common.util.UiUtils;

/**
 * main
 * Created by xll on 2018/9/11.
 */
public class MainActivity extends Activity {

    static {
        System.loadLibrary("gui");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);

        Log.d("app", "" + this.getFilesDir().getPath());
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag().equals("load")) {

                } else if (v.getTag().equals("unload")) {

                } else if (v.getTag().equals("test")) {
                    guiClick();
                }
            }
        };
        UiUtils.createButton(layout, "test", listener);

        MyGLSurfaceView view = new MyGLSurfaceView(this);
        layout.addView(view);
        setContentView(layout);
    }

    private static native void guiClick();
}
